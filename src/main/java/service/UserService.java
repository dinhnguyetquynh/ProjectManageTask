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
	public void add(User user) {
		boolean add =userDAO.add(user);
		if(add==true) {
			System.out.println("Thêm nhân viên thành công");
		}else {
			System.out.println("Không thêm được nhân viên");
		}
	}



	@Override
	public void update(User user) {
		boolean update =userDAO.update(user);
		if(update==true) {
			System.out.println("Cập nhật nhân viên thành công");
		}else {
			System.out.println("Cập nhật nhân viên thất bại");
		}
		
	}



	@Override
	public void delete(User user) {
		boolean del =userDAO.delete(user);
		if(del==true) {
			System.out.println("Xóa nhân viên thành công");
		}else {
			System.out.println("Xóa nhân viên thất bại");
		}
		
		
	}



	@Override
	public User findById(int id) {
		User user =  userDAO.findById(id);
		if(user!=null) {
			System.out.println("Tìm thấy nhân viên");
			return user;
		}else {
			System.out.println("Không tìm thấy nhân viên");
			return null;
		}
	}



	@Override
	public List<User> getAll() {
		List<User> users =  userDAO.getAll();
		if(users!=null) {
			System.out.println("Lấy danh sách nhân viên thành công");
			return users;
		}else {
			System.out.println("Lấy danh sách nhân viên thất bại");
			return null;
		}
	}


}
