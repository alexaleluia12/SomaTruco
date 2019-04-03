package aleluiainformatica.somatrucro;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements  PopupMenu.OnMenuItemClickListener {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    private StringWrapper mNameLeftTeam, mNameRightTeam;
    private TextView mPointsLeftTeam, mPointsRightTeam;
    private TextView mWinLeft, mWinRight;
    private Integer mNumberWinsLeft = 0, mNumberWinsRight = 0;
    private Integer mPointsLeft = 0, mPointsRight = 0;

    private static final String POINTS_LEFT_TEAM = "POINTS_LEFT_TEAM";
    private static final String POINTS_RIGHT_TEAM = "POINTS_RIGHT_TEAM";
    private static final String WINS_LEFT_TEAM = "WINS_LEFT_TEAM";
    private static final String WINS_RIGHT_TEAM = "WINS_RIGHT_TEAM";
    private static final String NAME_TEAM_LEFT = "TEAM_LEFT_NAME";
    private static final String NAME_TEAM_RIGHT = "TEAM_RIGHT_NAME";


    private static final Integer LIMIT_WIN = 12;
    private static final Integer NAME_SIZE = 9;
    // --- new code ---
    private Team leftTeam;
    private Team rightTeam;
    private AppStateSingleton appState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(LOG_TAG, "load App");
        //Log.i(LOG_TAG, "si:" + savedInstanceState.toString());

        // let screen turned on while the app is running in foreground
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // initialize name with default values
        mNameLeftTeam = new StringWrapper("");
        mNameRightTeam = new StringWrapper("");
        // --- new code ---
        appState = AppStateSingleton.getInstance();

        leftTeam = new Team(
                "",
                0,
                (TextView) findViewById(R.id.circleLeft),
                (TextView) findViewById(R.id.winsLeft),
                appState);
        rightTeam = new Team(
                "",
                0,
                (TextView) findViewById(R.id.circleRight),
                (TextView) findViewById(R.id.winsRight),
                appState);

        leftTeam.setPlusThreeId((Button) findViewById(R.id.leftButton3Plus));
        leftTeam.setPlusOneId((Button) findViewById(R.id.leftButtonPlus));
        leftTeam.setMinusOneId((Button) findViewById(R.id.leftButtonMinus));

        rightTeam.setPlusThreeId((Button) findViewById(R.id.rightButton3Plus));
        rightTeam.setPlusOneId((Button) findViewById(R.id.rightButtonPlus));
        rightTeam.setMinusOneId((Button) findViewById(R.id.rightButtonMinus));
        // FIXME(Alex) team and appState on require reference to other
        appState.setAppContext(this);
        appState.setLeftTeam(leftTeam);
        appState.setRightTeam(rightTeam);


        // --- end new code ---


        // where count points
//        mPointsLeftTeam = findViewById(R.id.circleLeft);
//        mPointsRightTeam = findViewById(R.id.circleRight);
//        mPointsRight = 0;
//        mPointsLeft = 0;
//        updateLeftSide();
//        updateRightSide();

        // button that control the points
        // left side
//        final Button plus3Left = findViewById(R.id.leftButton3Plus);
//        final Button plusLeft = findViewById(R.id.leftButtonPlus);
//        final Button minusLeft = findViewById(R.id.leftButtonMinus);
//        // right side
//        final Button plus3Right = findViewById(R.id.rightButton3Plus);
//        final Button plusRight = findViewById(R.id.rightButtonPlus);
//        final Button minusRight = findViewById(R.id.rightButtonMinus);


        // add actions to button
        // control points of each side
        // left
//        plus3Left.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mPointsLeft += 3;
//                updateLeftSide();
//            }
//        });
//        plusLeft.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mPointsLeft += 1;
//                updateLeftSide();
//            }
//        });
//        minusLeft.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mPointsLeft -= 1;
//                updateLeftSide();
//            }
//
//        });
//        // right
//        plus3Right.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mPointsRight += 3;
//                updateRightSide();
//            }
//        });
//        plusRight.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mPointsRight += 1;
//                updateRightSide();
//            }
//        });
//        minusRight.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mPointsRight -= 1;
//                updateRightSide();
//            }
//        });
//
//        // show number of wins end with
//        mWinLeft = findViewById(R.id.winsLeft);
//        mWinRight = findViewById(R.id.winsRight);
    }

    // lifecycle method
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        this.saveRuntimeState(savedInstanceState);

        super.onSaveInstanceState(savedInstanceState);
        Log.i(LOG_TAG, "onSave");
    }

    // lifecycle method
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        this.reloadRuntimeState(savedInstanceState);

        Log.i(LOG_TAG, "onRestore");
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences settings =
                PreferenceManager.getDefaultSharedPreferences(this);

        // definir nome salvo na tela
        // name
        String defaultNameLeftTeam = getString(R.string.nameLeftTeam);
        String nameLeftTeam = settings.getString(NAME_TEAM_LEFT, defaultNameLeftTeam);
        mNameLeftTeam.setValue(nameLeftTeam);
        setTextViewValue(R.id.LeftTeam, nameLeftTeam);
        appState.getLeftTeam().setName(nameLeftTeam);


        String defaultNameRightTeam = getString(R.string.nameRightTeam);
        String nameRightTeam = settings.getString(NAME_TEAM_RIGHT, defaultNameRightTeam);
        mNameRightTeam.setValue(nameRightTeam);
        setTextViewValue(R.id.RightTeam, nameRightTeam);
        appState.getRightTeam().setName(nameRightTeam);
        Log.i(LOG_TAG, "lefTeam name: " + mNameLeftTeam);

        // wins
        mNumberWinsLeft = settings.getInt(WINS_LEFT_TEAM, 0);
        setTextViewValue(R.id.winsLeft, "" + mNumberWinsLeft);
        mNumberWinsRight = settings.getInt(WINS_RIGHT_TEAM, 0);
        setTextViewValue(R.id.winsRight, "" + mNumberWinsRight);

    }

    @Override
    protected void onStop() {
        super.onStop();
        // save data on preferences
        SharedPreferences settings =
                PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEditor = settings.edit();

        // wins
        prefEditor.putInt(WINS_LEFT_TEAM, mNumberWinsLeft);
        prefEditor.putInt(WINS_RIGHT_TEAM, mNumberWinsRight);

        // name
        prefEditor.putString(NAME_TEAM_LEFT, mNameLeftTeam.getValue());
        prefEditor.putString(NAME_TEAM_RIGHT, mNameRightTeam.getValue());

        // async save preferences
        prefEditor.apply();

    }

    private void setTextViewValue(int resourceId, String newName) {
        TextView view = findViewById(resourceId);
        view.setText(newName);
    }

    private void saveRuntimeState(Bundle savedInstanceState) {
        // wins
        savedInstanceState.putInt(WINS_LEFT_TEAM, mNumberWinsLeft);
        savedInstanceState.putInt(WINS_RIGHT_TEAM, mNumberWinsRight);

        // points
        savedInstanceState.putInt(POINTS_LEFT_TEAM, mPointsLeft);
        savedInstanceState.putInt(POINTS_RIGHT_TEAM, mPointsRight);

    }

    private void reloadRuntimeState(Bundle savedInstanceState) {
        // wins
        mNumberWinsLeft = savedInstanceState.getInt(WINS_LEFT_TEAM, 0);
        mNumberWinsRight = savedInstanceState.getInt(WINS_RIGHT_TEAM, 0);

        // points
        mPointsLeft = savedInstanceState.getInt(POINTS_LEFT_TEAM, 0);
        mPointsRight = savedInstanceState.getInt(POINTS_RIGHT_TEAM, 0);
    }

