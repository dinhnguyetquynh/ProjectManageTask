
package service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

import com.google.gson.Gson;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import model.Account;
import model.Project;
import model.Request;
import model.Task;
import model.User;
import net.datafaker.idnumbers.SouthAfricanIdNumber;
import util.GsonHelper;

// Xử lý tất cả truyền nhận dữ liệu giữa client và server tại đây
public class ClientHandler extends Thread {
	private Socket clientSocket;
	private JTextArea textArea;
	private EntityManager em;
	private Account account;
	private BufferedReader in;
	private PrintWriter out;
	private DataOutputStream dos;

	public ClientHandler(Socket socket, JTextArea textArea) {
		this.clientSocket = socket;
		this.textArea = textArea;
		this.em = Persistence.createEntityManagerFactory("task").createEntityManager();
	}

	@Override
	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	        out = new PrintWriter(clientSocket.getOutputStream(), true);
	        dos = new DataOutputStream(clientSocket.getOutputStream());
			while (true) { // Lắng nghe liên tục
				// Nếu nhận được dữ liệu từ client (Request)
				String request = in.readLine();
				if (request != null) {
					textArea.append(request + "\n");
					try (JsonReader reader = Json.createReader(new StringReader(request))) {
						JsonObject jo = reader.readObject();
						if (jo != null) {
							String message = jo.getString("message");
							JsonObject joData = jo.getJsonObject("data");
							handleRequest(message, joData);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				

			}

		} catch (SocketException e) {
			textArea.append("Client ngắt kết nối!\n");
			Service.getInstance().removeClientOutputStream(account);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				clientSocket.close();
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void handleRequest(String message, JsonObject joData) throws IOException {
		switch (message) {
		case "LOGIN":
			if (joData != null) {
				if (!joData.containsKey("account")) {
					textArea.append("Lỗi: Không tìm thấy dữ liệu!\n");
					break;
				}
				JsonObject joAcc = joData.getJsonObject("account");
				// Nếu không tìm thấy account
				if (joData.isNull("account")) {
					textArea.append("khong tim thay account");
					break;
				}
				
				Gson gson = new Gson();
				Account accReceive = gson.fromJson(joAcc.toString(), Account.class);
				ServiceUser serviceUser = new ServiceUser(em);
				Account acc = serviceUser.login(accReceive.getAccountName(), accReceive.getPassword());
				
				if (acc != null) {
					acc.getUser().setManagedUsers(null);
					Service.getInstance().addClient(acc,out);
					this.account = acc;
				}
				ServiceMessage sm = ServiceMessage.getInstance();
				String response = sm.createMessage("LOGIN", sm.createObjectJson("account", gson.toJson(acc)));
				textArea.append(response + "\n");
				out.println(response);
				out.flush();
			}
			break;
		case "LIST_PROJECT":
			Gson gson = new Gson();
			ProjectService ps = new ProjectService(em);
			List<Project> list = ps.getAllByUser(account.getUser().getId());
			if(list!=null) {
				for(Project pro:list) {
					System.out.println("Project tim được là: "+pro.toString());
				}
			}else {
				System.out.println("Danh sach project rong");
			}
			
			String listProject = gson.toJson(list);
			textArea.append(listProject);
			
			ServiceMessage sm = ServiceMessage.getInstance();
			String res = sm.createMessage("LIST_PROJECT", sm.createObjectJson("listproject", listProject));
			textArea.append(res);
			out.println(res);
			out.flush();
			break;
		case "DELETE_PROJECT":
			System.out.println("DELETE_PROJECT");
			gson = new Gson();
			int projectId = joData.getInt("projectId");
			System.out.println("PROJECT ID LÀ:"+projectId);
			ProjectService sv = new ProjectService(em);
			sv.deleteProjectById(projectId);
			
			List<Project> updateProject = sv.getAllByUser(account.getUser().getId());
			String listProject2 = gson.toJson(updateProject);
			String res2 = ServiceMessage.getInstance().createMessage("LIST_PROJECT",
					ServiceMessage.getInstance().createObjectJson("listproject", listProject2));
			out.println(res2);
			out.flush();
			break;
		case "LIST_TASKS":
			int proId = joData.getInt("projectId");
			TaskService taskSV = new TaskService(em);
			List<Task> listTask = taskSV.findByProjectId(proId);
			for(Task t : listTask) {
				System.out.println("Task is:"+t);
			}
			
			String listTaskJson = GsonHelper.toJson(listTask);
			
			String res3 = ServiceMessage.getInstance().createMessage("LIST_TASKS",ServiceMessage.getInstance().createObjectJson("listTask", listTaskJson));
			System.out.println("Res3 được in ra la"+res3);
			String compactJson = res3.replace("\n", "").replace("\r", ""); // Bỏ \n, \r nếu có
			out.println(compactJson); // Gửi xong xuống dòng
			out.flush();
			break;
		case "LISTS_USER_PROJECT":
			int projectId1 = joData.getInt("projectId");
			UserProjectService upService = new UserProjectService(em);
			List<User> listUser = upService.getUserByProject(projectId1);
			String listUserJS = GsonHelper.toJson(listUser);
			
			String res4 = ServiceMessage.getInstance().createMessage("LISTS_USER_PROJECT",ServiceMessage.getInstance().createObjectJson("listUser", listUserJS));
			System.out.println("Res4 được in ra la"+res4);
			String compactJson1 = res4.replace("\n", "").replace("\r", ""); // Bỏ \n, \r nếu có
			out.println(compactJson1); // Gửi xong xuống dòng
			out.flush();
			
			
			break;
			
		case "CREATE_TASK":
			  System.out.println("Đã nhận được request create task");

			    // Lấy Task
			    JsonObject taskObject = joData.getJsonObject("task");
			    Task newTask = GsonHelper.fromJson(taskObject+"", Task.class);
			    int projectId2 = newTask.getProject().getId();
			    System.out.println("Task sau khi chuyển từ Json sang Task là: " + newTask);

			    // Lấy selectedUserIds
			    List<Integer> selectedUserIds = new ArrayList<>();
			    for (JsonValue val : joData.getJsonArray("selectedUserIds")) {
			        selectedUserIds.add(Integer.parseInt(val.toString()));
			    }
			    System.out.println("Selected User IDs: " + selectedUserIds);
			    UserService uSV = new UserService(em);
			    List<User> users = uSV.findListUserByIds(selectedUserIds);
			    
			    TaskService taskSV1 = new TaskService(em);
			    boolean ketqua = taskSV1.createTaskWithAssignments(newTask, users);
			    if(ketqua==true) {
			    	System.out.println("Tạo task thành công");
			    }else {
			    	System.out.println("Tạo task thất bại");
			    }
			    
			    //Gửi respone về cho client
				List<Task> listTask1 = taskSV1.findByProjectId(projectId2);
				for(Task t : listTask1) {
					System.out.println("Task is:"+t);
				}
				
				String listTaskJson1 = GsonHelper.toJson(listTask1);
				
				String res5 = ServiceMessage.getInstance().createMessage("LIST_TASKS",ServiceMessage.getInstance().createObjectJson("listTask", listTaskJson1));
				System.out.println("Res3 được in ra la"+res5);
				String compactJson2 = res5.replace("\n", "").replace("\r", ""); // Bỏ \n, \r nếu có
				out.println(compactJson2); // Gửi xong xuống dòng
				out.flush();
			
			    
			
			
			break;
			
		}
	}
}

