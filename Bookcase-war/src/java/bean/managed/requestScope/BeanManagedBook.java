package bean.managed.requestScope;

import bean.managed.sessionScoped.BeanManagedUser;
import bean.stateless.LocalBeanSessionBook;
import entity.EntityAuthor;
import entity.EntityBook;
import entity.EntityCopy;
import entity.EntityOwnership;
import entity.EntityUser;
import entity.EnumReadState;
import entity.EntityPrint;
import entity.EntityUser;
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
import javax.faces.event.AjaxBehaviorEvent;

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

    @RolesAllowed({"user", "admin", "librarian"})
    public Collection<EntityAuthor> getAllAuthors() {
        return beanSessionBook.getAllAuthors();
    }

    @RolesAllowed({"user", "admin", "librarian"})
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


    public Collection<EntityPrint> getPrintsOwnedByUser() {
        return getPrintsOwnerByUser(beanManagedUser.getUser());
        

    }

    /**
     * Vrati kolekci vytisku vlastnenych uzivatelem.
     * @param user Uzivatel, ktery ma vlastnit vytisky.
     * @return Kolekce vytisku, ktere uzivatel vlastni.
     */
    public Collection<EntityPrint> getPrintsOwnerByUser(EntityUser user) {
        return beanSessionBook.getPrintsOwnedByUser(user);
    }

    public Collection<EntityPrint> getAllPrints() {
        return beanSessionBook.getAllPrints();
    }

    public Collection<EntityBook> getAllBooks() {
        return beanSessionBook.getAllBooks();
    }

    /**
     * Rozhodne o vlastnictvi vytisku aktualnim prihlasenym uzivatelem.
     * @param print Svazek, o nemz se rozhoduje.
     * @return TRUE prihlaseny uzivatel svazek vlastni, jinak FALSE
     */

    public boolean isBookOwnedByUser(EntityCopy copy) {
        return isBookOwnedByUser(beanManagedUser.getUser(), copy);

    public boolean isPrintOwnedByUser(EntityPrint print) {
        return isPrintOwnedByUser(beanManagedUser.getUser(), print);       

    }

    /**
     * Rozhodne o vlastnictvi svazku.
     * @param user Uzivatel, kteremu ma svazek patrit.
     * @param print Svazek, o nemz se rozhoduje.
     * @return TRUE uzivatel svazek vlastni, jinak FALS.
     */

    public boolean isBookOwnedByUser(EntityUser user, EntityCopy copy) {
        return beanSessionBook.isOwner(user, copy);
    }

    public String getReadstatusOfUserForCopy(EntityCopy copy) {
        for (EntityOwnership ownership : copy.getOwnershipCollection()) {
            if (ownership.getUser().equals(beanManagedUser.getUser())) {
                return ownership.getReadState().name();
            }
        }
        return EnumReadState.UNREAD.name();
    }

    @RolesAllowed({"user", "admin", "librarian"})
    public void setToReadStatus(EntityCopy copy) {
        setReadStateToCopy(copy, EnumReadState.TO_READ);
    }

    @RolesAllowed({"user", "admin", "librarian"})
    public void setReadingStatus(EntityCopy copy) {
        setReadStateToCopy(copy, EnumReadState.READING);
    }

    @RolesAllowed({"user", "admin", "librarian"})
    public void setReadStatus(EntityCopy copy) {
        setReadStateToCopy(copy, EnumReadState.READ);
    }

    @RolesAllowed({"user", "admin", "librarian"})
    public void setUnfinishedStatus(EntityCopy copy) {
        setReadStateToCopy(copy, EnumReadState.UNFINISHED);
    }

    private void setReadStateToCopy(EntityCopy copy, EnumReadState readState) {
        beanSessionBook.setReadStateToBookCopy(readState, copy, beanManagedUser.getUser());
    }

    public boolean isPrintOwnedByUser(EntityUser user, EntityPrint print) {
        return beanSessionBook.isOwner(user, print);
    }

}
