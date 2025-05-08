package com.ordermanagement.users.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserDetailsImpl extends User {

	private static final long serialVersionUID = 1L;
	
	private int id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String password;
    private String username;
    private long contactNumber;
    private int managerId;
    private int departmentId;
    private int roleId;
    private boolean isDeleted;

    public UserDetailsImpl(int id, String firstName, String middleName, String lastName, String username,
                           String password, long contactNumber, int managerId, int departmentId, int roleId,
                           boolean isDeleted, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.password = password;
        this.username = username;
        this.contactNumber = contactNumber;
        this.managerId = managerId;
        this.departmentId = departmentId;
        this.roleId = roleId;
        this.isDeleted = isDeleted;
    }

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

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "UserDetailsImpl [id=" + id + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName="
				+ lastName + ", password=" + password + ", username=" + username + ", contactNumber=" + contactNumber
				+ ", managerId=" + managerId + ", departmentId=" + departmentId + ", roleId=" + roleId + ", isDeleted="
				+ isDeleted + "]";
	}

}
