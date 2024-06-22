package com.tp.projectbase.storage.impl;

import com.tp.projectbase.Utils;
import com.tp.projectbase.entity.Trending;
import com.tp.projectbase.logger.TPLogger;
import com.tp.projectbase.storage.TrendingStorage;
import org.slf4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class TrendingStorageImpl implements TrendingStorage {
    private static final Logger LOG = TPLogger.getLogger(TrendingStorageImpl.class);

    @Override
    public void save(List<Trending> trending) {
        StringBuilder QUERY_INSERT = new StringBuilder("INSERT INTO trending (country, contentId, contentType, downloadCount, clickCount, trending) VALUES ");
        trending.forEach(t -> {
            QUERY_INSERT.append(String.format("\n('%s', '%s', '%s', '%s', '%s', '%s'),", t.getCountry(), t.getContentId(), t.getContentType(), t.getDownloadCount(), t.getClickCount(), t.getTrending()));
        });
        // remove last ,
        QUERY_INSERT.deleteCharAt(QUERY_INSERT.length() - 1);
        String sql = QUERY_INSERT.toString();
        System.out.println("sql: " + sql);
        try {
            Utils.mysqlMainUtility.executeQuery(sql, Boolean.class);
        } catch (Exception e) {
            LOG.error("Fail to save trending: {}", e.getMessage());
        }
    }

    @Override
    public String getTrending(String country, Integer contentType, Integer offset, Integer limit) {
        String QUERY_GET_TRENDING = "SELECT contentId FROM trending WHERE country = '%s' AND contentType =%s ORDER BY trending DESC, createdDate DESC LIMIT %s,%s";
        String sql = String.format(QUERY_GET_TRENDING, country, contentType, offset, limit);
        try {
            List<Trending> trendings = Utils.mysqlMainUtility.executeQuery(sql, Trending.class);
            if (trendings!= null && !trendings.isEmpty()) {
                List<String> contentIds = trendings.stream().map(trending -> trending.getContentId().toString()).collect(Collectors.toList());
                return String.join(";", contentIds);
            }
        } catch (Exception e) {
            LOG.error("Fail to get trending: {}", e.getMessage());
        }
        return "";
    }

    @Override
    public String getTopDown(String country, Integer contentType, Integer offset, Integer limit) {
        String QUERY_GET_TOP_DOWN = "SELECT contentId FROM trending WHERE country = '%s' AND contentType =%s ORDER BY downloadCount DESC, createdDate DESC LIMIT %s,%s";
        String sql = String.format(QUERY_GET_TOP_DOWN, country, contentType, offset, limit);
        try {
            List<Trending> trendings = Utils.mysqlMainUtility.executeQuery(sql, Trending.class);
            if (trendings!= null && !trendings.isEmpty()) {
                List<String> contentIds = trendings.stream().map(trending -> trending.getContentId().toString()).collect(Collectors.toList());
                return String.join(";", contentIds);
            }
        } catch (Exception e) {
            LOG.error("Fail to get trending: {}", e.getMessage());
        }
        return "";
    }

}
