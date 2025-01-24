package dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Task;

public class TaskDAO implements DAOInterface<Task>{
	private EntityManager em;
	
	public TaskDAO(EntityManager em) {
		this.em = em;
	}

	@Override
	public boolean add(Task t) {
		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			em.persist(t);
			tr.commit();
			return true;
		} catch (Exception ex) {
			tr.rollback();
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(Task t) {
		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			em.remove(t);
			tr.commit();
			return true;
		} catch (Exception ex) {
			tr.rollback();
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Task t) {
		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			em.merge(t);
			tr.commit();
			return true;
		} catch (Exception ex) {
			tr.rollback();
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public Task findById(int id) {
		EntityTransaction tr = em.getTransaction();
		Task t = null;
		try {
			tr.begin();
			t = em.find(Task.class, id);
			tr.commit();
			return t;
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public List<Task> getAll() {
		EntityTransaction tr = em.getTransaction();
		String query = "SELECT t FROM Task t";
		List<Task> list = null;
		try {
			tr.begin();
			list= em.createQuery(query,Task.class).getResultList();
			tr.commit();
			return list;
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return null;
		}
	}
	
	//Nên viết thêm tìm task bằng tên

	
	

}
