package bean.stateless;

import entity.EntityUser;
import entity.EntityGroup;
import exception.ExceptionUserAlreadyExists;
import exception.ExceptionUserDoesNotExist;
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
     * Vyhledá uživatele podle jména 
     * @param firstName křestní jméno   
     * @return Uzivatele
     */
    public List<EntityUser> getUserByName(String firstName);    
    
    /**
     * Vyhledá uživatele podle jména 
     * @param firstName křestní jméno   
     * @param surname příjmení
     * @return Uzivatele
     */
    public List<EntityUser> getUserByNameAndSurname(String firstName, String surname);
    
    /**
     * Vyhledá uživatele podle příjmení
     * @param surName příjmní  
     * @return Uzivatele
     */
    public List<EntityUser> getUserBySurname(String surName);
    
    /**
     * Registruje noveho uzivatele.
     * @param user Registrovany uzivatel.
     * @throws ExceptionUserAlreadyExists Pokud uzivatel s danym e-mailem jiz existuje.
     */
    public void registerNewUser(entity.EntityUser user) throws ExceptionUserAlreadyExists;

    /**
     * Updatuje uzivatele.
     * @param user Osoba.
     */
    public void persistUser(EntityUser user);

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
     * Vrati uzivatele zadajici daneho uzivatele o pratelstvi.
     * @param user uzivatel.
     * @return uzivatele
     */
    public List<EntityUser> getFriendshipRequests(EntityUser user);
    
    
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
    public void addUserInGroup(EntityUser person, EntityGroup privilege);

    /**
     * Odebere uzivatele ze skupiny.
     * @param user Uzivatel.
     * @param group Role.
     */
    public void removeUserFromGroup(EntityUser user, EntityGroup group);

    /**
     * Rozhodne, zda je uzivatel v dane skupine.
     * @param user Uzivatel.
     * @param group Role.
     * @return Potvrzeni/zamitnuti role.
     */
    public boolean isUserInGroup(EntityUser user, EntityGroup group);

    /**
     * Vrati skupiny s danym jmenem.
     * @param group Nazev skupiny.
     * @return Objekt skupiny.
     */
    public EntityGroup getGroupByName(String group);

    /**
     * Umisti e-mail do fronty.
     * @param recipient Adresa prijemce.
     * @param subject Predmet zpravy.
     * @param body Text zpravy.
     */
    public void sendMail(String recipient, String subject, String body);

    /**
     * Nastavi uzivateli nove heslo a odesle mu ho e-mailem.
     * @param email E-mail uzivatele.    
     * @return Vygenerovane heslo.
     * @throws ExceptionUserDoesNotExist Pokud uzivatel s danym e-mailem neexistuje. 
     */
    public String resetPassword(String email) throws ExceptionUserDoesNotExist;
}
