<%@page import="hibernate.Carta"%>
<%@page import="hibernate.Mesa"%>
<%@page import="java.util.List"%>
<%@page import="com.izv.util.Util"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String idPedido=(String)request.getParameter("idpedido");
    List<Carta> cartas = (List) request.getAttribute("cartas");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Agregar detallepedido</title>
    </head>
    <body>
        <h1>Agregar detallepedido</h1>
        <form action="controlador" method="POST">
            <input type="hidden" name="op" value="opinsertardetallepedido" />
            <input type="hidden" name="idpedido" value="<%=idPedido%>"/>
            <label>IdPedido <%=idPedido%> </label>
            <br/>
            <%
              out.println("<select name=\"carta\"><option value=\"\">&nbsp;</option>");
              for(Carta c: cartas){
                  out.println("<option value=\""+c.getId()+"\">"+c.getNombre()+"</option>");
              }
              out.println("</select>");
            %>
            <input type="text" name="cantidad" id="cantidad"/>
            <input type="submit" value="Agregar" />
        </form>
        <h4><a href="controlador?op=vistadetallepedido&id=<%=idPedido%>">Detalles pedido</a></h4>
        <h3><a href=".">Inicio</a></h3>
    </body>
</html>