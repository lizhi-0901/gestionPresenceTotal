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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
public class Servletetufeuille extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        response.setContentType("application/xml;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		try (PrintWriter out = response.getWriter())
			{
			/*----- Ecriture de la page XML -----*/
			out.println("<?xml version=\"1.0\"?>");
                        DecimalFormat df=new DecimalFormat("0.0");
                        
                        
                        
                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        String idetudiant=(String)request.getSession().getAttribute("idetudiant");
                        Personnel p =bd.getEtudiantinfo(idetudiant);
                        String nom =p.getNom();
                        String prenom =p.getPrenom();
                        String photo =p.getPhoto();
                        List<String> listCreneau= new ArrayList<>();    
                        
                       
                        String mois = request.getParameter("mois");
                        String annee =request.getParameter("annee");
                        String anneemois =""; 
                        
                        if(annee.equals(null)){
                            System.out.println("not select year");
                             anneemois = Integer.toString(year)+"-"+mois;
                        }else{
                             anneemois =annee+"-"+mois;
                        }
                        
                        
                        out.println("<liste_etatpresence>");
                        out.println("<nom>" + nom + "</nom>");
                        out.println("<prenom>" + prenom + "</prenom>");
                        out.println("<photo>" + photo + "</photo>"); 
//                        System.out.println(idetudiant);
                        //heureE
                        List<Creneau> listem =bd.getHeureEmatin(idetudiant,anneemois);
                        List<Creneau> listea =bd.getHeureEApre(idetudiant,anneemois);
                        List<Creneau> liste =bd.getHeureE(idetudiant,anneemois);
                        //absent
                        List<Creneau> abslistm =bd.getHeurePresentmatin(idetudiant,anneemois,"absent");
                        List<Creneau> abslista =bd.getHeurePresentapres(idetudiant,anneemois,"absent");
                        List<Creneau> abslist =bd.getHeurePresent(idetudiant,anneemois,"absent");
                        //heureD
                        List<Creneau> listdm =bd.getHeureDmatin(idetudiant,anneemois);
                        List<Creneau> listda =bd.getHeureDApres(idetudiant,anneemois);
                        List<Creneau> listd =bd.getHeureD(idetudiant,anneemois);
                        
                        

                        List<String> listm=new ArrayList<>();
                        List<String> lista=new ArrayList<>();
                        List<String> list=new ArrayList<>();
                        //ajouter tous les elements de trois tables dans list
                        util.addlist(listm, bd.output(listem, 0));
                        util.addlist(listm, bd.output(abslistm,0));
                        util.addlist(listm, bd.output(listdm, 0));
                        
                        util.addlist(lista, bd.output(abslista,0));
                        util.addlist(lista, bd.output(listda,0));
                        util.addlist(lista, bd.output(listea,0));
                        
                        
                        util.addlist(list, bd.output(listd,0));
                        util.addlist(list, bd.output(liste,0));
                        util.addlist(list, bd.output(abslist,0));
                        //eliminer les duplicate
                        list=util.removeDuplicate(list);
                        listm=util.removeDuplicate(list);
                        lista=util.removeDuplicate(list);
                        
                        // creation hashmap
                        HashMap<String, String> mapem =new HashMap<>();
                        mapem=util.addMap(listem);
                        HashMap<String, String> mapdm =new HashMap<>();
                        mapdm=util.addMap(listdm);
                        HashMap<String, String> mapabsm =new HashMap<>();
                        mapabsm=util.addMap(abslistm);
                        
                        HashMap<String, String> mapea =new HashMap<>();
                        mapea=util.addMap(listea);
                        HashMap<String, String> mapda =new HashMap<>();
                        mapda=util.addMap(listda);
                        HashMap<String, String> mapabsa =new HashMap<>();
                        mapabsa=util.addMap(abslista);
                        
                        
                        HashMap<String, String> mape =new HashMap<>();
                        mape=util.addMap(liste);
                        HashMap<String, String> mapd =new HashMap<>();
                        mapd=util.addMap(listd);
                        HashMap<String, String> mapabs =new HashMap<>();
                        mapabs=util.addMap(abslist);
                        
                        float fe=0f;
                        for(String value:mape.values()){
                            fe=fe+Float.parseFloat(value)/60;
                        }
                        float fd=0f;
                        for(String value:mapd.values()){
                            fd=fd+Float.parseFloat(value)/60;
                        }
                        float fabs=0f;
                        for(String value:mapabs.values()){
                            fabs=fabs+Float.parseFloat(value)/60;
                        }
                        
                        for(int i=0;i<list.size();i++){
                            out.println("<date>" + list.get(i) + "</date>");
                        }
                        out.println("<E>" + fe + "</E>");
                        out.println("<D>" + fd + "</D>");
                        out.println("<abs>" + fabs + "</abs>");
                        // heuretotal
                        int sizee=liste.size();
                        for(int i=0;i<list.size();i++){
                            
                               if(i<sizee){
                                    String str=list.get(i);
                                    
                                    if((bd.output(liste,0)).contains(str)){
                                        String st=mape.get(str);
                                        int heure=Integer.parseInt(st);
                                        out.println("<Eheuretotal>" + df.format((float)heure/60) + "</Eheuretotal>");
                                        
                                    }
                                    else{
                                        
                                        out.println("<Eheuretotal>" + 0 + "</Eheuretotal>");
                                        sizee++;
                                    }
                                }
                                else{
                                    out.println("<Eheuretotal>" + 0 + "</Eheuretotal>");
                                }
                                
                            }
                        
                        // heureabs
                        int sizeabs=abslist.size();
                        for(int i=0;i<list.size();i++){
                               if(i<sizee){
                                    String str=list.get(i);
                                    if((bd.output(abslist,0)).contains(str)){
                                        String st=mapabs.get(str);
                                        int heure=Integer.parseInt(st);
                                        out.println("<absheuretotal>" + df.format((float)heure/60) + "</absheuretotal>");
                                        
                                    }
                                    else{
                                        out.println("<absheuretotal>" + 0 + "</absheuretotal>");
                                        sizeabs++;
                                    }
                                }
                                else{
                                    out.println("<absheuretotal>" + 0 + "</absheuretotal>");
                                }
                                
                            }
                        
                        
                        // heuredtotal
                        int sized=listd.size();
                        for(int i=0;i<list.size();i++){
                               if(i<sizee){
                                    String str=list.get(i);
                                    if((bd.output(listd,0)).contains(str)){
                                            String st=mapd.get(str);
                                            int heure=Integer.parseInt(st);
                                            out.println("<Dheuretotal>" + df.format((float)heure/60) + "</Dheuretotal>");
                                    }
                                    else{
                                        out.println("<Dheuretotal>" + 0 + "</Dheuretotal>");
                                        sizeabs++;
                                    }
                                }
                                else{
                                    out.println("<Dheuretotal>" + 0 + "</Dheuretotal>");
                                }
                                
                            }
                        
                        //--------------------------------------------------------------------------------------------//
                        
                        
                        
                        
                        
                        
                        
                        // heureematin
                        int sizeem=listem.size();
                        for(int i=0;i<listm.size();i++){
                               if(i<sizeem){
                                    String str=listm.get(i);
                                    if((bd.output(listem,0)).contains(str)){
                                            String st=mapem.get(str);
                                            int heure=Integer.parseInt(st);
                                            out.println("<Eheurematin>" +df.format((float)heure/60) + "</Eheurematin>");
                                        
                                    }
                                    else{
                                        out.println("<Eheurematin>" + 0 + "</Eheurematin>");
                                        sizeem++;
                                    }
                                }
                                else{
                                    out.println("<Eheurematin>" + 0 + "</Eheurematin>");
                                }
                                
                            }
                        // heuredmatin
                        int sizedm=listdm.size();
                        for(int i=0;i<listm.size();i++){
                              if(i<sizedm){
                                    String str=listm.get(i);
                                    if((bd.output(listdm,0)).contains(str)){
                                            String st=mapdm.get(str);
                                            int heure=Integer.parseInt(st);
                                            out.println("<heuredmatin>" + df.format((float)heure/60) + "</heuredmatin>");
                                     }
                                    else{
                                        out.println("<heuredmatin>" + 0 + "</heuredmatin>");
                                        sizedm++;
                                    }
                                }
                                else{
                                    out.println("<heuredmatin>" + 0 + "</heuredmatin>");
                                }
                                
                            }
                        
                        
                        // heureabsmatin
                        int sizeabsm=abslistm.size();
                        for(int i=0;i<listm.size();i++){
                              if(i<sizeabsm){
                                    String str=listm.get(i);
                                    if((bd.output(abslistm,0)).contains(str)){
                                        String st=mapabsm.get(str);
                                            int heure=Integer.parseInt(st);
                                            out.println("<absheurem>" + df.format((float)heure/60)+ "</absheurem>");
                                        
                                    }
                                    else{
                                        out.println("<absheurem>" + 0 + "</absheurem>");
                                        sizeabsm++;
                                    }
                                }
                                else{
                                    
                                    out.println("<absheurem>" + 0 + "</absheurem>");
                                }
                                
                            }
                        
                        
                        //--------------------------------------------------------------------------------//
                        //heureeapresmidi
                        int sizeea=listea.size();
                        for(int i=0;i<lista.size();i++){
                                if(i<sizeea){
                                    String str=lista.get(i);
                                    if((bd.output(listea,0)).contains(str)){
                                            String st=mapea.get(str);
                                            int heure=Integer.parseInt(st);
                                            out.println("<Eheureapres>" + df.format((float)heure/60) + "</Eheureapres>");
                                        
                                    }
                                    else{
                                        out.println("<Eheureapres>" + 0 + "</Eheureapres>");
                                        sizeea++;
                                        }
                                }
                                else{
                                    out.println("<Eheureapres>" + 0 + "</Eheureapres>");
                                }
                                
                            }
                        // heureabsapres
                        int sizeabsa=abslista.size();
                        for(int i=0;i<lista.size();i++){
                              if(i<sizeabsa){
                                    String str=lista.get(i);
                                    if((bd.output(abslista,0)).contains(str)){
                                            String st=mapabsa.get(str);
                                            int heure=Integer.parseInt(st);
                                            out.println("<absheurea>" + df.format((float)heure/60)+ "</absheurea>");
                                        
                                    }
                                    else{
                                        out.println("<absheurea>" + 0 + "</absheurea>");
                                        sizeabsa++;
                                    }
                                }
                                else{
                                    
                                    out.println("<absheurea>" + 0 + "</absheurea>");
                                }
                                
                            }
                        
                        
                        // heuredapres
                        int sizeda=listda.size();
                        for(int i=0;i<lista.size();i++){
                              if(i<sizeda){
                                    String str=lista.get(i);
                                    if((bd.output(listda,0)).contains(str)){
                                            String st=mapda.get(str);
                                            int heure=Integer.parseInt(st);
                                            out.println("<heureda>" + df.format((float)heure/60) + "</heureda>");
                                        
                                    }
                                    else{
                                        out.println("<heureda>" + 0 + "</heureda>");
                                        sizeda++;
                                    }
                                }
                                else{
                                    
                                    out.println("<heureda>" + 0 + "</heureda>");
                                }
                                
                            }
                        
                        //heure presence
//                        for(int i=0;i<max;i++){
//                            int ecart=max-clist.size();
//                            
//                            for(int j=i;j<ecart;j++){
//                                System.out.println("heure"+i+" "+bd.output(clist, 1).get(j));
//                                String str=bd.output(clist, 1).get(j);
//                                out.println("<heure>" + (Integer.parseInt(str)/60) + "</heure>");
//                            }
//                            for(int j=ecart;j<max;j++){
//                                out.println("<heure>" + 0 + "</heure>");
//                            }
//                            
//                           }  
                        
//                        for(int i=0;i<max;i++){
//                            String str=bd.output(retalist, 1).get(i);
//                            if(str==null){
//                                str="0";
//                                out.println("<reheure>" + (Integer.parseInt(str)/60) + "</reheure>");
//                            }else{
//                                out.println("<reheure>" + (Integer.parseInt(str)/60) + "</reheure>");
//                            }
//                            
//                        }  
//                        for(int i=0;i<max;i++){
//                            String str=bd.output(abslist, 1).get(i);
//                            if(str==null){
//                                str="0";
//                                out.println("<absheure>" + (Integer.parseInt(str)/60) + "</absheure>");
//                            }else{
//                                out.println("<absheure>" + (Integer.parseInt(str)/60) + "</absheure>");
//                            }
//                            
//                        }  
//                        for(String str :bd.getHeurePresentidCreneau(idetudiant, anneemois)){
//                            out.println("<idCreneau>" + str + "</idCreneau>");
//                            listCreneau.add(str);
//                        }  
                        HttpSession session = request.getSession(true);
                        session.setAttribute("id", listCreneau);
                        out.println("</liste_etatpresence>");
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
