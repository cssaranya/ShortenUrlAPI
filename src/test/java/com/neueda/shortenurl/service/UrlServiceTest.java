package com.neueda.shortenurl.service;

import java.util.Optional;

import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.ArgumentMatchers.any;

import com.neueda.shortenurl.entity.Url;
import com.neueda.shortenurl.exception.UrlNotFoundException;
import com.neueda.shortenurl.repository.UrlRepository;

@RunWith(MockitoJUnitRunner.class)
public class UrlServiceTest {
	
	@Mock
	private UrlRepository urlRepository;
	
	@InjectMocks
	private UrlService urlService;
	
	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCreateUrl() {
		// Given a complete URL
		String alias = null;
		String originalUrl = "http://www.google.com";
		Url urlObj = new Url(alias, originalUrl);
		
		int startIndex = 0;
		int endIndex = startIndex + 5;
		String newAlias = urlService.encodeUrl(originalUrl, startIndex, endIndex);
		
		Mockito.when(urlRepository.findByAlias(newAlias)).thenThrow(new UrlNotFoundException("Alias does not exist " + newAlias));
		
		Url url = new Url(newAlias, originalUrl);
		
		Mockito.when(urlRepository.save(any(Url.class))).thenReturn(url);
		
		// When
		Url newUrl = urlService.createUrl(urlObj);
		
		// Then
		Assert.assertEquals(url, newUrl);		
	}

	@Test
	public void testFindUrl() {
		// Given an alias
		String existingAlias = "ZWQ2ND";

		Url existingUrl = new Url(existingAlias, "http://www.google.com");
		Optional<Url> optional = Optional.of(existingUrl);
		Mockito.when(urlRepository.findByAlias(existingAlias)).thenReturn(optional);

		// When alias exists
		Url url = urlService.findUrl(existingAlias);

		// Then return original URL
		Assert.assertEquals(existingUrl, url);
	}
	
	@Test(expected = UrlNotFoundException.class)
	public void testUrlNotFound() {
		// Given an alias
		String notFoundAlias = "ZWQ2ND";

		Mockito.when(urlRepository.findByAlias(notFoundAlias))
				.thenThrow(new UrlNotFoundException("Alias does not exist " + notFoundAlias));

		// When alias does not exist
		urlService.findUrl(notFoundAlias);		
	}

}
