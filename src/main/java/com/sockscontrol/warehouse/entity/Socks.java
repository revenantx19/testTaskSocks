package com.sockscontrol.warehouse.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;

@Entity
@Data
public class Socks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NonNull
    @Schema(description = "Цвет носков", example = "red")
    String color;

    @NonNull
    @Min(0)
    @Schema(description = "% содержание хлопка", example = "20")
    int cottonPart;

    @NonNull
    @Min(1)
    @Schema(description = "Количество носков")
    int count;


    public Socks() {

    }
}
