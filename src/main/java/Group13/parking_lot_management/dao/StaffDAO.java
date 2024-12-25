package Group13.parking_lot_management.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import Group13.parking_lot_management.model.*;

public class StaffDAO implements DAOInterface<Staff> {

	@SuppressWarnings("unchecked")
	@Override
	public List<Staff> selectAll() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Query<Staff> query = session.createQuery("FROM model.Staff");
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Staff getByKey(int id) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.get(Staff.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean saveOrUpdate(Staff e) {
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
	public boolean delete(Staff e) {
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

}