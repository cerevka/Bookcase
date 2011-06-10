package bean.managed.requestScope;

import bean.managed.sessionScoped.BeanManagedUser;
import bean.statefull.LocalBeanSessionBasket;
import bean.stateless.LocalBeanSessionBook;
import entity.EntityAuthor;
import entity.EntityBook;
import entity.EntityCopy;
import entity.EntityShelf;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
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
     * Vrati knizky v policce "default" (zjednoduseni).
     * @return Kolekce svazku.
     */
    public Collection<EntityCopy> getCopiesInShelfs() {
        return beanSessionBook.getCopiesInSelf("default");
    }

    public Collection<EntityCopy> getAllCopies() {
        return beanSessionBook.getAllCopies();
    }

    public boolean isBookOwnedByUser(EntityCopy copy) {
        List<EntityShelf> shelfs = (List<EntityShelf>) copy.getShelfCollection();
        // Staci libovolna policka - svazek patri prave jednomu uzivateli.
        EntityShelf shelf = shelfs.get(0);
        return !shelf.getUserId().equals(beanManagedUser.getUser());
    }
}
