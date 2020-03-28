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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import metier.Creneau;
import metier.Groupe;
import metier.Matiere;

/**
 *
 * @author Arslan
 */
public class ServletInsertEtat extends HttpServlet
{
    protected void processRequest (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException
        {
            
             /*----- Récupération des paramètres -----*/
            HttpSession session=request.getSession();
            String idCreneau=(String)session.getAttribute("idCreneau");
            System.out.println("action.ServletInsertEtat.processRequest()"+idCreneau);
            System.out.println("idCCCC:"+idCreneau);
            String idEtudiant=request.getParameter("id");
            String etat=request.getParameter("etat");
            System.out.println("idEEE:"+idEtudiant);
            System.out.println("ETAT: "+etat);
            bd.EnregistrerEtat(idEtudiant, idCreneau, etat);
        }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(ServletInsertEtat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(ServletInsertEtat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

} /*----- Fin de la classe Enregistrer -----*/
