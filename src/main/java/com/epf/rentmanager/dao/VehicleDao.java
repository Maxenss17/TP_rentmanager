package com.epf.rentmanager.dao;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
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

		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(DELETE_VEHICLE_QUERY)) {

			ps.setInt(1, vehicle.getId());
			ps.executeUpdate();

			return vehicle.getId();

		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la suppression du client.");
		}
	}

	public Vehicle findById(int id) throws DaoException {

		try (Connection connection = ConnectionManager.getConnection(); PreparedStatement ps = connection.prepareStatement(FIND_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS)){

			ps.setInt(1, id);
			ResultSet resultSet = ps.executeQuery();

			Vehicle vehicle = new Vehicle();

			if (!resultSet.next()) {

				vehicle.setConstructeur(resultSet.getString(1));
				vehicle.setModele(resultSet.getString(2));
				vehicle.setNb_places(resultSet.getInt(3));
			}
			return vehicle;

		} catch (SQLException e) {
			throw new DaoException();
		}
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
			throw new DaoException("Erreur lors du comptage des véhicules.");
		}
	}

}


