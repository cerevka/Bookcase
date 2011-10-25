package bean.managed.requestScope;


import bean.managed.sessionScoped.BeanManagedUser;
import bean.stateless.LocalBeanSessionUser;
import entity.EntityFriendship;
import entity.EntityUser;
import java.util.Collection;
import java.util.ResourceBundle;
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
    public Collection<EntityFriendship> myRequests(){
        return beanSessionUser.getUsersRequests(beanManagedUser.getUser());
    }  
    
     @RolesAllowed({"user", "admin"})
     public void acept(EntityUser user){
         beanSessionUser.confirmFriendship(user, beanManagedUser.getUser());
          
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "bundle");
        String message = bundle.getString("message.friendship.accepted");
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, message, "");
        facesContext.addMessage(null, facesMessage);
     }
    
      @RolesAllowed({"user", "admin"})
     public void reject(EntityUser user){       
         beanSessionUser.refuseFriendship(user, beanManagedUser.getUser());         
          
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "bundle");
        String message = bundle.getString("message.sfriendship.rejected");
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, message, "");
        facesContext.addMessage(null, facesMessage);
     }
         
    
    
}
