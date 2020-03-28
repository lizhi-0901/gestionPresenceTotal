/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import bd.bd;
import bd.util;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.Creneau;
import metier.Matiere;
import metier.Personnel;

/**
 *
 * @author lizhiwang
 */
public class ServletValide extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        response.setContentType("application/xml;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		try (PrintWriter out = response.getWriter())
			{
			/*----- Ecriture de la page XML -----*/
			out.println("<?xml version=\"1.0\"?>");
                        out.println("<liste_etatpresence>");
                        List<?> listid =util.convertObjectToList(request.getSession().getAttribute("id"));
                        String idetudiant = request.getParameter("idetudiant");
                            
                        String etavalide = request.getParameter("etavalide");
                        System.out.println("action.ServletValide.etatvalide"+etavalide);
                        Iterator iterator = listid.iterator();
                        int j=0;
		        while (iterator.hasNext()) {
                            
                        System.out.println("action.ServletValide"+listid.size());
			String s = (String) iterator.next();
			System.out.println("servlet valide"+s);
                           bd.Valide(etavalide, idetudiant, s);
                           j=j+1;
                         }
                        if(j!=0){
                            out.println("<num>"+j+"</num>");
                        out.println("</liste_etatpresence>"); 
                        }
                        
//                        System.out.println(idetudiant);
                        
                        /**
                         * filter
                         */
                        
    }    }
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
