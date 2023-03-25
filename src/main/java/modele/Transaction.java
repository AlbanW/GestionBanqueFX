package modele;

import enums.TransactionType;

import java.util.Date;

public class Transaction {

    private Compte compte;
    private TransactionType type;
    private double montant;

    public Transaction(Compte compte, TransactionType type, double montant) {
        this.compte = compte;
        this.type = type;
        this.montant = montant;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
}
