package com.neueda.shortenurl.controller;

import static org.mockito.BDDMockito.given;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.neueda.shortenurl.entity.Url;
import com.neueda.shortenurl.service.UrlService;

@RunWith(SpringRunner.class)
@WebMvcTest
public class UrlControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UrlService urlService;
	
	  @Test public void testFindOriginalUrlPos() throws Exception{ 
	  // Given 
	  String alias = "ZWQ2ND";
	  
	  Url url = new Url(alias, "http://www.google.com");
	  
	  given(urlService.findUrl(alias)).willReturn(url);
	  
	  // When and Then 
	  this.mockMvc.perform(get("/url/" + alias + "/originalUrl")).andExpect(status().isOk()) 
	  .andExpect(content().
	  json("{'alias': 'ZWQ2ND','originalUrl': 'http://www.google.com'}")); }
	 
	@Test
	public void testUrlCreate()  throws Exception{
		// Given
		String originalUrl = "http://www.google.com";
		String newAlias = "ZWQ2ND";
		
		Url newUrl = new Url(newAlias, originalUrl);
		
		given(urlService.createUrl(any(Url.class))).willReturn(newUrl);

		String inputJson = "{\"originalUrl\":\"http://www.google.com\"}";
	
		// When and Then
		
		  this.mockMvc.perform(post("/url/").contentType(MediaType.
		  APPLICATION_JSON_VALUE).content(inputJson)) 
		  .andExpect(status().isCreated())
		  .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/url/" + newAlias));
		 	
	}
	
	  @Test public void testUrlRedirectPos() throws Exception{ 
	  // Given 
	  String alias = "ZWQ2ND";
	  
	  Url url = new Url(alias, "http://www.google.com");
	  
	  given(urlService.findUrl(alias)).willReturn(url);
	  
	  // When and Then 
	  this.mockMvc.perform(get("/url/" + alias)).andExpect(status().is3xxRedirection())
	  .andExpect(header().string(HttpHeaders.LOCATION,
	  equalTo(url.getOriginalUrl()))); }
	 
}
