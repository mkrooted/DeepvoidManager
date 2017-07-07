package com.helloworld.deepvoidmanager;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RecordDetails extends Activity implements View.OnClickListener {
    private Button recreate_button;

    private EditText service_name_view;
    private EditText salt_view;
    private EditText username_view;
    private EditText password_view;

    private String service_name;
    private String salt;
    private String master_key;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_details);
        ActionBar actionbar = getActionBar();
        if (actionbar != null)
            actionbar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        service_name = intent.getStringExtra("service_name");
        salt = intent.getStringExtra("salt");

        recreate_button = (Button) findViewById(R.id.recreate_btn);
        service_name_view = (EditText) findViewById(R.id.service_name_value);
        salt_view = (EditText) findViewById(R.id.salt_value);

        username_view = (EditText) findViewById(R.id.username_value);
        password_view = (EditText) findViewById(R.id.password_value);

        preferences = getSharedPreferences("deepvoid_manager", 0);

        master_key = preferences.getString("master_key", null);

        if (service_name != null) {
            service_name_view.setText(service_name);
        }
        if (salt != null) {
            salt_view.setText(salt);
        }

        recreate_button.setOnClickListener(this);

        generate_record(service_name, salt);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.recreate_btn:
//                Log.d("DEEPVOID", service_name_view.getText().toString() + " " + salt_view.getText().toString());
                generate_record(service_name_view.getText().toString(), salt_view.getText().toString());
                break;
        }
    }

    private void generate_record(String service_name) {
        generate_record(service_name, "");
    }

    private void generate_record(String service_name, String salt) {
        disable_inputs();

        if (service_name == null || service_name.length() < 1) {
            username_view.setText(R.string.error_service_name);
            password_view.setText("");
        } else if (master_key == null) {
            username_view.setText(R.string.error_master_key);
            password_view.setText("");
        } else {
            Generator generator = new Generator(master_key, service_name + salt);

            username_view.setText(generator.generate_username());
            password_view.setText(generator.generate_password());
        }

        enable_inputs();
    }

    private void disable_inputs() {
        recreate_button.setEnabled(false);
        service_name_view.setEnabled(false);
        salt_view.setEnabled(false);
    }

    private void enable_inputs() {
        recreate_button.setEnabled(true);
        service_name_view.setEnabled(true);
        salt_view.setEnabled(true);
    }
}
