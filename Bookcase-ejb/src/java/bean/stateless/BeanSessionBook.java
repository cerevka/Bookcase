package bean.stateless;

import bean.statefull.LocalBeanSessionBasket;
import entity.EntityAuthor;
import entity.EntityBook;
import entity.EntityCopy;
import entity.EntityOwnership;
import entity.EntityShelf;
import entity.EntityUser;
import entity.EnumReadState;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
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
           author = getAuthor(author.getId());
        } else {
            em.persist(author);
            em.flush();
        }
        
        // Knize se priradi autor.
        book.setAuthorId(author);
        
        // Autorovi se priradi kniha.
        Collection<EntityBook> booksOfAutor = author.getBookCollection();
        if (booksOfAutor == null) {
            booksOfAutor = new ArrayList<EntityBook>();
        }
        booksOfAutor.add(book);
        
        em.persist(book);
        em.persist(author);   
        em.flush();
        
        // Vytvori se novy svazek od knihy.
        EntityCopy copy = new EntityCopy();
        copy.setBookId(book);
        
        Collection<EntityCopy> copiesOfBook = book.getCopyCollection();
        if (copiesOfBook == null) {
            copiesOfBook = new ArrayList<EntityCopy>();
        }
        copiesOfBook.add(copy);
        
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
        ownership.setReadState(EnumReadState.UNREAD);
        user.getOwnershipCollection().add(ownership);
        copy.getOwnershipCollection().add(ownership);
        
        em.persist(ownership);
        em.persist(copy);
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
    public void setBookCopyToUserOwnership(EntityBook book, EntityUser user) {
        EntityCopy copy = new EntityCopy(book);
        EntityOwnership ownership = new EntityOwnership(copy, user, true);
        em.persist(copy);
        em.persist(ownership);
        em.flush();
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
    public void setReadStateToBookCopy(EnumReadState readState, EntityCopy copy, EntityUser user) {
        em.setFlushMode(FlushModeType.AUTO);
        for (EntityOwnership ownership : copy.getOwnershipCollection()) {
            if (ownership.getUser().equals(user)) {
                ownership.setReadState(readState);
             }
        }
    }
    
}

