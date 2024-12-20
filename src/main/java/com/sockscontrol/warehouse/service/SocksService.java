package com.sockscontrol.warehouse.service;

import com.sockscontrol.warehouse.entity.Socks;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.sockscontrol.warehouse.repository.SocksRepository;

@Service
public class SocksService {

    private final SocksRepository socksRepository;

    public SocksService(SocksRepository socksRepository) {
        this.socksRepository = socksRepository;
    }

    public ResponseEntity<?> regSocksIncome(Socks socks) {
        return null;
    }

    public ResponseEntity<?> regSocksOutcome(Socks socks) {
        return null;
    }

    public Integer getCountOfSocks() {
        return null;
    }

    public ResponseEntity<?> updateSocksData() {
        return null;
    }

    public Integer uploadFile() {
        return null;
    }
}
