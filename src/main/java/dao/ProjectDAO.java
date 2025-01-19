package dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Project;

public class ProjectDAO implements DAOInterface<Project> {

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
			// TODO: handle exception
			tr.rollback();
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Project project) {
		// TODO Auto-generated method stub
		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			em.merge(project);
			tr.commit();
		} catch (Exception ex) {
			// TODO: handle exception
			tr.rollback();
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			Project project = em.find(Project.class, id);
			if (project != null) {
				em.remove(project);
			}
			tr.commit();
		} catch (Exception ex) {
			// TODO: handle exception
			tr.rollback();
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public Project findById(String id) {
		// TODO Auto-generated method stub
		return em.find(Project.class, id);
	}

	@Override
	public List<Project> getAll() {
		String query = "SELECT project FROM Project project WHERE project.manager.id is not null";

		try {
			return em.createQuery(query, Project.class).getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	public void delete(Project projectToDelete) {
		// TODO Auto-generated method stub
		
	}

}
