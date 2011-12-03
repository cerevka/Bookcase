package bean.managed.requestScope;

import bean.managed.sessionScoped.BeanManagedUser;
import bean.stateless.LocalBeanSessionBook;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import entity.EntityAuthor;
import entity.EntityBook;
import entity.EntityPrint;
import entity.EntityRelease;
import entity.EntityUser;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
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
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedMap;
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
        addBookToFulltextSearch(book);
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
     * Přidá knihu do fulltextového vyhledávače
     * @param EntityBook book kniha 
     */
    public void addBookToFulltextSearch(EntityBook book) {
        Client client = Client.create();

        //prihlaseni do jejich systemu
        WebResource resource = client.resource("http://ec2-46-137-144-208.eu-west-1.compute.amazonaws.com:3000/user/login/");
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.add("username", "bookcase");
        queryParams.add("password", "bookcase");
        ClientResponse clientResponse = resource.post(ClientResponse.class, queryParams);
        //ulozeni seccion cookie
        Cookie sessionCookie = clientResponse.getCookies().get(0);
        switch (clientResponse.getStatus()) {
            case 200:

                break;
            default:
                logger.log(Level.WARNING, "faield to login fulltext service");
                logger.log(Level.WARNING, "book id:{0} was not addes to fulltext search", book.getId());
                break;
        }
        try {
            //pridani stranky do vyhledavace
            //UID mam nastaveny napevno...kdby sme toho s nima delali vic,
            //bylo by lespi je mit nekde v souboru a kontrolovat je
            JSONObject tittle = new JSONObject().put("element_uid", 40);
            tittle.put("content", book.getTitle());

            String authors = "";
            List<EntityAuthor> authorsCollection = (List) book.getAuthorCollection();
            for (EntityAuthor a : authorsCollection) {
                authors += a.getName();
                authors += " ";
                authors += a.getSurname();
                authors += ", ";
            }
            authors = authors.substring(0, authors.length() - 2);

            JSONObject autor = new JSONObject().put("element_uid", 41);
            autor.put("content", authors);
            JSONObject desc = new JSONObject().put("element_uid", 42);
            desc.put("content", book.getDescription());
            JSONArray page = new JSONArray();
            page.put(tittle);
            page.put(autor);
            page.put(desc);
            JSONObject message = new JSONObject();
            message.put("scheme_uid", 185);
            message.put("lang_uid", 1);
            
            //zjisti fragmenty url kde aplikace bezi
            FacesContext ctx = FacesContext.getCurrentInstance();
            String host = ctx.getExternalContext().getRequestServerName();
            String app = ctx.getExternalContext().getRequestContextPath();
            String port = new Integer(ctx.getExternalContext().getRequestServerPort()).toString();

            //kdyby nefungovalo automaticky generovani aktualni url tak na produkcnim serveru
            //nahradit radkou dole
            //  String tag = "http://www.bookcase.cz/book/detail.xhtml?bookId"+book.getId();
            String tag = "http://" + host + ":" + port + app + "/book/detail.xhtml?bookId=" + book.getId();
            message.put("tag", tag);
            message.put("data", page);

            //tohle je tu protoze JSONObject escapuje znak "/"
            String outMessage = message.toString().replace("\\", "");
            WebResource resource2 = client.resource("http://ec2-46-137-144-208.eu-west-1.compute.amazonaws.com:3000/page/");
            ClientResponse clientResponse2 = resource2.cookie(sessionCookie).type("application/json").post(ClientResponse.class, outMessage);

            switch (clientResponse2.getStatus()) {
                case 200:
                    break;
                default:
                    logger.log(Level.WARNING, "faield to add book to fulltext search");
                    logger.log(Level.WARNING, "book id:{0} was not addes to fulltext search", book.getId());
            }
        } catch (JSONException ex) {
            Logger.getLogger(BeanManagedBook.class.getName()).log(Level.SEVERE, null, ex);
        }



        //odhlaseni z jejich systemu
        WebResource resource3 = client.resource("http://ec2-46-137-144-208.eu-west-1.compute.amazonaws.com:3000/user/logout/");
        ClientResponse clientResponse3 = resource3.cookie(sessionCookie).post(ClientResponse.class);

        switch (clientResponse3.getStatus()) {
            case 200:
                break;
            default:
                logger.log(Level.WARNING, "faield to logout from fulltext service");
        }

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
