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
		boolean kq = accDao.add(t);
		if(kq==true) {
			System.out.println("Tạo account thành công");
		}else {
			System.out.println("Lỗi khi tạo account");
		}
		
	}



	@Override
	public void update(Account t) {
		boolean kq = accDao.update(t);
		if(kq==true) {
			System.out.println("Cập nhật account thành công");
		}else {
			System.out.println("Lỗi khi cập nhật account");
		}
		
		
	}



	@Override
	public void delete(Account t) {
		boolean kq = accDao.delete(t);
		if(kq==true) {
			System.out.println("Xóa account thành công");
		}else {
			System.out.println("Lỗi khi xóa account");
		}
		
		
	}



	@Override
	public Account findById(int id) {
		Account acc = accDao.findById(id);
		if(acc!=null) {
			System.out.println("Tìm thấy account");
			return acc;
		}else {
			System.out.println("Không tìm được account");
			return null;
		}
	}



	@Override
	public List<Account> getAll() {
		List<Account> list = accDao.getAll();
		if(list!=null) {
			System.out.println("Lấy danh sách account thành công");
			return list;
		}else {
			System.out.println("Lỗi không lấy được danh sách account");
			return null;
		}
	}
	
	public Account findAccountByUserName(String username) {
		Account acc = accDao.findAccountByUsername(username);
		if(acc!=null) {
			System.out.println("Đã tìm thấy account");
			return acc;
		}else {
			System.out.println("Lỗi không tìm thấy account");
			return null;
		}
	}
	
	
	
	

}
