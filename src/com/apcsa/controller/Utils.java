package com.apcsa.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;
import com.apcsa.model.Student;

public class Utils {

    /**
     * Returns an MD5 hash of the user's plain text password.
     *
     * @param plaintext the password
     * @return an MD5 hash of the password
     */

    public static String getHash(String plaintext) {
        StringBuilder pwd = new StringBuilder();

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(plaintext.getBytes());
            byte[] digest = md.digest(plaintext.getBytes());

            for (int i = 0; i < digest.length; i++) {
                pwd.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return pwd.toString();
    }


	/**
	 * Safely reads an integer from the user.
	 * 
	 * @param in the Scanner
	 * @param invalid an invalid (but type-safe) default
	 * @return the value entered by the user or the invalid default
	 */
    
    public static int getInt(Scanner in, int invalid) {
    	try {
    		return in.nextInt();                // try to read and return user-provided value
    	} catch (InputMismatchException e) {            
    		return invalid;                     // return default in the even of an type mismatch
    	} finally {
    		in.nextLine();                      // always consume the dangling newline character
    	}
    }
    
    /**
     * Confirms a user's intent to perform an action.
     * 
     * @param in the Scanner
     * @param message the confirmation prompt
     * @return true if the user confirms; false otherwise
     */

    public static boolean confirm(Scanner in, String message) {
        String response = "";
        
        // prompt user for explicit response of yes or no
        
        while (!response.equals("y") && !response.equals("n")) {
            System.out.print(message);
            response = in.next().toLowerCase();
        }
        
        return response.equals("y");
    }
    
    /**
     * Sorts the list of students by rank, using the index to update the underlying class rank.
     * 
     * @param students the list of students
     * @return the updated list of students
     */

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static ArrayList<Student> updateRanks(ArrayList<Student> students) {
        Collections.sort(students, new Comparator() {

            // compares each student based on gpa to aid sorting
            
            @Override
            public int compare(Object student1, Object student2) {
                if (((Student) student1).getGpa() > ((Student) student2).getGpa()) {
                    return -1;
                } else if (((Student) student1).getGpa() == ((Student) student2).getGpa()) {
                    return 0;
                } else {
                    return 1;
                }
            }
            
        });
        
        // applies a class rank (provided the student has a measurable gpa)
        
        int rank = 1;
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            
            student.setClassRank(student.getGpa() != -1 ? rank++ : 0);
        }
                
        return students;
    }
    
    /**
     * Computes a grade based on marking period grades and exam grades.
     * 
     * @param grades a list of grades
     * @return the final grade
     */

    public static Double getGrade(ArrayList<Double> grades) {
        int mps = 0;
        double mpSum = 0;
        double mpAvg = -1;
        double mpWeight = -1;

        int exams = 0;
        double examSum = 0;
        double examAvg = -1;
        double examWeight = -1;
        
        // compute some of marking period and/or exam grades
        
        for (int i = 0; i < grades.size(); i++) {
            if (grades.get(i) != null) {
                if (i < 2 || (i > 2 && i < 5)) {        // marking period grade
                    if(grades.get(i)==-1) {
                    	
                    } else {
                    	mps++;
                    	mpSum = mpSum + grades.get(i);
                    }
                } else {                                // mid term or final exam grade
                    if(grades.get(i)==-1) {
                    	
                    } else {
                    	exams++;
                    	examSum = examSum + grades.get(i);
                    }
                }
            }
        }
        
        // compute weights and averages based on entered grades
        
        if (mps > 0 && exams > 0) {
            mpAvg = mpSum / mps;
            examAvg = examSum / exams;
             
            mpWeight = 0.8;
            examWeight = 0.2;
        } else if (mps > 0) {
            mpAvg = mpSum / mps;
            
            mpWeight = 1.0;
            examWeight = 0.0;
        } else if (exams > 0) {
            examAvg = examSum / exams;
            
            mpWeight = 0.0;
            examWeight = 1.0;
        } else {
            return null;
        }
        return round(mpAvg * mpWeight + examAvg * examWeight, 2);
    }
    
    /**
     * Rounds a number to a set number of decimal places.
     * 
     * @param value the value to round
     * @param places the number of decimal places
     * @return the rounded value
     */
        
    public static Double round(Double grades, int decimals) {
    	String decimalPlaces = "#.";
    	for(int i = 0; i < decimals; i++) {
    		decimalPlaces += "#";
    	}
    	DecimalFormat df = new DecimalFormat(decimalPlaces);
    	double grade = Double.parseDouble(df.format(grades));
    	return grade;
    }

}