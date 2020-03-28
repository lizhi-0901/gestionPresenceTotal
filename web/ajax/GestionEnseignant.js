/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function exsitEnseignant(){
    //requpere les infos pour chercher dans BD
    var nom=document.getElementById("nomPersonne").value;
    var prenom=document.getElementById("prenomPersonne").value;
    
    //initialiser le table
    document.getElementById("btn_modifier").style.display="none";
    document.getElementById("btn_valider").style.display="none";
    document.getElementById("numTel").value="";
    document.getElementById("eMail").value="";
    
    var xhr = new XMLHttpRequest();

    // Requête au serveur avec les paramètres éventuels.
    if(nom.length>0 && prenom.length>0){
       xhr.open("GET","ServletVerifierEnseignant"+"?nom="+nom+"&prenom="+prenom+"&type=Enseignant",true);
    }
    
    
    
        xhr.onload = function()
                    {
                    // Si la requête http s'est bien passée.
                    if (xhr.status === 200)
                            {
                            var elt = document.getElementById("verifPersonneRes");
                            elt.innerHTML="";

                            var exist = xhr.responseXML.getElementsByTagName("Enseignant")[0].firstChild.nodeValue;
                            if(exist === "true"){
                                elt.innerHTML="Enseignant Existe !";
                                document.getElementById("btn_consulter").disabled="";
                                document.getElementById("btn_ajouter").disabled="disabled";
                                document.getElementById("btn_supprimer").disabled="";
                                }
                            else{
                                elt.innerHTML="Enseignant n'existe pas !";
                                document.getElementById("btn_consulter").disabled="disabled";
                                document.getElementById("btn_ajouter").disabled="";
                                document.getElementById("btn_supprimer").disabled="disabled";
                                }
                             var active=document.getElementById("infoAfficher");
                                active.style.display="none";
                            }
                    };
        xhr.send();

};

function consulterEnseignant(){
    
    var nom=document.getElementById("nomPersonne").value;
    var prenom=document.getElementById("prenomPersonne").value;
    
    var xhr = new XMLHttpRequest();
    var active=document.getElementById("infoAfficher");
    active.style.display="";
    
    // Requête au serveur avec les paramètres éventuels.
    xhr.open("GET","ServletConsulterEnseignant"+"?nom="+nom+"&prenom="+prenom,true); 
    
    xhr.onload = function()
                    {
                    // Si la requête http s'est bien passée.
                    if (xhr.status === 200)
                            {
                            //requperer les infos
                            var nom=document.getElementById("nomPersonne").value;
                            var prenom=document.getElementById("prenomPersonne").value;   
                            var numTel = xhr.responseXML.getElementsByTagName("numTel")[0].firstChild.nodeValue;
                            var eMail = xhr.responseXML.getElementsByTagName("eMail")[0].firstChild.nodeValue;
                            //Mise a jour la page
                            document.getElementById("nom").value=nom;
                            document.getElementById("prenom").value=prenom;
                            document.getElementById("numTel").value=numTel;
                            document.getElementById("eMail").value=eMail;
                            
                            
                            document.getElementById("btn_modifier").style.display="";
                            }
                    };
        xhr.send();
    
};

function ajouterEnseignant(){
    var nom=document.getElementById("nomPersonne").value;
    var prenom=document.getElementById("prenomPersonne").value;

    document.getElementById("nom").value=nom;
    document.getElementById("prenom").value=prenom;

    var active=document.getElementById("infoAfficher");
                    active.style.display="";
    document.getElementById("btn_valider").style.display="";
};

function validerAjouterEnseignant(){
    var nom=document.getElementById("nomPersonne").value;
    var prenom=document.getElementById("prenomPersonne").value;
    var numTel=document.getElementById("numTel").value;
    var eMail=document.getElementById("eMail").value;
    
    var xhr = new XMLHttpRequest();
    xhr.open("GET","ServletAjouterEnseignant"+"?nom="+nom+"&prenom="+prenom+"&numTel="+numTel+"&eMail="+eMail,true);
        alert("Enseignant ajoute!");
    xhr.send();
};

function suppEnseignant(){
    var nom=document.getElementById("nomPersonne").value;
    var prenom=document.getElementById("prenomPersonne").value;
    
    
    var xhr=new XMLHttpRequest();
    xhr.open("GET","ServletSupprimerEnseignant"+"?nom="+nom+"&prenom="+prenom,true);
        alert("Enseignant Supprime!");
    xhr.send();
}

function modifierEnseignant(){
    var nom=document.getElementById("nomPersonne").value;
    var prenom=document.getElementById("prenomPersonne").value;
    var numTel=document.getElementById("numTel").value;
    var eMail=document.getElementById("eMail").value;
    
    var xhr = new XMLHttpRequest();
    xhr.open("GET","ServletModifierEnseignant"+"?nom="+nom+"&prenom="+prenom+"&numTel="+numTel+"&eMail="+eMail,true);
        alert("Enseignant Modifie!");
    xhr.send();
}






document.addEventListener("DOMContentLoaded", () => {

	document.getElementById("prenomPersonne").addEventListener("change",exsitEnseignant);
        document.getElementById("nomPersonne").addEventListener("change",exsitEnseignant);
        document.getElementById("btn_consulter").addEventListener("click",consulterEnseignant);
        document.getElementById("btn_ajouter").addEventListener("click",ajouterEnseignant);
        document.getElementById("btn_valider").addEventListener("click",validerAjouterEnseignant);
        document.getElementById("btn_supprimer").addEventListener("click",suppEnseignant);
        document.getElementById("btn_modifier").addEventListener("click",modifierEnseignant);
});

