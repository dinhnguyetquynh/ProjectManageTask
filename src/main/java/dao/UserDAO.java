package dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.User;

public class UserDAO  implements DAOInterface<User>{
	
	private EntityManager em;

    public UserDAO(EntityManager em) {
        this.em = em;
    }

	@Override
	public void addUser(User t) {
		em.persist(t);
		
	}

	@Override
	public void deleteUser(User t) {
		// TODO Auto-generated method stub
		em.remove(t);
	}

	@Override
	public void updateUser(User t) {
		// TODO Auto-generated method stub
		em.merge(t);
	}

	@Override
	public User findUserById(int id) {
		return em.find(User.class,id);
	}

	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		String query ="SELECT u FROM User u";
		return em.createQuery(query, User.class).getResultList();
	}

	
//	public void addNewUser(User user) {
//		em.persist(user);
//	}
//	
//	public User findUserById(int id) {
//		return em.find(User.class,id);
//	}
}
