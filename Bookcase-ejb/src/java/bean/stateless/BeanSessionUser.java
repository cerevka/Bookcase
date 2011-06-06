package bean.stateless;

import entity.EntityUser;
import entity.EntityGroup;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Tomáš Čerevka
 */
@Stateless
public class BeanSessionUser implements LocalBeanSessionUser {

    @PersistenceContext
    private EntityManager em;

    @Override
    public EntityUser getUser(int userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public EntityUser getUserByEmail(String email) {
        Query query = em.createNamedQuery(EntityUser.FIND_BY_EMAIL);
        query.setParameter("email", email);
        return (EntityUser) query.getSingleResult();
    }

    @Override
    public void updateUser(EntityUser user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeUser(EntityUser user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<EntityUser> getAllUsers() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<EntityUser> getFriends(EntityUser user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean areFriends(EntityUser user1, EntityUser user2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void applyFriendship(EntityUser user1, EntityUser user2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void confirmFriendship(EntityUser user1, EntityUser user2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void refuseFriendship(EntityUser user1, EntityUser user2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<EntityGroup> getAllGroupsByUser(EntityUser user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addPrivilege(EntityUser person, EntityGroup privilege) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeGroup(EntityUser user, EntityGroup group) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isInGroup(EntityUser user, EntityGroup group) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
}
