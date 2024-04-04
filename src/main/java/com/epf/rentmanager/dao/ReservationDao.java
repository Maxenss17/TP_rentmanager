package com.epf.rentmanager.dao;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.except.DaoException;

import com.epf.rentmanager.persistence.ConnectionManager;

import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao {
//	private static ReservationDao instance = null;

	private ClientDao clientDao;
	private VehicleDao vehicleDao;
	private ReservationDao() {}

//	public static ReservationDao getInstance() {
//		if(instance == null) {
//			instance = new ReservationDao();
//		}
//		return instance;
//	}

	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String SET_VEHICLE_ID_NULL_QUERY = "UPDATE Reservation SET vehicle_id = null WHERE vehicle_id=?;";

	public int create(Reservation reservation) throws DaoException {

		try (Connection connection = ConnectionManager.getConnection(); PreparedStatement ps = connection.prepareStatement(CREATE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS)) {

			ps.setInt(1, (reservation.getClient_id()));
			ps.setInt(2, (reservation.getVehicle_id()));
			ps.setDate(3, java.sql.Date.valueOf(reservation.getDebut()));
			ps.setDate(4, java.sql.Date.valueOf(reservation.getFin()));

			ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys();

			if (!resultSet.next()) {
				throw new DaoException();
			} else {
				return resultSet.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}
	public int delete(Reservation reservation) throws DaoException {

		try (Connection connection = ConnectionManager.getConnection(); PreparedStatement ps = connection.prepareStatement(DELETE_RESERVATION_QUERY)) {

			ps.setInt(1, reservation.getId());
			ps.executeUpdate();

			return reservation.getId();

		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la suppression du client.");
		}
	}

	public List<Reservation> findResaByClientId(int clientId) throws DaoException {

		List<Reservation> reservations = new ArrayList<>();

		try (Connection connection = ConnectionManager.getConnection(); PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY)) {

			ps.setInt(1, clientId);
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {

				Reservation reservation = new Reservation();
				reservation.setId(resultSet.getInt(1));
				reservation.setClient_id(clientId);
				reservation.setVehicle_id(resultSet.getInt(2));
				reservation.setDebut(resultSet.getDate(3).toLocalDate());
				reservation.setFin(resultSet.getDate(4).toLocalDate());
				reservations.add(reservation);
			}

			return reservations;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Reservation> findResaByVehicleId(int vehicleId) throws DaoException {

		List<Reservation> reservations = new ArrayList<>();

		try (Connection connection = ConnectionManager.getConnection(); PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY)) {

			ps.setInt(1, vehicleId);
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {

				Reservation reservation = new Reservation();
				reservation.setId(resultSet.getInt(1));
				reservation.setClient_id(resultSet.getInt(2));
				reservation.setVehicle_id(vehicleId);
				reservation.setDebut(resultSet.getDate(3).toLocalDate());
				reservation.setFin(resultSet.getDate(4).toLocalDate());

				reservations.add(reservation);
			}

				return reservations;

			} catch (SQLException e) {
				throw new DaoException();
			}
		}

	public List<Reservation> findAll() throws DaoException {

		List<Reservation> reservations = new ArrayList<>();

		try (Connection connection = ConnectionManager.getConnection(); PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_QUERY)) {

			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {

				Reservation reservation = new Reservation();
				reservation.setId(resultSet.getInt(1));
				reservation.setClient_id(resultSet.getInt(2));
				reservation.setVehicle_id(resultSet.getInt(3));
				reservation.setDebut(resultSet.getDate(4).toLocalDate());
				reservation.setFin(resultSet.getDate(5).toLocalDate());

				reservations.add(reservation);
			}

			return reservations;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}

	}

	public void setVehicleIdToNull(int vehicleId) throws DaoException {

		try (Connection connection = ConnectionManager.getConnection(); PreparedStatement ps = connection.prepareStatement(SET_VEHICLE_ID_NULL_QUERY)){

			ps.setInt(1, vehicleId);
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new DaoException();
		}
	}
 }
