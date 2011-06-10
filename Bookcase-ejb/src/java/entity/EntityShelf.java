package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Entita policky.
 * @author Adam Činčura
 * @author Tomáš Čerevka
 */
@Entity
@Table(name = "entityShelf", catalog = "bookcase", schema = "")
@NamedQueries({
    @NamedQuery(name = EntityShelf.FIND_ALL, query = "SELECT s FROM EntityShelf s"),
    @NamedQuery(name = EntityShelf.FIND_BY_ID, query = "SELECT s FROM EntityShelf s WHERE s.id = :id"),
    @NamedQuery(name = EntityShelf.FIND_BY_NAME, query = "SELECT s FROM EntityShelf s WHERE s.name = :name"),
    @NamedQuery(name = EntityShelf.FIND_BY_USER_AND_NAME, query= "SELECT s FROM EntityShelf s WHERE s.userId = :user AND s.name = :name")       
})
public class EntityShelf implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public static final String FIND_ALL = "EntityShelf.findAll";
    
    public static final String FIND_BY_ID = "EntityShelf.findById";
    
    public static final String FIND_BY_NAME = "EntityShelf.findByName";
    
    public static final String FIND_BY_USER_AND_NAME = "EntityShelf.findByUserAndName";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 255)
    private String name;

    @ManyToMany(mappedBy = "shelfCollection")
    private Collection<EntityCopy> copyCollection;

    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private EntityUser userId;

    public EntityShelf() {
    }

    public EntityShelf(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<EntityCopy> getCopyCollection() {
        return copyCollection;
    }

    public void setCopyCollection(Collection<EntityCopy> copyCollection) {
        this.copyCollection = copyCollection;
    }

    public EntityUser getUserId() {
        return userId;
    }

    public void setUserId(EntityUser userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntityShelf)) {
            return false;
        }
        EntityShelf other = (EntityShelf) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.EntityShelf[id=" + id + "]";
    }
}
