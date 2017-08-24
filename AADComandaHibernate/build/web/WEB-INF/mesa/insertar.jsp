<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Agregar mesa</title>
    </head>
    <body>
        <h1>Agregar mesa</h1>
        <form action="controlador" method="POST">
            <input type="hidden" name="op" value="opinsertarmesa" />
            <input placeholder="Mesa" type="text" name="mesa" value="" /><br/>
            <input type="submit" value="Agregar" />
        </form>
        <h4><a href="controlador?op=vistamesa">Mesas</a></h4>
        <h3><a href=".">Inicio</a></h3>
    </body>
</html>