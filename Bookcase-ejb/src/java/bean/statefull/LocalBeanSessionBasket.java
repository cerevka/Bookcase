/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.statefull;

import entity.EntityPrint;
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
     public Collection<EntityPrint> getContent();

     /**
      * Prida svazek do kosiku.
      * @param print Pridavany svazek.
      */
     public void addPrint(EntityPrint print);

     /**
      * Odstrani svazek z kosiku.
      * @param print Odstranovany svazek.
      */
     public void removePrint(EntityPrint print);

     /**
      * Vyprazdni kosik.
      */
     public void clean();

     /**
      * Overi pritomnost svazku v kosiku.
      * @param print Overovany svazek.
      * @return
      */
     public boolean isIn(EntityPrint print);

     /**
      * Vsechny knihy v kosiku pujci uzivateli.
      */
    public void borrow();
    
}
