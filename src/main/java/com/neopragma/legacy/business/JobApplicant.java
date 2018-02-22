package com.neopragma.legacy.business;

import java.io.IOException;
import java.net.URISyntaxException;



/**
 * Job applicant class.
 */
public class JobApplicant {
	
	private String firstName = null;
	private String middleName = null;
	private String lastName = null;
	private String ssn;
	private String zipCode;    
	private String city;
	private String state;

	
	public void setFullName(String firstName, String middleName, String lastName) {
		setFirstName(firstName);
		setMiddleName(middleName);
		setLastName(lastName);
	}
	
	private void setFirstName(String firstName) {
		this.firstName = firstName == null ? "" : firstName;
	}
	
	private void setMiddleName(String middleName) {
		this.middleName = middleName == null ? "" : middleName;
	}
	
	private void setLastName(String lastName) {
		this.lastName = lastName == null ? "" : lastName;
	}
	

	public String getCity() {
		return city;
	}

	private void setCity(String city) {
		this.city = city;
	}
	
	public String getState() {
		return state;
	}
	
	private void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return this.zipCode;
	}
	
	private void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	public int validateName() {
		if ( firstName.length() > 0 && lastName.length() > 0 ) {
			return 0;
		} else {
			return 6;
		}
	}
	
	
	private String[] specialCases = new String[] {
	    "219099999", "078051120"
	};

	public void setSsn(String ssn) {
		if ( ssn.matches("(\\d{3}-\\d{2}-\\d{4}|\\d{9})") ) {
  		    this.ssn = ssn.replaceAll("-", "");
		} else {
  		    this.ssn = "";
		}    
	}
	
	public String formatSsn() {
		StringBuilder sb = new StringBuilder(ssn.substring(0,3));
		sb.append("-");
		sb.append(ssn.substring(3,5));
		sb.append("-");
		sb.append(ssn.substring(5));
		return sb.toString();
	}

	public int validateSsn() {
		if ( !ssn.matches("\\d{9}") ) {
			return 1;
		}
		if ( "000".equals(ssn.substring(0,3)) || 
			 "666".equals(ssn.substring(0,3)) ||
			 "9".equals(ssn.substring(0,1)) ) {
			return 2;
		}
		if ( "0000".equals(ssn.substring(5)) ) {
			return 3;
		}
		for (int i = 0 ; i < specialCases.length ; i++ ) {
			if ( ssn.equals(specialCases[i]) ) {
				return 4;
			}
		}
		return 0;
	}
	
	public void setSpanishName(String primerNombre, String segundoNombre,
			String primerApellido, String segundoApellido) {
		this.firstName = primerNombre == null ? "" : primerNombre;
		this.middleName = segundoNombre == null ? "" : segundoNombre;
		if ( primerApellido != null ) {
			StringBuilder sb = new StringBuilder(primerApellido);
			sb.append(segundoApellido == null ? null : " " + segundoApellido);
			this.lastName = sb.toString();
		} else {
			this.lastName = "";
		}
	}
	
	public String formatLastNameFirst() {
		StringBuilder sb = new StringBuilder(lastName);
		sb.append(", ");
		sb.append(firstName);
		if ( middleName.length() > 0 ) {
			sb.append(" ");
			sb.append(middleName);
		}
		return sb.toString();
	}
	
	public void addApplicantToDatabase(String firstName,
			       String middleName,
			       String lastName,
			       String ssn,
			       String zipCode, 
			       ApplicantLocation applicantLocation) throws URISyntaxException, IOException {
		setFullName(firstName, middleName, lastName);
		setSsn(ssn);
		setZipCode(zipCode);
		setCity(applicantLocation.getCity());
		setState(applicantLocation.getState());
		saveApplicant();
	}
	
	private void saveApplicant() {
		//TODO save information to a database
		System.out.println("Saving to database: " + formatLastNameFirst());
	}

	
	
}
