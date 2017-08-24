window.addEventListener("load", _inicio, false);

function _inicio(){
    var borra = document.getElementsByClassName("borrarmesa");
    for (var i = 0; i < borra.length; i++) {
        var elemento = borra[i];
        elemento.onclick=function(){
            var atributo = this.getAttribute("idmesa");
            if(confirm("¿Borrar?"))
                window.location="?op=opborrarmesa&id="+atributo;
        };
    }    
    
    borra = document.getElementsByClassName("borrarcarta");
    for (var i = 0; i < borra.length; i++) {
        var elemento = borra[i];
        elemento.onclick=function(){
            var atributo = this.getAttribute("idcarta");
            if(confirm("¿Borrar?"))
                window.location="?op=opborrarcarta&id="+atributo;
        };
    }
    
    borra = document.getElementsByClassName("borrarpedido");
    for (var i = 0; i < borra.length; i++) {
        var elemento = borra[i];
        elemento.onclick=function(){
            var atributo = this.getAttribute("idpedido");
            if(confirm("¿Borrar?"))
                window.location="?op=opborrarpedido&id="+atributo;
        };
    }
    
    borra = document.getElementsByClassName("borrardetallepedido");
    for (var i = 0; i < borra.length; i++) {
        var elemento = borra[i];
        elemento.onclick=function(){
            var atributo = this.getAttribute("id");
            var idpedido = this.getAttribute("idpedido");
            if(confirm("¿Borrar?"))
                window.location="?op=opborrardetallepedido&id="+atributo+"&idpedido="+idpedido;
        };
    }
    
}