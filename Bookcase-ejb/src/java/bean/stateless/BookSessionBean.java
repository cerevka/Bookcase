package bean.stateless;

import entity.Author;
import entity.Book;
import entity.Copy;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Tomáš Čerevka
 */
@Stateless
public class BookSessionBean implements BookSessionBeanRemote {
    
   @PersistenceContext
   private EntityManager em;

    @Override
    public Book getBook(int bookId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Copy getCopy(int copyId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Copy> getCopies(Book book) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Book> getAllBooks() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Copy> getAllCopies() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Book> getAllBooksFromAuthor(Author author) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Copy> getAllCopiesFromAuthor(Author author) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Author getAuthor(int authorId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Author> getAllAuthors() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
