/**
 * DashboardSystem - com.cmc.dashboard.util
 */
package com.cmc.dashboard.util;

import java.time.LocalDate;

/**
 * @author: DVNgoc
 * @Date: Dec 19, 2017
 */
public class CustomValueUtil {
	//
	public static final String PROJECT_MANAGER = "PM";
	public static final String MEMBER = "MEMBER";
	public static final String DU_LEAD = "DUL";
	public static final String QA = "QA";
	public static final String BOD = "BOD";
	public static final String GROUP_PREFIX = "Cx";

	public static final String COMPANY = "CMC Global";
	public static final LocalDate THE_FOUNDING_DATE_COMPANY = LocalDate.of(2017, 03, 31);

	public static final String ROLE_PM = "3";
	public static final String ROLE_MEMBER = "6";

	// Custom field identify of project
	public static final int DELIVERY_UNIT_USER_ID = 6;
	public static final int DELIVERY_UNIT_ID = 38;
	public static final int MAN_DAY_ID = 9;
	public static final int START_DATE_ID = 35;
	public static final int END_DATE_ID = 36;
	public static final int PROJECT_TYPE_ID = 37;
	public static final int PROJECT_CODE = 41;
	public static final int WORKING_DAY = 8;
	public static final int STATUS_CLOSE = 5;
	public static final int CUSTOM_FIELDS_ID_PLATFORM = 29;

	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATE_NULL = "0001-01-01";
	public static final float NULL_HOURS = -1;
	public static final int IS_SPENT_TIME = 1;
	public static final int IS_ESTIMAED_TIME = 2;
	public static final int IS_PLANED_TIME = 3;
	public static final String CSS_VALUE_UNKNOWN = "0";

	public static final String START_DATE_KEY = "START_DATE_KEY";
	public static final String END_DATE_KEY = "END_DATE_KEY";

	public static final int BILLABLE_MIN_VALUE = 0;
	public static final int BILLABLE_MAX_VALUE = 100;

	public static final LocalDate MIN_DATE = LocalDate.of(2017, 04, 01);

	public static final String SPLIT_CHAR = "\\-";
	public static final int SUBSTRING_INDEX_GROUP = 3;
	public static final String DA = "DA";
	public static final String DA_PREFIX = "DA_";

	// Tracker Custom Value
	public static final int BUG_TRACKER_ID = 1;
	public static final int FEATURE_TRACKER_ID = 2;
	public static final int TASK_TRACKER_ID = 6;

	// Issue Type 
	public static final String BUG = "Bug";
	public static final String FEATURE = "Feature";
	public static final String TASK = "Task";
	public static final String STORY = "Story";
	public static final String LEAKAGE = "Leakage";
	public static final String QANDA = "Q&A";

	// Severity Level
	public static final String COSMETIC = "Cosmetic";
	public static final String MEDIUM = "Medium";
	public static final String SERIOUS = "Serious";
	public static final String FATAL = "Fatal";

	// Weighted
	public static final int WEIGHTED_ONE = 1;
	public static final int WEIGHTED_TWO = 2;
	public static final int WEIGHTED_THREE = 3;
	public static final int WEIGHTED_FOUR = 4;

	// Custom Fields Id
	public static final int SEVERITY_CUSTOM_FIELDS_ID = 13;
	public static final int STORY_POINT_CUSTOM_FIELDS_ID = 4;

	// KPI name
	public static final String BUG_RATE = "Bug rate";
	public static final String LEAKAGE_RATE = "Leakage rate";
	public static final String REWORK_RATE = "Rework rate";
	public static final String BILLABLE_RATE = "Billable rate";
	public static final String PRODUCTIVITY = "Productivity";
	public static final String DELIVERY_TIMELINESS = "Timeliness";
	public static final String EFFORT_DEVIATION = "Effort Deviation";
	public static final String CSS = "CSS";
	public static final String PCV_RATE = "PCV rate";
	public static final String COMMENT_RATE = "Comment rate";

