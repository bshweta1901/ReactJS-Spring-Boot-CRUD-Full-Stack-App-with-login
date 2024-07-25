package com.springboot.assignment.model_structure.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PredefinedDTO {

    private Long id;

    private String name;

    private String entityType;

    private String code;


}
