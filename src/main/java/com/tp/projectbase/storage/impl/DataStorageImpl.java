package com.tp.projectbase.storage.impl;

import com.tp.projectbase.Utils;
import com.tp.projectbase.entity.Data;
import com.tp.projectbase.logger.TPLogger;
import com.tp.projectbase.rdbms.PageImpl;
import com.tp.projectbase.rdbms.api.Pageable;
import com.tp.projectbase.storage.DataStorage;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class DataStorageImpl implements DataStorage {
	private static final Logger LOG = TPLogger.getLogger(DataStorageImpl.class);

    public static Pageable<Data> getPageable(Pageable<Data> pageable) {
		final Pageable<Data> result = new PageImpl<>(pageable.getOffset(), pageable.getLimit());
		result.setList(new ArrayList<>());
		try {
			List<Data> listData = Utils.mysqlMainUtility.executeQuery(String.format("SELECT * FROM Tracking %s",""), Data.class);
			if (listData == null || listData.isEmpty()) {
				return result;
			}
			LOG.info("Data: {}", listData);
			result.setList(listData);
			return result;
		}catch (Exception e){
			LOG.error("Fail to pageable collections: {}", e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void save(Data data){
        String QUERY_INSERT = "INSERT INTO Tracking (deviceId, eventType, contentId, contentType, country, createdDate) VALUES ('%s', '%s', '%s', '%s', '%s', '%s')";
        String sql = String.format(QUERY_INSERT, data.getDeviceId(), data.getEventType(), data.getContentId(), data.getContentType(), data.getCountry(), data.getCreatedDate());
		try {
			Utils.mysqlMainUtility.executeQuery(sql, Boolean.class);
		} catch (Exception e) {
			LOG.error("Fail to save data: {}", e.getMessage());
		}
	}

	@Override
	public List<Data> getDataTracking(String eventType, Long startTime, Long endTime) {
		String QUERY_GET_TRACKING = "SELECT * FROM Tracking WHERE eventType = '%s' AND (createdDate >= %s AND createdDate <= %s) GROUP BY deviceId,contentId";
		String sql = String.format(QUERY_GET_TRACKING, eventType, startTime, endTime);
		try {
			return Utils.mysqlMainUtility.executeQuery(sql, Data.class);
		} catch (Exception e) {
			LOG.error("Fail to get data tracking: {}", e.getMessage());
		}
        return null;
	}
}
