package com.springboot.assignment.business.service.impl;

import com.springboot.assignment.auth.repository.UserRepository;
import com.springboot.assignment.business.service.PredefinedService;
import com.springboot.assignment.business.service.UserService;
import com.springboot.assignment.model_structure.entity.User;
import com.springboot.assignment.model_structure.entity.common.PredefinedMaster;
import com.springboot.assignment.utility.dao.GenericDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PredefinedServiceImpl implements PredefinedService {
    private  GenericDao genericDao;
    public PredefinedServiceImpl(GenericDao genericDao) {
        this.genericDao = genericDao;
    }

    @Override
    public List<PredefinedMaster> getList(PredefinedMaster predefinedMaster) {
        String query="FROM PredefinedMaster u WHERE isDelete='N' ";
        if(predefinedMaster.getEntityType()!=null && !predefinedMaster.getEntityType().isEmpty()){
            query+=" AND entityType='"+predefinedMaster.getEntityType()+"'";
        }

        // get content for page object
        List<PredefinedMaster> listOfPosts = genericDao.findCustomList(query);


        return listOfPosts;
    }
}
