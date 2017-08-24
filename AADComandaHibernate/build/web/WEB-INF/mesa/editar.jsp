<%@page import="hibernate.Mesa"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editar mesa</title>
    </head>
    <body>
        <h1>Editar mesa</h1>
        <form action="controlador" method="POST">
            <%
                Mesa m = (Mesa) request.getAttribute("mesa");
            %>
            <input type="hidden" name="op" value="opeditarmesa" />
            <input placeholder="id" type="text" name="id" value="<%=m.getId()%>" readonly="readonly" /><br/>
            <input placeholder="mesa" type="text" name="mesa" value="<%=m.getNombre()%>" /><br/>
            <input type="hidden" name="pk" value="<%=m.getId()%>" />
            <input type="submit" value="Editar" />
        </form>
        <h4><a href="controlador?op=vistamesa">Mesas</a></h4>
        <h3><a href=".">Inicio</a></h3>
    </body>
</html>