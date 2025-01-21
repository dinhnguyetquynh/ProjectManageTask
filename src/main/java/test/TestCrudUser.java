package test;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Gender;
import model.User;
import service.UserService;
import util.JPAUtil;

public class TestCrudUser {

	public static void main(String[] args) {
		   EntityManager em = JPAUtil.getEntityManager();
	        UserService userService = new UserService(em);
	        EntityTransaction transaction = em.getTransaction();
	        User newUser = null;
		
		try {
			transaction.begin();
			//LƯU Ý : MỖI LẦN TEST CHỨC NĂNG NÀO THÌ COMMENT CÁC CHỨC NĂNG KHÁC LẠI :>>>
			//Lấy danh sách nhân viên
			List<User> users = userService.getAll();
			if(users!=null) {
				for(User user :users) {
					System.out.println("User"+" "+user.getId()+":"+user.toString());
				}
			}else {
				System.out.println("Danh sách nhân viên trống");
			}
			
			
			
			//Thêm nhân viên
			   User manager = userService.findById(1);
				if(manager!=null) {
					System.out.println("Tìm được quản lí"+manager.getName());
					 newUser = new User("Nguyễn Minh Anh", Gender.FAMALE, 22, "minhanh@gmail.com", manager);
		            userService.add(newUser);
				}else {
					System.out.println("Không có quản lí");
				}   
				
			//Tìm kiếm nhân viên theo mã
			User userFind = userService.findById(30);
			if(userFind!=null) {
				System.out.println("Thông tin user cần tìm là: "+ userFind.toString());
			}else {
				System.out.println("Không tìm thấy user");
			}
			//Update nhân viên 
				User userUpdate = userService.findById(30); // Là nhân viên minh anh vừa thêm
				if(userUpdate!=null) {
					userUpdate.setEmail("minhanh202@gmail.com");
					userService.update(userUpdate);
					System.out.println("Cập nhật thông tin nhân viên thành công");
				}	
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			 if (transaction.isActive()) {
	                transaction.rollback(); // Rollback nếu có lỗi
	            }
		}finally {
			//Đóng entitymanager
			if(em!=null) {
				em.close();
			} 
		}	
	}

}
