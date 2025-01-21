package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("task");
	
	public static EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
	
	public static void closeEntityManagerFactory() {
		if(emf.isOpen()) {
			emf.close();
		}
	}

}
