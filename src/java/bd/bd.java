package bd;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.transaction.Transactional;
import metier.Affecter;
import metier.AffecterId;
import metier.Creneau;
import metier.Groupe;
import metier.Matiere;
import metier.Periode;
import metier.Personnel;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Transaction;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lizhiwang
 */
public class bd {
    

        static Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        static Transaction transaction = null;
        
//
//	/*----- Données de connexion -----*/
	private static final String URL ="jdbc:mysql://localhost:3307/db_21509151";
	private static final String LOGIN = "21509151";
	private static final String PASSWORD = "Q02NM0";
        
        private static Connection cx = null;
    
//        static SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
    
     /**
      * c'est une function qui prends en entree comme identifiant 
      * et retourne mot de pass
      * @param identifiant
      * @return 
      */
        
        /**
	 * Crée la connexion avec la base de données.
	 */
    private static void connexion() throws ClassNotFoundException, SQLException
            {
            /*----- Chargement du pilote pour la BD -----*/
            try {
                    Class.forName("com.mysql.jdbc.Driver");
                    }
            catch (ClassNotFoundException ex)
                    {
                    throw new ClassNotFoundException("Exception connexion() : Pilote MySql introuvable - " + ex.getMessage());
                    }

            /*----- Ouverture de la connexion -----*/
            try {
                    bd.cx = DriverManager.getConnection(URL,LOGIN,PASSWORD);
                    }
            catch (SQLException ex)
                    {
                    throw new SQLException("Exception connexion() : Problème de connexion à la base de données - " + ex.getMessage());
                    }
            }
     
    public static Personnel connection(String identifiant){

          if(transaction==null){
            transaction  = session.beginTransaction();
        }
          Personnel p=new Personnel();
          try{
              p = (Personnel) session.get(Personnel.class, identifiant);
              return p;
          }catch(HibernateException hibernateEx){
              try {
                transaction.rollback();
            } catch(RuntimeException runtimeEx){
                System.err.printf("Couldn’t Roll Back Transaction", runtimeEx);
                p= null;
            }
          }
//       
            return p;

     
    }
    
     /**
     * 
     * @param libelleFormation
     * @param idEnseignant
     * @return afficher la liste de formation qui est relie avec ce formation et enseignant.
     */

   
          
    public static List<Matiere> getMatieres(String libelleFormation,String idEnseignant){
        if(transaction==null){
            transaction=session.beginTransaction();
        } 
        List<Matiere> listMatieres=new ArrayList<>();
        try{
         listMatieres =session.createSQLQuery( "select  m.libelleMatiere,m.idFormation "+ 
                                                             "from Matiere m, Formation f,Enseigner e "+
                                                             "where f.idFormation =m.idFormation "+
                                                             "and e.libelleMatiere=m.libelleMatiere "+
                                                             "and e.idPersonne="+"'"+idEnseignant+"'"+
                                                             "and f.libelleFormation=" +"'"+libelleFormation+"'").list();
           
        }catch(HibernateException hibernateEx){
              try {
                transaction.rollback();
            } catch(RuntimeException runtimeEx){
                System.err.printf("Couldn’t Roll Back Transaction", runtimeEx);
                listMatieres= null;
            }
    }
    return listMatieres;
    }

    
    public static void getEnseignant(String idPersonne){
        if(transaction==null){
            transaction=session.beginTransaction();
        }
        Personnel e=(Personnel)session.load(Personnel.class, idPersonne);
        System.out.println(e.getIdPersonne());
        
    }
    

    /**
     * @param idGroupe
     * @return retourne la liste etudiant qui est dans ce group TD
     */
    public static List<Personnel> getEtudiants(String idGroupe){
        if(transaction==null){
            transaction  = session.beginTransaction();
        } 
        List<Personnel> listEtudiants =new ArrayList<>();
        try{
            Query query=session.createQuery("select new metier.Personnel(p.idPersonne,p.nom,p.prenom,p.photo) "+
                                        "from Personnel p inner join p.groupes pg, Groupe g "+
                                        "where g.idGroupe=:idGroupe "+
                                        "and pg.idGroupe=g.idGroupe ");
        
            query.setParameter("idGroupe", idGroupe);
            listEtudiants=query.list();
            
        }catch(HibernateException hibernateEx){
              try {
                transaction.rollback();
            } catch(RuntimeException runtimeEx){
                System.err.printf("Couldn’t Roll Back Transaction", runtimeEx);
                listEtudiants= null;
            }
    }

     return listEtudiants;
     
    }
    
    
    /**
     * c'est une function qui retourne une groupe qui prend entree comme date heuredebut et matiere
     * @param date
     * @param heureDeb
     * @param libelleMatiere
     * @return 
     */

