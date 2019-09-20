
package com.cmc.dashboard.util;

import java.util.Arrays;
import java.util.List;

public class Constants {

	public static final String SESSION_USER = "user";
	public static final String PAGE_ERROR_500 = "500";
	public static final String PAGE_ERROR_403 = "403";
	public static final String DATE_FORMAT = "MM-DD-YYYY";
	public static final String DATE_FORMAT_PARAMS = "yyyy-MM-dd";
	public static final String DATE_FORMAT_MONTH_YEAR = "MM-yyyy";
	public static final String TIME_ZONE = "GMT+7";
	public static final int PAGE_SIZE = 20;
	public static final String URL_IMG = "/resources/icon";
	public static final String BASE_URL = "http://localhost:8080/";
	public static class HTTP_STATUS {
		// SUCCESS
		public static final String OK = "200";
		public static final String CREATED = "201";
		// ERROR
		public static final String BAD_REQUEST = "400";
		public static final String UNAUTHORIZED = "401";
		public static final String FORBIDDEN = "403";
		public static final String NOT_FOUND = "200";
		public static final String INTERNAL_SERVER_ERROR = "500";
		
		public static final String UNPROCESSABLE_ENTITY_EXT = "4221";
		
	}
	
	public static class HTTP_STATUS_MSG {
		public static final String ERROR_COMMON = "Server error!";
		public static final String ERROR_NOT_FOUND = "Tài nguyên không có sẵn";
		public static final String AN_ERROR_OCCURRED = "An error occurred!";
		public static final String ACCESS_DENIED = "Access denied!";
		public static final String DATE_INVALID = "Date invalid";
		public static final String EXCEED_EFFOT_OF_DAY = "Chỉ làm 8h mỗi ngày";
		public static final String ERROR_BAD_REQUEST = "Bad Request";
		public static final String TYPE_RESOURCE_INVALID = "Type invalid. Type is must date, month, week";
		public static final String FROM_DATE_INVALID = "FromDate must after '2017-04-01'";
		public static final String FROM_TO_DATE_INVALID = "FromDate must after ToDate";
		public static final String PAGE_RESOURCE_INVALID = "Page invalid";
		public static final String NUMBER_PER_PAGE_RESOURCE_INVALID = "numberPerPage invalid";
		public static final String LIST_ID_INVALID = "List id invalid";
		public static final String LIST_NAME_INVALID = "List name invalid";
		public static final String PROJECT_OF_USER_NOT_EXIST = "project of User not exist ";
		public static final String USER_ID_NOT_EXIST = "userId not exist ";
		public static final String START_DATE_INVALID = "from must >= startDate of project";
		public static final String END_DATE_INVALID = "to must <= endDate of project";
		public static final String PLAN_ID_NOT_EXIST = "planId not exist ";
		public static final String USER_PLAN_NOT_COMFORTABLE ="Plan is not comfortable with working schedule";
	}

	public static class Numbers {
		public static final int HOURS_PER_DAY = 8;
		public static final int MAN_POWER_VALUE_MAX = 1;
		public static final int MAN_POWER_VALUE_MIN = 0;
		public static final int ZERO = 0;
		public static final int MANDAY_ZERO = 0;
		public static final int SIZE_ONE = 1;
		public static final int SIZE_O1 = -1;
		public static final int FIRST_LIST = 0;
		public static final int FIRST_DAY = 1;
		public static final int PER_UNALLOCTION = 100;
		public static final int LEVEL_STATUS_UNALLOCTION= 100;
		public static final int NE_ZERO= -1;
		public static final int NE_ZERO_FLOAT = -1;
		public static final int MAX_SIZE_EXPORT = 1000000;
		public static final int STATUS_OPEN = 1;
		public static final int STATUS_CLOSED = 5;
		public static final int PROJECT_TYPE_PROFIT = 1;
		public static final int PROJECT_TYPE_START = 2;
		public static final int PROJECT_TYPE_POOL = 3;
		public static final int PROJECT_TYPE_INVESTMENT = 4;
		public static final float MAN_POWER_NULL = -2;
		public static final float ALLOCATION_NULL = -1;
		public static final int UTILIZED_RATE_NULL = -1;
		public static final int EFFORT_EFFICIENCY_NULL = -1;
		public static final int SOLUTION_STATUS_CANCELLED = 4;
		public static final int _1 = 1;
		public static final int _2 = 2;
		public static final int _3 = 3;
		public static final int _4 = 4;
		public static final int _5 = 5;
		public static final int _6 = 6;
		public static final int _7 = 7;
	}

	public static class DateFormart {
		public static final String DATE_MM_YYYY = "MM-yyyy";
		public static final String DATE_D_M_Y_FORMAT2 = "dd-MM-yyyy";
	}
	
	public static class StringPool {
		public static final String UNDERSCORE_CHAR = "_";
		public static final String ESCAPE_UNDERSCORE_CHAR = "\\\\_";
		public static final String PERCENT_CHAR = "%";
		public static final String ESCAPE_PERCENT_CHAR = "\\\\%";
		public static final String START_DATE_TO = "2200-01-01";
		public static final String START_DATE_FROM = "1990-01-01";
		public static final String END_DATE_TO = "2200-01-01";
		public static final String END_DATE_FROM = "1990-01-01";
		public static final String LESS_THAN = "3";
		public static final String MORE_THAN = "2";
		public static final String TYPE_RESOURCE_DATE = "date";
		public static final String TYPE_RESOURCE_WEEK = "week";
		public static final String TYPE_RESOURCE_MONTH = "month";
		public static final String REGEX_MANPOWER = "^-?\\d*(\\d\\.\\d+)?$";
		public static final String MANPOWER = "manPower";
		public static final String EMPTY = "empty";
		public static final String ALLOCATION_VALUE = "allocationValue";
		public static final String MESSAGE_ERROR_MANPOWER = "error value, must >=0 and <=1";
		public static final String OPEN = "Open";
		public static final String CLOSED = "Closed";
		public static final String NA = "N/A";
		public static final String EMPTY_FIELD = "";
		public static final String MODEL_NAME = "writter";
		public static final String HYPHEN_CHAR = "-";
	}

