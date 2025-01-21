package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="accounts")
public class Account {
	@Id
	@Column(name="account_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="account_name", unique = true)
	private String accountName;
	private String password;
	private String role;
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;
	
	public Account() {
		// TODO Auto-generated constructor stub
	}

	public Account(int id, String accountName, String password, String role, User user) {
		
		this.id = id;
		this.accountName = accountName;
		this.password = password;
		this.role = role;
		this.user = user;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", accountName=" + accountName + ", password=" + password + ", role=" + role
				+ ", user=" + user + "]";
	}
	
	
	
}
