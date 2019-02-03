package aleluiainformatica.somatrucro;

import android.content.DialogInterface;
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
    private Integer mNameSize = 0;


    private static final Integer LIMIT_WIN = 12;
    private static final Integer NAME_SIZE = 9;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(LOG_TAG, "load App");

        // let screen turned on while the app is running in foreground
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

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
            // left win a global point (tento)
            mNumberWinsLeft += 1;
            setWinner(mWinLeft, mNumberWinsLeft, mNameLeftTeam.getValue());
        }
        if (mPointsLeft == LIMIT_WIN - 1) {
            showDialogWarningGame();
        }
        mPointsLeftTeam.setText(String.format("%d", mPointsLeft));
    }

    private void updateRightSide() {
        if (mPointsRight >= LIMIT_WIN) {
            mNumberWinsRight += 1;
            setWinner(mWinRight, mNumberWinsRight, mNameRightTeam.getValue());
        }
        if (mPointsRight == LIMIT_WIN - 1) {
            showDialogWarningGame();
        }
        mPointsRightTeam.setText(String.format("%d", mPointsRight));
    }

    private void setWinner(TextView winner, Integer number, String name) {
        winner.setText(number.toString());
        showDialogWinner(name);
    }

    private void showDialogWinner(String winner) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(winner + " ganhou " + getResources().getString(R.string.winEmoji))
                .setCancelable(false) // forca a clicar em ok
                .setPositiveButton(R.string.okText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        resetPoints();
                        renderPoints();
                    }
                });
        builder.create().show();
    }

    private void showDialogWarningGame() {
        showToastsMessageCenter("Está de onze");
    }

    private void showDialogReset() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.resetAllQuestion)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
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



    private void resetWins() {
        mNumberWinsRight = 0;
        mNumberWinsLeft = 0;
    }

    private void renderWins() {
        mWinRight.setText(mNumberWinsRight.toString());
        mWinLeft.setText(mNumberWinsLeft.toString());
    }

    private void resetPoints() {
        mPointsLeft = 0;
        mPointsRight = 0;
    }

    private void renderPoints() {
        mPointsRightTeam.setText(mPointsRight.toString());
        mPointsLeftTeam.setText(mPointsLeft.toString());
    }

    private void resetAll() {
        resetPoints();
        resetWins();
        renderPoints();
        renderWins();
    }

    // pop-up menu
    // https://developer.android.com/guide/topics/ui/menus#PopupMenu
    // metodo chamado na activity_main
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
    private void showToastsMessageCenter(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

    }
    private void showToastsMessageTop(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();

    }

    public void showInput(int teamID, final StringWrapper previousName) {
        final TextView team = findViewById(teamID);

        // set a dialog with input
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ConstraintLayout custonTitle = (ConstraintLayout) getLayoutInflater().inflate(R.layout.dialog_tile, null);
        builder.setCustomTitle(custonTitle);

        // get typed count reference
        final TextView countName = custonTitle.findViewById(R.id.nameSize);

        // poderia inflar o input (teria um xml apenas para essa entrada)
        // TextView myText = (TextView)getLayoutInflater().inflate(R.layout.tvtemplate, null);
        // set up the input
        final EditText input = new EditText(this);
        // add padding
        input.setPadding(40, 12, 40, 18);
        input.setFocusable(true); // define focus no texto
        input.setSelectAllOnFocus(true); // select all text
        input.setBackground(null); // remove underline

        // set previous value
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(previousName.getValue());
        // set limit on inputText (over xml is just android:maxLength="10")
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(NAME_SIZE)});

        // numeber of char that can type
        Integer availabeText = NAME_SIZE - previousName.getValue().length();
        countName.setText("(" + availabeText.toString() + ")");

        // adicona watcher editText para saber o tamanho que pode digitar
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() >= 9) {
                    showToastsMessageTop("Máx " + NAME_SIZE);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Integer availabeText = NAME_SIZE - editable.length();
                countName.setText("(" + availabeText.toString() + ")");
            }
        });



        builder.setView(input);

        // set up buttons actions
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
                    team.setText(digitado);
                    previousName.setValue(digitado);
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