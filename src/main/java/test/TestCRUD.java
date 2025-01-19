//package test;
//
//import java.util.List;
//import java.util.Random;
//import java.util.Scanner;
//
//import dao.AccountDAO;
//import dao.ProjectDAO;
//import dao.TaskAssignmentDAO;
//import dao.TaskDAO;
//import dao.UserDAO;
//import dao.UserProjectDAO;
//import datafaker.DataFakerGenerator;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.Persistence;
//import model.Account;
//import model.Project;
//import model.Task;
//import model.TaskAssignment;
//import model.User;
//import model.UserProject;
//
//public class TestCRUD {
//
//	public static void main(String[] args) {
//		EntityManager em = Persistence.createEntityManagerFactory("task").createEntityManager();
//		UserDAO userDAO = new UserDAO(em);
//		AccountDAO accountDAO = new AccountDAO(em);
//		ProjectDAO projectDAO = new ProjectDAO(em);
//		TaskDAO taskDAO = new TaskDAO(em);
//		TaskAssignmentDAO taskassignmentDAO = new TaskAssignmentDAO(em);
//		UserProjectDAO userprojectDAO = new UserProjectDAO(em);
//
//		DataFakerGenerator datafaker = new DataFakerGenerator();
//
//		// tao quan li
//		User manager = datafaker.generateFakeUsers();
//		userDAO.add(manager);
//		System.out.println("Tao quan li thành công: " + manager.getId());
//
//		// Tạo tai khoan quan li
//		Account managerAccount = datafaker.generateFakerAccounts(manager);
//		accountDAO.add(managerAccount);
//		System.out.println("Tao tai khoan quan li thành công: " + managerAccount.getId());
//
//		// tao nhan vien
//		for (int i = 0; i < 5; i++) {
//			User employee = datafaker.generateFakeUsers();
//			employee.setManager(manager);
//			userDAO.add(employee);
//			System.out.println("Tao nhan vien thành công: " + employee.getId());
//
//			// Tao tai khoan nhan vien
//			Account employeeAccount = datafaker.generateFakerAccounts(employee);
//			accountDAO.add(employeeAccount);
//			System.out.println("Tao tai khoan nhan vien thành công: " + employeeAccount.getId());
//			
//		}
//
//		// tạo project
//		Project project = datafaker.generateProject();
//		projectDAO.add(project);
//		em.persist(new UserProject(manager, project));
//		System.out.println("Tao Project thành công: " + project.getId());
//
//
//		// Tao task
//		Task task = datafaker.generateTask(project, null);
//		taskDAO.add(task);
//		System.out.println("Tao Task thành công: " + task.getId());
//
//		// Giao task cho nhan vien
//		List<User> employees = userDAO.getAll();
//        Random random = new Random();
//        for (User employee : employees) {
//            if (employee.getManager() != null) {
//                if (random.nextBoolean()) {
//                    TaskAssignment taskAssignment = new TaskAssignment(task, employee);
//                    taskassignmentDAO.add(taskAssignment);
//                    System.out.println("Giao Task thành công cho nhân viên: " + employee.getId());
//                }
//            }
//        }
//        
//		
//        
//        // Đóng EntityManager
//        em.close();
//
//	}
//
//}

package test;

