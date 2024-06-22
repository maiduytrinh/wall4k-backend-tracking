package com.tp.projectbase.service.impl;

import javax.inject.Inject;

import com.tp.projectbase.Utils;
import com.tp.projectbase.entity.Action;
import com.tp.projectbase.entity.Data;
import com.tp.projectbase.entity.Trending;
import com.tp.projectbase.index.service.DataIndexService;
import com.tp.projectbase.service.DataService;
import com.tp.projectbase.storage.DataStorage;
import com.tp.projectbase.storage.TrendingStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataServiceImpl implements DataService {

    protected static final Logger LOG = LoggerFactory.getLogger(DataServiceImpl.class);
	@Inject
	private DataStorage dataStorage;

    @Inject
    private TrendingStorage trendingStorage;


	@Override
	public void recordAction(Data data) {
	    dataStorage.save(data);
	}

	@Override
	public void updateTrending() {
        try {
            long currentTime = System.currentTimeMillis();
            // time 2 weeks ago
            long startTime = currentTime - 14 * 24 * 60 * 60 * 1000;
            // get data tracking
            List<Data> dataDown = dataStorage.getDataTracking("down", startTime, currentTime);
            List<Data> dataClick = dataStorage.getDataTracking("click", startTime, currentTime);

            // Build content treding
            Map<Long, Action> actionMap = mergeData(dataDown, dataClick);

            // truncate table trending
            List<Trending> trendingList = new ArrayList<>();
            actionMap.forEach((key, value) -> {
                if (value.getDown() > 0 && value.getClick() > 0) {
                    Trending trending = new Trending();
                    trending.setCountry("VN");
                    trending.setContentId(key);
                    trending.setContentType(0);
                    trending.setDownloadCount(value.getDown());
                    trending.setClickCount(value.getClick());
                    trending.setTrending(value.getTrending());
                    trendingList.add(trending);
                }
            });
            if (!trendingList.isEmpty()) {
                Utils.mysqlMainUtility.executeQuery("TRUNCATE TABLE trending", Boolean.class);
                trendingStorage.save(trendingList);
            }
        } catch (Exception e) {
            LOG.error("Fail to update trending: {}", e.getMessage());
        }
	}

    public Map<Long, Action> mergeData(List<Data> dataDown, List<Data> dataClick) {
        Map<Long, Action> actionMap = new HashMap<Long, Action>();

        // loop dataDown add down to actionMap
        if (dataDown != null &&!dataDown.isEmpty()) {
            dataDown.forEach(data -> {
            });
            for (Data data : dataDown) {
                long id = data.getContentId();
                Action action = actionMap.get(id);
                if (action == null) {
                    action = new Action();
                    actionMap.put(id, action);
                }
                action.setDown(action.getDown() + 1);
            }
        }

        // loop dataClick add click to actionMap
        if (dataClick!= null &&!dataClick.isEmpty()) {
            dataClick.forEach(data -> {
            });
            for (Data data : dataClick) {
                long id = data.getContentId();
                Action action = actionMap.get(id);
                if (action == null) {
                    action = new Action();
                    actionMap.put(id, action);
                }
                action.setClick(action.getClick() + 1);
            }
        }

        return actionMap;
    }

    @Override
    public String getDataTrending(String country, int pageNumber, int sizeConfig) {
        return trendingStorage.getTrending(country, 0, (pageNumber - 1) * sizeConfig, sizeConfig);
    }

    @Override
    public String getDataTopDown(String country, int pageNumber, int sizeConfig) {
        return trendingStorage.getTopDown(country, 0, (pageNumber - 1) * sizeConfig, sizeConfig);
    }
}
