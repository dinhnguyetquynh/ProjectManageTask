package service;

import java.util.List;

import dao.ProjectDAO;
import jakarta.persistence.EntityManager;
import model.Project;

public class ProjectService implements ServiceInterface<Project>{
	private EntityManager em;
	private ProjectDAO pDao;
	
	

	public ProjectService(EntityManager em) {
		this.em = em;
		this.pDao = new ProjectDAO(em);
	}

	@Override
	public void add(Project p) {
		boolean add = pDao.add(p);
		if(add==true) {
			System.out.println("Tạo project thành công");
		}else {
			System.out.println("Tạo project thất bại");
		}
		
	}

	@Override
	public void update(Project p) {
		boolean update = pDao.update(p);
		if(update==true) {
			System.out.println("Cập nhật project thành công");
		}else {
			System.out.println("Cập nhật project thất bại");
		}
		
	}

	@Override
	public void delete(Project p) {
		boolean del = pDao.delete(p);
		if(del==true) {
			System.out.println("Xóa project thành công");
		}else {
			System.out.println("Xóa project thất bại");
		}
		
	}

	@Override
	public Project findById(int id) {
		Project p = pDao.findById(id);
		if(p!=null) {
			System.out.println("Tìm thấy project");
			return p;
		}else {
			System.out.println("Không tìm thấy project");
			return null;
		}
	}

	@Override
	public List<Project> getAll() {
		List<Project> list = pDao.getAll();
		if(list!=null) {
			System.out.println("Lấy danh sách project thành công");
			return list;
		}else {
			System.out.println("Lấy danh sách project thất bại");
			return null;
		}
	}

}
