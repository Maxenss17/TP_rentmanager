package com.epf.rentmanager.dao;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.except.DaoException;

import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDao {
	
//	private static VehicleDao instance = null;
	private VehicleDao() {}

//	public static VehicleDao getInstance() {
//		if(instance == null) {
//			instance = new VehicleDao();
//		}
//		return instance;
//	}
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, modele ,nb_places) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";
	private static final String EDIT_VEHICLE_QUERY = "UPDATE Vehicle SET constructeur=?, modele=?, nb_places=? WHERE id=?;";


	public int create(Vehicle vehicle) throws DaoException {

		try (Connection connection = ConnectionManager.getConnection(); PreparedStatement ps = connection.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS)){

			ps.setString(1, vehicle.getConstructeur().toUpperCase());
			ps.setString(2, vehicle.getModele());
			ps.setInt(3, vehicle.getNb_places());
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

	public int delete(Vehicle vehicle) throws DaoException {

		try (Connection connection = ConnectionManager.getConnection(); PreparedStatement ps = connection.prepareStatement(DELETE_VEHICLE_QUERY)) {

			ps.setInt(1, vehicle.getId());
			ps.executeUpdate();

			return vehicle.getId();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Erreur lors de la suppression du client.");
		}
	}

	public Vehicle findById(int id) throws DaoException {

		Vehicle vehicle = new Vehicle();

		try (Connection connection = ConnectionManager.getConnection(); PreparedStatement ps = connection.prepareStatement(FIND_VEHICLE_QUERY)) {

			ps.setInt(1, id);
			ResultSet resultset = ps.executeQuery();

			if (resultset.next()){

				vehicle.setId(resultset.getInt(1));
				vehicle.setConstructeur(resultset.getString(2));
				vehicle.setModele(resultset.getString(3));
				vehicle.setNb_places(resultset.getInt(4));

			} else {
				throw new DaoException("Le véhicule recherché n'existe pas.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return vehicle;
	}

	public List<Vehicle> findAll() throws DaoException {

		try (Connection connection = ConnectionManager.getConnection(); PreparedStatement ps = connection.prepareStatement(FIND_VEHICLES_QUERY)){

			List<Vehicle> vehicles = new ArrayList<>();

			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {

				Vehicle vehicle = new Vehicle();
				vehicle.setId(resultSet.getInt(1));
				vehicle.setConstructeur(resultSet.getString(2));
				vehicle.setModele(resultSet.getString(3));
				vehicle.setNb_places(resultSet.getInt(4));

				vehicles.add(vehicle);
			}

			return vehicles;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	public int count() throws DaoException {
		try (Connection connection = ConnectionManager.getConnection(); PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM Vehicle")) {

			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				throw new DaoException("Erreur lors du comptage des véhicules.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Erreur lors du comptage des véhicules.");
		}
	}

	public int edit(Vehicle vehicle, int id) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection() ;PreparedStatement ps = connection.prepareStatement(EDIT_VEHICLE_QUERY)) {

			ps.setString(1, vehicle.getConstructeur().toUpperCase());
			ps.setString(2, vehicle.getModele());
			ps.setInt(3, vehicle.getNb_places());
			ps.setInt(4, id);

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Erreur lors de la modification du véhicule");
		}
		return vehicle.getId();
	}

}


