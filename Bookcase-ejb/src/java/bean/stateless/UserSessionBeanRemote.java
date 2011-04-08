package bean.stateless;

import entity.Person;
import entity.Privilege;
import java.util.List;
import javax.ejb.Remote;

/**
 * Beana zajistujici praci s uzivately.
 * @author Tomáš Čerevka
 */
@Remote
public interface UserSessionBeanRemote {

    /**
     * Vrati uzivatele.
     * @param int personId Id uzivatele.
     * @return Osoba.
     */
    public Person getPerson(int personId);

    /**
     * Updatuje uzivatele.
     * @param person Osoba.
     */
    public void updatePerson(Person person);

    /**
     * Odstrani uzivatele.
     * @param person Uzivatel.
     */
    public void removePerson(Person person);

    /**
     * Vrati vsechny uzivatele.
     * @return Seznam uzivatelu.
     */
    public List<Person> getAllPerson();

    /**
     * Vrati pratele uzivatele.
     * @param person Uzivatel.
     * @return Seznam pratele.
     */
    public List<Person> getFriends(Person person);

    /**
     * Rozhodne, zda jsou dva uzivatele pratele.
     * @param person1 Prvni uzivatel.
     * @param person2 Druhy uzivatel.
     * @return Potvrzeni/zamitnuti pratelstvi.
     */
    public boolean areFriends(Person person1, Person person2);

    /**
     * Vyvola zadost o pratelstvi mezi dvema uzivateli.
     * @param person1 Prvi uzivatel.
     * @param person2 Druhy uzivatel.
     */
    public void applyFriendship(Person person1, Person person2);

    /**
     * Potvrdi pratelstvi mezi dvema uzivateli.
     * @param person1 Prvni uzivatel.
     * @param person2 Druhy uzivatel.
     */
    public void confirmFriendship(Person person1, Person person2);

    /**
     * Zamitne pratelstvi mezi dvema uzivateli.
     * @param person1 Prvni uzivatel.
     * @param person2 Druhy uzivatel.
     */
    public void refuseFriendship(Person person1, Person person2);

    /**
     * Vrati vsechy uzivatelovy role.
     * @param person Uzivatel.
     * @return Seznam roli.
     */
    public List<Privilege> getAllPrivileges(Person person);

    /**
     * Prida uzivateli privilegium.
     * @param person Uzivatel.
     * @param privilege Privilegium.
     */
    public void addPrivilege(Person person, Privilege privilege);

    /**
     * Odebere uzivatele z role.
     * @param person Uzivatel.
     * @param privilege Role.
     */
    public void removePrivilege(Person person, Privilege privilege);

    /**
     * Rozhodne, zda ma uzivatel danou roli.
     * @param person Uzivatel.
     * @param privilege Role.
     * @return Potvrzeni/zamitnuti role.
     */
    public boolean hasPrivilege(Person person, Privilege privilege);
    
}
