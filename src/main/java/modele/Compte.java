package modele;

import enums.CompteType;
import enums.TransactionType;

import java.util.ArrayList;
import java.util.List;

public class Compte {

    private String nom;
    private float montant;
    private CompteType type;

    public Compte(CompteType type, String nom, float montant) {
        this.nom = nom;
        this.type = type;
        this.montant = montant;
    }

    public CompteType getType() {
        return type;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public void crediter(float montant) {
        this.montant += montant;
    }

    public void debiter(float montant) {
        this.montant -= montant;
    }
}
