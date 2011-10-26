package bean.stateless;

import entity.EntityAuthor;
import entity.EntityBook;
import entity.EntityCopy;
import entity.EntityOwnership;
import entity.EntityShelf;
import entity.EntityUser;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

        // Vytvori se novy svazek od knihy.
        EntityCopy copy = new EntityCopy();
        copy.setBookId(book);
        book.getCopyCollection().add(copy);
        em.persist(book);
        em.persist(copy);

        // Ziska se uzivatel.
        Principal principal = sessionContext.getCallerPrincipal();
        EntityUser user = beanSessionUser.getUserByEmail(principal.getName());

        // Nastavi se vlastnictvi svazku.
        EntityOwnership ownership = new EntityOwnership();
        ownership.setUser(user);
        ownership.setCopy(copy);
        ownership.setOwnership(true);
        ownership.setReadState(EntityOwnership.EnumReadState.UNREAD);
        user.getOwnershipCollection().add(ownership);
        copy.getOwnershipCollection().add(ownership);

        em.persist(ownership);
        em.persist(copy);
        //em.persist(shelf);  
        em.flush();
    }

    @Override
    public List<EntityBook> getAllBooks() {
        Query query = em.createNamedQuery(EntityBook.FIND_ALL);
        return query.getResultList();
    }

    @Override
    public Collection<EntityCopy> getCopiesOwnedByUser(EntityUser user) {
        TypedQuery<EntityCopy> query = (TypedQuery<EntityCopy>) em.createNamedQuery(EntityCopy.FIND_BY_OWNER);
        query.setParameter("user", user);
        return (Collection<EntityCopy>) query.getResultList();
    }

    @Override
    public List<EntityCopy> getCopiesInSelf(String shelfName) {
        // Ziska se uzivatel.
        Principal principal = sessionContext.getCallerPrincipal();
        EntityUser user = beanSessionUser.getUserByEmail(principal.getName());

        Query query = em.createNamedQuery(EntityShelf.FIND_BY_USER_AND_NAME);
        query.setParameter("user", user);
        query.setParameter("name", shelfName);
        EntityShelf shelf = (EntityShelf) query.getSingleResult();

        return (List<EntityCopy>) shelf.getCopyCollection();
    }

    @Override
    public List<EntityCopy> getAllCopies() {
        TypedQuery<EntityCopy> query = (TypedQuery<EntityCopy>) em.createNamedQuery(EntityCopy.FIND_ALL);
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
    public Boolean isOwner(EntityUser user, EntityCopy copy) {
        // Najde se vztah mezi uzivatelem a svazkem.
        TypedQuery<EntityOwnership> query = (TypedQuery<EntityOwnership>) em.createNamedQuery(EntityOwnership.FIND_BY_USER_AND_COPY);
        query.setParameter("user", user);
        query.setParameter("copy", copy);
        try {
            EntityOwnership ownership = (EntityOwnership) query.getSingleResult();
        } catch (NoResultException exception) {
            // Pokud neexistuje mezi uzivatelem a svazkem vlastnictvi, tak ji nevlastni.
            return false;
        }
        // Existuje-li vlastnictvi, pak ji vlastni.
        return true;
    }

    @Override
    public void setBookCopyToUserOwnership(EntityBook book, EntityUser user) {
        EntityCopy copy = new EntityCopy(book);
        EntityOwnership ownership = new EntityOwnership(copy, user, true);
        em.persist(copy);
        em.persist(ownership);
        em.flush();
    }
}
