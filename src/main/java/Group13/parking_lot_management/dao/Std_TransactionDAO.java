package Group13.parking_lot_management.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import Group13.parking_lot_management.model.Std_Transaction;

public class Std_TransactionDAO implements DAOInterface<Std_Transaction>{

	@SuppressWarnings("unchecked")
	@Override
	public List<Std_Transaction> selectAll() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM Std_Transaction");
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

	@Override
	public boolean delete(Std_Transaction e) {
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
