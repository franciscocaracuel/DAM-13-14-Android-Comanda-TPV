/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.izv.controlador;

import hibernate.Carta;
import hibernate.Detallepedido;
import hibernate.Pedido;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

/**
 *
 * @author fran
 */
public class OperacionesDetallePedido {

    private final RespuestaControlador rc = new RespuestaControlador();

    public RespuestaControlador borrarDetallePedido(HttpServletRequest request) {
        rc.setForward(false);
        String id = request.getParameter("id");
        
        //Este idpedido es el que esta en el script borrar, se le pasa en rc.setDestino de abajo
        //para que nos mantenga todos los detallespedidos que tiene el pedido
        String idPedido=request.getParameter("idpedido");
        
        Session session=HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        int r=0;
        
        try{
            Detallepedido dp=(Detallepedido)session.load(Detallepedido.class, Integer.parseInt(id));
            r=dp.getId();
            session.delete(dp);
            session.getTransaction().commit();
            session.flush();
        }catch(ConstraintViolationException e){
            session.getTransaction().rollback();
            r=-1;
        }
        
        session.close();
        
        rc.setDestino("controlador?op=vistadetallepedido&id="+idPedido);
        return rc;
    }

    public RespuestaControlador editarDetallePedido(HttpServletRequest request) {
        rc.setForward(false);
        String id = request.getParameter("id");
        String idPedido = request.getParameter("idpedido");
        String idCarta = request.getParameter("idcarta");
        String cantidad = request.getParameter("cantidad");
        String pk = request.getParameter("pk");
        
        Session session=HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        
        Carta c=(Carta)session.load(Carta.class, Integer.parseInt(idCarta));
        Pedido p=(Pedido)session.load(Pedido.class, Integer.parseInt(idPedido));
        
        long r = 0;
        
        try{
            Detallepedido dp=(Detallepedido)session.load(Detallepedido.class, Integer.parseInt(id));
            dp.setPedido(p);
            dp.setCarta(c);
            dp.setCantidad(Integer.parseInt(cantidad));
            dp.setPrecio(BigDecimal.valueOf(Integer.parseInt(cantidad)).multiply(c.getPrecio()));
            session.update(dp);
            r=dp.getId();
            session.getTransaction().commit();
            session.flush();
        }catch(ConstraintViolationException e){
            r=-1;
            session.getTransaction().rollback();
        }
        
        session.close();                
        
        rc.setDestino("controlador?op=vistadetallepedido&id="+p.getId()+"&update=&r=" + r);
        
        return rc;
    }

    public RespuestaControlador insertarDetallePedido(HttpServletRequest request) {
        rc.setForward(false);
        String idPedido = request.getParameter("idpedido");
        String idCarta = request.getParameter("carta");        
        String cantidad = request.getParameter("cantidad");
        
        Session session=HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        
        Carta c=(Carta)session.load(Carta.class, Integer.parseInt(idCarta));
        Pedido p=(Pedido)session.load(Pedido.class, Integer.parseInt(idPedido));
        
        Detallepedido dp=new Detallepedido(c, p, Integer.parseInt(cantidad),
                c.getPrecio().multiply(BigDecimal.valueOf(Double.parseDouble(cantidad))));
        
        
        long r = 0;
        
        try{
            session.save(dp);
            r=c.getId();
            session.getTransaction().commit();
            session.flush();
        }catch(ConstraintViolationException e){
            r=-1;
            session.getTransaction().rollback();
        }
        
        session.close();
       
        rc.setDestino("controlador?op=vistadetallepedido&insert=&r=" + r+"&id="+idPedido);
        
        return rc;
    }
    
    public RespuestaControlador verEditarDetallePedido(HttpServletRequest request) {
        rc.setForward(true);
        rc.setDestino("WEB-INF/detallepedido/editar.jsp");
        String id = request.getParameter("id");
        
        Session session=HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        Detallepedido dp=(Detallepedido)session.load(Detallepedido.class, Integer.parseInt(id));
        
        Hibernate.initialize(dp);
        
        request.setAttribute("detallepedido", dp);
        
        session.close();
        
        return rc;
    }
    
    public RespuestaControlador verInsertarDetallePedido(HttpServletRequest request) {
        rc.setForward(true);
        rc.setDestino("WEB-INF/detallepedido/insertar.jsp");
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        String hql="from Carta";
        Query q=session.createQuery(hql);
        List<Carta> cartas=q.list();
        
        request.setAttribute("cartas", cartas);
        
        session.getTransaction().commit();
        session.flush();
        session.close();
        
        return rc;
    }

    public RespuestaControlador verDetallePedidos(HttpServletRequest request) {
        rc.setForward(true);
        rc.setDestino("WEB-INF/detallepedido/vista.jsp");
        
        String parametroId=request.getParameter("id");
        
        Session session=HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        String hql="from Detallepedido where idpedido="+parametroId;
        Query q=session.createQuery(hql);
        List<Detallepedido> detallepedidos=q.list();
        
        Hibernate.initialize(detallepedidos);
        
        request.setAttribute("detallepedidos", detallepedidos);
        
        
        return rc;
    }

}