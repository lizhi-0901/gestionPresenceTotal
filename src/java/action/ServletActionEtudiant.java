/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Arsla
 */
public class ServletActionEtudiant extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @SuppressWarnings("empty-statement")
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        
        String nom=request.getParameter("nom");
        String prenom=request.getParameter("prenom");
        String numTel=request.getParameter("numTel");
        String eMail=request.getParameter("eMail");
        String formation=request.getParameter("formation");
        String entreprise=request.getParameter("entreprise");
        String numTelE=request.getParameter("numTelE");
        String eMailE=request.getParameter("eMailE");
        
        String action=request.getParameter("action");
        System.out.println("---------------AAAA"+action);
        if("ajouter".equals(action)){
            System.out.println(action);
            bd.bd.ajouterEtudiant(nom, prenom, eMail, numTel, formation, entreprise, numTelE, eMailE);
        };
        if("modifier".equals(action)){
                System.out.println("---------------MMMM"+action);
                bd.bd.modifierEtudiant(nom, prenom, eMail, numTel, formation, entreprise, numTelE, eMailE);
        };
        if("supprimer".equals(action)){
            bd.bd.SupprimerEnseignant(nom, prenom);
        }
            
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

    private void elsif(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
