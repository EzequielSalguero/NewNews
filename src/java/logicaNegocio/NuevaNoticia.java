/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logicaNegocio;

import DAO.NoticiaJpaController;
import DTO.Noticia;
import DTO.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.oreilly.servlet.MultipartRequest;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author usuario
 */
public class NuevaNoticia extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession();
        Usuario usu = (Usuario) session.getAttribute("usu");
        MultipartRequest mr = new MultipartRequest(request, getServletContext().getRealPath("/IMAGENES"));
        
        String titulo = new String(mr.getParameter("titulo").getBytes("ISO-8859-1"),"UTF-8");
        String subtitulo = new String(mr.getParameter("subtitulo").getBytes("ISO-8859-1"),"UTF-8");
        String cuerpo = mr.getParameter("cuerpo");
        Enumeration imgs = mr.getFileNames();
        String imagen =  mr.getFile((String) imgs.nextElement()).getName();
        String pieFoto = new String(mr.getParameter("pieFoto").getBytes("ISO-8859-1"),"UTF-8"); 
        String localidad = new String(mr.getParameter("localidad").getBytes("ISO-8859-1"),"UTF-8"); 
        String seccion = new String(mr.getParameter("seccion").getBytes("ISO-8859-1"),"UTF-8"); 
        String subseccion = mr.getParameter("subseccion");
        if(subseccion!=null){
            subseccion = new String(mr.getParameter("subseccion").getBytes("ISO-8859-1"),"UTF-8"); 
        }
        
        Date fecha = new Date();
        Noticia noticia = new Noticia(null, titulo, subtitulo, cuerpo, imagen, pieFoto, localidad, fecha, 0, seccion);
        noticia.setIdUsuario(usu);
        noticia.setSubseccion(subseccion);
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NewNewsPU");
        NoticiaJpaController ctrNoticia = new NoticiaJpaController(emf);
        
        ctrNoticia.create(noticia);

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
