package com.neopragma.legacy.business;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

public class ApplicantLocationTest {

	@Test
	public void createsApplicantLocation() {
		ApplicantLocation location = new ApplicantLocation("Saline", "MI");
		
		assertThat(location.getCity(), is(equalTo("Saline")));
		assertThat(location.getState(), is(equalTo("MI")));
	}
}
