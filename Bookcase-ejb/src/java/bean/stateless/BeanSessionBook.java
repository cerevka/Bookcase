package bean.stateless;

import entity.EntityAuthor;
import entity.EntityBook;
import entity.EntityEvaluation;
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
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.WebApplicationException;

/**
 * Beana obstaravajici logiku pro manipulaci s knihami.
 * @author Tomáš Čerevka
 * @author Adam Činčura
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

    @Override
    public List<EntityBook> getBooksByTittle(String title) {
        Query query = em.createNamedQuery(EntityBook.FIND_BY_TITLE);
        query.setParameter("title", title);
        return query.getResultList();
    }
    
    @Override
    public EntityRelease getReleaseByISBN(String isbn)  {
        TypedQuery<EntityRelease> query = (TypedQuery<EntityRelease>) em.createNamedQuery(EntityRelease.FIND_BY_ISBN);
        query.setParameter("isbn", isbn);
        try{
        EntityRelease release = query.getSingleResult();
        } catch (NoResultException exception) {
            throw new WebApplicationException(404);
        }
        return query.getSingleResult();
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

    @Override
    public void setPrintReadState(EntityPrint.EnumReadStatus readState, EntityPrint print) {
        print = em.merge(print);
        em.refresh(print);
        print.setReadStatus(readState);
        em.persist(print);
        em.flush();
    }

    @Override
    public EntityEvaluation getEaluationByBookAndUser(EntityBook book, EntityUser user) {
      
        Query query = em.createNamedQuery(EntityEvaluation.FIND_BY_BOOK_AND_USER);
        query.setParameter("book", book);
        query.setParameter("user", user);
        List<EntityEvaluation> l= query.getResultList();
        if(l.isEmpty()){
           return null;
        }
        else{
            return l.get(0);
        }
    }
 }
