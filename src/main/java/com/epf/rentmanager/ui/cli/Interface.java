
package com.epf.rentmanager.ui.cli;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.epf.rentmanager.config.AppConfiguration;
import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.service.ReservationService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


import java.time.format.DateTimeFormatter;

public class Interface {

    public static ClientService clientService;
    public static VehicleService vehicleService;
    public static ReservationService reservationService;

    public static void main(String[] args) throws ServiceException {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        clientService = context.getBean(ClientService.class);
        vehicleService = context.getBean(VehicleService.class);
        reservationService = context.getBean(ReservationService.class);

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("\nQue voulez-vous faire ? ");
            System.out.println("1. Gérer les clients");
            System.out.println("2. Gérer les véhicules");
            System.out.println("3. Gérer les réservations");
            System.out.println("0. Quitter");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 0:
                    System.out.println("\nAu revoir !");
                    System.exit(0);
                    break;
                case 1:
                    manageClients();
                    break;
                case 2:
                    manageVehicles();
                    break;
                case 3:
                    manageReservations();
                    break;
                default:
                    System.out.println("\nChoix invalide. Veuillez choisir parmi les options proposées.");
                    break;
            }
        }
    }
    private static void manageClients() throws ServiceException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nGestion des clients :");
        System.out.println("1. Créer un client");
        System.out.println("2. Lister tous les clients");
        System.out.println("3. Supprimer un client");
        System.out.println("0. Retour au menu principal");

        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 0:
                return;
            case 1:
                createClient();
                break;
            case 2:
                listClients();
                break;
            case 3:
                deleteClient();
                break;
            default:
                System.out.println("\nChoix invalide. Veuillez choisir parmi les options proposées.");
                break;
        }
    }

    private static void manageVehicles() throws ServiceException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nGestion des véhicules :");
        System.out.println("1. Créer un véhicule");
        System.out.println("2. Lister tous les véhicules");
        System.out.println("3. Supprimer un véhicule");
        System.out.println("0. Retour au menu principal");

        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 0:
                return;
            case 1:
                createVehicle();
                break;
            case 2:
                listVehicles();
                break;
            case 3:
                deleteVehicle();
                break;
            default:
                System.out.println("\nChoix invalide. Veuillez choisir parmi les options proposées.");
                break;
        }
    }

    private static void manageReservations() throws ServiceException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nGestion des réservations :");
        System.out.println("1. Créer une réservation");
        System.out.println("2. Lister toutes les réservations");
        System.out.println("3. Lister toutes les réservations associées à un client donné");
        System.out.println("4. Lister toutes les réservations associées à un véhicule donné");
        System.out.println("5. Supprimer une réservation");
        System.out.println("0. Retour au menu principal");

        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 0:
                return;
            case 1:
                createReservation();
                break;
            case 2:
                listAllReservations();
                break;
            case 3:
                listReservationsByClientId();
                break;
            case 4:
                listReservationsByVehicleId();
                break;
            case 5:
                deleteReservation();
                break;

            default:
                System.out.println("\nChoix invalide. Veuillez choisir parmi les options proposées.");
                break;
        }
    }

    public static long createClient() throws ServiceException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("\nCréation d'un nouveau client :");
        System.out.print("Nom : ");
        String nom = scanner.nextLine();

        System.out.print("Prénom : ");
        String prenom = scanner.nextLine();

        System.out.print("Email : ");
        String email;
        do {
            email = scanner.nextLine();
            if (!isValidEmail(email)) {
                System.out.println("\nFormat d'email incorrect. Veuillez réessayer.");
            }
        } while (!isValidEmail(email));

        System.out.print("Date de naissance (YYYY/MM/DD) : ");
        String naissanceString;
        LocalDate naissance;
        do {
            naissanceString = scanner.nextLine();
            naissance = parseDate(naissanceString);
            if (naissance == null) {
                System.out.println("\nFormat de date invalide. Veuillez saisir la date au format YYYY/MM/DD.");
            }
        } while (naissance == null);

        Client newClient = new Client();
        newClient.setNom(nom);
        newClient.setPrenom(prenom);
        newClient.setEmail(email);
        newClient.setNaissance(naissance);

        long new_client = clientService.create(newClient);
        System.out.println("\n Un nouveau client a été créé : " + new_client);
        return new_client;
    }

    private static boolean isValidEmail(String email) {

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }

    private static LocalDate parseDate(String date) {

        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        } catch (Exception e) {
            return null;
        }
    }

    private static void listClients() {

        try {
            List<Client> clients = clientService.findAll();

            if (clients.isEmpty()) {
                System.out.println("\nAucun client trouvé.");
            } else {
                System.out.println("\nListe des clients :");
                for (Client client : clients) {
                    System.out.println(client);
                }
            }
        } catch (ServiceException e) {
            System.out.println("\nErreur lors de la récupération de la liste des clients : " + e.getMessage());
        }
    }

    public static int createVehicle() throws ServiceException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Création d'un nouveau véhicule :");
        System.out.print("\nConstructeur : ");
        String constructeur = scanner.nextLine();

        System.out.print("\nModèle : ");
        String modele = scanner.nextLine();

        System.out.print("\nnombre des places : ");
        int nb_places = Integer.parseInt(scanner.nextLine());

        // Création d'un objet Client avec les informations saisies
        Vehicle newVehicle = new Vehicle();

        newVehicle.setConstructeur(constructeur);
        newVehicle.setModele(modele);
        newVehicle.setNb_places(nb_places);

        int new_vehicle = vehicleService.create(newVehicle);
        System.out.println("\nUn nouveau véhicule a été créé : " + new_vehicle);
        return new_vehicle;

    }

    private static void listVehicles() {

        try {
            List<Vehicle> vehicles = vehicleService.findAll();

            if (vehicles.isEmpty()) {
                System.out.println("\nAucun véhicule trouvé.");
            } else {
                System.out.println("\nListe des véhicules :");
                for (Vehicle vehicle : vehicles) {
                    System.out.println(vehicle);
                }
            }
        } catch (ServiceException e) {
            System.out.println("\nErreur lors de la récupération de la liste des véhicules : " + e.getMessage());
        }
    }

    public static void deleteClient() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Suppression d'un client :");

        listClients();

        System.out.println("\nChoisissez l'ID du client à supprimer : ");
        System.out.print("\nID du client à supprimer : ");

        int clientId = Integer.parseInt(scanner.nextLine());

        try {

            Client clientToDelete = new Client();
            clientToDelete.setId(clientId);

            int id_deleted = clientService.delete(clientToDelete);
            System.out.println("\nLe client a été supprimé avec succès, voici son ancien id :" + id_deleted);

        } catch (ServiceException e) {
            System.out.println("\nErreur lors de la suppression du client : " + e.getMessage());
        }
    }

    public static void deleteVehicle() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Suppression d'un véhicule :");

        listVehicles();

        System.out.println("\nChoisissez l'ID du véhicule à supprimer : ");
        System.out.print("\nID du véhicule à supprimer : ");

        int vehicleId = Integer.parseInt(scanner.nextLine());

        try {

            Vehicle vehicleToDelete = new Vehicle();
            vehicleToDelete.setId(vehicleId);

            int id_deleted = vehicleService.delete(vehicleToDelete);
            System.out.println("\nLe véhicule a été supprimé avec succès, voici son ancien id : " + id_deleted);

        } catch (ServiceException e) {
            System.out.println("\nErreur lors de la suppression du véhicule : " + e.getMessage());
        }
    }

    public static void createReservation() throws ServiceException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("\nCréation d'une nouvelle réservation :");

        System.out.print("\n");
        listClients();

        System.out.print("\nQuel est votre id client ? : ");
        int id_clt = Integer.parseInt(scanner.nextLine());
        Client client_res = clientService.findById(id_clt);
        System.out.print("\nClient choisi : " + client_res);

        System.out.print("\n");
        listVehicles();

        System.out.print("\nQuel est l'id de votre véhicule ? : ");
        int id_vhl = Integer.parseInt(scanner.nextLine());

        Vehicle vehicle_res = vehicleService.findById(id_vhl);
        System.out.print("\nVéhicule choisi : " + vehicle_res);

        System.out.print("\n\nIndiquez les dates correspondantes à votre réservation : ");

        System.out.print("\n\nDate de début (YYYY/MM/DD) : ");
        String debutString;
        LocalDate debut;

        do {
            debutString = scanner.nextLine();
            debut = parseDate(debutString);
            if (debut == null) {
                System.out.println("\nFormat de date invalide. Veuillez saisir la date au format YYYY/MM/DD.");
            }

        } while (debut == null);

        System.out.print("\nDate de fin (YYYY/MM/DD) : ");
        String finString;
        LocalDate fin;

        do {
            finString = scanner.nextLine();
            fin = parseDate(finString);
            if (fin == null) {

                System.out.println("\nFormat de date invalide. Veuillez saisir la date au format YYYY/MM/DD.");

            }
        } while (fin == null);

        Reservation newReservation = new Reservation();

        newReservation.setClient_id(id_clt);
        newReservation.setVehicle_id(id_vhl);
        newReservation.setDebut(debut);
        newReservation.setFin(fin);

        long new_reservation = reservationService.create(newReservation);
        System.out.println("\nUne nouvelle réservation a été créé : " + new_reservation);

    }

    public static void listAllReservations() {

        try {

            List<Reservation> reservations = reservationService.findAll();
            if (reservations.isEmpty()) {
                System.out.println("\nAucune réservation trouvée.");
            } else {
                System.out.println("\nListe de toutes les réservations :");
                for (Reservation reservation : reservations) {
                    System.out.println(reservation);
                }
            }
        } catch (ServiceException e) {
            System.out.println("\nErreur lors de la récupération de la liste des réservations : " + e.getMessage());
        }
    }

    public static void listReservationsByClientId() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nListe des réservations associées à un client donné :");

        listClients();

        System.out.print("\nEntrez l'ID du client : ");

        int clientId = Integer.parseInt(scanner.nextLine());

        try {

            List<Reservation> reservations = reservationService.findResaByClientId(clientId);

            if (reservations.isEmpty()) {
                System.out.println("\nAucune réservation trouvée pour ce client.");
            } else {
                System.out.println("\nListe des réservations pour le client avec l'ID " + clientId + " :");
                for (Reservation reservation : reservations) {
                    System.out.println(reservation);
                }
            }
        } catch (ServiceException e) {
            System.out.println("\nErreur lors de la récupération des réservations : " + e.getMessage());
        }
    }

    public static void listReservationsByVehicleId() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nListe des réservations associées à un véhicule donné :");

        listVehicles();

        System.out.print("\nEntrez l'ID du véhicule : ");
        int vehicleId = Integer.parseInt(scanner.nextLine());

        try {

            List<Reservation> reservations = reservationService.findResaByVehicleId(vehicleId);

            if (reservations.isEmpty()) {
                System.out.println("\nAucune réservation trouvée pour ce véhicule.");
            } else {
                System.out.println("\nListe des réservations pour le véhicule avec l'ID " + vehicleId + " :");
                for (Reservation reservation : reservations) {
                    System.out.println(reservation);
                }
            }
        } catch (ServiceException e) {
            System.out.println("\nErreur lors de la récupération des réservations : " + e.getMessage());
        }
    }

    public static void deleteReservation() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nSuppression d'une réservation :");

        listAllReservations();

        System.out.print("\nEntrez l'ID de la réservation à supprimer : ");
        int reservationId = Integer.parseInt(scanner.nextLine());

        try {

            Reservation reservationToDelete = new Reservation();
            reservationToDelete.setId(reservationId);
            int id_deleted = reservationService.delete(reservationToDelete);
            System.out.println("\nLa réservation a été supprimée avec succès, son ancien ID est : " + id_deleted);
        } catch (ServiceException e) {
            System.out.println("\nErreur lors de la suppression de la réservation : " + e.getMessage());
        }
    }
}




