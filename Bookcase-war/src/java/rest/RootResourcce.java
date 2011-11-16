package rest;

import bean.stateless.LocalBeanSessionBook;
import entity.EntityAuthor;
import entity.EntityBook;
import entity.EntityRelease;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Petr Vales
 */
@Path("/")
@Stateless
public class RootResourcce {
    @EJB
    private LocalBeanSessionBook beanSeasonBook;
    
    @PUT
    @Path("/book")
    @Consumes(MediaType.APPLICATION_XML)
    public void createBook(Book newBook) {
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
        release.setPublishDate(newBook.publishDate);
        release.setPublisher(newBook.publisher);
        beanSeasonBook.addBook(book, authors, release);
    }
}
