package com.example.tchong.alpha.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tchong.alpha.Configurations.ConfiguracionActivity;
import com.example.tchong.alpha.Fragments.ControlFragment;
import com.example.tchong.alpha.R;
import com.example.tchong.alpha.Fragments.ReconFragment;
import com.example.tchong.alpha.Fragments.SensorFragment;
import com.example.tchong.alpha.Singletons.Singleton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {
    private TextView Usuario, Email;
    private ImageView Fusuario;
    private GoogleApiClient googleApiClient;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView =  navigationView.getHeaderView(0);
        LinearLayout nave= (LinearLayout) hView.findViewById(R.id.Wall);


        Usuario = (TextView) hView.findViewById(R.id.TxtUsuario);
        Email = (TextView) hView.findViewById(R.id.TxtEmail);
        Fusuario=(ImageView) hView.findViewById(R.id.Fusuario);

        Usuario.setText(Singleton.getInstance().getUser());
        Email.setText(Singleton.getInstance().getEmail());
        Glide.with(this).load(Singleton.getInstance().getFoto()).into(Fusuario);


        GoogleSignInOptions gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient =new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        FragmentTransaction tx=getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.escenario,new ControlFragment());
        tx.commit();

        ImageView fondito=new ImageView(MenuActivity.this);
        fondito.setImageResource(R.mipmap.tarde);
        fondito.setMaxWidth(200);
        fondito.setMaxHeight(176);
        nave.addView(fondito);
        /*
        Calendar now = Calendar.getInstance();
        int a = now.get(Calendar.AM_PM);
        if(a == Calendar.AM) {

        }
        else {}
*/



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.CerrarSesion) {
            FirebaseAuth.getInstance().signOut();
            Singleton.getInstance().setFoto(null);
            Singleton.getInstance().setPassword(null);
            Singleton.getInstance().setEmail(null);
            Singleton.getInstance().setUser(null);
            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    if (status.isSuccess()){
                        goLogInScreen();
                    }else {
                        Toast.makeText(MenuActivity.this, R.string.NOCERRO, Toast.LENGTH_SHORT).show();
                    }
                }
            });
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void goLogInScreen() {
        finish();
        Intent intent= new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager fm= getSupportFragmentManager();
        int id = item.getItemId();

        if (id == R.id.ReconocimientoFacial) {
            // Handle the camera action
            fm.beginTransaction().replace(R.id.escenario, new ReconFragment()).commit();
//            startActivity(new Intent(this,ReconocimientoActivity.class));
        } else if (id == R.id.ControlDelHogar) {
            fm.beginTransaction().replace(R.id.escenario, new ControlFragment()).commit();
        } else if (id == R.id.Sensores) {
        fm.beginTransaction().replace(R.id.escenario,new SensorFragment()).commit();
        } else if (id == R.id.Configuracion) {
            startActivity(new Intent(this,ConfiguracionActivity.class));

        } else if (id == R.id.Compartir) {

           // fm.beginTransaction().replace(R.id.escenario,new PlusOneFragment()).commit();

        } else if (id == R.id.Soporte) {
            //Toast.makeText(this, Integer.toString(horas), Toast.LENGTH_SHORT).show();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    class  Clima extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... args) {
            String xml = "popo";
            return xml;
        }

        @Override
        protected void onPostExecute(String xml) {
            Double temp=0.0;
            try {
                JSONObject json = new JSONObject(xml);
                if (json != null) {
                    JSONObject details = json.getJSONArray("weather").getJSONObject(0);
                    JSONObject main = json.getJSONObject("main");
                    DateFormat df = DateFormat.getDateTimeInstance();
                     temp=main.getDouble("temp");

                    //cityField.setText(json.getString("name").toUpperCase(Locale.US) + ", " + json.getJSONObject("sys").getString("country"));
                    //detailsField.setText(details.getString("description").toUpperCase(Locale.US));

                    //humidity_field.setText("Humidity: " + main.getString("humidity") + "%");
                    //pressure_field.setText("Pressure: " + main.getString("pressure") + " hPa");
                    //updatedField.setText(df.format(new Date(json.getLong("dt") * 1000)));
                    //weatherIcon.setText(Html.fromHtml(Function.setWeatherIcon(details.getInt("id"),
                           // json.getJSONObject("sys").getLong("sunrise") * 1000,
                            //json.getJSONObject("sys").getLong("sunset") * 1000)));

                    //loader.setVisibility(View.GONE);

                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Error, Check City", Toast.LENGTH_SHORT).show();
            }
            if(temp<25.0){

            }
            if(temp<25.0){}




        }
    }





}
