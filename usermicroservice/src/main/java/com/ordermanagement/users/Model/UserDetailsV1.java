package com.ordermanagement.users.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Date;

@Entity(name = "users")
public class UserDetailsV1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String password;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    private long contactNumber;
    private int managerId;
    private int departmentId;
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdOn;
    @CreationTimestamp
    private Timestamp updatedOn;
    private Timestamp deletedOn;
    @JsonProperty("isDeleted")
    private boolean isDeleted;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public long getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(long contactNumber) {
		this.contactNumber = contactNumber;
	}
	public int getManagerId() {
		return managerId;
	}
	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public Timestamp getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}
	public Timestamp getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}
	public Timestamp getDeletedOn() {
		return deletedOn;
	}
	public void setDeletedOn(Timestamp deletedOn) {
		this.deletedOn = deletedOn;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	@Override
	public String toString() {
		return "UserDetailsV1 [id=" + id + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName="
				+ lastName + ", username=" + username + ", contactNumber=" + contactNumber + ", managerId=" + managerId
				+ ", departmentId=" + departmentId + ", createdOn=" + createdOn + ", updatedOn="
				+ updatedOn + ", deletedOn=" + deletedOn + ", isDeleted=" + isDeleted + "]";
	}
    
}
