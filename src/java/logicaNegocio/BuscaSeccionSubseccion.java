/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logicaNegocio;

import DAO.SeccionJpaController;
import DAO.SubseccionJpaController;
import DTO.Seccion;
import DTO.Subseccion;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author usuario
 */
public class BuscaSeccionSubseccion extends HttpServlet {

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
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NewNewsPU");
        SeccionJpaController ctrSeccion = new SeccionJpaController(emf);
        SubseccionJpaController ctrSubseccion = new SubseccionJpaController(emf);
        
        String seccion = request.getParameter("seccion");
        String subseccion = request.getParameter("subseccion");
        
        JSONObject obj = new JSONObject();
        
        if(seccion != null){
            List<Seccion> listaSecciones = ctrSeccion.findSeccionEntities();
            try {
                for(Seccion sec: listaSecciones){
                    obj.put(sec.getNomSeccion(), sec.getNomSeccion());
                }
            } catch (JSONException ex) {
                Logger.getLogger(BuscaSeccionSubseccion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            List<Seccion> sec = ctrSeccion.obtenerSeccionPorNombre(subseccion);
            for(Seccion s: sec){
                List<Subseccion> listaSubsecciones = ctrSubseccion.obtenerSubseccionesPorCodSeccion(s.getCodSeccion());
                for(Subseccion sub: listaSubsecciones){
                    try {
                        obj.put(sub.getNomSubseccion(), sub.getNomSubseccion());
                    } catch (JSONException ex) {
                        Logger.getLogger(BuscaSeccionSubseccion.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        
        response.setContentType("application/json");
        response.getWriter().print(obj.toString());
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
