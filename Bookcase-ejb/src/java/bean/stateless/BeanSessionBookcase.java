package bean.stateless;

import entity.EntityCopy;
import entity.EntityUser;
import entity.EntityShelf;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Tomáš Čerevka
 */
@Stateless
public class BeanSessionBookcase implements LocalBeanSessionBookcase {
    
   @PersistenceContext
   private EntityManager em;

    @Override
    public List<EntityCopy> getBorrowedCopiesByUser(EntityUser user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void borrowCopy(EntityCopy copy, EntityUser user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void returnCopy(EntityCopy copy) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<EntityCopy> getReservedCopiesByUser(EntityUser user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<EntityCopy> getOwnedCopiesByUser(EntityUser user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<EntityCopy> getCopiesInShelf(EntityShelf shelf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void insertCopyInShelf(EntityCopy copy, EntityShelf shelf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeCopyFromShlef(EntityCopy copy, EntityShelf shelf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isCopyInShelf(EntityCopy copy, EntityShelf shelf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
