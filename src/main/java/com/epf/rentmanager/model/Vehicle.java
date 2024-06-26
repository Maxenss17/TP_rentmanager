package com.epf.rentmanager.model;

public class Vehicle {
    private int id;
    private String constructeur;
    private String modele;
    private int nb_places;
    public Vehicle() {}

    public Vehicle(int id, String constructeur, String modele, int nb_places) {
        this.id = id;
        this.constructeur = constructeur;
        this.modele = modele;
        this.nb_places = nb_places;
    }
    // Getters
    public int getId() {
        return id;
    }
    public String getConstructeur() {
        return constructeur;
    }
    public String getModele() { return modele;}
    public int getNb_places() {
        return nb_places;
    }
    // Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setConstructeur(String constructeur) {
        this.constructeur = constructeur;
    }
    public void setModele(String modele) {
        this.modele = modele;
    }
    public void setNb_places(int nb_places) {
        this.nb_places = nb_places;
    }

    @Override
    public String toString() {
        return "Vehicle {id=" + id +
                ", constructeur ='" + constructeur + '\'' +
                ", modele ='" + modele + '\'' +
                ", nombre de places ='" + nb_places + '\'' +
                '}';
    }
}
