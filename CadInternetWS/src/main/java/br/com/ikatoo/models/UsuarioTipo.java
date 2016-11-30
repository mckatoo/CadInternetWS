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
@Table(name = "usuarioTipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioTipo.findAll", query = "SELECT u FROM UsuarioTipo u")
    , @NamedQuery(name = "UsuarioTipo.findById", query = "SELECT u FROM UsuarioTipo u WHERE u.id = :id")
    , @NamedQuery(name = "UsuarioTipo.findByTipo", query = "SELECT u FROM UsuarioTipo u WHERE u.tipo = :tipo")
    , @NamedQuery(name = "UsuarioTipo.findByCreatedAt", query = "SELECT u FROM UsuarioTipo u WHERE u.createdAt = :createdAt")
    , @NamedQuery(name = "UsuarioTipo.findByUpdatedAt", query = "SELECT u FROM UsuarioTipo u WHERE u.updatedAt = :updatedAt")
    , @NamedQuery(name = "UsuarioTipo.findByDeletedAt", query = "SELECT u FROM UsuarioTipo u WHERE u.deletedAt = :deletedAt")})
public class UsuarioTipo implements Serializable {

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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioTipoid")
    private Collection<Requisicoes> requisicoesCollection;

    public UsuarioTipo() {
    }

    public UsuarioTipo(Integer id) {
        this.id = id;
    }

    public UsuarioTipo(Integer id, String tipo, Date createdAt, Date updatedAt) {
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
    public Collection<Requisicoes> getRequisicoesCollection() {
        return requisicoesCollection;
    }

    public void setRequisicoesCollection(Collection<Requisicoes> requisicoesCollection) {
        this.requisicoesCollection = requisicoesCollection;
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
        if (!(object instanceof UsuarioTipo)) {
            return false;
        }
        UsuarioTipo other = (UsuarioTipo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ikatoo.models.UsuarioTipo[ id=" + id + " ]";
    }
    
}
