package dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Task;
import model.TaskAssignment;
import model.User;

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
	        
	        // Xóa các bản ghi liên quan trong bảng task_assignments trước
	        String deleteAssignmentsQuery = "DELETE FROM TaskAssignment ta WHERE ta.task = :task";
	        em.createQuery(deleteAssignmentsQuery)
	          .setParameter("task", t)
	          .executeUpdate();
	        
	        // Sau đó xóa Task
	        em.remove(t);
	        
	        tr.commit();
	        return true;
	    } catch (Exception ex) {
	        if (tr.isActive()) tr.rollback();
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
	
	//Tìm task bằng tên project
	public List<Task> findByProjectId(int projectId) {
	    EntityTransaction tr = em.getTransaction();
	    List<Task> list = null;
	    try {
	        tr.begin();
	        String query = "SELECT t FROM Task t WHERE t.project.id = :projectId";
	        list = em.createQuery(query, Task.class)
	                 .setParameter("projectId", projectId)
	                 .getResultList();
	        tr.commit();
	        return list;
	    } catch (Exception e) {
	        tr.rollback();
	        e.printStackTrace();
	        return null;
	    }
	}
	
	// Thêm Task và danh sách người được giao task
	public boolean createTaskWithAssignments(Task task, List<User> assignees) {
	    EntityTransaction tr = em.getTransaction();
	    try {
	        tr.begin();

	        // Lưu task
	        em.persist(task);

	        // Lưu assignment cho mỗi người
	        for (User user : assignees) {
	            TaskAssignment assignment = new TaskAssignment();
	            assignment.setTask(task);
	            assignment.setEmployee(user);
	            em.persist(assignment);
	        }

	        tr.commit();
	        return true;
	    } catch (Exception e) {
	        if (tr.isActive()) tr.rollback();
	        e.printStackTrace();
	        return false;
	    }
	}


}
