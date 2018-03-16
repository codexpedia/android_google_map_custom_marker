package com.example.mapcustommarker;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

// https://stackoverflow.com/a/15040761/2069407
public class CustomInfoWindowMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private MapWrapperLayout mapWrapperLayout;
    private ViewGroup infoWindow;
    private TextView infoTitle;
    private TextView infoSnippet;
    private Button infoButton;
    private OnInfoWindowElemTouchListener infoButtonListener;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mapWrapperLayout = (MapWrapperLayout)findViewById(R.id.map_relative_layout);

        // MapWrapperLayout initialization
        // 39 - default marker height
        // 20 - offset between the default InfoWindow bottom edge and it's content bottom edge
        mapWrapperLayout.init(mMap, getPixelsFromDp(this, 39 + 20));

        // We want to reuse the info window for all the markers,
        // so let's create only one class member instance
        this.infoWindow = (ViewGroup)getLayoutInflater().inflate(R.layout.custom_info_window, null);
        this.infoTitle = (TextView)infoWindow.findViewById(R.id.title);
        this.infoSnippet = (TextView)infoWindow.findViewById(R.id.snippet);
        this.infoButton = (Button)infoWindow.findViewById(R.id.button);

        // Setting custom OnTouchListener which deals with the pressed state
        // so it shows up
        this.infoButtonListener = new OnInfoWindowElemTouchListener(infoButton,
                getResources().getDrawable(R.drawable.button_normal),
                getResources().getDrawable(R.drawable.button_pressed)) {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                // Here we can perform some action triggered after clicking the button
                Toast.makeText(CustomInfoWindowMapsActivity.this, marker.getTitle() + "'s button clicked!", Toast.LENGTH_SHORT).show();
            }
        };
        this.infoButton.setOnTouchListener(infoButtonListener);


        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                // Setting up the infoWindow with current's marker info
                infoTitle.setText(marker.getTitle());
                infoSnippet.setText(marker.getSnippet());
                infoButtonListener.setMarker(marker);

                // We must call this to set the current marker and infoWindow references
                // to the MapWrapperLayout
                mapWrapperLayout.setMarkerWithInfoWindow(marker, infoWindow);
                return infoWindow;
            }
        });

        // Let's add a couple of markers
        mMap.addMarker(new MarkerOptions()
                .title("Prague")
                .snippet("Czech Republic")
                .position(new LatLng(50.08, 14.43)));

        mMap.addMarker(new MarkerOptions()
                .title("Paris")
                .snippet("France")
                .position(new LatLng(48.86,2.33)));

        mMap.addMarker(new MarkerOptions()
                .title("London")
                .snippet("United Kingdom")
                .position(new LatLng(51.51,-0.1)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_info_window);

        mapWrapperLayout = (MapWrapperLayout)findViewById(R.id.map_relative_layout);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
    }
}