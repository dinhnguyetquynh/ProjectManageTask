
package service;


import dao.AccountDAO;
import dao.UserDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Account;

public class ServiceUser {

	private UserDAO userDAO;
	private AccountDAO accDao;
	private EntityManager em;
	
	public ServiceUser(EntityManager em) {
		this.em = em;
		this.userDAO = new UserDAO(em);
		this.accDao = new AccountDAO(em);
	}
	
	public Account login(String username, String password) {
		return accDao.findAccountToLogin(username, password);
	}
	
	public boolean createAccount(Account acc) {
	    EntityTransaction transaction = em.getTransaction();

	    try {
	        transaction.begin();

	        // Tạo user trước
	        if (!userDAO.add(acc.getUser())) {
	            transaction.rollback();
	            return false; // Nếu thất bại, rollback và trả về false
	        }

	        // Nếu user tạo thành công thì tạo account
	        if (!accDao.add(acc)) {
	            transaction.rollback();
	            return false;
	        }

	        transaction.commit();
	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        if (transaction.isActive()) {
	            transaction.rollback();
	        }
	    }
	    return false;
	}
}

