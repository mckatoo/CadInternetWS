/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ikatoo.models;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Milton Carlos Katoo
 */
@Entity
@Table(name = "requisicoes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Requisicoes.findAll", query = "SELECT r FROM Requisicoes r")
    , @NamedQuery(name = "Requisicoes.findById", query = "SELECT r FROM Requisicoes r WHERE r.id = :id")
    , @NamedQuery(name = "Requisicoes.findByNome", query = "SELECT r FROM Requisicoes r WHERE r.nome = :nome")
    , @NamedQuery(name = "Requisicoes.findByRarefunc", query = "SELECT r FROM Requisicoes r WHERE r.rarefunc = :rarefunc")
    , @NamedQuery(name = "Requisicoes.findByIp", query = "SELECT r FROM Requisicoes r WHERE r.ip = :ip")
    , @NamedQuery(name = "Requisicoes.findByMac", query = "SELECT r FROM Requisicoes r WHERE r.mac = :mac")
    , @NamedQuery(name = "Requisicoes.findByCreatedAt", query = "SELECT r FROM Requisicoes r WHERE r.createdAt = :createdAt")
    , @NamedQuery(name = "Requisicoes.findByUpdatedAt", query = "SELECT r FROM Requisicoes r WHERE r.updatedAt = :updatedAt")
    , @NamedQuery(name = "Requisicoes.findByDeletedAt", query = "SELECT r FROM Requisicoes r WHERE r.deletedAt = :deletedAt")})
public class Requisicoes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "nome")
    private String nome;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "rarefunc")
    private long rarefunc;
    
    @Size(max = 15)
    @Column(name = "ip")
    private String ip;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "MAC")
    private String mac;
    
    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;
    
    @JoinColumn(name = "campus_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Campus campusId;
    
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Status statusId;
    
    @JoinColumn(name = "usuarioTipo_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UsuarioTipo usuarioTipoid;

    public Requisicoes() {
    }

    public Requisicoes(Integer id) {
        this.id = id;
    }

    public Requisicoes(Integer id, String nome, long rarefunc, String mac) {
        this.id = id;
        this.nome = nome;
        this.rarefunc = rarefunc;
        this.mac = mac;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getRarefunc() {
        return rarefunc;
    }

    public void setRarefunc(long rarefunc) {
        this.rarefunc = rarefunc;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
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

    public Campus getCampusId() {
        return campusId;
    }

    public void setCampusId(Campus campusId) {
        this.campusId = campusId;
    }

    public Status getStatusId() {
        return statusId;
    }

    public void setStatusId(Status statusId) {
        this.statusId = statusId;
    }

    public UsuarioTipo getUsuarioTipoid() {
        return usuarioTipoid;
    }

    public void setUsuarioTipoid(UsuarioTipo usuarioTipoid) {
        this.usuarioTipoid = usuarioTipoid;
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
        if (!(object instanceof Requisicoes)) {
            return false;
        }
        Requisicoes other = (Requisicoes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ikatoo.models.Requisicoes[ id=" + id + " ]";
    }
    
}
