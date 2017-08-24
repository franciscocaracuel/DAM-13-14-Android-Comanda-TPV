<%@page import="hibernate.Carta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editar carta</title>
    </head>
    <body>
        <h1>Editar carta</h1>
        <form action="controlador" method="POST">
            <%
                Carta c = (Carta) request.getAttribute("carta");
            %>
            <input type="hidden" name="op" value="opeditarcarta" />
            <input placeholder="id" type="text" name="id" value="<%=c.getId()%>" readonly="readonly" /><br/>
            <input placeholder="carta" type="text" name="carta" value="<%=c.getNombre()%>" /><br/>
            <input placeholder="precio" type="text" name="precio" value="<%=c.getPrecio()%>" /><br/>
            <input type="hidden" name="pk" value="<%=c.getId()%>" />
            <input type="submit" value="Editar" />
        </form>
        <h4><a href="controlador?op=vistacarta">Cartas</a></h4>            
        <h3><a href=".">Inicio</a></h3>
    </body>
</html>