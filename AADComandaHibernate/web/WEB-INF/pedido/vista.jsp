<%@page import="hibernate.Pedido"%>
<%@page import="hibernate.Mesa"%>
<%@page import="hibernate.PedidoMesa"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Pedido> pedidos = (List) request.getAttribute("pedido");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Vista pedidos</title>
        <script type="text/javascript" src="scripts/script.js"></script>
    </head>
    <body>
        <h1>Pedidos</h1>
        <h3><a href="controlador?op=vistainsertarpedido">Agregar pedidos</a></h3>
        <table border="1">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Fecha/Hora</th>
                    <th>Mesa</th>
                    <th>Cerrado</th>
                    <th colspan="3">Operaciones</th>
                </tr>
            </thead>
            <tbody>
                <%
                Mesa m;
                for (Pedido p: pedidos) {
                    m=p.getMesa();
                %>
                    <tr>
                        <td><%=p.getId()%></td>
                        <td><%=p.getFechahora()%></td>
                        <td><%=m.getNombre()%></td>
                        <%
                            if(p.getCerrado()==0){
                                out.print("<td>Abierto</td>");
                            }else{
                                out.print("<td>Cerrado</td>");
                            }
                        %>
                        <td><a href="?op=vistaeditarpedido&id=<%=p.getId()%>">Editar</a></td>
                        <td><a idpedido="<%=p.getId()%>" class="borrarpedido" href="#">Borrar</a></td>
                        <td><a href="?op=vistadetallepedido&id=<%=p.getId()%>">Detalle</a></td>
                    </tr>
                <%
                }
                %>
            </tbody>
        </table>
        <h3><a href=".">Inicio</a></h3>
    </body>
</html>