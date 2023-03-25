package facade;

import enums.CompteType;
import enums.TransactionType;
import modele.Compte;
import modele.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class FacadeImpl implements Facade{

    private Map<CompteType, Compte> comptes = new HashMap<>();
    private List<Transaction> transactions = new ArrayList<>();
    @Override
    public void initCompte() {
        this.comptes.put(CompteType.COURANT, new Compte(CompteType.COURANT, "Courant", 0));
        this.comptes.put(CompteType.LIVRETA, new Compte(CompteType.LIVRETA, "Livret A", 0));
    }

    @Override
    public float getSolde(CompteType compteType) {
        return this.comptes.get(compteType).getMontant();
    }

    @Override
    public void crediter(CompteType compteType, float montant) {
        this.transactions.add(new Transaction(
                this.comptes.get(compteType),
                TransactionType.TYPE_CREDIT,
                montant
        ));
        this.comptes.get(compteType).crediter(montant);
    }

    @Override
    public void debiter(CompteType compteType, float montant) {
        this.transactions.add(new Transaction(
                this.comptes.get(compteType),
                TransactionType.TYPE_DEBIT,
                montant
        ));
        this.comptes.get(compteType).debiter(montant);
    }

    @Override
    public Compte getCompte(String nom) {
        for (Compte compte : this.comptes.values()) {
            if (compte.getNom().equals(nom)) {
                return compte;
            }
        }
        return null;
    }

    @Override
    public List<Transaction> getTransactions() {
        return transactions;
    }

    @Override
    public Map<CompteType, Compte> getComptes() {
        return this.comptes;
    }
}
