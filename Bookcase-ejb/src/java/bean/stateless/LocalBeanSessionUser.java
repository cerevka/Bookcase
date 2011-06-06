package bean.stateless;

import entity.EntityUser;
import entity.EntityGroup;
import java.util.List;
import javax.ejb.Local;

/**
 * Beana zajistujici praci s uzivately.
 * @author Tomáš Čerevka
 */
@Local
public interface LocalBeanSessionUser {

    /**
     * Vrati uzivatele.
     * @param userId Id uzivatele.
     * @return Uzivatel.
     */
    public EntityUser getUser(int userId);
    
    /**
     * Vrati uzivatele s danym e-mailem.
     * @param email E-mail uzivatele.
     * @return  Uzivatel.
     */
    public EntityUser getUserByEmail(String email);

    /**
     * Updatuje uzivatele.
     * @param user Osoba.
     */
    public void updateUser(EntityUser user);

    /**
     * Odstrani uzivatele.
     * @param user Uzivatel.
     */
    public void removeUser(EntityUser user);

    /**
     * Vrati vsechny uzivatele.
     * @return Seznam uzivatelu.
     */
    public List<EntityUser> getAllUsers();

    /**
     * Vrati pratele uzivatele.
     * @param user Uzivatel.
     * @return Seznam pratele.
     */
    public List<EntityUser> getFriends(EntityUser user);

    /**
     * Rozhodne, zda jsou dva uzivatele pratele.
     * @param user1 Prvni uzivatel.
     * @param user2 Druhy uzivatel.
     * @return Potvrzeni/zamitnuti pratelstvi.
     */
    public boolean areFriends(EntityUser user1, EntityUser user2);

    /**
     * Vyvola zadost o pratelstvi mezi dvema uzivateli.
     * @param user1 Prvi uzivatel.
     * @param user2 Druhy uzivatel.
     */
    public void applyFriendship(EntityUser user1, EntityUser user2);

    /**
     * Potvrdi pratelstvi mezi dvema uzivateli.
     * @param user1 Prvni uzivatel.
     * @param user2 Druhy uzivatel.
     */
    public void confirmFriendship(EntityUser user1, EntityUser user2);

    /**
     * Zamitne pratelstvi mezi dvema uzivateli.
     * @param user1 Prvni uzivatel.
     * @param user2 Druhy uzivatel.
     */
    public void refuseFriendship(EntityUser user1, EntityUser user2);

    /**
     * Vrati vsechy skupiny v nichz je uzivatel.
     * @param user Uzivatel.
     * @return Seznam roli.
     */
    public List<EntityGroup> getAllGroupsByUser(EntityUser user);

    /**
     * Prida uzivatele do skupiny.
     * @param person Uzivatel.
     * @param privilege Skupina.
     */
    public void addPrivilege(EntityUser person, EntityGroup privilege);

    /**
     * Odebere uzivatele ze skupiny.
     * @param user Uzivatel.
     * @param group Role.
     */
    public void removeGroup(EntityUser user, EntityGroup group);

    /**
     * Rozhodne, zda je uzivatel v dane skupine.
     * @param user Uzivatel.
     * @param group Role.
     * @return Potvrzeni/zamitnuti role.
     */
    public boolean isInGroup(EntityUser user, EntityGroup group);    
    
}
