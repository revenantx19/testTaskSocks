package com.sockscontrol.warehouse.service;

import com.sockscontrol.warehouse.customexceptions.InvalidInputException;
import com.sockscontrol.warehouse.customexceptions.SocksNotFoundException;
import com.sockscontrol.warehouse.entity.Socks;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.sockscontrol.warehouse.repository.SocksRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class SocksService {

    private final SocksRepository socksRepository;

    public SocksService(SocksRepository socksRepository) {
        this.socksRepository = socksRepository;
    }

    public void regSocksIncome(Socks socks) {
        log.info("Вход в метод regSocksIncome класса SocksService. Начинаем проверку наличия данной строки в БД");
        if (socksRepository.socksExistsByColorAndCottonPart(socks.getColor(),
                socks.getCottonPart())) {
            socksRepository.increaseSocksByColorAndCottonPart(socks.getColor(),
                    socks.getCottonPart(),
                    socks.getCount());
            log.info("Данные успешно обновлены (добавлены носки)");
        } else {
            socksRepository.save(socks);
            log.info("Данные успешно добавлены");
        }
    }

    public void regSocksOutcome(Socks socks) {
        //нужна проверка существования такого количества носков
        log.info("Вход в метод regSocksOutcome класса SocksService. Начинаем проверку наличия данной строки в БД");
        if (socksRepository.socksExistsByColorAndCottonPart(socks.getColor(),
                socks.getCottonPart())) {
            socksRepository.reduceSocksByColorAndCottonPart(socks.getColor(),
                    socks.getCottonPart(),
                    socks.getCount());
            log.info("Данные успешно обновлены (уменьшено количество носков)");
        } else {
            throw new SocksNotFoundException(socks.getColor(), socks.getCottonPart());
        }
    }

    public Integer getCountOfSocks(String color,
                                   Integer cottonPart,
                                   String comparison) throws InvalidInputException {
        log.info("Вход в метод getCountOfSocks класса SocksService. Начинаем проверку наличия данной строки в БД");
        if (socksRepository.socksExistByColor(color)) {
            log.info("Строка с искомым цветом найдена, выполнение логики");
            if (comparison.equals(">")) {
                int countSocksByColor = socksRepository.findCountOfSocksMoreThan(color, cottonPart);
                log.info("Количество носков цвета {}, с процентным содержанием хлопка больше {}%, равно {} ", color, cottonPart, countSocksByColor);
                return countSocksByColor;
            } else if (comparison.equals("<")) {
                int countSocksByColor = socksRepository.findCountOfSocksLessThan(color, cottonPart);
                log.info("Количество носков цвета {}, с процентным содержанием хлопка меньше {}%, равно {} ", color, cottonPart, countSocksByColor);
                return countSocksByColor;
            } else if (comparison.equals("=") && (socksRepository.socksExistsByColorAndCottonPart(color, cottonPart))) {
                return socksRepository.findCountOfCocksByColorAndCottonPart(color, cottonPart);
            } else {
                throw new SocksNotFoundException(color, cottonPart);
            }
        }
        throw new SocksNotFoundException(color);
    }


    public void updateSocksData(Integer id,
                                String color,
                                Integer cottonPart,
                                Integer count) {
        log.info("Вход в метод updateSocksData класса SocksService. Начинаем проверку наличия данной строки в БД");
        if (socksRepository.existsBySocksId(id)) {
            socksRepository.updateSocksData(id, color, cottonPart, count);
            log.info("Данные успешно обновлены");
        } else {
            throw new SocksNotFoundException();
        }
    }

    public ResponseEntity<?> uploadDataFromExcelFile(MultipartFile excelFile) {

        try (Workbook workbook = new XSSFWorkbook(excelFile.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) { // Пропускаем заголовок
                    continue;
                }

                Cell colorCell = row.getCell(0);
                Cell cottonPartCell = row.getCell(1);
                Cell countCell = row.getCell(2);

                String color = colorCell.getStringCellValue();
                int cottonPart = (int) cottonPartCell.getNumericCellValue();
                int count = (int) countCell.getNumericCellValue();

                socksRepository.save(new Socks(color, cottonPart, count));
            }
            return ResponseEntity.ok("Данные успешно загружены в БД.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка чтения excel файла.");
        }

    }
}
