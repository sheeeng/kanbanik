package com.googlecode.kanbanik.client.messaging.messages.project;

import com.googlecode.kanbanik.client.messaging.BaseMessage;
import com.googlecode.kanbanik.client.api.Dtos.ProjectDto;


public class ProjectEditedMessage extends BaseMessage<ProjectDto> {

	public ProjectEditedMessage(ProjectDto payload, Object source) {
		super(payload, source);
	}

}
