package bean.stateless;

import entity.EntityFriendship;
import entity.EntityUser;
import entity.EntityGroup;
import entity.EntityShelf;
import exception.ExceptionUserAlreadyExists;
import exception.ExceptionUserDoesNotExist;
import java.security.Principal;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
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
public class BeanSessionUser implements LocalBeanSessionUser {

    private static final Logger logger = Logger.getLogger(BeanSessionUser.class.getName());

    @PersistenceContext
    private EntityManager em;

    @Resource(lookup = "jms/bookcaseMail")
    private Destination mailQueue;

    @Resource(lookup = "jms/bookcaseMailFactory")
    private ConnectionFactory connectionMailFactory;

    @Override
    public EntityUser getUser(int userId) {
        Query query = em.createNamedQuery(EntityUser.FIND_BY_ID);
        query.setParameter("id", userId);
        return (EntityUser) query.getSingleResult();
    }

    @Override
    public EntityUser getUserByEmail(String email) {
        Query query = em.createNamedQuery(EntityUser.FIND_BY_EMAIL);
        query.setParameter("email", email);
        return (EntityUser) query.getSingleResult();
    }

    @Override
    public EntityGroup getGroupByName(String group) {
        Query query = em.createNamedQuery(EntityGroup.FIND_BY_NAME);
        query.setParameter("name", group);
        return (EntityGroup) query.getSingleResult();
    }

    @Override
    public void registerNewUser(EntityUser user) throws ExceptionUserAlreadyExists {
        // Neexistuje jiz uzivatel se stejnym e-mailem?
        TypedQuery<EntityUser> query = (TypedQuery<EntityUser>) em.createNamedQuery(EntityUser.FIND_BY_EMAIL);
        query.setParameter("email", user.getEmail());
        Collection<EntityUser> users = query.getResultList();
        if (!users.isEmpty()) {
            throw new ExceptionUserAlreadyExists(user.getEmail());
        }

        // Uzivatel se ulozi.
        em.persist(user);
        em.flush();

        // Uzivateli se priradi role "user".               
        EntityGroup userGroup = getGroupByName("user");
        addUserInGroup(user, userGroup);
        
        // Uzivateli se vytvori defaultni policka.
        EntityShelf shelf = new EntityShelf();
        shelf.setUserId(user);
        shelf.setName("default");
        
        Collection<EntityShelf> shelfsOfUser = user.getShelfCollection();
        if (shelfsOfUser == null) {
            shelfsOfUser = new ArrayList<EntityShelf>();
        }
        shelfsOfUser.add(shelf);
        
        em.persist(shelf);
        em.persist(user);
    }

    @Override
    public String resetPassword(String email) throws ExceptionUserDoesNotExist {
        try {
            // Sahne se pro uzivatele do databaze.
            EntityUser user = getUserByEmail(email);
            String newPassword = generatePassword(8);
            user.setPassword(newPassword);
            em.persist(user);  
            return newPassword;
        } catch (NoResultException exception) {
            // Uzivatel v databazi neni.
            throw new ExceptionUserDoesNotExist(email);
        }
    }
    
