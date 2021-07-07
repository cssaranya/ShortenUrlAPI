package com.neueda.shortenurl.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Url {

	@Id
	@GeneratedValue
	@JsonIgnore
	private long urlId;
	
	@Column(length = 6)
	private String alias;
	
	@Column(length = 2048)
	private String originalUrl;
	
	@CreationTimestamp
	private Date createdAt;
	
	@UpdateTimestamp
	private Date updatedAt;
	
	public Url() {
	}
	
	public Url(String alias, String originalUrl) {
		super();
		this.alias = alias;
		this.originalUrl = originalUrl;
	}

	public long getUrlId() {
		return urlId;
	}

	public void setUrlId(long urlId) {
		this.urlId = urlId;
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "Url [urlId=" + urlId + ", alias=" + alias + ", originalUrl=" + originalUrl + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + "]";
	}
}
