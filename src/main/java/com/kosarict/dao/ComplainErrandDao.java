package com.kosarict.dao;

import com.kosarict.entity.ComplainErrand;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Sadegh-Pc on 12/6/2016.
 */
public interface ComplainErrandDao {
    List<ComplainErrand> getAllErrandList();

    List<ComplainErrand> getComplainErrandListByComplainId(long complainId);

    long saveComplainErrand(ComplainErrand complainErrandModel);

    ComplainErrand findComplainErrandById(long complainErrandId);

    boolean deleteComplainErrand(long complainErrandId);
}
