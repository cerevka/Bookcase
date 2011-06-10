/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.statefull;

import entity.EntityCopy;
import java.util.Collection;
import javax.ejb.Local;

/**
 *
 * @author Tomáš Čerevka
 */
@Local
public interface LocalBeanSessionBasket {

    /**
     * Vrati vsechny svazky v kosiku.
     * @return Seznam svazku v kosiku.
     */
     public Collection<EntityCopy> getContent();

     /**
      * Prida svazek do kosiku.
      * @param copy Pridavany svazek.
      */
     public void addCopy(EntityCopy copy);

     /**
      * Odstrani svazek z kosiku.
      * @param copy Odstranovany svazek.
      */
     public void removeCopy(EntityCopy copy);

     /**
      * Vyprazdni kosik.
      */
     public void clean();

     /**
      * Overi pritomnost svazku v kosiku.
      * @param entity Overovany svazek.
      * @return
      */
     public boolean isIn(EntityCopy entity);

     /**
      * Vsechny knihy v kosiku pujci uzivateli.
      */
    public void borrow();
    
}
