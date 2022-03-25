package uz.intellisoft.diction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if (savedInstanceState==null){
            changeToMainView();
        }

        bottomNavigationListner();
    }

    @SuppressLint("NonConstantResourceId")
    private void bottomNavigationListner() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.action_translate:
                            changeToMainView();
                            break;
                        case R.id.action_favourites:
                            changeToListView("Favourites.db");
                            break;
                        case R.id.action_history:
                            changeToListView("History.db");
                            break;
                    }
                    return true;
                });
    }

    private void changeToListView(String DBname) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ListViewFragment listViewFragment = ListViewFragment.newInstance(DBname);
        ft.replace(R.id.fragment, listViewFragment);
        ft.commit();
    }

    void changeToMainView() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, new MainFragment());
        ft.commit();
    }
    public void showConfirmationDialog(int answerID, final String nameOfDB, final Context context) {
        int title = R.string.ic_history;
        if (nameOfDB.equals("Favourites.db")) {
            title = R.string.ic_liked;
        }
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(answerID)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    // if user agrees to delete words, then delete
                    public void onClick(DialogInterface dialog, int whichButton) {
                        deleteWordsFromDB(context, nameOfDB);
                        changeToListView(nameOfDB);
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    public void deleteWordsFromDB(Context context, String nameOfDB) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context, nameOfDB);
        dataBaseHelper.deleteAllWords();
        dataBaseHelper.close();
    }
}