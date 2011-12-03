package bean.managed.viewScoped;

import bean.managed.sessionScoped.BeanManagedUser;
import bean.stateless.LocalBeanSessionBook;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import entity.EntityBook;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedMap;

/**
 *
 * @author Adam Činčura
 */
@ManagedBean(name = "findBook")
@ViewScoped
public class BeanManagedFindBook {

    private static final Logger logger = Logger.getLogger(BeanManagedFindUser.class.getName());
    private static final String FULL_TEXT_SEARCHER = "http://ec2-46-137-144-208.eu-west-1.compute.amazonaws.com:3000";
    private List<EntityBook> foundBooks = new ArrayList<EntityBook>();
    private List<SearchResult> searchResults = new ArrayList<SearchResult>();
    private EntityBook book = new EntityBook();
    private String tittle;
    @ManagedProperty(value = "#{user}")
    private BeanManagedUser beanManagedUser;
    @EJB
    private LocalBeanSessionBook beanSessionBook;
    
    public BeanManagedFindBook() {
    }

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

    public List<SearchResult> getSearchResults() {
        return searchResults;
    }

    public String Find() {
        foundBooks = beanSessionBook.getBooksByTittle(tittle);
        if (foundBooks.isEmpty()) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "bundle");
            String message = bundle.getString("message.found.nothing");
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, message, "");
            facesContext.addMessage(null, facesMessage);
            
        }
        searchResults = findBookInFullTextSearch(tittle);
        for (SearchResult searchResult : searchResults) {
            String replace = searchResult.getResult().replace("<sel>", "<strong>");
            replace = replace.replace("</sel>", "</strong>");
            replace = replace.replace("<fragsplit>", "<br />");
            searchResult.setResult(replace);
        }
        return null;
    }

    @RolesAllowed({"user", "admin"})
    public boolean isEvaluated(EntityBook book) {
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

    public String bookDetail(EntityBook b) {

        return "/book/detail.xhtml?faces-redirect=true&bookId=" + b.getId().toString();

    }

    public List<SearchResult> findBookInFullTextSearch(String searchedText) {
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        Cookie sessionCookie = null;
        try {
            sessionCookie = logInFullTextSearcher(client);
        } catch (LoginException ex) {
            return Collections.EMPTY_LIST;
        }
        List<SearchResult> results = getSearchResults(client, searchedText, sessionCookie);
        try {
            logOutFullTextSearcher(client, sessionCookie);
        } catch(LogoutException e) {
            return results;
        }
        return results;
    }

    private List<SearchResult> getSearchResults(Client client, String searchedText, Cookie sessionCookie) {
        WebResource resource = client.resource(FULL_TEXT_SEARCHER + "/search/");
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.putSingle("query", searchedText);
        queryParams.putSingle("lang", "cs");
        queryParams.putSingle("scheme", "books");
        ClientResponse response = resource.cookie(sessionCookie).post(ClientResponse.class, queryParams);
        switch (response.getStatus()) {
            case 200:
                return response.getEntity(new GenericType<List<SearchResult>>() {});
            default:
                logger.log(Level.WARNING, "faield get search results for inpit {0}", searchedText);
                return Collections.EMPTY_LIST;
        }
    }

    private void logOutFullTextSearcher(Client client, Cookie sessionCookie) {
        WebResource resource3 = client.resource(FULL_TEXT_SEARCHER + "/user/logout/");
        ClientResponse clientResponse3 = resource3.cookie(sessionCookie).post(ClientResponse.class);
        switch (clientResponse3.getStatus()) {
            case 200:
                return;
            default:
                logger.log(Level.WARNING, "faield to logout fulltext service");
                throw new LogoutException("Failed to logout full text searcher");
        }
    }

    private Cookie logInFullTextSearcher(Client client) {
        WebResource resource = client.resource(FULL_TEXT_SEARCHER + "/user/login/");
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.add("username", "bookcase");
        queryParams.add("password", "bookcase");
        ClientResponse clientResponse = resource.post(ClientResponse.class, queryParams);
        Cookie sessionCookie = clientResponse.getCookies().get(0);
        switch (clientResponse.getStatus()) {
            case 200:
                return sessionCookie;
            default:
                logger.log(Level.WARNING, "faield to login fulltext service");
                throw new LoginException("Failed to login full text searcher");
        }
    }
    
    
    private class LoginException extends RuntimeException {
         public LoginException(String message) {
            super(message);
        }
    }
    
    private class LogoutException extends RuntimeException {
         public LogoutException(String message) {
            super(message);
        }
    }  
}
