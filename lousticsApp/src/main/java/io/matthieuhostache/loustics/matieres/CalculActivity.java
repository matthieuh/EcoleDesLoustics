package io.matthieuhostache.loustics.matieres;

import android.app.Activity;
import android.content.Context;
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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import io.matthieuhostache.loustics.AddChildActivity;
import io.matthieuhostache.loustics.Child;
import io.matthieuhostache.loustics.ChildDB;
import io.matthieuhostache.loustics.MainActivity;
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
    private int typeCalc = 0;
    private int childId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcul);

        childId = getIntent().getExtras().getInt("Id");
        typeCalc = getIntent().getExtras().getInt("calculType");

        System.out.println("theId calc :" + childId);

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


        switch(typeCalc) {
            case 0:
                typeCalcul.setText(" + ");
                break;
            case 1:
                typeCalcul.setText(" - ");
                break;
            case 2:
                typeCalcul.setText(" x ");
                break;
            case 3:
                typeCalcul.setText(" / ");
                break;
        }

        newCalcul();

        endCalcView.setVisibility(View.INVISIBLE);

        result.requestFocus();
        if(result.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        result.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!s.toString().isEmpty() && finished == false) {

                    if( s.toString().length() == Integer.toString(res).length()) {
                        if( Integer.parseInt(s.toString()) == res) {
                            goodAnswers++;
                            goodAnswersView.setText(""+goodAnswers);
                        }
                        result.setText("");
                        newCalcul();
                    }

                }
            }
        });

        final Context context = this;


        new CountDownTimer(30000, 1) {

            public void onTick(long millisUntilFinished) {
                horloge.setText(""+millisUntilFinished / 1000);
            }

            public void onFinish() {
                finished = true;
                horloge.setText("Fini !");
                linearCalcView.removeAllViews();
                endCalcView.setVisibility(View.VISIBLE);

                ChildDB childDB = new ChildDB(context);
                childDB.open();
                Child currentChild = childDB.getChildWithId(childId);
                currentChild.setPoints(currentChild.getPoints()+goodAnswers);
                childDB.updateChild(childId,currentChild);
                childDB.close();

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
                /*finish();*/
                Intent returnToMain = new Intent(CalculActivity.this, MySpaceActivity.class);
                returnToMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                returnToMain.putExtra("Id",childId);
                startActivity(returnToMain);
            }

        });
    }

    public void newCalcul() {
        switch(typeCalc) {
            case 0:
                valeur1 = randNb(0,20);
                valeur2 = randNb(0,20);
                res = valeur1 + valeur2;
                break;
            case 1:
                valeur1 = randNb(0,20);
                valeur2 = randNb(0,valeur1);
                res = valeur1 - valeur2;
                break;
            case 2:
                valeur1 = randNb(0,10);
                valeur2 = randNb(0,10);
                res = valeur1 * valeur2;
                break;
            case 3:
                valeur1 = randNb(1,20);
                List integerDividers = new ArrayList<Integer>();
                for(int i=1;i<=valeur1;i++) {
                    if(valeur1%i==0)
                        integerDividers.add(i);
                }
                int temp = randNb(0,integerDividers.size()-1);

                valeur2 = (Integer)integerDividers.get(temp);

                res = valeur1 / valeur2;
                break;
        }
        val1.setText(""+valeur1);
        val2.setText(""+valeur2);
    }

    protected void onResume() {

        super.onResume();


    }

    public int randNb(int min, int max) {
        /*Random r = new Random();
        int res = r.nextInt(max - min + 1) + min;*/
        int res = min + (int)(Math.random() * ((max - min) + 1));
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
