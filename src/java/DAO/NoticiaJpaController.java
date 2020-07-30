/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import DTO.Noticia;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author usuario
 */
public class NoticiaJpaController implements Serializable {

    public NoticiaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Noticia noticia) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario idUsuario = noticia.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                noticia.setIdUsuario(idUsuario);
            }
            em.persist(noticia);
            if (idUsuario != null) {
                idUsuario.getNoticiaList().add(noticia);
                idUsuario = em.merge(idUsuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Noticia noticia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Noticia persistentNoticia = em.find(Noticia.class, noticia.getCodNoticia());
            Usuario idUsuarioOld = persistentNoticia.getIdUsuario();
            Usuario idUsuarioNew = noticia.getIdUsuario();
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                noticia.setIdUsuario(idUsuarioNew);
            }
            noticia = em.merge(noticia);
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getNoticiaList().remove(noticia);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getNoticiaList().add(noticia);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = noticia.getCodNoticia();
                if (findNoticia(id) == null) {
                    throw new NonexistentEntityException("The noticia with id " + id + " no longer exists.");
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
            Noticia noticia;
            try {
                noticia = em.getReference(Noticia.class, id);
                noticia.getCodNoticia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The noticia with id " + id + " no longer exists.", enfe);
            }
            Usuario idUsuario = noticia.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getNoticiaList().remove(noticia);
                idUsuario = em.merge(idUsuario);
            }
            em.remove(noticia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Noticia> findNoticiaEntities() {
        return findNoticiaEntities(true, -1, -1);
    }

    public List<Noticia> findNoticiaEntities(int maxResults, int firstResult) {
        return findNoticiaEntities(false, maxResults, firstResult);
    }

    private List<Noticia> findNoticiaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Noticia.class));
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

    public Noticia findNoticia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Noticia.class, id);
        } finally {
            em.close();
        }
    }

    public int getNoticiaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Noticia> rt = cq.from(Noticia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List busquedaGenerica(String texto){
        EntityManager em = getEntityManager();
        TypedQuery<Noticia> query = em.createNamedQuery("Noticia.busquedaGenerica", Noticia.class);
        query.setParameter("textoBuscar", "%"+texto+"%");
        return query.getResultList();
    }
    
    public List obtenerNoticiasPorNumVisitas(){
        EntityManager em = getEntityManager();
        TypedQuery<Noticia> query = em.createNamedQuery("Noticia.ordenadasNumVisitas", Noticia.class);
        return query.getResultList();
    }
    
    public List obtenerNoticiasPorFecha(){
        EntityManager em = getEntityManager();
        TypedQuery<Noticia> query = em.createNamedQuery("Noticia.ordenadasFecha", Noticia.class);
        return query.getResultList();
    }
    
    public List obtenerNoticiasPorNumVisitasSeccion(String seccion){
        EntityManager em = getEntityManager();
        TypedQuery<Noticia> query = em.createNamedQuery("Noticia.ordenadasNumVisitasSeccion", Noticia.class);
        query.setParameter("seccion", seccion);
        return query.getResultList();
    }
    
    public List obtenerNoticiasPorFechaSeccion(String seccion){
        EntityManager em = getEntityManager();
        TypedQuery<Noticia> query = em.createNamedQuery("Noticia.ordenadasFechaSeccion", Noticia.class);
        query.setParameter("seccion", seccion);
        return query.getResultList();
    }
    
    public List obtenerNoticiasPorNumVisitasSubseccion(String subseccion){
        EntityManager em = getEntityManager();
        TypedQuery<Noticia> query = em.createNamedQuery("Noticia.ordenadasNumVisitasSubseccion", Noticia.class);
        query.setParameter("subseccion", subseccion);
        return query.getResultList();
    }
    
    public List obtenerNoticiasPorFechaSubseccion(String subseccion){
        EntityManager em = getEntityManager();
        TypedQuery<Noticia> query = em.createNamedQuery("Noticia.ordenadasFechaSubseccion", Noticia.class);
        query.setParameter("subseccion", subseccion);
        return query.getResultList();
    }
    
    public List obtenerNoticiasUsu(String usu){
        EntityManager em = getEntityManager();
        TypedQuery<Noticia> query = em.createNamedQuery("Noticia.obtenerNoticiasUsuario", Noticia.class);
        query.setParameter("idUsuario", usu);
        return query.getResultList();
    }
}
