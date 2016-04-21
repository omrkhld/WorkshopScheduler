package omrkhld.com.workshopscheduler;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static final String TAG = "MapsActivity";
    private GoogleMap mMap;
    public ArrayList<Workshop> workshops = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        workshops = (ArrayList<Workshop>) getIntent().getSerializableExtra("Workshops");
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng singapore = new LatLng(1.3521, 103.8198);
        //mMap.addMarker(new MarkerOptions().position(singapore).title("Marker in Singapore"));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(singapore)          // Sets the center of the map to location user
                .zoom(10)                   // Sets the zoom
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        addMarkersToMap();

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + marker.getSnippet()));
                startActivity(intent);
            }
        });
    }

    private void addMarkersToMap() {
        mMap.clear();
        for (int i = 0; i < workshops.size(); i++) {
            String coords = workshops.get(i).getCoords();
            List<String> coordsList = Arrays.asList(coords.split(","));
            LatLng ll = new LatLng(Double.valueOf(coordsList.get(1)), Double.valueOf(coordsList.get(0)));
            mMap.addMarker(new MarkerOptions()
                    .position(ll)
                    .title(workshops.get(i).getName())
                    .snippet(workshops.get(i).getPhone())
            );
        }
    }
}
