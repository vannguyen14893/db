package com.cmc.dashboard.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmc.dashboard.dto.HistoryUserGroupDTO;
import com.cmc.dashboard.dto.ListUserInfoDTO;
import com.cmc.dashboard.dto.ManPowerDTO;
import com.cmc.dashboard.dto.ManpowerOlderDTO;
import com.cmc.dashboard.dto.UserDuDTO;
import com.cmc.dashboard.dto.UserManPowerDTO;
import com.cmc.dashboard.exception.BusinessException;
import com.cmc.dashboard.model.Group;
import com.cmc.dashboard.model.GroupUser;
import com.cmc.dashboard.model.ManPower;
import com.cmc.dashboard.qms.repository.UserQmsRepository;
import com.cmc.dashboard.repository.GroupRepository;
import com.cmc.dashboard.repository.GroupUserRepository;
import com.cmc.dashboard.repository.ManPowerRepository;
import com.cmc.dashboard.repository.UserRepository;
import com.cmc.dashboard.util.CalculateClass;
import com.cmc.dashboard.util.Constants;
import com.cmc.dashboard.util.Constants.User;
import com.cmc.dashboard.util.MessageUtil;
import com.cmc.dashboard.util.MethodUtil;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

@Service
public class ManPowerServiceImpl implements ManPowerService {
	@Autowired
	ManPowerRepository manPowerRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	UserQmsRepository userQmsRepository;
	
	@Autowired
	GroupRepository  groupRepository;
	
	@Autowired
	GroupUserRepository  groupUserRepository;

	/**
	 * Lay het du lieu tu bang man power len
	 *
	 * @return List<ManPower>
	 * @author: HungNC
	 * @created: 2018-04-07
	 */
	private List<ManPower> getAllData() {
		return manPowerRepository.getAll();
	}

	/**
	 * Neu toDate == null thi set toDate ve ngay cuoi thang
	 *
	 * @param manPowers
	 * @param time      void
	 * @author: HungNC
	 * @created: 2018-04-07
	 */
	private void handleToDateNull(List<ManPower> manPowers, String time) {
		for (ManPower manPower : manPowers) {
			if (manPower.getToDate() == null) {
				manPower.setToDate(MethodUtil.convertStringToDate(MethodUtil.getDayOfMonth(time).get("END_DATE_KEY")));
			}
		}
	}

	/**
	 * Lay du lieu theo thoi gian truyen vao. Cat du lieu thua ve ngay dau thang
	 * hoac cuoi thang
	 *
	 * @param time
	 * @return List<ManPower>
	 * @author: HungNC
	 * @created: 2018-04-07
	 */
	private List<ManPower> getDataByTime(String time) {
		List<ManPower> data = this.getAllData();
		Date startMonth = MethodUtil.convertStringToDate(MethodUtil.getDayOfMonth(time).get("START_DATE_KEY"));
		Date endMonth = MethodUtil.convertStringToDate(MethodUtil.getDayOfMonth(time).get("END_DATE_KEY"));

		this.handleToDateNull(data, time);

		this.handleFormatToDate(data, time);

		List<ManPower> list = new ArrayList<>();

		for (ManPower manPower : data) {
			Date fromDate = manPower.getFromDate();
			Date toDate = manPower.getToDate();

			// fromDate <= startMonth && toDate >= endMonth
			if (this.isBeforeOrEqual(fromDate, startMonth) && this.isAfterOrEqual(toDate, endMonth)) {
				manPower.setFromDate(startMonth);
				manPower.setToDate(endMonth);
				list.add(manPower);
			}

			// fromDate <= startMonth && startMonth <= toDate <= endMonth
			else if (this.isBeforeOrEqual(fromDate, startMonth) && this.isAfterOrEqual(toDate, startMonth)
					&& this.isBeforeOrEqual(toDate, endMonth)) {
				manPower.setFromDate(startMonth);
				list.add(manPower);
			}

			// startMonth <= fromDate <= endMonth && toDate >= endMonth
			else if (this.isAfterOrEqual(toDate, endMonth) && this.isAfterOrEqual(fromDate, startMonth)
					&& this.isBeforeOrEqual(fromDate, endMonth)) {
				manPower.setToDate(endMonth);
				list.add(manPower);
			}

			// startMonth <= fromDate <= endMonth && startMonth <= toDate <=
			// endMonth
			else if (this.isAfterOrEqual(fromDate, startMonth) && this.isBeforeOrEqual(fromDate, endMonth)
					&& this.isAfterOrEqual(toDate, startMonth) && this.isBeforeOrEqual(toDate, endMonth)) {
				list.add(manPower);
			}
		}

		return list;
	}

