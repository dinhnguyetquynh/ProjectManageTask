package dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Account;

public class AccountDAO implements DAOInterface<Account> {
	
	private EntityManager em;

    public AccountDAO(EntityManager em){
        this.em = em;
    }
    
    public boolean add(Account account) {
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
    
    public boolean update(Account account){
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
    
    public boolean delete(String id){
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
    
    public Account findById(String id){
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

	@Override
	public List<Account> getAll() {
		String query = "SELECT account FROM Account account WHERE account.manager.id is not null";
		
		try {
			return em.createQuery(query, Account.class).getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

}
