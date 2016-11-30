/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ikatoo.controllers;

import br.com.ikatoo.controllers.exceptions.IllegalOrphanException;
import br.com.ikatoo.controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.ikatoo.models.Requisicoes;
import br.com.ikatoo.models.UsuarioTipo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Milton Carlos Katoo
 */
public class UsuarioTipoJpaController implements Serializable {

    public UsuarioTipoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UsuarioTipo usuarioTipo) {
        if (usuarioTipo.getRequisicoesCollection() == null) {
            usuarioTipo.setRequisicoesCollection(new ArrayList<Requisicoes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Requisicoes> attachedRequisicoesCollection = new ArrayList<Requisicoes>();
            for (Requisicoes requisicoesCollectionRequisicoesToAttach : usuarioTipo.getRequisicoesCollection()) {
                requisicoesCollectionRequisicoesToAttach = em.getReference(requisicoesCollectionRequisicoesToAttach.getClass(), requisicoesCollectionRequisicoesToAttach.getId());
                attachedRequisicoesCollection.add(requisicoesCollectionRequisicoesToAttach);
            }
            usuarioTipo.setRequisicoesCollection(attachedRequisicoesCollection);
            em.persist(usuarioTipo);
            for (Requisicoes requisicoesCollectionRequisicoes : usuarioTipo.getRequisicoesCollection()) {
                UsuarioTipo oldUsuarioTipoidOfRequisicoesCollectionRequisicoes = requisicoesCollectionRequisicoes.getUsuarioTipoid();
                requisicoesCollectionRequisicoes.setUsuarioTipoid(usuarioTipo);
                requisicoesCollectionRequisicoes = em.merge(requisicoesCollectionRequisicoes);
                if (oldUsuarioTipoidOfRequisicoesCollectionRequisicoes != null) {
                    oldUsuarioTipoidOfRequisicoesCollectionRequisicoes.getRequisicoesCollection().remove(requisicoesCollectionRequisicoes);
                    oldUsuarioTipoidOfRequisicoesCollectionRequisicoes = em.merge(oldUsuarioTipoidOfRequisicoesCollectionRequisicoes);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UsuarioTipo usuarioTipo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UsuarioTipo persistentUsuarioTipo = em.find(UsuarioTipo.class, usuarioTipo.getId());
            Collection<Requisicoes> requisicoesCollectionOld = persistentUsuarioTipo.getRequisicoesCollection();
            Collection<Requisicoes> requisicoesCollectionNew = usuarioTipo.getRequisicoesCollection();
            List<String> illegalOrphanMessages = null;
            for (Requisicoes requisicoesCollectionOldRequisicoes : requisicoesCollectionOld) {
                if (!requisicoesCollectionNew.contains(requisicoesCollectionOldRequisicoes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Requisicoes " + requisicoesCollectionOldRequisicoes + " since its usuarioTipoid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Requisicoes> attachedRequisicoesCollectionNew = new ArrayList<Requisicoes>();
            for (Requisicoes requisicoesCollectionNewRequisicoesToAttach : requisicoesCollectionNew) {
                requisicoesCollectionNewRequisicoesToAttach = em.getReference(requisicoesCollectionNewRequisicoesToAttach.getClass(), requisicoesCollectionNewRequisicoesToAttach.getId());
                attachedRequisicoesCollectionNew.add(requisicoesCollectionNewRequisicoesToAttach);
            }
            requisicoesCollectionNew = attachedRequisicoesCollectionNew;
            usuarioTipo.setRequisicoesCollection(requisicoesCollectionNew);
            usuarioTipo = em.merge(usuarioTipo);
            for (Requisicoes requisicoesCollectionNewRequisicoes : requisicoesCollectionNew) {
                if (!requisicoesCollectionOld.contains(requisicoesCollectionNewRequisicoes)) {
                    UsuarioTipo oldUsuarioTipoidOfRequisicoesCollectionNewRequisicoes = requisicoesCollectionNewRequisicoes.getUsuarioTipoid();
                    requisicoesCollectionNewRequisicoes.setUsuarioTipoid(usuarioTipo);
                    requisicoesCollectionNewRequisicoes = em.merge(requisicoesCollectionNewRequisicoes);
                    if (oldUsuarioTipoidOfRequisicoesCollectionNewRequisicoes != null && !oldUsuarioTipoidOfRequisicoesCollectionNewRequisicoes.equals(usuarioTipo)) {
                        oldUsuarioTipoidOfRequisicoesCollectionNewRequisicoes.getRequisicoesCollection().remove(requisicoesCollectionNewRequisicoes);
                        oldUsuarioTipoidOfRequisicoesCollectionNewRequisicoes = em.merge(oldUsuarioTipoidOfRequisicoesCollectionNewRequisicoes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuarioTipo.getId();
                if (findUsuarioTipo(id) == null) {
                    throw new NonexistentEntityException("The usuarioTipo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UsuarioTipo usuarioTipo;
            try {
                usuarioTipo = em.getReference(UsuarioTipo.class, id);
                usuarioTipo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarioTipo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Requisicoes> requisicoesCollectionOrphanCheck = usuarioTipo.getRequisicoesCollection();
            for (Requisicoes requisicoesCollectionOrphanCheckRequisicoes : requisicoesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This UsuarioTipo (" + usuarioTipo + ") cannot be destroyed since the Requisicoes " + requisicoesCollectionOrphanCheckRequisicoes + " in its requisicoesCollection field has a non-nullable usuarioTipoid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(usuarioTipo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UsuarioTipo> findUsuarioTipoEntities() {
        return findUsuarioTipoEntities(true, -1, -1);
    }

    public List<UsuarioTipo> findUsuarioTipoEntities(int maxResults, int firstResult) {
        return findUsuarioTipoEntities(false, maxResults, firstResult);
    }

    private List<UsuarioTipo> findUsuarioTipoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UsuarioTipo.class));
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

    public UsuarioTipo findUsuarioTipo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UsuarioTipo.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioTipoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UsuarioTipo> rt = cq.from(UsuarioTipo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
