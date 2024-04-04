package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.except.DaoException;
import com.epf.rentmanager.except.ServiceException;
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


//  public static ReservationService instance;
@Autowired
    public ReservationService(ReservationDao reservationDao,ClientDao clientDao, VehicleDao vehicleDao) {

    this.reservationDao = reservationDao;
    this.clientDao = clientDao;
    this.vehicleDao = vehicleDao;

    }

//    public static ReservationService getInstance() {
//        if (instance == null) {
//            instance = new ReservationService();
//        }
//        return instance;
//    }

    public int create(Reservation reservation) throws ServiceException {

        try {

            int reservationVehicleId = reservation.getVehicle_id();
            int reservationClientId = reservation.getClient_id();
            LocalDate dateDebut = reservation.getDebut();
            LocalDate dateFin = reservation.getFin();

            try {

                clientDao.findById(reservationClientId);
            } catch (DaoException e) {

                throw new ServiceException("Le client donné n'existe pas.");
            }
            try {

                clientDao.findById(reservationVehicleId);
            } catch (DaoException e) {
                throw new ServiceException("Le véhicule donné n'existe pas.");
            }
            List<Reservation> allVehicleReservations = reservationDao.findResaByVehicleId(reservationVehicleId);

            for (Reservation reservation_x : allVehicleReservations) {
                if ((reservation_x.getDebut()).equals(dateDebut)) {

                    throw new ServiceException("Cette voiture a déjà été réservé dans la journée. Veuillez choisir une autre date.");
                }
            }

            long intervalle = ChronoUnit.DAYS.between(dateDebut, dateFin);

            if (intervalle > 7) {
                throw new ServiceException("Vous ne pouvez pas réserver une voiture pour plus de 7 jours consécutifs.");
            }

            checkReservationTotalExceedsThirtyDays(reservationVehicleId, dateDebut, dateFin);

            return reservationDao.create(reservation);

        } catch (DaoException e) {

            e.printStackTrace();
            throw new ServiceException("Impossible de créer une nouvelle réservation.");
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

    private void checkReservationTotalExceedsThirtyDays(int vehicleId, LocalDate startDate, LocalDate endDate) throws ServiceException, DaoException {

        List<Reservation> vehicleReservations = reservationDao.findResaByVehicleId(vehicleId);

        long totalDaysReserved = 0;
        long consecutiveDays = 0;
        LocalDate currentDate = startDate;

        for (Reservation vehicleReservation : vehicleReservations) {

            if (!(currentDate.isAfter(vehicleReservation.getFin()) || endDate.isBefore(vehicleReservation.getDebut()))) {
                consecutiveDays++;
            } else {
                consecutiveDays = 0;
            }
            totalDaysReserved = Math.max(totalDaysReserved, consecutiveDays);
            currentDate = currentDate.plusDays(1);
        }

        if (totalDaysReserved + ChronoUnit.DAYS.between(startDate, endDate) > 30) {
            throw new ServiceException("La voiture a été réservée pendant plus de 30 jours consécutifs.");
        }
    }
}
