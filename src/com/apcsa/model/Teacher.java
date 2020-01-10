package com.apcsa.model;

import com.apcsa.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Creates an instance of the Teacher class.
 * 
 * @param user
 * @param rs
 * @throws SQLException
 */

public Teacher(User user, ResultSet rs) throws SQLException {{
    super(user);
    
    this.teacherId = rs.getInt("teacher_id");
    this.departmentId = rs.getInt("department_id");
    this.firstName = rs.getString("first_name");
    this.lastName = rs.getString("last_name");
    this.departmentName = rs.getString("department_id");
}}


    public int getTeacherId() {
    	return teacherId;
    }
    
    public int getDepartmentId() {
    	return departmentId;
    }
    
    public String getFirstName() {
    	return firstName;
    }
    
    public String getLastName() {
    	return lastName;
    }
    
    public String getDepartmentName() {
    	return departmentName;
    }

    


}