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
import java.util.Calendar;
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
import metier.Matiere;
import metier.Personnel;

/**
 *
 * @author lizhiwang
 */
public class ServletSaisir extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        response.setContentType("application/xml;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		try (PrintWriter out = response.getWriter())
			{
			/*----- Ecriture de la page XML -----*/
			out.println("<?xml version=\"1.0\"?>");
                         String nom =(String)request.getSession().getAttribute("nometudiant");
                         String prenom =(String)request.getSession().getAttribute("prenometudiant");
                         String identifiant=(String)request.getSession().getAttribute("idetudiant");
                         String date =request.getParameter("date");
                         String minute =request.getParameter("minute");
                         String typeCreneau =request.getParameter("typeCreneau");
                         String heure =request.getParameter("heure");
                         String presence =request.getParameter("presence");
                         
                         int heureint=Integer.parseInt(heure.substring(1));
                         int minuteint =Integer.parseInt(minute);
                         int heureminute =(heureint*60)+minuteint;
                          
                        
                        out.println("<liste_etatpresence>");
                        out.println("<nom>" + nom + "</nom>");
                        out.println("<prenom>" + prenom + "</prenom>");
                        out.println("<presence>" + presence + "</presence>");
                        out.println("<typeCreneau>" + typeCreneau + "</typeCreneau>");
                            System.out.println("date"+date);
                            System.out.println("heureminute"+heureminute);
                            System.out.println(typeCreneau);
                        Creneau cre = bd.getCreneau(date, typeCreneau,heureminute );
                        //String idCreneau=cre.getIdCreneau();
                           // System.out.println(idCreneau);
                        bd.EnregistereSaisirheure("PJ20200327",identifiant, presence);
                        out.println("</liste_etatpresence>");
                       
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
    }// </editor-fold>// </editor-fold>

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

        
       
        

     
    
    

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
  