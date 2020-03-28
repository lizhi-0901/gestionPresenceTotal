/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * affichir les cours selon formation
 * @returns {undefined}
 */        
function getCours ()
        {
        //recuperer la valeur
        var formation = document.getElementById("formation").value;
//        var identifiant=document.getElementById("lg_username").value;
//        alert(identifiant);
	// Objet XMLHttpRequest.
	var xhr = new XMLHttpRequest();

	// Requête au serveur avec les paramètres éventuels.
	xhr.open("GET","ServletCours"+"?formation="+formation);

	// On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
	xhr.onload = function()
		{
		// Si la requête http s'est bien passée.
		if (xhr.status === 200)
			{
			// Elément html que l'on va mettre à jour.
                            var elt = document.getElementById("cours");
                            var tab=xhr.responseXML.getElementsByTagName("matiere")
                            elt.innerHTML="";
                            for ( i=0;i<tab.length;i++)
                            {
                                elt.insertAdjacentHTML("beforeend","<option>"+tab[i].firstChild.nodeValue+"</option>");
                            }
			}
		};
	
	// Envoie de la requête.
	xhr.send();
	//delock inputs        
        CoursDelock();
        
	}       
        
        
    /**
 * affichir les groupe selon cours 
 * @returns {undefined}
 */        
function getGroupe ()
        {
        //recuperer la valeur
        var cours = document.getElementById("cours").value;
        var heureSaisir = parseInt(document.getElementById("heure").value);
        var min=parseInt(document.getElementById("minute").value);
        var heure=heureSaisir*60+min;
        var date  =document.getElementById("datepicker").value;
        //faire l'affichir 
	// Objet XMLHttpRequest.
	var xhr = new XMLHttpRequest();

	// Requête au serveur avec les paramètres éventuels.
        xhr.open("GET","ServletGroupe?cours="+cours+"&heure="+heure+"&date="+date,true);
	// On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
	xhr.onload = function()
		{
		// Si la requête http s'est bien passée.
		if (xhr.status === 200)
			{
			// Elément html que l'on va mettre à jour.
                        var elt = document.getElementById("groupe");
			var tab = xhr.responseXML.getElementsByTagName("groupe");
                        elt.innerHTML="";
                        for ( i=0;i<tab.length;i++)
                            {
                                elt.insertAdjacentHTML("beforeend","<option>"+tab[i].firstChild.nodeValue+"</option>");
                            }
			}
		};
	
	// Envoie de la requête.
	xhr.send();
        
        //delock inputs
        typeCoursDelock();
	}     
    /**
 * affichir les etudiant selon groupe
 * @returns {undefined}
 */        
