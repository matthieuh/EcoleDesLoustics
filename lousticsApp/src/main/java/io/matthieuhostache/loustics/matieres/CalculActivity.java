package io.matthieuhostache.loustics.matieres;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Random;

import io.matthieuhostache.loustics.AddChildActivity;
import io.matthieuhostache.loustics.MySpaceActivity;
import io.matthieuhostache.loustics.R;

public class CalculActivity extends ActionBarActivity {

    private TextView horloge;
    private TextView val1;
    private TextView val2;
    private TextView typeCalcul;
    private TextView goodAnswersView;
    private EditText result;
    private LinearLayout linearCalcView;
    private LinearLayout endCalcView;
    private Button playAgain;
    private Button backMySpace;
    private int valeur1 = 0;
    private int valeur2 = 0;
    private int res = 0;
    private int goodAnswers = 0;
    private Boolean finished = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcul);

        horloge = (TextView) findViewById(R.id.horloge);
        val1 = (TextView) findViewById(R.id.val1);
        val2 = (TextView) findViewById(R.id.val2);
        typeCalcul = (TextView) findViewById(R.id.typeCalcul);
        result = (EditText) findViewById(R.id.result);
        goodAnswersView = (TextView) findViewById(R.id.goodAnswersView);
        linearCalcView = (LinearLayout) findViewById(R.id.linearCalcView);
        endCalcView = (LinearLayout) findViewById(R.id.endCalcView);
        playAgain = (Button) findViewById(R.id.playAgain);
        backMySpace = (Button) findViewById(R.id.backMySpace);

        typeCalcul.setText(" + ");

        valeur1 = randNb(0, 19);
        valeur2 = randNb(0,19);
        res = valeur1 + valeur2;
        val1.setText(""+valeur1);
        val2.setText(""+valeur2);

        endCalcView.setVisibility(View.INVISIBLE);

        result.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                System.out.println("CharSequence : "+s);
                if(!s.toString().isEmpty() && finished == false) {
                    System.out.println("tailleSeq : "+s.toString().length());
                    System.out.println("tailleSeq : "+Integer.toString(res).length());
                    System.out.println("tailleSeq : "+ (s.toString().length() ==  Integer.toString(res).length()));
                    if( s.toString().length() ==  Integer.toString(res).length()) {
                        if( Integer.parseInt(s.toString()) == res) {
                            goodAnswers++;
                            goodAnswersView.setText(""+goodAnswers);
                        }
                        valeur1 = randNb(0, 19);
                        valeur2 = randNb(0,19);
                        res = valeur1 + valeur2;
                        val1.setText(""+valeur1);
                        val2.setText(""+valeur2);
                        result.setText("");
                    }

                }
            }
        });


        new CountDownTimer(10000, 1) {

            public void onTick(long millisUntilFinished) {
                horloge.setText("Temps restant: " + millisUntilFinished / 1000
                        + "." + millisUntilFinished % 1000);
            }

            public void onFinish() {
                finished = true;
                horloge.setText("Fini !");
                linearCalcView.removeAllViews();
                endCalcView.setVisibility(View.VISIBLE);
            }
        }.start();

        playAgain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                finish();
                startActivity(getIntent());
            }

        });

        backMySpace.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent MySpaceActivityIntent = new Intent(CalculActivity.this, MySpaceActivity.class);
                startActivityForResult(MySpaceActivityIntent, 1);
            }

        });
    }

    public int randNb(int min, int max) {
        Random r = new Random();
        int res = r.nextInt(max - min + 1) + min;
        return res;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.calcul, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_calcul, container, false);
            return rootView;
        }
    }

}
