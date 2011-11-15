package rest;

import bean.stateless.LocalBeanSessionBook;
import com.sun.jersey.api.client.ClientResponse.Status;
import entity.EntityAuthor;
import entity.EntityBook;
import entity.EntityEvaluation;
import entity.EntityRelease;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Tomáš Čerevka
 * @author Adam Činčura
 */
@Path("/book")
@Stateless
public class BookResource {

    @EJB
    private LocalBeanSessionBook beanSeasonBook;

    
    /**
     * Vrati informace o knize.
     * @param isbn ISBN knihy.
     * @return Informace v XML.
     * @throws WebApplicationException Vraci 404, pokud kniha neexistuje.
     */
    @GET
    @Path("/{isbn}")
    @Produces(MediaType.APPLICATION_XML)
    public Book getBook(@PathParam("isbn") String isbn) {

        // Vyzvednou se data.
        EntityRelease entityRelease = beanSeasonBook.getReleaseByISBN(isbn);
        EntityBook entityBook = entityRelease.getBook();

        // Data se napamatuji na XML entity.
        Book book = new Book();
        book.isbn = entityRelease.getIsbn();
        book.title = entityBook.getTitle();
        for (EntityAuthor author : entityBook.getAuthorCollection()) {
            Author authorXML = new Author(author.getName(), author.getSurname());
            book.authors.add(authorXML);
        }
        return book;
    }
    
    
     
    /**
     * Vrati hodnoceni knihy.
     * @param isbn ISBN knihy.
     * @return Hodnoceni v TEXT_PLAIN.
     * @throws VerboseException Vraci 404, pokud kniha neexistuje.
     */
    @GET
    @Path("/{isbn}/rating")
    @Produces(MediaType.TEXT_PLAIN)
    public String getRating(@PathParam("isbn") String isbn) {

        
        try{
        // nactu vsechny hodnoceni dane knihy
        EntityRelease entityRelease = beanSeasonBook.getReleaseByISBN(isbn);
        EntityBook entityBook = entityRelease.getBook();
         List<EntityEvaluation> evaluations = beanSeasonBook.getEvaluationsByBook(entityBook);
         
         //spocte se hodnoceni jako prumer vsech hodnoceni
        int evaluation = 0;
        for (EntityEvaluation e : evaluations) {
            evaluation += e.getRate();
        }
        
        return Integer.toString(evaluation / evaluations.size());
        }
        catch(Exception ex){
            String message="Book with ISBN: "+isbn+" was not found!";
         throw new VerboseException(message,Status.NOT_FOUND);
            
        }
       
       
       
      
    }
    
    
}
