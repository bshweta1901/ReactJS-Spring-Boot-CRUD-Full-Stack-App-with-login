package com.springboot.assignment.business.service;


import com.springboot.assignment.model_structure.entity.RecordMaster;

import java.util.List;

public interface RecordsService {
    RecordMaster save(RecordMaster recordMaster);

    void update(RecordMaster recordMaster) throws Exception;

    List<RecordMaster> getAll(RecordMaster recordMaster);

    Object getCount(RecordMaster recordMaster);

    RecordMaster getById(Long id) throws Exception;

    void changeStatus(Long id);

    void delete(Long id) throws Exception;
}
