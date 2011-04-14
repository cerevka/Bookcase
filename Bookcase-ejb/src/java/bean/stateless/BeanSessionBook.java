package bean.stateless;

import entity.EntityAuthor;
import entity.EntityBook;
import entity.EntityCopy;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Tomáš Čerevka
 */
@Stateless
public class BeanSessionBook implements LocalBeanSessionBook {
    
   @PersistenceContext
   private EntityManager em;

    @Override
    public EntityBook getBook(int bookId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityCopy getCopy(int copyId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<EntityCopy> getCopies(EntityBook book) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<EntityBook> getAllBooks() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<EntityCopy> getAllCopies() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<EntityBook> getAllBooksFromAuthor(EntityAuthor author) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<EntityCopy> getAllCopiesFromAuthor(EntityAuthor author) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityAuthor getAuthor(int authorId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<EntityAuthor> getAllAuthors() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
