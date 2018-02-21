package com.neopragma.legacy.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.neopragma.legacy.business.ApplicantLocation;

public class ZipcodeService {
	public ApplicantLocation findCityState(String zipCode) throws URISyntaxException,IOException {
			ApplicantLocation applicantLocation;
			CloseableHttpResponse response = null;
	        
	        try {
	        	response = getZipCodeData(zipCode);
	            HttpEntity entity = response.getEntity();
	            if (entity != null) {
	              	StringBuffer result = readZipCodeResponse(response);
	              	applicantLocation = extractCityAndStateFromResult(result);
	            } else {
	            	throw new IOException("Service is down");
	            }
	        } finally {
	           response.close(); 
	        }
	        
	        return applicantLocation;
	}

	private ApplicantLocation extractCityAndStateFromResult(StringBuffer result) {
		
		int zipCodeMetaDataPosition = result.indexOf("<meta name=\"description\" content=\"Zip Code ");
		
		if(zipCodeMetaDataPosition > 0) {
			zipCodeMetaDataPosition += 19;
			zipCodeMetaDataPosition = result.indexOf(" - ", zipCodeMetaDataPosition);
			zipCodeMetaDataPosition += 3;
			int stateOffset = result.indexOf(" ", zipCodeMetaDataPosition);
			String city = result.substring(zipCodeMetaDataPosition, stateOffset);
			
			stateOffset += 1;
			String state = result.substring(stateOffset, stateOffset+2);
			return new ApplicantLocation(city, state);
		}
		
		return new ApplicantLocation("", "");
	}

	private StringBuffer readZipCodeResponse(CloseableHttpResponse response) throws IOException {
		BufferedReader rd = new BufferedReader(
		        new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		return result;
	}
	
	private CloseableHttpResponse getZipCodeData(String zipCode) throws URISyntaxException,IOException {
		URI uri = buildZipCodesDotComURI(zipCode);

		HttpGet request = new HttpGet(uri);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        
        return httpclient.execute(request);
	}

	private URI buildZipCodesDotComURI(String zipCode) throws URISyntaxException {
		URI uri = new URIBuilder()
	            .setScheme("http")
	            .setHost("www.zip-codes.com")
	            .setPath("/search.asp")
	            .setParameter("fld-zip", zipCode)
	            .setParameter("selectTab", "0")
	            .setParameter("srch-type", "city")
	            .build();
		return uri;
	}
}
