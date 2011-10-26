package bean.managed.requestScope;

import entity.EnumReadState;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

@ManagedBean(name="readState")
@RequestScoped
public class ReadStateManagedBean {
    public SelectItem[] getGenderValues() {
        SelectItem[] items = new SelectItem[EnumReadState.values().length];
        int i = 0;
        for(EnumReadState readState: EnumReadState.values()) {
          items[i++] = new SelectItem(readState, readState.name());
        }
        return items;
    }
}
