package dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Project;

public class ProjectDAO implements DAOInterface<Project>{
	
	private EntityManager em;

	public ProjectDAO(EntityManager em) {
		this.em = em;
	}
	@Override
	public boolean add(Project project) {
		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			em.persist(project);
			tr.commit();
			return true;
		} catch (Exception ex) {
			tr.rollback();
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Project project) {
		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			em.merge(project);
			tr.commit();
			return true;
		} catch (Exception ex) {
			tr.rollback();
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(Project project) {
		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			em.remove(project);
			tr.commit();
			return true;
		} catch (Exception ex) {
			tr.rollback();
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public Project findById(int id) {
		EntityTransaction tr = em.getTransaction();
		Project pro = null;
		try {
			tr.begin();
			pro =  em.find(Project.class, id);
			tr.commit();
			return pro;
		} catch (Exception e) {
			// TODO: handle exception
			tr.rollback();
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public List<Project> getAll() {
		EntityTransaction tr = em.getTransaction();
		List<Project> list = null;
		
		String query = "SELECT project FROM Project project WHERE project.manager.id is not null";
		try {
			tr.begin();
			list= em.createQuery(query, Project.class).getResultList();
			tr.commit();
			return list;
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return null;
		}
	}
	
	//Viết thêm chức năng tìm project bằng tên
	

}
