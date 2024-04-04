package com.epf.rentmanager.service;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.except.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

	private final ClientDao clientDao;
//	public static ClientService instance;

@Autowired
	public ClientService(ClientDao clientDao) {
		this.clientDao = clientDao;
	}

//	public static ClientService getInstance() {
//		if (instance == null) {
//			instance = new ClientService();
//		}
//		return instance;
//	}

	public int create(Client client) throws ServiceException {

		try {
			// Récupération des données du client
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
			 return clientDao.delete(client);
		} catch (DaoException e) {
			throw new ServiceException("Aucun client trouvé." + e.getMessage());
		}
	}

	private boolean isValidEmail(String email) {
		// Vérification de la validité de l'adresse email avec une expression régulière
		String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
		return Pattern.compile(emailRegex).matcher(email).matches();
	}
}

