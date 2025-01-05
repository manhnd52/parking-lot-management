package dao;

import java.util.List;

import jakarta.persistence.*;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.ParkingHistory;
import model.ParkingLot;
import model.Std_Transaction;
import model.Student;

public class StudentDAO implements DAOInterface<Student> {


    public Student getByKey(String id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Student.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Student> selectAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM model.Student");
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean saveOrUpdate(Student e) {
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

    public boolean insert(Student e) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tr = session.beginTransaction();
            session.save(e);
            tr.commit();
            return true;
        }catch(Exception et){
            return false;
        }
    }

    @Override
    public boolean delete(Student e) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tr = session.beginTransaction();
            session.saveOrUpdate(e);
            session.delete(e);
            tr.commit();
            return true;
        } catch (Exception er) {
            er.printStackTrace();
        }
        return false;
    }

    public List<Std_Transaction> getTransactionHistory(String studentId) {
        return (new Std_TransactionDAO()).getByStudentId(studentId);
    }

    public List<Std_Transaction> getTransactionHistory(Student student) {
        return (new Std_TransactionDAO()).getByStudentId(student.getStudent_id());
    }

    public List<ParkingHistory> getParkingHistory(String studentId) {
        return (new ParkingHistoryDAO()).getByStudentId(studentId);
    }

    public List<Std_Transaction> getParkingHistory(Student student) {
        return (new Std_TransactionDAO()).getByStudentId(student.getStudent_id());
    }

    // Return the parking lot now
    public ParkingLot getCurrentPosition(Student student) {
        return (new ParkingHistoryDAO()).getCurrentPosition(student);
    }

    public ParkingLot getCurrentPosition(String studentId) {
        Student std = getByKey(studentId);
        return (new ParkingHistoryDAO()).getCurrentPosition(std);
    }

    @Override
    public Student getByKey(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Student.class, String.valueOf(id));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}