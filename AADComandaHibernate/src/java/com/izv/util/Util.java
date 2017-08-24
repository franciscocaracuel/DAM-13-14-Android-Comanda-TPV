package com.izv.util;

import javax.servlet.http.HttpServletRequest;

public class Util {
    
    private Util(){
    }

    public static String getMensaje(HttpServletRequest request){
        String r=request.getParameter("r");
        if(r!=null){
            int i=Integer.parseInt(r);
            String insert=request.getParameter("insert");
            if(insert!=null){
                if(i>0)
                    return i+" elementos insertados";
                return "no se ha podido insertar";
            }
            String update=request.getParameter("update");
            if(update!=null){
                if(i>0)
                    return i+" elementos editados";
                return "no se ha podido editar";
            }
            String delete=request.getParameter("delete");
            if(delete!=null){
                if(i>0)
                    return i+" elementos borrados";
                return "no se ha podido borrar";
            }
        }
        return "";
    }

    public static String select(String name, String[] values, String value) {
        String todo = "<select name=\""+name+"\" id=\""+name+"\" >"
                + "<option value=\"\">&nbsp;</option>";
        String selected;
        for(String valor: values){
            selected = "";
            if (value.length() > 0 && valor.equals(value)) {
                selected = "selected=\"selected\"";
            }
            todo += "<option value=\"" + valor+ "\" " + selected + ">"
                    + valor + " " + "</option>";
        }
        todo += "</select>";
        return todo;
    }
    
    public static String select(String name, String[][] values, String value) {
        String todo = "<select name=\""+name+"\" id=\""+name+"\" >"
                + "<option value=\"\">&nbsp;</option>";
        String selected;
        for(String[] valor: values){
            selected = "";
            if (value.length() > 0 && valor[0].equals(value)) {
                selected = "selected=\"selected\"";
            }
            todo += "<option value=\"" + valor[0]+ "\" " + selected + ">"
                    + valor[1] + " " + "</option>";
        }
        todo += "</select>";
        return todo;
    }
}