    public static List<Groupe> getGroupe(Date date,int heureDeb, String libelleMatiere) throws ParseException{
        if(transaction==null){
            transaction  = session.beginTransaction();
        }

        SimpleDateFormat df =new SimpleDateFormat("yyyy-mm-dd");
        String d=df.format(date);
        Query query=session.createQuery("select new metier.Groupe(gs.idGroupe,gs.nomGroupe,gs.typeGroupe) " +
                                        "from Creneau c join c.groupes gs,Creneau c "+
                                        "where c.dateDeb=:date "+
                                        "and c.heureDeb=:heure "+
                                        "and c.matiere.libelleMatiere=:libelle");
        query.setParameter("date", d);
        query.setParameter("heure", heureDeb);
        query.setParameter("libelle", libelleMatiere);

        List<Groupe> listGroupe=query.list();
        
        return listGroupe;
    }
     



     public static List<Creneau> getIdCreneau(Date date,int heureDeb, String libelleMatiere)throws ParseException{
        if(transaction==null){
            transaction=session.beginTransaction();
        } 
        SimpleDateFormat df =new SimpleDateFormat("yyyy-mm-dd");
        String d=df.format(date);
        
        Query query=session.createQuery("select new metier.Creneau(c.idCreneau) "+
                                        "from Creneau c "+
                                        "where c.dateDeb=:date "+
                                        "and c.heureDeb=:heure "+
                                        "and c.matiere.libelleMatiere=:libelle ");
        
        query.setParameter("date", d);
        query.setParameter("heure", heureDeb);
        query.setParameter("libelle", libelleMatiere);
        
        List<Creneau> idCreneau=query.list();
        return idCreneau;
     };
     

     public static String getInitialeMatiere(String nomMatiere){
          if(transaction==null){
            transaction=session.beginTransaction();
          }
        Matiere m=(Matiere)session.load(Matiere.class, nomMatiere);
        System.out.println(m.getInitiale());
        String str=m.getInitiale();
        
        return str;
     }
     
     public static String creationIdCreneau(String libelleMatiere,String date,int heureDeb,int duree) throws ParseException{
            
            String dateString=date.substring(0, 4)+date.substring(5, 7)+date.substring(8);
            String heureString=String.valueOf(heureDeb);
            String dureeString=String.valueOf(duree);
            
            String initiale=bd.getInitialeMatiere(libelleMatiere);
            
            String idCreneau=initiale+dateString+heureString+dureeString;
         return idCreneau;
     }
     

     public static void creationCreneau(String idCreneau,String date,int heureDeb,int duree,String nomCreneau,String idEnseignant,String typeCours) throws ParseException{

         session=null;
//          if(transaction==null){
                session=HibernateUtil.getSessionFactory().openSession();
                transaction=session.beginTransaction();

        Creneau c=new Creneau();
        c.setIdCreneau(idCreneau);
        c.setDateDeb(date);
        c.setHeureDeb(heureDeb);
        c.setDuree(duree);
        c.setNomCreneau(nomCreneau);
        c.setTypeActivite(typeCours);
        c.setEnseignant(idEnseignant);

        session.save(c);
        transaction.commit();
     }
     
