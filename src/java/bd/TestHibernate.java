/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;
import bd.bd;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import metier.Affecter;
import metier.AffecterId;
import metier.Creneau;
import metier.Matiere;
import metier.Personnel;

import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author lizhiwang
 */

public class TestHibernate {
     public static void main (String[] args) throws ParseException{
//         Creneau cre = bd.getCreneau("2020-02-11", "conference",840);
//         String idCreneau=cre.getIdCreneau();
//         System.out.println(idCreneau);
         bd.EnregistereSaisirheure("PJ20200327","21509151", "absent");
         
}
}

