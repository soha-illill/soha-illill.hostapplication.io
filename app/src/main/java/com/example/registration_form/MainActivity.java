package com.example.registration_form;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    EditText username, password, email, phone;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(v -> {
            String user = username.getText().toString().trim();
            String pass = password.getText().toString().trim();
            String mail = email.getText().toString().trim();
            String phoneNumber = phone.getText().toString().trim();

            if (validateInputs(user, pass, mail, phoneNumber)) {
                boolean isInserted = db.insertUser(user, pass, mail, phoneNumber);
                if (isInserted) {
                    Toast.makeText(MainActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    clearInputs();
                } else {
                    Toast.makeText(MainActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateInputs(String user, String pass, String mail, String phoneNumber) {
        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(mail) || TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            Toast.makeText(this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (phoneNumber.length() < 10) {
            Toast.makeText(this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void clearInputs() {
        username.setText("");
        password.setText("");
        email.setText("");
        phone.setText("");
    }
}