     public static void EnregistrerEtat(String idEtudiant, String idCreneau, String etat){
        session=null;

        session=HibernateUtil.getSessionFactory().openSession();
        transaction=session.beginTransaction();
            
            AffecterId id=new AffecterId();
            id.setIdCreneau(idCreneau);
            id.setIdPersonne(idEtudiant);
            Affecter affecter= new Affecter();
            affecter.setId(id);
            affecter.setEtatPresence(etat);
            session.merge(affecter);
            transaction.commit();
    }
     
    
    

    
    public static List<Creneau> getHeurePresent(String idetudiant,String date,String etatPresence){
         if(transaction==null){
            transaction  = session.beginTransaction();
        }
         Query query=session.createSQLQuery("select  c.dateDeb,sum(duree),a.idCreneau "+
                                                          "from Creneau c,Affecter a "+
                                                          "where c.idCreneau=a.idCreneau "+
                                                          "and a.etatPresence=:etat "+
                                                          "and a.idPersonne=:id "+
                                                          "and c.dateDeb like :date "+        
                                                          "group by c.dateDeb "+
                                                          "order by c.dateDeb ");
        query.setParameter("id", idetudiant);
        query.setParameter("date", "%"+date+"%");
        query.setParameter("etat", etatPresence);
        List<Creneau> listcreneau =query.list();
        return listcreneau;
    }
    
    public static List<Creneau> getHeurePresentapres(String idetudiant,String date,String etatPresence){
         if(transaction==null){
            transaction  = session.beginTransaction();
        }
         Query query=session.createSQLQuery("select  c.dateDeb,sum(duree),a.idCreneau "+
                                                          "from Creneau c,Affecter a "+
                                                          "where c.idCreneau=a.idCreneau "+
                                                          "and a.etatPresence=:etat "+
                                                          "and a.idPersonne=:id "+
                                                          "and c.dateDeb like :date "+
                                                          "and c.heureDeb> 720 "+
                                                          "group by c.dateDeb "+
                                                          "order by c.dateDeb ");
        query.setParameter("id", idetudiant);
        query.setParameter("date", "%"+date+"%");
        query.setParameter("etat", etatPresence);
        List<Creneau> listcreneau =query.list();
        return listcreneau;
    }
    
    public static List<Creneau> getHeurePresentmatin(String idetudiant,String date,String etatPresence){
         if(transaction==null){
            transaction  = session.beginTransaction();
        }
         Query query=session.createSQLQuery("select  c.dateDeb,sum(duree),a.idCreneau "+
                                                          "from Creneau c,Affecter a "+
                                                          "where c.idCreneau=a.idCreneau "+
                                                          "and a.etatPresence=:etat "+
                                                          "and a.idPersonne=:id "+
                                                          "and c.dateDeb like :date "+
                                                          "and c.heureDeb< 630 "+
                                                          "group by c.dateDeb "+
                                                          "order by c.dateDeb ");
        query.setParameter("id", idetudiant);
        query.setParameter("date", "%"+date+"%");
        query.setParameter("etat", etatPresence);
        List<Creneau> listcreneau =query.list();
        return listcreneau;
    }
    
    
    /**
     * 
     * @param idetudiant
     * @param date
     * @return 
     */
    public static List<String> getHeurePresentidCreneau(String idetudiant,String date){
        if(transaction==null){
            transaction  = session.beginTransaction();
        }
         Query query=session.createSQLQuery("select a.idCreneau "+
                                                          "from Creneau c,Affecter a "+
                                                          "where c.idCreneau=a.idCreneau "+
                                                          "and a.etatPresence= 'present' "+
                                                          "and a.idPersonne=:id "+
                                                          "and c.dateDeb like :date "+        
                                                          "group by c.dateDeb,a.idCreneau");
        query.setParameter("id", idetudiant);
        query.setParameter("date", "%"+date+"%");
        List<String> listcreneau =query.list();
        return listcreneau;
       
    }
    
    
    
    
    
    public static Personnel getEtudiantinfo(String idetudiant){
        if(transaction==null){
            transaction  = session.beginTransaction();
        }
        
        Personnel etu =(Personnel)session.get(Personnel.class, idetudiant);
        return etu;
    }
    
