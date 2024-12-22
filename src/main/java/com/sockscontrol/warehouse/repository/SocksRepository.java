package com.sockscontrol.warehouse.repository;

import com.sockscontrol.warehouse.entity.Socks;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import javax.validation.constraints.Min;

public interface SocksRepository extends JpaRepository<Socks, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE socks SET count = count - :count WHERE color = :color AND cotton_part = :cottonPart AND count >= :count", nativeQuery = true)
    void reduceSocksByColorAndCottonPart(@NonNull String color,
                                         @NonNull @Min(0) int cottonPart,
                                         @NonNull @Min(1) int count);

    @Modifying
    @Transactional
    @Query(value = "UPDATE socks SET count = count + :count WHERE color = :color AND cotton_part = :cottonPart", nativeQuery = true)
    void increaseSocksByColorAndCottonPart(@NonNull String color,
                                         @NonNull @Min(0) int cottonPart,
                                         @NonNull @Min(1) int count);

    @Query(value = "SELECT EXISTS (SELECT 1 FROM socks WHERE color = :color AND cotton_part = :cottonPart)", nativeQuery = true)
    boolean socksExistsByColorAndCottonPart(@NonNull String color,
                        @NonNull @Min(0) int cottonPart);

    @Query(value = "SELECT EXISTS (SELECT 1 FROM socks WHERE color = :color)", nativeQuery = true)
    boolean socksExistByColor(String color);

    @Query(value = "SELECT SUM(count) FROM socks WHERE color = :color", nativeQuery = true)
    int findCountOfCocksByColor(String color);

    @Query(value = "SELECT SUM(count) FROM socks WHERE color = :color AND cotton_part > :cottonPart", nativeQuery = true)
    int findCountOfSocksMoreThan(String color, Integer cottonPart);

    @Query(value = "SELECT SUM(count) FROM socks WHERE color = :color AND cotton_part < :cottonPart", nativeQuery = true)
    int findCountOfSocksLessThan(String color, Integer cottonPart);

    @Query(value = "SELECT count FROM socks WHERE color = :color AND cotton_part = :cottonPart", nativeQuery = true)
    int findCountOfCocksByColorAndCottonPart(String color, Integer cottonPart);

    @Query(value = "SELECT EXISTS (SELECT 1 FROM socks WHERE id = :id)", nativeQuery = true)
    boolean existsBySocksId(Integer id);

    @Query(value = "SELECT 1 FROM socks WHERE id = :id", nativeQuery = true)
    Socks findSockById(Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE socks SET color = :color, cotton_part = :cottonPart, count = :count WHERE id = :id", nativeQuery = true)
    void updateSocksData(Integer id, String color, Integer cottonPart, Integer count);
}
