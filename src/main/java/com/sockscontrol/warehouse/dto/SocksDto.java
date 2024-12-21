package com.sockscontrol.warehouse.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SocksDto {

    @Schema(description = "id")
    private Long id;
    @Schema
    String color;
    @Schema
    byte cottonPart;
    @Schema
    int count;

}
