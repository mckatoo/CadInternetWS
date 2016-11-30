/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ikatoo.controllers;

import br.com.ikatoo.controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.ikatoo.models.Campus;
import br.com.ikatoo.models.Requisicoes;
import br.com.ikatoo.models.Status;
import br.com.ikatoo.models.UsuarioTipo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Milton Carlos Katoo
 */
public class RequisicoesJpaController implements Serializable {

    public RequisicoesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Requisicoes requisicoes) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Campus campusId = requisicoes.getCampusId();
            if (campusId != null) {
                campusId = em.getReference(campusId.getClass(), campusId.getId());
                requisicoes.setCampusId(campusId);
            }
            Status statusId = requisicoes.getStatusId();
            if (statusId != null) {
                statusId = em.getReference(statusId.getClass(), statusId.getId());
                requisicoes.setStatusId(statusId);
            }
            UsuarioTipo usuarioTipoid = requisicoes.getUsuarioTipoid();
            if (usuarioTipoid != null) {
                usuarioTipoid = em.getReference(usuarioTipoid.getClass(), usuarioTipoid.getId());
                requisicoes.setUsuarioTipoid(usuarioTipoid);
            }
            em.persist(requisicoes);
            if (campusId != null) {
                campusId.getRequisicoesCollection().add(requisicoes);
                campusId = em.merge(campusId);
            }
            if (statusId != null) {
                statusId.getRequisicoesCollection().add(requisicoes);
                statusId = em.merge(statusId);
            }
            if (usuarioTipoid != null) {
                usuarioTipoid.getRequisicoesCollection().add(requisicoes);
                usuarioTipoid = em.merge(usuarioTipoid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Requisicoes requisicoes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Requisicoes persistentRequisicoes = em.find(Requisicoes.class, requisicoes.getId());
            Campus campusIdOld = persistentRequisicoes.getCampusId();
            Campus campusIdNew = requisicoes.getCampusId();
            Status statusIdOld = persistentRequisicoes.getStatusId();
            Status statusIdNew = requisicoes.getStatusId();
            UsuarioTipo usuarioTipoidOld = persistentRequisicoes.getUsuarioTipoid();
            UsuarioTipo usuarioTipoidNew = requisicoes.getUsuarioTipoid();
            if (campusIdNew != null) {
                campusIdNew = em.getReference(campusIdNew.getClass(), campusIdNew.getId());
                requisicoes.setCampusId(campusIdNew);
            }
            if (statusIdNew != null) {
                statusIdNew = em.getReference(statusIdNew.getClass(), statusIdNew.getId());
                requisicoes.setStatusId(statusIdNew);
            }
            if (usuarioTipoidNew != null) {
                usuarioTipoidNew = em.getReference(usuarioTipoidNew.getClass(), usuarioTipoidNew.getId());
                requisicoes.setUsuarioTipoid(usuarioTipoidNew);
            }
            requisicoes = em.merge(requisicoes);
            if (campusIdOld != null && !campusIdOld.equals(campusIdNew)) {
                campusIdOld.getRequisicoesCollection().remove(requisicoes);
                campusIdOld = em.merge(campusIdOld);
            }
            if (campusIdNew != null && !campusIdNew.equals(campusIdOld)) {
                campusIdNew.getRequisicoesCollection().add(requisicoes);
                campusIdNew = em.merge(campusIdNew);
            }
            if (statusIdOld != null && !statusIdOld.equals(statusIdNew)) {
                statusIdOld.getRequisicoesCollection().remove(requisicoes);
                statusIdOld = em.merge(statusIdOld);
            }
            if (statusIdNew != null && !statusIdNew.equals(statusIdOld)) {
                statusIdNew.getRequisicoesCollection().add(requisicoes);
                statusIdNew = em.merge(statusIdNew);
            }
            if (usuarioTipoidOld != null && !usuarioTipoidOld.equals(usuarioTipoidNew)) {
                usuarioTipoidOld.getRequisicoesCollection().remove(requisicoes);
                usuarioTipoidOld = em.merge(usuarioTipoidOld);
            }
            if (usuarioTipoidNew != null && !usuarioTipoidNew.equals(usuarioTipoidOld)) {
                usuarioTipoidNew.getRequisicoesCollection().add(requisicoes);
                usuarioTipoidNew = em.merge(usuarioTipoidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = requisicoes.getId();
                if (findRequisicoes(id) == null) {
                    throw new NonexistentEntityException("The requisicoes with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Requisicoes requisicoes;
            try {
                requisicoes = em.getReference(Requisicoes.class, id);
                requisicoes.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The requisicoes with id " + id + " no longer exists.", enfe);
            }
            Campus campusId = requisicoes.getCampusId();
            if (campusId != null) {
                campusId.getRequisicoesCollection().remove(requisicoes);
                campusId = em.merge(campusId);
            }
            Status statusId = requisicoes.getStatusId();
            if (statusId != null) {
                statusId.getRequisicoesCollection().remove(requisicoes);
                statusId = em.merge(statusId);
            }
            UsuarioTipo usuarioTipoid = requisicoes.getUsuarioTipoid();
            if (usuarioTipoid != null) {
                usuarioTipoid.getRequisicoesCollection().remove(requisicoes);
                usuarioTipoid = em.merge(usuarioTipoid);
            }
            em.remove(requisicoes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Requisicoes> findRequisicoesEntities() {
        return findRequisicoesEntities(true, -1, -1);
    }

    public List<Requisicoes> findRequisicoesEntities(int maxResults, int firstResult) {
        return findRequisicoesEntities(false, maxResults, firstResult);
    }

    private List<Requisicoes> findRequisicoesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Requisicoes.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Requisicoes findRequisicoes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Requisicoes.class, id);
        } finally {
            em.close();
        }
    }

    public int getRequisicoesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Requisicoes> rt = cq.from(Requisicoes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
