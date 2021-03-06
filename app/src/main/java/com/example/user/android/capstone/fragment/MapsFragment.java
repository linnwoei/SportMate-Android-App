package com.example.user.android.capstone.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.android.capstone.R;
import com.example.user.android.capstone.activity.EventInfoActivity;
import com.example.user.android.capstone.model.Event;
import com.example.user.android.capstone.utils.MapUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback, EventsFragmentInterface, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {
    GoogleMap mMap;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mEventsRef = mRootRef.child("events");
    MapUtils mapUtils;

    public MapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mapUtils = new MapUtils(getContext());
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        return view;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(47.6101, -122.2015),
                Math.max(10, mMap.getCameraPosition().zoom)));
    }


    @Override
    public void updateList(final List<Event> events) {
        for (Event event : events) {
            LatLng address = mapUtils.getLocationFromAddress(event.getAddress());
            if (address != null) {
            }
        }
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);

    }


    public void callMap(GoogleMap googleMap, List<Event> eventsList) {
        mMap = googleMap;
        for (Event event : eventsList) {
            LatLng address = mapUtils.getLocationFromAddress(event.getAddress());
            if (address != null) {
                mMap.addMarker(new MarkerOptions().position(address)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        .title(event.getTitle()));
            }
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(47.6101, -122.2015),
                Math.max(10, mMap.getCameraPosition().zoom)));

        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
    }


    @Override
    public boolean onMarkerClick(final Marker marker) {
        return false;
    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        String markerTitle = marker.getTitle();
        Query findEventByTitleQuery = mEventsRef.orderByChild("title").equalTo(markerTitle);
        findEventByTitleQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String eventId = "";
                    String address = "";
                    String creatorId = "";
                    String date = "";
                    String time = "";
                    String details = "";
                    String peopleNeeded = "";
                    String title = "";
                    String sportCategory = "";
                    for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                        eventId = eventSnapshot.getKey();
                        address = (String) eventSnapshot.child("address").getValue();
                        creatorId = eventSnapshot.child("creatorId").getValue().toString();
                        date = (String) eventSnapshot.child("dataTime").getValue();
                        time = (String) eventSnapshot.child("time").getValue();
                        details = (String) eventSnapshot.child("details").getValue();
                        peopleNeeded = eventSnapshot.child("peopleNeeded").getValue().toString();
                        sportCategory = (String) eventSnapshot.child("sportCategory").getValue();
                        title = (String) eventSnapshot.child("title").getValue();
                    }
                    Event event = new Event(sportCategory, eventId, title, address, date, time, details, peopleNeeded, creatorId);
                    if (!eventId.equals("")) {
                        Intent intentToGetEventDetailsActivity = new Intent(getContext(), EventInfoActivity.class);
                        intentToGetEventDetailsActivity.putExtra("event", (Parcelable) event);
                        startActivity(intentToGetEventDetailsActivity);
                    } else {
                        System.out.println("Error: cannot find event");
                    }
                } else {
                    System.out.println("Error: Nothing found");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
