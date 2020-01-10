package com.apcsa.model;

import com.apcsa.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Creates an instance of the Administrator class.
 * 
 * @param user
 * @param rs
 * @throws SQLException
 */
    
public Administrator(User user, ResultSet rs) throws SQLException {
    super(user);
    
    this.administratorId = rs.getInt("administrator_id");
    this.firstName = rs.getString("first_name");
    this.lastName = rs.getString("last_name");
    this.jobTitle = rs.getString("job_title");

super(user);

this.administratorId = rs.getInt("administrator_id");
this.firstName = rs.getString("first_name");
this.lastName = rs.getString("last_name");
this.jobTitle = rs.getString("job_title");
}}

	public int getAdministratorId() {
		return administratorId;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	public String getJobTitle() {
		return jobTitle;
	}
}