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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Entita skupiny.
 * @author Adam Činčura
 * @author Tomáš Čerevka
 */
@Entity
@Table(name = "entityGroup", catalog = "bookcase", schema = "")
@NamedQueries({
    @NamedQuery(name = EntityGroup.FIND_ALL, query = "SELECT r FROM EntityGroup r"),
    @NamedQuery(name = EntityGroup.FIND_BY_ID, query = "SELECT r FROM EntityGroup r WHERE r.id = :id"),
    @NamedQuery(name = EntityGroup.FIND_BY_NAME, query = "SELECT r FROM EntityGroup r WHERE r.name = :name")
})
public class EntityGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public static final String FIND_ALL = "EntityGroup.findAll";
    
    public static final String FIND_BY_ID = "EntityGroup.findById";
    
    public static final String FIND_BY_NAME = "EntityGroup.findByName";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 255)
    private String name;

    @JoinTable(name = "userInGroup", joinColumns = {
        @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "groupId", referencedColumnName = "id", nullable = false)})
    @ManyToMany
    private Collection<EntityUser> userCollection;

    public EntityGroup() {
    }

    public EntityGroup(Integer id) {
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

    public Collection<EntityUser> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<EntityUser> userCollection) {
        this.userCollection = userCollection;
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
        if (!(object instanceof EntityGroup)) {
            return false;
        }
        EntityGroup other = (EntityGroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.EntityGroup[id=" + id + "]";
    }
}
