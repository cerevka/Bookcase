package bean.managed.requestScope;

import bean.managed.sessionScoped.BeanManagedUser;
import bean.stateless.LocalBeanSessionBook;
import entity.EntityAuthor;
import entity.EntityBook;
import entity.EntityCopy;
import entity.EntityOwnership;
import entity.EntityUser;
import entity.EnumReadState;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 * Prace s knihami.
 * @author Tomáš Čerevka
 */
@ManagedBean(name = "book")
@RequestScoped
public class BeanManagedBook {

    private static final Logger logger = Logger.getLogger(BeanManagedBook.class.getName());

    private EntityBook book = new EntityBook();

    private EntityAuthor author = new EntityAuthor();

    private String authorName;

    private String authorSurname;

    @ManagedProperty(value = "#{user}")
    private BeanManagedUser beanManagedUser;

    public void setBeanManagedUser(BeanManagedUser beanManagedUser) {
        this.beanManagedUser = beanManagedUser;
    }

    @EJB
    private LocalBeanSessionBook beanSessionBook;

    public EntityBook getBook() {
        return book;
    }

    public void setBook(EntityBook book) {
        this.book = book;
    }

    public EntityAuthor getAuthor() {
        return author;
    }

    public void setAuthor(EntityAuthor author) {
        this.author = author;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorSurname() {
        return authorSurname;
    }

    public void setAuthorSurname(String authorSurname) {
        this.authorSurname = authorSurname;
    }

    public BeanManagedBook() {
    }

    @RolesAllowed({"user", "admin"})
    public Collection<EntityAuthor> getAllAuthors() {
        return beanSessionBook.getAllAuthors();
    }

    @RolesAllowed({"user", "admin"})
    public String addNewBook() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "bundle");
        if (author == null) {
            // Novy autor.
            author = new EntityAuthor();
            author.setName(authorName);
            author.setSurname(authorSurname);

            // Hlaseni o novem autorovi.
            String newAuthorPattern = bundle.getString("message.success.authorAdded");
            String newAuthorMessage = MessageFormat.format(newAuthorPattern, authorName + " " + authorSurname);
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, newAuthorMessage, "");
            facesContext.addMessage(null, facesMessage);
        }
        // Kniha i s autorem se prida.
        beanSessionBook.addBook(book, author);

        // Zobrazi se hlaseni o nove knize.
        String newBookPattern = bundle.getString("message.success.bookAdded");
        String newBookMessage = MessageFormat.format(newBookPattern, book.getTitle());
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, newBookMessage, "");
        facesContext.addMessage(null, facesMessage);

        // Vycisti se promenne.
        book = new EntityBook();
        author = new EntityAuthor();
        authorName = authorSurname = "";

        return null;
    }
    
    /**
     * Vrati kolekci svazku, ktere vlastni aktualne prihlaseny uzivatel.
     * @return Kolekce svazku.
     */
    public Collection<EntityCopy> getCopiesOwnedByUser() {
        return getCopiesOwnerByUser(beanManagedUser.getUser());
        
    }
    
    /**
     * Vrati kolekci svazku vlastnene uzivatelem.
     * @param user Uzivatel, ktery ma vlastnit svazky.
     * @return Kolekce svazku, ktere uzivatel vlastni.
     */
    public Collection<EntityCopy> getCopiesOwnerByUser(EntityUser user) {
        return beanSessionBook.getCopiesOwnedByUser(user);
    }

    /**
     * Vrati knizky v policce "default" (zjednoduseni).
     * @return Kolekce svazku.
     */
    public Collection<EntityCopy> getCopiesInShelfs() {
        return beanSessionBook.getCopiesInSelf("default");
    }

    public Collection<EntityCopy> getAllCopies() {
        return beanSessionBook.getAllCopies();
    }
    
    public Collection<EntityBook> getAllBooks() {
        return beanSessionBook.getAllBooks();
    }

    /**
     * Rozhodne o vlastnictvi svazku aktualnim prihlasenym uzivatelem.
     * @param copy Svazek, o nemz se rozhoduje.
     * @return TRUE prihlaseny uzivatel svazek vlastni, jinak FALSE
     */
    public boolean isBookOwnedByUser(EntityCopy copy) {
        return isBookOwnedByUser(beanManagedUser.getUser(), copy);       
    }
    
    /**
     * Rozhodne o vlastnictvi svazku.
     * @param user Uzivatel, kteremu ma svazek patrit.
     * @param copy Svazek, o nemz se rozhoduje.
     * @return TRUE uzivatel svazek vlastni, jinak FALS.
     */
    public boolean isBookOwnedByUser(EntityUser user, EntityCopy copy) {
        return beanSessionBook.isOwner(user, copy);
    }
    
    public EnumReadState getReadstatusOfUserForCopy(EntityCopy copy) {
        for (EntityOwnership ownership : copy.getOwnershipCollection()) {
            if (ownership.getUser().equals(beanManagedUser.getUser())) {
                return ownership.getReadState();
            }
        }
        return EnumReadState.UNREAD;
    }
}
