/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function consulterP(){

    var date1=document.getElementById("date1").value;
    var date2=document.getElementById("date2").value;
    
    var xhr = new XMLHttpRequest();
    document.getElementById("type").value="";
    document.getElementById("btn_ajouter").disabled="";
    document.getElementById("btn_modifier").disabled="disabled";
    document.getElementById("btn_supprimer").disabled="disabled";
    
    // Requête au serveur avec les paramètres éventuels.
    xhr.open("GET","ServletConsulterPeriode"+"?date1="+date1+"&date2="+date2,true); 
    
    xhr.onload = function()
                    {
                    // Si la requête http s'est bien passée.
                    if (xhr.status === 200)
                            {
                            //requperer les infos  
                            var type = xhr.responseXML.getElementsByTagName("type")[0].firstChild.nodeValue;
                            //Mise a jour la page
                            if(type.length>0){
                                document.getElementById("btn_ajouter").disabled="disabled";
                                document.getElementById("btn_modifier").disabled="";
                                document.getElementById("btn_supprimer").disabled="";
                                document.getElementById("type").value=type;
                                }
                            }
                    };
    xhr.send();
    
};
function ajouterP(){
    var date1=document.getElementById("date1").value;
    var date2=document.getElementById("date2").value;
    var type=document.getElementById("type").value;

    var xhr = new XMLHttpRequest();
        xhr.open("GET","ServletActionPeriode"+"?date1="+date1+"&date2="+date2+"&type="+type+"&action=ajouter",true);
            alert("Periode ajoute!");
        xhr.send();
};

function supprimerP(){
        var date1=document.getElementById("date1").value;
        var date2=document.getElementById("date2").value;

        var xhr=new XMLHttpRequest();
        xhr.open("GET","ServletActionPeriode"+"?date1="+date1+"&date2="+date2+"&action=supprimer",true);
            alert("Periode Supprime!");
        xhr.send();
}

function modifierP(){
        var date1=document.getElementById("date1").value;
        var date2=document.getElementById("date2").value;
        var type=document.getElementById("type").value;

        var xhr=new XMLHttpRequest();
        xhr.open("GET","ServletActionPeriode"+"?date1="+date1+"&date2="+date2+"&type="+type+"&action=modifier",true);
            alert("Periode Modifier!");
        xhr.send();
}


document.addEventListener("DOMContentLoaded", () => {

//	document.getElementById("date1").addEventListener("change",consulterP);
//        document.getElementById("date2").addEventListener("change",consulterP);
        document.getElementById("btn_ajouter").addEventListener("click",ajouterP);
        document.getElementById("btn_supprimer").addEventListener("click",supprimerP);
        document.getElementById("btn_modifier").addEventListener("click",modifierP);
        document.getElementById("date1").addEventListener("click",consulterP);
        document.getElementById("date2").addEventListener("click",consulterP);
});

