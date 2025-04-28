package model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="projects")
public class Project implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="project_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;
	private String description;
	@Column(name="start_date")
	private Date startDate;
	@Column(name="end_date")
	private Date endDate;
	
	//thêm số lượng người trong project : 25/4
	@Transient
	private int numberUser;

	
	public Project() {
		// TODO Auto-generated constructor stub
	}
	

	
	public Project(int id, String title, String description, Date startDate, Date endDate, int numberUser) {
	
		this.id = id;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.numberUser = numberUser;
	}



	public Project(String title, String description, Date startDate, Date endDate) {
		
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
	} 
	
	
	public Project(int id, String title, String description, Date startDate, Date endDate) {

		this.id = id;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
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
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}



	public int getNumberUser() {
		return numberUser;
	}



	public void setNumberUser(int numberUser) {
		this.numberUser = numberUser;
	}



	@Override
	public String toString() {
		return "Project [id=" + id + ", title=" + title + ", description=" + description + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", numberUser=" + numberUser + "]";
	}
	
	
	

	
	
	

}
