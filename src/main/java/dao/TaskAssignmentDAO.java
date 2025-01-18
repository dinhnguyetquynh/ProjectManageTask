package dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.TaskAssignment;

public class TaskAssignmentDAO implements DAOInterface<TaskAssignment> {
 
    private EntityManager em;

    public TaskAssignmentDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public boolean add(TaskAssignment taskAssignment) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            em.persist(taskAssignment);
            tr.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            tr.rollback();
        }
        return false;
    }

    @Override
    public boolean update(TaskAssignment taskAssignment) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            em.merge(taskAssignment);
            tr.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            tr.rollback();
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            TaskAssignment taskAssignment = em.find(TaskAssignment.class, id);
            if (taskAssignment != null) {
                em.remove(taskAssignment);
            }
            tr.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            tr.rollback();
        }
        return false;
    }

    @Override
    public TaskAssignment findById(String id) {
        return em.find(TaskAssignment.class, id);
    }

    @Override
    public List<TaskAssignment> getAll() {
        String query = "SELECT ta FROM TaskAssignment ta";

        try {
            return em.createQuery(query, TaskAssignment.class).getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
