package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Account;

public class AccountDAO {
	
	private EntityManager em;

    public AccountDAO(EntityManager em){
        this.em = em;
    }
    
    public boolean addAccount(Account account) {
    	EntityTransaction tr = em.getTransaction();
        try{
            tr.begin();
            em.persist(account);
            tr.commit();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            tr.rollback();
        }
    	
    	return false;
    }
    
    public boolean updateClazz(Account account){
        EntityTransaction tr = em.getTransaction();
        try{
            tr.begin();
            em.merge(account);
            tr.commit();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            tr.rollback();
        }
        return false;
    }
    
    public boolean deleteAccount(String id){
        EntityTransaction tr = em.getTransaction();
        try{
            tr.begin();
            Account acc = em.find(Account.class, id);
            em.remove(acc);
            tr.commit();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            tr.rollback();
        }
        return false;
    }
    
    public Account findAccountById(String id){
        return em.find(Account.class, id);
    }
    
    public Account findAccountByUsername(String username){
    	String query = "SELECT acc FROM Account acc WHERE acc.username = :username";

        try {
            return em.createQuery(query, Account.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
        	e.printStackTrace();
            return null; 
        }
    }

}
