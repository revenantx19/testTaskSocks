package com.sockscontrol.warehouse.controller;

import com.sockscontrol.warehouse.entity.Socks;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.sockscontrol.warehouse.service.SocksService;

@RestController
public class SocksController {

    private final SocksService socksService;

    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }

    /**
     * Регистрация прихода носков:
     * POST /api/socks/income
     * Параметры: цвет носков, процентное содержание хлопка, количество.
     * Увеличивает количество носков на складе.
     */
    @PostMapping(path = "/api/socks/income")
    public ResponseEntity<?> regSocksIncome(@RequestBody Socks socks) {
        return socksService.regSocksIncome(socks);
    }
    /**
     * Регистрация отпуска носков:
     * POST /api/socks/outcome
     * Параметры: цвет носков, процентное содержание хлопка, количество.
     * Уменьшает количество носков на складе, если их хватает.
     */
    @PostMapping(path = "/api/socks/outcome")
    public ResponseEntity<?> regSocksOutcome(@RequestBody Socks socks) {
        return socksService.regSocksOutcome(socks);
    }

    /**
     * Получение общего количества носков с фильтрацией
     * Фильтры:
     * Цвет носков.
     * Оператор сравнения (moreThan, lessThan, equal).
     * Процент содержания хлопка.
     * Возвращает количество носков, соответствующих критериям.
     */
    @GetMapping(path = "/api/socks")
    public Integer getCountOfSocks(@RequestParam(required = false) String color,
                                   @RequestParam(required = false) Integer cottonPart,
                                   @RequestParam(required = false) Integer quantity) {
        return socksService.getCountOfSocks();
    }

    /**
     * Обновление данных носков:
     * PUT /api/socks/{id}.
     * Позволяет изменить параметры носков (цвет, процент хлопка, количество).
     */
    @PatchMapping(path = "/api/socks/{id}")
    public ResponseEntity<?> updateSocksData(@PathVariable("id") Integer id,
                                             @RequestParam(required = false) String color,
                                             @RequestParam(required = false) Integer cottonPart,
                                             @RequestParam(required = false) Integer count) {
        return socksService.updateSocksData();
    }

    /**
     * Загрузка партий носков из Excel или CSV (один формат на выбор) файла:
     * POST /api/socks/batch
     * Принимает Excel или CSV (один формат на выбор) файл с партиями носков, содержащими цвет, процентное содержание хлопка и количество.
     */
    @PostMapping(path = "/api/socks/batch")
    public Integer uploadFile(@RequestPart(required = false) MultipartFile excelFile) {
        return socksService.uploadFile();
    }


}
