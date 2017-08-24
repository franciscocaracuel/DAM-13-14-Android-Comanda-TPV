<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String usuario = request.getParameter("usuario");
    String clave = request.getParameter("pass");
    
    if(usuario!=null && clave !=null){
        if(usuario.equalsIgnoreCase("pepe") && clave.equalsIgnoreCase("pepe")){
            session.setAttribute("usuario", usuario);
        }
        else{
            session.invalidate();
        }
    }
    else{
        session.invalidate();
    }
    response.sendRedirect(".");
%>

