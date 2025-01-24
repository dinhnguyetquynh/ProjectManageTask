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
    
	@Override
	public boolean add(Account t) {
		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			em.persist(t);
			tr.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
			return false;
		}
		
	}

	@Override
	public boolean delete(Account t) {
		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			em.remove(t);
			tr.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
			return false;
		}

	}

	@Override
	public boolean update(Account t) {
		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			em.merge(t);
			tr.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
			return false;
		}

	}

	@Override
	public Account findById(int id) {
		EntityTransaction tr = em.getTransaction();
		Account acc = null;
		try {
			tr.begin();
				acc = em.find(Account.class, id);
			tr.commit();
			return acc;
			
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
			return null;
			
		}
	}

	@Override
	public List<Account> getAll() {
		String query = "SELECT account FROM Account account WHERE account.manager.id is not null";
		List<Account> list = null;
		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			list= em.createQuery(query, Account.class).getResultList();
			tr.commit();
			return list;
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return null;
		}
	}

	
	public Account findAccountByUsername(String username){
    	String query = "SELECT acc FROM Account acc WHERE acc.username = :username";
    	Account acc = null;
    	EntityTransaction tr = em.getTransaction();
        try {
        	tr.begin();
            acc = em.createQuery(query, Account.class)
                    .setParameter("username", username)
                    .getSingleResult();
            tr.commit();
            return acc;
        } catch (Exception e) {
        	tr.rollback();
        	e.printStackTrace();
            return null; 
        }
    }

   

	
}
