package bean.stateless;

import entity.Person;
import entity.Privilege;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Tomáš Čerevka
 */
@Stateless
public class UserSessionBean implements UserSessionBeanRemote {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Person getPerson(int personId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updatePerson(Person person) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removePerson(Person person) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Person> getAllPerson() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Person> getFriends(Person person) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean areFriends(Person person1, Person person2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void applyFriendship(Person person1, Person person2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void confirmFriendship(Person person1, Person person2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void refuseFriendship(Person person1, Person person2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Privilege> getAllPrivileges(Person person) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addPrivilege(Person person, Privilege privilege) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removePrivilege(Person person, Privilege privilege) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean hasPrivilege(Person person, Privilege privilege) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
