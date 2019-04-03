package aleluiainformatica.somatrucro;

import android.content.Context;

public class AppStateSingleton {

    private static AppStateSingleton instace = null;
    private Context appContext;
    private Team leftTeam;
    private Team rightTeam;

    private AppStateSingleton() {}

    public static AppStateSingleton getInstance() {
        if (instace == null)
            instace = new AppStateSingleton();

        return instace;
    }

    public Context getAppContext() {
        return appContext;
    }

    public void setAppContext(Context appContext) {
        this.appContext = appContext;
    }

    public Team getLeftTeam() {
        return leftTeam;
    }

    public void setLeftTeam(Team leftTeam) {
        this.leftTeam = leftTeam;
    }

    public Team getRightTeam() {
        return rightTeam;
    }

    public void setRightTeam(Team rightTeam) {
        this.rightTeam = rightTeam;
    }
}
