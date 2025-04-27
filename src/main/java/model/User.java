package model;

import java.io.Serializable;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="user_name")
	private String name;
	@Enumerated(EnumType.STRING)
	private Gender gender;
	private int age;
	private String email;
	
	@ManyToOne
	@JoinColumn(name="manager_id")
	private User manager;
	
	@OneToMany(mappedBy = "manager")
	transient private List<User> managedUsers = new ArrayList<User>(); 
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(int id, String name, Gender gender, int age, String email, User manager) {
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.email = email;
		this.manager = manager;
	}
	
	
	public User(String name, Gender gender, int age, String email, User manager) {
	
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.email = email;
		this.manager = manager;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	public List<User> getManagedUsers() {
		return managedUsers;
	}

	public void setManagedUsers(List<User> managedUsers) {
		this.managedUsers = managedUsers;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", gender=" + gender + ", age=" + age + ", email=" + email + "]";
	}

}
