package Group13.parking_lot_management.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import Group13.parking_lot_management.model.Student;

public class StudentDAO implements DAOInterface<Student> {
	
	@Override
	public Student getByKey(int id) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Student.class, String.valueOf(id));
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

}
