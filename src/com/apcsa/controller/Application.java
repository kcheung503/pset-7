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