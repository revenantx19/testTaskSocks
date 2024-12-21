package com.sockscontrol.warehouse.service;

import com.sockscontrol.warehouse.customexceptions.InvalidInputException;
import com.sockscontrol.warehouse.customexceptions.SocksNotFoundException;
import com.sockscontrol.warehouse.entity.Socks;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.sockscontrol.warehouse.repository.SocksRepository;

@Service
@Slf4j
public class SocksService {

    private final SocksRepository socksRepository;

    public SocksService(SocksRepository socksRepository) {
        this.socksRepository = socksRepository;
    }

    public void regSocksIncome(Socks socks) {
        log.info("Вход в метод regSocksIncome класса SocksRepository. Начинаем проверку наличия данной строки в БД");
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
        log.info("Вход в метод regSocksOutcome класса SocksRepository. Начинаем проверку наличия данной строки в БД");
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
                                   String comparison,
                                   Integer number) throws InvalidInputException {
        log.info("Вход в метод getCountOfSocks класса SocksRepository. Начинаем проверку наличия данной строки в БД");
        if (socksRepository.socksExistsByColorAndCottonPart(color, cottonPart)) {
            if (comparison.equals(">")) {
                int countSocksByColor = socksRepository.findCountOfSocksMoreThan(color, cottonPart);
                log.info("Количество носков цвета {}, {} ", color, countSocksByColor);
                return countSocksByColor;
            } else if (comparison.equals("<")) {
                return null;
            } else if (comparison.equals("=")) {
                return null;
            }
        }
        throw new InvalidInputException(comparison);
    }


    public ResponseEntity<?> updateSocksData() {
        return null;
    }

    public Integer uploadFile() {
        return null;
    }
}
