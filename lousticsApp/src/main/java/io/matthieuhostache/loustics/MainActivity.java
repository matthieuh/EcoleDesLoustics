package io.matthieuhostache.loustics;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.matthieuhostache.loustics.swipedismiss.SwipeDismissListViewTouchListener;
import io.matthieuhostache.loustics.swipedismiss.SwipeDismissTouchListener;

public class MainActivity extends Activity {

    private Button createNewChild;
    private ListView listView;
    private Context context;
    private List<HashMap<String, ?>> childrenList = new ArrayList<HashMap<String, ?>>();
    private ChildrenListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        createNewChild = (Button) findViewById(R.id.addchild);
        listView = (ListView) findViewById(R.id.listView);

        /*ChildDB childDB = new ChildDB(this);
        childDB.open();

        childrenList.addAll(childDB.getChildren());*/

        adapter = new ChildrenListAdapter(this, childrenList, R.layout.view_child, new String[] {"Name","Pic","Points"},
                new int[] {R.id.item_name,R.id.item_image,R.id.item_points });
        listView.setAdapter(adapter);
        /*childDB.close();*/

        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        listView,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                System.out.println("Dismiss can");
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                System.out.println("Dismiss on");
                                for (int position : reverseSortedPositions) {
                                    confirmDelete(adapter, position);
                                }
                            }
                        });
        listView.setOnTouchListener(touchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        listView.setOnScrollListener(touchListener.makeScrollListener());


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                HashMap<String, ?> map = (HashMap<String, ?>) listView.getItemAtPosition(position);

                Intent MySpaceActivityIntent = new Intent(MainActivity.this, MySpaceActivity.class);
                int Id = (Integer)map.get("Id");
                System.out.println("theId :" + Id);
                MySpaceActivityIntent.putExtra("Id",Id);
                startActivityForResult(MySpaceActivityIntent, 1);
            }
        });

        createNewChild.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent AddChildActivityIntent = new Intent(MainActivity.this, AddChildActivity.class);
                startActivityForResult(AddChildActivityIntent, 1);
            }

        });


    }

    protected void onResume() {
        super.onResume();

        ChildDB childDB = new ChildDB(this);
        childDB.open();
        childrenList.clear();
        childrenList.addAll(childDB.getChildren());
        childDB.close();

        adapter.notifyDataSetChanged();

    }

    public void confirmDelete(final ChildrenListAdapter adapter, final int position) {
        Object item = adapter.getItem(position);

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE: // Yes button clicked
                        HashMap<String, ?> map = (HashMap<String, ?>) listView.getItemAtPosition(position);
                        int childId = (Integer)map.get("Id");

                        ChildDB childDB = new ChildDB(context);
                        childDB.open();
                        childDB.removeChildWithId(childId);
                        childDB.close();
                        childrenList.remove(adapter.getItem(position));
                        adapter.notifyDataSetChanged();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE: // No button clicked // do nothing
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Es-tu sûr de vouloir supprimer ce loustic ?")
                .setPositiveButton("Supprimer", dialogClickListener)
                .setNegativeButton("Annuler", dialogClickListener).show();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
