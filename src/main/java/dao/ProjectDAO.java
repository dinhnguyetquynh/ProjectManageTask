package dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Project;
import model.User;
import model.UserProject;

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
		return null;
	}
	
	public List<Project> getAllByUserId(int userId) {
		 EntityTransaction tr = em.getTransaction();
		    List<Project> list = null;

		    // JPQL để lấy các project mà user có userId tham gia
		    String query = "SELECT DISTINCT up.project FROM UserProject up WHERE up.user.id = :userId";

		    try {
		        tr.begin();
		        list = em.createQuery(query, Project.class)
		                .setParameter("userId", userId)
		                .getResultList();

		        // Đếm số lượng user tham gia cho mỗi project
		        for (Project project : list) {
		            Long count = em.createQuery(
		                    "SELECT COUNT(up) FROM UserProject up WHERE up.project.id = :projectId", Long.class)
		                    .setParameter("projectId", project.getId())
		                    .getSingleResult();

		            project.setNumberUser(count.intValue()); // set vào trường @Transient
		        }

		        tr.commit();
		        return list;

		    } catch (Exception e) {
		        tr.rollback();
		        e.printStackTrace();
		        return null;
		    }
	}
	
	public void deleteProject(int projectId) {
		EntityTransaction tr = em.getTransaction();
		try {
	        tr.begin();

	        // Xoá dữ liệu liên quan ở user_project trước (native query hoặc JPQL)
	        em.createQuery("DELETE FROM UserProject up WHERE up.project.id = :id")
	            .setParameter("id", projectId)
	            .executeUpdate();

	        // Xoá project
	        Project pro = em.find(Project.class, projectId);
	        if (pro != null) {
	            em.remove(pro);
	        }

	        tr.commit();
	    } catch (Exception e) {
	        tr.rollback();
	        e.printStackTrace();
	    }
	}
	
	public boolean updateProjectAndUsers(Project project, List<User> newUsers) {
	    EntityTransaction tr = em.getTransaction();
	    try {
	        tr.begin();
	        
	        // Update Project
	        em.merge(project);

	        // Xóa tất cả các user cũ tham gia project này
	        em.createQuery("DELETE FROM UserProject up WHERE up.project.id = :projectId")
	            .setParameter("projectId", project.getId())
	            .executeUpdate();

	        // Thêm mới các user mới vào UserProject
	        for (User user : newUsers) {
	            UserProject userProject = new UserProject();
	            userProject.setUser(em.find(User.class, user.getId())); // nên dùng find để quản lý entity
	            userProject.setProject(em.find(Project.class, project.getId()));
	            em.persist(userProject);
	        }

	        tr.commit();
	        return true;
	    } catch (Exception e) {
	        tr.rollback();
	        e.printStackTrace();
	        return false;
	    }
	}
	

}
