package bean.managed.sessionScoped;

import bean.stateless.LocalBeanSessionUser;
import entity.EntityUser;
import exception.ExceptionUserAlreadyExists;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Tomáš Čerevka
 */
@ManagedBean(name = "user")
@SessionScoped
public class BeanManagedUser implements Serializable {

    public static final Logger logger = Logger.getLogger(BeanManagedUser.class.getName());

    @EJB
    private LocalBeanSessionUser beanSessionUser;

    /**
     * Pouze pro ucely prihlasovani.
     */
    private String email;

    /**
     * Pouze pro ucely prihlasovani a registrace.
     */
    private String password;

    /**
     * Aktualne prihlaseny uzivatel.
     */
    private EntityUser user = new EntityUser();

    public String getEmail() {
        return "";
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return "";
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EntityUser getUser() {
        return user;
    }

    public void setUser(EntityUser user) {
        this.user = user;
    }

    /**
     * Prihlasi uzivatele.
     * @return Vysledek akce.
     */
    public String doLogin() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
        try {
            request.login(email, password);
            user = beanSessionUser.getUserByEmail(email);
            email = password = null;
            return "index";
        } catch (ServletException ex) {
            email = password = null;
            ResourceBundle bundle = ctx.getApplication().getResourceBundle(ctx, "bundle");
            ctx.addMessage("loginForm:submit", new FacesMessage(bundle.getString("message.error.login")));
            return null;
        }

    }

    /**
     * Odhlasi uzivatele.
     * @return Vysledek akce.
     */
    public String doLogout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.invalidate();
        user = new EntityUser();
        return "logout";
    }

    /**
     * Registruje noveho uzivatele.
     * @return Vysledek akce.
     */
    public String doRegister() {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "bundle");
        
        // Overeni, ze se shoduji hesla.
        if (!user.getPassword().equals(password)) {
            FacesMessage message = new FacesMessage(bundle.getString("message.error.passwordsDoNotMatch"));
            context.addMessage("registrationForm:controlPassword", message);
            password = "";
            user.setPassword("");
            logger.log(Level.WARNING, "E-mails do not match.");
            return null;
        }
        
        // Vytvareni uzivatele.
        try {
            beanSessionUser.registerNewUser(user);
        } catch (ExceptionUserAlreadyExists ex) {
            FacesMessage message = new FacesMessage(bundle.getString("message.error.userAlreadyExists"));
            context.addMessage("registrationForm:email", message);
            password = "";
            user.setPassword("");
            logger.log(Level.SEVERE, "User with this e-mail already exists.", ex);
        }
        
        // Registrace je dokoncena.
        user = new EntityUser(); 
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("message.success.registrationComplete"), "");                
        context.addMessage(null, message);
        return "login";
    }

    /**
     * Rozhodne o prihlaseni uzivatele.
     * @return TRUE uzivatel je prihlaseny, FALSE uzivatel neni prihlaseny.
     */
    public Boolean isLogged() {
        return getRole() != null;
    }

    public Boolean isUser() {
        return FacesContext.getCurrentInstance().getExternalContext().isUserInRole("user");
    }

    public Boolean isAdmin() {
        return FacesContext.getCurrentInstance().getExternalContext().isUserInRole("admin");
    }

    /**
     * Vrati v jake roli je uzivatel prihlasen.
     * @return Role uzivatele.
     */
    public String getRole() {
        Collection<String> roles = new ArrayList<String>();
        if (isUser() == true) {
            roles.add("user");
        }
        if (isAdmin() == true) {
            roles.add("admin");
        }
        if (roles.isEmpty() == true) {
            return null;
        }
        return roles.toString();
    }
}
