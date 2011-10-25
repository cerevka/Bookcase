package bean.stateless;

import entity.EntityCopy;
import entity.EntityGroup;
import entity.EntityUser;
import entity.EntityShelf;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Tomáš Čerevka
 */
@Stateless
public class BeanSessionBookcase implements LocalBeanSessionBookcase {
    
   @PersistenceContext
   private EntityManager em;
   
   @Override
   public void initDatabase() {
       // Vytvori se view.
       Query queryView = em.createNativeQuery("CREATE OR REPLACE VIEW viewLogin AS SELECT `entityUser`.`email` AS `email`, `entityUser`.`password` AS `password`, `entityGroup`.`name` AS `groupName` FROM `entityUser` JOIN `userInGroup` ON `entityUser`.`id` = `userInGroup`.`userId` JOIN `entityGroup` ON `entityGroup`.`id` = `userInGroup`.`groupId`;");
       queryView.executeUpdate();
       
       // Skupina "admin".
       TypedQuery<EntityGroup> queryAdmin = (TypedQuery<EntityGroup>) em.createNamedQuery(EntityGroup.FIND_BY_NAME);
       queryAdmin.setParameter("name", "admin");
       try {
           queryAdmin.getSingleResult();
       } catch (NoResultException exception) {
           // Skupina "admin" neexistuje, vytvori se.
           EntityGroup groupAdmin = new EntityGroup();
           groupAdmin.setName("admin");
           em.persist(groupAdmin);
       }
       
       // Skupina "user".
       TypedQuery<EntityGroup> queryUser = (TypedQuery<EntityGroup>) em.createNamedQuery(EntityGroup.FIND_BY_NAME);
       queryUser.setParameter("name", "user");
       try {
           queryUser.getSingleResult();           
       } catch (NoResultException exception) {
           // Skupina "user" neexistuje, vytvori se.
           EntityGroup groupUser = new EntityGroup();
           groupUser.setName("user");
           em.persist(groupUser);
       }
       
       // Testovaci uzivatel.
       TypedQuery<EntityGroup> queryTestUser = (TypedQuery<EntityGroup>) em.createNamedQuery(EntityUser.FIND_BY_EMAIL);
       queryTestUser.setParameter("email", "bookcase@cerevka.cz");
       try {
           queryTestUser.getSingleResult();
       } catch (NoResultException exception) {
           // Testovaci uzivatel neexistuje, vytvori se.
           EntityUser user = new EntityUser();
           user.setEmail("bookcase@cerevka.cz");
           user.setName("Book");
           user.setSurname("Case");
           user.setPassword("bookcase");
           em.persist(user);
           
           // Testovaci uzivatel se zaradi do skupiny.
           TypedQuery<EntityGroup> queryUserGroup = (TypedQuery<EntityGroup>) em.createNamedQuery(EntityGroup.FIND_BY_NAME);
           queryUserGroup.setParameter("name", "user");
           EntityGroup userGroup = queryUserGroup.getSingleResult();
           user.getGroupCollection().add(userGroup);
           userGroup.getUserCollection().add(user);
           em.persist(user);
           em.persist(userGroup);
       }
       
       em.flush();            
   }

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