    public static String existEnseignant (String nom,String prenom,String type) throws ClassNotFoundException, SQLException
		{
		/*----- Création de la connexion à la base de données -----*/
		if (bd.cx == null)
			bd.connexion();

		/*----- Requête SQL -----*/
		String sql = "SELECT nom,prenom FROM Personnel WHERE nom=? AND prenom=? AND type=?";

		/*----- Ouverture de l'espace de requête -----*/
		try (PreparedStatement st = bd.cx.prepareStatement(sql))
			{
			/*----- Exécution de la requête -----*/
			st.setString(1, nom);
                        st.setString(2, prenom);
                        st.setString(3,type);
			try (ResultSet rs = st.executeQuery())
				{
				/*----- Lecture du contenu du ResultSet -----*/
				if (rs.next())
					return "true";
				else
					return "false";
				}
			}
		catch (SQLException ex)
			{
			throw new SQLException("Exception existPersonne() : Problème SQL - " + ex.getMessage());
			}
		}
    
    
    /**
     * Fonction permet de consulter les infos d'un enseignant.
     * @param nom
     * @param prenom
     * @return 
     */
    public static List<Personnel> consulterEnseignant(String nom,String prenom){
        if(transaction==null){
            transaction  = session.beginTransaction();
        }

        List list=session.createCriteria(Personnel.class)
                                .add(Restrictions.like("nom", nom))
                                .add(Restrictions.like("prenom", prenom))
                                .add(Restrictions.or(Restrictions.like("type","enseignant"),Restrictions.like("type","responsable"))).list();
        return list;       
    }
    /**
     * Fonction permet de consulter les infos d'un etudiant.
     * @param nom
     * @param prenom
     * @return 
     */
    public static List<Personnel> consulterEtudiant(String nom,String prenom){
        if(transaction==null){
            transaction  = session.beginTransaction();
        }

        List list=session.createCriteria(Personnel.class)
                                .add(Restrictions.like("nom", nom))
                                .add(Restrictions.like("prenom", prenom))
                                .add(Restrictions.like("type","etudiant")).list();

        return list;       
    }
    
    public static List<Matiere> consulterMatiere(String libelleMatiere){
        if(transaction==null){
            transaction  = session.beginTransaction();
        }
        
         List list=session.createCriteria(Matiere.class)
                                .add(Restrictions.like("libelleMatiere",libelleMatiere )).list();

        return list;
    }
    
    
    
    
    
    /**
     * Fonction permet d'ajouter un enseignant
     * @param nom
     * @param prenom
     * @param mail
     * @param numTel 
     */
    public static void ajouterEnseignant(String nom,String prenom,String mail,String numTel){
            session=null;
                session=HibernateUtil.getSessionFactory().openSession();
                transaction=session.beginTransaction();
            String idP=nom+prenom;
            Personnel p=new Personnel();
            p.setIdPersonne(idP);
            p.setNom(nom);
            p.setPrenom(prenom);
            p.setAdresseMail(mail);
            p.setNumTel(numTel);
            p.setType("Enseignant");
            p.setMotDePasse("123456");
            
            System.out.println(p.getNom());
            System.out.println(p.getPrenom());
            System.out.println(p.getAdresseMail());
            System.out.println(p.getNumTel());
            System.out.println(p.getType());
            
            session.save(p);
            transaction.commit();
        
    }
    /**
     * Fonction permet d'ajouter un etudiant 
     * @param nom
     * @param prenom
     * @param mail
     * @param numTel 
     * @param formation 
     * @param Entreprise 
     * @param numTelE 
     * @param mailE 
     */
    public static void ajouterEtudiant(String nom,String prenom,String mail,String numTel,String formation,String Entreprise,String numTelE,String mailE){
            session=null;
                session=HibernateUtil.getSessionFactory().openSession();
                transaction=session.beginTransaction();
            String idP=nom+prenom;
            Personnel p=new Personnel();
            p.setIdPersonne(idP);
            p.setNom(nom);
            p.setPrenom(prenom);
            p.setAdresseMail(mail);
            p.setNumTel(numTel);
            p.setType("Etudiant");
            p.setMotDePasse("123456");
            p.setTypeformation(formation);
            p.setEntreprise(Entreprise);
            p.setContactTel(numTelE);
            p.setContactMail(mailE);
            
            System.out.println(p.getNom());
            System.out.println(p.getPrenom());
            System.out.println(p.getAdresseMail());
            System.out.println(p.getNumTel());
            System.out.println(p.getType());
            
            session.save(p);
            transaction.commit();
        
    }
    
    
//    public static void ajouterMatiere(String libelleMatiere,String idFormation){
//        session=null;
//            try {
//                session = HibernateUtil.getSessionFactory().getCurrentSession();
//                transaction  = session.beginTransaction();
//                Query query =session.createSQLQuery("INSERT INTO Matiere(libelleMatiere,codeUE,idFormation,initiale) VALUES(:libelleMatiere,:codeUE,:idFormation,:initiale) ");
//                
//                String initiale=libelleMatiere.substring(0, 3);
//                query.setParameter("libelleMatiere", libelleMatiere);
//                
//                String codeUE="IPM201901";
//                query.setParameter("codeUE",codeUE );
//                query.setParameter("idFormation", idFormation);
//                query.setParameter("initiale", initiale);
//                query.executeUpdate();
//                transaction.commit();
//            }
//            catch (RuntimeException e) {
//                transaction.rollback();
//                throw e;
//            }
//    }
    
//     public static void ajouterPeriode(String dateDeb,String dateFin,String,String typePeriode){
//        session=null;
//            try {
//                session = HibernateUtil.getSessionFactory().getCurrentSession();
//                transaction  = session.beginTransaction();
//                Query query =session.createSQLQuery("INSERT INTO Matiere(libelleMatiere,codeUE,idFormation,initiale) VALUES(:libelleMatiere,:codeUE,:idFormation,:initiale) ");
//                
//                String initiale=libelleMatiere.substring(0, 4);
//                query.setParameter("libelleMatiere", libelleMatiere);
//                
//                String codeUE="IPM201901";
//                query.setParameter("codeUE",codeUE );
//                query.setParameter("idFormation", idFormation);
//                query.setParameter("initiale", initiale);
//                query.executeUpdate();
//                transaction.commit();
//            }
//            catch (RuntimeException e) {
//                transaction.rollback();
//                throw e;
//            }
//    }
            
