package org.hopto.tiempoplaya.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.hopto.tiempoplaya.modelo.TDatos;
import org.hopto.tiempoplaya.modelo.TPlayas;
import org.hopto.tiempoplaya.modelo.TUsuarios;
import org.hopto.tiempoplaya.utils.WSConnectionData;
import org.hopto.tiempoplaya.ws.DatosWSGetStatusPlaya;
import org.hopto.tiempoplaya.ws.PlayaWSGetByName;

import java.util.concurrent.ExecutionException;


public class StatusPlayaActivity extends AppCompatActivity {

    TDatos tdatos = null;

    private ImageView imageViewMainFotografia;

    private TUsuarios authUser = null;
    private TPlayas playa = null;
    private String utmx, utmy, utmz, utmr;

    private Toolbar toolbar;

    private LocationManager locManager;
    private LocationListener locListener;

    //icons LinearLayout
    private LinearLayout linearLayoutViento;
    private LinearLayout linearLayoutOleaje;
    private LinearLayout linearLayoutTiempo;
    private LinearLayout linearLayoutOcupation;
    private LinearLayout linearLayoutWater;
    private LinearLayout linearLayoutSand;
    private LinearLayout linearLayoutMareas;
    private LinearLayout linearLayoutFlags;

    //icons ImageView
    private ImageView imageViewViento;
    private ImageView imageViewOleaje;
    private ImageView imageViewTiempo;
    private ImageView imageViewOcupation;
    private ImageView imageViewWater;
    private ImageView imageViewSand;
    private ImageView imageViewMareas;
    private ImageView imageViewFlags;
    private ImageView imageViewMedusa;

    //icons details
    private TextView textViewViento;
    private TextView textViewOleaje;
    private TextView textViewTiempo;
    private TextView textViewOcupation;
    private TextView textViewWater;
    private TextView textViewSand;
    private TextView textViewMareas;
    private TextView textViewFlags;
    private TextView textViewMedusa;

