package exception;

import javax.ejb.ApplicationException;

/**
 * Vyjimka vyvolavana pri opakovane registraci (shoda e-mailu).
 * @author Tomáš Čerevka
 */
@ApplicationException(rollback = true)
public class ExceptionUserAlreadyExists extends Exception {

    public ExceptionUserAlreadyExists() {
    }

    public ExceptionUserAlreadyExists(String message) {
        super(message);
    }
}
