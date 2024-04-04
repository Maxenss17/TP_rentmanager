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
			String prenom = client.getPrenom();
			String email = client.getEmail();

			LocalDate naissance = client.getNaissance();

			// Vérification des données
			if (nom == null || prenom == null || nom.isEmpty() || prenom.isEmpty()) {
				throw new ServiceException("Le client doit posséder un nom et un prénom.");
			}

			if (nom.length() < 3 || prenom.length() < 3) {
				throw new ServiceException("Le nom et le prénom du client doivent comporter au moins 3 caractères.");
			}

			if (!isValidEmail(email)) {
				throw new ServiceException("Adresse email invalide.");
			}

			if (isEmailAlreadyUsed(email)) {
				throw new ServiceException("Cette adresse e-mail est déjà utilisée.");
			}

			LocalDate date18YearsAgo = LocalDate.now().minusYears(18);
			if (naissance.isAfter(date18YearsAgo)) {
				throw new ServiceException("Le client doit avoir au moins 18 ans.");
			}

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
	private boolean isValidEmail(String email) {

		String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
		return Pattern.compile(emailRegex).matcher(email).matches();
	}

	private boolean isEmailAlreadyUsed(String email) throws ServiceException {
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

}



