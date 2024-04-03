package com.ensta.rentmanager;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.except.DaoException;
import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ReservationService;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;

public class ReservationServiceTest {

    private ReservationDao reservationDaoMock;
    private ReservationService reservationService;

    @BeforeEach
    public void setUp() {

            reservationDaoMock = mock(ReservationDao.class);
            reservationService = new ReservationService(reservationDaoMock);
        }

    @Test
    void testDeleteReservation() throws DaoException, ServiceException {

        Reservation reservationToDelete = new Reservation();
        reservationToDelete.setId(1);
        int expectedDeletedReservationId = reservationToDelete.getId();

        when(reservationDaoMock.delete(reservationToDelete)).thenReturn(expectedDeletedReservationId);

        int actualDeletedReservationId = reservationService.delete(reservationToDelete);
        verify(reservationDaoMock).delete(reservationToDelete);
        assertEquals(expectedDeletedReservationId, actualDeletedReservationId);
    }
}

