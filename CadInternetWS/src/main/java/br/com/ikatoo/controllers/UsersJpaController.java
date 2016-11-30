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
import br.com.ikatoo.models.TipoUsers;
import br.com.ikatoo.models.Users;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Milton Carlos Katoo
 */
public class UsersJpaController implements Serializable {

    public UsersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Users users) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoUsers tipoUsersid = users.getTipoUsersid();
            if (tipoUsersid != null) {
                tipoUsersid = em.getReference(tipoUsersid.getClass(), tipoUsersid.getId());
                users.setTipoUsersid(tipoUsersid);
            }
            em.persist(users);
            if (tipoUsersid != null) {
                tipoUsersid.getUsersCollection().add(users);
                tipoUsersid = em.merge(tipoUsersid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Users users) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Users persistentUsers = em.find(Users.class, users.getId());
            TipoUsers tipoUsersidOld = persistentUsers.getTipoUsersid();
            TipoUsers tipoUsersidNew = users.getTipoUsersid();
            if (tipoUsersidNew != null) {
                tipoUsersidNew = em.getReference(tipoUsersidNew.getClass(), tipoUsersidNew.getId());
                users.setTipoUsersid(tipoUsersidNew);
            }
            users = em.merge(users);
            if (tipoUsersidOld != null && !tipoUsersidOld.equals(tipoUsersidNew)) {
                tipoUsersidOld.getUsersCollection().remove(users);
                tipoUsersidOld = em.merge(tipoUsersidOld);
            }
            if (tipoUsersidNew != null && !tipoUsersidNew.equals(tipoUsersidOld)) {
                tipoUsersidNew.getUsersCollection().add(users);
                tipoUsersidNew = em.merge(tipoUsersidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = users.getId();
                if (findUsers(id) == null) {
                    throw new NonexistentEntityException("The users with id " + id + " no longer exists.");
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
            Users users;
            try {
                users = em.getReference(Users.class, id);
                users.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The users with id " + id + " no longer exists.", enfe);
            }
            TipoUsers tipoUsersid = users.getTipoUsersid();
            if (tipoUsersid != null) {
                tipoUsersid.getUsersCollection().remove(users);
                tipoUsersid = em.merge(tipoUsersid);
            }
            em.remove(users);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Users> findUsersEntities() {
        return findUsersEntities(true, -1, -1);
    }

    public List<Users> findUsersEntities(int maxResults, int firstResult) {
        return findUsersEntities(false, maxResults, firstResult);
    }

    private List<Users> findUsersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Users.class));
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

    public Users findUsers(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Users.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Users> rt = cq.from(Users.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
