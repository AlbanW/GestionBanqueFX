package pnt;

import controleur.ControleurImpl;
import facade.FacadeImpl;
import javafx.application.Application;
import javafx.stage.Stage;
import vues.GestionnaireVue;
import vues.GestionnaireVueImpl;

import java.io.IOException;

public class MonApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Gestionnaire compte banquaire");
        stage.setResizable(false);

        GestionnaireVue gestionnaireVue = new GestionnaireVueImpl(stage);
        FacadeImpl facadeModele = new FacadeImpl();


        ControleurImpl controle = new ControleurImpl(gestionnaireVue, facadeModele,
                (controleur, gestionnaireVue1) -> {
                    // Propagation du contrôleur pour toutes les vues
                    gestionnaireVue1.getVuesInteractives().stream().forEach(vueInteractive ->
                            vueInteractive.setControleur(controleur));
                    // Inscription des ecouteurs d'ordres
                    gestionnaireVue1.getEcouteurOrdres().stream().forEach(ecouteurOrdre ->
                            ecouteurOrdre.setAbonnement(controleur));
                });
        controle.run();
    }



    public static void main(String[] args) {
        launch();
    }

}
