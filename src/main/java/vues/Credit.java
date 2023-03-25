package vues;

import controleur.ordres.EcouteurOrdre;
import controleur.ordres.LanceurOrdre;
import controleur.ordres.TypeOrdre;
import enums.CompteType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import modele.Compte;
import vues.abstractVue.AbstractVueInteractive;

import java.io.IOException;

public class Credit extends AbstractVueInteractive implements EcouteurOrdre {


    @FXML
    private ComboBox<String> compte;

    @FXML
    private TextField montant;

    @FXML
    private Pane pane;

    public static Credit creerVue(GestionnaireVue gestionnaireVue)
    {
        FXMLLoader loader = new FXMLLoader(Credit.class.getResource("credit.fxml"));
        try {
            loader.load();
            Credit vue = loader.getController();
            vue.initialisation();
            gestionnaireVue.ajouterVueInteractive(vue);
            gestionnaireVue.ajouterEcouteurOrdre(vue);
            return vue;
        } catch (IOException e) {

            throw new RuntimeException("Soucis de chargement de la vue Credit: "+ e.getMessage());
        }
    }

    public void loadCompte()
    {
        boolean valueSet = false;
        for(Compte c : this.getControleur().getComptes().values())
        {
            if(!valueSet){
                compte.setValue(c.getNom());
                valueSet = true;
            }
            this.compte.getItems().add(c.getNom());
        }

    }

    public void goToHomepage(ActionEvent actionEvent) {
        this.getControleur().goToHomepage();
    }

    @Override
    public Parent getTopParent() {
        return pane;
    }

    public void click(MouseEvent mouseEvent) {
        // check if this.montant is a number
        if(this.montant.getText().matches("[0-9]+")){
            //check if this.compte is a compte
            if(this.compte.getValue() != null){
                // get the value of this.compte and get his CompteType value
                CompteType compteType = this.getControleur().getCompte(this.compte.getValue()).getType();
                this.getControleur().crediter(
                        compteType,
                        Float.parseFloat(this.montant.getText())
                );
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Succès !");
                alert.setHeaderText("Crédit effectué");
                alert.setContentText("Le crédit de "+this.montant.getText()+"€ a été effectué sur le compte "+compteType.toString());
                alert.showAndWait();

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur de saisie");
                alert.setContentText("Le compte n'est pas valide");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur de saisie");
            alert.setContentText("Le montant doit être un nombre");
            alert.showAndWait();
        }
    }

    @Override
    public void setAbonnement(LanceurOrdre g) {
        g.abonnement(this, TypeOrdre.LOAD_COMPTE);
    }

    @Override
    public void traiter(TypeOrdre e) {
        if(e == TypeOrdre.LOAD_COMPTE){
            loadCompte();
        }
    }
}
