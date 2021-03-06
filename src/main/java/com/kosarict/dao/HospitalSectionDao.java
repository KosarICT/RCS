package com.kosarict.dao;

import com.kosarict.entity.HospitalSection;
import com.kosarict.entity.Section;

import java.util.List;

/**
 * Created by Sadegh-Pc on 12/6/2016.
 */
public interface HospitalSectionDao {

    List<HospitalSection> getHospitalSectionsListByHospitalId(int hospitalId);

    List<Section> getSectionList(int hospitalId);

    HospitalSection findHospitalSectionById(int hospitalSectionId);

    List<HospitalSection> getAllHospitalSection();

    boolean deleteHospitalSectionBySectionId(int hospitalSectionId);


    HospitalSection findHospitalSectionBySectionId(int hospitalSectionId);

    int saveHospitalSection(HospitalSection hospitalSection);
}
