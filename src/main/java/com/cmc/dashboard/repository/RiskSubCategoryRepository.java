/**
 * dashboard-phase2-backend - com.cmc.dashboard.repository
 */
package com.cmc.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cmc.dashboard.model.RiskSubCategory;

/**
 * @author: nvangoc
 * @Date: 26 Apr 2018
 */
@Repository
public interface RiskSubCategoryRepository extends JpaRepository<RiskSubCategory, Integer> {
	/**
	 * 
	 * description get sub-category by categoryId
	 * @param riskCategoryId
	 * @return List<RiskSubCategory> 
	 * @author: nvangoc
	 */
	@Query(value = "from RiskSubCategory rsc where rsc.isDeleted = 0")
	public List<RiskSubCategory> findAll();
	
	@Query(value = "from RiskSubCategory rsc where rsc.riskCategoryId.riskCategoryId = :riskCategoryId")
	public List<RiskSubCategory> findSubCategory(@Param("riskCategoryId") int riskCategoryId);
}
