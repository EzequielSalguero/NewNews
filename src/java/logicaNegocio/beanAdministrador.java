/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logicaNegocio;

import DAO.ContactoJpaController;
import DAO.NoticiaJpaController;
import DAO.UsuarioJpaController;
import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DTO.Contacto;
import DTO.Noticia;
import DTO.Usuario;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Séfora
 */
public class beanAdministrador {
    
    private EntityManagerFactory emf;
    private UsuarioJpaController ctrUsu;
    private NoticiaJpaController ctrNoticia;
    private ContactoJpaController ctrCont;
    private String codUsu;
    private int codNoticia;
    private String mensajeContacto;

    public beanAdministrador() {
        emf=Persistence.createEntityManagerFactory("NewNewsPU");
        ctrUsu=new UsuarioJpaController(emf);
        ctrNoticia=new NoticiaJpaController(emf);
        ctrCont=new ContactoJpaController(emf);
    }

    public String getMensajeContacto() {
        return mensajeContacto;
    }

    public void setMensajeContacto(String mensajeContacto) {
        this.mensajeContacto = mensajeContacto;
    }

    public int getCodNoticia() {
        return codNoticia;
    }

    public void setCodNoticiaM(int codNoticia) {
        this.codNoticia = codNoticia;
    }

    public String getCodUsu() {
        return codUsu;
    }

    public void setCodUsu(String codUsu) {
        this.codUsu = codUsu;
    }
    
    public List<Usuario> obtenerUsuarios(){
        return ctrUsu.findUsuarioEntities();
    }
    
    public List<Noticia> obtenerNoticias(){
        return ctrNoticia.findNoticiaEntities();
    }
    
    public void eliminarUsuario(){
        try {
            ctrUsu.destroy(codUsu);
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(beanAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(beanAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void elminarNoticia(){
        try {
            ctrNoticia.destroy(codNoticia);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(beanAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Contacto> obtenerMensajes(){
        return ctrCont.findContactoEntities();
    }
    
    public void eliminarMensaje(){
        try {
            ctrCont.destroy(mensajeContacto);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(beanAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
