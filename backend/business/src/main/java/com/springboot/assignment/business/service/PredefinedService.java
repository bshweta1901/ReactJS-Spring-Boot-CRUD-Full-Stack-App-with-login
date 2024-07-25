package com.springboot.assignment.business.service;

import com.springboot.assignment.model_structure.entity.User;
import com.springboot.assignment.model_structure.entity.common.PredefinedMaster;

import java.util.List;

public interface PredefinedService {

    List<PredefinedMaster> getList(PredefinedMaster predefinedMaster);
}
