package test;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Priority;
import model.Project;
import model.Status;
import model.Task;
import model.TaskAssignment;
import model.User;
import service.TaskAssigmentService;
import service.TaskService;
import service.UserService;
import util.JPAUtil;

public class TestCrudTask {
	public static void main(String[] args) {
		EntityManager em = JPAUtil.getEntityManager();
		EntityTransaction tr = em.getTransaction();
		TaskService sv = new TaskService(em);
		UserService uSv = new UserService(em);
		TaskAssigmentService taSv = new TaskAssigmentService(em);
		
				
		   		//Tạo task mới
			    Task task = new Task();
			    task.setTitle("Code chức năng đăng nhập");
			    task.setDescription("Không có");
			    task.setPriority(Priority.MEDIUM);
			    task.setStatus(Status.PENDING);
			    
//			    //Lấy project
			    Project project = em.find(Project.class, 1);
			    task.setProject(project);	
		    
			    LocalDateTime now = LocalDateTime.now();
			    task.setCreateAt(now);
			    LocalDateTime dueDate = LocalDate.of(2025, 1, 25).atTime(22, 00, 00);
			    task.setDueDate(dueDate);
			    task.setParentTask(null);
			    sv.add(task);
			
//				//GIAO TASK CHO NHÂN VIÊN
//				//Tìm task 
//				Task task=	sv.findById(8);
//				//Tìm nhân viên
//				User user = uSv.findById(22);
//				//Gán task cho nhân viên
//				TaskAssignment ta = new TaskAssignment(task, user);
//				taSv.add(ta);
			
			//Xóa nhân viên 2 trong task 
//				taSv.deleteUserFromTask(2);
				
			


		//LẤY DANH SÁCH USER TỪ TASK
//		List<TaskAssignment> users = taSv.getUsersFromTask(4);
//		if(users!=null) {
//			for(TaskAssignment u:users) {
//				System.out.println("Nhân viên là:"+u.getEmployee().getName());
//			}
//		}else {
//			System.out.println("Lỗi không lấy được ds nhân viên từ task");
//		}
		
//		
//		Xóa nhân viên khỏi task
//		taSv.deleteUserFromTask(12, 4);
		
		if(em.isOpen()) {
			System.out.println("EntityManager đang mở");
			em.close();
		}else {
			System.out.println("Entity đã đóng");
		}
		
		
	}

}
