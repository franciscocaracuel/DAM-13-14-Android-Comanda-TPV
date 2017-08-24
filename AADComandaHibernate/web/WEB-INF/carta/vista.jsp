<%@page import="hibernate.Carta"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Carta> cartas = (List) request.getAttribute("cartas");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Vista cartas</title>
        <script type="text/javascript" src="scripts/script.js"></script>
    </head>
    <body>
        <h1>Cartas</h1>
        <h3><a href="controlador?op=vistainsertarcarta">Agregar cartas</a></h3>
        <table border="1">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Carta</th>
                    <th>Precio</th>
                    <th colspan="2">Operaciones</th>
                </tr>
            </thead>
            <tbody>
                <%
                for (Carta m : cartas) {
                %>
                    <tr>
                        <td><%=m.getId()%></td>
                        <td><%=m.getNombre()%></td>
                        <td><a href="?op=vistaeditarcarta&id=<%=m.getId()%>">Editar</a></td>
                        <td><a idcarta="<%=m.getId()%>" class="borrarcarta" href="#">Borrar</a></td>
                    </tr>
                <%
                }
                %>
            </tbody>
        </table>
        <h3><a href=".">Inicio</a></h3>
    </body>
</html>