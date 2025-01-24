package test;

import java.util.List;

import jakarta.persistence.EntityManager;
import model.UserProject;
import service.UserProjectService;
import util.JPAUtil;

public class TestCrudUserProject {
	public static void main(String[] args) {
		EntityManager em =JPAUtil.getEntityManager();
		UserProjectService upSv = new UserProjectService(em);
		
		List<UserProject> listUser = upSv.getUsersFromProject(1);
		if(listUser!=null) {
			for(int i=0;i<listUser.size();i++) {
				System.out.println("Tên nhân viên là: "+listUser.get(i).getUser().getName());
			}
		}else {
			System.out.println("Danh sách nhân viên rỗng");
		}
	}

}
