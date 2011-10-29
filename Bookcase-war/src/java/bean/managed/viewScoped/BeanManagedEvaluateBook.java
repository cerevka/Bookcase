package bean.managed.viewScoped;

import bean.managed.sessionScoped.BeanManagedUser;
import bean.stateless.LocalBeanSessionBook;
import entity.EntityBook;
import entity.EntityEvaluation;
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
@ManagedBean(name = "evaluateBook")
@ViewScoped
public class BeanManagedEvaluateBook {

    private static final Logger logger = Logger.getLogger(BeanManagedFindUser.class.getName());
    private EntityBook book = new EntityBook();
    // @ManagedProperty
    private Integer bookId;
    @ManagedProperty(value = "#{user}")
    private BeanManagedUser beanManagedUser;
    @EJB
    private LocalBeanSessionBook beanSessionBook;

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

    public EntityBook getBook() {
        return book;
    }

    public void setBook(EntityBook book) {
        this.book = book;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public BeanManagedEvaluateBook() {
        bookId = Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("bookId"));
    }

    public EntityBook init() {
        book = beanSessionBook.getBook(bookId);
        return book;

    }

    @RolesAllowed({"user", "admin"})
    public boolean isEvaluated() {
        EntityEvaluation e = beanSessionBook.getEaluationByBookAndUser(book, beanManagedUser.getUser());
        if (e == null) {
             return false;           
        } else {
            return true;
        }
    }

    @RolesAllowed({"user", "admin"})
    public void evaluate(int val) {
        beanSessionBook.evaluateBook(book, beanManagedUser.getUser(), val);


        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "bundle");
        String message = bundle.getString("message.success.evaluation");
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, message, "");
        facesContext.addMessage(null, facesMessage);

    }

    @RolesAllowed({"user", "admin"})
    public String getEvaluation() {
        List<EntityEvaluation> l = beanSessionBook.getEvaluationsByBook(book);
        if (l.isEmpty()) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "bundle");
            return bundle.getString("message.evaluation.missing");
        }
        int i = 0;
        for (EntityEvaluation e : l) {
            i += e.getRate();
        }

        return Integer.toString(i / l.size());
    }

    @RolesAllowed({"user", "admin"})
    public int myEvaluation() {
        EntityEvaluation e = beanSessionBook.getEaluationByBookAndUser(book, beanManagedUser.getUser());


        if (e  == null) {
            return 0;
        } else {
            return e.getRate();
        }
    }
}