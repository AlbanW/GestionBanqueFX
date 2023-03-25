package vues;

import controleur.ordres.EcouteurOrdre;
import controleur.ordres.LanceurOrdre;
import controleur.ordres.TypeOrdre;
import enums.CompteType;
import enums.TransactionType;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import modele.Transaction;
import vues.abstractVue.AbstractVueInteractive;

import java.io.IOException;

public class Historique extends AbstractVueInteractive implements EcouteurOrdre {

    @FXML private Pane pane;
    @FXML private TableView<Transaction> historiqueTable;
    @FXML private TableColumn<Transaction, String> tableCompte;
    @FXML private TableColumn<Transaction, String> tableType;
    @FXML private TableColumn<Transaction, String> tableMontant;

    public static Historique creerVue(GestionnaireVue gestionnaireVue)
    {
        FXMLLoader loader = new FXMLLoader(Historique.class.getResource("historique.fxml"));
        try {
            loader.load();
            Historique vue = loader.getController();
            vue.initialisation();
            gestionnaireVue.ajouterVueInteractive(vue);
            gestionnaireVue.ajouterEcouteurOrdre(vue);
            return vue;
        } catch (IOException e) {
            throw new RuntimeException("Soucis de chargement de la vue Historique");
        }
    }




    public void goToHomepage(ActionEvent actionEvent) {
        this.getControleur().goToHomepage();
    }

    @Override
    public Parent getTopParent() {
        return pane;
    }

    @Override
    public void setAbonnement(LanceurOrdre g) {
        g.abonnement(this, TypeOrdre.LOAD_HISTORIQUE);
    }

    @Override
    public void traiter(TypeOrdre e) {
        if(e == TypeOrdre.LOAD_HISTORIQUE) {
            this.loadHistorique();
        }
    }

    private void loadHistorique() {
        this.historiqueTable.setEditable(false);
        this.tableCompte.setId("compte");
        this.tableMontant.setId("montant");
        this.tableType.setId("type");

        this.tableCompte.setCellValueFactory(new PropertyValueFactory<>("compte"));
        this.tableMontant.setCellValueFactory(new PropertyValueFactory<>("montant"));
        this.tableType.setCellValueFactory(new PropertyValueFactory<>("type"));

        this.tableCompte.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCompte().getNom()));
        this.tableType.setCellValueFactory(cellData -> {
            String type = "Débit";
            if(cellData.getValue().getType() == TransactionType.TYPE_CREDIT)
                type = "Crédit";
            return new SimpleStringProperty(type);
        });
        this.tableMontant.setCellValueFactory(cellData -> {
            String montant = String.valueOf(cellData.getValue().getMontant());
            if(cellData.getValue().getType() == TransactionType.TYPE_DEBIT)
                montant = "-" + montant;
            return new SimpleStringProperty(montant);
        });

        historiqueTable.setRowFactory(tv -> new TableRow<Transaction>() {
            @Override
            public void updateItem(Transaction transaction, boolean empty) {
                super.updateItem(transaction, empty);

                if (transaction == null || empty) {
                    setStyle("");
                } else {
                    if (transaction.getType() == TransactionType.TYPE_CREDIT) {
                        setStyle("-fx-background-color: #99ff99;"); // vert
                    } else {
                        setStyle("-fx-background-color: #ff9999;"); // rouge
                    }
                }
            }
        });

        ObservableList<Transaction> transactionsList = FXCollections.observableArrayList(this.getControleur().getTransactions());
        historiqueTable.setItems(transactionsList);    }
}
