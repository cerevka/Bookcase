package bean.stateless;

import entity.EntityAuthor;
import entity.EntityBook;
import entity.EntityCopy;
import entity.EntityEvaluation;
import entity.EntityOwnership;
import entity.EntityShelf;
import entity.EntityUser;
import entity.EnumReadState;
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
 *
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

        /*
         * Policky jiz nejsou treba, byl zmenen pristup k vlastnisvi knihy pres EntityOwnership.
         * 
        Query query = em.createNamedQuery(EntityShelf.FIND_BY_USER_AND_NAME);
        query.setParameter("user", user);
        query.setParameter("name", "default");
        EntityShelf shelf = (EntityShelf) query.getSingleResult();
        
        
        // Vlozi se svazek do policky.        
        copy.getShelfCollection().add(shelf); 
        shelf.getCopyCollection().add(copy);
         */

        // Nastavi se vlastnictvi svazku.
        EntityOwnership ownership = new EntityOwnership();
        ownership.setUser(user);
        ownership.setCopy(copy);
        ownership.setOwnership(true);
        ownership.setReadState(EnumReadState.UNREAD);
        user.getOwnershipCollection().add(ownership);
        copy.getOwnershipCollection().add(ownership);

        em.persist(ownership);
        em.persist(copy);
        //em.persist(shelf);  
        em.flush();
    }

    @Override
    public EntityCopy getCopy(int copyId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<EntityCopy> getCopies(EntityBook book) {
        throw new UnsupportedOperationException("Not supported yet.");
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
    public List<EntityBook> getAllBooksFromAuthor(EntityAuthor author) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<EntityCopy> getAllCopiesFromAuthor(EntityAuthor author) {
        throw new UnsupportedOperationException("Not supported yet.");
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

    @Override
    public List<EntityBook> getBooksByTittle(String title) {
        Query query = em.createNamedQuery(EntityBook.FIND_BY_TITLE);
        query.setParameter("title", title);
        return query.getResultList();        
    }

    @Override
    public void evaluateBook(EntityBook book, EntityUser user, int value) {
        Query query = em.createNamedQuery(EntityUser.FIND_BY_ID);
        query.setParameter("id", user.getId());
        EntityUser u = (EntityUser) query.getSingleResult();
        
        Query query1 = em.createNamedQuery(EntityBook.FIND_BY_ID);
        query1.setParameter("id", book.getId());
        EntityBook b = (EntityBook) query1.getSingleResult();
        
        EntityEvaluation e = new EntityEvaluation();
      
        e.setBookId(b);
        e.setRate(value);
        e.setUserId(u);
        
        u.getEvalluationCollection().add(e);
        b.getEvaluationCollection().add(e);
        
        em.persist(e);
        em.persist(u);
        em.persist(b);
        em.flush();        
    }

    @Override
    public List<EntityEvaluation> getEvaluationsByBook(EntityBook book) {
       Query query = em.createNamedQuery(EntityEvaluation.FIND_BY_BOOK);
        query.setParameter("book", book);
        return query.getResultList();
    }
}
