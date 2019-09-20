package com.cmc.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cmc.dashboard.model.GroupManager;

public interface GroupManagerRepository extends JpaRepository<GroupManager, Integer> {
  List<GroupManager> findByGroup(int groupId);
  @Query(value = "select user_id from group_manager where group_id=:groupId",nativeQuery = true)
  List<Integer> listUserIdByGroup(@Param("groupId") int groupId);
  
  @Modifying
  @Transactional
  @Query(value="delete from group_manager gm where gm.user_id in ?1",nativeQuery = true)
  void deleteGroupManager(List<Integer> ids);
}
