package service;

import java.util.List;

import dao.TaskDAO;
import jakarta.persistence.EntityManager;
import model.Task;

public class TaskService implements ServiceInterface<Task>{
	private EntityManager em;
	private TaskDAO taskDao;
	 

	public TaskService(EntityManager em) {
		this.em = em;
		this.taskDao = new TaskDAO(em);
	}

	@Override
	public void add(Task t) {
		boolean add = taskDao.add(t);
		if(add==true) {
			System.out.println("Tạo task thành công");
		}else {
			System.out.println("Tạo task thất bại");
		}
	}

	@Override
	public void update(Task t) {
		boolean up = taskDao.update(t);
		if(up==true) {
			System.out.println("Cập nhật task thành công");
		}else {
			System.out.println("Cập nhật task thất bại");
		}
		
	}

	@Override
	public void delete(Task t) {
		boolean del = taskDao.delete(t);
		if(del==true) {
			System.out.println("Xóa task thành công");
		}else {
			System.out.println("Xóa task thất bại");
		}
		
	}

	@Override
	public Task findById(int id) {
		Task t = taskDao.findById(id);
		
		if(t!=null) {
			System.out.println("Tìm thấy task");
			return t;
		}else {
			System.out.println("Không tìm được task");
			return null;
		}
	}

	@Override
	public List<Task> getAll() {
		List<Task> list = taskDao.getAll();
		
		if(list!=null) {
			System.out.println("Lấy ds task thành công");
			return list;
		}else {
			System.out.println("Lấy ds task thất bại");
			return null;
		}
	}
	

}
