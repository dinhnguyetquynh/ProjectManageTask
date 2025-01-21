package service;

import java.util.List;

public interface ServiceInterface<T> {
	

	public void add(T t);
	
	public void update(T t);
	
	public void delete(T t);
	
	public T findById(int id);
	
	public List<T> getAll();

}
