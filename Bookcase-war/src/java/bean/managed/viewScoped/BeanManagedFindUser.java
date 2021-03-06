package bean.managed.viewScoped;

import bean.managed.sessionScoped.BeanManagedUser;
import bean.stateless.LocalBeanSessionUser;
import entity.EntityUser;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Adam Činčura
 */
@ManagedBean(name = "findUser")
@ViewScoped
public class BeanManagedFindUser {

    private static final Logger logger = Logger.getLogger(BeanManagedFindUser.class.getName());
    private List<EntityUser> foundUsers = new ArrayList<EntityUser>();
    private String firstName;
    private String surName;
    @ManagedProperty(value = "#{user}")
    private BeanManagedUser beanManagedUser;
    @EJB
    private LocalBeanSessionUser beanSessionUser;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public Collection<EntityUser> getUsers() {
        return foundUsers;
    }

    public void setUsers(List<EntityUser> users) {
        this.foundUsers = users;
    }

    public void setBeanManagedUser(BeanManagedUser beanManagedUser) {
        this.beanManagedUser = beanManagedUser;
    }

    public BeanManagedFindUser() {
    }

    @RolesAllowed({"user", "admin"})
    public String Find() {
        foundUsers = beanSessionUser.getUserByNameAndSurname(firstName, surName);
        if (foundUsers.isEmpty()) {
            foundUsers = beanSessionUser.getUserBySurname(surName);
            if (foundUsers.isEmpty()) {
                foundUsers = beanSessionUser.getUserByName(firstName);
            }
        }
        
          if(foundUsers.isEmpty()){
       FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "bundle");
        String message = bundle.getString("message.found.nothing");
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, message, "");
        facesContext.addMessage(null, facesMessage);
            
        }
        return null;
    }

    public boolean renderResult() {
        return !foundUsers.isEmpty();
    }

    @RolesAllowed({"user", "admin"})
    public void addFriend(EntityUser user) {
        beanSessionUser.applyFriendship(beanManagedUser.getUser(), user);
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "bundle");
        String message = bundle.getString("message.success.requested");
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, message, "");
        facesContext.addMessage(null, facesMessage);

    }

    @RolesAllowed({"user", "admin"})
    public boolean isFriendship(EntityUser u) {
        return beanSessionUser.areFriends(beanManagedUser.getUser(), u);
    }
}
