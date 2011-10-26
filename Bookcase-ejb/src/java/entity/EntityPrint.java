/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity predstavujici jeden fyzicky svazek.
 * @author Tomáš Čerevka
 */
@Entity
@Table(name = "entityPrint", catalog = "bookcase", schema = "")
@NamedQueries({
    @NamedQuery(name = EntityPrint.FIND_ALL, query = "SELECT p FROM EntityPrint p"),
    @NamedQuery(name = EntityPrint.FIND_BY_ID, query = "SELECT p FROM EntityPrint p WHERE p.id = :p"),
    @NamedQuery(name = EntityPrint.FIND_BY_USER, query = "SELECT p FROM EntityPrint p WHERE p.user = :user")
})
public class EntityPrint implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "EntityPrint.findAll";

    public static final String FIND_BY_ID = "EntityPrint.findById";

    public static final String FIND_BY_USER = "EntityPrint.findByUser";

    public static enum EnumOwnershipType {

        PHYSICAL, EBOOK, ABSTRACT
    }

    public static enum EnumReadStatus {

        UNREAD, READING, READ, TO_READ, UNFINISHED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic(optional = false)
    @Column(name = "ownershipType", nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumOwnershipType ownershipType = EnumOwnershipType.ABSTRACT;

    @Basic(optional = false)
    @Column(name = "readStatus", nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumReadStatus readStatus;

    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
    @ManyToOne
    private EntityUser user;

    @JoinColumn(name = "releaseId", referencedColumnName = "id", nullable = false)
    @ManyToOne
    private EntityRelease release;

    @OneToMany(mappedBy = "print", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Collection<EntityBorrow> borrowsCollection = new ArrayList<EntityBorrow>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumOwnershipType getOwnershipType() {
        return ownershipType;
    }

    public void setOwnershipType(EnumOwnershipType ownershipType) {
        this.ownershipType = ownershipType;
    }

    public EnumReadStatus getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(EnumReadStatus readStatus) {
        this.readStatus = readStatus;
    }

    public EntityUser getUser() {
        return user;
    }

    public void setUser(EntityUser user) {
        this.user = user;
    }

    public Collection<EntityBorrow> getBorrowsCollection() {
        return borrowsCollection;
    }

    public void setBorrowsCollection(Collection<EntityBorrow> borrowsCollection) {
        this.borrowsCollection = borrowsCollection;
    }

    public EntityRelease getRelease() {
        return release;
    }

    public void setRelease(EntityRelease release) {
        this.release = release;
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
        if (!(object instanceof EntityPrint)) {
            return false;
        }
        EntityPrint other = (EntityPrint) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.EntityPrint[ id=" + id + " ]";
    }
}
