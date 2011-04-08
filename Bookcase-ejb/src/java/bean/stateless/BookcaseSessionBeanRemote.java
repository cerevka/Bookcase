package bean.stateless;

import entity.Book;
import entity.Copy;
import entity.Person;
import entity.Shelf;
import java.util.List;
import javax.ejb.Remote;

/**
 * Beana zajistujici praci s rezervacemi a vypujckami.
 * @author Tomáš Čerevka
 */
@Remote
public interface BookcaseSessionBeanRemote {

        /**
     * Vrati svazky, ktere ma pujcene uzivatel.
     * @param person Uzivatel.
     * @return Pujcene svazky.
     */
    public List<Copy> getBorrowedCopiesByPerson(Person person);

    /**
     * Pujci svazek osobe.
     * @param copy Svazek.
     * @param person Osoba.
     */
    public void borrowCopy(Book copy, Person person);

    /**
     * Vrati svazek.
     * @param copy Svazek.
     */
    public void returnCopy(Copy copy);


    /**
     * Vrati svazky rezervovane uzivatelem.
     * @param person Uzivatel.
     * @return Seznam svazku.
     */
    public List<Copy> getReservedCopiesByPerson(Person person);

    /**
     * Vrati svazky vlastnene uzivatelem.
     * @param person Uzivatel.
     * @return Vlastnene svazky.
     */
    public List<Copy> getOwnedCopiesByPerson(Person person);

    /**
     * Vrati svazky v dane policce.
     * @param shelf Policka.
     * @return Seznam svazku v policce.
     */
    public List<Copy> getCopiesInShelf(Shelf shelf);

    /**
     * Umisti svazek do policky.
     * @param copy Svazek.
     * @param shelf Policka.
     */
    public void insertCopyInShelf(Copy copy, Shelf shelf);

    /**
     * Odstrani svazek z policky.
     * @param copy Svazek.
     * @param shelf Policka.
     */
    public void removeCopyFromShlef(Copy copy, Shelf shelf);

    /**
     * Rozhodne o pritomnosti svazku v policce.
     * @param copy Svazek.
     * @param shelf Policka.
     * @return Potvrzeni/zamitnuti pritomnosti v policce.
     */
    public boolean isCopyInShelf(Copy copy, Shelf shelf);
    
}
