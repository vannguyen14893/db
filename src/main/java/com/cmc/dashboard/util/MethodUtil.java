package com.cmc.dashboard.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.cmc.dashboard.dto.BillableDTO;
import com.cmc.dashboard.dto.LoginParameterObject;
import com.cmc.dashboard.model.ManPower;
import com.cmc.dashboard.model.ProjectBillable;
import com.cmc.dashboard.util.Constants.StringPool;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class MethodUtil {
	
	private static final String PATTERN_DATE_MMYYYY = "MM-yyyy";
	private static final String PATTERN_DATE_YYYYMMDD = "yyyy-MM-dd";
	private static final String PATTERN_DATE_YYYYMM = "yyyy-MM";

	/**
	 * Check object null.<br>
	 * NOTE: use this method only to check null or empty for String only
	 * @param object
	 * @return boolean
	 * @author: Hoai-Nam
	 */
	public static boolean isNull(Object object) {
		return object == null || "".equals(object);
	}

	/**
	 * check list null.
	 * 
	 * 
	 * @param list
	 * @return boolean
	 * @author: Hoai-Nam
	 */
	public static boolean checkList(List<?> list) {
		return list == null || list.isEmpty();
	}

	/**
	 * method convert password get from client into sha1 code.
	 * 
	 * @param input
	 * @return String
	 * @author: Hoai-Nam
	 */
	public static String sha1(String input) {
		if (input == null || input.equalsIgnoreCase("")) {
			return input;
		}
		try {
			MessageDigest mDigest;
			mDigest = MessageDigest.getInstance("SHA1");
			byte[] result = mDigest.digest(input.getBytes());
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < result.length; i++) {
				sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Method check input login;
	 * 
	 * @param data
	 * @param regexter
	 * @return boolean
	 * @author: Hoai-Nam
	 */
	public static boolean validateLoginParams(String data, String regexter) {
		return (data == null || regexter == null || "".equals(data)) ? false : data.matches(regexter);
	}

	/**
	 * method get parameter request from client.
	 * 
	 * @param passedParams
	 * @return LoginParameterObject
	 * @author: Hoai-Nam
	 */
	public static LoginParameterObject getLoginParamsFromString(String passedParams) {
		if (passedParams == null || "".equals(passedParams)) {
			return null;
		}
		try {
			JSONObject object = new JSONObject(passedParams);
			String username = object.getString("username");
			String password = object.getString("password");
			return new LoginParameterObject(username, password);

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * method format float to 1 decimal places.
	 * 
	 * @param number
	 * @return Float
	 * @author: Hoai-Nam
	 */
	public static Float formatFloatNumberType(float number) {
		DecimalFormat formatter = new DecimalFormat("#0.00");
		return Float.valueOf(formatter.format(number));
	}

	public static Double formatDoubleNumberType(double number) {
		DecimalFormat formatter = new DecimalFormat("#.##");
		return Double.valueOf(formatter.format(number));
	}

	/**
	 * method get string in json parameter.
	 * 
	 * @param jObj
	 * @param keyName
	 * @return String
	 * @author: Hoai-Nam
	 */
	public static String getStringValue(JsonObject jObj, String keyName) {
		return jObj.get(keyName).isJsonNull() ? "" : jObj.get(keyName).getAsString();
	}

	/**
	 * method convert String to Date.
	 * 
	 * @param localDate
	 * @return Date
	 * @author: Hoai-Nam
	 */
	public static Date convertStringToDate(String localDate) {
		return Date.from(LocalDate.parse(localDate).atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public static LocalDate converStringToLocalDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_DATE_YYYYMMDD);
		return LocalDate.parse(date, formatter);
	}

	/**
	 * convert String FromMonth To LocalDate
	 * 
	 * @param date
	 * @return LocalDate
	 * @author: NNDuy
	 */
	public static LocalDate converStringFromMonthToLocalDate(String date) {
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern(PATTERN_DATE_YYYYMM)
				.parseDefaulting(ChronoField.DAY_OF_MONTH, 1).toFormatter();
		return LocalDate.parse(date, formatter);
	}

	/**
	 * convert String ToMonth To LocalDate
	 * 
	 * @param date
	 * @return LocalDate
	 * @author: NNDuy
	 */
	public static LocalDate converStringToMonthToLocalDate(String date) {
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern(PATTERN_DATE_YYYYMM)
				.parseDefaulting(ChronoField.DAY_OF_MONTH, 1).toFormatter();
		LocalDate dateConverted = LocalDate.parse(date, formatter);
		return dateConverted.withDayOfMonth(dateConverted.lengthOfMonth());
	}

	/**
	 * convert json to json array.
	 * 
	 * @param json
	 * @return JsonObject
	 * @author: Hoai-Nam
	 */
	public static JsonObject getJsonObjectByString(String json) {
		JsonParser parser = new JsonParser();
		JsonElement tradeElement = parser.parse(json);
		return tradeElement.getAsJsonObject();
	}

	/**
	 * check input json.
	 * 
	 * @param json
	 * @return boolean
	 * @author: Hoai-Nam
	 */
	public static boolean validateJson(String json) {
		try {
			new JSONObject(json);
		} catch (JSONException ex) {
			try {
				new JSONArray(json);
			} catch (JSONException ex1) {
				return false;
			}
		}
		return true;
	}
	

	/**
	 * 
	 * validate date time
	 * @param date
	 * @return boolean 
	 * @author: tvdung
	 */
	public static boolean validateDate(String date) {
		try {
            DateFormat df = new SimpleDateFormat(PATTERN_DATE_YYYYMMDD);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
	}

	/**
	 * Check date type.
	 * 
	 * @param inputDate
	 * @return boolean
	 * @author: Hoai-Nam
	 */
	public static boolean isValid(String input) {
		DateTimeFormatter[] formatters = {
				new DateTimeFormatterBuilder().appendPattern(PATTERN_DATE_YYYYMM).parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
						.toFormatter(),
				new DateTimeFormatterBuilder().appendPattern(PATTERN_DATE_YYYYMMDD).parseStrict().toFormatter() };
		for (DateTimeFormatter formatter : formatters) {
			try {
				LocalDate.parse(input, formatter);
				return true;
			} catch (DateTimeParseException e) {
				return false;
			}
		}
		return false;

	}

	/**
	 * The function to get a map containing start_date and end_date
	 * 
	 * @param month
	 * @param year
	 * @return Map<String,String>
	 * @author: DVNgoc
	 */
	public static Map<String, String> getDayOfDate(int month, int year) {
		Map<String, String> map = new HashMap<>();
		String startDate = null;
		String endDate = null;
		LocalDate initial = LocalDate.of(year, month, 1);
		startDate = initial.withDayOfMonth(1).toString();
		endDate = initial.withDayOfMonth(initial.lengthOfMonth()).toString();
		map.put(CustomValueUtil.START_DATE_KEY, startDate);
		map.put(CustomValueUtil.END_DATE_KEY, endDate);
		return map;
	}

	/**
	 * Validate format String date
	 * 
	 * @param date
	 * @return boolean
	 * @author: CMC-GLOBAL
	 */
	public static boolean isValidDate(int month, int year) {
		try {
			LocalDate date = LocalDate.now();
			LocalDate localDate = LocalDate.of(year, month, 1);
			return ((localDate.isBefore(date) || localDate.isEqual(date))
					&& (localDate.isAfter(CustomValueUtil.MIN_DATE) || localDate.isEqual(CustomValueUtil.MIN_DATE)));
			
		} catch (DateTimeException e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Compare between two parame.
	 * 
	 * @param paramter1
	 * @param paramter2
	 * @return boolean
	 * @author: Hoai-Nam
	 */
	public static boolean compare(String paramter1, String paramter2) {
		return (paramter1.equals(paramter2));
	}

	public static Map<String, Float> getEffortForEachMonth(Date startDate, Date endDate, float totalManday)
			throws ParseException {

		if (startDate.after(endDate)) {
			return null;
		}

		Map<String, Float> mapMonthAndEffort = new HashMap<>();
		DateFormat formater = new SimpleDateFormat(PATTERN_DATE_MMYYYY);

		float workingDays = getTotalWorkingDaysBetweenDate(startDate, endDate);

		float effortPerDay = totalManday / workingDays;
		String sDate = dateToString(startDate);
		String eDate = dateToString(endDate);
		List<String> lstMonth = getMonth2Date(sDate, eDate);
		float effortPerMonth;
		long totalWorkingDays = 0;

		Date startD = null;
		Date endD = null;
		Map<String, String> getStartDate = getDayOfMonth(formater.format(startDate));
		Map<String, String> getEndDate = getDayOfMonth(formater.format(endDate));
		Map<String, String> getCurrentDate;

		LocalDate startDateLocal = dateToLocalDate(startDate);
		LocalDate endDateLocal = dateToLocalDate(endDate);
		Date date = null;
		LocalDate localCurrentDate;
		for (String month : lstMonth) {
			date = getDateNow(month);
			localCurrentDate = dateToLocalDate(date);
			if (startDateLocal.equals(endDateLocal)) {
				totalWorkingDays = getTotalWorkingDaysBetweenDate(startDate, endDate);
			} else if (startDateLocal.getMonthValue() == endDateLocal.getMonthValue()
					&& startDateLocal.getYear() == endDateLocal.getYear()) {
				totalWorkingDays = getTotalWorkingDaysBetweenDate(startDate, endDate);
			} else if (startDateLocal.getMonthValue() == localCurrentDate.getMonthValue()
					&& startDateLocal.getYear() == localCurrentDate.getYear()) {
				totalWorkingDays = getTotalWorkingDaysBetweenDate(startDate,
						getDateNow(getStartDate.get(CustomValueUtil.END_DATE_KEY)));
			} else if (endDateLocal.getMonthValue() == localCurrentDate.getMonthValue()
					&& endDateLocal.getYear() == localCurrentDate.getYear()) {
				startD = getDateNow(getEndDate.get(CustomValueUtil.START_DATE_KEY));
				totalWorkingDays = getTotalWorkingDaysBetweenDate(startD, endDate);
			} else {
				getCurrentDate = getStartEndDayOfMonth(month);
				startD = getDateNow(getCurrentDate.get(CustomValueUtil.START_DATE_KEY));
				endD = getDateNow(getCurrentDate.get(CustomValueUtil.END_DATE_KEY));
				totalWorkingDays = getTotalWorkingDaysBetweenDate(startD, endD);
			}
			effortPerMonth = (effortPerDay * totalWorkingDays);
			mapMonthAndEffort.put(formater.format(date), effortPerMonth);
		}
		return mapMonthAndEffort;
	}

	public static List<DayOfWeek> getIgnoreDays() {
		List<DayOfWeek> ignoreList = new ArrayList<>();
		ignoreList.add(DayOfWeek.SATURDAY);
		ignoreList.add(DayOfWeek.SUNDAY);
		return ignoreList;
	}

	public static Date getDate(String strDate) throws ParseException {
		return new SimpleDateFormat(PATTERN_DATE_YYYYMMDD).parse(strDate);
	}

	public static long getTotalWorkingDaysBetweenDate(Date firstDate, Date secondDate) {
		LocalDate calStart;
		LocalDate originalEnd;
		if (firstDate.after(secondDate)) {
			calStart = secondDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			originalEnd = firstDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		} else {
			calStart = firstDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			originalEnd = secondDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		}
		List<DayOfWeek> ignore = getIgnoreDays();
		LocalDate calEnd = originalEnd.plusDays(1L);

		return Stream.iterate(calStart, d -> d.plusDays(1)).limit(calStart.until(calEnd, ChronoUnit.DAYS))
				.filter(d -> !ignore.contains(d.getDayOfWeek())).count();
	}

	public static long getTotalWorkingDaysBetweenDateWeekend(Date firstDate, Date secondDate, boolean weekend) {
		LocalDate calStart;
		LocalDate originalEnd;
		if (firstDate.after(secondDate)) {
			calStart = secondDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			originalEnd = firstDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		} else {
			calStart = firstDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			originalEnd = secondDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		}
		List<DayOfWeek> ignore = getIgnoreDays();
		LocalDate calEnd = originalEnd.plusDays(1L);
		 if(weekend) {
			return Stream.iterate(calStart, d -> d.plusDays(1)).limit(calStart.until(calEnd, ChronoUnit.DAYS))
				.filter(d -> !ignore.contains(d.getDayOfWeek())).count();
		 }  else {
			 return ChronoUnit.DAYS.between(calStart, originalEnd) + Constants.Numbers._1;
		} 
	}
	
	public static long getTotalWorkingDaysBetweenDateWeekendF(Date firstDate, Date secondDate) {
		LocalDate calStart;
		LocalDate originalEnd;
		if (firstDate.after(secondDate)) {
			calStart = secondDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			originalEnd = firstDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		} else {
			calStart = firstDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			originalEnd = secondDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		}
		return ChronoUnit.DAYS.between(calStart, originalEnd) + Constants.Numbers._1;
	}
	
	public static List<String> getMonthBetween2Date(String sDate, String eDate) {
		List<String> lstMonth = new ArrayList<>();

		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
		DateTimeFormatter dformater = DateTimeFormatter.ofPattern(PATTERN_DATE_MMYYYY);
		LocalDate startDate = LocalDate.parse(sDate, formatter);
		LocalDate endDate = LocalDate.parse(eDate, formatter);

		while (startDate.isBefore(endDate) || startDate.equals(endDate)) {
			startDate = LocalDate.of(startDate.getYear(), startDate.getMonthValue(), 1);
			String month = startDate.format(dformater);
			lstMonth.add(month);
			startDate = startDate.plusMonths(1);
		}
		return lstMonth;
	}

	/**
	 * Get working days of month
	 * 
	 * @param month
	 * @return int
	 * @author: DVNgoc
	 */
	public static int getWokingDaysOfMonth(String month) {
		try {
			LocalDate date = LocalDate.parse(("01-" + month), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			LocalDate startOfMonth = date.withDayOfMonth(1);
			LocalDate endOfMonth = date.withDayOfMonth(date.lengthOfMonth());
			int workingDays = 0;
			while (!startOfMonth.isAfter(endOfMonth)) {
				DayOfWeek day = startOfMonth.getDayOfWeek();
				if ((day != DayOfWeek.SATURDAY) && (day != DayOfWeek.SUNDAY)) {
					workingDays++;
				}
				startOfMonth = startOfMonth.plusDays(1);
			}
			if (workingDays <= 0) {
				return -1;
			} else {
				return workingDays;
			}
		} catch (DateTimeParseException e) {
			return -1;
		}
	}

	public static String getCurrentDate() {
		return LocalDate.now().toString();
	}

	/**
	 * get startdate and enddate of month
	 * 
	 * @param monthYear
	 * @return Map<String,String>
	 * @author: Hoai-Nam
	 */
	public static Map<String, String> getDayOfMonth(String monthYear) {
		Map<String, String> map = new HashMap<>();
		String[] splitDate = monthYear.split("-");
		String startDate = null;
		String endDate = null;
		LocalDate initial = LocalDate.of(Integer.parseInt(splitDate[1]), Integer.parseInt(splitDate[0]), 1);
		startDate = initial.withDayOfMonth(1).toString();
		endDate = initial.withDayOfMonth(initial.lengthOfMonth()).toString();
		map.put("START_DATE_KEY", startDate);
		map.put("END_DATE_KEY", endDate);
		return map;
	}

	public static String dateToString(Date date) {
		DateFormat outputFormatter = new SimpleDateFormat(PATTERN_DATE_YYYYMMDD);
		return outputFormatter.format(date);
	}

	/**
	 * Check user role has access api request
	 * 
	 * @param role
	 * @return true if has role else false
	 */
	public static boolean hasRole(Role role) {
		Collection<? extends GrantedAuthority> auths = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		return auths == null ? false
				: auths.stream().anyMatch(ga -> ga.getAuthority().equals(role.getName())
						|| ga.getAuthority().equals(Role.ADMIN.getName()));

	}

	/**
	 * get Total WorkingDay.
	 * 
	 * @param planMonth
	 * @return int
	 * @author: Hoai-Nam
	 */
	public static int getTotalWorkingDay(String planMonth) {
		Map<String, String> getDay = getDayOfMonth(planMonth);
		Date startDate = null;
		Date endDate = null;
		int total = 0;
		try {
			startDate = new SimpleDateFormat(PATTERN_DATE_YYYYMMDD).parse(getDay.get(CustomValueUtil.START_DATE_KEY));
			endDate = new SimpleDateFormat(PATTERN_DATE_YYYYMMDD).parse(getDay.get(CustomValueUtil.END_DATE_KEY));
			total = (int) getTotalWorkingDaysBetweenDate(startDate, endDate);
		} catch (ParseException e) {
			return 0;
		}
		return total;
	}

	/**
	 *
	 * @return String current date with format {@link #PATTERN_DATE_YYYYMMDD}
	 */
	public static String getDateNow() {
		return new SimpleDateFormat(PATTERN_DATE_YYYYMMDD).format(new Date());
	}

	public static List<String> getMonth2Date(String sDate, String eDate) {
		List<String> lstMonth = new ArrayList<>();

		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
		DateTimeFormatter dFormatter = DateTimeFormatter.ofPattern(PATTERN_DATE_YYYYMMDD);
		LocalDate startDate = LocalDate.parse(sDate, formatter);
		LocalDate endDate = LocalDate.parse(eDate, formatter);

		while (startDate.isBefore(endDate) || startDate.equals(endDate)) {
			startDate = LocalDate.of(startDate.getYear(), startDate.getMonthValue(), 1);
			String month = startDate.format(dFormatter);
			lstMonth.add(month);
			startDate = startDate.plusMonths(1);
		}
		return lstMonth;
	}

	public static Map<String, String> getStartEndDayOfMonth(String monthYear) {
		Map<String, String> map = new HashMap<>();
		String[] splitDate = monthYear.split("-");
		String startDate = null;
		String endDate = null;
		LocalDate initial = LocalDate.of(Integer.parseInt(splitDate[0]), Integer.parseInt(splitDate[1]), 1);
		startDate = initial.withDayOfMonth(1).toString();
		endDate = initial.withDayOfMonth(initial.lengthOfMonth()).toString();
		map.put("START_DATE_KEY", startDate);
		map.put("END_DATE_KEY", endDate);
		return map;
	}

	public static LocalDate dateToLocalDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static Date getDateNow(String date) throws ParseException {
		return new SimpleDateFormat(PATTERN_DATE_YYYYMMDD).parse(date);
	}

	/**
	 * Get delivery unit from DUL group
	 * 
	 * @param group
	 * @return String
	 * @author: NVKhoa
	 */
	public static String getUnitFromDUL(String group) {
		return group.substring(3, group.length());
	}

	/**
	 * Validate Billable isNull or Attribute IssueCode is Null
	 * 
	 * @param billableDTO
	 * @return
	 * @author LXLinh
	 */
	public static JsonObject validateBillable(final BillableDTO billableDto) {
		JsonObject jsonObject = new JsonObject();

		if (isNull(billableDto)) {
			jsonObject.addProperty(MessageUtil.MessegeBillable.BILLABLE, MessageUtil.MessegeBillable.BILLABLE_EMPTY);
			return jsonObject;
		}
		if (isNull(billableDto.getIssueCode())) {
			jsonObject.addProperty(MessageUtil.MessegeBillable.ISSUE_CODE,
					MessageUtil.MessegeBillable.ISSUE_CODE_EMPTY);
			return jsonObject;
		}
		if (billableDto.getBillableValue() == CustomValueUtil.BILLABLE_MIN_VALUE) {
			jsonObject.addProperty(MessageUtil.MessegeBillable.BILLABLE_VALUE,
					MessageUtil.MessegeBillable.BILLABLE_VALUE_ERROR);
			return jsonObject;
		}
		return null;
	}

	/**
	 * Validate List Billable isNull or Attribute IssueCode is Null
	 * 
	 * @param billableDTO
	 * @return
	 * @author LXLinh
	 * @return
	 */
	public static JsonObject validateListBillable(List<BillableDTO> billableDTOs) {
		for (BillableDTO billableDTO : billableDTOs) {
			JsonObject validateBillable = MethodUtil.validateBillable(billableDTO);
			if (validateBillable != null)
				return validateBillable;
		}
		return null;
	}

	/**
	 * Get DayTotal Between Two Date
	 * 
	 * @param startDate
	 * @param endDate
	 * @return long
	 * @author: GiangTM
	 */
	public static long getDayTotalBetweenTwoDate(Date startDate, Date endDate) {
		LocalDate calStart = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate originalEnd = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate calEnd = originalEnd.plusDays(1L);

		return Stream.iterate(calStart, d -> d.plusDays(1)).limit(calStart.until(calEnd, ChronoUnit.DAYS)).count();
	}

	/**
	 * Convert json to ManPower Entity
	 * 
	 * @param json
	 * @return ManPower
	 * @throws JsonParseException
	 *             ManPower
	 * @author: ngocdv
	 */
	public static ManPower convertJsonToManPower(String json) {
		Gson gson = new Gson();
		TypeToken<ManPower> token = new TypeToken<ManPower>() {
		};
		return gson.fromJson(json, token.getType());
	}

	/**
	 * Convert int month and int year from client to String month and year
	 * 
	 * @param month
	 * @param year
	 * @return
	 */
	public static String convertMonthAndYear(int month, int year) {
		LocalDate date = LocalDate.of(year, month, 1);
		return date.toString();
	}

	/**
	 * Get date filter from param of api project List
	 * 
	 * @return list date
	 */
	public static ArrayList<String> getDateFilterProjectList(String startDateEqual, String endDateEqual,
			String startDate, String endDate) {
		ArrayList<String> date = new ArrayList<String>();

		if (StringPool.MORE_THAN.equalsIgnoreCase(startDateEqual)) {
			date.add(startDate);
			date.add(StringPool.START_DATE_TO);
		} else if (StringPool.LESS_THAN.equalsIgnoreCase(startDateEqual)) {
			date.add(StringPool.START_DATE_FROM);
			date.add(startDate);
		} else {
			date.add(StringPool.START_DATE_FROM);
			date.add(StringPool.START_DATE_TO);
		}

		if (StringPool.MORE_THAN.equalsIgnoreCase(endDateEqual)) {
			date.add(endDate);
			date.add(StringPool.END_DATE_TO);
		} else if (StringPool.LESS_THAN.equalsIgnoreCase(endDateEqual)) {
			date.add(StringPool.END_DATE_FROM);
			date.add(endDate);
		} else {
			date.add(StringPool.END_DATE_FROM);
			date.add(StringPool.END_DATE_TO);
		}

		return date;
	}

	public static long getTotalWorkingDays(Date start, Date end) {
		LocalDate calStart = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate originalEnd = new java.sql.Date(end.getTime()).toLocalDate();
		List<DayOfWeek> ignore = getIgnoreDays();
		LocalDate calEnd = originalEnd.plusDays(1L);
		return Stream.iterate(calStart, d -> d.plusDays(1)).limit(calStart.until(calEnd, ChronoUnit.DAYS))
				.filter(d -> !ignore.contains(d.getDayOfWeek())).count();
	}

	/**
	 * 
	 * Convert date timestamp to localDate
	 * 
	 * @param date
	 * @return LocalDate
	 * @author: LXLinh
	 */
	public static LocalDate convertDateTimeToLocalDate(Date date) {
		return new java.sql.Date(date.getTime()).toLocalDate();
	}

	/**
	 * 
	 * get working day type localdate
	 * 
	 * @param start
	 * @param end
	 * @return long
	 * @author: LXLinh
	 */
	public static long getBetweenWorkingDaysLocalDate(LocalDate start, LocalDate end) {
		List<DayOfWeek> ignore = getIgnoreDays();
		LocalDate calEnd = end.plusDays(1L);
		return Stream.iterate(start, d -> d.plusDays(1)).limit(start.until(calEnd, ChronoUnit.DAYS))
				.filter(d -> !ignore.contains(d.getDayOfWeek())).count();
	}

	/**
	 * validate type of resource
	 * 
	 * @param type
	 * @return boolean
	 * @author: NNDuy
	 */
	public static boolean isValidateTypeResource(String type) {
	   // validate null
	   if(isNull(type)) {
	     return false;
	   }
	   
	   return StringPool.TYPE_RESOURCE_DATE.equals(type) || StringPool.TYPE_RESOURCE_WEEK.equals(type)
	           || StringPool.TYPE_RESOURCE_MONTH.equals(type) ? true : false;

	  }
	 
	  /**
	   * validate date
	   * 
	   * @param input
	   * @return boolean 
	   * @author: NNDuy
	   */
	  public static boolean isValidDate(String input) {
      try {
        Date.from(LocalDate.parse(input).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return true;
      } catch (DateTimeException | NullPointerException e) {
        return false;
      }
	  }

	public static Date getDateMinusDay(Date date, int day) {		
		return java.sql.Date.valueOf(Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate().minusDays(day));
	}

	/**
	 * Validate List ManPower
	 * 
	 * @param
	 * @return
	 * @author ntquy
	 * @return
	 */
	public static JsonObject validateListManPowers(List<ManPower> manPower) {
		for (ManPower man : manPower) {
			JsonObject validateManPower = MethodUtil.validateManPower(man);
			if (validateManPower != null)
				return validateManPower;
		}
		return null;
	}

	/**
	 * Validate ManPower
	 * 
	 * @param
	 * @return
	 * @author ntquy
	 */
	public static JsonObject validateManPower(final ManPower manPower) {
		JsonObject jsonObject = new JsonObject();

		if (isNull(manPower)) {
			jsonObject.addProperty(Constants.StringPool.MANPOWER, Constants.StringPool.EMPTY);
			return jsonObject;
		}
		if (isNull(manPower.getAllocationValue())) {
			jsonObject.addProperty(Constants.StringPool.ALLOCATION_VALUE, Constants.StringPool.EMPTY);
			return jsonObject;
		}
		Matcher m = Pattern.compile(Constants.StringPool.REGEX_MANPOWER)
				.matcher(Float.toString(manPower.getAllocationValue()));
		if (!m.find() || (manPower.getAllocationValue() < 0) || (manPower.getAllocationValue() > 1)) {
			jsonObject.addProperty(Constants.StringPool.ALLOCATION_VALUE, Constants.StringPool.MESSAGE_ERROR_MANPOWER);
			return jsonObject;
		}
		return null;
	}

	/**
	 * 
	 * @param month
	 * @param year
	 * @param type
	 * @return
	 * @author tvhuan
	 */
	public static String renMonthFromYm(int month, int year, int type) {
		String months = "" + month;
		if (month < 1 || month > 12 || year <= 2010) {
			return null;
		}
		if (month < 10 && month > 0) {
			months = "0" + month;
		}

		if (type == CustomValueUtil.MONTH_YEAR) {
			return months + "-" + year;
		} else if (type == CustomValueUtil.YEAR_MONTH) {
			return year + "-" + months;
		}
		return null;
	}

	/**
	 * format float of time resource
	 * 
	 * @param input
	 * @return float
	 * @author: NNDuy
	 */
	public static float formatFloatTimeResource(float input) {
		return (float) Math.round(input * 100) / 100;
	}

	/**
	 * get first day of week from date
	 * http://www.java2s.com/Tutorials/Java/Data_Type_How_to/Date/Get_current_week_start_and_end_date_MONDAY_TO_SUNDAY_.htm
	 * 
	 * @param input
	 * @return String
	 * @author: NNDuy
	 */
	public static String getFirstDayOfWeekFromDate(String input) {
		// Go backward to get Monday
		LocalDate monday = converStringToLocalDate(input);
		while (monday.getDayOfWeek() != DayOfWeek.MONDAY) {
			monday = monday.minusDays(1);
		}

		// return Monday of week
		return monday.toString();
	}

	/**
	 * get last day of week from date
	 * http://www.java2s.com/Tutorials/Java/Data_Type_How_to/Date/Get_current_week_start_and_end_date_MONDAY_TO_SUNDAY_.htm
	 * 
	 * @param input
	 * @return String
	 * @author: NNDuy
	 */
	public static String getLastDayOfWeekFromDate(String input) {
		// Go forward to get Sunday
		LocalDate sunday = converStringToLocalDate(input);
		while (sunday.getDayOfWeek() != DayOfWeek.SUNDAY) {
			sunday = sunday.plusDays(1);
		}

		// return Sunday of week
		return sunday.toString();
	}

	/**
	 * get first day of month from date
	 * 
	 * @param input
	 * @return String
	 * @author: NNDuy
	 */
	public static String getFirstDayOfMonthFromDate(String input) {
		// return month and year
		LocalDate date = converStringToLocalDate(input);
		// return first day of month
		return date.with(TemporalAdjusters.firstDayOfMonth()).toString();
	}

	/**
	 * get last day of month from date
	 * 
	 * @param input
	 * @return String
	 * @author: NNDuy
	 */
	public static String getLastDayOfMonthFromDate(String input) {
		// return month and year
		LocalDate date = converStringToLocalDate(input);
		// return first day of month
		return date.with(TemporalAdjusters.lastDayOfMonth()).toString();
	}

	/**
	 * validate page of resource
	 * 
	 * @param page
	 * @return boolean
	 * @author: NNDuy
	 */
	public static boolean isValidatePageNumber(String page) {
		// validate null
		if (isNull(page)) {
			return false;
		}
		try {
			return Integer.parseInt(page) > 0 ? true : false;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * get offset for paging
	 * 
	 * @param page
	 * @param numberPerPage
	 * @return int
	 * @author: NNDuy
	 */
	public static int getOffsetForPaging(final int page, final int numberPerPage) {
		return numberPerPage * (page - 1);
	}

	/**
	 * Format the float number rounded to the unit row
	 * 
	 * @author duyhieu
	 * @param input
	 * @return
	 */
	public static float formatFloat(float input) {
		return (float) Math.round(input * 100) / 100;
	}
	
  /**
   * format value for keyword search
   * 
   * @param keyword
   * @return String 
   * @author: NNDuy
   */
  public static String formatValueKeywordSearch(String keyword) {
    keyword = keyword.replace("%", "\\%");
    keyword = keyword.replace("_", "\\_");
    //keyword = MethodUtil.removeAccent(keyword);
    return keyword;
  }
  
  /**
   * remove Accent from input String
   * 
   * @param input
   *          - input String
   * @return String - String has remove Accent
   * @author: NNDuy
   */
  private static String removeAccent(String input) {
    String temp = Normalizer.normalize(input, Normalizer.Form.NFD);
    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    return pattern.matcher(temp).replaceAll("").replace('đ', 'd').replace('Đ', 'D');
  }
  
  /**
   * Convert status solution(integer) to string to save comment when change status solution.
   * @author dtthuyen1
   * @return
   */
  public static String convertStatusSolutionToString(int status) {
	  Map<Integer, String> listStatus = new HashMap<>();  
		listStatus.put(Constants.Solution.STATUS_INT_OPEN, Constants.Solution.STATUS_OPEN);
		listStatus.put(Constants.Solution.STATUS_INT_DONE, Constants.Solution.STATUS_DONE);
		listStatus.put(Constants.Solution.STATUS_INT_INPROCESS, Constants.Solution.STATUS_INPROCESS);
		listStatus.put(Constants.Solution.STATUS_INT_CANCEL, Constants.Solution.STATUS_CANCEL);
		return listStatus.get(status);
  }
  
  //PNTHANh
  public static int calculateWorkingDay(int month,int year) {
		 Calendar calendar = Calendar.getInstance();
	        int date = 1;
	        calendar.set(year, month-1, date);
	        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	        SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-MM-dd");
	 
			try {
				Date date1 = formatter1.parse(year+"-"+themSoKhong(month)+"-"+themSoKhong(date));
				Date date2= formatter1.parse(year+"-"+themSoKhong(month)+"-"+themSoKhong(maxDay));
				 return maxDay-saturdaysundaycount(date1,date2);
			} catch (ParseException e) {
				
				e.printStackTrace();
				return 0;
			}
	}

	 public static int saturdaysundaycount(Date d1, Date d2) {
	     Calendar c1 = Calendar.getInstance();
	     c1.setTime(d1);
	     Calendar c2 = Calendar.getInstance();
	     c2.setTime(d2);
	
	     int sundays = 0;
	     int saturday = 0;
	
	     while (! c1.after(c2)) {
	         if (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ){
	             saturday++; 
	         }
	         if(c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
	             sundays++;
	         }
	
	         c1.add(Calendar.DATE, 1);
	     }
	     return saturday + sundays;
	 }
	  public static String themSoKhong(int x) {
	 	 if(x<10) return "0"+String.valueOf(x);
	 	 else return String.valueOf(x);
	  }
//PNTHANH
	  public static ProjectBillable convertJsonBillable(String json) {
			Gson gson = new Gson();
			TypeToken<ProjectBillable> token = new TypeToken<ProjectBillable>() {
			};
			return gson.fromJson(json, token.getType());
		}
}
