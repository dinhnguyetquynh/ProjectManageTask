package dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.User;

public class UserDAO implements DAOInterface<User> {

	private EntityManager em;

	public UserDAO(EntityManager em) {
		this.em = em;
	}

	@Override
	public boolean add(User user) {
		EntityTransaction tr = em.getTransaction();
		try {
			
			tr.begin();
			em.persist(user);
			tr.commit();
			return true;
		} catch (Exception ex) {
			tr.rollback();
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(User user) {
		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			em.merge(user);
			tr.commit();
			return true;
		} catch (Exception ex) {
			tr.rollback();
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(User user) {
		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			em.remove(user);
			tr.commit();
			return true;
		} catch (Exception ex) {
			tr.rollback();
			ex.printStackTrace();
			return false;
		}
		
	}

	@Override
	public User findById(int id) {
		EntityTransaction tr = em.getTransaction(); 
		User u = null;
		try {
			tr.begin();
			u=em.find(User.class, id);
			tr.commit();
			return u;
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return null;
			
		}
	}

	@Override
	public List<User> getAll() {
		EntityTransaction tr = em.getTransaction(); 
		String query = "SELECT user FROM User user WHERE user.manager.id is not null";
		List<User> list = null;
		try {
			tr.begin();
			list= em.createQuery(query, User.class).getResultList();
			tr.commit();
			return list;
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return null;
		}
	}

	//Tìm user bằng list<ingteger> ids;
	public List<User> findUsersByIds(List<Integer> selectedUserIds) {
	    if (selectedUserIds == null || selectedUserIds.isEmpty()) {
	        return null;
	    }
	    EntityTransaction tr = em.getTransaction();
	    List<User> users = null;
	    try {
	        tr.begin();
	        users = em.createQuery("SELECT u FROM User u WHERE u.id IN :ids", User.class)
	                  .setParameter("ids", selectedUserIds)
	                  .getResultList();
	        tr.commit();
	        return users;
	    } catch (Exception e) {
	        tr.rollback();
	        e.printStackTrace();
	        return null;
	    }
	}

	
}