	/**
	 * Format lai toDate
	 *
	 * @param manPowers
	 * @param time
	 * @author: HungNC
	 * @created: 2018-04-08
	 */
	private void handleFormatToDate(List<ManPower> manPowers, String time) {
		for (ManPower manPower : manPowers) {
			manPower.setToDate(this.formatDate(manPower.getToDate()));
		}
	}

	/**
	 * Kiem tra ngay <= ngay nao do
	 *
	 * @param date
	 * @param dateCompare
	 * @return boolean \n true : date <= dateCompare
	 * @author: HungNC
	 * @created: 2018-04-07
	 */
	private boolean isBeforeOrEqual(Date date, Date dateCompare) {
		if (date.before(dateCompare) || date.equals(dateCompare)) {
			return true;
		}
		return false;
	}

	/**
	 * Kiem tra ngay >= ngay nao do
	 *
	 * @param date        : ngay truyen vao
	 * @param dateCompare : ngay muon so sanh
	 * @return boolean \n true : date >= dateCompare
	 * @author: HungNC
	 * @created: 2018-04-07
	 */
	private boolean isAfterOrEqual(Date date, Date dateCompare) {
		if (date.after(dateCompare) || date.equals(dateCompare)) {
			return true;
		}
		return false;
	}

	/**
	 * Tinh tong man power theo tung user
	 *
	 * @param time
	 * @return Map<Integer,Float>
	 * @author: HungNC
	 * @created: 2018-04-07
	 */
	private Map<Integer, Float> sumManPowerByUserId(String time) {
		List<ManPower> manPowers = this.getDataByTime(time);
		List<UserManPowerDTO> userDTOs = new ArrayList<>();
		for (ManPower manPower : manPowers) {
			long workingDay = MethodUtil.getTotalWorkingDaysBetweenDate(manPower.getFromDate(), manPower.getToDate());
			float valueManPower = (workingDay * manPower.getAllocationValue());
			userDTOs.add(new UserManPowerDTO(manPower.getUserId(), valueManPower, workingDay));
		}

		Map<Integer, Float> map = new HashMap<>();

		for (UserManPowerDTO userDTO : userDTOs) {

			if (map.containsKey(userDTO.getUserId())) {
				map.put(userDTO.getUserId(), map.get(userDTO.getUserId()) + userDTO.getManPower());
			} else {
				map.put(userDTO.getUserId(), userDTO.getManPower());
			}

		}

		return map;
	}

	/**
	 * format date sang dang yyyy-MM-dd
	 *
	 * @param toDate
	 * @return Date
	 * @author: HungNC
	 * @created: 2018-04-08
	 */
	private Date formatDate(Date toDate) {
		SimpleDateFormat f = new SimpleDateFormat(Constants.DATE_FORMAT_PARAMS);
		return MethodUtil.convertStringToDate(f.format(toDate));
	}

	/**
	 * Get all user by du
	 *
	 * @return List<UserManPowerDTO>
	 * @author: HungNC
	 * @created: 2018-04-08
	 */
	private List<UserManPowerDTO> getAllUserByDU() {
		// Get list object from database
		List<Object> objects = userQmsRepository.getAllUserByDeliveryUnit();

		List<UserManPowerDTO> list = new ArrayList<>();

		if (objects == null) {
			return list;
		}

		// convert list object to DTO
		for (Object object : objects) {
			Object[] obj = (Object[]) object;
			UserManPowerDTO userManPowerDTO = new UserManPowerDTO();
			userManPowerDTO.setUserId(Integer.parseInt(obj[0].toString()));
			userManPowerDTO.setDeliveryUnit(obj[1] == null ? "" : obj[1].toString());
			list.add(userManPowerDTO);
		}

		return list;
	}