//    private void updateLeftSide() {
//        if (mPointsLeft >= LIMIT_WIN) {
//            // left win a global point (tento)
//            mNumberWinsLeft += 1;
//            setWinner(mWinLeft, mNumberWinsLeft, mNameLeftTeam.getValue());
//        }
//        if (mPointsLeft == LIMIT_WIN - 1) {
//            showDialogWarningGame();
//        }
//        mPointsLeftTeam.setText(String.format("%d", mPointsLeft));
//    }

//    private void updateRightSide() {
//        if (mPointsRight >= LIMIT_WIN) {
//            mNumberWinsRight += 1;
//            setWinner(mWinRight, mNumberWinsRight, mNameRightTeam.getValue());
//        }
//        if (mPointsRight == LIMIT_WIN - 1) {
//            showDialogWarningGame();
//        }
//        mPointsRightTeam.setText(String.format("%d", mPointsRight));
//    }

//    private void setWinner(TextView winner, Integer number, String name) {
//        winner.setText(String.format("%d", number));
//        showDialogWinner(name);
//    }

//    private void showDialogWinner(String winner) {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage(winner + " ganhou " + getResources().getString(R.string.winEmoji))
//                .setCancelable(false) // forca a clicar em ok
//                .setPositiveButton(R.string.okText, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        resetPoints();
//                        renderPoints();
//                    }
//                });
//        builder.create().show();
//    }