    public static void SupprimerEnseignant(String nom,String prenom){
        session=null;
        session=HibernateUtil.getSessionFactory().openSession();
        transaction=session.beginTransaction();
        
        String idP=nom+prenom;
        Personnel p=(Personnel)session.get(Personnel.class, idP);
        System.out.println("Supprimer   "+p.getNom());
        
        session.delete(p);
        transaction.commit();
        
    }
    
    public static void ModifierEnseignant(String nom,String prenom,String numTel,String eMail){
        session=null;
        session=HibernateUtil.getSessionFactory().getCurrentSession();
        transaction=session.beginTransaction();
        
        Query query=session.createQuery("update Personnel p set p.numTel=:numTel, p.adresseMail=:eMail "+
                                        "where p.nom=:nom "+
                                        "and p.prenom=:prenom ");
        query.setParameter("nom", nom);
        query.setParameter("prenom", prenom);
        query.setParameter("numTel", numTel);
        query.setParameter("eMail", eMail);
        
        query.executeUpdate();    
        transaction.commit();
    }
    
    public static void modifierEtudiant(String nom,String prenom,String mail,String numTel,String formation,String entreprise,String numTelE,String mailE){
        session=null;
        session=HibernateUtil.getSessionFactory().openSession();
        transaction=session.beginTransaction();
        
        Query query=session.createQuery("update Personnel p set p.numTel=:numTel, p.adresseMail=:eMail, p.typeformation=:typeformation, "+
                                        "p.entreprise=:entreprise,p.contactTel=:numTelE,p.contactMail=:mailE  "+
                                        "where p.nom=:nom "+
                                        "and p.prenom=:prenom ");
        query.setParameter("nom", nom);
        query.setParameter("prenom", prenom);
        query.setParameter("numTel", numTel);
        query.setParameter("eMail", mail);
        query.setParameter("typeformation",formation);
        query.setParameter("entreprise",entreprise);
        query.setParameter("numTelE",numTelE);
        query.setParameter("mailE",mailE);
        
        
        query.executeUpdate();    
        transaction.commit();
        
    }

    

            
            public static void Valide(String etatValide, String idetudiant, String idCreneau){
                if(transaction==null){
                transaction  = session.beginTransaction();
                    }
                    try {
                      session = HibernateUtil.getSessionFactory().getCurrentSession();
                      transaction  = session.beginTransaction();

                      AffecterId id=new AffecterId();
                      id.setIdCreneau(idCreneau);
                      id.setIdPersonne(idetudiant);
                      Affecter affecter =(Affecter) session.get(Affecter.class, id);
                      affecter.setEtatValide(etatValide);
                      transaction.commit();
                    }
                    catch (RuntimeException e) {
                        transaction.rollback();
                        throw e;
                    }
                  } 
            
