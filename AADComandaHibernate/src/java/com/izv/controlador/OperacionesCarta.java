package com.izv.controlador;

import hibernate.Carta;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

public class OperacionesCarta {

    private final RespuestaControlador rc = new RespuestaControlador();

    public RespuestaControlador borrarCarta(HttpServletRequest request) {
        rc.setForward(false);
        String id = request.getParameter("id");
        
        Session session=HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        long r=0;
        
        try{
            Carta c=(Carta)session.load(Carta.class, Integer.parseInt(id));
            r=c.getId();
            session.delete(c);
            session.getTransaction().commit();
            session.flush();
        }catch(ConstraintViolationException e){
            session.getTransaction().rollback();
            r=-1;
        }
        
        session.close();
        
        rc.setDestino("controlador?op=vistacarta&delete=&r=" + r);
     
        return rc;
    }

    public RespuestaControlador editarCarta(HttpServletRequest request) {
        rc.setForward(false);
        String id = request.getParameter("id");
        String carta = request.getParameter("carta");
        String precio = request.getParameter("precio");
        String pk = request.getParameter("pk");
        
        Session session=HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        long r=0;
        
        try{
            Carta c=(Carta)session.load(Carta.class, Integer.parseInt(pk));
            c.setNombre(carta);
            c.setPrecio(BigDecimal.valueOf(Double.parseDouble(precio)));
            r=c.getId();
            session.update(c);
            session.getTransaction().commit();
            session.flush();
        }catch(ConstraintViolationException e){
            r=-1;
            session.getTransaction().rollback();
        }
        
        session.close();
        
        rc.setDestino("controlador?op=vistacarta&update=&r=" + r);
        
        return rc;
    }

    public RespuestaControlador insertarCarta(HttpServletRequest request) {
        rc.setForward(false);
        String carta = request.getParameter("carta");
        String precio = request.getParameter("precio");
        
        Carta c=new Carta();
        c.setNombre(carta);
        c.setPrecio(BigDecimal.valueOf(Double.parseDouble(precio)));
        
        Session session=HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        long r = 0;
        
        try{
            session.save(c);
            r=c.getId();
            session.getTransaction().commit();
            session.flush();
        }catch(ConstraintViolationException e){
            r=-1;
            session.getTransaction().rollback();
        }
        
        rc.setDestino("controlador?op=vistacarta&insert=&r=" + r);
        
        return rc;
    }

    public RespuestaControlador verEditarCarta(HttpServletRequest request) {
        rc.setForward(true);
        rc.setDestino("WEB-INF/carta/editar.jsp");
        String id = request.getParameter("id");
        
        Session session=HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        Carta c=(Carta)session.load(Carta.class, Integer.parseInt(id));
        
        Hibernate.initialize(c);
        
        session.getTransaction().commit();
        session.flush();
        session.close();
        
        request.setAttribute("carta", c);
        
        return rc;
    }

    public RespuestaControlador verInsertarCarta() {
        rc.setForward(true);
        rc.setDestino("WEB-INF/carta/insertar.jsp");
        return rc;
    }

    public RespuestaControlador verCartas(HttpServletRequest request) {
        rc.setForward(true);
        rc.setDestino("WEB-INF/carta/vista.jsp");
        
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

}