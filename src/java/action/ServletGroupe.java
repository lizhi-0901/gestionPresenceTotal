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
import metier.Groupe;
import metier.Matiere;

/**
 *
 * @author lizhiwang
 */
public class ServletGroupe extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        response.setContentType("application/xml;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
                
		try (PrintWriter out = response.getWriter())
			{
			/*----- Ecriture de la page XML -----*/
			out.println("<?xml version=\"1.0\"?>");
                        out.println("<liste_groupe>");
//
			/*----- Récupération des paramètres -----*/
	
//                        Date date = df.parse(request.getParameter("date"));
//                        String libelleMatiere = request.getParameter("cours");
//                        int heureDeb= Integer.parseInt(request.getParameter("heure"));
//                        
                        Date date =new Date(2019-10-01);
                        String libelleMatiere ="Donnee,integration";
                        int heureDeb =570;
                        System.out.println(heureDeb);
                        System.out.println(libelleMatiere);
                        System.out.println(date);
                        
                        ArrayList<String> glist =bd.output(bd.getGroupe(date, heureDeb, libelleMatiere),0);
                        
                        System.out.println(glist.size());
                        for(String g: glist){
                          
                        out.println("<groupe>" + g + "</groupe>");
                            System.out.println("groupe"+g); 
                            // retourne idgroupe
			
			}
                        out.println("<liste_groupe>");
//                        String dateString=request.getParameter("date");
//                        String libelleMatiere = request.getParameter("cours");
//                        String heure=request.getParameter("heure");
//                        SimpleDateFormat df = new SimpleDateFormat("yy-mm-dd");
//                        Date date = df.parse(dateString);
//                        int heureDeb= Integer.parseInt(heure);
//                        
                        
                        List<Groupe> listGroupes=bd.getGroupe(date, heureDeb, libelleMatiere);
                        for(Groupe g:listGroupes){
                            String idGroupe=g.getIdGroupe();
                            out.println("<groupe>" + idGroupe + "</groupe>");
                        }
                        out.println("</liste_groupe>");
    }   catch (ParseException ex) {
            Logger.getLogger(ServletGroupe.class.getName()).log(Level.SEVERE, null, ex);
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
