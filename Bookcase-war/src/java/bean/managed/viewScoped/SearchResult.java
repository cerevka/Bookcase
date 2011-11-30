package bean.managed.viewScoped;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SearchResult {
    
    private String tag;
    @XmlElement(name="element_name")
    private String elementName;
    private String result;

    public SearchResult(String tag, String elementName, String result) {
        this.tag = tag;
        this.elementName = elementName;
        this.result = result;
    }

    public SearchResult() {}
    
    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
    
    
    
}
