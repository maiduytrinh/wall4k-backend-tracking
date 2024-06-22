package com.tp.projectbase.service;

import com.tp.projectbase.entity.Data;

import java.util.List;

public interface DataService {
    void recordAction(Data data);
    void updateTrending();

    String getDataTrending(String country, int pageNumber, int sizeConfig);
    String getDataTopDown(String country, int pageNumber, int sizeConfig);
}
