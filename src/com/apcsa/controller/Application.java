package com.apcsa.controller;

import java.util.Scanner;
import com.apcsa.data.PowerSchool;
import com.apcsa.model.User;
import java.util.ArrayList;
import com.apcsa.model.Teacher;

public class Application {

    private Scanner in;
    private User activeUser;

    /**
     * Creates an instance of the Application class, which is responsible for interacting
     * with the user via the command line interface.
     */

        public Application() {
            this.in = new Scanner(System.in);

            try {
                PowerSchool.initialize(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * Starts the PowerSchool application.
         */

        public void startup() {
            System.out.println("PowerSchool -- now for students, teachers, and school administrators!");

            while (true) {
                System.out.print("\nUsername: ");
                String username = in.next();

                System.out.print("Password: ");
                String password = in.next();

                try {
    	            if (login(username, password)) {
    	                activeUser = activeUser.isAdministrator()
    	                    ? PowerSchool.getAdministrator(activeUser) : activeUser.isTeacher()
    	                    ? PowerSchool.getTeacher(activeUser) : activeUser.isStudent()
    	                    ? PowerSchool.getStudent(activeUser) : activeUser.isRoot()
    	                    ? activeUser : null;
    	
    	                if (isFirstLogin() && !activeUser.isRoot()) {
    	                    System.out.print("\nInput a new password: ");
    	                    String newPassword = in.next();
    	                    
//    	                    changePass(username, newPassword);
    	                }
    	                
//    	                createAndShowUI();
    	            } else {
    	                System.out.println("\nInvalid username and/or password.");
    	            }
                } catch (Exception e) {
//                	shutdown(e);
                }
            }
        }
        
        public void createAndShowUI() {
        	
        	System.out.println("\nHello, again, " + activeUser.getFirstName() + "!\n");
        	
        	if(activeUser.isRoot()) {
        		showRootUI();
        		
        	} else if (activeUser.isAdministrator()) {
        		showAdministratorUI();
        		
        	} else if (activeUser.isTeacher()) {
        		showTeacherUI();
        		
        	} else if (activeUser.isStudent()) {
        		
        		showStudentUI();
        		
        	}
        }
        
        private void showRootUI() {
        	
        	while (activeUser != null) {
        		
        		switch (getRootMenuSelection()) {
                case 1: rootResetPassword(); break;
                case 2: factoryReset(); break;
                case 3: logout(); break;
                case 4: shutdown(); break;
                default: System.out.println("\nInvalid selection.\n"); break;
                
            	}
        	}
        }
        
        private void showAdministratorUI() {
        	
        	while (activeUser != null) {
        		
     			switch (administratorSelection()) {
     			case 1: faculty(); break;
     			case 2: facultyByDepartment(); break;
     			case 3: studentEnrollment(); break;
     			case 4: studentEnrollmentbyGrade(); break;
     			case 5: studentEnrollmentbyCourse(); break;
     			case 6: resetPassword(); break;
     			case 7: logout(); break;
     			default: System.out.println("\nInvalid selection. \n"); break;
     			
     			}
             }
        }
        
        private void showTeacherUI() {
        	
        	if((activeUser.getFirstName()).equals("Ryan")) {
        		
        		while (activeUser != null) {
        			
         			switch (wilsonSelection()) {
         			case 1: enrollment(); break;
         			case 2: addAssignment(); break;
         			case 3: deleteAssignment(); break;
         			case 4: enterGrade(); break;
         			case 5: resetPassword(); break;
         			case 6: logout(); break;
         			case 7: message(); break;
         			default: System.out.println("\nInvalid selection. \n"); break;
         			}
                 }
        		
        	} else {
        		
        		while (activeUser != null) {
        			
         			switch (teacherSelection()) {
         			case 1: enrollment(); break;
         			case 2: addAssignment(); break;
         			case 3: deleteAssignment(); break;
         			case 4: enterGrade(); break;
         			case 5: resetPassword(); break;
         			case 6: logout(); break;
         			default: System.out.println("\nInvalid selection. \n"); break;
         			
         			}
                 }
        	}
        	
        }
        
        private void showStudentUI() {
        	
        	while (activeUser != null) {
        		
     			switch (studentSelection()) {
     			case 1: courseGrades(); break;
     			case 2: assignment(); break;
     			case 3: resetPassword(); break;
     			case 4: logout(); break;
     			default: System.out.println("\nInvalid selection. \n"); break;
     			
     			}
             }
        }
        
        public int getRootMenuSelection() {
        	
        	System.out.println("[1] Reset user password.");
        	System.out.println("[2] Factory reset database.");
        	System.out.println("[3] Logout.");
        	System.out.println("[4] Shutdown.");
        	System.out.print("\n::: ");
        	int selection = in.nextInt();
        	return selection;
            
        }
        
        public int administratorSelection() {
        	
        	System.out.println("[1] View faculty.");
        	System.out.println("[2] View faculty by department.");
        	System.out.println("[3] View student enrollment.");
        	System.out.println("[4] View student enrollment by grade.");
        	System.out.println("[5] View student enrollment by course.");
        	System.out.println("[6] Change password.");
        	System.out.println("[7] Logout.");
        	System.out.print("\n::: ");
        	
        	int selection = in.nextInt();
    		return selection;
        }
        
        public int teacherSelection() {
        	
        	System.out.println("[1] View enrollment by course.");
        	System.out.println("[2] Add assignment.");
        	System.out.println("[3] Delete assignment.");
        	System.out.println("[4] Enter grade.");
        	System.out.println("[5] Change password.");
        	System.out.println("[6] Logout.");
        	System.out.print("\n::: ");
        	
        	int selection = in.nextInt();
    		return selection;
        }
        
        public int studentSelection() {
        	
        	System.out.println("[1] View course grades.");
        	System.out.println("[2] View assignment grades by course.");
        	System.out.println("[3] Change password.");
        	System.out.println("[4] Logout.");
        	System.out.print("\n::: ");
        	
        	int selection = in.nextInt();
    		return selection;
        }
        
        private int checkYesNo(String yesNo, String wantTo) {
        	if(!yesNo.equals("y") && !yesNo.equals("n")) {
        		while(!yesNo.equals("y") && !yesNo.equals("n")) {
        			System.out.println("\nInvalid selection.\n");
        			System.out.print("Are you sure " + wantTo +" (y/n) ");
        			yesNo = in.nextLine();
        			yesNo = yesNo.toLowerCase();
        		}
        		if((yesNo.equals("n"))) {
        			return -1;
        		} else if (yesNo.equals("y")) {
        			return 1;
        		}
        	} else if (yesNo.equals("n")) {
        		return -1;
        	} else if (yesNo.equals("y")) {
        		return 1;
        	}
    		return 0;
        }
        
        public void rootResetPassword() {
        	in.nextLine();
        	
        	System.out.print("\nUsername: ");
        	String username = in.nextLine();
        	String wantTo = "you want to reset the password for " + username + "?";
        	System.out.print("Are you sure you want to reset the password for " + username + "? (y/n) ");
        	String yesNo = in.nextLine(); 	
        	
        	yesNo = yesNo.toLowerCase();
        	int checked = checkYesNo(yesNo, wantTo);
        	
        	if(checked == -1) {
    			System.out.println("");
        	} else if (checked == 1) {
        		int worked = PowerSchool.updatePasswordAndTime(username);
        		if(worked == 1) {
        			System.out.println("\nSuccessfully reset password for " + username + ".\n");
        		}
        	}
        }
        
        public void resetPassword() {
        	
        	in.nextLine();
        	
        	System.out.print("\nEnter current password: ");
        	String currentPassword = in.nextLine();
        	System.out.print("Enter new password: ");
        	String newPassword = in.nextLine();
        	
        	String truePassword = PowerSchool.getPassword(activeUser, currentPassword);
        	
        	if(!Utils.getHash(currentPassword).equals(truePassword)) {
        		System.out.println("\nInvalid current password.\n");
        	
        	} else {
        		
        		changePass(activeUser.getUsername(), newPassword);
        		System.out.println("");
        	}
        }
        
        private void logout() {
        	
        	in.nextLine();
        	
        	String wantTo = "you want to logout?";
        	System.out.print("\nAre you sure you want to logout? (y/n) ");
        	String yesNo = in.nextLine();
        	yesNo = yesNo.toLowerCase();
        	
        	int checked = checkYesNo(yesNo, wantTo);
        	
        	if(checked == -1) {
    			System.out.println("");
    			
        	} else if (checked == 1) {
        		
        		activeUser = null;
        	}
        }
        
        private void factoryReset() {
        	
        	in.nextLine();
        	String wantTo = "you want to reset all settings and data?";
        	System.out.print("\nAre you sure you want to reset all settings and data? (y/n) ");
        	String yesNo = in.nextLine();
        	yesNo = yesNo.toLowerCase();
        	int checked = checkYesNo(yesNo, wantTo);
        	
        	if (checked == -1) {
        		
    			System.out.println("");
    			
        	} else if (checked == 1) {
        		
        		PowerSchool.initialize(true);
    			System.out.println("\nSuccessfully reset database.\n");
        	}
        }
        
        public void faculty() {
        	
        	ArrayList<String> teachers = PowerSchool.getTeachers();
        	String departmentId = null;
        	ArrayList<String> departmentIds = new ArrayList<String>();
        	
        	for (int i = 0; i < teachers.size(); i = i + 2) {
        		departmentId = PowerSchool.getDepartmentId(teachers.get(i));
        		departmentIds.add(departmentId);
        	}
        	
        	ArrayList<String> departmentTitles = new ArrayList<String>();
        	for (int i = 0; i < departmentIds.size(); i++) {
        		departmentTitles.add ( PowerSchool.getDepartmentTitle(departmentIds.get(i)));
        	}
        	
        	System.out.println("");
        	
        	for (int i = 0, x = 0; i < teachers.size(); i = i + 2) {;
        		System.out.println((x+1) + ". " + teachers.get(i+1) + ", " + teachers.get(i) + " / " + departmentTitles.get(x));
        		x += 1;
        	}
        	
        	System.out.println("");
        }
        
        public void facultyByDepartment() {
        	
        	System.out.println("\nChoose a department.\n");
        	ArrayList<String> departmentTitles = PowerSchool.getAllDepartmentTitles();
        	
        	for(int i = 0; i<departmentTitles.size(); i++) {
        		System.out.println("[" + (i+1) + "] " + departmentTitles.get(i) + ".");
        	}
        	
        	System.out.print("\n::: ");
        	int department = in.nextInt();
        	
        	if (department < 1 || department > departmentTitles.size()) {
        		
        		while(department < 1 || department > departmentTitles.size()) {
        			
        			System.out.println("\nInvalid selection.");
        			System.out.println("\nChoose a department.\n");
        	    	departmentTitles = PowerSchool.getAllDepartmentTitles();
        	    	
        	    	for (int i = 0; i<departmentTitles.size(); i++) {
        	    		System.out.println("[" + (i+1) + "] " + departmentTitles.get(i) + ".");
        	    		
        	    	}
        	    	
        	    	System.out.print("\n::: ");
        	    	department = in.nextInt();
        		}
        	}
        	
        	ArrayList<String> teachers = PowerSchool.getTeachersWithDepartmentId(department);
        	System.out.println("");
        	
        	for (int i = 0, x = 0; i < teachers.size(); i = i + 2) {
        		System.out.println((x+1) + ". " + teachers.get(i+1) + ", " + teachers.get(i) + " / " + departmentTitles.get(department-1) + "\n");
        		x += 1;
        	}	
        }
        
public void studentEnrollment() {
        	
        	ArrayList<String> students = PowerSchool.getStudents();
        	ArrayList<String> gradYears = PowerSchool.getGradYears();
        	System.out.println("");
        	
        	for (int i = 0, x = 0; i < students.size(); i = i + 2) {
        		
        		System.out.println((x+1) + ". " + students.get(i+1) + ", " + students.get(i) + " / " + gradYears.get(x));
        		x += 1;
        	}
        	
        	System.out.println("");
        }
        
        public void studentEnrollmentbyGrade() {
        	
        	System.out.println("\nChoose a grade level.\n");
        	System.out.println("[1] Freshman.");
        	System.out.println("[2] Sophomore.");
        	System.out.println("[3] Junior.");
        	System.out.println("[4] Senior.");
        	System.out.print("\n::: ");
        	int gradeLevel = in.nextInt();
        	
        	if (gradeLevel < 1 || gradeLevel > 4) {
        		
        		while (gradeLevel < 1 || gradeLevel > 4) {
        			System.out.println("\nInvalid selection.");
        			System.out.println("\nChoose a grade level.\n");
        	    	System.out.println("[1] Freshman.");
        	    	System.out.println("[2] Sophomore.");
        	    	System.out.println("[3] Junior.");
        	    	System.out.println("[4] Senior.");
        	    	System.out.print("\n::: ");
        	    	gradeLevel = in.nextInt();
        		}
        	}
        	
        	switch(gradeLevel) {
        	case 1: gradeLevel = 9; break;
        	case 2: gradeLevel = 10; break;
        	case 3: gradeLevel = 11; break;
        	case 4: gradeLevel = 12; break;
        	default: ;
    	}
        	
        	ArrayList<String> students = PowerSchool.getStudentsByGrade(gradeLevel);
        	ArrayList<String> studentClassRank = PowerSchool.getGradeOrder(gradeLevel);
        	if(students.isEmpty()) {
        		
        		System.out.println("\nThere are no students in this grade.\n");
        		
        	} else {
        		
        		System.out.println("");
        		
            	for(int i = 0, x = 0, y = 1; i < studentClassRank.size(); i = i + 3) {
            		String gpa = studentClassRank.get(i+2);
            		
            		if (gpa.equals("-1.0")) {
            			y = 0;
            		}
            		
            		System.out.println((x+1) + ". " + studentClassRank.get(i+1) + ", " + studentClassRank.get(i) + " / #" + y);
            		x += 1;
            		y += 1;
            	}
            	
            	System.out.println("");
        	}
        }
        
        public void studentEnrollmentbyCourse() {
        	
        	in.nextLine();
        	System.out.print("\nCourse No.: ");
        	String courseNo = in.nextLine();
        	courseNo = courseNo.toUpperCase();
        	ArrayList<String> courses = PowerSchool.getAllCourses();
        	
        	while (!courses.contains(courseNo)) {
        		
        		System.out.println("\nCourse not found.\n");
        		System.out.print("Course No.: ");
            	courseNo = in.nextLine();
            	
        	}
        	String courseId = PowerSchool.getCourseIdFromCourseNo2(courseNo);
        	ArrayList<String> studentIds = PowerSchool.getStudentId(courseId);
        	ArrayList<String> students = new ArrayList<String>();
        	
        	for (int i = 0; i < studentIds.size(); i++) {
        		
        		students.addAll(PowerSchool.getStudentsByStudentId(studentIds.get(i)));
        		
        	}
        	
        	System.out.println(courseId);

        	System.out.println(students);
        	System.out.println("");
        	
        	for (int i = 0, x = 0; i < students.size(); i = i + 3) {
        		String gpa = students.get(i+2);
        		
        		if (gpa.equals("-1.0")) {
        			gpa = "--";
        		}
        		
        		System.out.println((x+1) + ". " + students.get(i+1) + ", " + students.get(i) + " / " + gpa);
        		x += 1;
        	}
        	
        	System.out.println("");
        }
        
        public void enrollment() {
        	
        	System.out.println("\nChoose a course.\n");
        	int departmentId = ((Teacher) activeUser).getDepartmentId();
        	ArrayList<String> courses = PowerSchool.getCourses(departmentId);
        	
        	for (int i = 0; i <= courses.size()-1; i++) {
        		System.out.println("[" + (i + 1) + "] " + courses.get(i));
        	}
        	
        	System.out.print("\n::: ");
        	int courseSelection = in.nextInt();
        	
        	if (courseSelection < 1 || courseSelection > courses.size()) {
        		
        		while (courseSelection < 1 || courseSelection > courses.size()) {
        			System.out.println("\nInvalid selection.");
        			System.out.println("\nChoose a course.\n");
        			
        			for (int i = 0; i <= courses.size()-1; i++) {
        	    		System.out.println("[" + (i + 1) + "] " + courses.get(i));
        	    	}
        			
        	    	System.out.print("\n::: ");
        	    	courseSelection = in.nextInt();
        		}
        	}
        	
        	String courseNo = courses.get(courseSelection-1);
        	String courseId = PowerSchool.getCourseIdFromCourseNo2(courseNo);
        	ArrayList<String> studentIds = PowerSchool.getStudentId(courseId);
        	ArrayList<String> students = new ArrayList<String>();
        	
        	for (int i = 0; i < studentIds.size(); i++) {
        		students.addAll(PowerSchool.getStudentsByStudentId(studentIds.get(i)));
        	}
        	
        	ArrayList<String> studentGrades = new ArrayList<String>();
        	
        	for (int i = 0; i < studentIds.size(); i++) {
        		studentGrades.add(PowerSchool.getStudentGrade(courseId, studentIds.get(i)));
        	}
        	
        	if (studentIds.isEmpty()) {
        		System.out.println("\nThere are no students in this course.\n");
        	
        	} else {
        		
        		System.out.println("");
        		
        		for (int i = 0, x = 0; i < students.size(); i = i + 3) {
            		String studentGrade = studentGrades.get(x);
            		if(studentGrade == null) {
            			studentGrade = "--";
            		}
            		
            		System.out.println((x+1) + ". " + students.get(i+1) + ", " + students.get(i) + " / " + studentGrade);
            		x += 1;
            		
            	}
        		
            	System.out.println("");
        	}
        }
        
        public void addAssignment() {
        	
        	System.out.println("\nChoose a course.\n");
        	
        	int departmentId = ((Teacher) activeUser).getDepartmentId();
        	
        	ArrayList<String> courses = PowerSchool.getCourses(departmentId);
        	
        	for (int i = 0; i <= courses.size()-1; i++) {
        		System.out.println("[" + (i + 1) + "] " + courses.get(i));
        		
        	}
        	
        	System.out.print("\n::: ");
        	int courseSelection = in.nextInt();
        	
        	while (courseSelection > courses.size() || courseSelection < 1) {
        		
        		System.out.println("\nInvalid selection.");
        		System.out.println("\nChoose a course.\n");
        		
            	for (int i = 0; i <= courses.size()-1; i++) {
            		System.out.println("[" + (i + 1) + "] " + courses.get(i));
            		
            	}
            	
            	System.out.print("\n::: ");
            	courseSelection = in.nextInt();
        	}
        	
        	String courseNo = courses.get(courseSelection-1);
        	int courseId = PowerSchool.getCourseIdFromCourseNo(courseNo);
        	
        	printMarkingPeriods();
        	int markingPeriod = in.nextInt();
        	
        	while (markingPeriod > 6 || markingPeriod < 1) {
        		
        		System.out.println("\nInvalid selection.");
        		printMarkingPeriods();
            	markingPeriod = in.nextInt();
        	}
        	
        	int isMidterm = 0;
        	int isFinal = 0;
        	
        	if (markingPeriod == 5) {
        		isMidterm = 1;
        		
        	} else if (markingPeriod == 6) {
        		
        		isFinal = 1;
        	}
        	
        	in.nextLine();
        	System.out.print("\nAssignment Title: ");
        	String title = in.nextLine();
        	
        	System.out.print("Point Value: ");
    		int pointValue = in.nextInt();
    		
    		while (pointValue > 100 || pointValue < 1) {
    			
    			System.out.println("Point values must be between 1 and 100.");
    			System.out.print("Point Value: ");
    			pointValue = in.nextInt();
    			
    		}
    		
        	int assignmentId = 0;
        	in.nextLine();
        	String wantTo = "you want to create this assignment?";
        	System.out.print("Are you sure you want to create this assignment? (y/n) ");
        	String yesNo = in.nextLine();
        	
        	yesNo = yesNo.toLowerCase();
        	int checked = checkYesNo(yesNo, wantTo);
        	
        	if(checked == -1) {
        		
    			System.out.println("");
    			
        	} else if (checked == 1) {
        		
        		int rows = PowerSchool.assignmentRows();
        		if(rows == 0) {
        			
        			assignmentId = 1;
        			
        		} else {
        			
        			ArrayList<String> assignmentIds = PowerSchool.getAssignmentIds();
        			String lastAssignmentId = assignmentIds.get(assignmentIds.size() - 1);
        			assignmentId = Integer.parseInt(lastAssignmentId) + 1;
        		}
        		
        		PowerSchool.addAssignment(courseId, assignmentId, markingPeriod, isMidterm, isFinal, title, pointValue);
        		System.out.println("\nSuccessfully created assignment.\n");
        	}
        }
        
        
        
        
    /**
     * Logs in with the provided credentials.
     *
     * @param username the username for the requested account
     * @param password the password for the requested account
     * @return true if the credentials were valid; false otherwise
     */

    public boolean login(String username, String password) {
        activeUser = PowerSchool.login(username, password);

        return activeUser != null;
    }

    /**
     * Determines whether or not the user has logged in before.
     *
     * @return true if the user has never logged in; false otherwise
     */

    public boolean isFirstLogin() {
        return activeUser.getLastLogin().equals("0000-00-00 00:00:00.000");
    }

    /////// MAIN METHOD ///////////////////////////////////////////////////////////////////

    /*
     * Starts the PowerSchool application.
     *
     * @param args unused command line argument list
     */

    public static void main(String[] args) {
        Application app = new Application();

        app.startup();
    }
}