package com.example.user.android.capstone.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.Resource;
import com.example.user.android.capstone.R;
import com.example.user.android.capstone.activity.EventInfoActivity;
import com.example.user.android.capstone.activity.MainActivity;
import com.example.user.android.capstone.activity.MapsActivity;
import com.example.user.android.capstone.model.Event;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


/**
 * Created by nataliakuleniuk on 6/26/17.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    ImageView eventPhoto;

    List<Event> events;
    Context context;


    public EventAdapter(Context context, List<Event> events) {
        this.events = events;
        this.context = context;
    }


    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new EventAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventAdapter.ViewHolder holder, final int position) {
        holder.eventTitleTextView.setText(events.get(position).getTitle());
        if (context.getClass() == MainActivity.class) {
            switch (events.get(position).getSportCategory()) {
                case "Tennis":
                    holder.eventTitleTextView.setBackgroundResource(R.drawable.tennis);
                    break;
                case "Football":
                    holder.eventTitleTextView.setBackgroundResource(R.drawable.football);
                    break;
                case "Boxing":
                    holder.eventTitleTextView.setBackgroundResource(R.drawable.boxing);
                    break;
                case "Skiing":
                    holder.eventTitleTextView.setBackgroundResource(R.drawable.skiing);
                    break;
                case "Cycling":
                    holder.eventTitleTextView.setBackgroundResource(R.drawable.cycling);
                    break;
                case "Running":
                    holder.eventTitleTextView.setBackgroundResource(R.drawable.running);
                    break;
                case "Ice Hockey":
                    holder.eventTitleTextView.setBackgroundResource(R.drawable.hockey);
                    break;
                case "Hiking":
                    holder.eventTitleTextView.setBackgroundResource(R.drawable.hiking);
                    break;
                case "Climbing":
                    holder.eventTitleTextView.setBackgroundResource(R.drawable.climbing);
                    break;
                case "Gym Workout":
                    holder.eventTitleTextView.setBackgroundResource(R.drawable.workout);
                    break;
                case "Swimming":
                    holder.eventTitleTextView.setBackgroundResource(R.drawable.swimming);
                    break;
                case "Snowboarding":
                    holder.eventTitleTextView.setBackgroundResource(R.drawable.snowboarding);
                    break;
                case "Skateboarding":
                    holder.eventTitleTextView.setBackgroundResource(R.drawable.skateboarding);
                    break;
                case "Volleyball":
                    holder.eventTitleTextView.setBackgroundResource(R.drawable.volleyball);
                    break;
                case "Basketball":
                    holder.eventTitleTextView.setBackgroundResource(R.drawable.basketbal);
                    break;
                case "Bowling":
                    holder.eventTitleTextView.setBackgroundResource(R.drawable.bowling);
                    break;
                case "Golf":
                    holder.eventTitleTextView.setBackgroundResource(R.drawable.golf);
                    break;
                case "Baseball":
                    holder.eventTitleTextView.setBackgroundResource(R.drawable.baseball);
                    break;
                case "Soccer":
                    holder.eventTitleTextView.setBackgroundResource(R.drawable.soccer);
                    break;
                case "Other":
                    holder.eventTitleTextView.setBackgroundResource(R.drawable.default_sport1);
                    break;
                default:
                    holder.eventTitleTextView.setBackgroundResource(R.drawable.default_sport1);
                    break;
            }
            holder.eventDateTextView.setText(events.get(position).getDate());
            String address = events.get(position).getAddress();
            LatLng location = getLocationFromAddress(address);
            if (location != null) {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String cityName = addresses.get(0).getAddressLine(1);
                String[] addressValues = cityName.split(",");
                holder.eventLocationTextView.setText(addressValues[0]);

            }
        }
        else{
            holder.layoutLinear.setVisibility(View.GONE);
            holder.eventTitleTextView.setTextColor(Color.parseColor("#513551"));
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.eventTitleTextView.getLayoutParams();
            params.height = 100;
            holder.eventTitleTextView.setTextSize(20);
            holder.eventTitleTextView.setLayoutParams(params);
        }

        holder.eventLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Class destinationClass = EventInfoActivity.class;
                Intent intentToStartEventInfoActivity = new Intent(context, destinationClass);
                intentToStartEventInfoActivity.putExtra("event", (Parcelable) events.get(position));
                context.startActivity(intentToStartEventInfoActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        LinearLayout eventLinearLayout;
        TextView eventTitleTextView;
        TextView eventLocationTextView;
        TextView eventDateTextView;
        LinearLayout layoutLinear;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            eventDateTextView = (TextView) itemView.findViewById(R.id.event_date);
            eventLocationTextView = (TextView) itemView.findViewById(R.id.event_location);
            eventTitleTextView = (TextView) itemView.findViewById(R.id.event_sport_title);
            eventLinearLayout = (LinearLayout) itemView.findViewById(R.id.layout_item);
            eventPhoto = (ImageView) itemView.findViewById(R.id.event_photo);
            Uri imageUri = Uri.parse("https://cdn0.iconfinder.com/data/icons/sport-and-fitness/500/Ball_football_game_play_soccer_sport_sports_man-512.png");
            eventPhoto.setImageURI(imageUri);
            layoutLinear = (LinearLayout) itemView.findViewById(R.id.date_city_layout);

            // Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(eventPhoto);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public LatLng getLocationFromAddress(String strAddress) {
        Geocoder coder = new Geocoder(context);
        List<android.location.Address> address;
        LatLng p1 = null;
        try {
            if (strAddress != null) {
                address = coder.getFromLocationName(strAddress, 5);
                if (address == null || address.size() == 0) {
                    return null;
                }
                double latit = address.get(0).getLatitude();
                double longit = address.get(0).getLongitude();
                p1 = new LatLng(latit, longit);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p1;
    }

}
