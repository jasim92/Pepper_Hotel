package com.example.pepper_hotel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class ServerIPActivity extends AppCompatActivity {

    AppCompatButton setIPButton;
    EditText serverIp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_i_p);
        serverIp = findViewById(R.id.ip_address_box);
        setIPButton = findViewById(R.id.set_ip_button);
        setIPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(ServerIPActivity.this, MainActivity.class);
                intent.putExtra("ip","http://"+serverIp.getText().toString()+"/");
                startActivity(intent);
                finish();

            }
        });
    }
}