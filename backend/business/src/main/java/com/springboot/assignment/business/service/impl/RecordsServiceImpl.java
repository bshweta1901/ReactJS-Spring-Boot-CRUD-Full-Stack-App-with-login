package com.springboot.assignment.business.service.impl;

import com.springboot.assignment.auth.exception.APIException;
import com.springboot.assignment.business.service.RecordsService;
import com.springboot.assignment.model_structure.entity.RecordMaster;
import com.springboot.assignment.utility.dao.GenericDao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service(value = "recordService")
@Transactional
@RequiredArgsConstructor
public class RecordsServiceImpl implements RecordsService {
    private final GenericDao genericDao;

    @Override
    public RecordMaster save(RecordMaster recordMaster) {
        recordMaster.setIsDelete("N");
        recordMaster = (RecordMaster) genericDao.save(recordMaster);
        return recordMaster;
    }

    @Override
    public void update(RecordMaster recordMaster) throws Exception {
        RecordMaster oldRecord = getById(recordMaster.getId());
        if (recordMaster.getName() != null && !recordMaster.getName().isEmpty() && !recordMaster.getName().equalsIgnoreCase(oldRecord.getName())) {
            oldRecord.setName(recordMaster.getName());

        }
        if (recordMaster.getDescription() != null && !recordMaster.getDescription().isEmpty() && !recordMaster.getDescription().equalsIgnoreCase(oldRecord.getDescription())) {
            oldRecord.setDescription(recordMaster.getDescription());
        }

        if (recordMaster.getCategory() != null && recordMaster.getCategory().getId() != null ) {
            oldRecord.setCategory(recordMaster.getCategory());
        }
        genericDao.update(oldRecord);

    }
    @Override
    public List<RecordMaster> getAll(RecordMaster recordMaster) {
        String query = " FROM RecordMaster r";
        query = queryCrieteria(query, recordMaster);
        query += " GROUP BY r.id Order by r.id Desc";
        return (List<RecordMaster>) genericDao.findCustomList(query);
    }

    private String queryCrieteria(String query, RecordMaster recordMaster) {
        query += " LEFT JOIN PredefinedMaster c on c.id=r.categoryId " +
                " WHERE r.isDelete='N' ";

        if (recordMaster.getCategory() != null && recordMaster.getCategory().getId()!=null) {
            query += " AND  c.id='" + recordMaster.getCategory().getId() + "'";
        }

        if (recordMaster.getSearchValue() != null && !recordMaster.getSearchValue().isEmpty()) {
            query += " and (" +
                    "  r.name LIKE '%" + recordMaster.getSearchValue() + "%' " +
                    " OR c.name LIKE '%" + recordMaster.getSearchValue() + "%' " +
                    ")  ";
        }


        return query;
    }

    @Override
    public Object getCount(RecordMaster recordMaster) {
        String query = " SELECT COUNT(DISTINCT(r.id)) FROM RecordMaster r";
        query = queryCrieteria(query, recordMaster);
        return genericDao.countCustomList(query);
    }

    @Override
    public RecordMaster getById(Long id) throws Exception {
        String query="from RecordMaster where id=" + id + " and isDelete='N' ";
        List<RecordMaster> recordMasters = genericDao.findCustomList( query);
        if (!recordMasters.isEmpty()) {
            return recordMasters.get(0);
        }
        throw new APIException(HttpStatus.BAD_REQUEST,"RecordMaster not found");
    }

    @Override
    public void changeStatus(Long id) {
        String query = "UPDATE RecordMaster SET isDeactivate = CASE WHEN isDeactivate = 'Y' THEN 'N' ELSE 'Y' END WHERE id = " + id;
        genericDao.customUpdate(query);

    }

    @Override
    public void delete(Long id) throws Exception {

        String query = "SELECT isDeactivate FROM RecordMaster WHERE id=" + id;
        String activeState = genericDao.findSingleColumnValue(query);

        if (activeState != null && !activeState.isEmpty()) {
            if (activeState.equals("N")) {
                throw new APIException(HttpStatus.BAD_REQUEST,"Can't delete Active RecordMaster\"");
            }
        }
        query = "UPDATE RecordMaster SET isDelete = 'Y', isDeactivate='Y' WHERE id = " + id;
        genericDao.customUpdate(query);

    }
}
