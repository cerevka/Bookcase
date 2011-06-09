package exception;

import javax.ejb.ApplicationException;

/**
 * Vyjimka vyvolavana v pripade, ze pozadovany uzivatel neexistuje.
 * @author Tomáš Čerevka
 */
@ApplicationException(rollback = true)
public class ExceptionUserDoesNotExist extends Exception {

    public ExceptionUserDoesNotExist() {
    }

    public ExceptionUserDoesNotExist(String message) {
        super(message);
    }
}