	public static class SortType {
		public static final String DESCENDING = "DESC";
		public static final String ASCENDING = "ASC";
	}
	public static class FieldDeliveryUnit {
		public static final String NAME = "NAME";
		public static final String MANPOWER = "MANPOWER";
		public static final String ALLOCATION = "ALLOCATION";
		public static final String ALLOCATION_TO_OTHER = "ALLOCATIONOTHER";
		public static final String ULTILIZED_RATE = "ULTILIZEDRATE";
		public static final String BILLABLE = "EXTERNALBILLABLE";
		public static final String EFFORT_EFFICIENCY = "EFFORTEFFICIENCY";
		public static final String SPENTTIME = "SPENTTIME";
		public static final String USER = "USER";
	}
	

  public enum TypeResourceConvert {
    USER, PROJECT, ESTIMATE_TIMES, SPENT_TIMES, PLAN_TIMES, MAN_POWER,SKILL;
  }
  
	public static class FieldResourceUnallocation {
		public static final String NAME = "RESOURCENAME";
		public static final String DELIVERY_UNIT = "DELIVERYUNIT";
		public static final String ALLOCATION = "ALLOCATION";
		public static final String STATUS = "STATUS";
		public static final String NO_ALLOCATION = "0";
		public static final String NOT_FULL_ALLOCATION = "1";
		public static final String ALL_ALLOCATION = "";
		public static final String STATUS_NO_ALLOCATION = "No Allocation";
		public static final String STATUS_NOT_FULL_ALLOCATION = "Not Full Allocation";
	}
	
	public static class Issue {
		public static final int ISSUE_CATEGORY_NOT_DELETED = 0;
		public static final int ISSUE_PRIORITY_NOT_DELETED = 0;
		public static final int ISSUE_SERVERITY_NOT_DELETED = 0;
		public static final int ISSUE_SOURCE_NOT_DELETED = 0;
		public static final int ISSUE_STATUS_NOT_DELETED = 0;
		public static final int ISSUE_TYPE_NOT_DELETED = 0;
		
		public static final int ISSUE_STATUS_DEFAULT = 1;
	}

	public static class HeaderExportFile {
		public static final List<String> DELIVERY_UNIT_XLSX_HEADER = Arrays.asList("Delivery Unit", "Total Man Power",
				"Total Allocation", "Adjusted Total Allocation", "Utilized Rate", "All Other", "Internal Billable",
				"External Billable", "Effort Efficiency");
		public static final List<String> RESOURCE_UNALLOCATION_XLSX_HEADER = Arrays.asList("Delivery Unit",
				"Resource Name", "Allocation", "Status");
		public static final List<String> RESOURCE_ALLOCATION_XLSX_HEADER = Arrays.asList("Delivery Unit", "Full Name", "Role", "DU PIC",
				"Project Name", "Project Code", "Project Type", "Project Status", "Plan Allocation", "Allocation");
	}

	public static class DefaultSheet {
		public static final String DELIVERY_UNIT_DEFAULT_SHEET = "Delivery Unit";
		public static final String RESOURCE_UNALLOCATION_DEFAULT_SHEET = "Resource Unallocation";
		public static final String RESOURCE_ALLOCATION_DEFAULT_SHEET = "Resource Allocation";
	}

	public static final String FORMAT_EXPORT_FILE = ".xlsx";
	
	public static class Solution {
		public static final int SOLUTION_COMMENT_NOT_DELETED = 0;
		public static final int SOLUTION_COMMENT_DELETED = 1;
		public static final int STATUS_INT_OPEN = 1;
		public static final int STATUS_INT_DONE = 2;
		public static final int STATUS_INT_INPROCESS = 3;
		public static final int STATUS_INT_CANCEL = 4;
		public static final String STATUS_OPEN = "Open";
		public static final String STATUS_DONE = "Done";
		public static final String STATUS_INPROCESS = "Inprocess";
		public static final String STATUS_CANCEL = "Cancel";
	}

	public static final String STRING_EMPTY = "";
	
	public static class Group {
		public static final int GROUP_MEMBER = 354;
	}

	public static final class WeekDay {
		public static final String MONDAY = "mon";
		public static final String TUESDAY = "tue";
		public static final String WEDNESDAY = "wed";
		public static final String THURSDAY = "thu";
		public static final String FRIDAY = "fri";
		public static final String SATURDAY = "sat";
		public static final String SUNDAY = "sun";
	}
	public static final class Role {
		public static final int ADMIN = 2;
		public static final int QA = 5;
		public static final int PMO = 6;
		public static final int MEMBER = 1;
		public static final int PM = 3;
		public static final int DUL = 4;
		
	}
	public static final class ProjectRole {
		public static final int PM = 1;
		public static final int MEMBER = 0;
	}
	
	public static final class TypeLogger {
		public static final String saveProjectFromUser = " Đồng bộ data project từ user";
		public static final String saveUserFromAMS = " Đồng bộ user từ AMS";
		public static final String autoCanculateManpower = " Tự động tính toán manpower";
	}
	
	public static final class User {
		public static final String IMG = "default.jpg";
	}
	public static final class Level {
		public static final int None = 0;
		public static final int Fresher = 1;
		public static final int Junior = 2;
		public static final int Senior1 = 3;
		public static final int Senior2 = 4;
	}
}