    //get playa from GPS snniper
    private String nombrePlaya = null;
    private String tk = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_status_playas);

        // get user data from intent extra
        setAuthenticatedUserData();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_login_main);
        setSupportActionBar(toolbar);

        linearLayoutViento = findViewById(R.id.main_content_llViento);
        linearLayoutSand = findViewById(R.id.main_content_llSand);

        //cargar playa mas cercana
        nombrePlaya = getIntent().getStringExtra("nombrePlaya");
        tk = getIntent().getStringExtra("tk");

        //si no se ha seleccionado una playa desde el GPS
        //se realiza una geolocalizacion del device y se muestra
        //la informacion de la playa mas cercana
        if (nombrePlaya == null) {


        } else {
            //se el parametro nombrePlaya tiene informacion sobre la
            //playa, significa que se ha escogido una plata desde el MAPS
            //se lanza un método específico para este fin.

            this.getInformacionPlaya(tk, nombrePlaya);

        }

        //main fotografia de la playa
        imageViewMainFotografia = findViewById(R.id.ivPhotoBeach);

        //imageviews
        imageViewViento = findViewById(R.id.main_content_ivViento);
        imageViewOleaje = findViewById(R.id.main_content_ivOleaje);
        imageViewTiempo = findViewById(R.id.main_content_ivTiempo);
        imageViewOcupation = findViewById(R.id.main_content_ivOcupation);
        imageViewSand = findViewById(R.id.main_content_ivSand);
        imageViewWater = findViewById(R.id.main_content_ivWater);
        imageViewFlags = findViewById(R.id.main_content_ivFlags);

    }

    public void doClickOnViento(View view) {

        textViewViento = findViewById(R.id.main_content_tvViento);

        String vientoDetails = (String) textViewViento.getText();

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.app_main_toast,
                (ViewGroup) findViewById(R.id.app_main_toast));

        TextView textTitle = (TextView) layout.findViewById(R.id.app_main_toast_title);
        TextView textDetails = (TextView) layout.findViewById(R.id.app_main_toast_text_details);
        ImageView image = (ImageView) layout.findViewById(R.id.app_main_toast_image);

        textTitle.setText(getResources().getString(R.string.status_wind));

        ImageView iv = (ImageView) findViewById(R.id.main_content_ivViento);
        image.setImageDrawable(iv.getDrawable());

        textDetails.setText(vientoDetails);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }

    public void doClickOnOleaje(View view) {
        textViewOleaje = findViewById(R.id.main_content_tvOleaje);

        String oleajeDetails = (String) textViewOleaje.getText();

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.app_main_toast,
                (ViewGroup) findViewById(R.id.app_main_toast));

        TextView textTitle = (TextView) layout.findViewById(R.id.app_main_toast_title);
        TextView textDetails = (TextView) layout.findViewById(R.id.app_main_toast_text_details);
        ImageView image = (ImageView) layout.findViewById(R.id.app_main_toast_image);

        textTitle.setText(getResources().getString(R.string.status_sea));

        ImageView iv = (ImageView) findViewById(R.id.main_content_ivOleaje);
        image.setImageDrawable(iv.getDrawable());

        textDetails.setText(oleajeDetails);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public void doClickOnTiempo(View view) {
        textViewTiempo = findViewById(R.id.main_content_tvTiempo);

        String tiempoDetails = (String) textViewTiempo.getText();

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.app_main_toast,
                (ViewGroup) findViewById(R.id.app_main_toast));

        TextView textTitle = (TextView) layout.findViewById(R.id.app_main_toast_title);
        TextView textDetails = (TextView) layout.findViewById(R.id.app_main_toast_text_details);
        ImageView image = (ImageView) layout.findViewById(R.id.app_main_toast_image);

        textTitle.setText(getResources().getString(R.string.status_weather));

        ImageView iv = (ImageView) findViewById(R.id.main_content_ivTiempo);
        image.setImageDrawable(iv.getDrawable());

        textDetails.setText(tiempoDetails);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public void doClickOnOcupation(View view) {
        textViewOcupation = findViewById(R.id.main_content_tvOcupation);

        String ocupationDetails = (String) textViewOcupation.getText();

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.app_main_toast,
                (ViewGroup) findViewById(R.id.app_main_toast));

        TextView textTitle = (TextView) layout.findViewById(R.id.app_main_toast_title);
        TextView textDetails = (TextView) layout.findViewById(R.id.app_main_toast_text_details);
        ImageView image = (ImageView) layout.findViewById(R.id.app_main_toast_image);

        textTitle.setText(getResources().getString(R.string.status_ocupacion));

        ImageView iv = (ImageView) findViewById(R.id.main_content_ivOcupation);
        image.setImageDrawable(iv.getDrawable());

        textDetails.setText(ocupationDetails);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public void doClickOnWater(View view) {
        textViewWater = findViewById(R.id.main_content_tvWater);

        String waterDetails = (String) textViewWater.getText();

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.app_main_toast,
                (ViewGroup) findViewById(R.id.app_main_toast));

        TextView textTitle = (TextView) layout.findViewById(R.id.app_main_toast_title);
        TextView textDetails = (TextView) layout.findViewById(R.id.app_main_toast_text_details);
        ImageView image = (ImageView) layout.findViewById(R.id.app_main_toast_image);

        textTitle.setText(getResources().getString(R.string.status_water));

        ImageView iv = (ImageView) findViewById(R.id.main_content_ivWater);
        image.setImageDrawable(iv.getDrawable());

        textDetails.setText(waterDetails);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public void doClickOnSand(View view) {

        textViewSand = findViewById(R.id.main_content_tvSand);

        String sandDetails = (String) textViewSand.getText();

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.app_main_toast,
                (ViewGroup) findViewById(R.id.app_main_toast));

        TextView textTitle = (TextView) layout.findViewById(R.id.app_main_toast_title);
        TextView textDetails = (TextView) layout.findViewById(R.id.app_main_toast_text_details);
        ImageView image = (ImageView) layout.findViewById(R.id.app_main_toast_image);

        textTitle.setText(getResources().getString(R.string.status_sand));

        ImageView iv = (ImageView) findViewById(R.id.main_content_ivSand);
        image.setImageDrawable(iv.getDrawable());

        textDetails.setText(sandDetails);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }

    public void doClickOnMedusas(View view) {

        textViewMedusa = findViewById(R.id.main_content_tvMedusas);

        String medusasDetails = (String) textViewMedusa.getText();

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.app_main_toast,
                (ViewGroup) findViewById(R.id.app_main_toast));

        TextView textTitle = (TextView) layout.findViewById(R.id.app_main_toast_title);
        TextView textDetails = (TextView) layout.findViewById(R.id.app_main_toast_text_details);
        ImageView image = (ImageView) layout.findViewById(R.id.app_main_toast_image);

        textTitle.setText(getResources().getString(R.string.status_medusas));

        ImageView iv = (ImageView) findViewById(R.id.main_content_ivMedusas);
        image.setImageDrawable(iv.getDrawable());

        textDetails.setText(medusasDetails);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }

    public void doClickOnFlags(View view) {
        textViewFlags = findViewById(R.id.main_content_tvFlags);

        String flagsDetails = (String) textViewFlags.getText();

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.app_main_toast,
                (ViewGroup) findViewById(R.id.app_main_toast));

        TextView textTitle = (TextView) layout.findViewById(R.id.app_main_toast_title);
        TextView textDetails = (TextView) layout.findViewById(R.id.app_main_toast_text_details);
        ImageView image = (ImageView) layout.findViewById(R.id.app_main_toast_image);

        textTitle.setText(getResources().getString(R.string.status_flag));

        ImageView iv = (ImageView) findViewById(R.id.main_content_ivFlags);
        image.setImageDrawable(iv.getDrawable());

        textDetails.setText(flagsDetails);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    private void setAuthenticatedUserData() {

        Intent intent = getIntent();

        Integer id = intent.getIntExtra("id", -1);
        String nombre = intent.getStringExtra("nombre");
        String apellidos = intent.getStringExtra("apellidos");
        String usuario = intent.getStringExtra("usuario");
        String email = intent.getStringExtra("email");
        String tk = intent.getStringExtra("tk");

        authUser = new TUsuarios(id, nombre, apellidos, email, usuario, tk);

    }

    public void getInformacionPlaya(String tk, String nombrePlaya) {

        PlayaWSGetByName playaWSGetByName = new PlayaWSGetByName();
        playaWSGetByName.execute(tk, nombrePlaya);

        try {

            playa = (TPlayas) playaWSGetByName.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_login_main);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        mTitle.setText(playa.getNombre());
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DatosWSGetStatusPlaya datosWSGetStatusPlaya = new DatosWSGetStatusPlaya();
        datosWSGetStatusPlaya.execute(tk, String.valueOf(playa.getId()));

        tdatos = new TDatos();

        try {
            tdatos = datosWSGetStatusPlaya.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //Log.d("DEBUG", tdatos.toString());

        //set main fotografia
        setMainFotografiaBeach(playa.getId());
        //set data to icons
        setInformacionPlaya();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tp_info_beaches_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_webcam_status_beaches){
            if (this.playa.getWebcam().toString().length() > 10){
                String url = playa.getWebcam();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }else {
                Toast.makeText(getApplicationContext(),getString(R.string.no_webcam_message), Toast.LENGTH_LONG).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public TUsuarios getAuthUser() {

        return authUser;
    }

    public TPlayas getPlaya() {
        return playa;
    }

    public String getUtmx() {
        return utmx;
    }

    public void setUtmx(String utmx) {
        this.utmx = utmx;
    }

    public String getUtmy() {
        return utmy;
    }

    public void setUtmy(String utmy) {
        this.utmy = utmy;
    }

    public String getUtmz() {
        return utmz;
    }

    public void setUtmz(String utmz) {
        this.utmz = utmz;
    }

    public String getUtmr() {
        return utmr;
    }

    public void setUtmr(String utmr) {
        this.utmr = utmr;
    }

    private TDatos getTdatos() {
        return tdatos;
    }

    private void setInformacionPlaya() {

        imageViewViento = (ImageView) findViewById(R.id.main_content_ivViento);
        imageViewOleaje = (ImageView) findViewById(R.id.main_content_ivOleaje);
        imageViewTiempo = (ImageView) findViewById(R.id.main_content_ivTiempo);
        imageViewOcupation = (ImageView) findViewById(R.id.main_content_ivOcupation);
        imageViewSand = (ImageView) findViewById(R.id.main_content_ivSand);
        imageViewWater = (ImageView) findViewById(R.id.main_content_ivWater);
        imageViewMedusa = (ImageView) findViewById(R.id.main_content_ivMedusas);
        imageViewFlags = (ImageView) findViewById(R.id.main_content_ivFlags);

        textViewViento = (TextView) findViewById(R.id.main_content_tvViento);
        textViewOleaje = (TextView) findViewById(R.id.main_content_tvOleaje);
        textViewTiempo = (TextView) findViewById(R.id.main_content_tvTiempo);
        textViewOcupation = (TextView) findViewById(R.id.main_content_tvOcupation);
        textViewWater = (TextView) findViewById(R.id.main_content_tvWater);
        textViewSand = (TextView) findViewById(R.id.main_content_tvSand);
        textViewMedusa = (TextView) findViewById(R.id.main_content_tvMedusas);
        textViewFlags = (TextView) findViewById(R.id.main_content_tvFlags);

        //viento
        if (getTdatos().getViento() == -1) {
            imageViewViento.setImageResource(R.drawable.ic_flip_flops_bn);
            textViewViento.setText(getString(R.string.no_data));
        } else if (getTdatos().getViento() == 1) {
            imageViewViento.setImageResource(R.drawable.ic_viento_brisa);
            textViewViento.setText(getString(R.string.details_wind_1));
        } else if (getTdatos().getViento() == 2) {
            imageViewViento.setImageResource(R.drawable.ic_viento_suave);
            textViewViento.setText(getString(R.string.details_wind_2));
        } else if (getTdatos().getViento() == 3) {
            imageViewViento.setImageResource(R.drawable.ic_viento_fuerte);
            textViewViento.setText(getString(R.string.details_wind_3));
        } else if (getTdatos().getViento() == 4) {
            imageViewViento.setImageResource(R.drawable.ic_viento_muyfuerte);
            textViewViento.setText(getString(R.string.details_wind_4));
        }

        //mar
        if (getTdatos().getOleaje() == -1) {
            imageViewOleaje.setImageResource(R.drawable.ic_flip_flops_bn);
            textViewOleaje.setText(getString(R.string.no_data));
        } else if (getTdatos().getOleaje() == 1) {
            imageViewOleaje.setImageResource(R.drawable.ic_mar_calma);
            textViewOleaje.setText(getString(R.string.details_sea_1));
        } else if (getTdatos().getOleaje() == 2) {
            imageViewOleaje.setImageResource(R.drawable.ic_mar_marejadilla);
            textViewOleaje.setText(getString(R.string.details_sea_2));
        } else if (getTdatos().getOleaje() == 3) {
            imageViewOleaje.setImageResource(R.drawable.ic_mar_marejada);
            textViewOleaje.setText(getString(R.string.details_sea_3));
        } else if (getTdatos().getOleaje() == 4) {
            imageViewOleaje.setImageResource(R.drawable.ic_mar_granmarejada);
            textViewOleaje.setText(getString(R.string.details_sea_4));
        }

        //tiempo
        if (getTdatos().getNubosidad() == -1) {
            imageViewTiempo.setImageResource(R.drawable.ic_flip_flops_bn);
            textViewTiempo.setText(getString(R.string.no_data));
        } else if (getTdatos().getNubosidad() == 1) {
            imageViewTiempo.setImageResource(R.drawable.ic_sol);
            textViewTiempo.setText(getString(R.string.details_weather_1));
        } else if (getTdatos().getNubosidad() == 2) {
            imageViewTiempo.setImageResource(R.drawable.ic_sol_nubes);
            textViewTiempo.setText(getString(R.string.details_weather_2));
        } else if (getTdatos().getNubosidad() == 3) {
            imageViewTiempo.setImageResource(R.drawable.ic_nubes);
            textViewTiempo.setText(getString(R.string.details_weather_3));
        } else if (getTdatos().getNubosidad() == 4) {
            imageViewTiempo.setImageResource(R.drawable.ic_lluvia);
            textViewTiempo.setText(getString(R.string.details_weather_4));
        }

        //ocupacion
        if (getTdatos().getOcupacion() == -1) {
            imageViewOcupation.setImageResource(R.drawable.ic_flip_flops_bn);
            textViewOcupation.setText(getString(R.string.no_data));
        } else if (getTdatos().getOcupacion() == 1) {
            imageViewOcupation.setImageResource(R.drawable.ic_ocupacion_empty);
            textViewOcupation.setText(getString(R.string.details_ocupacion_vacia));
        } else if (getTdatos().getOcupacion() == 2) {
            imageViewOcupation.setImageResource(R.drawable.ic_ocupacion_baja);
            textViewOcupation.setText(getString(R.string.details_ocupacion_baja));
        } else if (getTdatos().getOcupacion() == 3) {
            imageViewOcupation.setImageResource(R.drawable.ic_ocupacion_media);
            textViewOcupation.setText(getString(R.string.details_ocupacion_media));
        } else if (getTdatos().getOcupacion() == 4) {
            imageViewOcupation.setImageResource(R.drawable.ic_ocupacion_mucha);
            textViewOcupation.setText(getString(R.string.details_ocupacion_alta));
        }

        //agua
        if (getTdatos().getLimpiezaAgua() == -1) {
            imageViewWater.setImageResource(R.drawable.ic_flip_flops_bn);
            textViewWater.setText(getString(R.string.no_data));
        } else if (getTdatos().getLimpiezaAgua() == 1) {
            imageViewWater.setImageResource(R.drawable.ic_agua_cristalina);
            textViewWater.setText(getString(R.string.details_water_cristalina));
        } else if (getTdatos().getLimpiezaAgua() == 2) {
            imageViewWater.setImageResource(R.drawable.ic_agua_limpia);
            textViewWater.setText(getString(R.string.details_water_limpia));
        } else if (getTdatos().getLimpiezaAgua() == 3) {
            imageViewWater.setImageResource(R.drawable.ic_agua_sucia);
            textViewWater.setText(getString(R.string.details_water_sucia));
        } else if (getTdatos().getLimpiezaAgua() == 4) {
            imageViewWater.setImageResource(R.drawable.ic_agua_muysucia);
            textViewWater.setText(getString(R.string.details_water_muysucia));
        }

        if (getTdatos().getLimpiezaArena() == -1) {
            imageViewSand.setImageResource(R.drawable.ic_flip_flops_bn);
            textViewSand.setText(getString(R.string.no_data));
        } else if (getTdatos().getLimpiezaArena() == 1) {
            imageViewSand.setImageResource(R.drawable.ic_playa_muylimpia);
            textViewSand.setText(getString(R.string.details_sand_muylimpia));
        } else if (getTdatos().getLimpiezaArena() == 2) {
            imageViewSand.setImageResource(R.drawable.ic_playa_limpia);
            textViewSand.setText(getString(R.string.details_sand_limpia));
        } else if (getTdatos().getLimpiezaArena() == 3) {
            imageViewSand.setImageResource(R.drawable.ic_playa_sucia);
            textViewSand.setText(getString(R.string.details_sand_sucia));
        } else if (getTdatos().getLimpiezaArena() == 4) {
            imageViewSand.setImageResource(R.drawable.ic_playa_muysucia);
            textViewSand.setText(getString(R.string.details_sand_muysucia));
        }

        //medusas
        if (getTdatos().getMedusas() == -1) {
            imageViewMedusa.setImageResource(R.drawable.ic_flip_flops_bn);
            textViewMedusa.setText(getString(R.string.no_data));
        } else if (getTdatos().getMedusas() == 1) {
            imageViewMedusa.setImageResource(R.drawable.ic_tortoise);
            textViewMedusa.setText(getString(R.string.details_medusa_no));
        } else if (getTdatos().getMedusas() == 2) {
            imageViewMedusa.setImageResource(R.drawable.ic_medusa_muy_pocas);
            textViewMedusa.setText(getString(R.string.details_medusa_muy_pocas));
        } else if (getTdatos().getMedusas() == 3) {
            imageViewMedusa.setImageResource(R.drawable.ic_medusa_pocas);
            textViewMedusa.setText(getString(R.string.details_medusa_pocas));
        } else if (getTdatos().getMedusas() == 4) {
            imageViewMedusa.setImageResource(R.drawable.ic_medusa_muchas);
            textViewMedusa.setText(getString(R.string.details_medusa_muchas));
        }

        //bandera
        if (getTdatos().getBanderaMar() == -1) {
            imageViewFlags.setImageResource(R.drawable.ic_flip_flops_bn);
            textViewFlags.setText(getString(R.string.no_data));
        } else if (getTdatos().getBanderaMar() == 1) {
            imageViewFlags.setImageResource(R.drawable.ic_bandera_verde);
            textViewFlags.setText(getString(R.string.details_flag_verde));
        } else if (getTdatos().getBanderaMar() == 2) {
            imageViewFlags.setImageResource(R.drawable.ic_bandera_amarilla);
            textViewFlags.setText(getString(R.string.details_flag_amarilla));
        } else if (getTdatos().getBanderaMar() == 3) {
            imageViewFlags.setImageResource(R.drawable.ic_bandera_roja);
            textViewFlags.setText(getString(R.string.details_flag_roja));
        } else if (getTdatos().getBanderaMar() == 4) {
            imageViewFlags.setImageResource(R.drawable.ic_bandera_no);
            textViewFlags.setText(getString(R.string.details_flag_no));
        }

    }

    private void setMainFotografiaBeach(Integer idPlaya) {

        //Log.d("MAIN_FOTO", "setting photo");

        imageViewMainFotografia = findViewById(R.id.ivPhotoBeach);

        Picasso.get().load(WSConnectionData.getPROTOCOL() + "://" + WSConnectionData.getHOST() + "/images/beaches/" + String.format("%04d", getPlaya().getId()) + "/_ph_main.jpg").into(imageViewMainFotografia);

        //full screen image
        imageViewMainFotografia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListPhotosBeachActivity.class);
                intent.putExtra("tk", tk);
                intent.putExtra("playaId", getPlaya().getId());
                intent.putExtra("playaNombre", getPlaya().getNombre());
                startActivity(intent);
            }
        });
    }

    public ImageView getImageViewViento() {
        return imageViewViento;
    }

    public ImageView getImageViewOleaje() {
        return imageViewOleaje;
    }

    public ImageView getImageViewTiempo() {
        return imageViewTiempo;
    }

    public ImageView getImageViewOcupation() {
        return imageViewOcupation;
    }

    public ImageView getImageViewWater() {
        return imageViewWater;
    }

    public ImageView getImageViewSand() {
        return imageViewSand;
    }

    public ImageView getImageViewMedusa() {
        return imageViewMedusa;
    }

    public ImageView getImageViewFlags() {
        return imageViewFlags;
    }


}
