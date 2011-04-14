package bean.stateless;

import entity.EntityCopy;
import entity.EntityUser;
import entity.EntityShelf;
import java.util.List;
import javax.ejb.Local;

/**
 * Beana zajistujici praci s rezervacemi a vypujckami.
 * @author Tomáš Čerevka
 */
@Local
public interface LocalBeanSessionBookcase {

        /**
     * Vrati svazky, ktere ma pujcene uzivatel.
     * @param user Uzivatel.
     * @return Pujcene svazky.
     */
    public List<EntityCopy> getBorrowedCopiesByUser(EntityUser user);

    /**
     * Pujci svazek osobe.
     * @param copy Svazek.
     * @param user Osoba.
     */
    public void borrowCopy(EntityCopy copy, EntityUser user);

    /**
     * Vrati svazek.
     * @param copy Svazek.
     */
    public void returnCopy(EntityCopy copy);


    /**
     * Vrati svazky rezervovane uzivatelem.
     * @param user Uzivatel.
     * @return Seznam svazku.
     */
    public List<EntityCopy> getReservedCopiesByUser(EntityUser user);

    /**
     * Vrati svazky vlastnene uzivatelem.
     * @param person Uzivatel.
     * @return Vlastnene svazky.
     */
    public List<EntityCopy> getOwnedCopiesByUser(EntityUser user);

    /**
     * Vrati svazky v dane policce.
     * @param shelf Policka.
     * @return Seznam svazku v policce.
     */
    public List<EntityCopy> getCopiesInShelf(EntityShelf shelf);

    /**
     * Umisti svazek do policky.
     * @param copy Svazek.
     * @param shelf Policka.
     */
    public void insertCopyInShelf(EntityCopy copy, EntityShelf shelf);

    /**
     * Odstrani svazek z policky.
     * @param copy Svazek.
     * @param shelf Policka.
     */
    public void removeCopyFromShlef(EntityCopy copy, EntityShelf shelf);

    /**
     * Rozhodne o pritomnosti svazku v policce.
     * @param copy Svazek.
     * @param shelf Policka.
     * @return Potvrzeni/zamitnuti pritomnosti v policce.
     */
    public boolean isCopyInShelf(EntityCopy copy, EntityShelf shelf);
    
}
