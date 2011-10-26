package bean.stateless;

import entity.EntityAuthor;
import entity.EntityBook;
import entity.EntityPrint;
import entity.EntityUser;
import entity.EnumReadState;
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
     * Rozhodne, zda uzivatel vlastni danou knihu.
     * @return TRUE pokud vlastni, FALSE pokud nevlastni.
     */
    public Boolean isOwner(EntityUser user, EntityPrint print);    

    /**
     * Vrati kolekci svazku, ktere vlastni uzivatel.
     * @param user Majitel svazku.
     * @return Kolekce svazku, ktere vlastni uzivatel.
     */

    public Collection<EntityCopy> getCopiesOwnedByUser(EntityUser user);
    
    public void setBookCopyToUserOwnership(EntityBook book, EntityUser user);
    
    public void setReadStateToBookCopy(EnumReadState readState, EntityCopy copy, EntityUser user);

    public Collection<entity.EntityPrint> getPrintsOwnedByUser(EntityUser user);

    /**
     * Vrati vsechny vytisky.
     * @return Vsechny vytisky.
     */
    public List<EntityPrint> getAllPrints();

    
}
