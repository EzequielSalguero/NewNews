/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Noticia;
import DTO.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author usuario
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        if (usuario.getNoticiaList() == null) {
            usuario.setNoticiaList(new ArrayList<Noticia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Noticia> attachedNoticiaList = new ArrayList<Noticia>();
            for (Noticia noticiaListNoticiaToAttach : usuario.getNoticiaList()) {
                noticiaListNoticiaToAttach = em.getReference(noticiaListNoticiaToAttach.getClass(), noticiaListNoticiaToAttach.getCodNoticia());
                attachedNoticiaList.add(noticiaListNoticiaToAttach);
            }
            usuario.setNoticiaList(attachedNoticiaList);
            em.persist(usuario);
            for (Noticia noticiaListNoticia : usuario.getNoticiaList()) {
                Usuario oldIdUsuarioOfNoticiaListNoticia = noticiaListNoticia.getIdUsuario();
                noticiaListNoticia.setIdUsuario(usuario);
                noticiaListNoticia = em.merge(noticiaListNoticia);
                if (oldIdUsuarioOfNoticiaListNoticia != null) {
                    oldIdUsuarioOfNoticiaListNoticia.getNoticiaList().remove(noticiaListNoticia);
                    oldIdUsuarioOfNoticiaListNoticia = em.merge(oldIdUsuarioOfNoticiaListNoticia);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getIdUsuario()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getIdUsuario());
            List<Noticia> noticiaListOld = persistentUsuario.getNoticiaList();
            List<Noticia> noticiaListNew = usuario.getNoticiaList();
            List<String> illegalOrphanMessages = null;
            for (Noticia noticiaListOldNoticia : noticiaListOld) {
                if (!noticiaListNew.contains(noticiaListOldNoticia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Noticia " + noticiaListOldNoticia + " since its idUsuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Noticia> attachedNoticiaListNew = new ArrayList<Noticia>();
            for (Noticia noticiaListNewNoticiaToAttach : noticiaListNew) {
                noticiaListNewNoticiaToAttach = em.getReference(noticiaListNewNoticiaToAttach.getClass(), noticiaListNewNoticiaToAttach.getCodNoticia());
                attachedNoticiaListNew.add(noticiaListNewNoticiaToAttach);
            }
            noticiaListNew = attachedNoticiaListNew;
            usuario.setNoticiaList(noticiaListNew);
            usuario = em.merge(usuario);
            for (Noticia noticiaListNewNoticia : noticiaListNew) {
                if (!noticiaListOld.contains(noticiaListNewNoticia)) {
                    Usuario oldIdUsuarioOfNoticiaListNewNoticia = noticiaListNewNoticia.getIdUsuario();
                    noticiaListNewNoticia.setIdUsuario(usuario);
                    noticiaListNewNoticia = em.merge(noticiaListNewNoticia);
                    if (oldIdUsuarioOfNoticiaListNewNoticia != null && !oldIdUsuarioOfNoticiaListNewNoticia.equals(usuario)) {
                        oldIdUsuarioOfNoticiaListNewNoticia.getNoticiaList().remove(noticiaListNewNoticia);
                        oldIdUsuarioOfNoticiaListNewNoticia = em.merge(oldIdUsuarioOfNoticiaListNewNoticia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = usuario.getIdUsuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getIdUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Noticia> noticiaListOrphanCheck = usuario.getNoticiaList();
            for (Noticia noticiaListOrphanCheckNoticia : noticiaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Noticia " + noticiaListOrphanCheckNoticia + " in its noticiaList field has a non-nullable idUsuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