function getEtudiant ()
        {
        //recuperer la valeur
        var groupe = document.getElementById("groupe").value;
	// Objet XMLHttpRequest.
	var xhr = new XMLHttpRequest();
	// Requête au serveur avec les paramètres éventuels.
	xhr.open("GET","ServletEtudiant"+"?groupe="+groupe);

	// On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
	xhr.onload = function()
		{
		// Si la requête http s'est bien passée.
		if (xhr.status === 200)
			{
                        var initialise = document.getElementById("listEtudiant");
                        initialise.innerHTML="";
                        initialise.insertAdjacentHTML("beforeend","<th>Photo</th>"+
                                                      "<th style=\"display:none;\">Identifiant</th>"+
                                                      "<th>Nom</th>"+
                                                      "<th>Pr&eacutenom</th>"+
                                                      "<th>Etat Pr&eacutesent</th>");

			// Elément html que l'on va mettre à jour.
                        var elt = document.getElementById("listEtudiant");
                        
                        var tabphoto=xhr.responseXML.getElementsByTagName("photo");
			var tabid = xhr.responseXML.getElementsByTagName("id");
                        var tabnom=xhr.responseXML.getElementsByTagName("nom");
                        var tabprenom=xhr.responseXML.getElementsByTagName("prenom");
                        for ( i=0;i<tabid.length;i++)
                            {
                                elt.insertAdjacentHTML("beforeend","<tr id=\"contenu\"><td><img src=\""+tabphoto[i].firstChild.nodeValue+"\"height=\"50\" width=\"50\"alt=\"Photo\"></td>"+
                                                                    "<td style=\"display:none;\">"+tabid[i].firstChild.nodeValue+"</td>"+
                                                                    "<td>"+tabnom[i].firstChild.nodeValue+"</td>"+
                                                                    "<td>"+tabprenom[i].firstChild.nodeValue+"</td>"+
                                                                    " <td><select "+"id=\"etatPre"+(i+1)+
                                                                    "\"><option>Present</option><option>Retard</option><option>Absent</option><option>AbsentJustifie</option></select></td>");
                            };
			};
		};
	
	// Envoie de la requête.
	xhr.send();
        
        //delock inputs
        typeCoursDelock();
	}
        
    function creationCreneau(){
        //recuperer la valeur
        
        var cours = document.getElementById("cours").value;
        //alert(cours);
        var date  =document.getElementById("datepicker").value;
        //alert(date);
        //var heureSaisir = parseInt(document.getElementById("heure").value);
        //var min=parseInt(document.getElementById("minute").value);
        var heureSaisir = getheure();
        var min = getminutes();
        
        var heure=heureSaisir*60+min;
        var duree=document.getElementById("duree").value;
        var typeCours=document.getElementById("typeCours").value;
        //alert(typeCours);
        
        var xhr = new XMLHttpRequest();
        xhr.open("GET","ServletCreneau"+"?cours="+cours+"&heure="+heure+"&date="+date+"&duree="+duree+"&typeCours="+typeCours,true);
            xhr.send();
            
        //delock btn_valider   
        document.getElementById("btn_valider").disabled = false ;    
            
        };
    
    function valider(){
       // alert("valider()");
        var mytable=document.getElementById("listEtudiant"); 
        var nbEtudiant=mytable.rows.length;
//        var cours = document.getElementById("cours").value;
//        var date  =document.getElementById("datepicker").value;
//        var heureSaisir = parseInt(document.getElementById("heure").value);
//        var min=parseInt(document.getElementById("minute").value);
//        var heure=heureSaisir*60+min;
//        var duree=document.getElementById("duree").value;
       // alert(nbEtudiant);
        
        for(var i=1;nbEtudiant-1;i++){
            //obtenir les id et etat des etudiant dans la table
            
            var id=mytable.rows[i].cells[1].innerHTML;
            //alert(id);
            var idEtat="etatPre"+i;
            // alert(idEtat);
            var obj=document.getElementById(idEtat);
            var index = obj.selectedIndex;
            var etat = obj.options[index].text;
            alert(etat);
            var xhr = new XMLHttpRequest();
            // Requête au serveur avec les paramètres éventuels.
                xhr.open("GET","ServletInsertEtat"+"?id="+id+"&etat="+etat); //+"&cours="+cours+"&heure="+heure+"&date="+date+"&duree="+duree,true);
                
            // Envoie de la requête.
            xhr.send();           
        }
        //alert("Validation fini");
        
       
    }
    
    

     
        
        
        
        
        /**
 * Lancement après le chargement du DOM.
 */
