package aleluiainformatica.somatrucro;

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
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements  PopupMenu.OnMenuItemClickListener {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    private StringWrapper mNameLeftTeam, mNameRightTeam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(LOG_TAG, "load App");

        mNameLeftTeam = new StringWrapper(getResources().getString(R.string.nameLeftTeam));
        mNameRightTeam = new StringWrapper(getResources().getString(R.string.nameRightTeam));

    }

    // pop-up menu
    // https://developer.android.com/guide/topics/ui/menus#PopupMenu
    // metodo chamada na activity_main
    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_bar);

        // sobreescreve nomes padrao
        final Menu menu = popup.getMenu();
        final MenuItem menuLeftTeam = menu.findItem(R.id.menuLeftTeam);
        final MenuItem menuRightTeam = menu.findItem(R.id.menuRightTeam);

        menuLeftTeam.setTitle(mNameLeftTeam.getValue());
        menuRightTeam.setTitle(mNameRightTeam.getValue());

        popup.show();
    }

    // implementa Popup.OnMenuItemClickListener
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Log.i(LOG_TAG, "gerencia click");

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