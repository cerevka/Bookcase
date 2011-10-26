package bean.stateless;

import entity.EntityAuthor;
import entity.EntityBook;
import entity.EntityCopy;
import entity.EntityUser;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

/**
 * Beana zajistujici praci s knihami.
 * @author Tomáš Čerevka
 */
@Local
public interface LocalBeanSessionBook {


    /**
     * Vrati knihu.
     * @param bookId Identifikator knihy.
     * @return Kniha.
     */
    public EntityBook getBook(int bookId);

    /**
     * Vrati vsechny knihy.
     * @return Seznam knih.
     */
    public List<EntityBook> getAllBooks();

    /**
     * Vrati vsechny svazky.
     * @return Seznam svazku.
     */
    public List<EntityCopy> getAllCopies();

    /**
     * Vrati autora.
     * @param authorId Identifikator autora.
     * @return Autor.
     */
    public EntityAuthor getAuthor(int authorId);

    /**
     * Vrati vsechny autory.
     * @return Seznam autoru.
     */
    public List<EntityAuthor> getAllAuthors();

    /**
     * Prida novou knihu do databaze.
     * @param book Pridavana kniha.
     * @param author Autor knihy.
     */
    public void addBook(EntityBook book, EntityAuthor author);

    /**
     * Vrati vsechny svazky z dane policky pro prihlaseneho uzivatele..
     */
    public List<entity.EntityCopy> getCopiesInSelf(String shelfName);

    /**
     * Rozhodne, zda uzivatel vlastni danou knihu.
     * @return TRUE pokud vlastni, FALSE pokud nevlastni.
     */
    public Boolean isOwner(EntityUser user, EntityCopy copy);

    /**
     * Vrati kolekci svazku, ktere vlastni uzivatel.
     * @param user Uzivatel, jemuz svazky maji patrit.
     * @return Kolekce knih vlastnenych uzivatelem.
     */
    public Collection<EntityCopy> getCopiesOwnedByUser(EntityUser user);
    
    public void setBookCopyToUserOwnership(EntityBook book, EntityUser user);
    
}