	// KPI Unit
	public static final String UNIT_MAN_DAY = "md";
	public static final String UNIT_WDF_MAN_DAY = "wdf/md";
	public static final String UNIT_PERCENT = "%";
	public static final String UNIT_STORY_POINT_MAN_DAY = "sp/md";
	public static final String UNIT_POINT = "p";
	public static final String UNIT_NO_OF_COMMENTS_MAN_DAY = "noc/md";
	public static final String UNIT_HOUR = "h";
	public static final String UNIT_STORY_POINT = "sp";
	public static final String UNIT_MILESTONES = "milestones";
	public static final String UNIT_ONTIME = "ontime";
	public static final String UNIT_LINE_OF_CODE = "loc";
	public static final String UNIT_PRODUCTIVITY = "loc/h";

	public static final String DATE_D_M_Y_FORMAT = "dd/MM/yyyy";

	// Colums sort
	public static final String PROJECT_NAME_C = "projectName";
	public static final String PROJECT_SIZE_C = "projectSize";
	public static final String DELIVERY_UNIT_C = "deliveryUnit";
	public static final String START_DATE_C = "startDate";
	public static final String END_DATE_C = "endDate";
	public static final String ASC = "ASC";
	public static final String DESC = "DESC";

	// Prefix
	public static final String PREFIX_LEAKAGE = "[Leakage]";
	public static final String PREFIX_REVIEW = "[Review]";

	// Issue statuses id
	public static final int ISSUE_STATUS_ID_NEW = 1;
	public static final int ISSUE_STATUS_ID_IN_PROGRESS = 2;
	public static final int ISSUE_STATUS_ID_RESOLVED = 3;
	public static final int ISSUE_STATUS_ID_CLOSED = 5;
	public static final int ISSUE_STATUS_ID_REJECTED = 6;

	public static final String DEFAULT_FORMAT_FLOAT = "#.00";
	public static final String DEFAULT_PROJECT_LABEL = "All Projects";
	public static final String DEFAULT_DU_LABEL = "All Du";
	public static final String DEFAULT_DUPIC_LABEL = "All DuPic";

	// KPI code
	public static final int BUG_RATE_CODE = 1;
	public static final int LEAKAGE_RATE_CODE = 2;
	public static final int COMMENT_RATE_CODE = 3;
	public static final int REWORK_RATE_CODE = 4;
	public static final int EFFORT_DEVIATION_CODE = 5;
	public static final int PRODUCTIVITY_CODE = 6;
	public static final int BILLABLE_RATE_CODE = 7;
	public static final int DELIVERY_TIMELINESS_CODE = 8;
	public static final int CSS_CODE = 9;
	public static final int PCV_RATE_CODE = 10;
	
	public static final int PAGE_SIZE = 20;
	public static final int MAX_PAGE_SIZE = 1000000;
	public static final int MONTH_YEAR = 1;
	public static final int YEAR_MONTH = 0;

	// common constant
	public static final int WORKING_TIME_FOR_A_DAY = 8;

	public static final String DB_SORT_QMS = "CVS.";
	public static final String DB_SORT_DASBOARD = "db.";
	public static final String RESOURCE_COLUMN_USERNAME = "username";
	public static final String RESOURCE_COLUMN_DU = "du";
	public static final String RESOURCE_COLUMN_ROLE = "role";
	public static final String RESOURCE_COLUMN_DUPIC = "duPic";
	public static final String RESOURCE_COLUMN_PROJECTNAME = "name";
	public static final String RESOURCE_COLUMN_PROJECTCODE = "projectCode";
	public static final String RESOURCE_COLUMN_PROJECTTYPE = "projectType";
	public static final String RESOURCE_COLUMN_STATUS = "status";
	public static final String RESOURCE_COLUMN_PLANALLOC = "planAllocation";
	public static final String RESOURCE_COLUMN_ALLOC = "allocation";

	// Risk statuses id
	public static final int RISK_STATUS_ID_OPEN = 1;

	// Project statuses
	public static final int PROJECT_STATUS_OPEN = 1;

	public static final int GROUP_MEMBER = 354;

}
