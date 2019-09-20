package com.cmc.dashboard.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmc.dashboard.dto.HistoryDTO;
import com.cmc.dashboard.dto.UserInfoDTO;
import com.cmc.dashboard.model.HistoryData;
import com.cmc.dashboard.model.Issue;
import com.cmc.dashboard.model.Risk;
import com.cmc.dashboard.model.User;
import com.cmc.dashboard.repository.HistoryDataRepository;
import com.cmc.dashboard.repository.UserRepository;
/**
 * 
 * @author: LXLinh
 * @Date: Jul 6, 2018
 */
@Service
public class HistoryDataServiceImpl implements HistoryDataService {

	@Autowired
	HistoryDataRepository historyDataRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<HistoryDTO> getHistoryByIssueId(int issueId) {
		List<HistoryDTO> historyDTOs = new ArrayList<>();
		Optional<List<HistoryData>> historyDatas = Optional.ofNullable(
				historyDataRepository.findByTableNameAndRowId(Issue.class.getSimpleName(), Long.valueOf(issueId)));
		if (historyDatas.isPresent())
			historyDTOs = this.getHistoryDatas(historyDatas.get());
		return historyDTOs;
	}

	private List<HistoryDTO> getHistoryDatas(List<HistoryData> mapHistoryData) {
		List<HistoryDTO> historyDTOs = new ArrayList<>();
		List<UserInfoDTO> users = this.getUsers(mapHistoryData);
		this.groupHistoryByUserAndUpdateOn(mapHistoryData).entrySet().forEach(history -> {
			HistoryDTO historyDTO = new HistoryDTO();
			historyDTO.setUpdateOn(history.getKey());
			if (!history.getValue().isEmpty()) {
				historyDTO.setHistoryDatas(history.getValue());
				Optional<UserInfoDTO> user = users.stream()
						.filter(u -> u.getUserName().equals(history.getValue().get(0).getUserName())).findFirst();
				if (user.isPresent())
					historyDTO.setUserInfoDTO(user.get());
			}
			historyDTOs.add(historyDTO);
		});
		return historyDTOs.stream().sorted(Comparator.comparing(HistoryDTO::getUpdateOn).reversed())
				.collect(Collectors.toList());
	}

	private Map<Date, List<HistoryData>> groupHistoryByUserAndUpdateOn(List<HistoryData> historyDatas) {
		return historyDatas.stream().sorted(Comparator.comparing(HistoryData::getUpdatedOn))
				.collect(Collectors.groupingBy(HistoryData::getUpdatedOn));
	}

	private List<UserInfoDTO> getUsers(List<HistoryData> historyDatas) {
		List<UserInfoDTO> userDTOs = new ArrayList<>();
		Optional<List<String>> prHistoryDatas = Optional.ofNullable(historyDatas.stream().map(HistoryData::getUserName)
				.filter(Objects::nonNull).distinct().collect(Collectors.toList()));
		Optional<List<User>> users = Optional.empty();

		if (prHistoryDatas.isPresent()) {
			users = Optional.ofNullable(userRepository.findByUserNameIn(prHistoryDatas.get()));
		}
		if (users.isPresent()) {
			users.get().forEach(user -> userDTOs.add(new UserInfoDTO(user)));
		}
		return userDTOs;

	}

	@Override
	public List<HistoryDTO> getHistoryByRiskId(int riskId) {
		List<HistoryDTO> historyDTOs = new ArrayList<>();
		Optional<List<HistoryData>> historyDatas = Optional.ofNullable(
				historyDataRepository.findByTableNameAndRowId(Risk.class.getSimpleName(), Long.valueOf(riskId)));
		if (historyDatas.isPresent())
			historyDTOs = this.getHistoryDatas(historyDatas.get());
		return historyDTOs;
	}
}
