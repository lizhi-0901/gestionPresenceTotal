/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function exsitEtudiant(){
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
       xhr.open("GET","ServletVerifierEnseignant"+"?nom="+nom+"&prenom="+prenom+"&type=Etudiant",true);
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
                                elt.innerHTML="Etudiant Existe !";
                                document.getElementById("btn_consulter").disabled="";
                                document.getElementById("btn_ajouter").disabled="disabled";
                                document.getElementById("btn_supprimer").disabled="";
                                }
                            else{
                                elt.innerHTML="Etudiant n'existe pas !";
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

function ajouterEtudiant(){
    var nom=document.getElementById("nomPersonne").value;
    var prenom=document.getElementById("prenomPersonne").value;

    document.getElementById("nom").value=nom;
    document.getElementById("prenom").value=prenom;

    var active=document.getElementById("infoAfficher");
                    active.style.display="";
    document.getElementById("btn_valider").style.display="";
};

    function validerAjouterEtudiant(){
        var nom=document.getElementById("nomPersonne").value;
        var prenom=document.getElementById("prenomPersonne").value;
        var numTel=document.getElementById("numTel").value;
        var eMail=document.getElementById("eMail").value;
        var formation=document.getElementById("formation").value;
        var entreprise=document.getElementById("entreprise").value;
        var numTelE=document.getElementById("numTelE").value;
        var eMailE=document.getElementById("eMailE").value;

        var xhr = new XMLHttpRequest();
        xhr.open("GET","ServletActionEtudiant"+"?nom="+nom+"&prenom="+prenom+"&numTel="+numTel+"&eMail="+eMail+
                   "&formation="+formation+"&entreprise="+entreprise+"&numTelE="+numTelE+"&eMailE="+eMailE+"&action=ajouter",true);
            alert("Etudiant ajoute!");
        xhr.send();
    };


    function suppEtudiant(){
        var nom=document.getElementById("nomPersonne").value;
        var prenom=document.getElementById("prenomPersonne").value;


        var xhr=new XMLHttpRequest();
        xhr.open("GET","ServletActionEtudiant"+"?nom="+nom+"&prenom="+prenom+"&action=supprimer",true);
            alert("Etudiant Supprime!");
        xhr.send();
    }
    
    
    function consulterEtudiant(){

        var nom=document.getElementById("nomPersonne").value;
        var prenom=document.getElementById("prenomPersonne").value;

        var xhr = new XMLHttpRequest();
        var active=document.getElementById("infoAfficher");
        active.style.display="";

        // Requête au serveur avec les paramètres éventuels.
        xhr.open("GET","ServletConsulterEtudiant"+"?nom="+nom+"&prenom="+prenom,true); 

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
                                var formation = xhr.responseXML.getElementsByTagName("formation")[0].firstChild.nodeValue;
                                var entreprise = xhr.responseXML.getElementsByTagName("entreprise")[0].firstChild.nodeValue;
                                var numTelE = xhr.responseXML.getElementsByTagName("numTelE")[0].firstChild.nodeValue;
                                var eMailE = xhr.responseXML.getElementsByTagName("eMailE")[0].firstChild.nodeValue;
                                //Mise a jour la page
                                document.getElementById("nom").value=nom;
                                document.getElementById("prenom").value=prenom;
                                document.getElementById("numTel").value=numTel;
                                document.getElementById("eMail").value=eMail;
                                document.getElementById("formation").value=formation;
                                document.getElementById("entreprise").value=entreprise;
                                document.getElementById("numTelE").value=numTelE;
                                document.getElementById("eMailE").value=eMailE;
                                
                                document.getElementById("btn_modifier").style.display="";
                                }
                        };
            xhr.send();

    };
    
    function modifierEtudiant(){
        alert("click ok");
    var nom=document.getElementById("nomPersonne").value;
    var prenom=document.getElementById("prenomPersonne").value;
    var numTel=document.getElementById("numTel").value;
    var eMail=document.getElementById("eMail").value;
    var formation=document.getElementById("formation").value;
    var entreprise=document.getElementById("entreprise").value;
    var numTelE=document.getElementById("numTelE").value;
    var eMailE=document.getElementById("eMailE").value;
    
    var xhr = new XMLHttpRequest();
    xhr.open("GET","ServletActionEtudiant"+"?nom="+nom+"&prenom="+prenom+"&numTel="+numTel+"&eMail="+eMail+
                   "&formation="+formation+"&entreprise="+entreprise+"&numTelE="+numTelE+"&eMailE="+eMailE+"&action=modifier",true);
            alert("Etudiant Modifie!");
    xhr.send();
}

document.addEventListener("DOMContentLoaded", () => {

	document.getElementById("prenomPersonne").addEventListener("change",exsitEtudiant);
        document.getElementById("nomPersonne").addEventListener("change",exsitEtudiant);
        document.getElementById("btn_consulter").addEventListener("click",consulterEtudiant);
        document.getElementById("btn_ajouter").addEventListener("click",ajouterEtudiant);
        document.getElementById("btn_valider").addEventListener("click",validerAjouterEtudiant);
        document.getElementById("btn_supprimer").addEventListener("click",suppEtudiant);
        document.getElementById("btn_modifier").addEventListener("click",modifierEtudiant);
});