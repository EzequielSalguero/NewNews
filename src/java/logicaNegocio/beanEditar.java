/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logicaNegocio;

import DAO.NoticiaJpaController;
import DTO.Noticia;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author usuario
 */
public class beanEditar {

    private EntityManagerFactory emf;
    private NoticiaJpaController ctrNoticia;
    
    public beanEditar() {
        emf=Persistence.createEntityManagerFactory("NewNewsPU");
        ctrNoticia=new NoticiaJpaController(emf);
    }
    
    public List<Noticia> obtenerNoticiasUsuario(String usu){
        return ctrNoticia.obtenerNoticiasUsu(usu);
    }
    
    public Noticia obtenerNoticia(int cod){
        return ctrNoticia.findNoticia(cod);
    }
    
    public List<Noticia> obtenerTodasLasNoticias(){
        return ctrNoticia.findNoticiaEntities();
    }
}
