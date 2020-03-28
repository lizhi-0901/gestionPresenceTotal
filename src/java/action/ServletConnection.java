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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import metier.Personnel;

/**
 *
 * @author lizhiwang
 */
public class ServletConnection extends HttpServlet {
     protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
	    String identifiant = request.getParameter("lg_username");
            identifiant =identifiant.trim();
            String mdp_p = request.getParameter("lg_password");
            mdp_p=mdp_p.trim();
            System.out.println("identifiant"+identifiant);
            Personnel p = bd.connection(identifiant);
            if(p==null){
                request.setAttribute("msg_erreur", "identifiant n'existe pas !");
		request.getRequestDispatcher("login").forward(request, response);
            }else{
                    
                    String mdp =  p.getMotDePasse();
                    System.out.println("mdp "+mdp);		 
                    String type = bd.connection(identifiant).getType();
                    System.out.println("type "+type);	
                    HttpSession session = request.getSession(true);
                    session.setAttribute("nometudiant", p.getNom());
                    session.setAttribute("prenometudiant", p.getPrenom());
                    session.setAttribute("idetudiant",identifiant );
        //            String mdp="123456";
        //            String type ="Enseignat";
                      
                            if(! mdp_p.equals(mdp))
					{
                                            
                                            
					request.setAttribute("msg_erreur", "Mot de passe erroné !");
					request.getRequestDispatcher("login").forward(request, response);
					}
                                 else
                                        //System.out.println("mot de pass correcte");
					{
					switch(type)
						{
						case "Etudiant": 
							request.getRequestDispatcher("etudiantnav").forward(request, response);
							break;
						case "Enseignant":
                                                        //System.out.println("=============================");
                                                        request.getRequestDispatcher("getionE").forward(request, response);
							break;
                                                case "Responsable":
                                                        request.getRequestDispatcher("GestionEnseignant").forward(request, response);
							break;       
                                                case "Gestionnaire":
                                                        request.getRequestDispatcher("GestionnairePage").forward(request, response);
							break;
						default:
						}
					}
                        }
                    }
            
			
        

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
