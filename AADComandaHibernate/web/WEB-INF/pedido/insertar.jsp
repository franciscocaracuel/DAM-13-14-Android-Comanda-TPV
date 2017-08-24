<%@page import="com.izv.util.Util"%>
<%@page import="java.util.List"%>
<%@page import="hibernate.Mesa"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Mesa> mesas = (List) request.getAttribute("mesas");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Agregar pedido</title>
    </head>
    <body>
        <h1>Agregar pedido</h1>
        <form action="controlador" method="POST">
            <input type="hidden" name="op" value="opinsertarpedido" />
            <%
              out.println("<select name=\"mesa\"><option value=\"\">&nbsp;</option>");
              for(Mesa m: mesas){
                  out.println("<option value=\""+m.getId()+"\">"+m.getNombre()+"</option>");
              }
              out.println("</select>");
            %>
            <br/>
            <%
              String[][] valores={{"0", "Abierto"},{"1", "Cerrado"}};
              out.println(Util.select("cerrado", valores, ""));
            %>
            <input type="submit" value="Agregar" />
        </form>
        <h4><a href="controlador?op=vistapedido">Pedidos</a></h4>
        <h3><a href=".">Inicio</a></h3>
    </body>
</html>