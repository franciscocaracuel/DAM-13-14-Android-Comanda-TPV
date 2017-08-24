<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Index JSP Sesiones</h1>
        <%
        String usuario = (String) session.getAttribute("usuario");
        if(usuario==null){
            out.println("Sin sesión");
        }
        else{
            out.println("Con sesión");
            out.print("<a href='logout.jsp'>Logout</a>");
        }
        %>
        
        <br/><br/>
        
        <form action="login.jsp" method="POST">
            
            <label>Usuario</label>
            <input type="text" name="usuario"/>
            <br/>
            <label>Password</label>
            <input type="password" name="pass"/>
            <br/>
            <input type="submit" name="submit" value="Aceptar" id="submit" style="margin-top: 3%;">
            
        </form>
                
    </body>
</html>
