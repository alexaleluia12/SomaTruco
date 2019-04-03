package aleluiainformatica.somatrucro;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
     * ui element related with this team
     */
    private TextView uiTarget;

    public Team(String name, int score, TextView uiTarget) {
        this.name = name;
        this.uiTarget = uiTarget;
        this.score = score;
    }

    public void setPlusThreeId(Button plusThreeButton) {
        plusThreeId = plusThreeButton.getId();
        plusThreeButton.setOnClickListener(this);
    }

    public void setPlusOneId(Button plusOneButton) {
        plusOneId = plusOneButton.getId();
        plusOneButton.setOnClickListener(this);
    }

    public void setMinusOneId(Button minusOneButton) {
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

    private void updateTargetWithScore() {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        uiTarget.setText(numberFormat.format(score));
    }

    public void resetSocore() {
        score = 0;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public int getWins() {
        return winsCount;
    }
    
    public void setWins(int winsNumber) {
        winsCount = winsNumber;
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

        updateTargetWithScore();
    }
}
