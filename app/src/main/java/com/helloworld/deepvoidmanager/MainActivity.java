package com.helloworld.deepvoidmanager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_NULL;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button change_key_btn;
    private ImageButton proceed_btn;

    private EditText key_input_view;
    private EditText service_name_view;
    private EditText seed_view;
    private TextView main_text;

    private boolean now_changing_key = false;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_text = (TextView) findViewById(R.id.welcome_text);

        change_key_btn = (Button) findViewById(R.id.change_key_btn);
        change_key_btn.setOnClickListener(this);
        proceed_btn = (ImageButton) findViewById(R.id.main_proceed_btn);
        proceed_btn.setOnClickListener(this);

        preferences = getSharedPreferences("deepvoid_manager", 0);

        key_input_view = (EditText) findViewById(R.id.key_input);
        service_name_view = (EditText) findViewById(R.id.service_name_input);
        seed_view = (EditText) findViewById(R.id.seed_input);

        key_input_view.setInputType(TYPE_NULL);
        key_input_view.setText("********");
        key_input_view.setEnabled(false);

        validate_master_key();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.change_key_btn:
                if (now_changing_key) {
                    save_new_key();
                } else {
                    initiate_key_change();
                }
                break;
            case R.id.main_proceed_btn:
                Editable service_name = service_name_view.getText();
                if (service_name.length() > 0) {
                    Intent intent = new Intent(this, RecordDetails.class);
                    intent.putExtra("service_name", service_name.toString());

                    Editable seed = seed_view.getText();
                    if (seed.length() > 0) {
                        intent.putExtra("salt", seed.toString());
                    }

                    startActivity(intent);
                }
                break;
        }
    }

    private void initiate_key_change() {
        now_changing_key = true;

        key_input_view.setEnabled(true);
        proceed_btn.setEnabled(false);
        service_name_view.setEnabled(false);
        main_text.setText(R.string.enter_key_to_proceed);
        key_input_view.setInputType(TYPE_CLASS_TEXT);

        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.remove("maser_key").apply();

        change_key_btn.setText(R.string.save_master_key);
    }

    private void save_new_key() {
        now_changing_key = false;

        SharedPreferences.Editor preferencesEditor = preferences.edit();
        Editable new_key = key_input_view.getText();
        if (new_key.length() > 0) {
            preferencesEditor.putString("master_key", String.valueOf(new_key.toString().hashCode())).commit();

            change_key_btn.setText(R.string.change_master_key);
            main_text.setText(R.string.enter_service_name);
            proceed_btn.setEnabled(true);
            service_name_view.setEnabled(true);
            seed_view.setEnabled(true);
            key_input_view.setInputType(TYPE_NULL);
            key_input_view.setText("********");
            key_input_view.setEnabled(false);
        } else {
            Toast.makeText(this, "Please, enter master key", Toast.LENGTH_SHORT).show();
        }
    }

    private void validate_master_key() {
        if (!preferences.contains("master_key")) {
            initiate_key_change();
        }
    }
}
