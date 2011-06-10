package bean.stateless;

import entity.EntityAuthor;
import entity.EntityBook;
import entity.EntityCopy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

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
        Query query = em.createNamedQuery(EntityBook.FIND_BY_ID);
        query.setParameter("id", bookId);
        return (EntityBook) query.getSingleResult();
    }
    
    @Override    
    public void addBook(EntityBook book, EntityAuthor author) {
        // Autor se umisti do databaze.
        if (author.getId() != null) {
           author = getAuthor(author.getId());
        } else {
            em.persist(author);
            em.flush();
        }
        
        // Knize se priradi autor.
        book.setAuthorId(author);
        
        // Autorovi se priradi kniha.
        Collection<EntityBook> booksOfAutor = author.getBookCollection();
        if (booksOfAutor == null) {
            booksOfAutor = new ArrayList<EntityBook>();
        }
        booksOfAutor.add(book);
        
        em.persist(book);
        em.persist(author);                
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
        Query query = em.createNamedQuery(EntityAuthor.FIND_BY_ID);
        query.setParameter("id", authorId);
        return (EntityAuthor) query.getSingleResult();
    }

    @Override
    public List<EntityAuthor> getAllAuthors() {
        TypedQuery<EntityAuthor> query = (TypedQuery<EntityAuthor>) em.createNamedQuery(EntityAuthor.FIND_ALL);
        return query.getResultList();
    }
}
