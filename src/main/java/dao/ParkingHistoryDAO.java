package dao;

import java.util.List;


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import model.*;

public class ParkingHistoryDAO implements DAOInterface<ParkingHistory>{

    @SuppressWarnings("unchecked")
    @Override
    public List<ParkingHistory> selectAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ParkingHistory> query = session.createQuery("FROM ParkingHistory");
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ParkingHistory getByKey(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(ParkingHistory.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean saveOrUpdate(ParkingHistory e) {
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


    public void insert(ParkingHistory e){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tr = session.beginTransaction();
            session.save(e);
            tr.commit();
        }catch (Exception er){
            er.printStackTrace();
        }
    }

    @Override
    public boolean delete(ParkingHistory e) {
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

    public List<ParkingHistory> getByStudentId(String studentId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ParkingHistory> query = session.createQuery("FROM ParkingHistory t WHERE t.student.student_id=:id", ParkingHistory.class);
            return query.setParameter("id", studentId).getResultList();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public ParkingLot getCurrentPosition(Student student) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ParkingLot> query = session.createQuery("SELECT t.parking_lot FROM ParkingHistory t "
                    + "WHERE t.student=:student AND t.exit_time = NULL", ParkingLot.class);
            return query.setParameter("student", student).getSingleResult();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }


    public ParkingHistory getCurrentParking(Student student) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ParkingHistory> query = session.createQuery(
                    "FROM ParkingHistory t " + "WHERE t.student=:student AND t.exit_time = NULL",
                    ParkingHistory.class);
            return query.setParameter("student", student).getSingleResult();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }


    public ParkingHistory getCurrentParking(String studentId) {
        return getCurrentParking((new StudentDAO()).getByKey(studentId));
    }
}