/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function getAffecter ()
        {
        //recuperer la valeur
       // var idetudiant = document.getElementById("idetudiant").value;
        var mois =document.getElementById("mois").value;
        var annee = document.getElementById("annee").value;
        
	// Objet XMLHttpRequest.
	var xhr = new XMLHttpRequest();

	// Requête au serveur avec les paramètres éventuels.
	xhr.open("GET","Servletetufeuille"+"?mois="+mois+"&annee="+annee);

	// On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
	xhr.onload = function()
		{
		// Si la requête http s'est bien passée.
		if (xhr.status === 200)
			{
			    var tab_date=xhr.responseXML.getElementsByTagName("date");
                          //  alert("tab_date[0]"+tab_date[0].firstChild.nodeValue);
                            var tab_heureetotal=xhr.responseXML.getElementsByTagName("Eheuretotal");
                            var tab_heureem=xhr.responseXML.getElementsByTagName("Eheurematin");
                            var tab_heureea=xhr.responseXML.getElementsByTagName("Eheureapres");
                            var tab_absheuretotal=xhr.responseXML.getElementsByTagName("absheuretotal");
                            var tab_absheurem=xhr.responseXML.getElementsByTagName("absheurem");
                            var tab_absheurea=xhr.responseXML.getElementsByTagName("absheurea");
                            var tab_heuredtotal=xhr.responseXML.getElementsByTagName("Dheuretotal");
                            var tab_heuredm=xhr.responseXML.getElementsByTagName("heuredmatin");
                            var tab_heureda=xhr.responseXML.getElementsByTagName("heureda");
                            var tab_creneau=xhr.responseXML.getElementsByTagName("idCreneau");
                            var nom=xhr.responseXML.getElementsByTagName("nom");
                            var prenom =xhr.responseXML.getElementsByTagName("prenom");
                            var photo =xhr.responseXML.getElementsByTagName("photo");    
                            var E =xhr.responseXML.getElementsByTagName("E");    
                            var D =xhr.responseXML.getElementsByTagName("D");    
                            var abs=xhr.responseXML.getElementsByTagName("abs");    
                            
                            document.getElementById("div_etu").className="etudiant_show";
                            var elt_nom=document.getElementById("nom");
                            var elt_pnom=document.getElementById("prenom");
                            //var elt_photo=
                            /**
                             * info etudiant
                             */
                            elt_nom.innerHTML=nom[0].firstChild.nodeValue;
                            elt_pnom.innerHTML=prenom[0].firstChild.nodeValue;
                            
                            /**
                             * table 
                             */
                            var tab_c=[];
                            var elt = document.getElementById("tab_feuille");
                            
                            for(i=0;i <6;i++){
                                    
                                    var tr=document.createElement("tr");//创建行
                                   for(j=0;j<14; j++){
                                        if(j===0){
                                         var td_1=document.createElement("td");//创建列
                                        // alert("j===0"+tab_date[i].firstChild.nodeValue);
                                         td_1.innerText="";
                                         td_1.innerText=tab_date[i].firstChild.nodeValue;
//                                         alert("la "+i+"row "+j+" col"+tab_date[i].firstChild.nodeValue);
                                         tr.appendChild(td_1);//向行中添加子节点列
                                         }
                                        else if(j===1){
                                         var td_2=document.createElement("td");//创建列
                                         td_2.innerText="";
                                         td_2.innerText=tab_heureem[i].firstChild.nodeValue;
//                                         alert("la "+i+"row "+j+" col"+tab_heure[i].firstChild.nodeValue);
                                         tr.appendChild(td_2);//向行中添加子节点列
                                        }else if(j===2){
                                         var td_3=document.createElement("td");//创建列
                                         td_3.innerText="";
                                         td_3.innerText=tab_heuredm[i].firstChild.nodeValue;
//                                         alert("la "+i+"row "+j+" col"+tab_absheure[i].firstChild.nodeValue);
                                         tr.appendChild(td_3);//  
                                        }else if(j===3){
                                         var td_4=document.createElement("td");//创建列
                                         td_4.innerText="";
                                         td_4.innerText=tab_absheurem[i].firstChild.nodeValue;
//                                         alert("la "+i+"row "+j+" col"+tab_reheure[i].firstChild.nodeValue);
                                         tr.appendChild(td_4);//   
                                        }else if(j===4){
                                         var td_5=document.createElement("td");//创建列
                                         //signature
                                         td_5.innerText="";
                                         tr.appendChild(td_5);//   
                                        }else if(j===5){
                                         var td_6=document.createElement("td");//创建列
                                         td_6.innerText="UT1";
                                         //site
                                         tr.appendChild(td_6);//   
                                        }else if(j===6){
                                         var td_7=document.createElement("td");//创建列
                                         td_7.innerText="";
                                         td_7.innerText=tab_heureea[i].firstChild.nodeValue;
                                         
                                         tr.appendChild(td_7);//   
                                        }else if(j===7){
                                         var td_8=document.createElement("td");//创建列
                                         td_8.innerText="";
                                         td_8.innerText=tab_heureda[i].firstChild.nodeValue;
                                         
                                         tr.appendChild(td_8);//   
                                        }else if(j===8){
                                         var td_9=document.createElement("td");//创建列
                                         td_9.innerText="";
                                         td_9.innerText=tab_absheurea[i].firstChild.nodeValue;
                                         
                                         tr.appendChild(td_9);//   
                                        }else if(j===9){
                                         var td_10=document.createElement("td");//创建列
                                         td_10.innerText="";
                                         tr.appendChild(td_10);//   
                                        }else if(j===10){
                                         var td_11=document.createElement("td");//创建列
                                         td_11.innerText="UT1";
                                         tr.appendChild(td_11);//   
                                        }else if(j===11){
                                         var td_12=document.createElement("td");//创建列
                                         td_12.innerText="";
                                         td_12.innerText=tab_heureetotal[i].firstChild.nodeValue;
                                         tr.appendChild(td_12);//   
                                        }else if(j===12){
                                         var td_13=document.createElement("td");//创建列
                                         td_13.innerText="";
                                         td_13.innerText=tab_heuredtotal[i].firstChild.nodeValue;
                                         tr.appendChild(td_13);//   
                                        }else{
                                         var td_14=document.createElement("td");//创建列
                                         td_14.innerText="";
                                         td_14.innerText=tab_absheuretotal[i].firstChild.nodeValue;
                                         tr.appendChild(td_14);// 
                                        }
                                        
                                    }

                                         elt.appendChild(tr);//添加子节点tr
                                }
                                var trbas=document.createElement("tr");
                                for(j=0;j<6; j++){
                                            if(j===0){
                                             var td1=document.createElement("td");//创建列
                                             
                                             td1.innerText="*Nombre d'heure par type d'activit&eacute"+ 
                                             "E : pour enseignements"+
                                             "(TD-TP-PT-accompagnement ou examen)"+
                                             "D: pour Documentation sur site"+
                                             "Abs: pour Absence";
                                             //alert("la "+i+"row "+j+" col"+tab_date[i].firstChild.nodeValue);
                                             trbas.appendChild(td1);//向行中添加子节点列
                                             }else if(j===1){
                                             var td2=document.createElement("td");//创建列
                                             
                                             td2.innerText="**Indiquez pour chaque demi-journ&eacutee le"+
                                            "site de l'activit&eacute:"+
                                            "UT1"+
                                            "Lieu du Stage"+
                                            "IUT Rodez";
                                             trbas.appendChild(td2);
                                             }else if(j===2){
                                              var td3=document.createElement("td");//创建列
                                              td3.innerText="Sous Tataux";  
                                              trbas.appendChild(td3);
                                              
                                             }else if(j===3){
                                               var td4=document.createElement("td");//创建列
                                              td4.innerText=E[0].firstChild.nodeValue;   
                                              trbas.appendChild(td4);
                                              }
                                             else if(j===4){
                                               var td5=document.createElement("td");//创建列
                                              td5.innerText=D[0].firstChild.nodeValue;    
                                              trbas.appendChild(td5);
                                              }else{
                                               var td6=document.createElement("td");//创建列
                                               td6.innerText=abs[0].firstChild.nodeValue;    
                                               trbas.appendChild(td6);
                                              }

                                             elt.appendChild(trbas);
                                             
                                             
                                         }
                                var nb=elt.rows.length;
                                
                                 for(var i=0;i<2;i++){
                                            elt.rows[nb-1].cells[i].colSpan="5";
                                 }
                                }
            };
	// Envoie de la requête.
	xhr.send();
	
	}       
        
          
    
       /* * Lancement après le chargement du DOM.
 */
document.addEventListener("DOMContentLoaded", () => {

	
//	document.getElementById("groupe").addEventListener("change",getEtudiant);
        document.getElementById("affecter").addEventListener("click",getAffecter);
//        document.getElementById("valide").addEventListener("click",valider);
        
});
        
        