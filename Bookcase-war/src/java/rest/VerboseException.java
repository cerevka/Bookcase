package rest;

import com.sun.jersey.api.client.ClientResponse.Status;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Vyjímka obsahující popis své příčiny
 * @author Adam Činčura
 */
public class VerboseException extends WebApplicationException{   
    
    public VerboseException(String message, Status s) {
        super(Response.status(s).entity(message).type("text/plain").build());
     }
        
}
