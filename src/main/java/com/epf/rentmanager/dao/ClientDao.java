package com.epf.rentmanager.dao;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.except.DaoException;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class ClientDao {
	private ClientDao() {}

//	private static ClientDao instance = null;

//	public static ClientDao getInstance() {
//		if(instance == null) {
//			instance = new ClientDao();
//		}
//		return instance;
//	}

	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String EDIT_CLIENT_QUERY = "UPDATE Client SET nom=?, prenom=?, email=? , naissance=? WHERE id=?;";

	public int create(Client client) throws DaoException {

		try (Connection connection = ConnectionManager.getConnection(); PreparedStatement ps = connection.prepareStatement(CREATE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS)){

			ps.setString(1, client.getNom().toUpperCase());
			ps.setString(2, client.getPrenom());
			ps.setString(3, client.getEmail());
			ps.setDate(4, java.sql.Date.valueOf(client.getNaissance()));
			ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys();

			if (!resultSet.next()) {
				throw new DaoException();
			} else {
				return resultSet.getInt(1);
			}

		} catch (SQLException e) {
			throw new DaoException();
		}
    }

	public int delete(Client client) throws DaoException {

		try (Connection connection = ConnectionManager.getConnection(); PreparedStatement ps = connection.prepareStatement(DELETE_CLIENT_QUERY)) {

			ps.setInt(1, client.getId());
			ps.executeUpdate();

			return client.getId();

		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la suppression du client.");
		}
	}

	public Client findById(int id) throws DaoException {

		Client client = new Client();

		try (Connection connection = ConnectionManager.getConnection(); PreparedStatement ps = connection.prepareStatement(FIND_CLIENT_QUERY)) {

			ps.setInt(1, id);
			ResultSet resultset = ps.executeQuery();

			if (resultset.next()){

				client.setId(id);
				client.setNom(resultset.getString(1));
				client.setPrenom(resultset.getString(2));
				client.setEmail(resultset.getString(3));
				client.setNaissance(resultset.getDate(4).toLocalDate());

			} else {
				throw new DaoException("Le client recherché n'existe pas.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return client;
	}

	public List<Client> findAll() throws DaoException {

		List<Client> clients = new ArrayList<>();

		try (Connection connection = ConnectionManager.getConnection(); PreparedStatement ps = connection.prepareStatement(FIND_CLIENTS_QUERY)){

			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {

			Client client = new Client();
			client.setId(resultSet.getInt(1));;
			client.setNom(resultSet.getString(2));
			client.setPrenom(resultSet.getString(3));
			client.setEmail(resultSet.getString(4));
			client.setNaissance(resultSet.getDate(5).toLocalDate());

			clients.add(client);
			}

		return clients;

		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	public int count() throws DaoException {
		try (Connection connection = ConnectionManager.getConnection(); PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM Client")) {

			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				throw new DaoException("Erreur lors du comptage des clients.");
			}

		} catch (SQLException e) {
			throw new DaoException("Erreur lors du comptage des clients.");
		}
	}

	public int edit(Client client, int id) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection()) {
			PreparedStatement ps = connection.prepareStatement(EDIT_CLIENT_QUERY);

			ps.setString(1, client.getNom());
			ps.setString(2, client.getPrenom());
			ps.setString(3, client.getEmail());
			ps.setDate(4, java.sql.Date.valueOf(client.getNaissance()));
			ps.setInt(5, id);

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Erreur lors de la modification du client");
		}
		return client.getId();
	}

}



