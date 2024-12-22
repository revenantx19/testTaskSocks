package com.sockscontrol.warehouse.service;

import com.sockscontrol.warehouse.customexceptions.InvalidInputException;
import com.sockscontrol.warehouse.customexceptions.SocksNotFoundException;
import com.sockscontrol.warehouse.entity.Socks;
import com.sockscontrol.warehouse.repository.SocksRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class SocksServiceTest {

    @Mock
    private SocksRepository socksRepository;

    @InjectMocks
    private SocksService socksService;

    private Socks socks;

    @BeforeEach
    public void setUp() {
        socks = new Socks("red", 75, 100);
    }



    //tests regSocksIncome
    @Test
    public void testRegSocksIncomeWhenSocksExist() {
        when(socksRepository.socksExistsByColorAndCottonPart(socks.getColor(), socks.getCottonPart()))
                .thenReturn(true);

        socksService.regSocksIncome(socks);

        verify(socksRepository, times(1)).increaseSocksByColorAndCottonPart(socks.getColor(), socks.getCottonPart(), socks.getCount());
        verify(socksRepository, never()).save(socks);
    }


    @Test
    public void testRegSocksIncomeWhenSocksDoNotExist() {
        when(socksRepository.socksExistsByColorAndCottonPart(socks.getColor(), socks.getCottonPart()))
                .thenReturn(false);

        socksService.regSocksIncome(socks);

        verify(socksRepository, never()).increaseSocksByColorAndCottonPart(any(), any(), anyInt());
        verify(socksRepository, times(1)).save(socks);
    }




    //tests regSocksOutcome
    @Test
    public void testRegSocksOutcomeWhenSocksExist() {
        when(socksRepository.socksExistsByColorAndCottonPart(socks.getColor(), socks.getCottonPart()))
                .thenReturn(true);

        socksService.regSocksOutcome(socks);

        verify(socksRepository, times(1))
                .reduceSocksByColorAndCottonPart(socks.getColor(),
                        socks.getCottonPart(),
                        socks.getCount());
    }

    @Test
    public void testRegSocksOutcomeWhenSocksDoNotExist() {
        when(socksRepository.socksExistsByColorAndCottonPart(socks.getColor(), socks.getCottonPart()))
                .thenReturn(false);

        socksService.regSocksOutcome(socks);

        verify(socksRepository, never()).reduceSocksByColorAndCottonPart(any(), any(), anyInt());
        verify(socksRepository, never()).save(any(Socks.class)); // Убедимся, что добавление не произошло
    }





    //tests getCountOfSocks
    @Test
    public void testGetCountOfSocksGreaterThan() throws InvalidInputException {
        when(socksRepository.socksExistByColor(socks.getColor())).thenReturn(true);
        when(socksRepository.findCountOfSocksMoreThan(socks.getColor(), socks.getCottonPart())).thenReturn(10);

        int result = socksService.getCountOfSocks(socks.getColor(), socks.getCottonPart(), ">");

        assertEquals(10, result);
        verify(socksRepository, times(1)).findCountOfSocksMoreThan(socks.getColor(), socks.getCottonPart());
        verify(socksRepository, never()).socksExistsByColorAndCottonPart(any(), any());
    }

    @Test
    public void testGetCountOfSocksLessThan() throws InvalidInputException {
        when(socksRepository.socksExistByColor(socks.getColor())).thenReturn(true);
        when(socksRepository.findCountOfSocksLessThan(socks.getColor(), socks.getCottonPart())).thenReturn(5);

        int result = socksService.getCountOfSocks(socks.getColor(), socks.getCottonPart(), "<");

        assertEquals(5, result);
        verify(socksRepository, times(1)).findCountOfSocksLessThan(socks.getColor(), socks.getCottonPart());
    }

    @Test
    public void testGetCountOfSocksEqual() throws InvalidInputException {
        when(socksRepository.socksExistByColor(socks.getColor())).thenReturn(true);
        when(socksRepository.socksExistsByColorAndCottonPart(socks.getColor(), socks.getCottonPart())).thenReturn(true);
        when(socksRepository.findCountOfCocksByColorAndCottonPart(socks.getColor(), socks.getCottonPart())).thenReturn(3);

        int result = socksService.getCountOfSocks(socks.getColor(), socks.getCottonPart(), "=");

        assertEquals(3, result);
        verify(socksRepository, times(1)).findCountOfCocksByColorAndCottonPart(socks.getColor(), socks.getCottonPart());
    }

    @Test
    public void testGetCountOfSocksColorNotFound() {
        when(socksRepository.socksExistByColor(socks.getColor())).thenReturn(false);

        assertThrows(SocksNotFoundException.class, () -> {
            socksService.getCountOfSocks(socks.getColor(), socks.getCottonPart(), ">");
        });

        verify(socksRepository, never()).findCountOfSocksMoreThan(any(), anyInt());
    }

    @Test
    public void testGetCountOfSocksInvalidComparison() {
        when(socksRepository.socksExistByColor(socks.getColor())).thenReturn(true);

        assertThrows(SocksNotFoundException.class, () -> {
            socksService.getCountOfSocks(socks.getColor(), socks.getCottonPart(), "!=");
        });

        verify(socksRepository, never()).findCountOfSocksMoreThan(any(), anyInt());
    }

    @Test
    public void testUnexpectedException() {
        when(socksRepository.socksExistByColor(socks.getColor())).thenReturn(true);
        when(socksRepository.findCountOfSocksMoreThan(socks.getColor(), socks.getCottonPart())).thenThrow(new RuntimeException("Unexpected error"));

        assertThrows(InvalidInputException.class, () -> {
            socksService.getCountOfSocks(socks.getColor(), socks.getCottonPart(), ">");
        });
    }


    @Test
    void updateSocksData() {
    }

    @Test
    void uploadDataFromExcelFile() {
    }
}