package com.izv.controlador;

public class RespuestaControlador {

    private String destino;
    private boolean forward;

    public RespuestaControlador() {
        this("index.jsp", true);
    }

    public RespuestaControlador(String destino, boolean forward) {
        this.destino = destino;
        this.forward = forward;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public boolean isForward() {
        return forward;
    }

    public void setForward(boolean forward) {
        this.forward = forward;
    }

}