    /**
     * Vygeneruje nahodne heslo o zadane delce.
     * @param lenght Delka noveho hesla.
     * @return Vygenerovane heslo.
     */
    private String generatePassword(int lenght) {
        String charset = "!0123456789abcdefghijklmnopqrstuvwxyz";
        Random random = new Random(System.currentTimeMillis());
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < lenght; ++i) {
            int position = random.nextInt(charset.length());
            stringBuffer.append(charset.charAt(position));
        }
        return stringBuffer.toString();
    }

    @Override
    public void persistUser(EntityUser user) {
        em.persist(user);
    }

    @Override
    public void removeUser(EntityUser user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<EntityUser> getAllUsers() {
         Query query = em.createNamedQuery(EntityUser.FIND_ALL);
        return query.getResultList();
    }

    @Override
    public List<EntityUser> getFriends(EntityUser user) {
        Query query = em.createNamedQuery(EntityFriendship.FIND_BY_USER1_AND_STATE);
        query.setParameter("userId1", user);
        query.setParameter("status" ,EntityFriendship.FriendshipState.AUTHORIZED);
        List<EntityFriendship> listFriendship = query.getResultList();
        List<EntityUser> listUsers= new ArrayList<EntityUser>();
       
        //do listu si ulozim jeho pratele ktere zadal o pratelstvi
        for(EntityFriendship e : listFriendship){
            listUsers.add(e.getUserId2());
        }
        
        
        Query query2 = em.createNamedQuery(EntityFriendship.FIND_BY_USER2_AND_STATE);
        query2.setParameter("userId2", user);
        query2.setParameter("status" ,EntityFriendship.FriendshipState.AUTHORIZED);
        listFriendship = query2.getResultList();
        
        //jeste pridam ty kteri zadali jeho
        for(EntityFriendship e : listFriendship){
            listUsers.add(e.getUserId1());
        }
        
        return listUsers;
    }

    @Override
    public boolean areFriends(EntityUser user1, EntityUser user2) {
         Query query = em.createNamedQuery(EntityFriendship.FIND_BY_USER1);
        query.setParameter("userId1", user1);
        List<EntityFriendship> l= query.getResultList();
        Query query2 = em.createNamedQuery(EntityFriendship.FIND_BY_USER2);
        query2.setParameter("userId2", user1);
        l.addAll(query2.getResultList());
        
        for(EntityFriendship e: l){
            if(e.getUserId1().getId()==user2.getId()||e.getUserId2().getId()==user2.getId()){
                return true;
            }
        }
        return false;
    }

    @Override
    public void applyFriendship(EntityUser requestingUser, EntityUser requestedUser) {
       
        EntityUser user = getUserByEmail(requestingUser.getEmail());
        EntityUser user1 = getUserByEmail(requestedUser.getEmail());
         
         EntityFriendship f = new EntityFriendship();
         em.persist(f);
         f.setUserId1(requestingUser);
         f.setUserId2(requestedUser);
         f.setState(EntityFriendship.FriendshipState.UNAUTHORIZED);
         Collection<EntityFriendship> friendshipsRequestingUser = user.getFriendshipCollection1();
     
         if (friendshipsRequestingUser == null) {
            friendshipsRequestingUser = new ArrayList<EntityFriendship>();
        }
        friendshipsRequestingUser.add(f);
        
               

        Collection<EntityFriendship> friendshipsRequestedUser = user1.getFriendshipCollection2();
         if (friendshipsRequestedUser == null) {
            friendshipsRequestedUser= new ArrayList<EntityFriendship>();
        }
        
        friendshipsRequestedUser.add(f);
        em.persist(user);
        em.persist(user1);
        em.persist(f);
      
        em.flush();
    
    }

     @Override
    public List<EntityUser> getFriendshipRequests(EntityUser user) {
         Query query = em.createNamedQuery(EntityFriendship.FIND_BY_USER2_AND_STATE);
        query.setParameter("userId2", user);
        query.setParameter("status", EntityFriendship.FriendshipState.UNAUTHORIZED);
        List<EntityFriendship> l = query.getResultList();
        List<EntityUser> listUsers = new ArrayList<EntityUser>();
        for(EntityFriendship e :l){
            listUsers.add(e.getUserId1());
        }
        
         
         return listUsers;
    }
    
    @Override
    public void confirmFriendship(EntityUser user1, EntityUser user2) {
       Query query = em.createNamedQuery(EntityFriendship.FIND_BY_USER1_AND_USER2);
        query.setParameter("userId1", user1);
        query.setParameter("userId2", user2);
        EntityFriendship f =(EntityFriendship) query.getSingleResult();
        f.setState(EntityFriendship.FriendshipState.AUTHORIZED);
        em.persist(f);
        em.flush();
    }
    
    @Override
    public List<EntityFriendship> getUsersRequests(EntityUser user) {
        Query query = em.createNamedQuery(EntityFriendship.FIND_BY_USER1_AND_NEG_STATE);
        query.setParameter("userId1", user);
        query.setParameter("status", EntityFriendship.FriendshipState.AUTHORIZED);
        return query.getResultList();
    }

    

    @Override
    public void refuseFriendship(EntityUser user1, EntityUser user2) {
       Query query = em.createNamedQuery(EntityFriendship.FIND_BY_USER1_AND_USER2);
        query.setParameter("userId1", user1);
        query.setParameter("userId2", user2);
        EntityFriendship f =(EntityFriendship) query.getSingleResult();
        f.setState(EntityFriendship.FriendshipState.REJECTED);
        em.persist(f);
        em.flush();
    }

    @Override
    public List<EntityGroup> getAllGroupsByUser(EntityUser user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @RolesAllowed("admin")
    public void addUserInGroup(EntityUser user, EntityGroup group) {
        Collection<EntityGroup> groupsOfUser = user.getGroupCollection();
        if (groupsOfUser == null) {
            groupsOfUser = new ArrayList<EntityGroup>();
        }
        Collection<EntityUser> usersInGroup = group.getUserCollection();
        if (usersInGroup == null) {
            usersInGroup = new ArrayList<EntityUser>();
        }
        groupsOfUser.add(group);
        user.setGroupCollection(groupsOfUser);

        usersInGroup.add(user);
        group.setUserCollection(usersInGroup);

        em.persist(user);
        em.persist(group);
    }

    @Override
    public void removeUserFromGroup(EntityUser user, EntityGroup group) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isUserInGroup(EntityUser user, EntityGroup group) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void sendMail(String recipient, String subject, String body) {
        try {
            // Pripravi se spojeni.
            Connection connection = connectionMailFactory.createConnection();
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(mailQueue);

            // Sestavi se zprava.
            Message message = session.createMessage();
            message.setStringProperty("recipient", recipient);
            message.setStringProperty("subject", subject);
            message.setStringProperty("body", body);

            // Zprava se posle do fronty.
            producer.send(message);
            session.close();
            connection.close();
        } catch (JMSException exception) {
            logger.log(Level.SEVERE, "Error when putting a message into queue: JMS exception", exception);
        }
    }

 

    @Override
    public List<EntityUser> getUserByName(String firstName) {
         Query query = em.createNamedQuery(EntityUser.FIND_BY_NAME);
        query.setParameter("name", firstName);
        return query.getResultList();
    }

    @Override
    public List<EntityUser> getUserByNameAndSurname(String firstName, String surName) {
        Query query = em.createNamedQuery(EntityUser.FIND_BY_NAME_AND_SURNAME);
        query.setParameter("name", firstName);
        query.setParameter("surname", surName);
        return query.getResultList();
    }

    @Override
    public List<EntityUser> getUserBySurname(String surName) {
        Query query = em.createNamedQuery(EntityUser.FIND_BY_SURNAME);
        query.setParameter("surname", surName);
        return query.getResultList();
    }

   
   
}
