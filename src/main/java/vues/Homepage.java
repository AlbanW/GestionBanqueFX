package vues;

import controleur.ordres.EcouteurOrdre;
import controleur.ordres.LanceurOrdre;
import controleur.ordres.TypeOrdre;
import enums.CompteType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import vues.abstractVue.AbstractVueInteractive;

import java.io.IOException;

public class Homepage extends AbstractVueInteractive implements EcouteurOrdre {

    @FXML public Text livreta;
    @FXML public Text courant;
    @FXML public Button credit;
    @FXML public Button debit;
    @FXML public Button historique;

    @FXML
    private Pane pane;

    public static Homepage creerVue(GestionnaireVue gestionnaireVue)
    {
        FXMLLoader loader = new FXMLLoader(Homepage.class.getResource("homepage.fxml"));
        try {
            loader.load();
            Homepage vue = loader.getController();
            vue.initialisation();
            gestionnaireVue.ajouterVueInteractive(vue);
            gestionnaireVue.ajouterEcouteurOrdre(vue);
            return vue;
        } catch (IOException e) {
            throw new RuntimeException("Soucis de chargement de la vue Homepage");
        }
    }


    @Override
    public Parent getTopParent() {
        return pane;
    }

    public void goToDebit(ActionEvent actionEvent) {
        this.getControleur().goToDebit();
    }
    public void goToCredit(ActionEvent actionEvent) {
        this.getControleur().goToCredit();
    }
    public void goToHistorique(ActionEvent actionEvent) {
        this.getControleur().goToHistorique();
    }

    @Override
    public void setAbonnement(LanceurOrdre g) {
        g.abonnement(this, TypeOrdre.LOAD_SOLDE);
    }

    @Override
    public void traiter(TypeOrdre e) {
        if(e == TypeOrdre.LOAD_SOLDE)
        {
            this.loadSolde();
        }
    }

    private void loadSolde() {
        float courant = this.getControleur().getSolde(CompteType.COURANT);
        float livreta = this.getControleur().getSolde(CompteType.LIVRETA);
        if(courant >= 0) this.courant.setStyle("-fx-fill: #61ad95;"); else this.courant.setStyle("-fx-fill: #e14545;");
        if(livreta >= 0) this.livreta.setStyle("-fx-fill: #61ad95;"); else this.livreta.setStyle("-fx-fill: #e14545;");

        this.livreta.setText(String.valueOf(livreta));
        this.courant.setText(String.valueOf(courant));
    }
}
