package bean.statefull;

import bean.stateless.LocalBeanSessionUser;
import entity.EntityBorrow;
import entity.EntityCopy;
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
import javax.ejb.Remove;
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

    private Collection<EntityCopy> content = content = new ArrayList<EntityCopy>();

    @Override
    public Collection<EntityCopy> getContent() {
        logger.log(Level.INFO, "getContent");
        for (EntityCopy copy : content) {
            logger.log(Level.INFO, copy.getBookId().getTitle());
        }
        return content;
    }

    @Override
    public void addCopy(EntityCopy copy) {
        content.add(copy);        
    }

    @Override
    public void removeCopy(EntityCopy copy) {
        content.remove(copy);
    }

    @Override
    public void clean() {
        content.clear();
    }

    @Override
    public boolean isIn(EntityCopy entity) {
        return content.contains(entity);
    }
    
    @Override
    //@Remove
    public void borrow() {
        // Ziska se uzivatel, kteremu se pujcuje.
        Principal principal = sessionContext.getCallerPrincipal();
        EntityUser user = beanSessionUser.getUserByEmail(principal.getName());
        
        // Pujci se jednotlive knizky.
        for (EntityCopy copy : content) {
            copy = em.find(EntityCopy.class, copy.getId());
            EntityBorrow borrow = new EntityBorrow();
            borrow.setCopyId(copy);
            borrow.setUserId(user);
            borrow.setFromDate(new Date());
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 1);
            borrow.setLimitDate(calendar.getTime());
            
            Collection<EntityBorrow> borrowsOfUser = user.getBorrowCollection();
            if (borrowsOfUser == null) {
                borrowsOfUser = new ArrayList<EntityBorrow>();
            }
            borrowsOfUser.add(borrow);
            
            Collection<EntityBorrow> borrowsOfCopy = copy.getBorrowCollection();
            if (borrowsOfCopy == null) {
                borrowsOfCopy = new ArrayList<EntityBorrow>();
            }
            borrowsOfCopy.add(borrow);
            
            em.persist(borrow);
            em.persist(user);
            em.persist(copy);
        }
        clean();        
    }
}
