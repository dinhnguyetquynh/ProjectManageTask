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
			ex.printStackTrace();
			tr.rollback();
		}
		return false;
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
			User user = em.find(User.class, id);
			if (user != null) {
				em.remove(user); // xóa người dùng nếu tồn tại
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
	public User findById(String id) {
		return em.find(User.class, id);
	}

	@Override
	public List<User> getAll() {
		String query = "SELECT user FROM User user WHERE user.manager.id is not null";

		try {
			return em.createQuery(query, User.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
