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
	public boolean add(Task task) {
		// TODO Auto-generated method stub
		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			em.persist(task);
			tr.commit();
			return true;
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
			tr.rollback();
		}
		return false;
	}

	@Override
	public boolean update(Task task) {
		// TODO Auto-generated method stub
		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			em.merge(task);
			tr.commit();
			return true;
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
			tr.rollback();
		}
		return false;
	}

	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            Task task = em.find(Task.class, id);
            if (task != null) {
                em.remove(task);
            }
            tr.commit();
        } catch (Exception ex) {
            tr.rollback();
            ex.printStackTrace();
        }
		return false;
	}

	@Override
	public Task findById(String id) {
		// TODO Auto-generated method stub
		return em.find(Task.class, id);
	}

	@Override
	public List<Task> getAll() {
		String query = "SELECT task FROM Task task WHERE task.manager.id is not null";
				
		try {
			return em.createQuery(query, Task.class).getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

}
