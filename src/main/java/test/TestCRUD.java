package test;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import dao.AccountDAO;
import dao.ProjectDAO;
import dao.TaskAssignmentDAO;
import dao.TaskDAO;
import dao.UserDAO;
import dao.UserProjectDAO;
import datafaker.DataFakerGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import model.Account;
import model.Project;
import model.Task;
import model.TaskAssignment;
import model.User;
import model.UserProject;

public class TestCRUD {

	public static void main(String[] args) {
		EntityManager em = Persistence.createEntityManagerFactory("task").createEntityManager();
		UserDAO userDAO = new UserDAO(em);
		AccountDAO accountDAO = new AccountDAO(em);
		ProjectDAO projectDAO = new ProjectDAO(em);
		TaskDAO taskDAO = new TaskDAO(em);
		TaskAssignmentDAO taskassignmentDAO = new TaskAssignmentDAO(em);
		UserProjectDAO userprojectDAO = new UserProjectDAO(em);

		DataFakerGenerator datafaker = new DataFakerGenerator();

		// tao quan li
		User manager = datafaker.generateFakeUsers();
		userDAO.add(manager);

		// Tạo tai khoan quan li
		Account managerAccount = datafaker.generateFakerAccounts(manager);
		accountDAO.add(managerAccount);

		// tao nhan vien
		for (int i = 0; i < 5; i++) {
			User employee = datafaker.generateFakeUsers();
			employee.setManager(manager);
			userDAO.add(employee);

			// Tao tai khoan nhan vien
			Account employeeAccount = datafaker.generateFakerAccounts(employee);
			accountDAO.add(employeeAccount);
		}

		// tạo project
		Project project1 = datafaker.generateProject();
		projectDAO.add(project1);
		em.persist(new UserProject(manager, project1));


		// Tao task
		Task task = datafaker.generateTask(project1, null);
		taskDAO.add(task);

		// Giao task cho nhan vien
		List<User> employees = userDAO.getAll();
        Random random = new Random();
        for (User employee : employees) {
            if (employee.getManager() != null) {
                if (random.nextBoolean()) {
                    TaskAssignment taskAssignment = new TaskAssignment(task, employee);
                    taskassignmentDAO.add(taskAssignment);
                    System.out.println("Giao Task thành công cho nhân viên: " + employee.getId());
                }
            }
        }

        
        
		em.close();
	}

}
