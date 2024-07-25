package com.springboot.assignment.business.controllers;


import com.springboot.assignment.business.service.PredefinedService;
import com.springboot.assignment.model_structure.entity.User;
import com.springboot.assignment.model_structure.entity.common.PredefinedMaster;
import com.springboot.assignment.model_structure.payload.PredefinedDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/predefined")
@RequiredArgsConstructor
public class PredefinedController {

    private final PredefinedService predefinedService;
    private final ModelMapper modelMapper;


    @PostMapping("/list")
    public ResponseEntity<?> list(HttpServletRequest httpServletRequest, @RequestBody PredefinedMaster predefinedMaster) throws Exception {
        List<PredefinedMaster> serviceList = predefinedService.getList(predefinedMaster);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        List<PredefinedDTO> list =
                serviceList
                        .stream()
                        .map(predefineds -> modelMapper.map(predefineds, PredefinedDTO.class)).toList();

        return ResponseEntity.ok(list);
    }



}
