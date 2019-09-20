package com.cmc.dashboard.dto;

import com.cmc.dashboard.util.MessageUtil;

public class UpdateResourceDTO {
	private String message;

	private Object listResourceUpdate;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getListResourceUpdate() {
		return listResourceUpdate;
	}

	public void setListResourceUpdate(Object listResourceUpdate) {
		this.listResourceUpdate = listResourceUpdate;
	}

	public UpdateResourceDTO(String message, Object listResourceUpdate) {
		super();
		this.message = message;
		this.listResourceUpdate = listResourceUpdate;
	}

	public static UpdateResourceDTO success(Object listResourceUpdate) {
		return new UpdateResourceDTO(MessageUtil.SUCCESS, listResourceUpdate);
	}

	public static UpdateResourceDTO errorWithData(String message, Object listResourceUpdate) {
		return new UpdateResourceDTO(message, listResourceUpdate);
	}

	public static UpdateResourceDTO errorSQL() {
		return new UpdateResourceDTO(MessageUtil.DATABASE_ERROR, null);
	}

	public static UpdateResourceDTO error(String message) {
		return new UpdateResourceDTO(message, null);
	}

}
