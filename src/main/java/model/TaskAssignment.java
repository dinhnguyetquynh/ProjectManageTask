package model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
@EqualsAndHashCode
@Entity
@Table(name="task_assignments")
public class TaskAssignment {
	@Id
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="task_id")
	private Task task;
	
	@Id
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="employee_id")
	private User employee;
	
	public TaskAssignment() {
		// TODO Auto-generated constructor stub
	}

	public TaskAssignment(Task task, User employee) {
		
		this.task = task;
		this.employee = employee;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public User getEmployee() {
		return employee;
	}

	public void setEmployee(User employee) {
		this.employee = employee;
	}
	
	

}
