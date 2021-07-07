package com.neueda.shortenurl.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.neueda.shortenurl.domain.UrlDTO;
import com.neueda.shortenurl.entity.Url;
import com.neueda.shortenurl.service.UrlService;

@RestController
@RequestMapping("/url")
public class UrlController {
	Logger logger = LoggerFactory.getLogger(UrlController.class);
	
	private UrlService urlService;
	
	@Autowired
	public UrlController(UrlService urlService) {
		this.urlService = urlService;
	}
	
	@PostMapping(value = "/")
	public ResponseEntity<Url> urlCreate(@Valid @RequestBody UrlDTO urlDto) {
		Url url = new Url(urlDto.getAlias(),urlDto.getOriginalUrl());
		
		logger.info("Creating URL for "+ url.toString());
		
		url = urlService.createUrl(url);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{alias}").buildAndExpand(url.getAlias())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping("/{alias}")
	public ResponseEntity<Url> urlRedirect(@PathVariable String alias, @RequestHeader Map<String, String> headersMap) throws URISyntaxException{
		logger.info("Redirecting "+ alias);
		
		Url url = urlService.findUrl(alias);
		
		URI uri = new URI(url.getOriginalUrl());
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(uri);

		logger.info("Uri created for Get is "+url.getOriginalUrl());
		return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
	}
	
	@GetMapping(path = "/{alias}/originalUrl")
	public ResponseEntity<Url> findOriginalUrl(@PathVariable String alias) {
		logger.info("Finding original URL for "+alias);

		ResponseEntity<Url> responseEntity;

		Url url = urlService.findUrl(alias);
		responseEntity = ResponseEntity.ok().body(url);

		return responseEntity;
	}

}
