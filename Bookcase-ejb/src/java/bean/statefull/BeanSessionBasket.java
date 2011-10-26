package bean.statefull;

import bean.stateless.LocalBeanSessionUser;
import entity.EntityBorrow;
import entity.EntityPrint;
import entity.EntityUser;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Tomáš Čerevka
 */
@Stateful
public class BeanSessionBasket implements LocalBeanSessionBasket {

    private static final Logger logger = Logger.getLogger(BeanSessionBasket.class.getName());

    @EJB
    private LocalBeanSessionUser beanSessionUser;

    @Resource
    private SessionContext sessionContext;

    @PersistenceContext
    private EntityManager em;

    private Collection<EntityPrint> content = new ArrayList<EntityPrint>();

    @Override
    public Collection<EntityPrint> getContent() {
        logger.log(Level.INFO, "getContent");
        for (EntityPrint print : content) {
            logger.log(Level.INFO, print.getRelease().getBook().getTitle());
        }
        return content;
    }

    @Override
    public void addPrint(EntityPrint print) {
        content.add(print);
    }

    @Override
    public void removePrint(EntityPrint print) {
        content.remove(print);
    }

    @Override
    public void clean() {
        content.clear();
    }

    @Override
    public boolean isIn(EntityPrint print) {
        return content.contains(print);
    }

    @Override  
    public void borrow() {
        // Ziska se uzivatel, kteremu se pujcuje.
        Principal principal = sessionContext.getCallerPrincipal();
        EntityUser user = beanSessionUser.getUserByEmail(principal.getName());

        // Pujci se jednotlive vytisky.
        for (EntityPrint print : content) {
            print = em.find(EntityPrint.class, print.getId());
            EntityBorrow borrow = new EntityBorrow();
            borrow.setPrint(print);
            borrow.setUser(user);
            borrow.setReservationDate(new Date());
            //Calendar calendar = Calendar.getInstance();
            //calendar.add(Calendar.MONTH, 1);
            //borrow.setLimitDate(calendar.getTime());
            borrow.setStatus(EntityBorrow.EnumBorrowStatus.RESERVED);

            user.getBorrowCollection().add(borrow);
            print.getBorrowsCollection().add(borrow);

            em.persist(borrow);
            em.persist(user);
            em.persist(print);
            em.flush();
        }
        clean();
    }
}
