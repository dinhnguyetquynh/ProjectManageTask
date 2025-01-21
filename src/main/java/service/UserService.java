package service;

import java.util.List;
import dao.UserDAO;
import jakarta.persistence.EntityManager;
import model.User;

public class UserService implements ServiceInterface<User>{
	private UserDAO userDAO;
	private EntityManager em;
	
	

	public UserService(EntityManager em) {
		this.em = em;
		this.userDAO= new UserDAO(em);
	}



	@Override
	public void add(User t) {
		// TODO Auto-generated method stub
		userDAO.addUser(t);
	}



	@Override
	public void update(User t) {
		// TODO Auto-generated method stub
		userDAO.updateUser(t);
		
	}



	@Override
	public void delete(User t) {
		// TODO Auto-generated method stub
		userDAO.deleteUser(t);
		
	}



	@Override
	public User findById(int id) {
		return userDAO.findUserById(id);
	}



	@Override
	public List<User> getAll() {
		return userDAO.getAll();
	}


}
