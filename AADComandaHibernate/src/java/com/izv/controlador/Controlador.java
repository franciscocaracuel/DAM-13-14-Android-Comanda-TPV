package com.izv.controlador;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Controlador", urlPatterns = {"/controlador"})
public class Controlador extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /* op */
        String op = request.getParameter("op");
        RespuestaControlador rc = doOp(op, request);

        /* respuesta */
        if (rc.isForward()) {
            RequestDispatcher rd = request.getRequestDispatcher(rc.getDestino());
            rd.forward(request, response);
        } else {
            response.sendRedirect(rc.getDestino());
        }
    }

    private RespuestaControlador doOp(String op, HttpServletRequest request) {
        
        RespuestaControlador rc = null;

        if (op == null) {
            rc = new RespuestaControlador();
            rc.setForward(false);//da igual
            rc.setDestino("index.jsp");
        } else if (op.indexOf("mesa") >= 0) {
            return doOpMesa(op, request);
        } else if (op.indexOf("carta") >= 0) {
            return doOpCarta(op, request);
        } else if(op.indexOf("detallepedido") >= 0){
            return doOpDetallePedido(op, request);
        } else if(op.indexOf("pedido") >= 0){
            return doOpPedido(op, request);
        } else{
            rc = new RespuestaControlador();
            rc.setForward(false);
            rc.setDestino("index.jsp");
        }
        
        return rc;
        
    }
    
    private RespuestaControlador doOpMesa(String op, HttpServletRequest request) {
        
        RespuestaControlador rc = null;
        
        if (op.equals("vistamesa")) {
            OperacionesMesa om = new OperacionesMesa();
            rc = om.verMesas(request);
        } else if (op.equals("vistainsertarmesa")) {
            OperacionesMesa om = new OperacionesMesa();
            rc = om.verInsertarMesa();
        } else if (op.equals("opinsertarmesa")) {
            OperacionesMesa om = new OperacionesMesa();
            rc=om.insertarMesa(request);
        } else if (op.equals("opborrarmesa")) {
            OperacionesMesa om = new OperacionesMesa();
            rc=om.borrarMesa(request);
        } else if (op.equals("vistaeditarmesa")) {
            OperacionesMesa om = new OperacionesMesa();
            rc=om.verEditarMesa(request);
        } else if (op.equals("opeditarmesa")) {
            OperacionesMesa om = new OperacionesMesa();
            rc=om.editarMesa(request);
        } else{
            rc = new RespuestaControlador();
            rc.setForward(false);
            rc.setDestino("index.jsp");
        }
        
        return rc;
        
    }
    
    private RespuestaControlador doOpCarta(String op, HttpServletRequest request) {
        
        RespuestaControlador rc = null;
        
        if (op.equals("vistacarta")) {
            OperacionesCarta oc = new OperacionesCarta();
            rc = oc.verCartas(request);
        } else if (op.equals("vistainsertarcarta")) {
            OperacionesCarta oc = new OperacionesCarta();
            rc = oc.verInsertarCarta();
        } else if (op.equals("opinsertarcarta")) {
            OperacionesCarta oc = new OperacionesCarta();
            rc=oc.insertarCarta(request);
        } else if (op.equals("opborrarcarta")) {
            OperacionesCarta oc = new OperacionesCarta();
            rc=oc.borrarCarta(request);
        } else if (op.equals("vistaeditarcarta")) {
            OperacionesCarta oc = new OperacionesCarta();
            rc=oc.verEditarCarta(request);
        } else if (op.equals("opeditarcarta")) {
            OperacionesCarta oc = new OperacionesCarta();
            rc=oc.editarCarta(request);
        }else{
            rc = new RespuestaControlador();
            rc.setForward(false);
            rc.setDestino("index.jsp");
        }
        
        return rc;
        
    }
    
    private RespuestaControlador doOpDetallePedido(String op, HttpServletRequest request) {
        
        RespuestaControlador rc = null;
        
        if (op.equals("vistadetallepedido")) {
            OperacionesDetallePedido oc = new OperacionesDetallePedido();
            rc = oc.verDetallePedidos(request);
        } else if (op.equals("vistainsertardetallepedido")) {
            OperacionesDetallePedido oc = new OperacionesDetallePedido();
            rc = oc.verInsertarDetallePedido(request);
        } else if (op.equals("opinsertardetallepedido")) {
            OperacionesDetallePedido oc = new OperacionesDetallePedido();
            rc=oc.insertarDetallePedido(request);
        } else if (op.equals("opborrardetallepedido")) {
            OperacionesDetallePedido oc = new OperacionesDetallePedido();
            rc=oc.borrarDetallePedido(request);
        } else if (op.equals("vistaeditardetallepedido")) {
            OperacionesDetallePedido oc = new OperacionesDetallePedido();
            rc=oc.verEditarDetallePedido(request);
        } else if (op.equals("opeditardetallepedido")) {
            OperacionesDetallePedido oc = new OperacionesDetallePedido();
            rc=oc.editarDetallePedido(request);
        } else{
            rc = new RespuestaControlador();
            rc.setForward(false);
            rc.setDestino("index.jsp");
        }
        
        return rc;
        
    }
    
    
    private RespuestaControlador doOpPedido(String op, HttpServletRequest request) {
        
        RespuestaControlador rc = null;
        
        if (op.equals("vistapedido")) {
            OperacionesPedido oc = new OperacionesPedido();
            rc = oc.verPedidos(request);
        } else if (op.equals("vistainsertarpedido")) {
            OperacionesPedido oc = new OperacionesPedido();
            rc = oc.verInsertarPedido(request);
        } else if (op.equals("opinsertarpedido")) {
            OperacionesPedido oc = new OperacionesPedido();
            rc=oc.insertarPedido(request);
        } else if (op.equals("opborrarpedido")) {
            OperacionesPedido oc = new OperacionesPedido();
            rc=oc.borrarPedido(request);
        } else if (op.equals("vistaeditarpedido")) {
            OperacionesPedido oc = new OperacionesPedido();
            rc=oc.verEditarPedido(request);
        } else if (op.equals("opeditarpedido")) {
            OperacionesPedido oc = new OperacionesPedido();
            rc=oc.editarPedido(request);
        }else{
            rc = new RespuestaControlador();
            rc.setForward(false);
            rc.setDestino("index.jsp");
        }
        
        return rc;
        
    }
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}