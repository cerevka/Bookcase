package bean.managed.viewScoped;

import bean.managed.sessionScoped.BeanManagedUser;
import bean.stateless.LocalBeanSessionBook;
import entity.EntityBook;
import entity.EntityUser;
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
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Adam Činčura
 */
@ManagedBean(name = "findBook")
@ViewScoped
public class BeanManagedFindBook {

    private static final Logger logger = Logger.getLogger(BeanManagedFindUser.class.getName());
   
    private List<EntityBook> foundBooks = new ArrayList<EntityBook>();
    
    private EntityBook book = new EntityBook();
    
   
    private String tittle; 
    
    @ManagedProperty(value = "#{user}")
    private BeanManagedUser beanManagedUser;
    @EJB
    private LocalBeanSessionBook beanSessionBook;
    
    
    public EntityBook getBook() {
        return book;
    }

    public void setBook(EntityBook book) {
        this.book = book;
    }
    
    public BeanManagedUser getBeanManagedUser() {
        return beanManagedUser;
    }

    public void setBeanManagedUser(BeanManagedUser beanManagedUser) {
        this.beanManagedUser = beanManagedUser;
    }

    public LocalBeanSessionBook getBeanSessionBook() {
        return beanSessionBook;
    }

    public void setBeanSessionBook(LocalBeanSessionBook beanSessionBook) {
        this.beanSessionBook = beanSessionBook;
    }


    public List<EntityBook> getFoundBooks() {
        return foundBooks;
    }

    public void setFoundBooks(List<EntityBook> foundBooks) {
        this.foundBooks = foundBooks;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }
       
 
    public BeanManagedFindBook() {
    }

    
    public String Find() {
        foundBooks = beanSessionBook.getBooksByTittle(tittle);
        if(foundBooks.isEmpty()){
       FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "bundle");
        String message = bundle.getString("message.found.nothing");
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, message, "");
        facesContext.addMessage(null, facesMessage);
            
        }
        return null;
    }
    
    @RolesAllowed({"user", "admin"})
    public boolean isEvaluated(EntityBook book){
        return true;
    }
    
    
    public boolean renderResult() {
        return !foundBooks.isEmpty();
    }

    @RolesAllowed({"user", "admin"})
    public void evaluate(int val) {
      //  beanSessionUser.applyFriendship(beanManagedUser.getUser(), user);
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "bundle");
        String message = bundle.getString("message.success.requested");
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, message, "");
        facesContext.addMessage(null, facesMessage);

    }
   
    public String bookDetail(EntityBook b){
        
       return "/book/detail.xhtml?faces-redirect=true&bookId=" + b.getId().toString();
     
    }
    
}
