package bean.statefull;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Tomáš Čerevka
 */
@Stateful
public class UserBean implements UserBeanRemote {
    
    @PersistenceContext
    EntityManager em;
}
