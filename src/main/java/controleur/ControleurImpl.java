package controleur;

import controleur.ordres.TypeOrdre;
import enums.CompteType;
import facade.FacadeImpl;
import modele.Compte;
import modele.Transaction;
import vues.GestionnaireVue;

import java.util.List;
import java.util.Map;

public class ControleurImpl extends AbstractControleur {

    private FacadeImpl facade;

    public ControleurImpl(GestionnaireVue gestionnaireVue, FacadeImpl facadeModele, ControleurSetUp controleurSetUp) {
        super(gestionnaireVue);
        this.facade = facadeModele;
        facade.initCompte();
        controleurSetUp.setUp(this, getGestionnaireVue());
    }

    @Override
    public void run() {
        this.fireOrdre(TypeOrdre.LOAD_COMPTE);
        this.fireOrdre(TypeOrdre.LOAD_HISTORIQUE);
        this.fireOrdre(TypeOrdre.LOAD_SOLDE);
        this.fireOrdre(TypeOrdre.SHOW_HOMEPAGE);
    }

    public Map<CompteType, Compte> getComptes()
    {
        return this.facade.getComptes();
    }
    public Compte getCompte(String nom)
    {
        return this.facade.getCompte(nom);
    }


    public void goToHistorique() {
        this.fireOrdre(TypeOrdre.SHOW_HISTORIQUE);
    }
    public void goToDebit() {
        this.fireOrdre(TypeOrdre.SHOW_DEBIT);
    }

    public void goToCredit() {
        this.fireOrdre(TypeOrdre.SHOW_CREDIT);
    }

    public void goToHomepage() {
        this.fireOrdre(TypeOrdre.SHOW_HOMEPAGE);
    }

    public float getSolde(CompteType type) {
        return facade.getSolde(type);
    }

    public void crediter(CompteType compteType, float montant) {
        this.facade.crediter(compteType, montant);
        this.fireOrdre(TypeOrdre.LOAD_SOLDE);
        this.fireOrdre(TypeOrdre.LOAD_HISTORIQUE);
        this.goToHomepage();
    }

    public void debiter(CompteType compteType, float montant) {
        this.facade.debiter(compteType, montant);
        this.fireOrdre(TypeOrdre.LOAD_SOLDE);
        this.fireOrdre(TypeOrdre.LOAD_HISTORIQUE);
        this.goToHomepage();
    }
    public List<Transaction> getTransactions() {
        return this.facade.getTransactions();
    }
}
