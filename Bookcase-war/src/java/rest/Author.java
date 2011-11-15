package rest;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Tomáš Čerevka
 */
@XmlRootElement
public class Author {
    
    public String name;
    
    public String surname;
    
    public Author() {
        
    }
    
    public Author(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
    
}