            public static Creneau getCreneau(String date,String activ, int heureDeb){
                if(transaction==null){
                  transaction  = session.beginTransaction();
                        }
                         
                    Query query=session.createQuery("select new metier.Creneau(c.idCreneau, c.matiere, c.nomCreneau, c.salle, c.enseignant, c.dateDeb, c.heureDeb, c.duree, c.typeActivite, c.commantaire) " +
                                                    "from Creneau c "+
                                                    "where c.dateDeb=:date "+
                                                    "and c.heureDeb=:heureDeb "+
                                                    "and c.typeActivite=:type");
                    query.setParameter("date", date);
                    query.setParameter("heureDeb", heureDeb);
                    query.setParameter("type", activ);
                    query.setMaxResults(1);
		    Creneau cre = (Creneau) query.uniqueResult();
                    return cre;
            }
            
            
            public static void EnregistereSaisirheure(String idCreneau,String idEtudiant,String etatPresence){
                
                 if(transaction==null){
                        transaction  = session.beginTransaction();
                                     }
                
                    try {
                      //session = HibernateUtil.getSessionFactory().getCurrentSession();
                      //transaction  = session.beginTransaction();
                      Query query =session.createSQLQuery("INSERT INTO Affecter(idPersonne,idCreneau,etatPresence) VALUES(:idEtudiant,:idCreneau,:etatPresence) ");
                      query.setParameter("idEtudiant", idEtudiant);
                       query.setParameter("idCreneau", idCreneau);
                        query.setParameter("etatPresence", etatPresence);
                        query.executeUpdate();
                        transaction.commit();
                    }
                    catch (Exception e) {
                        System.out.println("erruer"+e);
                        throw e;
                    }

                  } 
          
          public static List<Creneau> getHeureE(String idetudiant,String date){
         if(transaction==null){
            transaction  = session.beginTransaction();
        }
         Query query=session.createSQLQuery("select  c.dateDeb,sum(duree),a.idCreneau "+
                                                          "from Creneau c,Affecter a "+
                                                          "where c.idCreneau=a.idCreneau "+
                                                          "and c.typeActivite in('Cours','TD','Exam') "+
                                                          "and a.idPersonne=:id "+
                                                          "and c.dateDeb like :date "+
                                                          "group by c.dateDeb "+
                                                          "order by c.dateDeb ");
        query.setParameter("id", idetudiant);
        query.setParameter("date", "%"+date+"%");
       
        List<Creneau> listcreneau =query.list();
        return listcreneau;
        }
          
         public static List<Creneau> getHeureEmatin(String idetudiant,String date){
         if(transaction==null){
            transaction  = session.beginTransaction();
        }
         Query query=session.createSQLQuery("select  c.dateDeb,sum(duree),a.idCreneau "+
                                                          "from Creneau c,Affecter a "+
                                                          "where c.idCreneau=a.idCreneau "+
                                                          "and c.typeActivite in('Cours','TD','Exam') "+
                                                          "and a.idPersonne=:id "+
                                                          "and c.dateDeb like :date "+
                                                          "and c.heureDeb< 630 "+
                                                          "group by c.dateDeb "+
                                                          "order by c.dateDeb ");
        query.setParameter("id", idetudiant);
        query.setParameter("date", "%"+date+"%");
       
        List<Creneau> listcreneau =query.list();
        return listcreneau;
        } 
          
