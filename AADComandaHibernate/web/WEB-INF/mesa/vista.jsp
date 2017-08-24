<%@page import="hibernate.Mesa"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Mesa> mesas = (List) request.getAttribute("mesas");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Vista mesas</title>
        <script type="text/javascript" src="scripts/script.js"></script>
    </head>
    <body>
        <h1>Mesas</h1>
        <h3><a href="controlador?op=vistainsertarmesa">Agregar mesas</a></h3>
        <table border="1">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Mesa</th>
                    <th colspan="2">Operaciones</th>
                </tr>
            </thead>
            <tbody>
                <%
                for (Mesa m : mesas) {
                %>
                    <tr>
                        <td><%=m.getId()%></td>
                        <td><%=m.getNombre()%></td>
                        <td><a href="?op=vistaeditarmesa&id=<%=m.getId()%>">Editar</a></td>
                        <td><a idmesa="<%=m.getId()%>" class="borrarmesa" href="#">Borrar</a></td>
                    </tr>
                <%
                }
                %>
            </tbody>
        </table>
        <h3><a href=".">Inicio</a></h3>
    </body>
</html>