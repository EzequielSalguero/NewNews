/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Seccion;
import DTO.Subseccion;
import DTO.SubseccionPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author usuario
 */
public class SubseccionJpaController implements Serializable {

    public SubseccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Subseccion subseccion) throws PreexistingEntityException, Exception {
        if (subseccion.getSubseccionPK() == null) {
            subseccion.setSubseccionPK(new SubseccionPK());
        }
        subseccion.getSubseccionPK().setCodSeccion(subseccion.getSeccion().getCodSeccion());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Seccion seccion = subseccion.getSeccion();
            if (seccion != null) {
                seccion = em.getReference(seccion.getClass(), seccion.getCodSeccion());
                subseccion.setSeccion(seccion);
            }
            em.persist(subseccion);
            if (seccion != null) {
                seccion.getSubseccionList().add(subseccion);
                seccion = em.merge(seccion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSubseccion(subseccion.getSubseccionPK()) != null) {
                throw new PreexistingEntityException("Subseccion " + subseccion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Subseccion subseccion) throws NonexistentEntityException, Exception {
        subseccion.getSubseccionPK().setCodSeccion(subseccion.getSeccion().getCodSeccion());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Subseccion persistentSubseccion = em.find(Subseccion.class, subseccion.getSubseccionPK());
            Seccion seccionOld = persistentSubseccion.getSeccion();
            Seccion seccionNew = subseccion.getSeccion();
            if (seccionNew != null) {
                seccionNew = em.getReference(seccionNew.getClass(), seccionNew.getCodSeccion());
                subseccion.setSeccion(seccionNew);
            }
            subseccion = em.merge(subseccion);
            if (seccionOld != null && !seccionOld.equals(seccionNew)) {
                seccionOld.getSubseccionList().remove(subseccion);
                seccionOld = em.merge(seccionOld);
            }
            if (seccionNew != null && !seccionNew.equals(seccionOld)) {
                seccionNew.getSubseccionList().add(subseccion);
                seccionNew = em.merge(seccionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SubseccionPK id = subseccion.getSubseccionPK();
                if (findSubseccion(id) == null) {
                    throw new NonexistentEntityException("The subseccion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SubseccionPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Subseccion subseccion;
            try {
                subseccion = em.getReference(Subseccion.class, id);
                subseccion.getSubseccionPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The subseccion with id " + id + " no longer exists.", enfe);
            }
            Seccion seccion = subseccion.getSeccion();
            if (seccion != null) {
                seccion.getSubseccionList().remove(subseccion);
                seccion = em.merge(seccion);
            }
            em.remove(subseccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Subseccion> findSubseccionEntities() {
        return findSubseccionEntities(true, -1, -1);
    }

    public List<Subseccion> findSubseccionEntities(int maxResults, int firstResult) {
        return findSubseccionEntities(false, maxResults, firstResult);
    }

    private List<Subseccion> findSubseccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Subseccion.class));
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

    public Subseccion findSubseccion(SubseccionPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Subseccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getSubseccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Subseccion> rt = cq.from(Subseccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List obtenerSubseccionesPorCodSeccion(int cod){
        EntityManager em = getEntityManager();
        TypedQuery<Subseccion> query = em.createNamedQuery("Subseccion.findByCodSeccion", Subseccion.class);
        query.setParameter("codSeccion", cod);
        return query.getResultList();
    }
}
