<%@page import="java.util.List"%>
<%@page import="hibernate.Mesa"%>
<%@page import="hibernate.Pedido"%>
<%@page import="com.izv.util.Util"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Mesa> mesas = (List) request.getAttribute("mesas");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editar pedido</title>
    </head>
    <body>
        <h1>Editar pedido</h1>
        <form action="controlador" method="POST">
            <%
                Pedido p = (Pedido) request.getAttribute("pedido");
            %>
            <input type="hidden" name="op" value="opeditarpedido" />
            <input placeholder="id" type="text" name="id" value="<%=p.getId()%>" readonly="readonly" /><br/>
            <input placeholder="fechahora" type="text" name="fechahora" value="<%=p.getFechahora()%>" readonly="readonly"/><br/>
            <%
              out.println("<select name=\"mesa\"><option value=\"\">&nbsp;</option>");
              for(Mesa m: mesas){
                  if(p.getMesa().getId()==m.getId()){
                      out.println("<option value=\""+m.getId()+"\" selected>"+m.getNombre()+"</option>");
                  } else{
                      out.println("<option value=\""+m.getId()+"\">"+m.getNombre()+"</option>");
                  }                  
              }
              out.println("</select>");
            %>
            <br/>
            <%
              String[][] valores={{"0", "Abierto"},{"1", "Cerrado"}};
              out.println(Util.select("cerrado", valores, p.getCerrado()+""));
            %>
            <br/>
            <input type="hidden" name="pk" value="<%=p.getId()%>" />
            <input type="submit" value="Editar" />
        </form>
        <h4><a href="controlador?op=vistapedido">Pedido</a></h4>
        <h3><a href=".">Inicio</a></h3>
    </body>
</html>