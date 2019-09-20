package com.cmc.dashboard.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.cmc.dashboard.dto.ManpowerTime;

public class CalculateClass {
	public  List<Integer> differentNumber(List<Integer> first, List<Integer> second) {
		if (first.size()==0)
			return first;
		else if (second.size()==0)
			return first;
		else {
			List<Integer> result = new ArrayList<Integer>();
			String secondString=second.toString().replace("["," ").replace("]"," ").replace(",","");
			for (Integer tem : first)
				if (secondString.indexOf(" "+tem.toString()+" ") == -1)
					result.add(tem);
			return result;
		}

	}
	public  List<Integer> sameNumber(List<Integer> first, List<Integer> second) {
		if (first.size()==0)
			return first;
		else if (second.size()==0)
			return first;
		else {
			List<Integer> result = new ArrayList<Integer>();
			String secondString=second.toString().replace("["," ").replace("]"," ").replace(",","");
			for (Integer tem : first)
				if (secondString.indexOf(" "+tem.toString()+" ") != -1)
					result.add(tem);
			return result;
		}

	}
	public int calculateWorkingDay(int month,int year) {
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
	public static  int countWorkingDay(Date date1, Date date2) {
   	 int count =0;
   	 LocalDate nextDate = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
   	 LocalDate endDate = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
   	 while(nextDate.isBefore(endDate)||nextDate.isEqual(endDate)) {
   		 if(nextDate.getDayOfWeek().name()!="SUNDAY"&&nextDate.getDayOfWeek().name()!="SATURDAY") count++;
   		 nextDate= nextDate.plusDays(1);
   	 }
   	 return count;
    }
	
	public  int countWorkingDayInMonth(Date date1, Date date2,int month) {
	   	 int count =0;
	   	 LocalDate nextDate = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	   	 LocalDate endDate = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	   	 while(nextDate.isBefore(endDate)||nextDate.isEqual(endDate)) {
	   		 if(nextDate.getDayOfWeek().name()!="SUNDAY"&&nextDate.getDayOfWeek().name()!="SATURDAY"&&nextDate.getMonthValue()==month) count++;
	   		 nextDate= nextDate.plusDays(1);
	   	 }
	   	 return count;
	    }
	public  List<String> devideDateByMonth(Date d1, Date d2,int currentMonth){
   	 List<String> result = new ArrayList<String>();
   	 LocalDate startDate = d1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
   	 LocalDate endDate = d2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
   	 LocalDate nextDate = startDate.plusDays(1);
   	 String element = startDate.toString() + "to";
   	 int month = startDate.getMonthValue();  
   	 while(nextDate.isBefore(endDate)||nextDate.isEqual(endDate)) {
   		 if(nextDate.getMonthValue()!=month) {
   			 LocalDate endDateOfMonth = nextDate.plusDays(-1);
   			 element+=endDateOfMonth.toString();
   			 if(currentMonth==0 || endDateOfMonth.getMonthValue()==currentMonth)
   			 result.add(element);
   			 element=nextDate.toString() + "to";
   			 month = nextDate.getMonthValue();
   			 nextDate=nextDate.plusDays(1);
   			 continue;
   		 }
   		 nextDate=nextDate.plusDays(1);
   	 }
   	 if(nextDate.getDayOfMonth()==1) {
   		 LocalDate endDateOfMonth = nextDate.plusDays(-1);
			 element+=endDateOfMonth.toString();
			 if(currentMonth==0 || endDateOfMonth.getMonthValue()==currentMonth)
			 result.add(element);
   	 }
   	 if(nextDate.getMonthValue()==month) {
   		 LocalDate lastDate = nextDate.plusDays(-1);
   		 element+=lastDate.toString();
   		 if(currentMonth==0 || lastDate.getMonthValue()==currentMonth)
   		 result.add(element);
   	 }
		return result;
   	 
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

        System.out.println("Saturday Count = "+saturday);
        System.out.println("Sunday Count = "+sundays);
        return saturday + sundays;
    }
     public static String themSoKhong(int x) {
    	 if(x<10) return "0"+String.valueOf(x);
    	 else return String.valueOf(x);
     }
     public Date addDays(Date date, int days) {
 		GregorianCalendar cal = new GregorianCalendar();
 		cal.setTime(date);
 		cal.add(Calendar.DATE, days);
 				
 		return cal.getTime();
 	}
    public String convertDateToString(Date date) {
    	Date today = new Date();
    	String result="";
    	SimpleDateFormat formatterDate = new SimpleDateFormat("dd/MM/yyyy");  
    	SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm");
    	if(date!=null) {
    		if(formatterDate.format(date).equals(formatterDate.format(today))) {
    			result="Hôm nay lúc: "+formatterTime.format(date);
    		}
    		else result =formatterDate.format(date)+" lúc: "+formatterTime.format(date);
    		return result;
    	}
    	else return "";
    }
    public Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
          .atZone(ZoneId.systemDefault())
          .toInstant());
    }

    public static ManpowerTime devideTimeManpower(ManpowerTime manPower,ManpowerTime devide ){
		List<ManpowerTime> result = new ArrayList<ManpowerTime>();
		 ManpowerTime mt = new ManpowerTime();
		 LocalDate startDate = manPower.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    	 LocalDate endDate = manPower.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    	 LocalDate startDateDevide = devide.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    	 LocalDate endDateDevide = devide.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    	 int workingDay=0;
    	 double manpower=0.0d; 
    	 // Nếu không giao nhau
    	 if(startDateDevide.isAfter(endDate)||endDateDevide.isBefore(startDate)) {
    		 return null;
    	 }
    	 else
         // Nếu divide nằm trong khoảng đang xét		 
    	 if((startDateDevide.isAfter(startDate)||startDateDevide.isEqual(startDate))&& (endDateDevide.isBefore(endDate)||endDateDevide.isEqual(endDate)))
    	 {   
    		workingDay= countWorkingDay(devide.getStartDate(), devide.getEndDate());
    		 manpower = (double)workingDay/manPower.getWorkingDay() * manPower.getManPower();
    	    devide.setWorkingDay(workingDay);
    	    devide.setManPower(((float) Math.round(manpower * 100) / 100));
    	  //  result.add(devide);
    	    return devide;	 
    	 }
    	 else 
         // Nếu khoảng đang xét gói gọn trong devide
    	 if((startDate.isAfter(startDateDevide)||startDate.isEqual(startDateDevide))&& (endDate.isBefore(endDateDevide)||endDate.isEqual(endDateDevide))) {
//    		 result.add(manPower);
	    	  return manPower;	 
    	 }
    	 else
    	 {  
    		 if(devide.getStartDate().getTime()>=manPower.getStartDate().getTime()&&devide.getStartDate().getTime()<=manPower.getEndDate().getTime()) {
    			 mt.setStartDate(devide.getStartDate());
    			 mt.setEndDate(manPower.getEndDate());
    			 workingDay= countWorkingDay(devide.getStartDate(), manPower.getEndDate());
    			 manpower = (double)workingDay/manPower.getWorkingDay() * manPower.getManPower();
    			 mt.setWorkingDay(workingDay);
    			 mt.setManPower(((float) Math.round(manpower * 100) / 100));
    			 
    		 }
    		 else if(devide.getEndDate().getTime()>=manPower.getStartDate().getTime()&&devide.getEndDate().getTime()<=manPower.getEndDate().getTime()) {
    			 mt.setStartDate(manPower.getStartDate());
    			 mt.setEndDate(devide.getEndDate());
    			 workingDay= countWorkingDay(manPower.getStartDate(),devide.getEndDate());
    			 manpower = (double)workingDay/manPower.getWorkingDay() * manPower.getManPower();
    			 mt.setWorkingDay(workingDay);
    			 mt.setManPower(((float) Math.round(manpower * 100) / 100));
    			 
    		 }
    	 }
		return mt;
	}
	public  List<ManpowerTime> devideManPowerByTime(ManpowerTime manPower,ManpowerTime userPlan, List<ManpowerTime> projectTypeTime) {
		List<ManpowerTime> result = new ArrayList<ManpowerTime>();
		ManpowerTime manPowerPart1 = devideTimeManpower(manPower, userPlan);
		if (manPowerPart1 == null) {
			return result;
		}
		ManpowerTime tem = null;
		for(ManpowerTime man: projectTypeTime) {
			 tem = devideTimeManpower(manPowerPart1, man);
			if(tem!=null) {
				tem.setProjectType(man.getProjectType());
					result.add(tem);
				}
		}	
		return result;
	}
	
	public String getLastDateOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  

        calendar.add(Calendar.MONTH, 1);  
        calendar.set(Calendar.DAY_OF_MONTH, 1);  
        calendar.add(Calendar.DATE, -7);  

        Date lastDayOfMonth = calendar.getTime();  

        return this.convertDateToString(lastDayOfMonth);
	}
	
	public Date getLastNumberDate(Date date, int number_date) {
		Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  

        calendar.add(Calendar.MONTH, 1);  
        calendar.set(Calendar.DAY_OF_MONTH, 1);  
        calendar.add(Calendar.DATE, -number_date);  

        Date date_result = calendar.getTime();  

        return date_result;
	}
	public Boolean checkInRangeDate(String start_date_check, String end_date_check, String start_date, String end_date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start_date_check_parse = sdf.parse(start_date_check);
        Date end_date_check_parse = sdf.parse(end_date_check);
        Date start_date_parse = sdf.parse(start_date);
        Date end_date_parse = sdf.parse(end_date);
        
        if (start_date_check_parse.before(end_date_parse) && end_date_check_parse.after(start_date_parse)) {
        	return true;
        }
        
        return false;
	}
}
