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
import br.com.ikatoo.models.Status;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Milton Carlos Katoo
 */
public class StatusJpaController implements Serializable {

    public StatusJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Status status) {
        if (status.getRequisicoesCollection() == null) {
            status.setRequisicoesCollection(new ArrayList<Requisicoes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Requisicoes> attachedRequisicoesCollection = new ArrayList<Requisicoes>();
            for (Requisicoes requisicoesCollectionRequisicoesToAttach : status.getRequisicoesCollection()) {
                requisicoesCollectionRequisicoesToAttach = em.getReference(requisicoesCollectionRequisicoesToAttach.getClass(), requisicoesCollectionRequisicoesToAttach.getId());
                attachedRequisicoesCollection.add(requisicoesCollectionRequisicoesToAttach);
            }
            status.setRequisicoesCollection(attachedRequisicoesCollection);
            em.persist(status);
            for (Requisicoes requisicoesCollectionRequisicoes : status.getRequisicoesCollection()) {
                Status oldStatusIdOfRequisicoesCollectionRequisicoes = requisicoesCollectionRequisicoes.getStatusId();
                requisicoesCollectionRequisicoes.setStatusId(status);
                requisicoesCollectionRequisicoes = em.merge(requisicoesCollectionRequisicoes);
                if (oldStatusIdOfRequisicoesCollectionRequisicoes != null) {
                    oldStatusIdOfRequisicoesCollectionRequisicoes.getRequisicoesCollection().remove(requisicoesCollectionRequisicoes);
                    oldStatusIdOfRequisicoesCollectionRequisicoes = em.merge(oldStatusIdOfRequisicoesCollectionRequisicoes);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Status status) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Status persistentStatus = em.find(Status.class, status.getId());
            Collection<Requisicoes> requisicoesCollectionOld = persistentStatus.getRequisicoesCollection();
            Collection<Requisicoes> requisicoesCollectionNew = status.getRequisicoesCollection();
            List<String> illegalOrphanMessages = null;
            for (Requisicoes requisicoesCollectionOldRequisicoes : requisicoesCollectionOld) {
                if (!requisicoesCollectionNew.contains(requisicoesCollectionOldRequisicoes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Requisicoes " + requisicoesCollectionOldRequisicoes + " since its statusId field is not nullable.");
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
            status.setRequisicoesCollection(requisicoesCollectionNew);
            status = em.merge(status);
            for (Requisicoes requisicoesCollectionNewRequisicoes : requisicoesCollectionNew) {
                if (!requisicoesCollectionOld.contains(requisicoesCollectionNewRequisicoes)) {
                    Status oldStatusIdOfRequisicoesCollectionNewRequisicoes = requisicoesCollectionNewRequisicoes.getStatusId();
                    requisicoesCollectionNewRequisicoes.setStatusId(status);
                    requisicoesCollectionNewRequisicoes = em.merge(requisicoesCollectionNewRequisicoes);
                    if (oldStatusIdOfRequisicoesCollectionNewRequisicoes != null && !oldStatusIdOfRequisicoesCollectionNewRequisicoes.equals(status)) {
                        oldStatusIdOfRequisicoesCollectionNewRequisicoes.getRequisicoesCollection().remove(requisicoesCollectionNewRequisicoes);
                        oldStatusIdOfRequisicoesCollectionNewRequisicoes = em.merge(oldStatusIdOfRequisicoesCollectionNewRequisicoes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = status.getId();
                if (findStatus(id) == null) {
                    throw new NonexistentEntityException("The status with id " + id + " no longer exists.");
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
            Status status;
            try {
                status = em.getReference(Status.class, id);
                status.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The status with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Requisicoes> requisicoesCollectionOrphanCheck = status.getRequisicoesCollection();
            for (Requisicoes requisicoesCollectionOrphanCheckRequisicoes : requisicoesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Status (" + status + ") cannot be destroyed since the Requisicoes " + requisicoesCollectionOrphanCheckRequisicoes + " in its requisicoesCollection field has a non-nullable statusId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(status);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Status> findStatusEntities() {
        return findStatusEntities(true, -1, -1);
    }

    public List<Status> findStatusEntities(int maxResults, int firstResult) {
        return findStatusEntities(false, maxResults, firstResult);
    }

    private List<Status> findStatusEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Status.class));
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

    public Status findStatus(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Status.class, id);
        } finally {
            em.close();
        }
    }

    public int getStatusCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Status> rt = cq.from(Status.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
