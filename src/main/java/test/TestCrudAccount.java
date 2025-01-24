package test;

import jakarta.persistence.EntityManager;
import model.Account;
import model.Gender;
import model.User;
import service.AccountService;
import service.UserService;
import util.JPAUtil;

public class TestCrudAccount {
	public static void main(String[] args) {
		EntityManager em = JPAUtil.getEntityManager();
		AccountService accService = new AccountService(em);
		UserService uService = new UserService(em);
		//Thêm Account 
		User u1 = new User("Nguyễn Minh Nhi", Gender.FAMALE, 20, "minhnhi@gmail.com", null);
		uService.add(u1);
		
		Account acc1 = new Account("minhnhi29", "123", "Employee", u1);
		accService.add(acc1);
	}

}
