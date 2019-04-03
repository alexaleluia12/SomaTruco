package aleluiainformatica.somatrucro;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class Team implements View.OnClickListener {

    /**
     * id for button +3
     */
    private int plusThreeId = -1;
    private int plusOneId = -1;
    private int minusOneId = -1;
    /**
     * number of wins from this team
     */
    private int winsCount = 0;
    /**
     * Team name
     */
    private String name = "";
    /**
     * score count of the Team
     */
    private int score = 0;
    /**
     * ui element related with score
     */
    private TextView uiCore;
    /**
     * ui element related with winsCount
     */
    private TextView uiWinsCount;

    private AppState appState;

    public Team(String name, int score, TextView uiCore, TextView uiWinsCount, AppState appState) {
        this.name = name;
        this.score = score;
        this.uiCore = uiCore;
        this.uiWinsCount = uiWinsCount;
        this.appState = appState;
    }

    void setPlusThreeId(Button plusThreeButton) {
        plusThreeId = plusThreeButton.getId();
        plusThreeButton.setOnClickListener(this);
    }

    void setPlusOneId(Button plusOneButton) {
        plusOneId = plusOneButton.getId();
        plusOneButton.setOnClickListener(this);
    }

    void setMinusOneId(Button minusOneButton) {
        minusOneId = minusOneButton.getId();
        minusOneButton.setOnClickListener(this);
    }

    private void incrementThree() {
        score += 3;
    }

    private void incrementOne() {
        score += 1;
    }

    private void decrementOne() {
        score -= 1;
    }

    private void checkAndChangeWins() {
        if (score >= 12) {
            winsCount += 1;
        }
    }

    void renderUiWinElement() {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        uiWinsCount.setText(numberFormat.format(winsCount));
    }

    void renderUiScoreElement() {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        uiCore.setText(numberFormat.format(score));
    }

    private void showAlertWinner() {
        Context context = appState.getAppContext();

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getString(R.string.winEmoji, name))
                .setCancelable(false) // force click on ok
                .setPositiveButton(R.string.okText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        appState.getLeftTeam().resetScore();
                        appState.getRightTeam().resetScore();

                        appState.getRightTeam().renderUiScoreElement();
                        appState.getLeftTeam().renderUiScoreElement();
                    }
                });
        builder.create().show();
    }

    private void updateUiScore() {
        renderUiScoreElement();

        checkAndChangeWins();
        if (score >= AppConfig.LIMIT_WIN) {
            resetScore();
            renderUiWinElement();
            showAlertWinner();
        }

        if (score == AppConfig.LIMIT_WIN - 1) {
            showDialogWarningGame();
        }
    }

    private void showDialogWarningGame() {
        showToastsMessageCenter(appState.getAppContext().getString(R.string.warnMessage));
    }

    void resetScore() {
        score = 0;
    }

    void resetWins() {
        winsCount = 0;
    }

    void resetAll() {
        resetScore();
        resetWins();
    }

    int getScore() {
        return score;
    }

    void setScore(int score) {
        this.score = score;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    int getWins() {
        return winsCount;
    }
    
    void setWins(int winsNumber) {
        winsCount = winsNumber;
    }

    // show toast at center
    private void showToastsMessageCenter(String message) {
        Toast toast = Toast.makeText(appState.getAppContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

    }

    @Override
    public void onClick(View button) {
        int currentButtonId = button.getId();
        if (currentButtonId == plusThreeId) {
            incrementThree();
        } else if (currentButtonId == plusOneId) {
            incrementOne();
        } else if (currentButtonId == minusOneId) {
            decrementOne();
        } else {
            throw new RuntimeException("id for button " + currentButtonId + " is not expected");
        }

        updateUiScore();
    }
}


