package facade;

import enums.CompteType;
import modele.Compte;
import modele.Transaction;

import java.util.List;
import java.util.Map;

public interface Facade {

    void initCompte();
    float getSolde(CompteType compteType);
    Compte getCompte(String nom);
    Map<CompteType, Compte> getComptes();
    List<Transaction> getTransactions();
    void crediter(CompteType compteType, float montant);
    void debiter(CompteType compteType, float montant);

}
