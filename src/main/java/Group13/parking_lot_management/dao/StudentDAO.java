package Group13.parking_lot_management.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import Group13.parking_lot_management.model.ParkingHistory;
import Group13.parking_lot_management.model.ParkingLot;
import Group13.parking_lot_management.model.Std_Transaction;
import Group13.parking_lot_management.model.Student;

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
            Query query = session.createQuery("FROM Student");
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
