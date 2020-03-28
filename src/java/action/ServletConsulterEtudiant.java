/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import bd.bd;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.Personnel;

/**
 *
 * @author Arsla
 */
public class ServletConsulterEtudiant extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
                response.setContentType("application/xml;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                /*----- Ecriture de la page XML -----*/
                out.println("<?xml version=\"1.0\"?>");
                /*----- Récupération des paramètres -----*/
                String nom = request.getParameter("nom");
                String prenom = request.getParameter("prenom");
                List<Personnel> listEtudiants=bd.consulterEtudiant(nom, prenom);
                
                for(Personnel p:listEtudiants){
                    out.println("<Enseignant>");
                    out.println("<nom>" + nom + "</nom>");
                    System.out.println(nom);
                    out.println("<prenom>" + prenom + "</prenom>");
                    System.out.println(prenom);
                    String numTel=p.getNumTel();
                    out.println("<numTel>"+numTel+"</numTel>");
                    System.out.println(numTel);
                    String eMail=p.getAdresseMail();
                    out.println("<eMail>"+eMail+"</eMail>");
                    System.out.println(eMail);
                    String formation=p.getTypeformation();
                    out.println("<formation>"+formation+"</formation>");
                    System.out.println(formation);
                    String entreprise=p.getEntreprise();
                    out.println("<entreprise>"+entreprise+"</entreprise>");
                    System.out.println(entreprise);
                    String numTelE=p.getContactTel();
                    out.println("<numTelE>"+numTelE+"</numTelE>");
                    System.out.println(numTelE);
                    String eMailE=p.getContactMail();
                    out.println("<eMailE>"+eMailE+"</eMailE>");
                    System.out.println(eMailE);                    
                  out.println("</Enseignant>");
            } 
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

}
