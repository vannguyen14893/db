package com.cmc.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.cmc.dashboard.model.GroupUser;

public interface GroupUserRepository extends JpaRepository<GroupUser, Integer> {
	List<GroupUser> findByUserId(int userId);
}
