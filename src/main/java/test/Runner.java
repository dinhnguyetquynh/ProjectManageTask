package test;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import datafaker.DataFakerGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import model.Account;
import model.Gender;
import model.Project;
import model.Task;
import model.TaskAssignment;
import model.User;
import model.UserProject;
import net.datafaker.Faker;
import service.AccountService;
import util.JPAUtil;

public class Runner {
	public static void main(String[] args) {
	//TẠO DỮ LIỆU GIẢ
		EntityManager em = JPAUtil.getEntityManager();
		EntityTransaction tr = em.getTransaction();
		Faker faker = new Faker(new Locale("vi"));
		DataFakerGenerator datafaker = new DataFakerGenerator();
		try {
			tr.begin();
			// tạo quản lí
			User quanli = datafaker.generateFakeUsers();
			em.persist(quanli);
			em.flush();
			
			//tạo tài khoản quản lí
			Account taikhoanquanli = datafaker.generateFakerAccounts(quanli);
			em.persist(taikhoanquanli);
			

			// tạo nhân viên
			for (int i = 0; i < 10; i++) {
				User nhanvien = datafaker.generateFakeUsers();
				nhanvien.setManager(quanli);
				em.persist(nhanvien);
				em.flush();
				
				//tạo tài khoản nhân viên
				Account taikhoannhanvien = datafaker.generateFakerAccounts(nhanvien);
				em.persist(taikhoannhanvien);

			}
			
//			Tạo project
			Project project1 = datafaker.generateProject();
			em.persist(project1);
			em.persist(new UserProject(quanli, project1));
			
			Project project2 = datafaker.generateProject();
			em.persist(project2);
			em.persist(new UserProject(quanli, project2));
			
	        
			
//			Chọn những nhân viên được phép tham gia Project
			String query2 = "select user from User user ";
			List<User> listUser = em.createQuery(query2, User.class).getResultList();
			for (int i = 0; i < 5; i++) {
				if (i >= listUser.size()) {
					break;
				}
//				Bỏ qua quản lý
				if(listUser.get(i).getManager() == null) {
					continue;
				}
				em.persist(new UserProject(listUser.get(i), project1));
			}
			
			// Tạo task lớn
			Task task1 = datafaker.generateTask(project1, null);
			em.persist(task1);
//			// Giao project cho nhân viên
			String query4 = "select user from User user join UserProject up on user.id = up.user.id where user.id is not null";
	        List<User> listUser2 = em.createQuery(query4, User.class).getResultList();
	        Random random = new Random();
	        for (User user : listUser2) {
				int r = random.nextInt(2);
				if (r == 1) {
					em.persist(new TaskAssignment(task1, user));
				}
			}
			
//			Tạo project con
	        Task childTask1 = datafaker.generateTask(project1, task1);
	        Task childTask2 = datafaker.generateTask(project1, task1);
	        em.persist(childTask1);
	        em.persist(childTask2);

			tr.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tr.rollback();
		} finally {
			em.close();
		}
		

	}

}