document.addEventListener("DOMContentLoaded", () => {

	
	document.getElementById("groupe").addEventListener("change",getEtudiant);
        document.getElementById("formation").addEventListener("change",getCours);
        document.getElementById("btn_valider").addEventListener("click",valider);
        document.getElementById("typeCours").addEventListener("change",creationCreneau);
         document.getElementById("cours").addEventListener("change",timeGrpDelock);
        //to get time 
       // document.getElementById("btn_valider").addEventListener("click",getCreneaux);
        document.getElementById("heureDeb").addEventListener("keyup",verifTimeInterval);
});


 /** -------------------------------
 *       fonction demarrage
 * -------------------------------
 */
     window.onload=function(){  
        var myDate = new Date();        
        var element1 = document.getElementById("heureDeb");
        var element2 = document.getElementById("minute");           
          
        element1.setAttribute("value", setTime() ); 
        // disable demarrage
        
        document.getElementById("cours").disabled = true ;
        document.getElementById("datepicker").disabled = true ;        
        document.getElementById("heureDeb").disabled = true ;
        document.getElementById("duree").disabled = true ;
        document.getElementById("groupe").disabled = true ;
        document.getElementById("typeCours").disabled = true ;
        document.getElementById("btn_valider").disabled = true ;
    } 
    
    function CoursDelock(){
        if(document.getElementById("formation").value !="Formation" ){
            document.getElementById("cours").disabled = false ;
        }else{
            document.getElementById("cours").disabled = true ;
        }        
    }
     function timeGrpDelock(){
        if(document.getElementById("cours").value !="" ){
            document.getElementById("datepicker").disabled = false ;
            document.getElementById("heureDeb").disabled = false ;
            document.getElementById("duree").disabled = false ;
            document.getElementById("groupe").disabled = false ;
        }else{
            document.getElementById("datepicker").disabled = true ;
            document.getElementById("heureDeb").disabled = true ;
            document.getElementById("duree").disabled = true ;
            document.getElementById("groupe").disabled = true ;
        }        
    }
     function typeCoursDelock(){
        if(document.getElementById("groupe").value !="" ){
            document.getElementById("typeCours").disabled = false ;
        }else{
            document.getElementById("typeCours").disabled = true ;
        }        
    }
/** -------------------------------
 * fonction concernant de Timepicker
 * -------------------------------
 */ 

    //--- predre heure minute d'instance -----    
        function setTime(){
            var myDate = new Date();
            var heure = myDate.getHours();
            var minute = myDate.getMinutes();            
            if(heure < 10){                
                heure = "0"+ heure.toString();
            }            
             if(minute < 10){
                minute = "0"+ minute.toString();
            }

           return  heure.toString() +":"+ minute.toString();  
        }

         
     

     //---- verifier time saisie ----   
        function verifTimeInterval(){
           var inputT = document.getElementById("heureDeb").value;
           var heure = parseInt(inputT.substring(0,2) );
            
            if(heure>20|heure<8|inputT.length<5){                 
                 document.getElementById("typeCours").disabled = true ; 
                 document.getElementById("msg_timeErr").style.display="block";
            }else{               
                document.getElementById("typeCours").disabled = false ; 
                document.getElementById("msg_timeErr").style.display="none";               
            }           
            
        };

        function getheure(){
            var heure = document.getElementById("heureDeb").value;
            heure = parseInt( heure.substring(0,2));
            return heure;
        }

         function getminutes(){
            var minutes = document.getElementById("heureDeb").value;
            minutes =parseInt( minutes.substring(3,5));           
            return minutes;
        }



//----fonction de composant TimePicker ----
document.querySelector("#time").addEventListener("input", timePickerF);

// c'est un fonction cotroler "horloge" icone. 
 function timePickerF(e) {
  const reTime = /^([0-1][0-9]|2[0-3]):[0-5][0-9]$/;
  const time = this.value;
  if (reTime.exec(time)) {
    const minute = Number(time.substring(3,5));
    const hour = Number(time.substring(0,2)) % 12 + (minute / 60);
    this.style.backgroundImage = `url("data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='40' height='40'><circle cx='20' cy='20' r='18.5' fill='none' stroke='%23222' stroke-width='3' /><path d='M20,4 20,8 M4,20 8,20 M36,20 32,20 M20,36 20,32' stroke='%23bbb' stroke-width='1' /><circle cx='20' cy='20' r='2' fill='%23222' stroke='%23222' stroke-width='2' /></svg>"), url("data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='40' height='40'><path d='M18.5,24.5 19.5,4 20.5,4 21.5,24.5 Z' fill='%23222' style='transform:rotate(${360 * minute / 60}deg); transform-origin: 50% 50%;' /></svg>"), url("data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='40' height='40'><path d='M18.5,24.5 19.5,8.5 20.5,8.5 21.5,24.5 Z' style='transform:rotate(${360 * hour / 12}deg); transform-origin: 50% 50%;' /></svg>")`;
  }
};