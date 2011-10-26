package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Dekomponovana entita urcujici vztah mezi uzivatelem a svazkem.
 * @author Tomáš Čerevka
 */
@Entity
@Table(name = "entityOwnership", catalog = "bookcase", schema = "")
@NamedQueries({
    @NamedQuery(name = EntityOwnership.FIND_ALL, query = "SELECT o FROM EntityOwnership o"),
    @NamedQuery(name = EntityOwnership.FIND_BY_ID, query = "SELECT o FROM EntityOwnership o WHERE o.id = :id"),
    @NamedQuery(name = EntityOwnership.FIND_BY_USER, query = "SELECT o FROM EntityOwnership o WHERE o.user = :user"),
    @NamedQuery(name = EntityOwnership.FIND_BY_COPY, query = "SELECT o FROM EntityOwnership o WHERE o.copy = :copy"),
    @NamedQuery(name = EntityOwnership.FIND_BY_USER_AND_COPY, query = "SELECT o FROM EntityOwnership o WHERE o.user = :user AND o.copy = :copy")
})
public class EntityOwnership implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "EntityOwnership.findAll";

    public static final String FIND_BY_ID = "EntityOwnership.findById";

    public static final String FIND_BY_USER = "EntityOwnership.findByUser";

    public static final String FIND_BY_COPY = "EntityOwnership.findByCopy";

    public static final String FIND_BY_USER_AND_COPY = "EntityOwnership.findByUserAndCopy";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "ownership")
    private Boolean ownership;

    @Column(name = "readState")
    @Enumerated(EnumType.STRING)
    private EnumReadState readState;

    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private EntityUser user;

    @JoinColumn(name = "copy", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private EntityCopy copy;

    public EntityOwnership() {
    }

    public EntityOwnership(EntityCopy copy, EntityUser user, boolean ownership) {
        this.copy = copy;
        this.user = user;
        this.ownership = ownership;
        this.readState = EnumReadState.UNREAD;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getOwnership() {
        return ownership;
    }

    public void setOwnership(Boolean ownership) {
        this.ownership = ownership;
    }

    public EnumReadState getReadState() {
        return readState;
    }

    public void setReadState(EnumReadState readState) {
        this.readState = readState;
    }

    public EntityCopy getCopy() {
        return copy;
    }

    public void setCopy(EntityCopy copy) {
        this.copy = copy;
    }

    public EntityUser getUser() {
        return user;
    }

    public void setUser(EntityUser user) {
        this.user = user;
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
        if (!(object instanceof EntityOwnership)) {
            return false;
        }
        EntityOwnership other = (EntityOwnership) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.EntityOwnership[ id=" + id + " ]";
    }
}
