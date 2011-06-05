package bean.managed.sessionScoped;

import java.security.Principal;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;

/**
 *
 * @author Tomáš Čerevka
 */
@ManagedBean(name = "user")
@SessionScoped
public class BeanManagedUser {

    public static final Logger logger = Logger.getLogger(BeanManagedUser.class.getName());
    private String email;
    private String password;

    /**
     * Vytvori novou beanu.
     */
    public BeanManagedUser() {
    }

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

    /**
     * Prihlasi uzivatele.
     * @return Vysledek akce.
     */
    public String doLogin() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
        try {
            request.login(email, password);
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
        return "logout";
    }

    /**
     * Rozhodne o prihlaseni uzivatele.
     * @return TRUE uzivatel je prihlaseny, FALSE uzivatel neni prihlaseny.
     */
    public Boolean isLogged() {
        return getRole() != null;
    }

    /**
     * Vrati v jake roli je uzivatel prihlasen.
     * @return Role uzivatele.
     */
    public String getRole() {
        Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        if (principal == null) {
            return null;
        }
        return principal.getName();
    }
}
