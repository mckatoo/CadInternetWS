/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ikatoo.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Milton Carlos Katoo
 */
@Entity
@Table(name = "tipoUsers")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoUsers.findAll", query = "SELECT t FROM TipoUsers t")
    , @NamedQuery(name = "TipoUsers.findById", query = "SELECT t FROM TipoUsers t WHERE t.id = :id")
    , @NamedQuery(name = "TipoUsers.findByTipo", query = "SELECT t FROM TipoUsers t WHERE t.tipo = :tipo")
    , @NamedQuery(name = "TipoUsers.findByCreatedAt", query = "SELECT t FROM TipoUsers t WHERE t.createdAt = :createdAt")
    , @NamedQuery(name = "TipoUsers.findByUpdatedAt", query = "SELECT t FROM TipoUsers t WHERE t.updatedAt = :updatedAt")
    , @NamedQuery(name = "TipoUsers.findByDeletedAt", query = "SELECT t FROM TipoUsers t WHERE t.deletedAt = :deletedAt")})
public class TipoUsers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "tipo")
    private String tipo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoUsersid")
    private Collection<Users> usersCollection;

    public TipoUsers() {
    }

    public TipoUsers(Integer id) {
        this.id = id;
    }

    public TipoUsers(Integer id, String tipo, Date createdAt, Date updatedAt) {
        this.id = id;
        this.tipo = tipo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    @XmlTransient
    public Collection<Users> getUsersCollection() {
        return usersCollection;
    }

    public void setUsersCollection(Collection<Users> usersCollection) {
        this.usersCollection = usersCollection;
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
        if (!(object instanceof TipoUsers)) {
            return false;
        }
        TipoUsers other = (TipoUsers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ikatoo.models.TipoUsers[ id=" + id + " ]";
    }
    
}
