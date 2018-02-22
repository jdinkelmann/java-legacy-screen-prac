package com.neopragma.legacy.screen;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

import com.neopragma.legacy.business.ApplicantLocation;
import com.neopragma.legacy.business.JobApplicant;
import com.neopragma.legacy.service.ZipcodeService;

public class Main {
	public static void main(String[] args) throws URISyntaxException, IOException {
		JobApplicant jobApplicant = new JobApplicant();
		ZipcodeService zipcodeService = new ZipcodeService();
		
		boolean done = false;
		Scanner scanner = new Scanner(System.in);
		String firstName = "";
		String middleName = "";
		String lastName = "";
		String ssn = "";
		String zipCode = "";
		while (!done) {
			System.out.println("Please enter info about a job candidate or 'quit' to quit");
			System.out.println("First name?");
            firstName = scanner.nextLine();		
            if (firstName.equals("quit")) {
            	scanner.close();
            	System.out.println("Bye-bye!");
            	done = true;
            	break;
            }
			System.out.println("Middle name?");
            middleName = scanner.nextLine();
			System.out.println("Last name?");
            lastName = scanner.nextLine();			
			System.out.println("SSN?");
            ssn = scanner.nextLine();			
			System.out.println("Zip Code?");
            zipCode = scanner.nextLine();	
            
            System.out.println("Locating City & State...");
            ApplicantLocation applicantLocation = zipcodeService.findCityState(zipCode);
            System.out.println("City & State: " + applicantLocation.getCity() + " " + applicantLocation.getState());
            
            
            jobApplicant.addApplicantToDatabase(firstName, middleName, lastName, ssn, zipCode, applicantLocation);
		}
	}
}
