package com.epf.rentmanager.service;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.except.DaoException;
import com.epf.rentmanager.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
	private final ClientDao clientDao;
	private final ReservationDao reservationDao;
//	public static ClientService instance;

	@Autowired
	public ClientService(ClientDao clientDao, ReservationDao reservationDao) {

		this.clientDao = clientDao;
		this.reservationDao = reservationDao;
	}

//	public static ClientService getInstance() {
//		if (instance == null) {
//			instance = new ClientService();
//		}
//		return instance;
//	}

	public int create(Client client) throws ServiceException {

		try {
			String nom = client.getNom();
			client.setNom(nom.toUpperCase());
			return clientDao.create(client);
		} catch (DaoException e) {
			throw new ServiceException("Impossible de créer un nouveau client. : " + e.getMessage());
		}
	}

	public Client findById(int id) throws ServiceException {

		try {
			return clientDao.findById(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("Aucun client trouvé." + e.getMessage());
		}
	}

	public List<Client> findAll() throws ServiceException {

		try {
			return clientDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException("Aucun client trouvé." + e.getMessage());
		}
	}

	public int delete(Client client) throws ServiceException {

		try {
			List<Reservation> reservations = reservationDao.findResaByClientId(client.getId());
			if (!reservations.isEmpty()) {
				for (Reservation reservation : reservations) {
					reservationDao.delete(reservation);
				}
			}
		} catch (DaoException e) {
			throw new ServiceException();
		}
		try {
			return clientDao.delete(client);
		} catch (DaoException e) {
			throw new ServiceException("Aucun client trouvé." + e.getMessage());
		}
	}

	public boolean isEmailAlreadyUsed(String email) throws ServiceException {

		try {
			List<Client> clients = clientDao.findAll();
			for (Client existingClient : clients) {
				if (existingClient.getEmail().equalsIgnoreCase(email)) {
					return true;
				}
			}
			return false;
		} catch (DaoException e) {
			throw new ServiceException("Impossible de vérifier l'adresse e-mail du client.");
		}
	}

	public int count() throws ServiceException {

		try {
			return clientDao.count();
		} catch (DaoException e) {
			throw new ServiceException("Aucun client trouvé." + e.getMessage());
		}
	}

	public int edit(Client client, int id) throws ServiceException {

		try {
			return clientDao.edit(client, id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("Aucun client trouvé." + e.getMessage());
		}
	}
}




