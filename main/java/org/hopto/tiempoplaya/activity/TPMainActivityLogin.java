package org.hopto.tiempoplaya.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.hopto.tiempoplaya.modelo.TUsuarios;
import org.hopto.tiempoplaya.utils.FilesAccess;
import org.hopto.tiempoplaya.ws.UsuarioWSGoogleSignIn;
import org.hopto.tiempoplaya.ws.UsuarioWSLogOn;
import org.hopto.tiempoplaya.ws.UsuarioWSLogOnWithToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

public class TPMainActivityLogin extends AppCompatActivity {

    private static final int PERMISSIONS_ALL = 0;

    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static final int RC_SIGN_IN = 0;


    //private final String AUTH_FAILED = "AUTH_FAILED";
    private final String TKFILENAME = "tiempoyplaya_tk.txt";

    private EditText etUsername;
    private EditText etPassword;

    private TUsuarios authenticatedUser = new TUsuarios();

    private Boolean thereIsTokenOK = false;
    //private String tkFilePath;
    //private String token = AUTH_FAILED;

    String username = "";
    String password = "";

    private ProgressBar progressBar;

    private com.google.android.gms.common.SignInButton googleSignInButton;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onStart() {
        super.onStart();

        tpCheckPermissions();

        //verify Internet Access
        ConnectivityManager cm = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if(!isConnected){
            //Toast.makeText(getApplicationContext(), "Para ejecutar TyP es necesario acceder a Internet, verifica la conexión", Toast.LENGTH_LONG).show();
        }

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if (account != null){

            if (loginWithToken(account.getIdToken())) {
                //si es true, funciono el tk y hace click en login
                Button b = findViewById(R.id.buttonLogin);
                b.performClick();
            }

        }else {
            // check session tokens
            String tk = findLocalSessionToken();
            if (tk != "") {

                if (loginWithToken(tk)) {
                    //si es true, funciono el tk y hace click en login
                    Button b = findViewById(R.id.buttonLogin);
                    b.performClick();
                }

            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_tpmain_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_login_main);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        mTitle.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //loading forms
        etUsername = findViewById(R.id.editTextUsername);
        etPassword = findViewById(R.id.editTextPassword);

        //progress bar
        progressBar = (ProgressBar) findViewById(R.id.progressBarIndeterminateLoginForm);

        //Animation List Background
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.llLoginLauout);
        linearLayout.setBackgroundResource(R.drawable.login_background_animation);

        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.start();



        //google signin
        googleSignInButton = findViewById(R.id.buttonGoogleSignIn);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.default_web_client_id))
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
            }
        });

    }

    public void doShowCredits(View view) {
        Toast.makeText(getApplicationContext(), "Icons and Images from Flaticon, https://profile.flaticon.com/license/free", Toast.LENGTH_LONG).show();
    }

    //login con token detectado.
    public boolean loginWithToken(String tk) {

        boolean result = false;

        UsuarioWSLogOnWithToken loginWithToken = new UsuarioWSLogOnWithToken(progressBar);
        loginWithToken.execute(tk);

        try {

            if (loginWithToken.get().getId() != null) {

                //System.out.println("\t\tDEBUG: TOKEN USER: " + this.getClass() + loginWithToken.get().toString());
                authenticatedUser = (TUsuarios) loginWithToken.get();
                //el token leido es correcto
                thereIsTokenOK = true;
                result = true;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return result;
    }

    //login con usuario y contrasena
    public boolean loginUserPassword(String user, String pass) {

        boolean result = false;

        UsuarioWSLogOn u = new UsuarioWSLogOn(progressBar);
        u.execute(user, pass);

        try {

            authenticatedUser = (TUsuarios) u.get();

            if (getAuthenticatedUser().getId() > 0){
                result = true;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void doLogin(View view) {

        boolean isAuthUserPassOK = false;
        boolean formOK = false;

        if (!thereIsTokenOK) {

            //comprueba los compos completados.
            if (!etUsername.getText().toString().trim().equals("")) {
                if (!etPassword.getText().toString().trim().equals("")) {
                    username = etUsername.getText().toString();
                    password = etPassword.getText().toString();
                    formOK = true;
                } else {
                    Toast.makeText(this, "introducir contraseña", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "introducir nombre de usuario", Toast.LENGTH_SHORT).show();
            }

            //si el formulario esta correcto entonces
            if (formOK) {

                isAuthUserPassOK = loginUserPassword(getUsername(), getPassword());

                //si el login con user password ha sido correcta se guarda el tk
                if (isAuthUserPassOK) {
                    //se guarda el tk en un archivo
                    if (saveTokenToLocal(getAuthenticatedUser().getTk())) {
                        Toast.makeText(this, "token guardado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "problema con al guardar el token", Toast.LENGTH_SHORT).show();
                        isAuthUserPassOK = false;
                    }

                    //se crea el objeto de usuario
                } else {
                    Toast.makeText(this, "error en los credenciales", Toast.LENGTH_SHORT).show();
                }
            }
        }


        if (isAuthUserPassOK || thereIsTokenOK) {

            Intent i = new Intent(getApplicationContext(), TPMainMenuActivity.class);

            i.putExtra("id", getAuthenticatedUser().getId());
            i.putExtra("nombre", getAuthenticatedUser().getNombre());
            i.putExtra("apellidos", getAuthenticatedUser().getApellidos());
            i.putExtra("email", getAuthenticatedUser().getEmail());
            i.putExtra("usuario", getAuthenticatedUser().getUsuario());
            i.putExtra("tk", getAuthenticatedUser().getTk());

            startActivity(i);

        }

    }

    /**
     * Trying to find a session token
     *
     * @return
     */
    private String findLocalSessionToken() {

        String tkInFile = "";
        File storageDir = getExternalFilesDir("Data");
        File tkFile = new File(storageDir + System.getProperty("file.separator") + TKFILENAME);

        if (tkFile.exists()) {

            FileInputStream fileInputStream = null;

            try {

                fileInputStream = new FileInputStream(tkFile.getAbsolutePath());
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                tkInFile = bufferedReader.readLine();
                fileInputStream.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return tkInFile;
    }

    private boolean saveTokenToLocal(String tk) {

        // Create an tk file name
        File storageDir = getExternalFilesDir("Data");
        File tkFile = new File(storageDir + System.getProperty("file.separator") + TKFILENAME);
        // Save a file: path for use with ACTION_VIEW intents
        //tkFilePath = tkFile.getAbsolutePath();
        FilesAccess.writeToFile(tk, tkFile);

        if (tkFile.exists() && tkFile.isFile() && tkFile.length() > 0)
            return true;
        else
            return false;
    }

    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleGoogleSignInResult(task);
        }
    }

    private void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask){
        try{

            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            UsuarioWSGoogleSignIn usuarioWSGoogleSignIn = new UsuarioWSGoogleSignIn(progressBar);
            usuarioWSGoogleSignIn.execute(account.getIdToken());

            try {

                authenticatedUser = (TUsuarios) usuarioWSGoogleSignIn.get();
                saveTokenToLocal(authenticatedUser.getTk());

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent i = new Intent(getApplicationContext(), TPMainMenuActivity.class);

            i.putExtra("id", getAuthenticatedUser().getId());
            i.putExtra("nombre", getAuthenticatedUser().getNombre());
            i.putExtra("apellidos", getAuthenticatedUser().getApellidos());
            i.putExtra("email", getAuthenticatedUser().getEmail());
            i.putExtra("usuario", getAuthenticatedUser().getUsuario());
            i.putExtra("tk", getAuthenticatedUser().getTk());

            startActivity(i);

        }catch (ApiException e) {
            e.printStackTrace();
        }
    }

    private void tpCheckPermissions(){

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this, INITIAL_PERMS, PERMISSIONS_ALL);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSIONS_ALL: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public TUsuarios getAuthenticatedUser() {
        return authenticatedUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
