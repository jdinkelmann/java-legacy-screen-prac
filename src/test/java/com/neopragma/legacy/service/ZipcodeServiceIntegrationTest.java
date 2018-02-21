package com.neopragma.legacy.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Test;

import com.neopragma.legacy.business.ApplicantLocation;

public class ZipcodeServiceIntegrationTest {
	private ZipcodeService service;
	
	@Before
	public void beforeEach() {
		service = new ZipcodeService();
	}
	
	@Test
	public void itFindsAddisonTexasBy5DigitZipCode() throws URISyntaxException, IOException {
		ApplicantLocation actualLocation = service.findCityState("75001");
		
		assertEquals("Addison", actualLocation.getCity());
		assertEquals("TX", actualLocation.getState());
	}
	
	@Test
	public void itFindsMaranaArizonaBy9DigitZipCode() throws URISyntaxException, IOException {
		ApplicantLocation actualLocation = service.findCityState("856585578");

		assertEquals("Marana", actualLocation.getCity());
		assertEquals("AZ", actualLocation.getState());
	}
	
	@Test
	public void returnsEmptyApplicantLocationIfNoneFound() throws URISyntaxException, IOException {
		ApplicantLocation actualLocation = service.findCityState("1");
		
		assertEquals("", actualLocation.getCity());
		assertEquals("", actualLocation.getState());
	}
}
