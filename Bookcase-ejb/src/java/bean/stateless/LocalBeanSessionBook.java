package bean.stateless;

import entity.EntityAuthor;
import entity.EntityBook;
import entity.EntityEvaluation;
import entity.EntityPrint;
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
     * Vrati kolekci vytisku, ktere patri uzivateli.
     * @param user Kteremu uzivateli maji vytisky patrit.
     * @return Vytisky patrici uzivateli.
     */
    public Collection<EntityPrint> getPrintsOwnedByUser(EntityUser user);

      /**
     * Vrati kolekci knih odpovidajicho nazvu
     * @param String nazev
     * @return Kolekce knih odpovidajicich nazvem
     */
    public List<EntityBook> getBooksByTittle(String name);
    
    /**
     * Vrati vsechny vytisky.
     * @return Vsechny vytisky.
     */
    public List<EntityPrint> getAllPrints();

    
     /**
     * prida hodnoceni ke knize
     * @param book hodnocena kniha, user hodnotici u6ivatel, value vyse hodnoceni
     */
    public void evaluateBook(EntityBook book, EntityUser user, int value);
    
    
    /**
     * vrati vsechna hodnoceni knihy
     * 
     * @param book knihy jejiz hodnoceni chci
     * @return list hodnoceni
     */
    public List<EntityEvaluation> getEvaluationsByBook(EntityBook book);
 
    /**
     * Nastavi vytisku stav precteni.
     * @param readState Novy stav.
     * @param print Vytisk.
     */
    public void setPrintReadState(EntityPrint.EnumReadStatus readState, EntityPrint print);

  
    
}