         public static List<Creneau> getHeureEApre(String idetudiant,String date){
         if(transaction==null){
            transaction  = session.beginTransaction();
        }
         Query query=session.createSQLQuery("select  c.dateDeb,sum(duree),a.idCreneau "+
                                                          "from Creneau c,Affecter a "+
                                                          "where c.idCreneau=a.idCreneau "+
                                                          "and c.typeActivite in('Cours','TD','Exam') "+
                                                          "and a.idPersonne=:id "+
                                                          "and c.dateDeb like :date "+
                                                          "and c.heureDeb> 720 "+
                                                          "group by c.dateDeb "+
                                                          "order by c.dateDeb ");
        query.setParameter("id", idetudiant);
        query.setParameter("date", "%"+date+"%");
       
        List<Creneau> listcreneau =query.list();
        return listcreneau;
        }
         public static List<Creneau> getHeureD(String idetudiant,String date){
         if(transaction==null){
            transaction  = session.beginTransaction();
        }
         Query query=session.createSQLQuery("select  c.dateDeb,sum(duree),a.idCreneau "+
                                                          "from Creneau c,Affecter a "+
                                                          "where c.idCreneau=a.idCreneau "+
                                                          "and c.typeActivite in('projet','conference') "+
                                                          "and a.idPersonne=:id "+
                                                          "and c.dateDeb like :date "+        
                                                          "group by c.dateDeb "+
                                                          "order by c.dateDeb ");
        query.setParameter("id", idetudiant);
        query.setParameter("date", "%"+date+"%");
       
        List<Creneau> listcreneau =query.list();
        return listcreneau;
        }
        
         public static List<Creneau> getHeureDmatin(String idetudiant,String date){
         if(transaction==null){
            transaction  = session.beginTransaction();
        }
         Query query=session.createSQLQuery("select  c.dateDeb,sum(duree),a.idCreneau "+
                                                          "from Creneau c,Affecter a "+
                                                          "where c.idCreneau=a.idCreneau "+
                                                          "and c.typeActivite in('projet','conference') "+
                                                          "and a.idPersonne=:id "+
                                                          "and c.dateDeb like :date "+
                                                          "and c.heureDeb< 630 "+
                                                          "group by c.dateDeb "+
                                                          "order by c.dateDeb ");
        query.setParameter("id", idetudiant);
        query.setParameter("date", "%"+date+"%");
       
        List<Creneau> listcreneau =query.list();
        return listcreneau;
        }
         
         public static List<Creneau> getHeureDApres(String idetudiant,String date){
         if(transaction==null){
            transaction  = session.beginTransaction();
        }
         Query query=session.createSQLQuery("select  c.dateDeb,sum(duree),a.idCreneau "+
                                                          "from Creneau c,Affecter a "+
                                                          "where c.idCreneau=a.idCreneau "+
                                                          "and c.typeActivite in('projet','conference') "+
                                                          "and a.idPersonne=:id "+
                                                          "and c.dateDeb like :date "+
                                                          "and c.heureDeb> 720 "+
                                                          "group by c.dateDeb "+
                                                          "order by c.dateDeb ");
        query.setParameter("id", idetudiant);
        query.setParameter("date", "%"+date+"%");
       
        List<Creneau> listcreneau =query.list();
        return listcreneau;
        }
         
        public static String ajouterMatiere(String libelleMatiere,String idFormation)throws ClassNotFoundException, SQLException{
		{
		/*----- Création de la connexion à la base de données -----*/
		if (bd.cx == null)
			bd.connexion();

		/*----- Requête SQL -----*/
		String sql = "INSERT INTO Matiere VALUES (?,?,?,?)";
                String codeUE="IPM201901";
                String initiale=libelleMatiere.substring(0, 3);
		/*----- Ouverture de l'espace de requête -----*/
		try (PreparedStatement st = bd.cx.prepareStatement(sql))
			{
			/*----- Exécution de la requête -----*/
			st.setString(1, libelleMatiere);
                        st.setString(2, codeUE);
                        st.setString(3, idFormation);
                        st.setString(4, initiale);
			return st.executeUpdate() + "";
			}
		catch (SQLException ex)
			{
			throw new SQLException("Exception addMot() : Problème SQL - " + ex.getMessage());
			}
		}
    }
    
    public static String supprimerMatiere (String mot) throws ClassNotFoundException, SQLException
		{
		/*----- Création de la connexion à la base de données -----*/
		if (bd.cx == null)
			bd.connexion();

		/*----- Requête SQL -----*/
		String sql = "DELETE FROM Matiere WHERE libelleMatiere = ?";

		try (PreparedStatement st = bd.cx.prepareStatement(sql))
			{
			/*----- Exécution de la requête -----*/
			st.setString(1, mot);
			return st.executeUpdate() + "";
			}
		catch (SQLException ex)
			{
			throw new SQLException("Exception supprMot() : Problème SQL - " + ex.getMessage());
			}
		}
    
