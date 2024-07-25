package com.springboot.assignment.model_structure.payload;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.springboot.assignment.model_structure.entity.common.PredefinedMaster;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecordMasterDTO  {
    private Long id;

    private String name;
    private String description;
    private PredefinedMaster category;

    private Long categoryId;

}
