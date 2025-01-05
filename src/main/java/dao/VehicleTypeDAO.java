package dao;

import java.util.List;


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import model.VehicleType;

public class VehicleTypeDAO implements DAOInterface<VehicleType>{

    @Override
    public List<VehicleType> selectAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<VehicleType> query = session.createQuery("FROM VehicleType", VehicleType.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public VehicleType getByKey(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(VehicleType.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public VehicleType getByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<VehicleType> query = session.createQuery("FROM VehicleType v WHERE v.name=:name", VehicleType.class);
            return query.setParameter("name", name).uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean saveOrUpdate(VehicleType e) {
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
    public boolean delete(VehicleType e) {
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