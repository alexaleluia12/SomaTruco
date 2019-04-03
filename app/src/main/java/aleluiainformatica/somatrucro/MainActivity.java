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

    private static final String POINTS_LEFT_TEAM = "POINTS_LEFT_TEAM";
    private static final String POINTS_RIGHT_TEAM = "POINTS_RIGHT_TEAM";
    private static final String WINS_LEFT_TEAM = "WINS_LEFT_TEAM";
    private static final String WINS_RIGHT_TEAM = "WINS_RIGHT_TEAM";
    private static final String NAME_TEAM_LEFT = "TEAM_LEFT_NAME";
    private static final String NAME_TEAM_RIGHT = "TEAM_RIGHT_NAME";

    // --- new code ---
    private Team leftTeam;
    private Team rightTeam;
    private AppStateSingleton appState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(LOG_TAG, "load App");

        // let screen turned on while the app is running in foreground
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

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

        // define saved name on screen
        // name
        String defaultNameLeftTeam = getString(R.string.nameLeftTeam);
        String nameLeftTeam = settings.getString(NAME_TEAM_LEFT, defaultNameLeftTeam);
        leftTeam.setName(nameLeftTeam);
        setTextViewValue(R.id.LeftTeam, nameLeftTeam);

        String defaultNameRightTeam = getString(R.string.nameRightTeam);
        String nameRightTeam = settings.getString(NAME_TEAM_RIGHT, defaultNameRightTeam);
        rightTeam.setName(nameRightTeam);
        setTextViewValue(R.id.RightTeam, nameRightTeam);

        // wins
        int winsLefTeam = settings.getInt(WINS_LEFT_TEAM, 0);
        leftTeam.setWins(winsLefTeam);
        leftTeam.renderUiWinElement();

        int winsRightTeam = settings.getInt(WINS_RIGHT_TEAM, 0);
        rightTeam.setWins(winsRightTeam);
        rightTeam.renderUiWinElement();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // save data on preferences
        SharedPreferences settings =
                PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEditor = settings.edit();

        // wins
        prefEditor.putInt(WINS_LEFT_TEAM, leftTeam.getWins());
        prefEditor.putInt(WINS_RIGHT_TEAM, rightTeam.getWins());

        // name
        prefEditor.putString(NAME_TEAM_LEFT, leftTeam.getName());
        prefEditor.putString(NAME_TEAM_RIGHT, rightTeam.getName());

        // async save preferences
        prefEditor.apply();
    }

    private void setTextViewValue(int resourceId, String newName) {
        TextView view = findViewById(resourceId);
        view.setText(newName);
    }

    private void saveRuntimeState(Bundle savedInstanceState) {
        // wins
        savedInstanceState.putInt(WINS_LEFT_TEAM, leftTeam.getWins());
        savedInstanceState.putInt(WINS_RIGHT_TEAM, rightTeam.getWins());

        // points
        savedInstanceState.putInt(POINTS_LEFT_TEAM, leftTeam.getScore());
        savedInstanceState.putInt(POINTS_RIGHT_TEAM, rightTeam.getScore());

    }

    // FIXME(Alex) can I serialize the whole Team class ??
    private void reloadRuntimeState(Bundle savedInstanceState) {
        // wins
        int winsLeftTeam = savedInstanceState.getInt(WINS_LEFT_TEAM, 0);
        leftTeam.setWins(winsLeftTeam);
        int winsRightTeam = savedInstanceState.getInt(WINS_RIGHT_TEAM, 0);
        rightTeam.setWins(winsRightTeam);

        // points
        int pointsLeftTeam = savedInstanceState.getInt(POINTS_LEFT_TEAM, 0);
        leftTeam.setScore(pointsLeftTeam);
        int pointsRightTeam = savedInstanceState.getInt(POINTS_RIGHT_TEAM, 0);
        rightTeam.setScore(pointsRightTeam);
    }

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
    // method called on activity_main from config ui element (3 dogs vertical)
    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_bar);

        // override default names
        final Menu menu = popup.getMenu();
        final MenuItem menuLeftTeam = menu.findItem(R.id.menuLeftTeam);
        final MenuItem menuRightTeam = menu.findItem(R.id.menuRightTeam);

        menuLeftTeam.setTitle(leftTeam.getName());
        menuRightTeam.setTitle(rightTeam.getName());

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
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(AppConfig.LIMIT_NAME)});

        // number of char that can type
        Integer availableText = AppConfig.LIMIT_NAME - team.getName().length();
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
                Integer availableText = AppConfig.LIMIT_NAME - editable.length();
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
                if (digitado.length() > AppConfig.LIMIT_NAME) {
                    String message = "Tamanho max de " + AppConfig.LIMIT_NAME;
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