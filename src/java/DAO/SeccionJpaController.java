/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import DTO.Noticia;
import DTO.Seccion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Subseccion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author usuario
 */
public class SeccionJpaController implements Serializable {

    public SeccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Seccion seccion) throws PreexistingEntityException, Exception {
        if (seccion.getSubseccionList() == null) {
            seccion.setSubseccionList(new ArrayList<Subseccion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Subseccion> attachedSubseccionList = new ArrayList<Subseccion>();
            for (Subseccion subseccionListSubseccionToAttach : seccion.getSubseccionList()) {
                subseccionListSubseccionToAttach = em.getReference(subseccionListSubseccionToAttach.getClass(), subseccionListSubseccionToAttach.getSubseccionPK());
                attachedSubseccionList.add(subseccionListSubseccionToAttach);
            }
            seccion.setSubseccionList(attachedSubseccionList);
            em.persist(seccion);
            for (Subseccion subseccionListSubseccion : seccion.getSubseccionList()) {
                Seccion oldSeccionOfSubseccionListSubseccion = subseccionListSubseccion.getSeccion();
                subseccionListSubseccion.setSeccion(seccion);
                subseccionListSubseccion = em.merge(subseccionListSubseccion);
                if (oldSeccionOfSubseccionListSubseccion != null) {
                    oldSeccionOfSubseccionListSubseccion.getSubseccionList().remove(subseccionListSubseccion);
                    oldSeccionOfSubseccionListSubseccion = em.merge(oldSeccionOfSubseccionListSubseccion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSeccion(seccion.getCodSeccion()) != null) {
                throw new PreexistingEntityException("Seccion " + seccion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Seccion seccion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Seccion persistentSeccion = em.find(Seccion.class, seccion.getCodSeccion());
            List<Subseccion> subseccionListOld = persistentSeccion.getSubseccionList();
            List<Subseccion> subseccionListNew = seccion.getSubseccionList();
            List<String> illegalOrphanMessages = null;
            for (Subseccion subseccionListOldSubseccion : subseccionListOld) {
                if (!subseccionListNew.contains(subseccionListOldSubseccion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Subseccion " + subseccionListOldSubseccion + " since its seccion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Subseccion> attachedSubseccionListNew = new ArrayList<Subseccion>();
            for (Subseccion subseccionListNewSubseccionToAttach : subseccionListNew) {
                subseccionListNewSubseccionToAttach = em.getReference(subseccionListNewSubseccionToAttach.getClass(), subseccionListNewSubseccionToAttach.getSubseccionPK());
                attachedSubseccionListNew.add(subseccionListNewSubseccionToAttach);
            }
            subseccionListNew = attachedSubseccionListNew;
            seccion.setSubseccionList(subseccionListNew);
            seccion = em.merge(seccion);
            for (Subseccion subseccionListNewSubseccion : subseccionListNew) {
                if (!subseccionListOld.contains(subseccionListNewSubseccion)) {
                    Seccion oldSeccionOfSubseccionListNewSubseccion = subseccionListNewSubseccion.getSeccion();
                    subseccionListNewSubseccion.setSeccion(seccion);
                    subseccionListNewSubseccion = em.merge(subseccionListNewSubseccion);
                    if (oldSeccionOfSubseccionListNewSubseccion != null && !oldSeccionOfSubseccionListNewSubseccion.equals(seccion)) {
                        oldSeccionOfSubseccionListNewSubseccion.getSubseccionList().remove(subseccionListNewSubseccion);
                        oldSeccionOfSubseccionListNewSubseccion = em.merge(oldSeccionOfSubseccionListNewSubseccion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = seccion.getCodSeccion();
                if (findSeccion(id) == null) {
                    throw new NonexistentEntityException("The seccion with id " + id + " no longer exists.");
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
            Seccion seccion;
            try {
                seccion = em.getReference(Seccion.class, id);
                seccion.getCodSeccion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The seccion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Subseccion> subseccionListOrphanCheck = seccion.getSubseccionList();
            for (Subseccion subseccionListOrphanCheckSubseccion : subseccionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Seccion (" + seccion + ") cannot be destroyed since the Subseccion " + subseccionListOrphanCheckSubseccion + " in its subseccionList field has a non-nullable seccion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(seccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Seccion> findSeccionEntities() {
        return findSeccionEntities(true, -1, -1);
    }

    public List<Seccion> findSeccionEntities(int maxResults, int firstResult) {
        return findSeccionEntities(false, maxResults, firstResult);
    }

    private List<Seccion> findSeccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Seccion.class));
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

    public Seccion findSeccion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Seccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getSeccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Seccion> rt = cq.from(Seccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List obtenerSeccionPorNombre(String nomSeccion){
        EntityManager em = getEntityManager();
        TypedQuery<Seccion> query = em.createNamedQuery("Seccion.findByNomSeccion", Seccion.class);
        query.setParameter("nomSeccion", nomSeccion);
        return query.getResultList();
    }
}
