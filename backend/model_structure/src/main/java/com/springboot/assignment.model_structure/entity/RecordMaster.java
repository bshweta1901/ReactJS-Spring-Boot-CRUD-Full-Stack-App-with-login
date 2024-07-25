package com.springboot.assignment.model_structure.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.springboot.assignment.model_structure.entity.common.PredefinedMaster;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecordMaster  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    @ManyToOne(targetEntity = PredefinedMaster.class, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "categoryId")
    private PredefinedMaster category;

    @Column(name = "categoryId", insertable = false, updatable = false, nullable = true)
    private Long categoryId;
    private String isDelete;
    private String isDeactivate;

    @Transient
    private String searchValue;

}
