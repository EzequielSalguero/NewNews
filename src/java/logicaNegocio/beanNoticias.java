/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logicaNegocio;

import DAO.NoticiaJpaController;
import DTO.Noticia;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Séfora
 */
public class beanNoticias {
    
    private EntityManagerFactory emf;
    private NoticiaJpaController ctrNoticia;

    public beanNoticias() {
        emf=Persistence.createEntityManagerFactory("NewNewsPU");
        ctrNoticia=new NoticiaJpaController(emf);
    }
    
    public List<Noticia> obtenerNoticiasUsu(String usu){
        return ctrNoticia.obtenerNoticiasUsu(usu);
    }
}
