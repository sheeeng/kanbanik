package com.googlecode.kanbanik.client.modules.editworkflow.workflow.v2;


import com.allen_sauer.gwt.dnd.client.HasDragHandle;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.kanbanik.client.KanbanikResources;
import com.googlecode.kanbanik.dto.WorkflowitemDto;

public class WorkflowitemWidget extends Composite implements HasDragHandle {
	
	@UiField
	PushButton editButton;
	
	@UiField
	PushButton deleteButton;
	
	@UiField
	Label workflowitemName;
	
	@UiField
	FocusPanel header;
	
	@UiField
	Panel content;
	
	interface MyUiBinder extends UiBinder<Widget, WorkflowitemWidget> {}
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	private Widget child;

	private final WorkflowitemDto workflowitem;
	
	public WorkflowitemWidget(WorkflowitemDto workflowitem) {
		this.workflowitem = workflowitem;
		initWidget(uiBinder.createAndBindUi(this));
		workflowitemName.setText(createHeader(workflowitem));
		editButton.getUpFace().setImage(new Image(KanbanikResources.INSTANCE.editButtonImage()));
		deleteButton.getUpFace().setImage(new Image(KanbanikResources.INSTANCE.deleteButtonImage()));
		
		new WorkflowitemDeletingComponent(workflowitem, deleteButton);
		new WorkflowitemEditingComponent(workflowitem, editButton);
	}

	private String createHeader(WorkflowitemDto workflowitem) {
		String wip = workflowitem.getWipLimit() == -1 ? "" : "(" + workflowitem.getWipLimit() + ")";
		return workflowitem.getName() + wip;
	}
	
	public WorkflowitemWidget(WorkflowitemDto workflowitem, Widget child) {
		this(workflowitem);
		this.child = child;
		content.add(child);
	}

	public FocusPanel getHeader() {
		return header;
	}

	public Widget getChild() {
		return child;
	}

	public Widget getDragHandle() {
		return header;
	}

	public WorkflowitemDto getWorkflowitem() {
		return workflowitem;
	}
	
}