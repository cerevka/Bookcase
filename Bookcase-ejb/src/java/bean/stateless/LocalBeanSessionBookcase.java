package bean.stateless;

import javax.ejb.Local;

/**
 * Beana zajistujici praci s rezervacemi a vypujckami.
 * @author Tomáš Čerevka
 */
@Local
public interface LocalBeanSessionBookcase {

    /**
     * Inicializuje databazi.
     */
    public void initDatabase();
}
