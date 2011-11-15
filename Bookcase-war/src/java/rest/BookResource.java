package rest;

import bean.stateless.LocalBeanSessionBook;
import entity.EntityAuthor;
import entity.EntityBook;
import entity.EntityRelease;
import java.util.Iterator;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
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
    
}
