package com.cmc.dashboard.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import com.cmc.dashboard.model.HistoryData;
public class HistoryDataUtil {

	public static final String CREATE = "CREATE";
	public static final String UPDATE = "UPDATE";
	public static final String DELETE = "DELETE";
	public static final String ASSIGN = "ASSIGN";
	public static <V> List<HistoryData> getChangeHistorys(String tableName, Long id, Integer userId, String userName,
			String action, V objectOld, V objectNew) {
		List<HistoryData> historyDatas = new ArrayList<>();
		Class<?> classObjectNew = objectNew.getClass();
		Field[] fields = classObjectNew.getDeclaredFields();
		int i = 1;
		while (i < fields.length) {
			fields[i].setAccessible(true);
			HistoryData historyData = new HistoryData();
			historyData.setTableName(tableName);
			historyData.setRowId(id);
			historyData.setFieldName(fields[i].getName());
			historyData.setUserId(userId);
			historyData.setUserName(userName);
			try {
				if (HistoryDataUtil.CREATE.equals(action)) {
					historyData.setValueNew(fields[i].get(objectNew).toString());
					historyData.setAction(action);
					historyDatas.add(historyData);
				}
					if (objectOld != null && !fields[i].get(objectOld).equals(fields[i].get(objectNew))) {
						historyData.setValueNew(fields[i].get(objectNew).toString());
						historyData.setValueOld(fields[i].get(objectOld).toString());
						historyData.setAction(action);
						historyDatas.add(historyData);
					}
			} catch (IllegalAccessException e) {
			}
			i++;
		}
		return historyDatas;
	}

	private HistoryDataUtil() {
		throw new IllegalStateException(HistoryDataUtil.class.getName());
	}

}
