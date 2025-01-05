package dao;

import java.util.List;

import jakarta.persistence.*;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import model.Pending;

public class PendingDAO implements DAOInterface<Pending>{
    @SuppressWarnings("unchecked")
    @Override
    public List<Pending> selectAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM Pending");
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Pending getByKey(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Pending.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean saveOrUpdate(Pending e) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tr = session.beginTransaction();
            session.saveOrUpdate(e);
            tr.commit();
            return true;
        } catch (Exception er) {
            er.printStackTrace();
        }
        return false;
    }

    public void insert(Pending e) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tr = session.beginTransaction();
            session.save(e);
            tr.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean delete(Pending e) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tr = session.beginTransaction();
            session.delete(e);
            tr.commit();
            return true;
        } catch (Exception er) {
            er.printStackTrace();
        }
        return false;
    }

    public List<Pending> getByStudentId(String studentId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Pending> query = session.createQuery("FROM Pending t WHERE t.student.student_id=:id",
                    Pending.class);
            return query.setParameter("id", studentId).getResultList();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

}