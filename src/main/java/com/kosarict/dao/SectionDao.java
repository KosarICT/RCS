package com.kosarict.dao;

import com.kosarict.entity.Section;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Sadegh-Pc on 12/6/2016.
 */
public interface SectionDao {
    List<Section> getAllSectionList();

    int saveSection(Section sectionModel);

    Section findSectionById(int sectionId);

    boolean deleteSection(int sectionId);

    List<Section> getSections(int hospitalId);
}
