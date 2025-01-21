package service;

import java.util.List;

import dao.AccountDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Account;

public class AccountService implements ServiceInterface<Account>{
	private EntityManager em;
	private AccountDAO accDao;
	
	
	
	public AccountService(EntityManager em) {
		this.em = em;
		this.accDao= new AccountDAO(em);
	}



	@Override
	public void add(Account t) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void update(Account t) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void delete(Account t) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public Account findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<Account> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	

}
