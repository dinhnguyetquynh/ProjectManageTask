package test;

import java.util.Scanner;

import dao.UserDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import model.Gender;
import model.User;

public class TestCRUD {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EntityManager em = Persistence.createEntityManagerFactory("task").createEntityManager();
		UserDAO userDAO = new UserDAO(em);
		Scanner scaner = new Scanner(System.in);
		

        // 1. Thêm người dùng mới
        User newUser = new User("Nguyen Van A", Gender.MALE, 30, "a.nguyen@example.com", null);
        boolean added = userDAO.add(newUser);
        System.out.println("Added new user: " + added);

        
        // Đóng EntityManager
        em.close();
		
	
		
	}

}
