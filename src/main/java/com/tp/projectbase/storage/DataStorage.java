package com.tp.projectbase.storage;

import com.tp.projectbase.entity.Data;

import java.util.List;

public interface DataStorage  {
    void save(Data data);
    List<Data> getDataTracking(String eventType, Long startTime, Long endTime);
}
