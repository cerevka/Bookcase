package rest;

import bean.stateless.LocalBeanSessionBook;
import com.sun.jersey.api.client.ClientResponse.Status;
import entity.EntityAuthor;
import entity.EntityBook;
import entity.EntityEvaluation;
import entity.EntityRelease;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

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
    @Context
    UriInfo uriInfo;
    private static final Logger logger = Logger.getLogger(BookResource.class.getName());

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
            if (evaluation == 0) {
                return Integer.toString(0);
            } else {
                return Integer.toString(evaluation / evaluations.size());
            }
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

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response createBook(Book newBook) {        
        Response response = null;
        try {
            //if (beanSeasonBook.existsISBN(newBook.isbn) == true) {
            //    throw new VerboseException("Book with this ISBN already exists.", Status.CONFLICT);
            //}
            
            List<EntityAuthor> authors = new ArrayList<EntityAuthor>(newBook.authors.size());
            for (Author author : newBook.authors) {
                EntityAuthor entityAuthor = new EntityAuthor();
                entityAuthor.setName(author.name);
                entityAuthor.setSurname(author.surname);
                authors.add(entityAuthor);
            }
            EntityBook book = new EntityBook();
            book.setDescription(newBook.description);
            book.setTitle(newBook.title);
            EntityRelease release = new EntityRelease();
            release.setIsbn(newBook.isbn);
            release.setEan(newBook.ean);
            Date date;
            SimpleDateFormat formatter = new SimpleDateFormat("YYYY");
            date = (Date) formatter.parse(String.valueOf(newBook.publishYear));
            release.setPublishDate(date);
            release.setPublisher(newBook.publisher);
            beanSeasonBook.addBook(book, authors, release);

            URI baseUri = uriInfo.getBaseUri();
            response = Response.created(new URI(baseUri.toString() + "rest/book/" + newBook.isbn)).entity(getBook(newBook.isbn)).build();
        } catch (ParseException exception) {
            logger.log(Level.SEVERE, "Parse Exception", exception);
            throw new VerboseException("Invalid input XML.", Status.BAD_REQUEST);
        } catch (URISyntaxException exception) {
            logger.log(Level.SEVERE, "URI Syntax Exception", exception);
            throw new VerboseException("Error while book creation.", Status.BAD_REQUEST);
        }
        return response;
    }
}
