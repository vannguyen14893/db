/**
 * dashboard-phase2-backend - com.cmc.dashboard.repository
 */
package com.cmc.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmc.dashboard.model.RiskStatus;

/**
 * @author: nvangoc
 * @Date: 26 Apr 2018
 */
@Repository
public interface RiskStatusRepository extends JpaRepository<RiskStatus, Integer>{

}
