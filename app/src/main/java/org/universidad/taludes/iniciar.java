package org.universidad.taludes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class iniciar extends AppCompatActivity {


    EditText usuario, contrasena;
    Button ingresar, registrarse;
    private static FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar);
        conectar();
        mAuth = FirebaseAuth.getInstance();

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = usuario.getText().toString();
                String password = contrasena.getText().toString();

                if (!usuario.getText().toString().equals("") && !contrasena.getText().toString().equals("")) {
                    logearUsuario(email, password);
                } else {
                    Toast.makeText(getApplicationContext(), "No puede haber ningún campo vacio.",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = usuario.getText().toString();
                String password = contrasena.getText().toString();

                if (!usuario.getText().toString().equals("") && !contrasena.getText().toString().equals("")) {
                    crearUsuario(email, password);
                } else {
                    Toast.makeText(getApplicationContext(), "No puede haber ningún campo vacio.",
                            Toast.LENGTH_SHORT).show();
                }

            }

        });
    }

    private void crearUsuario(String email, String password) {

        getmAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(getApplicationContext(), "Registro satisfactorio.",
                                    Toast.LENGTH_SHORT).show();
                            usuario.setText("");
                            contrasena.setText("");

                            FirebaseUser user = getmAuth().getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "No se puedo registrar.",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                        // ...
                    }
                });
    }

    private void logearUsuario(String email, String password) {
        getmAuth().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(getApplicationContext(), "Ingresado correctamente.",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = getmAuth().getCurrentUser();

                            usuario.setText("");
                            contrasena.setText("");

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("nombreUsuario", user.getEmail());
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "No se pudo ingresar.",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                    }
                });
    }

    private void conectar() {

        usuario = findViewById(R.id.txtUsuario);
        contrasena = findViewById(R.id.txtContrasena);
        ingresar = findViewById(R.id.btnIngresar);
        registrarse = findViewById(R.id.btnRegistrarse);

    }

    public static FirebaseAuth getmAuth() {
        return mAuth;
    }
}
