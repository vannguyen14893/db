/**
 * dashboard-phase2-backend- - com.cmc.dashboard.repository
 */
package com.cmc.dashboard.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cmc.dashboard.model.ManPower;

/**
 * @author: ngocdv
 * @Date: Mar 21, 2018
 */
@Repository
public interface ManPowerRepository extends JpaRepository<ManPower, Integer> {

	@Query("FROM ManPower m WHERE m.userId = :userId")
	public List<ManPower> getManPowersByUserId(@Param("userId") int userId);

	@Query(value = "SELECT * FROM man_power m WHERE m.man_power_id = :manPowerId", nativeQuery = true)
	public ManPower getManPowerById(@Param("manPowerId") int manPowerId);
	
	@Query(value = "SELECT * FROM man_power MP"
			+" WHERE DATE_FORMAT(MP.from_date,'%m-%Y') = :monthYear" 
			+" AND MP.user_id = :userId" 
			+" ORDER BY MP.from_date ASC", nativeQuery = true)
	public List<ManPower> getManPowersByMonthUser(@Param("monthYear") String monthYear,  @Param("userId") Integer userId);
	
	/**
	 * 
	 * TODO description
	 * 
	 * @param monthYear
	 * @return List<ManPower>
	 * @author: LXLinh
	 */
	@Query(value = "SELECT * FROM man_power MP  " 
			+" WHERE ( " 
			+" (STR_TO_DATE(:monthYear , '%m-%Y') > MP.from_date AND STR_TO_DATE(:monthYear , '%m-%Y')< MP.to_date ) OR " 
			+" DATE_FORMAT(MP.from_date,'%m-%Y') = :monthYear OR DATE_FORMAT(MP.to_date,'%m-%Y') = :monthYear " 
			+" OR MP.to_date IS NULL ) " 
			+" ORDER BY MP.user_id ASC, MP.from_date ASC", nativeQuery = true)
	public List<ManPower> getManPowerByMonth(@Param("monthYear") String monthYear);

	
	/**
	 * Get All data and order by user_id & from_date
	 * 
	 * @return  
	 * @author: HungNC
	 * @created: 2018-04-08
	 */
	@Query(value="SELECT * FROM man_power ORDER BY user_id, from_date;", nativeQuery = true)
	public List<ManPower> getAll();
	
  /**
  * get manpower by user
  * @param fromDate
  * @param toDate
  * @return List<ManPower> 
  * @author: NNDuy
  */
 @Query(value =  "SELECT    mp.user_id, mp.man_power_id, mp.allocation_value, DATE(mp.from_date) AS from_date, DATE(mp.to_date) AS to_date \r\n" + 
     "FROM      man_power mp \r\n" + 
     "WHERE     \r\n" +  
     "      !((mp.to_date IS NOT NULL AND mp.to_date < :fromDate) OR  mp.from_date > :toDate OR (mp.from_date > :toDate AND mp.to_date IS NULL) ) \r\n" + 
     "          AND mp.user_id IN (:userIds)\r\n" + 
     "ORDER BY  mp.user_id ASC, mp.from_date ASC", nativeQuery = true)
  public List<Object> getManPowerByUsers(@Param("fromDate") String fromDate,
       @Param("toDate") String toDate, @Param("userIds") List<Integer> userIds);

	
	/**
	 * Add to_date
	 * 
	 * @return int 
	 * @author: ntquy
	 * @created: 2018-04-16
	 */
	@Transactional
	@Modifying(clearAutomatically=true)
	@Query(value = "UPDATE man_power SET man_power.to_date = :toDate WHERE man_power.user_id = :userId AND man_power.to_date IS NULL", nativeQuery = true)
	public int updateToDate(@Param("userId") int userId, @Param("toDate") Date toDate);
	
	/**
	 * Delete man_power_id
	 * 
	 * @return int 
	 * @author: ntquy
	 * @created: 2018-04-16
	 */
	@Transactional
	@Modifying(clearAutomatically=true)
	@Query(value = "DELETE FROM man_power WHERE man_power.man_power_id = :id", nativeQuery = true)
	void deleteMan(@Param("id") int id);
	
	/**
	 * Get last row of userId
	 * 
	 * @return int 
	 * @author: ntquy
	 * @created: 2018-04-16
	 */
	@Query(value = "SELECT * FROM man_power WHERE man_power.user_id = :id ORDER BY man_power.man_power_id DESC LIMIT 1", nativeQuery = true)
	public ManPower getManLast(@Param("id") int id);
	
	@Query(value="select * from man_power where user_id =:userId and group_id =:groupId and month(from_date) =:month and year(from_date) =:year limit 1", nativeQuery = true)
	public ManPower findByUserGroupMonthYear(@Param("userId")int userId, @Param("groupId")int groupId,@Param("month")int month, @Param("year")int year);
	
	@Modifying
	@Transactional
	@Query("delete from ManPower gu where gu.fromDate>=:fromDate and gu.toDate <=:toDate and gu.userId=:userId")
	void deleteManByFromDateToDate(@Param("userId") int userId,@Param("fromDate") Date fromDate,@Param("toDate") Date toDate );
	
	@Modifying
	@Transactional
	@Query("delete from ManPower gu where month(gu.fromDate)=:month and year(gu.fromDate) =:year and gu.userId=:userId")
	void deleteManByMonthYear(@Param("userId") int userId,@Param("month") int month,@Param("year") int year );
	
	@Modifying
	@Transactional
	@Query("delete from ManPower gu where gu.fromDate>=:fromDate and gu.userId=:userId")
	void deleteManByFromDate(@Param("userId") int userId,@Param("fromDate") Date fromDate );
	
	@Query("select gu from ManPower gu where month(gu.fromDate)=:month and year(gu.fromDate) =:year and gu.userId=:userId")
	List<ManPower> findManByMonthYear(@Param("userId") int userId,@Param("month") int month,@Param("year") int year );
	
	ManPower findFirstByOrderByManPowerIdDesc();
}
