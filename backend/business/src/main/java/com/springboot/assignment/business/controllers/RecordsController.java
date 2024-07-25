package com.springboot.assignment.business.controllers;


import com.springboot.assignment.business.service.RecordsService;
import com.springboot.assignment.business.service.UserService;
import com.springboot.assignment.model_structure.entity.RecordMaster;
import com.springboot.assignment.model_structure.payload.RecordMasterDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/records")
@RequiredArgsConstructor
public class RecordsController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final RecordsService recordsService;

    @PostMapping("/save")
    public ResponseEntity<?> save(HttpServletRequest httpServletRequest, @RequestBody RecordMasterDTO recordMasterDTO) throws Exception {

        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        RecordMaster recordMaster = modelMapper.map(recordMasterDTO, RecordMaster.class);
        recordMaster = recordsService.save(recordMaster);
        Map<String, Long> data = new HashMap<>();
        data.put("id", recordMaster.getId());
        return ResponseEntity.ok(data);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(HttpServletRequest httpServletRequest, @RequestBody RecordMasterDTO recordMasterDTO) throws Exception {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        RecordMaster recordMaster = modelMapper.map(recordMasterDTO, RecordMaster.class);
        recordsService.update(recordMaster);
        return ResponseEntity.ok(Map.of("message", "update Sucessfully"));
    }


    @PostMapping("/list")
    public ResponseEntity<?> list( @RequestBody RecordMaster recordMaster) throws Exception {
        List<RecordMaster> recordMasters = recordsService.getAll(recordMaster);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        List<RecordMasterDTO> list =
                recordMasters
                        .stream()
                        .map(recordMasterDTO -> modelMapper.map(recordMasterDTO, RecordMasterDTO.class)).toList();

        return ResponseEntity.ok(list);
    }

    @PostMapping("/count")
    public ResponseEntity<?> getCount(HttpServletRequest httpServletRequest, @RequestBody RecordMaster recordMaster) throws Exception {
        return ResponseEntity.ok(recordsService.getCount(recordMaster));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) throws Exception {
        RecordMaster recordMaster = recordsService.getById(id);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        RecordMasterDTO recordMasterDTO = modelMapper.map(recordMaster, RecordMasterDTO.class);

        return ResponseEntity.ok(recordMasterDTO);
    }

    @PatchMapping("/change-state/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") Long id) throws Exception {
        recordsService.changeStatus(id);
        return ResponseEntity.ok(Map.of("message", "Status Change Successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws Exception {
        recordsService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Delete User Successfully"));
    }

}
