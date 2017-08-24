package com.izv.controlador;


import hibernate.Mesa;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

public class OperacionesMesa {

    private final RespuestaControlador rc = new RespuestaControlador();

    public RespuestaControlador borrarMesa(HttpServletRequest request) {
        
        rc.setForward(false);
        
        String id = request.getParameter("id");
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        long r=0;
        
        try{
            Mesa m= (Mesa) session.load(Mesa.class, Integer.parseInt(id));
            r = m.getId();
            session.delete(m);
            session.getTransaction().commit();
            session.flush();
        }catch(ConstraintViolationException e){
            session.getTransaction().rollback();
            r=-1;
        }        
        
        session.close();
        
        rc.setDestino("controlador?op=vistamesa&delete=&r=" + r);
        
        return rc;
        
    }

    public RespuestaControlador editarMesa(HttpServletRequest request) {
        
        rc.setForward(false);
        
        String id = request.getParameter("id");
        String mesa = request.getParameter("mesa");
        String pk = request.getParameter("pk");
        
        Session session=HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        long r=0;
        
        try{
            Mesa m=(Mesa)session.load(Mesa.class, Integer.parseInt(id));
            m.setNombre(mesa); 
            session.update(m);
            session.getTransaction().commit();
            session.flush();
        } catch(ConstraintViolationException e){
            session.getTransaction().rollback();
        }
        
        
        session.close();
        
        rc.setDestino("controlador?op=vistamesa&update=&r=" + r);
        
        return rc;
        
    }

    public RespuestaControlador insertarMesa(HttpServletRequest request) {

        rc.setForward(false);
        rc.setDestino("controlador?op=vistamesa");

        String mesa = request.getParameter("mesa");

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Mesa m = new Mesa(mesa);

        long r = 0;

        try {
            session.save(m);
            r = m.getId();
            session.getTransaction().commit();
            session.flush();
        } catch (ConstraintViolationException e) {
            r = -1;
            session.getTransaction().rollback();
        } finally {
            rc.setDestino("controlador?op=vistamesa&id=&r=" + r);
        }

        session.close();
        
        return rc;

    }

    public RespuestaControlador verEditarMesa(HttpServletRequest request) {
        rc.setForward(true);
        rc.setDestino("WEB-INF/mesa/editar.jsp");
        
        String id = request.getParameter("id");
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        Mesa m = (Mesa) session.load(Mesa.class, Integer.parseInt(id));
        
        Hibernate.initialize(m);
        
        session.getTransaction().commit();
        session.flush();
        session.close();
                
        request.setAttribute("mesa", m);
        return rc;
    }

    public RespuestaControlador verInsertarMesa() {
        rc.setForward(true);
        rc.setDestino("WEB-INF/mesa/insertar.jsp");
        return rc;
    }

    public RespuestaControlador verMesas(HttpServletRequest request) {

        rc.setForward(true);
        rc.setDestino("WEB-INF/mesa/vista.jsp");

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        String hql = "from Mesa";
        Query q = session.createQuery(hql);
        List<Mesa> mesas = q.list();

        request.setAttribute("mesas", mesas);

        session.getTransaction().commit();
        session.flush();
        session.close();

        return rc;
    }
        
}