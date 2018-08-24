package com.example.tchong.alpha;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int CHOOSE_IMAGE =123 ;
    private static final String TAG = "11";
    private FirebaseAuth mAuth;
    private EditText usuario, password,password2,email;
    private ProgressDialog progressDialog;
    private ImageView foto;
    private Uri uriProfileImage;
    String profileImageUrl;
    private TextView verificacion;
    private Toolbar mTopToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mTopToolbar = (Toolbar) findViewById(R.id.toolbarRegistro);
        setSupportActionBar(mTopToolbar);

        mAuth = FirebaseAuth.getInstance();
        usuario = (EditText) findViewById(R.id.Rusuario);
        email = (EditText) findViewById(R.id.REmail);
        password = (EditText) findViewById(R.id.Password);
        password2 = (EditText) findViewById(R.id.Password2);
        foto= (ImageView)findViewById(R.id.Foto);
        verificacion=(TextView)findViewById(R.id.VerificarEmail);
        findViewById(R.id.Foto).setOnClickListener(this);
        findViewById(R.id.Registrar).setOnClickListener(this);
        findViewById(R.id.Login).setOnClickListener(this);
        findViewById(R.id.RefPassword).setOnClickListener(this);
        findViewById(R.id.RefPassword2).setOnClickListener(this);
        findViewById(R.id.VerificarEmail).setOnClickListener(this);
        findViewById(R.id.VerificarEmail).setVisibility(View.INVISIBLE);
        findViewById(R.id.EntrarMenu).setOnClickListener(this);
        findViewById(R.id.EntrarMenu).setVisibility(View.INVISIBLE);
        progressDialog = new ProgressDialog(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.ActualizarRegistro) {
            sendEmailVerification();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    private void registerUser(){

        final String Email=email.getText().toString().trim();
        final String User=usuario.getText().toString().trim();
        final String Password= password.getText().toString().trim();
        String Password2= password2.getText().toString().trim();
        //****PONER VALIDACIONES DE EMAIL Y CONTRASEÑAS

        if(TextUtils.isEmpty(Email)){
            email.setError("Please enter a valid email");
            email.requestFocus();
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            email.setError("Please enter a valid email");
            email.requestFocus();
            return;
        }

        if (User.isEmpty()) {
            usuario.setError("Escriba su usuario");
            usuario.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(Password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }
        if (Password.isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

        if (Password.length() < 6) {
            password.setError("Minimum lenght of password should be 6");
            password.requestFocus();
            return;
        }

        //creating a new user
        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        //checking if success
                        if(task.isSuccessful()){

                            final CUser user = new CUser(
                                    User,
                                    Email,
                                    Password,
                                    foto
                            );
                            createFirebaseUserProfile(task.getResult().getUser(),user.name);


                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                       Toast.makeText(RegisterActivity.this, "Registro completo, por favor verifica tu usuario", Toast.LENGTH_LONG).show();
                                       findViewById(R.id.VerificarEmail).setVisibility(View.VISIBLE);
                                        //startActivity(new Intent(RegisterActivity.this,MenuActivity.class));
                                    } else {
                                        //display a failure message
                                        Toast.makeText(RegisterActivity.this, "No furula", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }else{
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                        progressDialog.dismiss();
                    }
                });




    }
    private void createFirebaseUserProfile(final FirebaseUser user, final String mName) {

        UserProfileChangeRequest addProfileName = new UserProfileChangeRequest.Builder()
                .setDisplayName(mName)
                .build();

        user.updateProfile(addProfileName)
                .addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Singleton.getInstance().setUser(mName);

                        }
                    }

                });
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.EntrarMenu:
                startActivity(new Intent(RegisterActivity.this,MenuActivity.class));
                break;
            case R.id.VerificarEmail:
                sendEmailVerification();
                break;
            case R.id.Registrar:
                registerUser();
                break;
            case  R.id.Login:
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.Foto:
                showImageChooser();
                break;
            case R.id.RefPassword:
                AlertDialog.Builder pass= new AlertDialog.Builder(this);
                pass.setTitle("Contraseña");

                final TextView info= new TextView(this);
                info.setText("La contraseña debe de tener almenos 6 digitos");
                pass.setView(info);
                pass.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();

                    }
                });
                break;

        }
    }

    private void showImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), CHOOSE_IMAGE);



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriProfileImage = data.getData();
            Singleton.getInstance().setFoto(data.getData());
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriProfileImage);
                foto.setImageBitmap(bitmap);

                uploadImageToFirebaseStorage();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToFirebaseStorage() {
        StorageReference profileImageRef =
                FirebaseStorage.getInstance().getReference("profilepics/" + password2.getText().toString() + ".jpg");

        if (uriProfileImage != null) {

            profileImageRef.putFile(uriProfileImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            profileImageUrl = taskSnapshot.getDownloadUrl().toString();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


    private void sendEmailVerification() {
        FirebaseUser bb = mAuth.getCurrentUser();
        findViewById(R.id.VerificarEmail).setEnabled(false);


        if (bb.isEmailVerified()) {
            verificacion.setText("Email Verified");
            verificacion.setEnabled(true);
            findViewById(R.id.EntrarMenu).setVisibility(View.VISIBLE);


            final FirebaseUser user = mAuth.getCurrentUser();
            user.sendEmailVerification()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // Re-enable Verify Email button

                            findViewById(R.id.VerificarEmail).setEnabled(true);

                            if (task.isSuccessful()) {

                                Toast.makeText(getApplicationContext(), "Verification email sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e(TAG, "sendEmailVerification failed!", task.getException());
                                Toast.makeText(getApplicationContext(), "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }
}

