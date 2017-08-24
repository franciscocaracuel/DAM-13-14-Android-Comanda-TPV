<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Agregar carta</title>
    </head>
    <body>
        <h1>Agregar carta</h1>
        <form action="controlador" method="POST">
            <input type="hidden" name="op" value="opinsertarcarta" />
            <input placeholder="Nombre" type="text" name="carta" value="" /><br/>
            <input placeholder="Precio" type="text" name="precio" value="" /><br/>
            <input type="submit" value="Agregar" />
        </form>
        <h4><a href="controlador?op=vistacarta">Cartas</a></h4>
        <h3><a href=".">Inicio</a></h3>
    </body>
</html>