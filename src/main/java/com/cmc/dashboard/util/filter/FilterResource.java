/**
 * dashboard-phase2-backend- - com.cmc.dashboard.util.filter
 */
package com.cmc.dashboard.util.filter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import com.cmc.dashboard.util.Constants;
import com.cmc.dashboard.util.CustomValueUtil;
import com.cmc.dashboard.util.MethodUtil;

/**
 * @author: NNDuy
 * @Date: May 11, 2018
 */
public class FilterResource {
  private String fromDate;
  private String toDate;
  private String userNameSearch;
  private List<Integer> userIds;
  private List<String> duNames;
  private List<Integer> projectIds;

  /**
   * Constructure
   */
  public FilterResource() {
    super();
    userIds =new ArrayList<>();
    duNames = new ArrayList<>();
    projectIds = new ArrayList<>();
  }

  public List<Integer> getUserIds() {
    return userIds;
  }

  public void setUserIds(List<Integer> userIds) {
    this.userIds = userIds;
  }

  public List<Integer> getProjectIds() {
    return projectIds;
  }

  public void setProjectIds(List<Integer> projectIds) {
    this.projectIds = projectIds;
  }

  public String getFromDate() {
    return fromDate.trim();
  }

  public void setFromDate(String fromDate) {
    this.fromDate = fromDate;
  }

  public String getToDate() {
    return toDate.trim();
  }

  public void setToDate(String toDate) {
    this.toDate = toDate;
  }

  public List<String> getDuNames() {
    return duNames;
  }

  public void setDuNames(List<String> duNames) {
    this.duNames = duNames;
  }

  public String getUserNameSearch() {
    if (StringUtils.isEmpty(userNameSearch)) {
      return null;
    }
    return MethodUtil.formatValueKeywordSearch(userNameSearch).toLowerCase();
  }

  public void setUserNameSearch(String userNameSearch) {
    this.userNameSearch = userNameSearch;
  }

  /**
   * Validate object filter resource
   * 
   * @return ResponseEntity<Object>
   * @author: NNDuy
   */
  public ResponseEntity<Object> validate() {
    ResponseEntity<Object> respone;

    // validate FromDate and ToDate
    respone = validateFromDateAndToDate();
    if (null != respone) {
      return respone;
    }

    // validate userIds
    respone = validateListId(userIds);
    if (null != respone) {
      return respone;
    }

    // validate duNames
    respone = validateListString(duNames);
    if (null != respone) {
      return respone;
    }

    // validate projectIds
    respone = validateListId(projectIds);
    if (null != respone) {
      return respone;
    }

    return null;
  }

  /**
   * check list id
   * 
   * @param ids
   * @return ResponseEntity<Object>
   * @author: NNDuy
   */
  private ResponseEntity<Object> validateListId(List<Integer> ids) {
    // id bằng empty thì không check
    if (MethodUtil.checkList(ids)) {
      return null;
    }

    for (Integer id : ids) {
      if (id <= 0)
        return new ResponseEntity<>(Constants.HTTP_STATUS_MSG.LIST_ID_INVALID,
            HttpStatus.BAD_REQUEST);
    }

    return null;
  }

  /**
   * check list String
   * 
   * @param names
   * @return ResponseEntity<Object>
   * @author: NNDuy
   */
  private ResponseEntity<Object> validateListString(List<String> names) {
    // name bằng empty thì không check
    if (MethodUtil.checkList(names)) {
      return null;
    }

    for (String name : names) {
      if (StringUtils.isEmpty(name))
        return new ResponseEntity<>(Constants.HTTP_STATUS_MSG.LIST_NAME_INVALID,
            HttpStatus.BAD_REQUEST);
    }

    return null;
  }

  /**
   * validate FromDate and ToDate
   * 
   * @return ResponseEntity<Object>
   * @author: NNDuy
   */
  private ResponseEntity<Object> validateFromDateAndToDate() {
    // Validate date format
    if (!MethodUtil.isValidDate(fromDate) || !MethodUtil.isValidDate(toDate)) {
      return new ResponseEntity<>(Constants.HTTP_STATUS_MSG.DATE_INVALID, HttpStatus.BAD_REQUEST);
    }

    final LocalDate fromDateConvert = MethodUtil.converStringToLocalDate(fromDate);
    final LocalDate toDateConvert = MethodUtil.converStringToLocalDate(toDate);

    // Validate fromMonth > 2017-04-01
    if (fromDateConvert.isBefore(CustomValueUtil.MIN_DATE)) {
      return new ResponseEntity<>(Constants.HTTP_STATUS_MSG.FROM_DATE_INVALID,
          HttpStatus.BAD_REQUEST);
    }

    // Validate fromDate < toDate
    if (fromDateConvert.compareTo(toDateConvert) > 0) {
      return new ResponseEntity<>(Constants.HTTP_STATUS_MSG.FROM_TO_DATE_INVALID,
          HttpStatus.BAD_REQUEST);
    }
    // validate success
    return null;
  }
}
