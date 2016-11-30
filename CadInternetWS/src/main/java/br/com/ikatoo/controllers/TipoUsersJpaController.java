/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ikatoo.controllers;

import br.com.ikatoo.controllers.exceptions.IllegalOrphanException;
import br.com.ikatoo.controllers.exceptions.NonexistentEntityException;
import br.com.ikatoo.models.TipoUsers;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.ikatoo.models.Users;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Milton Carlos Katoo
 */
public class TipoUsersJpaController implements Serializable {

    public TipoUsersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoUsers tipoUsers) {
        if (tipoUsers.getUsersCollection() == null) {
            tipoUsers.setUsersCollection(new ArrayList<Users>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Users> attachedUsersCollection = new ArrayList<Users>();
            for (Users usersCollectionUsersToAttach : tipoUsers.getUsersCollection()) {
                usersCollectionUsersToAttach = em.getReference(usersCollectionUsersToAttach.getClass(), usersCollectionUsersToAttach.getId());
                attachedUsersCollection.add(usersCollectionUsersToAttach);
            }
            tipoUsers.setUsersCollection(attachedUsersCollection);
            em.persist(tipoUsers);
            for (Users usersCollectionUsers : tipoUsers.getUsersCollection()) {
                TipoUsers oldTipoUsersidOfUsersCollectionUsers = usersCollectionUsers.getTipoUsersid();
                usersCollectionUsers.setTipoUsersid(tipoUsers);
                usersCollectionUsers = em.merge(usersCollectionUsers);
                if (oldTipoUsersidOfUsersCollectionUsers != null) {
                    oldTipoUsersidOfUsersCollectionUsers.getUsersCollection().remove(usersCollectionUsers);
                    oldTipoUsersidOfUsersCollectionUsers = em.merge(oldTipoUsersidOfUsersCollectionUsers);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoUsers tipoUsers) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoUsers persistentTipoUsers = em.find(TipoUsers.class, tipoUsers.getId());
            Collection<Users> usersCollectionOld = persistentTipoUsers.getUsersCollection();
            Collection<Users> usersCollectionNew = tipoUsers.getUsersCollection();
            List<String> illegalOrphanMessages = null;
            for (Users usersCollectionOldUsers : usersCollectionOld) {
                if (!usersCollectionNew.contains(usersCollectionOldUsers)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Users " + usersCollectionOldUsers + " since its tipoUsersid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Users> attachedUsersCollectionNew = new ArrayList<Users>();
            for (Users usersCollectionNewUsersToAttach : usersCollectionNew) {
                usersCollectionNewUsersToAttach = em.getReference(usersCollectionNewUsersToAttach.getClass(), usersCollectionNewUsersToAttach.getId());
                attachedUsersCollectionNew.add(usersCollectionNewUsersToAttach);
            }
            usersCollectionNew = attachedUsersCollectionNew;
            tipoUsers.setUsersCollection(usersCollectionNew);
            tipoUsers = em.merge(tipoUsers);
            for (Users usersCollectionNewUsers : usersCollectionNew) {
                if (!usersCollectionOld.contains(usersCollectionNewUsers)) {
                    TipoUsers oldTipoUsersidOfUsersCollectionNewUsers = usersCollectionNewUsers.getTipoUsersid();
                    usersCollectionNewUsers.setTipoUsersid(tipoUsers);
                    usersCollectionNewUsers = em.merge(usersCollectionNewUsers);
                    if (oldTipoUsersidOfUsersCollectionNewUsers != null && !oldTipoUsersidOfUsersCollectionNewUsers.equals(tipoUsers)) {
                        oldTipoUsersidOfUsersCollectionNewUsers.getUsersCollection().remove(usersCollectionNewUsers);
                        oldTipoUsersidOfUsersCollectionNewUsers = em.merge(oldTipoUsersidOfUsersCollectionNewUsers);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoUsers.getId();
                if (findTipoUsers(id) == null) {
                    throw new NonexistentEntityException("The tipoUsers with id " + id + " no longer exists.");
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
            TipoUsers tipoUsers;
            try {
                tipoUsers = em.getReference(TipoUsers.class, id);
                tipoUsers.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoUsers with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Users> usersCollectionOrphanCheck = tipoUsers.getUsersCollection();
            for (Users usersCollectionOrphanCheckUsers : usersCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoUsers (" + tipoUsers + ") cannot be destroyed since the Users " + usersCollectionOrphanCheckUsers + " in its usersCollection field has a non-nullable tipoUsersid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoUsers);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoUsers> findTipoUsersEntities() {
        return findTipoUsersEntities(true, -1, -1);
    }

    public List<TipoUsers> findTipoUsersEntities(int maxResults, int firstResult) {
        return findTipoUsersEntities(false, maxResults, firstResult);
    }

    private List<TipoUsers> findTipoUsersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoUsers.class));
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

    public TipoUsers findTipoUsers(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoUsers.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoUsersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoUsers> rt = cq.from(TipoUsers.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
