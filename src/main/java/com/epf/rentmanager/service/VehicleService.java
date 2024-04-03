package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.except.DaoException;
import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.dao.ReservationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

	private final VehicleDao vehicleDao;
//	public static VehicleService instance;

@Autowired
public VehicleService(VehicleDao vehicleDao) {
		this.vehicleDao = vehicleDao;
	}

//	public static VehicleService getInstance() {
//		if (instance == null) {
//			instance = new VehicleService();
//
//		}
//		return instance;
//	}

	public int create(Vehicle vehicle) throws ServiceException {

		try {
			validateVehicleData(vehicle);
//			checkVehicleConstraints(vehicle);

			return vehicleDao.create(vehicle);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("Impossible de créer un nouveau véhicule.");
        }
    }

	public Vehicle findById(int id) throws ServiceException {

		try {
			return vehicleDao.findById(id);
		} catch (DaoException e) {
			throw new ServiceException("Aucun véhicule trouvé." + e.getMessage());
		}
	}

	public List<Vehicle> findAll() throws ServiceException {
		try {
			return vehicleDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException("Aucun véhicule trouvé." + e.getMessage());
		}
	}

	public int delete(Vehicle vehicle) throws ServiceException {
		try {
			return vehicleDao.delete(vehicle);
		} catch (DaoException e) {
			throw new ServiceException("Aucun véhicule trouvé." + e.getMessage());
		}
	}

	public int count() throws ServiceException {
		try {
			return vehicleDao.count();
		} catch (DaoException e) {
			throw new ServiceException("Aucun véhicule trouvé." + e.getMessage());
		}
	}

	private void validateVehicleData(Vehicle vehicle) throws ServiceException {

		if (vehicle.getModele() == null || vehicle.getModele().isEmpty() ||
				vehicle.getConstructeur() == null || vehicle.getConstructeur().isEmpty() ||
 				vehicle.getNb_places() < 2 || vehicle.getNb_places() > 9) {
			throw new ServiceException("Modèle, constructeur ou nombre de places du véhicule invalide.");
		}
	}
//
//	private void checkVehicleConstraints(Vehicle vehicle) throws ServiceException {
//
//		if (checkReservationLimitExceeded(vehicle, 30)) {
//			throw new ServiceException("Le véhicule ne peut pas être réservé plus de 30 jours de suite.");
//		}
//	}
//
//	private boolean checkReservationLimitExceeded(Vehicle vehicle, int limitDays) throws ServiceException {
//		try {
//
//			List<Reservation> reservations = vehicleDao.findReservationsByVehicleId(vehicle.getId());
//			LocalDate currentDate = LocalDate.now();
//			int consecutiveDays = 0;
//
//			for (Reservation reservation : reservations) {
//				if (reservation.getDebut().isEqual(currentDate)) {
//					consecutiveDays++;
//				} else {
//					consecutiveDays = 0;
//				}
//
//				if (consecutiveDays >= limitDays) {
//					return true;
//				}
//
//				currentDate = currentDate.plusDays(1);
//			}
//			return false;
//		} catch (DaoException e) {
//			throw new ServiceException("Erreur lors de la vérification des réservations du véhicule.");
//		}
//	}

}
