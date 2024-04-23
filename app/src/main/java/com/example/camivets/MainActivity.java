package com.example.camivets;

import static androidx.constraintlayout.widget.Constraints.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
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
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private static final String KEY = "stored_login";
    private boolean isLogin = false;
    private FirebaseAuth auth,mAuth;
    FirebaseUser user;
    GoogleSignInClient googleSignInClient;
    TextView name, mail;
    CallbackManager mCallbackManager;
    private LoginButton btnFace;
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
                                     ValidateUserBD(auth.getCurrentUser().getEmail(),auth.getCurrentUser().getDisplayName());
                                     goToHome();
                                     setIsLogin(true);
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
        FacebookSdk.sdkInitialize(getApplicationContext());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializa la Autenticación de Firebase
        mAuth = FirebaseAuth.getInstance();

        if (validateIsLogin()){
            goToHome();
        }

        FirebaseApp.initializeApp(this);
        mCallbackManager = CallbackManager.Factory.create();
        mAuth = FirebaseAuth.getInstance();
        btnFace = findViewById(R.id.login_button);

        btnFace.setReadPermissions("email", "public_profile");
        btnFace.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {}

            @Override
            public void onError(FacebookException error) {}
        });

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithEmailAndPassword();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            user = mAuth.getCurrentUser();
                            assert user != null;
                            Toast.makeText(MainActivity.this,"Has iniciado sesión correctamente!",Toast.LENGTH_SHORT).show();
                            goToHome();
                            setIsLogin(true);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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

    public  void sessionFacebook(View view){
        btnFace.callOnClick();
    }

    private void ValidateUserBD(String email, String name){

    }

    private boolean validateIsLogin(){
        sharedPreferences = getSharedPreferences("isAuth", Context.MODE_PRIVATE);
        isLogin = sharedPreferences.getBoolean(KEY,false);
        return isLogin;
    }

    private void setIsLogin(boolean login){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY,login);
        editor.apply();
    }
    private void signInWithEmailAndPassword() {
        EditText editTextEmail = findViewById(R.id.txtUser);
        EditText editTextPassword = findViewById(R.id.txtPassword);

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(MainActivity.this, "Por favor ingresa un correo y contraseña válidos", Toast.LENGTH_SHORT).show();
            return;
        }

        auth = FirebaseAuth.getInstance();
        final FirebaseAuth finalAuth = auth; // Declarar finalAuth como efectivamente final

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Inicio de sesión exitoso
                        FirebaseUser user = finalAuth.getCurrentUser(); // Usar finalAuth en lugar de auth
                        // Actualiza la UI
                        goToHome();
                        setIsLogin(true);
                    } else {
                        // Fallo en el inicio de sesión
                        String errorMessage = task.getException().getMessage();
                        Toast.makeText(MainActivity.this, "Fallo en el inicio de sesión: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}