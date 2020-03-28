/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function getCours ()
        {
        //recuperer la valeur
        var formation = document.getElementById("formation").value;
        
	// Objet XMLHttpRequest.
	var xhr = new XMLHttpRequest();

	// Requête au serveur avec les paramètres éventuels.
	xhr.open("GET","ServletListCours"+"?formation="+formation);

	// On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
	xhr.onload = function()
		{
		// Si la requête http s'est bien passée.
		if (xhr.status === 200)
			{
			// Elément html que l'on va mettre à jour.
                            var elt = document.getElementById("cours");
                            var tab=xhr.responseXML.getElementsByTagName("matiere");
                            elt.innerHTML="";
                            for ( i=0;i<tab.length;i++)
                            {
                                elt.insertAdjacentHTML("beforeend","<option>"+tab[i].firstChild.nodeValue+"</option>");
                            }
			}
		};
	
	// Envoie de la requête.
	xhr.send();
	
	}
        

function exsitCours(){
    //requpere les infos pour chercher dans BD
    var nomC=document.getElementById("nomC").value;
    var xhr = new XMLHttpRequest();
    
    
    // Requête au serveur avec les paramètres éventuels.
    if(nomC.length>0){
       xhr.open("GET","ServletVerifierCours"+"?nomC="+nomC,true);
    }
        xhr.onload = function()
                    {
                    // Si la requête http s'est bien passée.
                    if (xhr.status === 200)
                            {
                            var elt = document.getElementById("verifPersonneRes");
                            elt.innerHTML="";

                            var exist = xhr.responseXML.getElementsByTagName("Cours")[0].firstChild.nodeValue;
                            if(exist === "true"){
                                elt.innerHTML="Cours Existe !";
                                document.getElementById("btn_ajouter").disabled="disabled";
                                }
                            else{
                                elt.innerHTML="Cours n'existe pas !";
                                document.getElementById("btn_ajouter").disabled="";
                                }
                            };
                    };
        xhr.send();

};


function ajouterCours(){
    var box=document.getElementById("formation");
    var index=box.selectedIndex;
    var formation=box.options[index].value;
    var cours=document.getElementById("nomC").value;
    
    var xhr = new XMLHttpRequest();
    xhr.open("GET","ServletActionCours"+"?nomC="+cours+"&formation="+formation+"&action=ajouter",true);
        alert("Cours ajoute!");
    xhr.send();
};

function suppCours(){
    var cours=document.getElementById("cours").value;
    
    var xhr=new XMLHttpRequest();
    xhr.open("GET","ServletActionCours"+"?nomC="+cours+"&action=supprimer",true);
        alert("Cours Supprime!");
    xhr.send();
}


document.addEventListener("DOMContentLoaded", () => {
        document.getElementById("formation").addEventListener("change",getCours);
        document.getElementById("nomC").addEventListener("change",exsitCours);
        document.getElementById("btn_ajouter").addEventListener("click",ajouterCours);
        document.getElementById("btn_supprimer").addEventListener("click",suppCours);
        //document.getElementById("Cours").addEventListener("change",activerBtn);
});