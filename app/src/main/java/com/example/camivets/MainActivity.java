package com.example.camivets;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    GoogleSignInClient googleSignInClient;
    TextView name, mail;
    private final ActivityResultLauncher<Intent> intentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
                 if (result.getResultCode()== RESULT_OK){
                     Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                     try {
                        GoogleSignInAccount signInAccount = accountTask.getResult(ApiException.class);
                         AuthCredential  authCredential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(),null);
                         auth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                             @Override
                             public void onComplete(@NonNull Task<AuthResult> task) {
                                 if (task.isSuccessful()){
                                     auth = FirebaseAuth.getInstance();
                                     Toast.makeText(MainActivity.this,"Has iniciado sesión correctamente!",Toast.LENGTH_SHORT).show();
                                     goToHome();
                                 }else {
                                     Toast.makeText(MainActivity.this,"Error al iniciar: " + task.getException(),Toast.LENGTH_SHORT).show();
                                 }
                             }
                         });
                     } catch (ApiException e) {
                         e.printStackTrace();
                     }
                 }
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        FirebaseApp.initializeApp(this);
    }

    public void sessionGoogle(View view){
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(MainActivity.this,options);
        auth = FirebaseAuth.getInstance();

        Intent intent = googleSignInClient.getSignInIntent();
        intentActivityResultLauncher.launch(intent);
    }
    private void goToHome(){
        Intent actHome = new Intent(this, NavCamivets.class);
        startActivity(actHome);
    }

    public void goToRegister(View view){
        Intent actRegister = new Intent(this, RegisterActivity.class);
        startActivity(actRegister);
    }
}