package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.except.DaoException;
import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.dao.ReservationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

	private final VehicleDao vehicleDao;
	private final ReservationDao reservationDao;
//	public static VehicleService instance;

	@Autowired
	public VehicleService(VehicleDao vehicleDao, ReservationDao reservationDao) {

		this.vehicleDao = vehicleDao;
		this.reservationDao = reservationDao;
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

			List<Reservation> reservations = reservationDao.findResaByVehicleId(vehicle.getId());
			if (!reservations.isEmpty()) {

				for (Reservation reservation : reservations) {
					reservationDao.delete(reservation);
				}
			}

		} catch (DaoException e) {
			throw new ServiceException();
		}

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

	public int edit(Vehicle vehicle, int id) throws ServiceException {

		try {
			return vehicleDao.edit(vehicle, id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("Aucun véhicule trouvé." + e.getMessage());
		}
	}
}