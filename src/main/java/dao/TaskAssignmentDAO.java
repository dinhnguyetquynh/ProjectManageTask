package dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import model.TaskAssignment;
import model.User;

public class TaskAssignmentDAO implements DAOInterface<TaskAssignment> {
	private EntityManager em;
	
	

	public TaskAssignmentDAO(EntityManager em) {
		this.em = em;
	}
	

		//Gán user vào task
		@Override
		public boolean add(TaskAssignment t) {
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
		public boolean delete(TaskAssignment t) {
			return false;
		}
		
		
		
		@Override
		public boolean update(TaskAssignment t) {
			// TODO Auto-generated method stub
			return false;
		}
		
		
		
		@Override
		public TaskAssignment findById(int id) {
			// TODO Auto-generated method stub
			return null;
		}
		
		
		//Không cần chức năng này , vì không sử dụng được trong thực tế
		@Override
		public List<TaskAssignment> getAll() {
			EntityTransaction tr = em.getTransaction();
			List<TaskAssignment> list = null;
			String query = "Select ta from TaskAssignment ta";
			try {
				tr.begin();
				list = em.createQuery(query, TaskAssignment.class).getResultList();
				tr.commit();
				return list;
				
			} catch (Exception e) {
				// TODO: handle exception
				tr.rollback();
				e.printStackTrace();
				return null;
			}
		}
		
		//Lấy danh sách user có trong 1 task
		public List<TaskAssignment> getUsersFromTask(int id){
			EntityTransaction tr = em.getTransaction();
			List<TaskAssignment> list = null;
			String query = "Select ta from TaskAssignment ta where ta.task.id=:id";
			try {
				tr.begin();
					list = em.createQuery(query,TaskAssignment.class).setParameter("id", id).getResultList();
				tr.commit();
				return list;
			} catch (Exception e) {
				tr.rollback();
				e.printStackTrace();
				return null;
			}
		}
			

		//Xóa user ra khỏi task
		public boolean deleteUserFromTask(int userId,int taskId) {
			EntityTransaction tr = em.getTransaction();
			String query ="Delete from TaskAssignment ta where ta.employee.id=:userId and ta.task.id=:taskId ";
			try {
				tr.begin();
					em.createQuery(query).setParameter("userId", userId).setParameter("taskId", taskId).executeUpdate();
				tr.commit();
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				tr.rollback();
				e.printStackTrace();
				return false;
			}
		}
		
		

}
