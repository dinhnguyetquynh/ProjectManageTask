package dao;

import java.util.List;

public interface DAOInterface<T> {
	
	public void addUser(T t);
	public void deleteUser(T t);
	public void updateUser(T t);
	public T findUserById(int id);
	public List<T> getAll();
}
