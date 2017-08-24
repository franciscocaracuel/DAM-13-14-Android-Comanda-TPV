<%@page import="hibernate.Pedido"%>
<%@page import="hibernate.Detallepedido"%>
<%@page import="hibernate.Carta"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Detallepedido> detallepedidos = (List) request.getAttribute("detallepedidos");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Vista detalle pedidos</title>
        <script type="text/javascript" src="scripts/script.js"></script>
    </head>
    <body>
        <h1>Detalle Pedidos</h1>
        <h3><a href="controlador?op=vistainsertardetallepedido&idpedido=<%=request.getParameter("id")%>">Agregar detalle pedidos</a></h3>
        <table border="1">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Pedido</th>
                    <th>Carta</th>
                    <th>Cantidad</th>
                    <th>Precio</th>
                    <th colspan="2">Operaciones</th>
                </tr>
            </thead>
            <tbody>
                <%
                Carta c;
                Pedido p;
                for (Detallepedido dp: detallepedidos) {
                    c=dp.getCarta();
                    p=dp.getPedido();
                %>
                    <tr>
                        <td><%=dp.getId()%></td>
                        <td><%=p.getId()%></td>
                        <td><%=c.getNombre()%></td>
                        <td><%=dp.getCantidad()%></td>
                        <td><%=dp.getPrecio()%></td>
                        <td><a href="?op=vistaeditardetallepedido&id=<%=dp.getId()%>">Editar</a></td>
                        <td><a id="<%=dp.getId()%>" idpedido="<%=p.getId()%>" class="borrardetallepedido" href="#">Borrar</a></td>
                    </tr>
                <%
                }
                %>
            </tbody>
        </table>
        <h3><a href="controlador?op=vistapedido">Pedidos</a></h3>
        <h3><a href=".">Inicio</a></h3>
    </body>
</html>