import java.util.List;
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
		TaskAssignmentDAO taskAssignmentDAO = new TaskAssignmentDAO(em);
		UserProjectDAO userProjectDAO = new UserProjectDAO(em);
		DataFakerGenerator datafaker = new DataFakerGenerator();

		Scanner scanner = new Scanner(System.in);
		boolean running = true;

		while (running) {
			System.out.println("\n=== CRUD MENU ===");
			System.out.println("1. Tạo người dùng");
			System.out.println("2. Tạo dự án (Project)");
			System.out.println("3. Tạo Task và giao Task");
			System.out.println("4. Đọc danh sách");
			System.out.println("5. Cập nhật thông tin");
			System.out.println("6. Xóa đối tượng");
			System.out.println("7. Thoát");
			System.out.print("Chọn chức năng: ");

			int choice = scanner.nextInt();
			scanner.nextLine(); // Clear newline

			switch (choice) {
			case 1: // Tạo người dùng
				System.out.println("=== Tạo người dùng ===");
				System.out.println("1. Tạo quản lý");
				System.out.println("2. Tạo nhân viên và gán với quản lý");
				System.out.print("Chọn loại người dùng cần tạo: ");
				int userTypeChoice = scanner.nextInt();
				scanner.nextLine(); // Clear newline

				if (userTypeChoice == 1) {
					// Tạo quản lý
					System.out.print("Nhập số lượng quản lý muốn tạo: ");
					int managerCount = scanner.nextInt();
					scanner.nextLine(); // Clear newline

					for (int i = 0; i < managerCount; i++) {
						User manager = datafaker.generateFakeUsers();
						userDAO.add(manager);
						System.out.println("Tạo quản lý thành công: " + manager.getId());

						Account managerAccount = datafaker.generateFakerAccounts(manager);
						accountDAO.add(managerAccount);
						System.out.println("Tạo tài khoản cho quản lý: " + managerAccount.getId());
					}
				} else if (userTypeChoice == 2) {
					// Tạo nhân viên
					System.out.print("Nhập ID quản lý để gán nhân viên: ");
					String managerId = scanner.nextLine();
					User manager = userDAO.findById(managerId);

					if (manager == null) {
						System.out.println("Không tìm thấy quản lý với ID: " + managerId);
						break;
					}

					System.out.print("Nhập số lượng nhân viên muốn tạo: ");
					int employeeCount = scanner.nextInt();
					scanner.nextLine(); // Clear newline

					for (int i = 0; i < employeeCount; i++) {
						User employee = datafaker.generateFakeUsers();
						employee.setManager(manager); // Gán nhân viên với quản lý
						userDAO.add(employee);
						System.out.println("Tạo nhân viên thành công: " + employee.getId());

						Account employeeAccount = datafaker.generateFakerAccounts(employee);
						accountDAO.add(employeeAccount);
						System.out.println("Tạo tài khoản cho nhân viên: " + employeeAccount.getId());
					}
				} else {
					System.out.println("Lựa chọn không hợp lệ.");
				}
				break;

			case 2: // Tạo Project
				System.out.println("Tạo mới một dự án...");
				Project project = datafaker.generateProject();

				// Giao dự án cho một quản lý
				System.out.print("Nhập ID của quản lý để giao dự án: ");
				String managerIdForProject = scanner.nextLine();
				User managerForProject = userDAO.findById(managerIdForProject);

				if (managerForProject == null) {
					System.out.println("Không tìm thấy quản lý với ID: " + managerIdForProject);
					break;
				}

				// Gán quản lý cho dự án
				project.setManager(managerForProject);
				projectDAO.add(project);
				System.out.println("Tạo dự án thành công: " + project.getId() + ", được giao cho quản lý: "
						+ managerForProject.getId());

				// Tạo UserProject cho quản lý gán vào dự án
				UserProject userProject = new UserProject(managerForProject, project); // Gán người quản lý vào dự án
				userProjectDAO.add(userProject); // Lưu vào cơ sở dữ liệu
				System.out.println("Gán người quản lý " + managerForProject.getId() + " vào dự án.");
				break;

			case 3: // Tạo Task và giao Task
				System.out.print("Nhập ID của dự án để tạo Task: ");
				String projectId = scanner.nextLine();
				Project selectedProject = projectDAO.findById(projectId);
				if (selectedProject == null) {
					System.out.println("Không tìm thấy dự án với ID: " + projectId);
					break;
				}
				Task task = datafaker.generateTask(selectedProject, null);
				taskDAO.add(task);
				System.out.println("Tạo Task thành công: " + task.getId());
				System.out.println("Giao Task cho nhân viên...");
				List<User> users = userDAO.getAll();
				for (User user1 : users) {
					TaskAssignment taskAssignment = new TaskAssignment(task, user1);
					taskAssignmentDAO.add(taskAssignment);
					System.out.println("Giao Task cho nhân viên: " + user1.getId());
				}
				break;

			case 4: // Đọc danh sách
				System.out.println("Danh sách người dùng:");
				List<User> usersList = userDAO.getAll();
				if (usersList.isEmpty()) {
					System.out.println("Không có người dùng nào.");
				} else {
					for (User u : usersList) {
						System.out.println("- ID: " + u.getId() + ", Tên: " + u.getName() + ", Quản lý: "
								+ (u.getManager() != null ? u.getManager().getId() : "Không có"));
					}
				}

				System.out.println("Danh sách dự án:");
				List<Project> projects = projectDAO.getAll();
				if (projects.isEmpty()) {
					System.out.println("Không có dự án nào.");
				} else {
					for (Project p : projects) {
						System.out.println("- ID: " + p.getId() + ", Tên dự án: " + p.getTitle() + ", Quản lý: "
								+ (p.getManager() != null ? ((Project) p.getManager()).getId() : "Không có"));
					}
				}
				break;

			case 5: // Cập nhật thông tin
				System.out.print("Nhập ID người dùng cần cập nhật: ");
				String userId1 = scanner.nextLine();
				User userToUpdate = userDAO.findById(userId1);
				if (userToUpdate == null) {
					System.out.println("Không tìm thấy người dùng với ID: " + userId1);
					break;
				}
				System.out.print("Nhập tên mới: ");
				String newName = scanner.nextLine();
				userToUpdate.getId();
				userDAO.update(userToUpdate);
				System.out.println("Cập nhật thành công người dùng: " + newName);
				break;

			case 6: // Xóa đối tượng
				System.out.println("1. Xóa người dùng");
				System.out.println("2. Xóa dự án");
				System.out.println("3. Xóa Task");
				System.out.print("Chọn đối tượng muốn xóa: ");
				int deleteChoice = scanner.nextInt();
				scanner.nextLine(); // Clear newline
				if (deleteChoice == 1) {
					System.out.print("Nhập ID người dùng cần xóa: ");

					String deleteUserId = scanner.nextLine();
					User userToDelete = userDAO.findById(deleteUserId);
					if (userToDelete != null) {
						userDAO.delete(userToDelete);
						System.out.println("Xóa thành công người dùng: " + userToDelete.getId());
					} else {
						System.out.println("Không tìm thấy người dùng.");
					}
				} else if (deleteChoice == 2) {
					System.out.print("Nhập ID dự án cần xóa: ");
					String deleteProjectId = scanner.nextLine();
					Project projectToDelete = projectDAO.findById(deleteProjectId);
					if (projectToDelete != null) {
						projectDAO.delete(projectToDelete);
						System.out.println("Xóa thành công dự án: " + projectToDelete.getId());
					} else {
						System.out.println("Không tìm thấy dự án.");
					}
				} else if (deleteChoice == 3) {
					System.out.print("Nhập ID Task cần xóa: ");
					String deleteTaskId = scanner.nextLine();
					Task taskToDelete = taskDAO.findById(deleteTaskId);
					if (taskToDelete != null) {
						taskDAO.delete(taskToDelete);
						System.out.println("Xóa thành công Task: " + taskToDelete.getId());
					} else {
						System.out.println("Không tìm thấy Task.");
					}
				} else {
					System.out.println("Lựa chọn không hợp lệ.");
				}
				break;

			case 7: // Thoát
				running = false;
				System.out.println("Kết thúc chương trình.");
				break;

			default:
				System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
			}
		}

		em.close();
		scanner.close();
	}
}