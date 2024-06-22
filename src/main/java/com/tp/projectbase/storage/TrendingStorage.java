package com.tp.projectbase.storage;

import com.tp.projectbase.entity.Data;
import com.tp.projectbase.entity.Trending;

import java.util.List;

public interface TrendingStorage {
    void save(List<Trending> trending);
    String getTrending(String country, Integer contentType, Integer offset, Integer limit);
    String getTopDown(String country, Integer contentType, Integer offset, Integer limit);
}
