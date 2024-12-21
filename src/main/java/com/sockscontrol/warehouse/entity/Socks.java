package com.sockscontrol.warehouse.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Socks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "id")
    private Long id;
    @Schema
    String color;
    @Schema
    byte cottonPart;
    @Schema
    int count;




}
