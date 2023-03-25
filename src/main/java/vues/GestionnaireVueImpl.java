package vues;

import controleur.ordres.EcouteurOrdre;
import controleur.ordres.LanceurOrdre;
import controleur.ordres.TypeOrdre;
import javafx.stage.Stage;
import vues.abstractVue.AbstractGestionnaireVue;

import java.util.Collection;

public class GestionnaireVueImpl extends AbstractGestionnaireVue {

    private Homepage homepage;
    private Credit credit;
    private Debit debit;
    private Historique historique;

    public GestionnaireVueImpl(Stage stage) {
        super(stage);
        credit = Credit.creerVue(this);
        homepage = Homepage.creerVue(this);
        debit = Debit.creerVue(this);
        historique = Historique.creerVue(this);
    }

    @Override
    public void setAbonnement(LanceurOrdre g) {
        g.abonnement(this, TypeOrdre.SHOW_HOMEPAGE, TypeOrdre.SHOW_CREDIT, TypeOrdre.SHOW_HISTORIQUE, TypeOrdre.SHOW_DEBIT);
    }

    @Override
    public void traiter(TypeOrdre e) {
        switch (e)
        {
            case SHOW_HOMEPAGE: default:
                this.getStage().setScene(this.homepage.getScene());
                break;
            case SHOW_CREDIT:
                this.getStage().setScene(this.credit.getScene());
                break;
            case SHOW_DEBIT:
                this.getStage().setScene(this.debit.getScene());
                break;
            case SHOW_HISTORIQUE:
                this.getStage().setScene(this.historique.getScene());
                break;
        }
        this.getStage().show();
    }

}
