/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ikatoo.controllers;

import br.com.ikatoo.controllers.exceptions.IllegalOrphanException;
import br.com.ikatoo.controllers.exceptions.NonexistentEntityException;
import br.com.ikatoo.models.Campus;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.ikatoo.models.Requisicoes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Milton Carlos Katoo
 */
public class CampusJpaController implements Serializable {

    public CampusJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Campus campus) {
        if (campus.getRequisicoesCollection() == null) {
            campus.setRequisicoesCollection(new ArrayList<Requisicoes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Requisicoes> attachedRequisicoesCollection = new ArrayList<Requisicoes>();
            for (Requisicoes requisicoesCollectionRequisicoesToAttach : campus.getRequisicoesCollection()) {
                requisicoesCollectionRequisicoesToAttach = em.getReference(requisicoesCollectionRequisicoesToAttach.getClass(), requisicoesCollectionRequisicoesToAttach.getId());
                attachedRequisicoesCollection.add(requisicoesCollectionRequisicoesToAttach);
            }
            campus.setRequisicoesCollection(attachedRequisicoesCollection);
            em.persist(campus);
            for (Requisicoes requisicoesCollectionRequisicoes : campus.getRequisicoesCollection()) {
                Campus oldCampusIdOfRequisicoesCollectionRequisicoes = requisicoesCollectionRequisicoes.getCampusId();
                requisicoesCollectionRequisicoes.setCampusId(campus);
                requisicoesCollectionRequisicoes = em.merge(requisicoesCollectionRequisicoes);
                if (oldCampusIdOfRequisicoesCollectionRequisicoes != null) {
                    oldCampusIdOfRequisicoesCollectionRequisicoes.getRequisicoesCollection().remove(requisicoesCollectionRequisicoes);
                    oldCampusIdOfRequisicoesCollectionRequisicoes = em.merge(oldCampusIdOfRequisicoesCollectionRequisicoes);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Campus campus) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Campus persistentCampus = em.find(Campus.class, campus.getId());
            Collection<Requisicoes> requisicoesCollectionOld = persistentCampus.getRequisicoesCollection();
            Collection<Requisicoes> requisicoesCollectionNew = campus.getRequisicoesCollection();
            List<String> illegalOrphanMessages = null;
            for (Requisicoes requisicoesCollectionOldRequisicoes : requisicoesCollectionOld) {
                if (!requisicoesCollectionNew.contains(requisicoesCollectionOldRequisicoes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Requisicoes " + requisicoesCollectionOldRequisicoes + " since its campusId field is not nullable.");
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
            campus.setRequisicoesCollection(requisicoesCollectionNew);
            campus = em.merge(campus);
            for (Requisicoes requisicoesCollectionNewRequisicoes : requisicoesCollectionNew) {
                if (!requisicoesCollectionOld.contains(requisicoesCollectionNewRequisicoes)) {
                    Campus oldCampusIdOfRequisicoesCollectionNewRequisicoes = requisicoesCollectionNewRequisicoes.getCampusId();
                    requisicoesCollectionNewRequisicoes.setCampusId(campus);
                    requisicoesCollectionNewRequisicoes = em.merge(requisicoesCollectionNewRequisicoes);
                    if (oldCampusIdOfRequisicoesCollectionNewRequisicoes != null && !oldCampusIdOfRequisicoesCollectionNewRequisicoes.equals(campus)) {
                        oldCampusIdOfRequisicoesCollectionNewRequisicoes.getRequisicoesCollection().remove(requisicoesCollectionNewRequisicoes);
                        oldCampusIdOfRequisicoesCollectionNewRequisicoes = em.merge(oldCampusIdOfRequisicoesCollectionNewRequisicoes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = campus.getId();
                if (findCampus(id) == null) {
                    throw new NonexistentEntityException("The campus with id " + id + " no longer exists.");
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
            Campus campus;
            try {
                campus = em.getReference(Campus.class, id);
                campus.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The campus with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Requisicoes> requisicoesCollectionOrphanCheck = campus.getRequisicoesCollection();
            for (Requisicoes requisicoesCollectionOrphanCheckRequisicoes : requisicoesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Campus (" + campus + ") cannot be destroyed since the Requisicoes " + requisicoesCollectionOrphanCheckRequisicoes + " in its requisicoesCollection field has a non-nullable campusId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(campus);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Campus> findCampusEntities() {
        return findCampusEntities(true, -1, -1);
    }

    public List<Campus> findCampusEntities(int maxResults, int firstResult) {
        return findCampusEntities(false, maxResults, firstResult);
    }

    private List<Campus> findCampusEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Campus.class));
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

    public Campus findCampus(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Campus.class, id);
        } finally {
            em.close();
        }
    }

    public int getCampusCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Campus> rt = cq.from(Campus.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