    public static void ajouterPeriode (String dateDeb, String dateFin, String typePeriode){
            session=null;
            session=HibernateUtil.getSessionFactory().openSession();
            transaction=session.beginTransaction();
            Periode p=new Periode();
            
            p.setDateDeb(dateDeb);
            p.setDateFin(dateFin);
            p.setTypePeriode(typePeriode);
            System.out.println(p.getTypePeriode());
            System.out.println(p.getDateFin());
            System.out.println(p.getDateDeb());

            session.save(p);
            transaction.commit();
       }
    
    public static void supprimerPeriode (String dateDeb, String dateFin) {
        session=HibernateUtil.getSessionFactory().getCurrentSession();
        transaction=session.beginTransaction();
           
          
           Query query =session.createSQLQuery("Delete from Periode "+
                                               "Where dateDeb=:dated "+
                                               "and dateFin=:datef ");
           
            
            query.setParameter("dated", dateDeb);
            query.setParameter("datef", dateFin);
            query.executeUpdate();
        
            transaction.commit();
      } 
      public static String existCours (String nomC) throws ClassNotFoundException, SQLException
		{
		/*----- Création de la connexion à la base de données -----*/
		if (bd.cx == null)
			bd.connexion();

		/*----- Requête SQL -----*/
		String sql = "SELECT libelleMatiere FROM Matiere WHERE libelleMatiere=?";

		/*----- Ouverture de l'espace de requête -----*/
		try (PreparedStatement st = bd.cx.prepareStatement(sql))
			{
			/*----- Exécution de la requête -----*/
			st.setString(1, nomC);
			try (ResultSet rs = st.executeQuery())
				{
				/*----- Lecture du contenu du ResultSet -----*/
				if (rs.next())
					return "true";
				else
					return "false";
				}
			}
		catch (SQLException ex)
			{
			throw new SQLException("Exception existPersonne() : Problème SQL - " + ex.getMessage());
			}
		}
        public static List<Periode>  consulterPeriode (String dateDeb, String dateFin){
             if(transaction==null){
            transaction  = session.beginTransaction();
        }

        List list=session.createCriteria(Periode.class)
                                .add(Restrictions.like("dateDeb", dateDeb))
                                .add(Restrictions.like("dateFin", dateFin)).list();

        return list;       
       }          
            
        public static List<Matiere> getAllMatieres(String libelleFormation){
        if(transaction==null){
            transaction=session.beginTransaction();
        } 
        List<Matiere> listMatieres=new ArrayList<>();
        try{
         listMatieres =session.createSQLQuery( "select  m.libelleMatiere,m.idFormation "+ 
                                                             "from Matiere m, Formation f "+
                                                             "where f.idFormation =m.idFormation "+
                                                             "and f.libelleFormation=" +"'"+libelleFormation+"'").list();
           
        }catch(HibernateException hibernateEx){
              try {
                transaction.rollback();
            } catch(RuntimeException runtimeEx){
                System.err.printf("Couldn’t Roll Back Transaction", runtimeEx);
                listMatieres= null;
            }
    }
    return listMatieres;
    }    

	/*----------------------------*/
	/* Programme principal (test) */
	/*----------------------------*/
    
        private static void affichage (List l)
		{
		Iterator e = l.iterator();
		while (e.hasNext())
			{
			Object[] tab_obj = ((Object[]) e.next());

			for (Object obj : tab_obj)
				System.out.print(obj + " ");

			System.out.println("");
			}
		}
        /**
         * 
         * @param l
         * @return 
         */
        public  static ArrayList<String> output (List l,int i)
		{
		Iterator e = l.iterator();
                Object[] tab_obj = null;
                ArrayList<String> str= new ArrayList<>(); 
		while (e.hasNext())
			{
			tab_obj = ((Object[]) e.next());
                        str.add(String.valueOf(tab_obj[i])); 
                        }
                        return str;

		}
                
                
                
	public static void main (String[] s) throws ParseException, ClassNotFoundException, SQLException

		{   
                    List<Periode> lp;
                    lp=bd.consulterPeriode("2019-09-02", "2019-10-20");
                    for(Periode p:lp){
                        String type=p.getTypePeriode();
                            System.out.println(type);
                
            } 
                  }
}
