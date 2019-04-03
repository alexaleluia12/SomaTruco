package aleluiainformatica.somatrucro;

import android.content.Context;

class AppState {

    private Context appContext;
    private Team leftTeam;
    private Team rightTeam;

    Context getAppContext() {
        return appContext;
    }

    void setAppContext(Context appContext) {
        this.appContext = appContext;
    }

    Team getLeftTeam() {
        return leftTeam;
    }

    void setLeftTeam(Team leftTeam) {
        this.leftTeam = leftTeam;
    }

    Team getRightTeam() {
        return rightTeam;
    }

    void setRightTeam(Team rightTeam) {
        this.rightTeam = rightTeam;
    }
}
