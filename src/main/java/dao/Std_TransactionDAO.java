package dao;

import java.util.List;


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import model.Std_Transaction;

public class Std_TransactionDAO implements DAOInterface<Std_Transaction>{

    @SuppressWarnings("unchecked")
    @Override
    public List<Std_Transaction> selectAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Std_Transaction> query = session.createQuery("FROM Std_Transaction");
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Std_Transaction getByKey(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Std_Transaction.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean saveOrUpdate(Std_Transaction e) {
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

    public void insert(Std_Transaction e) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tr = session.beginTransaction();
            session.save(e);
            tr.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean delete(Std_Transaction e) {
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

    public List<Std_Transaction> getByStudentId(String studentId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Std_Transaction> query = session.createQuery("FROM Std_Transaction t WHERE t.student.student_id=:id", Std_Transaction.class);
            return query.setParameter("id", studentId).getResultList();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }



}