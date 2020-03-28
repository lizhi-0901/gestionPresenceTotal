/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Saisir les Presence pour un etudiant de maniere individuelle
 */
     
/**
 *  affichir les creneaux selon matiere date et typeActivite
 * @return {undefined}
 */ 
        function getCreneaux ()
        {
        //recuperer la valeure
        
        //var heure = document.getElementById("heureDeb").value;
        //var minute =document.getElementById("minute").value;
        var heure = getheure();
        var minute = getminutes();
        
        var typeCreneau = document.getElementById("typeCreneau").value;
        var d  =document.getElementById("datepicker").value;
        var presence=document.getElementById("presence").value;
                
     
	
        // Objet XMLHttpRequest.
	var xhr = new XMLHttpRequest();

	// Requête au serveur avec les paramètres éventuels.
	xhr.open("GET","ServletSaisir"+"?heure="+heure+"&typeCreneau="+typeCreneau+"&date="+d+"&minute="+minute+"&presence="+presence);

	// On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
	xhr.onload = function()
		{
		// Si la requête http s'est bien passée.
		if (xhr.status === 200)
			{
			// Elément html que l'on va mettre à jour.
                          var nom = document.getElementById("nom");
                          var prenom =document.getElementById("prenom");
                          var msg =document.getElementById("espace");
                          var nom=xhr.responseXML.getElementsByTagName("nom");
                          var prenom=xhr.responseXML.getElementsByTagName("prenom");
                          var type = xhr.responseXML.getElementsByTagName("typeCreneau");
                          var etatprsence=xhr.responseXML.getElementsByTagName("presence");
                          nom.innerHTML=nom[0].firstChild.nodeValue;
                          prenom.innerHTML= prenom[0].firstChild.nodeValue; 
                          msg.innerHTML=type[0].firstChild.nodeValue+" "+etatprsence[0].firstChild.nodeValue;
			}
		};
	
	// Envoie de la requête.
	xhr.send();
	}     
 /**
 * Lancement après le chargement du DOM.
 */
        document.addEventListener("DOMContentLoaded", () => 
        {
            
            document.getElementById("btn_valider").addEventListener("click",getCreneaux);
             document.getElementById("heureDeb").addEventListener("keyup",verifTimeInterval);
        });
        
        

/** ---------------------------------
 * fonction concernant de Timepicker
 * ----------------------------------
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

        
        //-- capter heure de moment Load 
     window.onload=function(){  
        var myDate = new Date();        
        var element1 = document.getElementById("heureDeb");
        var element2 = document.getElementById("minute");           
          
        element1.setAttribute("value", setTime() ); // myDate.getHours());       
    };   
     

     //---- verifier time saisie ----   
        function verifTimeInterval(){
           var inputT = document.getElementById("heureDeb").value;
           var heure = parseInt(inputT.substring(0,2) );
            
            if(heure>20|heure<8|inputT.length<5){
                //alert("input time between 8 and 20");    
                 document.getElementById("btn_valider").disabled = true ; 
                 document.getElementById("msg_timeErr").style.display="block";
            }else{
                document.getElementById("btn_valider").disabled = false ; 
                document.getElementById("msg_timeErr").style.display="none";               
            }           
            
        };

        function getheure(){
            var heure = document.getElementById("heureDeb").value;
            heure = heure.substring(0,2);
            return heure;
        }

         function getminutes(){
            var minutes = document.getElementById("heureDeb").value;
            minutes = minutes.substring(3,5);
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
    
