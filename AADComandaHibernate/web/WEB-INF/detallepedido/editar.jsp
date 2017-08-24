<%@page import="hibernate.Detallepedido"%>
<%@page import="com.izv.util.Util"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editar detallepedido</title>
    </head>
    <body>
        <h1>Editar detallepedido</h1>
        <form action="controlador" method="POST">
            <%
                Detallepedido dp = (Detallepedido) request.getAttribute("detallepedido");
            %>
            <input type="hidden" name="op" value="opeditardetallepedido" />
            <label>Id DetallePedido</label>
            <input placeholder="id" type="text" name="id" value="<%=dp.getId()%>" readonly="readonly" /><br/>
            <label>Id Pedido</label>
            <input placeholder="idpedido" type="text" name="idpedido" value="<%=dp.getPedido().getId()%>" readonly="readonly" /><br/>
            <label>Id Carta</label>
            <input placeholder="idcarta" type="text" name="idcarta" value="<%=dp.getCarta().getId()%>" readonly="readonly"/><br/>
            <label>Cantidad</label>
            <input placeholder="cantidad" type="text" name="cantidad" value="<%=dp.getCantidad()%>" /><br/>
            <label>Precio</label>
            <input placeholder="precio" type="text" name="precio" value="<%=dp.getPrecio()%>" readonly="readonly"/><br/>
            <br/>
            <input type="hidden" name="pk" value="<%=dp.getId()%>" />
            <input type="submit" value="Editar" />
        </form>
        <h4><a href="controlador?op=vistadetallepedido&id=<%=dp.getPedido().getId()%>">Detalles pedido</a></h4>
        <h3><a href=".">Inicio</a></h3>
    </body>
</html>