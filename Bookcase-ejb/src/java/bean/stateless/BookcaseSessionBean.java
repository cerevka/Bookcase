package bean.stateless;

import entity.Book;
import entity.Copy;
import entity.Person;
import entity.Shelf;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Tomáš Čerevka
 */
@Stateless
public class BookcaseSessionBean implements BookcaseSessionBeanRemote {
    
   @PersistenceContext
   private EntityManager em;

    @Override
    public List<Copy> getBorrowedCopiesByPerson(Person person) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void borrowCopy(Book copy, Person person) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void returnCopy(Copy copy) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Copy> getReservedCopiesByPerson(Person person) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Copy> getOwnedCopiesByPerson(Person person) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Copy> getCopiesInShelf(Shelf shelf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void insertCopyInShelf(Copy copy, Shelf shelf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeCopyFromShlef(Copy copy, Shelf shelf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isCopyInShelf(Copy copy, Shelf shelf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
