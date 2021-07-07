package com.neueda.shortenurl.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

public class UrlDTO {

	private String alias;
	
	@NotNull
	@NotEmpty
	@URL
	@Length(max = 2048)
	private String originalUrl;
	
	public UrlDTO() {
	}
	
	public UrlDTO(String alias, String originalUrl) {
		super();
		this.alias = alias;
		this.originalUrl = originalUrl;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getOriginalUrl() {
		return originalUrl;
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}

	@Override
	public String toString() {
		return "UrlDTO [alias=" + alias + ", originalUrl=" + originalUrl + "]";
	}
}
