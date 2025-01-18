package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="tasks")
public class Task {
	@Id
	@Column(name="task_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;
	private String description;
	private Priority priority;
	private Date createAt;
	private Date dueDate;
	@Enumerated(EnumType.STRING)
	private Status status;
	@ManyToOne
	@JoinColumn(name="project_id")
	private Project project;
	
	@ManyToOne
    @JoinColumn(name = "parent_task_id")
    private Task parentTask;

//    @OneToMany(mappedBy = "parentTask", cascade = CascadeType.ALL)
//    private List<Task> subTasks = new ArrayList<Task>();
	
    public Task() {
		// TODO Auto-generated constructor stub
	}

	public Task(int id, String title, String description, Priority priority, Date createAt, Date dueDate, Status status,
			Project project, Task parentTask) {
		
		this.id = id;
		this.title = title;
		this.description = description;
		this.priority = priority;
		this.createAt = createAt;
		this.dueDate = dueDate;
		this.status = status;
		this.project = project;
		this.parentTask = parentTask;
	}
	
	public Task(String title, String description, Priority priority, Date createAt, Date dueDate, Status status,
			Project project, Task parentTask) {
		this.title = title;
		this.description = description;
		this.priority = priority;
		this.createAt = createAt;
		this.dueDate = dueDate;
		this.status = status;
		this.project = project;
		this.parentTask = parentTask;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Task getParentTask() {
		return parentTask;
	}

	public void setParentTask(Task parentTask) {
		this.parentTask = parentTask;
	}
    
	
}
