package bean.managed.requestScope;

import bean.managed.sessionScoped.BeanManagedUser;
import bean.stateless.LocalBeanSessionBook;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import entity.EntityAuthor;
import entity.EntityBook;
import entity.EntityPrint;
import entity.EntityRelease;
import entity.EntityUser;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.ws.rs.core.Response.StatusType;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Prace s knihami.
 * @author Tomáš Čerevka
 */
@ManagedBean(name = "book")
@RequestScoped
public class BeanManagedBook {

    private static final Logger logger = Logger.getLogger(BeanManagedBook.class.getName());
    private EntityBook book = new EntityBook();
    private EntityRelease release = new EntityRelease();
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

    public EntityRelease getRelease() {
        return release;
    }

    public void setRelease(EntityRelease release) {
        this.release = release;
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
        beanSessionBook.addBook(book, release, author);

        // Zobrazi se hlaseni o nove knize.
        String newBookPattern = bundle.getString("message.success.bookAdded");
        String newBookMessage = MessageFormat.format(newBookPattern, book.getTitle());
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, newBookMessage, "");
        facesContext.addMessage(null, facesMessage);

        // Vycisti se promenne.
        book = new EntityBook();
        release = new EntityRelease();
        author = new EntityAuthor();
        authorName = authorSurname = "";

        return null;
    }

    public String prefillBook() {
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "bundle");
        FacesMessage facesMessage;

        Client client = Client.create();
        WebResource resource = client.resource("http://1.mdw11ws105t2.appspot.com/resources/title");
        resource = resource.path(release.getIsbn());
        ClientResponse clientResponse = resource.get(ClientResponse.class);
        
        switch (clientResponse.getStatus()) {
            case 200:
                String data = clientResponse.getEntity(String.class);
                try {
                    JSONObject json = new JSONObject(data);
                    book.setTitle(json.getString("name"));
                    SimpleDateFormat formatter = new SimpleDateFormat("YYYY");
                    release.setPublishDate(formatter.parse(json.getString("publishYear")));
                    release.setPublisher(json.getString("publisherNames"));
                    
                    JSONArray authors = json.getJSONArray("authors");
                    authorName = authors.getJSONObject(0).getString("firstName");
                    authorSurname = authors.getJSONObject(0).getString("surname");
                } catch (JSONException exception) {
                    facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("message.error.invalidInput"), "");
                    facesContext.addMessage(null, facesMessage);
                } catch (ParseException exception) {
                    facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("message.error.invalidPublishDate"), "");
                    facesContext.addMessage(null, facesMessage);
                }
                facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("message.success.bookFound"), "");
                facesContext = FacesContext.getCurrentInstance();
                facesContext.addMessage(null, facesMessage);
                break;
            case 404:
                facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("message.error.bookNotFound"), "");
                facesContext.addMessage(null, facesMessage);
                break;
            default:
                facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("message.error.unknownError"), String.valueOf(clientResponse.getStatus()));
                facesContext.addMessage(null, facesMessage);
                break;
        }

        return null;
    }

    /**
     * Vrati kolekci vytisku, ktere vlastni aktualne prihlaseny uzivatel.
     * @return Kolekce svazku.
     */
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

    /**
     * Vrati vsechny vytisky.
     * @return Kolekce vsech vytisku.
     */
    public Collection<EntityPrint> getAllPrints() {
        return beanSessionBook.getAllPrints();
    }

    /**
     * Vrati vsechny knihy.
     * @return Kolekce vsech knih.
     */
    public Collection<EntityBook> getAllBooks() {
        return beanSessionBook.getAllBooks();
    }

    /**
     * Rozhodne o vlastnictvi vytisku aktualnim prihlasenym uzivatelem.
     * @param print Svazek, o nemz se rozhoduje.
     * @return TRUE prihlaseny uzivatel svazek vlastni, jinak FALSE
     */
    public boolean isPrintOwnedByUser(EntityPrint print) {
        return isPrintOwnedByUser(beanManagedUser.getUser(), print);

    }

    /**
     * Rozhodne o vlastnistvi vytisku.
     * @param user Kteremu uzivateli ma svazek patrit.
     * @param print Ktery svazek ma uzivateli patrit.
     * @return TRUE uzivatel svazek vlastni, jinak FALSE
     */
    public boolean isPrintOwnedByUser(EntityUser user, EntityPrint print) {
        return beanSessionBook.isOwner(user, print);
    }

    /**
     * Nastavi vytisku status rozecteni.
     * @param print Vytisk.
     * @param readState Novy status.
     */
    public void setPrintReadStatus(EntityPrint print, EntityPrint.EnumReadStatus readState) {
        beanSessionBook.setPrintReadState(readState, print);
    }

    /**
     * Zmeni stav svazku.
     * @param print 
     */
    public void changeReadStatus(ValueChangeEvent event) {
        EntityPrint.EnumReadStatus newStatus = (EntityPrint.EnumReadStatus) event.getNewValue();
        EntityPrint print = (EntityPrint) event.getComponent().getAttributes().get("print");
        beanSessionBook.setPrintReadState(newStatus, print);

    }

    /**
     * Vrati pole ruznych stavu vytisku.
     * @return Pole stavu rozectenosti vytisku.
     */
    public EntityPrint.EnumReadStatus[] getReadStatuses() {
        return EntityPrint.EnumReadStatus.values();
    }
}
