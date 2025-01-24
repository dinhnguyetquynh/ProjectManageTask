package service;

import java.util.List;

import dao.TaskAssignmentDAO;
import jakarta.persistence.EntityManager;
import model.TaskAssignment;

public class TaskAssigmentService implements ServiceInterface<TaskAssignment>{
	private EntityManager em;
	private TaskAssignmentDAO taDAO;
	
	
	public TaskAssigmentService(EntityManager em) {
		this.em = em;
		this.taDAO = new TaskAssignmentDAO(em);
	}

	@Override
	public void add(TaskAssignment t) {
		boolean add = taDAO.add(t);
		if(add==true) {
			System.out.println("Gán Nhân Viên vào task thành công");
		}else {
			System.out.println("Gán nhân viên vào task thất bại");
		}
	}

	@Override
	public void update(TaskAssignment t) {
		taDAO.update(t);
	}

	@Override
	public void delete(TaskAssignment t) {
		taDAO.delete(t);
		
	}

	@Override
	public TaskAssignment findById(int id) {
		return taDAO.findById(id);
	}

	@Override
	public List<TaskAssignment> getAll() {
		return taDAO.getAll();
	}
	
	public void deleteUserFromTask(int userId,int taskId) {
		boolean del = taDAO.deleteUserFromTask(userId,taskId);
		if(del==true) {
			System.out.println("Xóa nhân viên khỏi task thành công");
		}else {
			System.out.println("Xóa nhân viên khỏi task thất bại");
		}
	}
	public List<TaskAssignment> getUsersFromTask(int id){
		List<TaskAssignment> list = taDAO.getUsersFromTask(id);
		if(list!=null) {
			System.out.println("Lấy danh sách nhân viên từ task thành công");
			return list;
		}else {
			System.out.println("Lấy danh sách nhân viên từ task thất bại");
			return null;
		}
	}

}
