package dao;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.UserProject;

public class UserProjectDAO implements DAOInterface<UserProject>{
	
	private EntityManager em;
	public UserProjectDAO(EntityManager em) {
		this.em = em;
	}
 
	@Override
    public boolean add(UserProject userProject) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            em.persist(userProject);
            tr.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            tr.rollback();
        }
        return false;
    }

    @Override
    public boolean update(UserProject userProject) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            em.merge(userProject);
            tr.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            tr.rollback();
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            UserProject userProject = em.find(UserProject.class, id);
            if (userProject != null) {
                em.remove(userProject);
            }
            tr.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            tr.rollback();
        }
        return false;
    }

    @Override
    public UserProject findById(String id) {
        return em.find(UserProject.class, id);
    }

    @Override
    public List<UserProject> getAll() {
        String query = "SELECT up FROM UserProject up";

        try {
            return em.createQuery(query, UserProject.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
