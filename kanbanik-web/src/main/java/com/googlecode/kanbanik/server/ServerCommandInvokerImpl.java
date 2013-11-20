package com.googlecode.kanbanik.server;

import com.googlecode.kanbanik.dto.*;
import com.googlecode.kanbanik.dto.shell.*;
import org.apache.shiro.SecurityUtils;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.kanbanik.client.services.ServerCommandInvoker;
import com.googlecode.kanbanik.commands.AddProjectsToBoardCommand;
import com.googlecode.kanbanik.commands.CreateUserCommand;
import com.googlecode.kanbanik.commands.DeleteBoardCommand;
import com.googlecode.kanbanik.commands.DeleteClassOfServiceCommand;
import com.googlecode.kanbanik.commands.DeleteProjectCommand;
import com.googlecode.kanbanik.commands.DeleteTasksCommand;
import com.googlecode.kanbanik.commands.DeleteUserCommand;
import com.googlecode.kanbanik.commands.DeleteWorkflowitemCommand;
import com.googlecode.kanbanik.commands.EditUserCommand;
import com.googlecode.kanbanik.commands.EditWorkflowCommand;
import com.googlecode.kanbanik.commands.EditWorkflowitemDataCommand;
import com.googlecode.kanbanik.commands.GetAllBoardsCommand;
import com.googlecode.kanbanik.commands.GetAllClassOfServices;
import com.googlecode.kanbanik.commands.GetAllProjectsCommand;
import com.googlecode.kanbanik.commands.GetAllUsersCommand;
import com.googlecode.kanbanik.commands.GetBoardCommand;
import com.googlecode.kanbanik.commands.GetCurrentUserCommand;
import com.googlecode.kanbanik.commands.GetTaskCommand;
import com.googlecode.kanbanik.commands.LoginCommand;
import com.googlecode.kanbanik.commands.LogoutCommand;
import com.googlecode.kanbanik.commands.MoveTaskCommand;
import com.googlecode.kanbanik.commands.RemoveProjectFromBoardCommand;
import com.googlecode.kanbanik.commands.SaveBoardCommand;
import com.googlecode.kanbanik.commands.SaveClassOfServiceCommand;
import com.googlecode.kanbanik.commands.SaveProjectCommand;
import com.googlecode.kanbanik.commands.SaveTaskCommand;
import com.googlecode.kanbanik.shared.ServerCommand;
import com.googlecode.kanbanik.shared.UserNotLoggedInException;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.session.mgt.WebSessionKey;

public class ServerCommandInvokerImpl extends RemoteServiceServlet implements ServerCommandInvoker {

	private static final long serialVersionUID = 5624445329544670227L;

	// well... Anybody an idea how to get rid of this huge if-else statement?
    // nvm, rewriting to scala anyway :)
	@SuppressWarnings("unchecked")
	public <P extends Params, R extends Result> R invokeCommand(ServerCommand command, P params) throws UserNotLoggedInException {
        boolean isLoggedIn = false;

        Subject subject = SecurityUtils.getSubject();

        String sessionId = params.getSessionId();
        if (sessionId != null && !"".equals(sessionId)) {
            subject = new Subject.Builder().sessionId(sessionId).buildSubject();
        }

        isLoggedIn = subject.isAuthenticated();

		// unsecure zone - anyone can call this commands whatever the user is logged in or not

		if (!isLoggedIn) {
			throw new UserNotLoggedInException();
		}
		
		// secure zone - only logged in users can call this commands
		if (command == ServerCommand.GET_ALL_BOARDS_WITH_PROJECTS) {
			return (R) new GetAllBoardsCommand().execute((GetAllBoardsWithProjectsParams) params);
		} else if (command == ServerCommand.MOVE_TASK) {
			return (R) new MoveTaskCommand().execute((MoveTaskParams) params);
		} else if (command == ServerCommand.SAVE_TASK) {
			return (R) new SaveTaskCommand().execute((SimpleParams<TaskDto>) params);
		} else if (command == ServerCommand.GET_TASK) {
			return (R) new GetTaskCommand().execute((SimpleParams<TaskDto>) params);
		} else if (command == ServerCommand.DELETE_TASKS) {
			return (R) new DeleteTasksCommand().execute((SimpleParams<ListDto<TaskDto>>) params);
		} else if (command == ServerCommand.GET_ALL_PROJECTS) {
			return (R) new GetAllProjectsCommand().execute((VoidParams) params);
		} else if (command == ServerCommand.SAVE_BOARD) {
			return (R) new SaveBoardCommand().execute((SimpleParams<BoardDto>) params);
		} else if (command == ServerCommand.DELETE_BOARD) {
			return (R) new DeleteBoardCommand().execute((SimpleParams<BoardDto>) params);
		} else if (command == ServerCommand.SAVE_PROJECT) {
			return (R) new SaveProjectCommand().execute((SimpleParams<ProjectDto>) params);
		} else if (command == ServerCommand.DELETE_PROJECT) {
			return (R) new DeleteProjectCommand().execute((SimpleParams<ProjectDto>) params);
		} else if (command == ServerCommand.ADD_PROJECTS_TO_BOARD) {
			return (R) new AddProjectsToBoardCommand().execute((SimpleParams<BoardWithProjectsDto>) params);
		} else if (command == ServerCommand.REMOVE_PROJECTS_FROM_BOARD) {
			return (R) new RemoveProjectFromBoardCommand().execute((SimpleParams<BoardWithProjectsDto>) params);
		} else if (command == ServerCommand.EDIT_WORKFLOW) {
			return (R) new EditWorkflowCommand().execute((EditWorkflowParams) params);
		} else if (command == ServerCommand.GET_BOARD) {
			return (R) new GetBoardCommand().execute((SimpleParams<BoardDto>) params);
		} else if (command == ServerCommand.DELETE_WORKFLOWITEM) {
			return (R) new DeleteWorkflowitemCommand().execute((SimpleParams<WorkflowitemDto>) params);
		} else if (command == ServerCommand.EDIT_WORKFLOWITEM_DATA) {
			return (R) new EditWorkflowitemDataCommand().execute((SimpleParams<WorkflowitemDto>) params);
		} else if (command == ServerCommand.GET_ALL_USERS_COMMAND) {
			return (R) new GetAllUsersCommand().execute((VoidParams) params);
		} else if (command == ServerCommand.EDIT_USER_COMMAND) {
			return (R) new EditUserCommand().execute((SimpleParams<ManipulateUserDto>) params);
		} else if (command == ServerCommand.CREATE_USER_COMMAND) {
			return (R) new CreateUserCommand().execute((SimpleParams<ManipulateUserDto>) params);
		} else if (command == ServerCommand.DELETE_USER_COMMAND) {
			return (R) new DeleteUserCommand().execute((SimpleParams<UserDto>) params);
		} else if (command == ServerCommand.SAVE_CLASS_OF_SERVICE) {
			return (R) new SaveClassOfServiceCommand().execute((SimpleParams<ClassOfServiceDto>) params);
		} else if (command == ServerCommand.DELETE_CLASS_OF_SERVICE) {
			return (R) new DeleteClassOfServiceCommand().execute((SimpleParams<ClassOfServiceDto>) params);
		} else if (command == ServerCommand.GET_ALL_CLASS_OF_SERVICES) {
			return (R) new GetAllClassOfServices().execute((VoidParams) params);
		}

		return null;
	}

}
