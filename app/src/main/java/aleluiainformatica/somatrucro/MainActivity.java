package aleluiainformatica.somatrucro;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;


public class MainActivity extends AppCompatActivity implements  PopupMenu.OnMenuItemClickListener {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    private StringWrapper mNameLeftTeam, mNameRightTeam;
    private TextView mPointsLeftTeam, mPointsRightTeam;
    private TextView mWinLeft, mWinRight;
    private Integer mNumberWinsLeft = 0, mNumberWinsRight = 0;
    private Integer mPointsLeft = 0, mPointsRight = 0;


    private static final Integer LIMIT_WIN = 12;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(LOG_TAG, "load App");

        // initialize name with default values
        mNameLeftTeam = new StringWrapper(getResources().getString(R.string.nameLeftTeam));
        mNameRightTeam = new StringWrapper(getResources().getString(R.string.nameRightTeam));

        // where count points
        mPointsLeftTeam = findViewById(R.id.circleLeft);
        mPointsRightTeam = findViewById(R.id.circleRight);
        mPointsRight = 0;
        mPointsLeft = 0;
        updateLeftSide();
        updateRightSide();

        // button that control the points
        // left side
        final Button plus3Left = findViewById(R.id.leftButton3Plus);
        final Button plusLeft = findViewById(R.id.leftButtonPlus);
        final Button minusLeft = findViewById(R.id.leftButtonMinus);
        // right side
        final Button plus3Right = findViewById(R.id.rightButton3Plus);
        final Button plusRight = findViewById(R.id.rightButtonPlus);
        final Button minusRight = findViewById(R.id.rightButtonMinus);


        // add actions to button
        // control points of each side
        // left
        plus3Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPointsLeft += 3;
                updateLeftSide();
            }
        });
        plusLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPointsLeft += 1;
                updateLeftSide();
            }
        });
        minusLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPointsLeft -= 1;
                updateLeftSide();
            }
        });
        // right
        plus3Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPointsRight += 3;
                updateRightSide();
            }
        });
        plusRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPointsRight += 1;
                updateRightSide();
            }
        });
        minusRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPointsRight -= 1;
                updateRightSide();
            }
        });

        // show number of wins end with
        mWinLeft = findViewById(R.id.winsLeft);
        mWinRight = findViewById(R.id.winsRight);
    }

    private void updateLeftSide() {
        if (mPointsLeft >= LIMIT_WIN) {
            // every one start at 0
            resetPoints();
            renderPoints();
            // left win a global point (tento)
            mNumberWinsLeft += 1;
            setWinner(mWinLeft, mNumberWinsLeft);
        } else {
            mPointsLeftTeam.setText(String.format("%d", mPointsLeft));
        }

    }

    private void updateRightSide() {
        if (mPointsRight >= LIMIT_WIN) {
            resetPoints();
            renderPoints();
            mNumberWinsRight += 1;
            setWinner(mWinRight, mNumberWinsRight);
        } else {
            mPointsRightTeam.setText(String.format("%d", mPointsRight));
        }

    }

    private void setWinner(TextView winner, Integer number) {
        winner.setText(number.toString());
    }

    private void resetPoints() {
        mPointsLeft = 0;
        mPointsRight = 0;
    }

    private void renderPoints() {
        mPointsRightTeam.setText(mPointsRight.toString());
        mPointsLeftTeam.setText(mPointsLeft.toString());
    }

    // pop-up menu
    // https://developer.android.com/guide/topics/ui/menus#PopupMenu
    // metodo chamada na activity_main
    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_bar);

        // override default names
        final Menu menu = popup.getMenu();
        final MenuItem menuLeftTeam = menu.findItem(R.id.menuLeftTeam);
        final MenuItem menuRightTeam = menu.findItem(R.id.menuRightTeam);

        menuLeftTeam.setTitle(mNameLeftTeam.getValue());
        menuRightTeam.setTitle(mNameRightTeam.getValue());

        popup.show();
    }

    // implement Popup.OnMenuItemClickListener
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuLeftTeam:
                // retorna true quando termina de processar o clique
                showInput(R.id.LeftTeam, mNameLeftTeam);
                return true;
            case R.id.menuRightTeam:
                showInput(R.id.RightTeam, mNameRightTeam);
                return true;
            case R.id.menuActionReset:
                // TODO: falta completar
                Log.w(LOG_TAG, "Action Nao fieta");
                // Perguntar se deseja reamente zerar todo os pontos e comecar nova partida
                return true;
            default:
                return false;
        }
    }

    public void showInput(int teamID, final StringWrapper previousName) {
        final TextView team = findViewById(teamID);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mudar Nome");

        // set up the input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(previousName.getValue());
        builder.setView(input);

        // set up buttons actions
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String digitado = input.getText().toString();
                team.setText(digitado);
                previousName.setValue(digitado);
                Log.i(LOG_TAG, "digitou: " + digitado);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.show();
    }

}


class StringWrapper {
    private String value;

    public StringWrapper(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}