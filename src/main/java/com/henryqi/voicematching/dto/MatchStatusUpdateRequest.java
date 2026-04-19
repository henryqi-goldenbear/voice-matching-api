package com.henryqi.voicematching.dto;

public class MatchStatusUpdateRequest {
	private String status;

	public MatchStatusUpdateRequest() {}

	public MatchStatusUpdateRequest(String status) { this.status = status; }

	public String getStatus() { return status; }
	public String status() { return status; }
	public void setStatus(String status) { this.status = status; }
}