	/**
	 * Lay duoc list manpower cua tat ca cac user trong cong ty (bao gom tat ca cac
	 * DU )
	 *
	 * @return List<UserManPowerDTO>
	 * @author: HungNC
	 * @created: 2018-04-08
	 */
	@Override
	public List<UserManPowerDTO> getManPowerByUserIdOfAllDU(String time) {

		// ManPower theo User
		Map<Integer, Float> manPowerByUserId = this.sumManPowerByUserId(time);

		// List user theo DU
		List<UserManPowerDTO> userManPowerDTOs = this.getAllUserByDU();

		// Kiem tra user_id = nhau thi set manpower
		for (UserManPowerDTO u : userManPowerDTOs) {
			if (manPowerByUserId.containsKey(u.getUserId())) {
				u.setManPower(manPowerByUserId.get(u.getUserId()));
			}
		}
		return userManPowerDTOs;
	}

	/**
	 * format date sang dang yyyy-MM
	 *
	 * @param fromDate
	 */
	private Date formatDateMonthYear(Date fromDate) {
		SimpleDateFormat f = new SimpleDateFormat(Constants.DATE_FORMAT_MONTH_YEAR);
		return MethodUtil.convertStringToDate(f.format(fromDate));
	}

	@Override
	public ManPower update(ManPower mp) {
		ManPower entity = manPowerRepository.getOne(mp.getManPowerId());
		if (entity == null) {
			throw new BusinessException(MessageUtil.NOT_EXIST);
		}
		if (this.formatDateMonthYear(entity.getFromDate()).after(this.formatDateMonthYear(new Date()))) {
			throw new BusinessException("The record connot be edit");
		}
		if (this.isBeforeOrEqual(entity.getFromDate(), entity.getToDate())) {
			throw new BusinessException(MessageUtil.EVALUATE_ERROR_START_END_DATE);
		}

		entity.setAllocationValue(mp.getAllocationValue());
		entity.setFromDate(mp.getFromDate());
		entity.setToDate(mp.getToDate());
		entity.setUpdatedOn(new Date());

		ManPower success = null;
		try {
			success = manPowerRepository.save(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	@Override
	public boolean getManPowerFromAms(ManPowerDTO[] manPowerDTOs, String manMonth) {
		if(manPowerDTOs != null) {
			List<Object> objects = userRepository.getListUserOfDu();
			List<UserDuDTO> userDuDTOs = new ArrayList<>();
			for (Object object : objects) {
				Object[] obj = (Object[]) object;
				UserDuDTO userDuDTO = new UserDuDTO();
				userDuDTO.setUserId(Integer.parseInt(obj[0].toString()));
				userDuDTO.setUserName(obj[1].toString());
				userDuDTOs.add(userDuDTO);
			}
			List<String> userNameAms = new ArrayList<>();
			for (ManPowerDTO manPowerDTO : manPowerDTOs) {
				userNameAms.add(manPowerDTO.getUserName());
			}
			for (UserDuDTO user : userDuDTOs) {			
					ManPower manPowerInsert = new ManPower();
					manPowerInsert.setManMonth(manMonth);
					int dayOff = 0;
					if(userNameAms.contains(user.getUserName())) {
//						ManPowerDTO manPowerDTO = manPowerDTOs.stream().filter(manPower -> user.getUserName().equals(manPower.getUserName())).findAny().orElse(null);
						for (ManPowerDTO manPowerDTO : manPowerDTOs) {
							if(manPowerDTO.getUserName().equals(user.getUserName())) {
								dayOff = manPowerDTO.getNumberDayOff();
							}
						}	
					} else {
						dayOff = 0;
					}
					manPowerInsert.setDayOff(dayOff);
					manPowerInsert.setUserId(user.getUserId());
					String[] strings = manMonth.split("-");
					double value = ((double) (this.numberOfWorkingDayInMonth(Integer.parseInt(strings[0]),Integer.parseInt(strings[1])) - dayOff) / (double) this.numberOfWorkingDayInMonth(Integer.parseInt(strings[0]),Integer.parseInt(strings[1])));
					DecimalFormat df = new DecimalFormat("0.00");
					manPowerInsert.setAllocationValue( Float.parseFloat(df.format(value))); 
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					String dateInString = "01/" + Integer.parseInt(strings[0]) + "/" + Integer.parseInt(strings[1]);
					Date date = null;
					
					try {
						date = formatter.parse(dateInString);
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					manPowerInsert.setFromDate(getFirstDateOfMonth(date));
					manPowerInsert.setToDate(getLastDateOfMonth(date));
					manPowerRepository.save(manPowerInsert);
			}
			return true;
		}
		return false;
	}
	
	@Override
	public boolean getManpowerOlderFromAms(ManpowerOlderDTO[] manpowerOlderDTOs) {
		if(manpowerOlderDTOs != null) {
			List<Object> objects = userRepository.getListUserOfDu();
			List<UserDuDTO> userDuDTOs = new ArrayList<>();
			for (Object object : objects) {
				Object[] obj = (Object[]) object;
				UserDuDTO userDuDTO = new UserDuDTO();
				userDuDTO.setUserId(Integer.parseInt(obj[0].toString()));
				userDuDTO.setUserName(obj[1].toString());
				userDuDTOs.add(userDuDTO);
			}
			
		List<ListUserInfoDTO> listUserInfoDTOs = new ArrayList<>();
		for (ManpowerOlderDTO manpowerOlderDTO : manpowerOlderDTOs) {
			listUserInfoDTOs = manpowerOlderDTO.getListInfoUser();
		}
		
		List<String> userNameAms = new ArrayList<>();
		for (ListUserInfoDTO listUserInfoDTO : listUserInfoDTOs) {
			userNameAms.add(listUserInfoDTO.getUserName());
		}
		List<String> manMonths = new ArrayList<>();
		for (ManpowerOlderDTO manpowerOlderDTO : manpowerOlderDTOs) {
			manMonths.add(manpowerOlderDTO.getMonthOff());
		}
		for (String manMonth : manMonths) {
			for (UserDuDTO user : userDuDTOs) {	
				ManPower manPowerInsert = new ManPower();
				manPowerInsert.setManMonth(manMonth);
				int dayOff = 0;
				if(userNameAms.contains(user.getUserName())) {
					for(ManpowerOlderDTO manpowerOlderDTO : manpowerOlderDTOs) {
						if(manMonth.equals(manpowerOlderDTO.getMonthOff())) {
							List<ListUserInfoDTO> listUserInfoDTOs1 = manpowerOlderDTO.getListInfoUser();
							for (ListUserInfoDTO listUserInfoDTO : listUserInfoDTOs1) {
								if(listUserInfoDTO.getUserName().equals(user.getUserName())) {
									dayOff = listUserInfoDTO.getNumberDayOff();
									break;
								}
							}
						}
						break;
					}
				} else {
					dayOff = 0;
				}
				manPowerInsert.setDayOff(dayOff);
				manPowerInsert.setUserId(user.getUserId());
				String[] strings = manMonth.split("/");
				double value = ((double) (this.numberOfWorkingDayInMonth(Integer.parseInt(strings[0]),Integer.parseInt(strings[1])) - dayOff) / (double) this.numberOfWorkingDayInMonth(Integer.parseInt(strings[0]),Integer.parseInt(strings[1])));
				DecimalFormat df = new DecimalFormat("0.00");
				manPowerInsert.setAllocationValue( Float.parseFloat(df.format(value))); 
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				String dateInString = "01/" + Integer.parseInt(strings[0]) + "/" + Integer.parseInt(strings[1]);
				Date date = null;
				
				try {
					date = formatter.parse(dateInString);
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				manPowerInsert.setFromDate(getFirstDateOfMonth(date));
				manPowerInsert.setToDate(getLastDateOfMonth(date));
				manPowerRepository.save(manPowerInsert);
			}
		}	
		}
		
		
		return false;
	}
	
	public static Date getFirstDateOfMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }
	public static Date getLastDateOfMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }
	//count the number of working days in a month
	public int numberOfWorkingDayInMonth(int month, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		 int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		    int count = 0;
		    for (int day = 1; day <= daysInMonth; day++) {
		        calendar.set(year, month - 1, day);
		        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		        if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY) {
		            count++;
		        }
		    }
		    int workingDayInMonth = daysInMonth - count;
		return workingDayInMonth;
	}

	@Override
	public boolean getManpower() {
//		Set<Integer> userIds = userRepository.getListUserId();
//		LocalDateTime now = LocalDateTime.now();
//		int year = now.getYear();
//		int month = now.getMonthValue();
//		for (Integer userId : userIds) {
//			ManPower manPowerInsert = new ManPower();
//			manPowerInsert.setManMonth((month - 1) + "-" + year);
//			manPowerInsert.setDayOff(0);
//			manPowerInsert.setUserId(userId);
//			manPowerInsert.setAllocationValue(1); 
//			manPowerRepository.save(manPowerInsert);
//		}
		return true;
	}
	
	@Override
	public void generateManpowerAuto() {
		LocalDate now = LocalDate.now();
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		int year = now.getYear();
		int month= now.getMonthValue();
		int numDayInMonth = now.lengthOfMonth();
		String startDate= year +"-"+(month<10?("0"+month):month)+"-"+"01";
		String endDate =  year +"-"+(month<10?("0"+month):month)+"-"+numDayInMonth;
		List<com.cmc.dashboard.model.User> listAll = userRepository.findAll();
		ManPower man = null;
		try {
		for(com.cmc.dashboard.model.User user : listAll) {
			man = manPowerRepository.findByUserGroupMonthYear(user.getUserId(), user.getGroup().getGroupId(), month, year);
			if(user.getStatus()==1 && man ==null) {
				man = new ManPower();
				man.setAllocationValue(1.0f);
				try {
					man.setFromDate(formatter.parse(startDate));
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					man.setToDate(formatter.parse(endDate));
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				man.setUserId(user.getUserId());
				man.setGroupId(user.getGroup().getGroupId());
				man.setManMonth(month+"-"+year);
				System.out.println(user.getUserId());
				manPowerRepository.save(man);
			}
		}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteManpowerFromDateToDate(int userId, Date fromDate, Date toDate) {
		manPowerRepository.deleteManByFromDateToDate(userId, fromDate, toDate);
		
	}

	@Override
	public void deleteManPowerMonth(int userId, int month, int year) {
		manPowerRepository.deleteManByMonthYear(userId, month, year);
		
	}

	@Override
	public void updateManPowerByGroupUser(GroupUser groupUser, GroupUser oldGroupUser) {
	  SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
	  CalculateClass cal= new CalculateClass();
	  //Xóa các manpower trước nếu có 
	  if(oldGroupUser!=null) {
		  try {
			if(oldGroupUser.getEndDate()!=null)
		    manPowerRepository.deleteManByFromDateToDate(oldGroupUser.getUserId(), oldGroupUser.getStartDate(), oldGroupUser.getEndDate()); 
			else manPowerRepository.deleteManByFromDate(oldGroupUser.getUserId(), oldGroupUser.getStartDate());
		  } catch(Exception e) {
			  e.printStackTrace();
		  }
	  }
	  //Cập nhật manpower
	  LocalDate current = LocalDate.now();
	  LocalDate dateDes=current.plusMonths(4).withDayOfMonth(1);
	  LocalDate underLimitedDate = LocalDate.of(2019, 4, 1);
	  Date startDate = groupUser.getStartDate();
	  Date endDate = groupUser.getEndDate();
	  if(endDate ==null) endDate= cal.convertToDateViaInstant(dateDes.plusDays(-1)); 
	  if(endDate.getTime()<startDate.getTime()) return ;
	  List<String> devideTime=cal.devideDateByMonth(startDate, endDate, 0);
	  String[] splitTime = null;
	  Date fromDate= null;
	  Date toDate= null;
	  int counWorkingDay=0;
	  if(devideTime.size()>0) {
		  try {
		  for(String time : devideTime) {
		  splitTime= time.split("to");
		  fromDate = formatDate.parse(splitTime[0]);
		  toDate = formatDate.parse(splitTime[1]);
		  LocalDate temLocalDate = fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		  int workingDayInMonth= cal.calculateWorkingDay(temLocalDate.getMonthValue(), temLocalDate.getYear());
		  
		 if (temLocalDate.isBefore(dateDes) && (temLocalDate.isAfter(underLimitedDate)||temLocalDate.isEqual(underLimitedDate)) ) 
		   {
			  counWorkingDay=cal.countWorkingDay(fromDate, toDate);
			  float alo = (float)counWorkingDay/workingDayInMonth;
			   ManPower man = new ManPower();
			   man.setAllocationValue((float) Math.floor(alo * 100) / 100);
			   man.setFromDate(fromDate);
			   man.setToDate(toDate);
			   man.setUserId(groupUser.getUserId());
			   man.setGroupId(groupUser.getGroupId());
			   man.setManMonth((temLocalDate.getMonthValue()<10?"0"+temLocalDate.getMonthValue():temLocalDate.getMonthValue())+"-"+temLocalDate.getYear());
			   manPowerRepository.save(man);
		        } else continue;
		    }
		  }
		  catch(Exception e) {
			  e.printStackTrace();
		  }
	  }
	  
	}
	
	public void  insertManPower3rdMonth(LocalDate currentDate, List<HistoryUserGroupDTO> listHistoryUserGroup,int userId) {
		CalculateClass cal= new CalculateClass();
		SimpleDateFormat formatDate1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatDate2 = new SimpleDateFormat("MM/dd/yyyy");
		
	    LocalDate current = LocalDate.now();
	    LocalDate dateDes=current.plusMonths(4).withDayOfMonth(1);
	    
		LocalDate startDate = currentDate.plusMonths(3).withDayOfMonth(1);
		LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
		int workingDayInMonth= cal.calculateWorkingDay(startDate.getMonthValue(), startDate.getYear());
		int counWorkingDay=0;
		try {
	    LocalDate fromDate = null; 
	    LocalDate toDate = null;
	    List<String> devideTimeElements = null;
	    String[] splitTime = null;
	    Group group=null;
	    //Xóa các manpower tháng tương lai tháng 3 cũ nễu có
	    this.deleteManPowerMonth(userId, startDate.getMonthValue(), startDate.getYear());
	    //
		for(HistoryUserGroupDTO dto : listHistoryUserGroup) {
			
			fromDate = formatDate2.parse(dto.getStartDate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			if(dto.getEndDate()==null|| "".equals(dto.getEndDate())) toDate=dateDes.plusDays(-1);	
			else toDate = formatDate2.parse(dto.getEndDate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			
			if(toDate.isBefore(fromDate)) continue;
			
			if(fromDate.isAfter(endDate)||toDate.isBefore(startDate)) continue;
			else {
				group=groupRepository.findByName(dto.getDuName());
				devideTimeElements = cal.devideDateByMonth(cal.convertToDateViaInstant(fromDate),cal.convertToDateViaInstant(toDate), 0);
				  for(String time : devideTimeElements) {
					  splitTime= time.split("to");
					  Date from = formatDate1.parse(splitTime[0]);
					  Date to = formatDate1.parse(splitTime[1]);
					  LocalDate temLocalDate = from.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					 if (temLocalDate.getMonthValue()==startDate.getMonthValue()&&temLocalDate.getYear()==startDate.getYear()) 
					   {
						  counWorkingDay=cal.countWorkingDay(from, to);
						  float alo = (float)counWorkingDay/workingDayInMonth;
						   ManPower man = new ManPower();
						   man.setAllocationValue((float) Math.floor(alo * 100) / 100);
						   man.setFromDate(from);
						   man.setToDate(to);
						   man.setUserId(userId);
						   man.setGroupId(group.getGroupId());
						   man.setManMonth((temLocalDate.getMonthValue()<10?"0"+temLocalDate.getMonthValue():temLocalDate.getMonthValue())+"-"+temLocalDate.getYear());
						   manPowerRepository.save(man);
					        }
					    }
			}
		 
			
			//LocalDate temLocalDate
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateManPowerCustomDate(int userId, Date startDate, Date endDate) {
      CalculateClass cal = new CalculateClass();
  	  SimpleDateFormat formatDate1 = new SimpleDateFormat("yyyy-MM-dd");
	  List<Integer> listUsers = new ArrayList<Integer>();
	  LocalDate localStart = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	  LocalDate underLimitedDate = LocalDate.of(2019, 4, 1);
	  
	  //Set ngày bắt đầu của tháng của startDate
	  localStart=localStart.withDayOfMonth(1);
	  LocalDate localEnd=null;
	  // check UserId = 0 thì tìm tất cả user nếu != 0 tìm user đó 
	  if(userId ==0) listUsers = userRepository.getAllUserId();
	  else
		  listUsers.add(userId);
	
	  
	  // Nếu endDate = null thì  tính từ startDate đến 3 tháng sau
	  if(endDate==null)
		  localEnd=localStart.plusMonths(3);
	  else localEnd=endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusMonths(3);
	  
	  // Set ngày cuối tháng của endDate vào localEnd
	  localEnd=localEnd.withDayOfMonth(localEnd.lengthOfMonth());
	  
	  List<String> devideTimeElements = null;
	  String[] splitTime = null;
	  int counWorkingDay=0;
	  int workingDayInMonth=0;
	  try {
	  for(Integer us: listUsers) {
		  List<GroupUser> listGroupUser= groupUserRepository.findByUserId(us);
	  // Xóa manpower từ startMonth đến endMonth
		 LocalDate deleteMonth = localStart;
		 while(deleteMonth.isBefore(localEnd)) {
			 manPowerRepository.deleteManByMonthYear(us, deleteMonth.getMonthValue(), deleteMonth.getYear());
			 deleteMonth=deleteMonth.plusMonths(1);
		 }
	  // nếu user có listGroupUser mới tiếp tục
		  if(listGroupUser!=null&&listGroupUser.size()>0) {
			    LocalDate fromDate=null; 
			    LocalDate toDate = null; 
			    Group group=null;
			  for(GroupUser gu : listGroupUser) {
	 // parse Date trong mỗi GroupUser sang LocalDate để tiện so sánh
				  fromDate = gu.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				  if(gu.getEndDate()!=null)
					  toDate = gu.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				  else toDate = localEnd;
				  if(toDate.isBefore(fromDate)) continue;
	 // Nếu fromDate lớn hơn ngày cuối cùng so sánh hoặc toDate nhỏ hơn ngày bắt đầu thì tiếp tục 
				  if(fromDate.isAfter(localEnd)||toDate.isBefore(localStart)) continue;
				  else {
					  group=groupRepository.findById(gu.getGroupId());
					  if(group.isDevelopmentUnit()) {
	// Cắt timeline thành các đoạn trong tháng
						  
						devideTimeElements = cal.devideDateByMonth(gu.getStartDate(),cal.convertToDateViaInstant(toDate), 0);
						  for(String time : devideTimeElements) {
							  splitTime= time.split("to");
							  Date from = formatDate1.parse(splitTime[0]);
							  Date to = formatDate1.parse(splitTime[1]);
	// parse ngày bắt đầu của đoạn trong tháng thành LocalDate để tiện so sánh
							  LocalDate temLocalDate = from.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
							  workingDayInMonth=cal.calculateWorkingDay(temLocalDate.getMonthValue(), temLocalDate.getYear());
	// Nếu fromDate năm trong đoạn từ Ngày bắt đầy->Ngày kết thúc thì mới tính manpower
							 if ((temLocalDate.isAfter(localStart)||temLocalDate.isEqual(localStart))
									 && (temLocalDate.isBefore(localEnd)||temLocalDate.isEqual(localEnd)
									 &&(temLocalDate.isAfter(underLimitedDate))&&temLocalDate.isEqual(underLimitedDate))) 
							   {
								  counWorkingDay=cal.countWorkingDay(from, to);
								  float alo = (float)counWorkingDay/workingDayInMonth;
								   ManPower man = new ManPower();
								   man.setAllocationValue((float) Math.floor(alo * 100) / 100);
								   man.setFromDate(from);
								   man.setToDate(to);
								   man.setUserId(us);
								   man.setGroupId(group.getGroupId());
								   man.setManMonth((temLocalDate.getMonthValue()<10?"0"+temLocalDate.getMonthValue():temLocalDate.getMonthValue())+"-"+temLocalDate.getYear());
								   manPowerRepository.save(man);
							        }
							    }
				  }
			  }
		  }
	   }
	  }
	  }
	  catch (Exception e) {
		e.printStackTrace();
	}
		
	}

//	@Override
//	public List<UserManPowerDTO> getManPowerByUserIdOfAllDU(String time) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public ManPower update(ManPower mp) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public boolean getManPowerFromAms(ManPowerDTO[] manPowerDTOs, String manMonth) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean getManpowerOlderFromAms(ManpowerOlderDTO[] manpowerOlderDTOs) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public void deleteManpowerFromDateToDate(int userId, Date fromDate, Date toDate) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateManPowerByGroupUser(GroupUser groupUser, GroupUser oldGroupUser) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void insertManPower3rdMonth(LocalDate currenDate, List<HistoryUserGroupDTO> listHistoryUserGroup,
//			int userId) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateManPowerCustomDate(int userId, Date startDate, Date endDate) {
//		// TODO Auto-generated method stub
//		
//	}
}
