package com.neueda.shortenurl.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neueda.shortenurl.entity.Url;
import com.neueda.shortenurl.exception.UrlHashGenException;
import com.neueda.shortenurl.exception.UrlNotFoundException;
import com.neueda.shortenurl.repository.UrlRepository;

@Service
public class UrlService {
	Logger logger = LoggerFactory.getLogger(UrlService.class);

	private UrlRepository urlRepository;
	
	@Autowired
	public UrlService(UrlRepository urlRepository) {
		this.urlRepository = urlRepository;
	}
	
	public Url createUrl(Url url) {
		String originalUrl = url.getOriginalUrl();
		
		logger.info("Create alias if does not exist for "+ originalUrl);
		
		int startIndex = 0;
		int endIndex = startIndex + 5;
		
		return recursiveInsert(originalUrl,startIndex,endIndex);
	}
	
	private Url recursiveInsert(String originalUrl, int startIndex, int endIndex) {
		logger.info("Recirsive insert of "+ originalUrl);

		String alias = encodeUrl(originalUrl,startIndex,endIndex);

		Url url;
		try {
			url = findUrl(alias);

			if (!url.getOriginalUrl().equals(originalUrl)) {
				logger.info("Same alias is already exists for different URLs");

				url = recursiveInsert(originalUrl, startIndex + 1, endIndex + 1);
			}
		} catch (UrlNotFoundException e) {
			logger.warn("Alias not found so creating a new one for alias "+alias+" originalUrl "+originalUrl);
			
			url = urlRepository.save(new Url(alias,originalUrl));
		}

		return url;
	}
	
	public String encodeUrl(String originalUrl, int startIndex, int endIndex) {
		String hash = "";
		
		try {
			byte[] message = MessageDigest.getInstance("MD5").digest(originalUrl.getBytes());

			hash = new BigInteger(1, message).toString(16);
			hash = hash.length() < 32 ? "0".concat(hash) : hash;
		
		} catch (NoSuchAlgorithmException e) {
			throw new UrlHashGenException("The MD5 algorithm is not available in the environment.", e);
		}
		String base64 = Base64.getEncoder().encodeToString(hash.getBytes());
		String encodedUrl = base64.replace('/', '_').replace('+', '-');
		encodedUrl = encodedUrl.substring(startIndex, endIndex + 1);;
		logger.info("Alias for "+originalUrl+" is "+encodedUrl);
		return encodedUrl;
	}
	
	public Url findUrl(String shortUrl) {
		Url url = urlRepository.findByAlias(shortUrl).orElseThrow(() -> new UrlNotFoundException("Alias does not exist " + shortUrl));
		logger.info("Url in findUrl "+url);
		return url;
	}
}