package dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.UserProject;

public class UserProjectDAO implements DAOInterface<UserProject>{
	
	private EntityManager em;
	public UserProjectDAO(EntityManager em) {
		this.em = em;
	}
 
	//Gán nhân viên vào project
	@Override
    public boolean add(UserProject userProject) {
		EntityTransaction tr = em.getTransaction();
        try {
        	tr.begin();
            em.persist(userProject);
            tr.commit();
            return true;
        } catch (Exception ex) {
        	tr.rollback();
            ex.printStackTrace();
            return false;
        }
    }
	
	//Không cần chức năng này
	@Override
    public boolean update(UserProject userProject) {
		EntityTransaction tr = em.getTransaction();
        try {
        	tr.begin();
            em.merge(userProject);
            tr.commit();
            return true;
        } catch (Exception ex) {
        	tr.rollback();
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(UserProject up) {
    	EntityTransaction tr = em.getTransaction();
        try {
        	tr.begin();
            em.remove(up);
            tr.commit();
            return true;
        } catch (Exception ex) {
        	tr.rollback();
            ex.printStackTrace();
            return false;
        }
    }
    
    @Override
    public UserProject findById(int id) {
    	return null;
    }

    @Override
    public List<UserProject> getAll() {
       return null;
    }
    
    //Lấy danh sách user có trong 1 project
    public List<UserProject> getUsersFromProject(int id){
    	EntityTransaction tr = em.getTransaction();
    	String query = "Select up from UserProject up where up.project.id=:id";
    	List<UserProject> list = null;
    	try {
			tr.begin();
			list = em.createQuery(query, UserProject.class).setParameter("id", id).getResultList();
			tr.commit();
			return list;
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return null;
		}
    }
}