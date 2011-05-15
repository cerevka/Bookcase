package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entita výpůjčky.
 * @author Adam Činčura
 * @author Tomáš Čerevka
 */
@Entity
@Table(name = "entityBorrow", catalog = "bookcase", schema = "")
@NamedQueries({
    @NamedQuery(name = EntityBorrow.FIND_ALL, query = "SELECT b FROM EntityBorrow b"),
    @NamedQuery(name = EntityBorrow.FIND_BY_ID, query = "SELECT b FROM EntityBorrow b WHERE b.id = :id"),
    @NamedQuery(name = EntityBorrow.FIND_BY_LIMIT_DATE, query = "SELECT b FROM EntityBorrow b WHERE b.limitDate = :limitDate"),
    @NamedQuery(name = EntityBorrow.FIND_BY_TO_DATE, query = "SELECT b FROM EntityBorrow b WHERE b.toDate = :toDate"),
    @NamedQuery(name = EntityBorrow.FIND_BY_FROM_DATE, query = "SELECT b FROM EntityBorrow b WHERE b.fromDate = :fromDate")
})
public class EntityBorrow implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "EntityBorrow.findAll";

    public static final String FIND_BY_ID = "EntityBorrow.findById";

    public static final String FIND_BY_LIMIT_DATE = "EntityBorrow.findByLimitData";

    public static final String FIND_BY_TO_DATE = "EntityBorrow.findByToDate";

    public static final String FIND_BY_FROM_DATE = "EntityBorrow.findByFromDate";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "limitDate")
    @Temporal(TemporalType.DATE)
    private Date limitDate;

    @Column(name = "toDate")
    @Temporal(TemporalType.DATE)
    private Date toDate;

    @Basic(optional = false)
    @Column(name = "fromDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fromDate;

    @JoinColumn(name = "copyId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private EntityCopy copyId;

    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private EntityUser userId;

    public EntityBorrow() {
    }

    public EntityBorrow(Integer id) {
        this.id = id;
    }

    public EntityBorrow(Integer id, Date fromDate) {
        this.id = id;
        this.fromDate = fromDate;
    }

    public Date getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(Date limitDate) {
        this.limitDate = limitDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EntityCopy getCopyId() {
        return copyId;
    }

    public void setCopyId(EntityCopy copyId) {
        this.copyId = copyId;
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
        if (!(object instanceof EntityBorrow)) {
            return false;
        }
        EntityBorrow other = (EntityBorrow) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.EntityBorrow[id=" + id + "]";
    }
}
