package bean.managed.requestScope;


import bean.managed.sessionScoped.BeanManagedUser;
import bean.stateless.LocalBeanSessionUser;
import entity.EntityUser;
import java.util.Collection;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Adam Činčura
 */
@ManagedBean(name = "friendship")
@RequestScoped
public class BeanManagedFriendship {

    private static final Logger logger = Logger.getLogger(BeanManagedFriendship.class.getName());
 

    @ManagedProperty(value = "#{user}")
    private BeanManagedUser beanManagedUser;
    
    @EJB
    private LocalBeanSessionUser beanSessionUser;


    public void setBeanManagedUser(BeanManagedUser beanManagedUser) {
        this.beanManagedUser = beanManagedUser;
    }

    public BeanManagedFriendship() {
    }

      
    @RolesAllowed({"user", "admin"})
    public Collection<EntityUser> friends(){
        return beanSessionUser.getFriends(beanManagedUser.getUser());
    }
    
    @RolesAllowed({"user", "admin"})
    public Collection<EntityUser> requests(){
        return beanSessionUser.getFriendshipRequests(beanManagedUser.getUser());
    }
    
     @RolesAllowed({"user", "admin"})
     public void acept(EntityUser user){
         beanSessionUser.confirmFriendship(user, beanManagedUser.getUser());
     }
    
      @RolesAllowed({"user", "admin"})
     public void reject(EntityUser user){
          //message
         beanSessionUser.refuseFriendship(user, beanManagedUser.getUser());
     }
     
    
    public void addFriend(EntityUser user) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Zadost odeslana.", "");
        FacesContext.getCurrentInstance().addMessage(null, message);
        beanSessionUser.applyFriendship(beanManagedUser.getUser(), user);
    }
    
    
}
