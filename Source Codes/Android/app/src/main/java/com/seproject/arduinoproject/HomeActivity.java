package com.seproject.arduinoproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private TextView textStatus;
    private ImageView bottleImage;
    private Button button;
    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("user").child("data");


        textStatus = findViewById(R.id.textStatus);
        bottleImage = findViewById(R.id.imageBottle);
        button = findViewById(R.id.wateredButton);



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = (String) snapshot.getValue();
                int data = Integer.parseInt(value);
                System.out.println(data);
                if (data < 600){
                    textStatus.setText("The Flower doesn't need watering...");
                    bottleImage.setImageResource(R.drawable.water_empty);
                    button.setVisibility(View.GONE);
                } else {
                    textStatus.setText("The flower need watering...");
                    bottleImage.setImageResource(R.drawable.water_full);
                    button.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG,"loadPost:onCancelled", error.toException());
            }
        });
    }

    public void wateredButtonClicked(View view) {
        databaseReference.setValue("0");

    }
}