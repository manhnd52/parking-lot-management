package Group13.parking_lot_management.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import Group13.parking_lot_management.model.ParkingHistory;

public class ParkingHistoryDAO implements DAOInterface<ParkingHistory>{

	@SuppressWarnings("unchecked")
	@Override
	public List<ParkingHistory> selectAll() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM ParkingHistory");
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

}
