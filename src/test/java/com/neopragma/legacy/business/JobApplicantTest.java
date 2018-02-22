package com.neopragma.legacy.business;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Test;

import com.neopragma.legacy.business.JobApplicant;

/**
 * Automated unit checks for the base version of the JobApplicant application.
 * This version of the code contains a number of code smells that may point to
 * potential improvements in the design of the code.
 * 
 * @author neopragma
 * @version 1.0.0
 * @since 1.7
 */

public class JobApplicantTest {
	
	private JobApplicant jobApplicant;
	
	@Before
	public void beforeEach() {
		jobApplicant = new JobApplicant();
	}
	
	@Test
	public void shouldAddAndSaveAJobApplicat() throws URISyntaxException, IOException {
		ApplicantLocation applicantLocation = new ApplicantLocation("Saline", "MI");
		
		jobApplicant.addApplicantToDatabase("Jason", "Thomas", "Dinkelmann", "375-68-9012", "48176", applicantLocation);
		assertEquals(0, jobApplicant.validateName());
		assertEquals(0, jobApplicant.validateSsn());
		assertEquals("48176", jobApplicant.getZipCode());
		assertEquals("Saline", jobApplicant.getCity());
		assertEquals("MI", jobApplicant.getState());
	}
	
	@Test
	public void completeNameProvided() {
		jobApplicant.setFullName("First", "Middle", "Last");
		assertEquals(0, jobApplicant.validateName());
	}
	
	@Test
	public void firstAndLastNamesProvided() {
		jobApplicant.setFullName("First", null, "Last");
		assertEquals(0, jobApplicant.validateName());
	}
	
	@Test
	public void missingFirstName() {
		jobApplicant.setFullName(null, null, "Last");
		assertEquals(6, jobApplicant.validateName());
	}
	
	@Test
	public void missingLastName() {
		jobApplicant.setFullName("First", null, null);
		assertEquals(6, jobApplicant.validateName());
	}
	
	@Test
	public void completeSpanishNameProvided() {
		jobApplicant.setSpanishName("PrimerNombre", "SegundoNombre", "PrimerApellido", "SegundoApellido");
		assertEquals(0, jobApplicant.validateName());
	}
	
	@Test
	public void spanishNameWithOneFirstNameProvided() {
		jobApplicant.setSpanishName("PrimerNombre", null, "PrimerApellido", "SegundoApellido");
		assertEquals(0, jobApplicant.validateName());
	}
	
	@Test
	public void spanishNameWithOneLastNameProvided() {
		jobApplicant.setSpanishName("PrimerNombre", null, "PrimerApellido", null);
		assertEquals(0, jobApplicant.validateName());
	}
	
	@Test
	public void spanishNameWithNoFirstNameProvided() {
		jobApplicant.setSpanishName(null, null, "PrimerApellido", null);
		assertEquals(6, jobApplicant.validateName());
	}
	
	@Test
	public void spanishNameWithNoLastNameProvided() {
		jobApplicant.setSpanishName("PrimerNombre", "SegundoNombre", null, null);
		assertEquals(6, jobApplicant.validateName());
	}
	
	@Test
	public void formatEnglishNameLastNameFirst() {
		jobApplicant.setFullName("First", "Middle", "Last");
		assertEquals("Last, First Middle", jobApplicant.formatLastNameFirst());
	}
	
	@Test
	public void formatEnglishNameLastNameFirstNoMiddle() {
		jobApplicant.setFullName("First", null, "Last");
		assertEquals("Last, First", jobApplicant.formatLastNameFirst());
	}
	
	@Test
	public void ssnFormattingTest() {
		jobApplicant.setSsn("123456789");
		assertEquals("123-45-6789", jobApplicant.formatSsn());
	}

	@Test
	public void validSsnWithNoDashes() {
		jobApplicant.setSsn("123456789");
		assertEquals(0, jobApplicant.validateSsn());
	}
	
	@Test
	public void ssnWithDashesInWrongPlaces() {
		jobApplicant.setSsn("12-3456-789");
		assertEquals(1, jobApplicant.validateSsn());
	}

	@Test
	public void validSsnWithDashes() {
		jobApplicant.setSsn("123-45-6789");
		assertEquals(0, jobApplicant.validateSsn());
	}
	
	@Test
	public void ssnIsTooShort() {
		jobApplicant.setSsn("12345678");
		assertEquals(1, jobApplicant.validateSsn());
	}
	
	@Test
	public void ssnIsTooLong() {
		jobApplicant.setSsn("1234567890");
		assertEquals(1, jobApplicant.validateSsn());
	}
	
	@Test
	public void ssnAreaNumberIs000() {
		jobApplicant.setSsn("000223333");
		assertEquals(2, jobApplicant.validateSsn());
	}
	
	@Test
	public void ssnAreaNumberIs666() {
		jobApplicant.setSsn("666223333");
		assertEquals(2, jobApplicant.validateSsn());
	}
	
	@Test
	public void ssnAreaNumberStartsWith9() {
		jobApplicant.setSsn("900223333");
		assertEquals(2, jobApplicant.validateSsn());
	}
	
	@Test
	public void ssnSerialNumberIs0000() {
		jobApplicant.setSsn("111220000");
		assertEquals(3, jobApplicant.validateSsn());
	}
	
	@Test
	public void itRejectsSsn078051120() {
		jobApplicant.setSsn("078051120");
		assertEquals(4, jobApplicant.validateSsn());
	}
	
	@Test
	public void itRejectsSsn219099999() {
		jobApplicant.setSsn("219099999");
		assertEquals(4, jobApplicant.validateSsn());
	}
}
