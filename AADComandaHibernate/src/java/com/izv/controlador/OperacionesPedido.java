/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.izv.controlador;

import hibernate.Mesa;
import hibernate.Pedido;
import hibernate.PedidoMesa;
import java.util.Date;
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
public class OperacionesPedido {

    private final RespuestaControlador rc = new RespuestaControlador();

    public RespuestaControlador borrarPedido(HttpServletRequest request) {
        rc.setForward(false);
        String id = request.getParameter("id");
       
        Session session=HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        int r=0;
        
        try{
            Pedido p=(Pedido)session.load(Pedido.class, Integer.parseInt(id));
            r=p.getId();
            session.delete(p);
            session.getTransaction().commit();
            session.flush();
        }catch(ConstraintViolationException e){
            session.getTransaction().rollback();
            r=-1;
        }
        
        session.close();
        
        rc.setDestino("controlador?op=vistapedido&delete=&r=" + r);
        return rc;
    }

    public RespuestaControlador editarPedido(HttpServletRequest request) {
        rc.setForward(false);
        String id = request.getParameter("id");
        String fechahora = request.getParameter("fechahora");
        String idmesa = request.getParameter("mesa");
        String cerrado = request.getParameter("cerrado");
        String pk = request.getParameter("pk");
        
        Session session=HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        long r=0;
        
        try{
            Mesa m=(Mesa)session.load(Mesa.class, Integer.parseInt(idmesa));
                        
            Pedido p=(Pedido)session.load(Pedido.class, Integer.parseInt(pk));
            
            p.setMesa(m);
            p.setCerrado(Byte.parseByte(cerrado));
            
            session.update(p);
            
            r=p.getId();
            
            session.getTransaction().commit();
            session.flush();
            
        }catch(ConstraintViolationException e){
            session.getTransaction().rollback();
            r=-1;
        }
        
        rc.setDestino("controlador?op=vistapedido&update=&r=" + r);
        return rc;
    }

    public RespuestaControlador insertarPedido(HttpServletRequest request) {
        rc.setForward(false);
        String idmesa = request.getParameter("mesa");
        String cerrado = request.getParameter("cerrado");
        
        Session session=HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        long r=0;
        
        try{
            Mesa m=(Mesa)session.load(Mesa.class, Integer.parseInt(idmesa));
                        
            Pedido p=new Pedido(m, new Date(), Byte.parseByte(cerrado));
            p.setId(0);
            
            r=p.getId();
            
            session.save(p);
            
            session.getTransaction().commit();
            session.flush();
        } catch(ConstraintViolationException e){
            session.getTransaction().rollback();
        }
        
        
        session.close();
        
        rc.setDestino("controlador?op=vistapedido&insert=&r=" + r);
        return rc;
    }

    public RespuestaControlador verEditarPedido(HttpServletRequest request) {
        rc.setForward(true);
        rc.setDestino("WEB-INF/pedido/editar.jsp");
        String id = request.getParameter("id");
        
        Session session=HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        String hql="from Mesa";
        Query q=session.createQuery(hql);
        List<Mesa> mesas=q.list();
        
        Hibernate.initialize(mesas);
        
        request.setAttribute("mesas", mesas);
        
        Pedido p=(Pedido)session.load(Pedido.class, Integer.parseInt(id));
        
        Hibernate.initialize(p);
        
        request.setAttribute("pedido", p);
                
        session.close();
        
        return rc;
    }

    public RespuestaControlador verInsertarPedido(HttpServletRequest request) {
        rc.setForward(true);
        rc.setDestino("WEB-INF/pedido/insertar.jsp");
        
        Session session=HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        String hql="from Mesa";
        Query q=session.createQuery(hql);
        List<Mesa> mesas=q.list();
        
        Hibernate.initialize(mesas);
        
        request.setAttribute("mesas", mesas);
        
        session.close();
        
        return rc;
    }

    public RespuestaControlador verPedidos(HttpServletRequest request) {
        rc.setForward(true);
        rc.setDestino("WEB-INF/pedido/vista.jsp");
        
        Session session=HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        String hql="from Pedido";
        Query q=session.createQuery(hql);
        List<Pedido> pedido=q.list();
        
        for(int i=0; i<pedido.size(); i++){
            System.out.println(pedido.get(i).getMesa().toString());
        }
        
        Hibernate.initialize(pedido);
              
        request.setAttribute("pedido", pedido);
        
        return rc;
    }
    
}