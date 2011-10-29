package entity;

import java.io.Serializable;
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

/**
 * entita hodnocení knihy
 * @author Adam Činčura
 */
@Entity
@Table(name = "entityEvaluation", catalog = "bookcase", schema = "")
@NamedQueries({
@NamedQuery(name = EntityEvaluation.FIND_BY_BOOK, query = "SELECT e FROM EntityEvaluation e where e.bookId = :book"),
@NamedQuery(name = EntityEvaluation.FIND_BY_BOOK_AND_USER, query = "SELECT e FROM EntityEvaluation e where e.bookId = :book AND e.userId = :user")   
})
public class EntityEvaluation implements Serializable {    
    
    private static final long serialVersionUID = 1L;
    public static final String FIND_BY_BOOK = "EntityEvaluation.findByBook";
    public static final String FIND_BY_BOOK_AND_USER = "EntityEvaluation.findByBookAndUser";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "rate", nullable = false)
    private int rate;

    @JoinColumn(name = "bookId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private EntityBook bookId;

       
    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private EntityUser userId;
    
    
     
    public EntityEvaluation(){
        
    }
    public EntityBook getBookId() {
        return bookId;
    }

    public void setBookId(EntityBook bookId) {
        this.bookId = bookId;
    }
    
    public int getRate() {
        return rate;
    }

   
    public EntityUser getUserId() {
        return userId;
    }

    public void setUserId(EntityUser userId) {
        this.userId = userId;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof EntityEvaluation)) {
            return false;
        }
        EntityEvaluation other = (EntityEvaluation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.EntityEvaluation[ id=" + id + " ]";
    }
    
}
