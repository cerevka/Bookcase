/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.statefull;

import entity.EntityCopy;
import java.util.Collection;
import javax.ejb.Stateful;

/**
 *
 * @author Tomáš Čerevka
 */
@Stateful
public class BeanSessionBasket implements LocalBeanSessionBasket {

    private Collection<EntityCopy> content;

    @Override
    public Collection<EntityCopy> getContent() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addCopy(EntityCopy copy) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeCopy(EntityCopy copy) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void clean() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isIn(EntityCopy entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
}
