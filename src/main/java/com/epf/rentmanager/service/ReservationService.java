package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.except.DaoException;
import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ClientDao clientDao;
    private final VehicleDao vehicleDao;

    @Autowired
    public ReservationService(ReservationDao reservationDao, ClientDao clientDao, VehicleDao vehicleDao) {
        this.reservationDao = reservationDao;
        this.clientDao = clientDao;
        this.vehicleDao = vehicleDao;
    }

    public int create(Reservation reservation) throws ServiceException {

        try {
            return reservationDao.create(reservation);
        } catch (DaoException e) {
            throw new ServiceException("Impossible de créer une nouvelle réservation.");
        }
    }

    public boolean isVehicleAlreadyReserved(int reservationVehicleId, LocalDate dateDebut, LocalDate dateFin) throws ServiceException {

        try {
            List<Reservation> allVehicleReservations = reservationDao.findResaByVehicleId(reservationVehicleId);
            for (Reservation reservation : allVehicleReservations) {
                if (!(dateFin.isBefore(reservation.getDebut()) || dateDebut.isAfter(reservation.getFin())) ||
                        dateDebut.equals(reservation.getFin()) || dateFin.equals(reservation.getDebut())) {
                    return true;
                }
            }
            return false;
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la vérification de la disponibilité du véhicule.");
        }
    }

    //validateReservationTotalExceedsThirtyDays

    public boolean validateReservationTotalExceedsThirtyDays(List<Reservation> reservations, LocalDate debut, LocalDate fin) throws ServiceException {

        long reservedDays = debut.datesUntil(fin.plusDays(1))
                .filter(date -> reservations.stream().anyMatch(reservation -> reservation.getDebut().compareTo(date) <= 0 && reservation.getFin().compareTo(date) >= 0))
                .count();
        return reservedDays > 31;

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
            throw new ServiceException("Aucune réservation trouvée.");
        }
    }

    public Reservation findById(int id) throws ServiceException {

        try {
            return reservationDao.findById(id);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Aucune réservation trouvée." + e.getMessage());
        }
    }

    public int count() throws ServiceException {

        try {
            return reservationDao.count();
        } catch (DaoException e) {
            throw new ServiceException("Aucune réservation trouvée." + e.getMessage());
        }
    }

    public int edit(Reservation reservation, int id) throws ServiceException {

        try {
            return reservationDao.edit(reservation, id);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Aucune réservation trouvée." + e.getMessage());
        }
    }

}