//    private void showDialogWarningGame() {
//        showToastsMessageCenter(getResources().getString(R.string.warnMessage));
//    }

    private void showDialogReset() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.resetAllQuestion)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton(R.string.okText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        resetAll();
                    }
                });

        builder.create().show();
    }



//    private void resetWins() {
//        mNumberWinsRight = 0;
//        mNumberWinsLeft = 0;
//    }
//
//    private void renderWins() {
//        mWinRight.setText(String.format("%d", mNumberWinsRight));
//        mWinLeft.setText(String.format("%d", mNumberWinsLeft));
//    }
//
//    private void resetPoints() {
//        mPointsLeft = 0;
//        mPointsRight = 0;
//    }
//
//    private void renderPoints() {
//        mPointsRightTeam.setText(String.format("%d", mPointsRight));
//        mPointsLeftTeam.setText(String.format("%d", mPointsLeft));
//    }

    private void resetAll() {
        leftTeam.resetAll();
        rightTeam.resetAll();

        leftTeam.renderUiScoreElement();
        leftTeam.renderUiWinElement();
        rightTeam.renderUiScoreElement();
        rightTeam.renderUiWinElement();
    }

    // pop-up menu
    // https://developer.android.com/guide/topics/ui/menus#PopupMenu
    // method called on activity_main
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
                // return true when finish click process
                showInput(R.id.LeftTeam, leftTeam);
                return true;
            case R.id.menuRightTeam:
                showInput(R.id.RightTeam, rightTeam);
                return true;
            case R.id.menuActionReset:
                showDialogReset();
                return true;
            default:
                return false;
        }
    }

    private void showToastsMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    // show toast at center
//    private void showToastsMessageCenter(String message) {
//        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        toast.show();
//
//    }
    private void showToastsMessageTop(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();

    }

    public void showInput(int teamID, final Team team) {
        final TextView uiTeam = findViewById(teamID);

        // set a dialog with input
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ConstraintLayout customTitle = (ConstraintLayout) getLayoutInflater().inflate(R.layout.dialog_tile, null);
        builder.setCustomTitle(customTitle);

        // get typed count reference
        final TextView countName = customTitle.findViewById(R.id.nameSize);

        // could inflate the input (need an xml just for this input)
        // TextView myText = (TextView)getLayoutInflater().inflate(R.layout.template, null);
        // set up the input
        final EditText input = new EditText(this);
        // add padding
        input.setPadding(40, 12, 40, 18);
        input.setFocusable(true); // define focus no text
        input.setSelectAllOnFocus(true); // select all text
        input.setBackground(null); // remove underline

        // set previous value
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(team.getName());
        // set limit on inputText (over xml is just android:maxLength="10")
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(NAME_SIZE)});

        // number of char that can type
        Integer availableText = NAME_SIZE - team.getName().length();
        final String pattern = getString(R.string.availableSpace);
        countName.setText(String.format(pattern, availableText));

        // textWatcher - https://developer.android.com/reference/android/text/TextWatcher.html
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

                if (start + after == AppConfig.LIMIT_NAME && count == after) {
                    // FIXME(Alex) hardcode runtime message
                    showToastsMessageTop("Máx " + AppConfig.LIMIT_NAME);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                Log.i(LOG_TAG, "on; start=" + start + "; count = " + count + "; before = " + before);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i(LOG_TAG, "after - " + editable.length() + ";" + editable.toString());
                Integer availableText = NAME_SIZE - editable.length();
                countName.setText(String.format(pattern, availableText));
            }
        });



        builder.setView(input);

        // set up buttons actions
        // FIXME(Alex) hardcode runtime message
        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String digitado = input.getText().toString();
                if (digitado.length() > NAME_SIZE) {
                    String message = "Tamanho max de " + NAME_SIZE;
                    showToastsMessage(message);
                } else if (digitado.trim().length() == 0) {
                    showToastsMessage("Digite o Nome por favor");
                } else {
                    uiTeam.setText(digitado);
                    team.setName(digitado);
                }


            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
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

    StringWrapper(String value) {
        this.value = value;
    }

    String getValue() {
        return value;
    }

    void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}