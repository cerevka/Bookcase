package bean.stateless;

import entity.EntityAuthor;
import entity.EntityBook;
import entity.EntityCopy;
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
     * Vrati svazek.
     * @param copyId Identifikator svazku.
     * @return Svazek.
     */
    public EntityCopy getCopy(int copyId);


    /**
     * Vrati svazky knihy.
     * @param book Kniha.
     * @return Seznam svazku.
     */
    public List<EntityCopy> getCopies(EntityBook book);

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
     * Vrati vsechny knihy od daneho autora.
     * @param author Autor.
     * @return Seznam knih od daneho autora.
     */
    public List<EntityBook> getAllBooksFromAuthor(EntityAuthor author);

    /**
     * Vrati vsechny svazky od daneho autora.
     * @param author Autor.
     * @return Seznam svazku od daneho autora.
     */
    public List<EntityCopy> getAllCopiesFromAuthor(EntityAuthor author);

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
    
}