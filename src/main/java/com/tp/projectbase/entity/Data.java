package com.tp.projectbase.entity;

import com.tp.projectbase.utils.AppUtils;
import io.vertx.codegen.annotations.DataObject;

@DataObject(generateConverter = true)
public class Data {

	private Integer id;
	private String deviceId;
	private String eventType;
	private Long contentId;
	private Integer contentType;
	private String country;
	private Long createdDate;

	public Data() {
	}

	public Data(String deviceId, String eventType, Long contentId, Integer contentType, String country) {
		this.deviceId = deviceId;
		this.eventType = eventType;
		this.contentId = contentId;
		this.contentType = contentType;
		this.country = country;
	}

	public static Data valueOf(String json) {
		return AppUtils.fromJson(json, Data.class);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public Integer getContentType() {
		return contentType;
	}

	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Long getCreatedDate() {
		if (createdDate == null) {
			return System.currentTimeMillis();
		}
		return createdDate;
	}

	public Data setCreatedDate(Long createdDate) {
		this.createdDate = createdDate;
		return this;
	}

	@Override
	public String toString() {
		return "{" +
				"id=" + id +
				", deviceId='" + deviceId + '\'' +
				", eventType='" + eventType + '\'' +
				", contentId='" + contentId + '\'' +
				", contentType=" + contentType +
				", country='" + country + '\'' +
				", createdDate=" + createdDate +
				'}';
	}
}
