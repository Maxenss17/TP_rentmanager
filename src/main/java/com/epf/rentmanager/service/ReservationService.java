package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.except.DaoException;
import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.model.Reservation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
//  public static ReservationService instance;

@Autowired
    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

//    public static ReservationService getInstance() {
//        if (instance == null) {
//            instance = new ReservationService();
//        }
//        return instance;
//    }

    public int create(Reservation reservation) throws ServiceException {
        try {
            return reservationDao.create(reservation);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Impossible de créer une nouvelle réservation." + e.getMessage());
        }
    }

    public int delete(Reservation reservation) throws ServiceException {
        try {
             return reservationDao.delete(reservation);
        } catch (DaoException e) {
            throw new ServiceException("Impossible de supprimer la réservation de la base de données.");
        }
    }

    public List<Reservation> findResaByClientId(int clientId) throws ServiceException {
        try {
            return reservationDao.findResaByClientId(clientId);
        } catch (DaoException e) {
            throw new ServiceException("Aucune réservation n'a a été trouvée pour le client donné.");
        }
    }

    public List<Reservation> findResaByVehicleId(int vehicleId) throws ServiceException {
        try {
            return reservationDao.findResaByVehicleId(vehicleId);
        } catch (DaoException e) {
            throw new ServiceException("Aucune réservation n'a a été trouvée pour le véhicule donné.");
        }
    }

    public List<Reservation> findAll() throws ServiceException {
        try {
            return reservationDao.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Aucune réservation trouvée.");
        }
    }
}
