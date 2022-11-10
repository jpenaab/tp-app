package org.hopto.tiempoplaya.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.hopto.tiempoplaya.modelo.TFotografias;
import org.hopto.tiempoplaya.modelo.TPlayas;
import org.hopto.tiempoplaya.modelo.TUsuarios;
import org.hopto.tiempoplaya.utils.ImageUtils;
import org.hopto.tiempoplaya.ws.DatosWSAddNew;
import org.hopto.tiempoplaya.ws.FotografiaWSAddNew;
import org.kobjects.base64.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class TPFormDataActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String mCurrentPhotoPath;
    private File photoFile;
    private String strPhotoBase64;

    //flag foto clicked
    private boolean fotoClicked = false;

    //gps client
    String utmx, utmy, utmz;

    private TPlayas playas;
    private TUsuarios usuario;

    //Values
    private int vientoValue = 0;
    private int marValue = 0;
    private int tiempoValue = 0;
    private int ocupacionValue = 0;
    private int aguaValue = 0;
    private int arenaValue = 0;
    private int banderaValue = 0;
    private int medusasValue = 0;

    // Viento
    private LinearLayout linearLayoutWindBrisa;
    private LinearLayout linearLayoutWindSuave;
    private LinearLayout linearLayoutWindFuerte;
    private LinearLayout linearLayoutWindTornado;

    // Mar
    private LinearLayout linearLayoutSeaCalma;
    private LinearLayout linearLayoutSeaMarejadilla;
    private LinearLayout linearLayoutSeaMarejada;
    private LinearLayout linearLayoutSeaMaremoto;

    // Tiempo
    private LinearLayout linearLayoutWeatherSol;
    private LinearLayout linearLayoutWeatherSolNubes;
    private LinearLayout linearLayoutWeatherNubes;
    private LinearLayout linearLayoutWeatherLluvia;

    // Ocupacion
    private LinearLayout linearLayoutOcupationVacia;
    private LinearLayout linearLayoutOcupationBaja;
    private LinearLayout linearLayoutOcupationMedia;
    private LinearLayout linearLayoutOcupationAlta;

    // Agua
    private LinearLayout linearLayoutWaterCristalina;
    private LinearLayout linearLayoutWaterLimpia;
    private LinearLayout linearLayoutWaterSucia;
    private LinearLayout linearLayoutWaterMuySucia;

    // Arena
    private LinearLayout linearLayoutSandLimpia;
    private LinearLayout linearLayoutSandMedia;
    private LinearLayout linearLayoutSandSucia;
    private LinearLayout linearLayoutSandMuySucia;

    // Banderas
    private LinearLayout linearLayoutFlagVerde;
    private LinearLayout linearLayoutFlagAmarilla;
    private LinearLayout linearLayoutFlagRoja;
    private LinearLayout linearLayoutFlagSinBandera;

    //Medusas
    private LinearLayout linearLayoutMedusasNo;
    private LinearLayout linearLayoutMedusasMuyPocas;
    private LinearLayout linearLayoutMedusasPocas;
    private LinearLayout linearLayoutMedusasMuchas;

    // Photo
    private ImageView playaImageView;

    // Progress bar
    private ProgressBar progressBarPhoto;
    private ProgressBar progressBarData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_tpform_data);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_login_main);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        mTitle.setText(getResources().getString(R.string.form_data_beach));
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setPlayas((TPlayas) getIntent().getSerializableExtra("TPlayas"));
        setUsuario((TUsuarios) getIntent().getSerializableExtra("TUsuarios"));
        utmx = getIntent().getStringExtra("utmx");
        utmy = getIntent().getStringExtra("utmy");
        utmz = getIntent().getStringExtra("utmz");

        // init

        linearLayoutWindBrisa = findViewById(R.id.lLayoutWindBrisa);
        linearLayoutWindSuave = findViewById(R.id.lLayoutWindSuave);
        linearLayoutWindFuerte = findViewById(R.id.lLayoutWindFuerte);
        linearLayoutWindTornado = findViewById(R.id.lLayoutWindTornado);

        linearLayoutSeaCalma = findViewById(R.id.lLayoutSeaCalma);
        linearLayoutSeaMarejadilla = findViewById(R.id.lLayoutSeaMarejadilla);
        linearLayoutSeaMarejada = findViewById(R.id.lLayoutSeaMarejada);
        linearLayoutSeaMaremoto = findViewById(R.id.lLayoutSeaMaremoto);

        linearLayoutWeatherSol = findViewById(R.id.lLayoutWeatherSol);
        linearLayoutWeatherSolNubes = findViewById(R.id.lLayoutWeatherSolNubes);
        linearLayoutWeatherNubes = findViewById(R.id.lLayoutWeatherNubes);
        linearLayoutWeatherLluvia = findViewById(R.id.lLayoutWeatherLluvia);

        linearLayoutOcupationVacia = findViewById(R.id.lLayoutOcupationVacia);
        linearLayoutOcupationBaja = findViewById(R.id.lLayoutOcupationBaja);
        linearLayoutOcupationMedia = findViewById(R.id.lLayoutOcupationMedia);
        linearLayoutOcupationAlta = findViewById(R.id.lLayoutOcupationAlta);

        linearLayoutWaterCristalina = findViewById(R.id.lLayoutWaterCristalina);
        linearLayoutWaterLimpia = findViewById(R.id.lLayoutWaterLimpia);
        linearLayoutWaterSucia = findViewById(R.id.lLayoutWaterSucia);
        linearLayoutWaterMuySucia = findViewById(R.id.lLayoutWaterMuySucia);

        linearLayoutSandLimpia = findViewById(R.id.lLayoutSandLimpia);
        linearLayoutSandMedia = findViewById(R.id.lLayoutSandMedia);
        linearLayoutSandSucia = findViewById(R.id.lLayoutSandSucia);
        linearLayoutSandMuySucia = findViewById(R.id.lLayoutSandMuySucia);

        linearLayoutMedusasNo = findViewById(R.id.lLayoutMedusasNo);
        linearLayoutMedusasMuyPocas = findViewById(R.id.lLayoutMedusasMuyPocas);
        linearLayoutMedusasPocas = findViewById(R.id.lLayoutMedusasPocas);
        linearLayoutMedusasMuchas = findViewById(R.id.lLayoutMedusasMuchas);

        linearLayoutFlagVerde = findViewById(R.id.lLayoutFlagVerde);
        linearLayoutFlagAmarilla = findViewById(R.id.lLayoutFlagAmarilla);
        linearLayoutFlagRoja = findViewById(R.id.lLayoutFlagRoja);
        linearLayoutFlagSinBandera = findViewById(R.id.lLayoutFlagSinBandera);

        // Photo
        playaImageView = findViewById(R.id.ivPlayaTakePhoto);

        //progress bar
        progressBarData = (ProgressBar) findViewById(R.id.progressBarIndeterminateDataFormData);
        progressBarPhoto = (ProgressBar) findViewById(R.id.progressBarIndeterminateDataFormFotografias);
    }

    public void doSelectWind(View view) {

        Integer tag = Integer.parseInt((String) view.getTag());

        switch (tag) {
            case 101:
                linearLayoutWindBrisa.setSelected(true);
                linearLayoutWindBrisa.setBackground(getResources().getDrawable(R.drawable.selected_item));
                setVientoValue(1);
                linearLayoutWindSuave.setSelected(false);
                linearLayoutWindSuave.setBackground(null);
                linearLayoutWindFuerte.setSelected(false);
                linearLayoutWindFuerte.setBackground(null);
                linearLayoutWindTornado.setSelected(false);
                linearLayoutWindTornado.setBackground(null);

                break;
            case 102:

                linearLayoutWindSuave.setSelected(true);
                linearLayoutWindSuave.setBackground(getResources().getDrawable(R.drawable.selected_item));
                setVientoValue(2);
                linearLayoutWindBrisa.setSelected(false);
                linearLayoutWindBrisa.setBackground(null);
                linearLayoutWindFuerte.setSelected(false);
                linearLayoutWindFuerte.setBackground(null);
                linearLayoutWindTornado.setSelected(false);
                linearLayoutWindTornado.setBackground(null);

                break;
            case 103:

                linearLayoutWindFuerte.setSelected(true);
                linearLayoutWindFuerte.setBackground(getResources().getDrawable(R.drawable.selected_item));
                setVientoValue(3);
                linearLayoutWindSuave.setSelected(false);
                linearLayoutWindSuave.setBackground(null);
                linearLayoutWindBrisa.setSelected(false);
                linearLayoutWindBrisa.setBackground(null);
                linearLayoutWindTornado.setSelected(false);
                linearLayoutWindTornado.setBackground(null);

                break;
            case 104:

                linearLayoutWindTornado.setSelected(true);
                linearLayoutWindTornado.setBackground(getResources().getDrawable(R.drawable.selected_item));
                setVientoValue(4);
                linearLayoutWindSuave.setSelected(false);
                linearLayoutWindSuave.setBackground(null);
                linearLayoutWindFuerte.setSelected(false);
                linearLayoutWindFuerte.setBackground(null);
                linearLayoutWindBrisa.setSelected(false);
                linearLayoutWindBrisa.setBackground(null);

                break;
            default:
        }
    }

    public void doSelectSea(View view) {

        Integer tag = Integer.parseInt((String) view.getTag());

        switch (tag) {
            case 201:
                linearLayoutSeaCalma.setSelected(true);
                linearLayoutSeaCalma.setBackground(getResources().getDrawable(R.drawable.selected_item));
                setMarValue(1);
                linearLayoutSeaMarejadilla.setSelected(false);
                linearLayoutSeaMarejadilla.setBackground(null);
                linearLayoutSeaMarejada.setSelected(false);
                linearLayoutSeaMarejada.setBackground(null);
                linearLayoutSeaMaremoto.setSelected(false);
                linearLayoutSeaMaremoto.setBackground(null);

                break;
            case 202:

                linearLayoutSeaMarejadilla.setSelected(true);
                linearLayoutSeaMarejadilla.setBackground(getResources().getDrawable(R.drawable.selected_item));
                setMarValue(2);
                linearLayoutSeaCalma.setSelected(false);
                linearLayoutSeaCalma.setBackground(null);
                linearLayoutSeaMarejada.setSelected(false);
                linearLayoutSeaMarejada.setBackground(null);
                linearLayoutSeaMaremoto.setSelected(false);
                linearLayoutSeaMaremoto.setBackground(null);

                break;
            case 203:

                linearLayoutSeaMarejada.setSelected(true);
                linearLayoutSeaMarejada.setBackground(getResources().getDrawable(R.drawable.selected_item));
                setMarValue(3);
                linearLayoutSeaCalma.setSelected(false);
                linearLayoutSeaCalma.setBackground(null);
                linearLayoutSeaMarejadilla.setSelected(false);
                linearLayoutSeaMarejadilla.setBackground(null);
                linearLayoutSeaMaremoto.setSelected(false);
                linearLayoutSeaMaremoto.setBackground(null);

                break;
            case 204:

                linearLayoutSeaMaremoto.setSelected(true);
                linearLayoutSeaMaremoto.setBackground(getResources().getDrawable(R.drawable.selected_item));
                setMarValue(4);
                linearLayoutSeaMarejadilla.setSelected(false);
                linearLayoutSeaMarejadilla.setBackground(null);
                linearLayoutSeaMarejada.setSelected(false);
                linearLayoutSeaMarejada.setBackground(null);
                linearLayoutSeaCalma.setSelected(false);
                linearLayoutSeaCalma.setBackground(null);

                break;
            default:
        }

    }

    public void doSelectWeather(View view) {

        Integer tag = Integer.parseInt((String) view.getTag());

        switch (tag) {
            case 301:
                linearLayoutWeatherSol.setSelected(true);
                linearLayoutWeatherSol.setBackground(getResources().getDrawable(R.drawable.selected_item));
                setTiempoValue(1);
                linearLayoutWeatherSolNubes.setSelected(false);
                linearLayoutWeatherSolNubes.setBackground(null);
                linearLayoutWeatherNubes.setSelected(false);
                linearLayoutWeatherNubes.setBackground(null);
                linearLayoutWeatherLluvia.setSelected(false);
                linearLayoutWeatherLluvia.setBackground(null);

                break;
            case 302:

                linearLayoutWeatherSolNubes.setSelected(true);
                linearLayoutWeatherSolNubes.setBackground(getResources().getDrawable(R.drawable.selected_item));
                setTiempoValue(2);
                linearLayoutWeatherSol.setSelected(false);
                linearLayoutWeatherSol.setBackground(null);
                linearLayoutWeatherNubes.setSelected(false);
                linearLayoutWeatherNubes.setBackground(null);
                linearLayoutWeatherLluvia.setSelected(false);
                linearLayoutWeatherLluvia.setBackground(null);

                break;
            case 303:

                linearLayoutWeatherNubes.setSelected(true);
                linearLayoutWeatherNubes.setBackground(getResources().getDrawable(R.drawable.selected_item));
                setTiempoValue(3);
                linearLayoutWeatherSol.setSelected(false);
                linearLayoutWeatherSol.setBackground(null);
                linearLayoutWeatherSolNubes.setSelected(false);
                linearLayoutWeatherSolNubes.setBackground(null);
                linearLayoutWeatherLluvia.setSelected(false);
                linearLayoutWeatherLluvia.setBackground(null);

                break;
            case 304:

                linearLayoutWeatherLluvia.setSelected(true);
                linearLayoutWeatherLluvia.setBackground(getResources().getDrawable(R.drawable.selected_item));
                setTiempoValue(4);
                linearLayoutWeatherSolNubes.setSelected(false);
                linearLayoutWeatherSolNubes.setBackground(null);
                linearLayoutWeatherNubes.setSelected(false);
                linearLayoutWeatherNubes.setBackground(null);
                linearLayoutWeatherSol.setSelected(false);
                linearLayoutWeatherSol.setBackground(null);

                break;
            default:
        }

    }

    public void doSelectOcupation(View view) {

        Integer tag = Integer.parseInt((String) view.getTag());

        switch (tag) {
            case 401:
                linearLayoutOcupationVacia.setSelected(true);
                linearLayoutOcupationVacia.setBackground(getResources().getDrawable(R.drawable.selected_item));
                setOcupacionValue(1);
                linearLayoutOcupationBaja.setSelected(false);
                linearLayoutOcupationBaja.setBackground(null);
                linearLayoutOcupationMedia.setSelected(false);
                linearLayoutOcupationMedia.setBackground(null);
                linearLayoutOcupationAlta.setSelected(false);
                linearLayoutOcupationAlta.setBackground(null);

                break;
            case 402:

                linearLayoutOcupationBaja.setSelected(true);
                linearLayoutOcupationBaja.setBackground(getResources().getDrawable(R.drawable.selected_item));
                setOcupacionValue(2);
                linearLayoutOcupationVacia.setSelected(false);
                linearLayoutOcupationVacia.setBackground(null);
                linearLayoutOcupationMedia.setSelected(false);
                linearLayoutOcupationMedia.setBackground(null);
                linearLayoutOcupationAlta.setSelected(false);
                linearLayoutOcupationAlta.setBackground(null);

                break;
            case 403:

                linearLayoutOcupationMedia.setSelected(true);
                linearLayoutOcupationMedia.setBackground(getResources().getDrawable(R.drawable.selected_item));
                setOcupacionValue(3);
                linearLayoutOcupationVacia.setSelected(false);
                linearLayoutOcupationVacia.setBackground(null);
                linearLayoutOcupationBaja.setSelected(false);
                linearLayoutOcupationBaja.setBackground(null);
                linearLayoutOcupationAlta.setSelected(false);
                linearLayoutOcupationAlta.setBackground(null);

                break;
            case 404:

                linearLayoutOcupationAlta.setSelected(true);
                linearLayoutOcupationAlta.setBackground(getResources().getDrawable(R.drawable.selected_item));
                setOcupacionValue(4);
                linearLayoutOcupationBaja.setSelected(false);
                linearLayoutOcupationBaja.setBackground(null);
                linearLayoutOcupationMedia.setSelected(false);
                linearLayoutOcupationMedia.setBackground(null);
                linearLayoutOcupationVacia.setSelected(false);
                linearLayoutOcupationVacia.setBackground(null);

                break;
            default:
        }

    }

    public void doSelectWater(View view) {

        Integer tag = Integer.parseInt((String) view.getTag());

        switch (tag) {
            case 501:
                linearLayoutWaterCristalina.setSelected(true);
                linearLayoutWaterCristalina.setBackground(getResources().getDrawable(R.drawable.selected_item));
                setAguaValue(1);
                linearLayoutWaterLimpia.setSelected(false);
                linearLayoutWaterLimpia.setBackground(null);
                linearLayoutWaterSucia.setSelected(false);
                linearLayoutWaterSucia.setBackground(null);
                linearLayoutWaterMuySucia.setSelected(false);
                linearLayoutWaterMuySucia.setBackground(null);

                break;
            case 502:

                linearLayoutWaterLimpia.setSelected(true);
                linearLayoutWaterLimpia.setBackground(getResources().getDrawable(R.drawable.selected_item));
                setAguaValue(2);
                linearLayoutWaterCristalina.setSelected(false);
                linearLayoutWaterCristalina.setBackground(null);
                linearLayoutWaterSucia.setSelected(false);
                linearLayoutWaterSucia.setBackground(null);
                linearLayoutWaterMuySucia.setSelected(false);
                linearLayoutWaterMuySucia.setBackground(null);

                break;
            case 503:

                linearLayoutWaterSucia.setSelected(true);
                linearLayoutWaterSucia.setBackground(getResources().getDrawable(R.drawable.selected_item));
                setAguaValue(3);
                linearLayoutWaterCristalina.setSelected(false);
                linearLayoutWaterCristalina.setBackground(null);
                linearLayoutWaterLimpia.setSelected(false);
                linearLayoutWaterLimpia.setBackground(null);
                linearLayoutWaterMuySucia.setSelected(false);
                linearLayoutWaterMuySucia.setBackground(null);

                break;
            case 504:

                linearLayoutWaterMuySucia.setSelected(true);
                linearLayoutWaterMuySucia.setBackground(getResources().getDrawable(R.drawable.selected_item));
                setAguaValue(4);
                linearLayoutWaterCristalina.setSelected(false);
                linearLayoutWaterCristalina.setBackground(null);
                linearLayoutWaterLimpia.setSelected(false);
                linearLayoutWaterLimpia.setBackground(null);
                linearLayoutWaterSucia.setSelected(false);
                linearLayoutWaterSucia.setBackground(null);

                break;
            default:
        }

    }

    public void doSelectArena(View view) {

        Integer tag = Integer.parseInt((String) view.getTag());

        switch (tag) {
            case 601:
                linearLayoutSandLimpia.setSelected(true);
                linearLayoutSandLimpia.setBackground(getResources().getDrawable(R.drawable.selected_item));
                setArenaValue(1);
                linearLayoutSandMedia.setSelected(false);
                linearLayoutSandMedia.setBackground(null);
                linearLayoutSandSucia.setSelected(false);
                linearLayoutSandSucia.setBackground(null);
                linearLayoutSandMuySucia.setSelected(false);
                linearLayoutSandMuySucia.setBackground(null);

                break;
            case 602:

                linearLayoutSandMedia.setSelected(true);
                linearLayoutSandMedia.setBackground(getResources().getDrawable(R.drawable.selected_item));
                setArenaValue(2);
                linearLayoutSandLimpia.setSelected(false);
                linearLayoutSandLimpia.setBackground(null);
                linearLayoutSandSucia.setSelected(false);
                linearLayoutSandSucia.setBackground(null);
                linearLayoutSandMuySucia.setSelected(false);
                linearLayoutSandMuySucia.setBackground(null);

                break;
            case 603:

                linearLayoutSandSucia.setSelected(true);
                linearLayoutSandSucia.setBackground(getResources().getDrawable(R.drawable.selected_item));
                setArenaValue(3);
                linearLayoutSandLimpia.setSelected(false);
                linearLayoutSandLimpia.setBackground(null);
                linearLayoutSandMedia.setSelected(false);
                linearLayoutSandMedia.setBackground(null);
                linearLayoutSandMuySucia.setSelected(false);
                linearLayoutSandMuySucia.setBackground(null);

                break;
            case 604:

                linearLayoutSandMuySucia.setSelected(true);
                linearLayoutSandMuySucia.setBackground(getResources().getDrawable(R.drawable.selected_item));
                setArenaValue(4);
                linearLayoutSandMedia.setSelected(false);
                linearLayoutSandMedia.setBackground(null);
                linearLayoutSandSucia.setSelected(false);
                linearLayoutSandSucia.setBackground(null);
                linearLayoutSandLimpia.setSelected(false);
                linearLayoutSandLimpia.setBackground(null);

                break;
            default:
        }

    }

    public void doSelectMedusas(View view) {

        Integer tag = Integer.parseInt((String) view.getTag());

        switch (tag) {
            case 801:
                linearLayoutMedusasNo.setSelected(true);
                linearLayoutMedusasNo.setBackground(getResources().getDrawable(R.drawable.selected_item));
                setMedusasValue(1);
                linearLayoutMedusasMuyPocas.setSelected(false);
                linearLayoutMedusasMuyPocas.setBackground(null);
                linearLayoutMedusasPocas.setSelected(false);
                linearLayoutMedusasPocas.setBackground(null);
                linearLayoutMedusasMuchas.setSelected(false);
                linearLayoutMedusasMuchas.setBackground(null);

                break;
            case 802:

                linearLayoutMedusasMuyPocas.setSelected(true);
                linearLayoutMedusasMuyPocas.setBackground(getResources().getDrawable(R.drawable.selected_item));
                setMedusasValue(2);
                linearLayoutMedusasNo.setSelected(false);
                linearLayoutMedusasNo.setBackground(null);
                linearLayoutMedusasPocas.setSelected(false);
                linearLayoutMedusasPocas.setBackground(null);
                linearLayoutMedusasMuchas.setSelected(false);
                linearLayoutMedusasMuchas.setBackground(null);

                break;
            case 803:

                linearLayoutMedusasPocas.setSelected(true);
                linearLayoutMedusasPocas.setBackground(getResources().getDrawable(R.drawable.selected_item));
                setMedusasValue(3);
                linearLayoutMedusasNo.setSelected(false);
                linearLayoutMedusasNo.setBackground(null);
                linearLayoutMedusasMuyPocas.setSelected(false);
                linearLayoutMedusasMuyPocas.setBackground(null);
                linearLayoutMedusasMuchas.setSelected(false);
                linearLayoutMedusasMuchas.setBackground(null);

                break;
            case 804:

                linearLayoutMedusasMuchas.setSelected(true);
                linearLayoutMedusasMuchas.setBackground(getResources().getDrawable(R.drawable.selected_item));
                setMedusasValue(4);
                linearLayoutMedusasMuyPocas.setSelected(false);
                linearLayoutMedusasMuyPocas.setBackground(null);
                linearLayoutMedusasPocas.setSelected(false);
                linearLayoutMedusasPocas.setBackground(null);
                linearLayoutMedusasNo.setSelected(false);
                linearLayoutMedusasNo.setBackground(null);

                break;
            default:
        }

    }

    public void doSelectFlag(View view) {

        Integer tag = Integer.parseInt((String) view.getTag());

        switch (tag) {
            case 701:
                linearLayoutFlagVerde.setSelected(true);
                linearLayoutFlagVerde.setBackground(getResources().getDrawable(R.drawable.selected_item));
                setBanderaValue(1);
                linearLayoutFlagAmarilla.setSelected(false);
                linearLayoutFlagAmarilla.setBackground(null);
                linearLayoutFlagRoja.setSelected(false);
                linearLayoutFlagRoja.setBackground(null);
                linearLayoutFlagSinBandera.setSelected(false);
                linearLayoutFlagSinBandera.setBackground(null);
                break;
            case 702:
                linearLayoutFlagAmarilla.setSelected(true);
                linearLayoutFlagAmarilla.setBackground(getResources().getDrawable(R.drawable.selected_item));
                setBanderaValue(2);
                linearLayoutFlagVerde.setSelected(false);
                linearLayoutFlagVerde.setBackground(null);
                linearLayoutFlagRoja.setSelected(false);
                linearLayoutFlagRoja.setBackground(null);
                linearLayoutFlagSinBandera.setSelected(false);
                linearLayoutFlagSinBandera.setBackground(null);

                break;
            case 703:
                linearLayoutFlagRoja.setSelected(true);
                linearLayoutFlagRoja.setBackground(getResources().getDrawable(R.drawable.selected_item));
                setBanderaValue(3);
                linearLayoutFlagVerde.setSelected(false);
                linearLayoutFlagVerde.setBackground(null);
                linearLayoutFlagAmarilla.setSelected(false);
                linearLayoutFlagAmarilla.setBackground(null);
                linearLayoutFlagSinBandera.setSelected(false);
                linearLayoutFlagSinBandera.setBackground(null);

                break;
            case 704:
                linearLayoutFlagSinBandera.setSelected(true);
                linearLayoutFlagSinBandera.setBackground(getResources().getDrawable(R.drawable.selected_item));
                setBanderaValue(4);
                linearLayoutFlagVerde.setSelected(false);
                linearLayoutFlagVerde.setBackground(null);
                linearLayoutFlagRoja.setSelected(false);
                linearLayoutFlagRoja.setBackground(null);
                linearLayoutFlagAmarilla.setSelected(false);
                linearLayoutFlagAmarilla.setBackground(null);

                break;
            default:
                break;


        }

    }

    public void doTakePhoto(View view) {

        setFotoClicked(true);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.tiempoyplaya.android.fileprovider",
                        photoFile);

                takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }

    }

    private byte[] getBytesPhoto(String imagePath){

        //Bitmap bm = BitmapFactory.decodeFile(imagePath);
        Bitmap bm = ImageUtils.getReducedBitmapImages(imagePath, 1280, 800);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 70, bao);
        byte[] ba = bao.toByteArray();

        return ba;

    }

    public String getStrPhotoBase64() {
        return strPhotoBase64;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            //Bitmap imageBitmap = BitmapFactory.decodeFile(this.getPhotoFilePath());
            Bitmap imageBitmap = ImageUtils.getReducedBitmapImages(this.getPhotoFilePath(), 1280, 800);
            playaImageView.setImageBitmap(imageBitmap);

            strPhotoBase64 = Base64.encode(getBytesPhoto(getmCurrentPhotoPath()));
        }
    }


    /**
     * create photo file in external memory
     *
     * @return
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,      /* prefix */
                ".jpg",      /* suffix */
                storageDir          /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public String getPhotoFilePath() {
        return photoFile.getAbsolutePath();
    }

    public void doSendData(View view) {

        Boolean isAllDataFillUp = false;

        if (getVientoValue() != 0) {
            //System.out.println(getVientoValue());

            if (getMarValue() != 0) {
                //System.out.println(getMarValue());

                if (getTiempoValue() != 0) {
                    //System.out.println(getTiempoValue());

                    if (getOcupacionValue() != 0) {
                        //System.out.println(getOcupacionValue());

                        if (getAguaValue() != 0) {
                            //System.out.println(getAguaValue());

                            if (getArenaValue() != 0) {
                                //System.out.println(getArenaValue());

                                if (getBanderaValue() != 0) {
                                    //System.out.println(getBanderaValue());

                                    if (getMedusasValue() != 0) {
                                        //System.out.println(getMedusasValue());

                                        isAllDataFillUp = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


        if (isAllDataFillUp) {

            //System.out.println("DEBUG: USER: " + getUsuario());
            //System.out.println("DEBUG: PLAYA: " + getPlayas());

            progressBarData = (ProgressBar) findViewById(R.id.progressBarIndeterminateDataFormData);
            progressBarPhoto = (ProgressBar) findViewById(R.id.progressBarIndeterminateDataFormFotografias);


            DatosWSAddNew datosWSAddNew = null;

            if (isFotoClicked()) {

                //TFotografias fotografia = new TFotografias();

                FotografiaWSAddNew fotografiaWSAddNew = new FotografiaWSAddNew(TPFormDataActivity.this, progressBarPhoto);
                fotografiaWSAddNew.execute(getUsuario().getTk(), getUsuario().getId(), getPlayas().getId(), getStrPhotoBase64());

                Integer photoId = null;

                try {

                    photoId = (Integer) fotografiaWSAddNew.get();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                datosWSAddNew = new DatosWSAddNew(TPFormDataActivity.this, this.progressBarData);
                datosWSAddNew.execute(getUsuario().getTk(), getUsuario().getId(), getPlayas().getId(), photoId, utmx, utmy, utmz, this.getVientoValue(), this.getMarValue(), this.getTiempoValue(), this.getOcupacionValue(), this.getAguaValue(), this.getArenaValue(), this.getBanderaValue(), this.getMedusasValue());
            } else {

                //progressBar.setVisibility(View.VISIBLE);
                //progressBar.setProgress(0);
                datosWSAddNew = new DatosWSAddNew(TPFormDataActivity.this, this.progressBarData);
                datosWSAddNew.execute(getUsuario().getTk(), getUsuario().getId(), getPlayas().getId(), -1, utmx, utmy, utmz, this.getVientoValue(), this.getMarValue(), this.getTiempoValue(), this.getOcupacionValue(), this.getAguaValue(), this.getArenaValue(), this.getBanderaValue(), getMedusasValue());
            }

        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.left_info_to_send), Toast.LENGTH_LONG).show();
        }

        finish();
    }

    private String getmCurrentPhotoPath() {
        return mCurrentPhotoPath;
    }

    public int getVientoValue() {
        return vientoValue;
    }

    public void setVientoValue(int vientoValue) {
        this.vientoValue = vientoValue;
    }

    public int getMarValue() {
        return marValue;
    }

    public void setMarValue(int marValue) {
        this.marValue = marValue;
    }

    public int getTiempoValue() {
        return tiempoValue;
    }

    public void setTiempoValue(int tiempoValue) {
        this.tiempoValue = tiempoValue;
    }

    public int getOcupacionValue() {
        return ocupacionValue;
    }

    public void setOcupacionValue(int ocupacionValue) {
        this.ocupacionValue = ocupacionValue;
    }

    public int getAguaValue() {
        return aguaValue;
    }

    public void setAguaValue(int aguaValue) {
        this.aguaValue = aguaValue;
    }

    public int getArenaValue() {
        return arenaValue;
    }

    public void setArenaValue(int arenaValue) {
        this.arenaValue = arenaValue;
    }

    public int getBanderaValue() {
        return banderaValue;
    }

    public void setBanderaValue(int banderaValue) {
        this.banderaValue = banderaValue;
    }

    public int getMedusasValue() {
        return medusasValue;
    }

    public void setMedusasValue(int medusasValue) {
        this.medusasValue = medusasValue;
    }

    public TPlayas getPlayas() {
        return playas;
    }

    public void setPlayas(TPlayas playas) {
        this.playas = playas;
    }

    public TUsuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(TUsuarios usuario) {
        this.usuario = usuario;
    }

    public boolean isFotoClicked() {
        return fotoClicked;
    }

    public void setFotoClicked(boolean fotoClicked) {
        this.fotoClicked = fotoClicked;
    }
}
