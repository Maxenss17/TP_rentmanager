package com.ensta.rentmanager;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.except.DaoException;
import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import java.time.LocalDate;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;


public class ClientServiceTest {
    private ClientDao clientDaoMock;
    private ReservationDao reservationDaoMock;

    private ClientService clientService;

    @BeforeEach
    public void setUp() {

        clientDaoMock = mock(ClientDao.class);
        reservationDaoMock = mock(ReservationDao.class);
        clientService = new ClientService(clientDaoMock, reservationDaoMock);
    }
    @Test
    public void testCreateClient() throws DaoException, ServiceException {

        int expectedClientId = 1;

        Client expectedClient = new Client();
        expectedClient.setNom("YANGASA");
        expectedClient.setPrenom("Maxence");
        expectedClient.setEmail("maxence@exemple.com");
        expectedClient.setNaissance(LocalDate.of(1999, 1, 1));

        when(clientDaoMock.create(expectedClient)).thenReturn(expectedClientId);

        long actualClientId = clientService.create(expectedClient);
        assertEquals(expectedClientId, actualClientId);
    }

    @Test
    void testFindByIdClient() throws DaoException, ServiceException {

        int clientId = 1;
        String expectedNom = "YANGASA";
        String expectedPrenom = "Maxence";
        String expectedEmail = "maxence@exemple.com";
        java.sql.Date expectedDate = java.sql.Date.valueOf(LocalDate.of(1999, 1, 1));

        Client expectedClient = new Client();
        expectedClient.setId(clientId);
        expectedClient.setNom(expectedNom);
        expectedClient.setPrenom(expectedPrenom);
        expectedClient.setEmail(expectedEmail);
        expectedClient.setNaissance(expectedDate.toLocalDate());

        when(clientDaoMock.findById(clientId)).thenReturn(expectedClient);

        Client actualClient = clientService.findById(clientId);
        assertEquals(expectedClient, actualClient);

    }
}

