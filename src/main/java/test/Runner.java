package test;

import java.util.List;
import java.util.Locale;

import datafaker.DataFakerGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import model.Gender;
import model.User;
import net.datafaker.Faker;

public class Runner {
	public static void main(String[] args) {
		EntityManager em = Persistence.createEntityManagerFactory("task").createEntityManager();
		
		EntityTransaction tr = em.getTransaction();
		Faker faker = new Faker(new Locale("vi"));
		DataFakerGenerator datafaker = new DataFakerGenerator();
		try {
			tr.begin();
			//tạo quản lí
			User quanli = datafaker.generateFakeUsers();
			em.persist(quanli);
			em.flush();
		
			//tạo nhan viên
			for(int i=0;i<10;i++) {
				User nhanvien = datafaker.generateFakeUsers();
				nhanvien.setManager(quanli);
				em.persist(nhanvien);
			}
			
			
			tr.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tr.rollback();
		}
		
	}

}
