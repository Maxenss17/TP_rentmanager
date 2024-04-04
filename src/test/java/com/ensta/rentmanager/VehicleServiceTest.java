package com.ensta.rentmanager;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.except.DaoException;
import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;

public class VehicleServiceTest {

    private VehicleDao vehicleDaoMock;
    private VehicleService vehicleService;
    private ReservationDao reservationDaoMock;


    @BeforeEach
    public void setUp() {

        vehicleDaoMock = mock(VehicleDao.class);
        reservationDaoMock = mock(ReservationDao.class);
        vehicleService = new VehicleService(vehicleDaoMock, reservationDaoMock);
    }

    @Test
    void testFindAll() throws DaoException, ServiceException {

        List<Vehicle> expectedVehicles = new ArrayList<>();

        Vehicle vehicle1 = new Vehicle();
        vehicle1.setId(1);
        vehicle1.setConstructeur("Peugeot");
        vehicle1.setModele("206");
        vehicle1.setNb_places(5);
        expectedVehicles.add(vehicle1);

        Vehicle vehicle2 = new Vehicle();
        vehicle1.setId(2);
        vehicle1.setConstructeur("Renault");
        vehicle1.setModele("Clio");
        vehicle1.setNb_places(5);
        expectedVehicles.add(vehicle2);

        when(vehicleDaoMock.findAll()).thenReturn(expectedVehicles);
        List<Vehicle> actualVehicles = vehicleService.findAll();
        assertEquals(expectedVehicles, actualVehicles);

    }
}