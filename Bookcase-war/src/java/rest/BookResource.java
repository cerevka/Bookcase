package rest;

import bean.stateless.LocalBeanSessionBook;
import com.sun.jersey.api.client.ClientResponse.Status;
import entity.EntityBook;
import entity.EntityEvaluation;
import entity.EntityRelease;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
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
        try {
            // Vyzvednou se data.
            EntityRelease entityRelease = beanSeasonBook.getReleaseByISBN(isbn);

            // Data se napamatuji na XML entity.                 
            return new Book(entityRelease);
        } catch (Exception exception) {
            throw new VerboseException("Book with ISBN: " + isbn + " was not found!", Status.NOT_FOUND);
        }
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
        try {
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
        } catch (Exception ex) {
            String message = "Book with ISBN: " + isbn + " was not found!";
            throw new VerboseException(message, Status.NOT_FOUND);
        }
    }

    /**
     * Aktualizje popis knihy a vrati jeji aktualni podobu.
     * @param isbn ISBN knihy.
     * @param description Novy popis knihy.
     * @return Nova podoba knihy v XML.
     */
    @PUT
    @Path("/{isbn}/description")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Book updateDescription(@PathParam("isbn") String isbn, @FormParam("description") String description) {
        try {
            beanSeasonBook.updateBookDescriptionByISBN(isbn, description);            
            return getBook(isbn);
        } catch (Exception exception) {
            throw new VerboseException("Book with ISBN: " + isbn + " was not found!", Status.NOT_FOUND);
        }
    }
}
