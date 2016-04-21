package omrkhld.com.workshopscheduler;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.WorkSource;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private RecyclerView mRecyclerView;
    public ArrayList<Workshop> workshops = null;
    private CardAdapter ca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        workshops = new ArrayList<Workshop>();
    }

    @Override
    public void onResume() {
        super.onResume();
        new UpdateWorkshops().execute();
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("workshoplist.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    class UpdateWorkshops extends AsyncTask<Void,Void,ArrayList<Workshop>> {
        @Override
        protected ArrayList<Workshop> doInBackground(Void... params) {
            workshops.clear();
            String id = "";
            String name = "";
            String coords = "";
            String phone = "";
            int tyre = 0;
            int oil = 0;
            int batt = 0;
            int rating = 0;
            boolean t, o, b;
            try {
                JSONArray arrayNode = new JSONArray(loadJSONFromAsset());
                for (int i = 0; i < arrayNode.length(); i++) {
                    JSONObject workshopNode = arrayNode.getJSONObject(i);
                    id = workshopNode.getString("workshop_id");
                    name = workshopNode.getString("workshop_name");
                    coords = workshopNode.getString("workshop_coordinates");
                    phone = workshopNode.getString("phone");
                    tyre = workshopNode.getInt("tyre_change");
                    if (tyre == 1) {
                        t = true;
                    } else {
                        t = false;
                    }
                    oil = workshopNode.getInt("oil_change");
                    if (oil == 1) {
                        o = true;
                    } else {
                        o = false;
                    }
                    batt = workshopNode.getInt("battery_change");
                    if (batt == 1) {
                        b = true;
                    } else {
                        b = false;
                    }
                    rating = workshopNode.getInt("customer_rating");

                    Workshop shop = new Workshop(id, name, coords, phone, t, o, b, rating);
                    workshops.add(shop);
                }
                return workshops;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Workshop> shops) {
            ca = updateAdapter(shops);
            if (ca != null) {
                mRecyclerView.setAdapter(ca);
            }
        }
    }

    private CardAdapter updateAdapter(ArrayList<Workshop> shops) {
        if (shops != null) {
            ca = new CardAdapter(shops);
        }
        return ca;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter:
                return true;
            case R.id.map:
                startActivity(new Intent(this, MapsActivity.class));
                return true;
            case R.id.sortName:
                if (workshops != null) {
                    Collections.sort(workshops, new Comparator<Workshop>() {
                        @Override
                        public int compare(Workshop lhs, Workshop rhs) {
                            return lhs.getName().compareTo(rhs.getName());
                        }
                    });
                    mRecyclerView.setAdapter(null);
                    ca = updateAdapter(workshops);
                    mRecyclerView.setAdapter(ca);
                }
                return true;
            case R.id.sortRating:
                if (workshops != null) {
                    Collections.sort(workshops, new Comparator<Workshop>() {
                        @Override
                        public int compare(Workshop lhs, Workshop rhs) {
                            return rhs.getRating() - lhs.getRating();
                        }
                    });
                    mRecyclerView.setAdapter(null);
                    ca = updateAdapter(workshops);
                    mRecyclerView.setAdapter(ca);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
