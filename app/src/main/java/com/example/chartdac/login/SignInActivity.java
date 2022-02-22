package com.example.chartdac.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chartdac.MainActivity;
import com.example.chartdac.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignInActivity extends AppCompatActivity {

    //vars
    private TextInputEditText etMail, etPassword;
    private String email, password;

    private FirebaseFirestore db;

    private Context ctx;

    /*public SignInActivity(Context ctx) {
        this.ctx = ctx;
    }*/

    private void init() {
        etMail = findViewById(R.id.etMail);
        etPassword = findViewById(R.id.etPassword);
    }

    public void btnLoginClick(View v1) {
        //Recup les data saisie
        email = etMail.getText().toString().trim();
        password = etPassword.getText().toString().trim();

        //Verif du remplissage
        if(email.equals("")) {
            etMail.setError(getString(R.string.login_enter_email));
        } else if(password.equals("")) {
            etPassword.setError(getString(R.string.login_enter_password));
        } else {
            //Connexion à Authenficator
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {        //envoi fini
                            if (task.isSuccessful()) {
                                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                                finish();   //Ferme l'activity et recup la mémoire

                        } else {
                            Toast.makeText(SignInActivity.this, getString(R.string.sign_in_failed) + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    });

        }


    }

    public void afficheSignUp(View v1) {
        startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
    }

    public void resetPassword(View v1) {
        startActivity(new Intent(SignInActivity.this, ChangePasswordActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_in);

        init();
    }
}