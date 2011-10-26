package bean.stateless;

import entity.EntityAuthor;
import entity.EntityBook;
import entity.EntityPrint;
import entity.EntityRelease;
import entity.EntityUser;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Beana obstaravajici logiku pro manipulaci s knihami.
 * @author Tomáš Čerevka
 */
@Stateless
public class BeanSessionBook implements LocalBeanSessionBook {

    @PersistenceContext
    private EntityManager em;

    @Resource
    private SessionContext sessionContext;

    @EJB
    private LocalBeanSessionUser beanSessionUser;

    @Override
    public EntityBook getBook(int bookId) {
        Query query = em.createNamedQuery(EntityBook.FIND_BY_ID);
        query.setParameter("id", bookId);
        return (EntityBook) query.getSingleResult();
    }

    @Override
    public void addBook(EntityBook book, EntityAuthor author) {
        // Autor se umisti do databaze.
        if (author.getId() != null) {
            author = em.merge(author);
            em.refresh(author);
        } else {
            em.persist(author);
        }

        // Propoji se autor s knihou.
        book.getAuthorCollection().add(author);
        author.getBookCollection().add(book);
        em.persist(book);
        em.persist(author);

        // Vytvori se nove vydani knihy.
        EntityRelease release = new EntityRelease();
        release.setBook(book);
        book.getReleasesCollection().add(release);
        em.persist(book);
        em.persist(release);

        // Ziska se uzivatel.
        Principal principal = sessionContext.getCallerPrincipal();
        EntityUser user = beanSessionUser.getUserByEmail(principal.getName());


        // Vytvori se novy vytisk knihy.
        EntityPrint print = new EntityPrint();
        print.setRelease(release);
        release.getPrintsCollection().add(print);
        print.setOwnershipType(EntityPrint.EnumOwnershipType.PHYSICAL);
        print.setReadStatus(EntityPrint.EnumReadStatus.UNREAD);
        print.setUser(user);
        user.getPrintsCollection().add(print);

        em.persist(print);
        em.persist(release);
        em.persist(user);

        em.flush();
    }

    @Override
    public List<EntityBook> getAllBooks() {
        Query query = em.createNamedQuery(EntityBook.FIND_ALL);
        return query.getResultList();
    }

    @Override
    public Collection<EntityPrint> getPrintsOwnedByUser(EntityUser user) {
        TypedQuery<EntityPrint> query = (TypedQuery<EntityPrint>) em.createNamedQuery(EntityPrint.FIND_BY_USER);
        query.setParameter("user", user);
        return (Collection<EntityPrint>) query.getResultList();
    }

    @Override
    public List<EntityPrint> getAllPrints() {
        TypedQuery<EntityPrint> query = (TypedQuery<EntityPrint>) em.createNamedQuery(EntityPrint.FIND_ALL);
        return query.getResultList();
    }

    @Override
    public EntityAuthor getAuthor(int authorId) {
        Query query = em.createNamedQuery(EntityAuthor.FIND_BY_ID);
        query.setParameter("id", authorId);
        return (EntityAuthor) query.getSingleResult();
    }

    @Override
    public List<EntityAuthor> getAllAuthors() {
        TypedQuery<EntityAuthor> query = (TypedQuery<EntityAuthor>) em.createNamedQuery(EntityAuthor.FIND_ALL);
        return query.getResultList();
    }

    @Override
    public Boolean isOwner(EntityUser user, EntityPrint print) {        
        if (user.equals(print.getUser())) {
            return true;
        }
        return false;
    }
}
