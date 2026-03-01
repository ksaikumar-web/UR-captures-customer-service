package com.skilltrack.customerservice.model;

import java.time.LocalDateTime;

public class Images {

	private Long id;
	private String customerId;
	private String fileName;
	private String filePath;
	private String contentType;
	private Long sizeBytes;
	private LocalDateTime createdAt = LocalDateTime.now();
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public Long getSizeBytes() {
		return sizeBytes;
	}
	public void setSizeBytes(Long sizeBytes) {
		this.sizeBytes = sizeBytes;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	

}
