package com.cmc.dashboard.util;

public class MessageUtil {
	public static final String LOGIN_SUCCESS = "LOGIN_SUCCESS";
	public static final String LOGIN_ERROR = "LOGIN_ERROR";
	public static final String UNSIGNED_YET = "UNSIGNED_YET";
	public static final String USER_NONEXIST = "USER_NONEXIST";
	public static final String USERNAME_OR_PASSWORD_INVALID = "USERNAME_OR_PASSWORD_INVALID";
	public static final String NO_CONTENT = "NO_CONTENT";
	public static final String GET_BILLABLE_SUCCESS = "GET_BILLABLE_SUCCESS";
	// Project/Css
	public static final String UPDATE_CSS_SUCCESS = "UPDATE_CSS_SUCCESS";
	public static final String UPDATE_CSS_ERROR = "UPDATE_CSS_ERROR";
	//public static final String DELETE_CSS_SUCCESS = "DELETE_CSS_SUCCESS";
	//public static final String DELETE_CSS_ERROR = "DELETE_CSS_ERROR";
	public static final String ADD_CSS_SUCCESS = "ADD_CSS_SUCCESS";
	public static final String ADD_CSS_ERROR = "ADD_CSS_ERROR";

	// Resource Allocate
	public static final String SUCCESS = "SUCCESS";
	public static final String ERROR = "ERROR";
	//public static final String UPDATE_ALLOCATE_SUCCESS = "UPDATE_ALLOCATE_SUCCESS";
	//public static final String UPDATE_ALLOCATE_ERROR = "UPDATE_ALLOCATE_ERROR";
	/*public static final String DELETE_ALLOCATE_SUCCESS = "DELETE_ALLOCATE_SUCCESS";
	public static final String DELETE_ALLOCATE_ERROR = "DELETE_ALLOCATE_ERROR";
	public static final String DATE_TYPE_INVALID = "DATE_TYPE_INVALID";*/
	
	public static final String UPDATE_SUCCESS = "UPDATE_SUCCESS";
	public static final String UPDATE_ERROR = "UPDATE_ERROR";
	public static final String DATA_INVALID = "DATA_INVALID";
	public static final String DATA_VALID = "DATA_VALID";
	public static final String DATA_EMPTY = "DATA_EMPTY";
	public static final String DATA_NOT_EXIST = "DATA_NOT_EXIST";
	public static final String DATA_UNFORMAT = "DATA_UNFORMAT";
	public static final String DATABASE_ERROR = "DATABASE_ERROR";
	
	//
	public static final String CREATE_FILE_SUCCESS = "CREATE_FILE_SUCCESS";
	public static final String CREATE_FILE_FAIL = "CREATE_FILE_FAIL";
	
	public static final String GET_SUCCESS = "GET_SUCCESS";
	public static final String GET_ERROR = "GET_ERROR";
	
	//Permission Manage
	public static final String SAVE_SUCCESS = "SAVE_SUCCESS";
	public static final String SAVE_ERROR = "SAVE_ERROR";
	
	public static final String CREATED_PLAN_SUCCESSFULLY="CREATED_PLAN_SUCCESSFULLY";
	public static final String INTERNAL_ERROR_HAPPENED = "INTERNAL_ERROR_HAPPENED";
	public static final String PARAMETERS_WERE_INVALID="PARAMETERS_WERE_INVALID";
	
	public static final String TIMESHEET_ERROR_FROMDATE = "From date must be not null !";
	public static final String TIMESHEET_ERROR_TODATE = "To date must be not null !";
	public static final String TIMESHEET_ERROR_DATE = "To date must be more than From date !";
	
	public static final String PLAN_ERROR_START = "From date must more than start date project";
	public static final String PLAN_ERROR_END = "To date must less than end date project";
	public static final String PLAN_ERROR_FROMANDTO = "From date must less than to date";
	public static final String PLAN_ERROR_UNKNOW = "Unknown error";
	
	public static final String NOT_FULL_ALLOCATION ="Not Full Allocation";
	public static final String NOT_ALLOCATION ="No Allocation";
	
	public static final String NOT_EXIST ="Not Exist";
	
	public static final String EVALUATE_ERROR_START_END_DATE = "From date must less than to date";
	
	public static final String COMPARE_DATE_TIME = "Start date must less than completed date";
	
	public static final String SAME_MONTH_TIME = "From date, to date must be same month";
	
	public static final String CANNOT_EDIT = "The record cannot be edit";
	
	public static class MessegeBillable {
		public static final String BILLABLE="Billable Object";
		public static final String ISSUE_CODE="Issue code";
		public static final String BILLABLE_VALUE="Billable value";
		public static final String BILLABLE_EMPTY="Billable not null !";
		public static final String ISSUE_CODE_EMPTY="IssueCode value must be not null !";
		public static final String BILLABLE_VALUE_ERROR="Billable must more than 0 !";
	}
	
	public static class MessageProjectRisk{
		public static final String IMPACTDATE = "Latest impact date must be bigger than or equal to earliest impact date";
		public static final String MISSINGIMPACTDATE = "Must fill all of impact date";
		public static final String HANDLINGISACTIVE = "The reason cannot be blank when choose this handling option";
		public static final String REASONISACTIVE = "Must choose handling option before fill reason";
	}
	
	public static class MessageEvaluateProject{
		public static final String PROJECT="project";
		public static final String USER="user";
		public static final String QUALITY ="quality";
		public static final String PROCESS="process";
		public static final String DELIVERY="delivery ";
		public static final String COST="cost";
		public static final String START_DATE="start date";
		public static final String MEMBER="member";
		public static final String EVALUATE_PROJECT_EMPTY="not evaluation of the project";
		public static final String MEMBER_OF_PROJECT="\r\n" + 
				"User is not a member of the project";
		
	}
	
	public static class MessageProjectDelivery{
		
		public static final String COMPARE_DATE_SHEDULE="Schedule date current must be more than schedule date befor";
	}
	
	public static class MessageManPower{
		
		public static final String CREATE_A_MANPOWER="Only create a manpower for a month";
	}	

}
