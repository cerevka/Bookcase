package bean.stateless;

import entity.Author;
import entity.Book;
import entity.Copy;
import java.util.List;
import javax.ejb.Remote;

/**
 * Beana zajistujici praci s knihami.
 * @author Tomáš Čerevka
 */
@Remote
public interface BookSessionBeanRemote {


    /**
     * Vrati knihu.
     * @param bookId Identifikator knihy.
     * @return Kniha.
     */
    public Book getBook(int bookId);

    /**
     * Vrati svazek.
     * @param copyId Identifikator svazku.
     * @return Svazek.
     */
    public Copy getCopy(int copyId);


    /**
     * Vrati svazky knihy.
     * @param book Kniha.
     * @return Seznam svazku.
     */
    public List<Copy> getCopies(Book book);

    /**
     * Vrati vsechny knihy.
     * @return Seznam knih.
     */
    public List<Book> getAllBooks();

    /**
     * Vrati vsechny svazky.
     * @return Seznam svazku.
     */
    public List<Copy> getAllCopies();

    /**
     * Vrati vsechny knihy od daneho autora.
     * @param author Autor.
     * @return Seznam knih od daneho autora.
     */
    public List<Book> getAllBooksFromAuthor(Author author);

    /**
     * Vrati vsechny svazky od daneho autora.
     * @param author Autor.
     * @return Seznam svazku od daneho autora.
     */
    public List<Copy> getAllCopiesFromAuthor(Author author);

    /**
     * Vrati autora.
     * @param authorId Identifikator autora.
     * @return Autor.
     */
    public Author getAuthor(int authorId);

    /**
     * Vrati vsechny autory.
     * @return Seznam autoru.
     */
    public List<Author> getAllAuthors();
    
}
