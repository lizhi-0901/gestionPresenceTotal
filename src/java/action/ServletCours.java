/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import bd.bd;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import metier.Matiere;

/**
 *
 * @author lizhiwang
 */
public class ServletCours extends HttpServlet {

    
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        response.setContentType("application/xml;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		try (PrintWriter out = response.getWriter())
			{
                            /*----- Ecriture de la page XML -----*/
                            out.println("<?xml version=\"1.0\"?>");
                            out.println("<liste_matiere>");
    //
                          
    /*----- Récupération des paramètres -----*/
                            String formation = request.getParameter("formation");
//                            session.setAttribute("idetudiant",identifiant );
                            HttpSession session=request.getSession();
                            String identifiant=(String)session.getAttribute("idetudiant");
                            System.out.println(identifiant);
                            System.out.println(formation);
                            ArrayList<String> mlist =bd.output(bd.getMatieres(formation,identifiant),0);
                            System.out.println(mlist.size());
                            for(String m: mlist){
                                out.println("<matiere>" + m + "</matiere>");
                            }
                            out.println("</liste_matiere>");
                        }
    }
    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { doGet(request, response); }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */



    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
