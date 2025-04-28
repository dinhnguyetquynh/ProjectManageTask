package service;

import java.util.List;

import dao.UserProjectDAO;
import jakarta.persistence.EntityManager;
import model.User;
import model.UserProject;

public class UserProjectService implements ServiceInterface<UserProject> {
	private EntityManager em;
	private UserProjectDAO upDao;
	
	

	public UserProjectService(EntityManager em) {
		
		this.em = em;
		this.upDao = new UserProjectDAO(em);
	}
	
	//Gán user vào project
	@Override
	public void add(UserProject up) {
		boolean add = upDao.add(up);
		if(add==true) {
			System.out.println("Gán nhân viên vào project thành công");
		}else {
			System.out.println("Gán nhân viên vào project thất bại");
		}
		
	}

	@Override
	public void update(UserProject t) {
		
		
	}
	
	//Xóa user khỏi project
	@Override
	public void delete(UserProject up) {
		boolean del = upDao.delete(up);
		if(del==true) {
			System.out.println("Xóa nhân viên khỏi project thành công");
		}else {
			System.out.println("Xóa nhân viên khỏi project thất bại");
		}
	}

	@Override
	public UserProject findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserProject> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	// Lấy danh sách nhân viên có trong project
	public List<UserProject> getUsersFromProject(int id){
		List<UserProject> list = upDao.getUsersFromProject(id);
		if(list!=null) {
			System.out.println("Lấy danh sách nhân viên từ project thành công");
			return list;	
		}else {
			System.out.println("Lỗi: Không lấy được danh sách nhân viên");
			return null;
		}
		
		
	}
	
	//Lấy danh sách user từ project id
	public List<User> getUserByProject(int id){
		return upDao.getUserListFromProject(id);
	}
	

}
