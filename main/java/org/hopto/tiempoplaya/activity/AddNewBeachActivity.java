package org.hopto.tiempoplaya.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.hopto.tiempoplaya.utils.ImageUtils;
import org.hopto.tiempoplaya.ws.DatosWSAddNew;
import org.hopto.tiempoplaya.ws.FotografiaWSAddNew;
import org.hopto.tiempoplaya.ws.PlayaWSAddNew;
import org.kobjects.base64.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import uk.me.jstott.jcoord.UTMRef;

public class AddNewBeachActivity extends AppCompatActivity {

    //fotos
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String mCurrentPhotoPath;
    private File photoFile;
    private String strPhotoBase64;

    //flag foto clicked
    private boolean fotoClicked = false;

    private String tk;
    private Integer userId;
    private String userUtmx;
    private String userUtmy;
    private String userUtmz;
    private String userUtmr;

    private UTMRef utmRef;

    DecimalFormat DF = new DecimalFormat("###,###.##");

    //widgets
    private EditText editTextName;
    private EditText editTextCoordenadas;
    private EditText editTextMunicipio;
    private EditText editTextCP;
    private EditText editTextPais;

    private ImageView playaImageView;

    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_beach);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_login_main);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        mTitle.setText(getResources().getString(R.string.new_beach_title));
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tk = getIntent().getStringExtra("tk");
        userId = getIntent().getIntExtra("userId",-1);
        userUtmx = getIntent().getStringExtra("utmx");
        userUtmy = getIntent().getStringExtra("utmy");
        userUtmz = getIntent().getStringExtra("utmz");
        userUtmr = getIntent().getStringExtra("utmr");

        editTextName = findViewById(R.id.et_add_new_beach_name);
        editTextCoordenadas = findViewById(R.id.et_add_new_beach_utm);
        editTextMunicipio = findViewById(R.id.et_add_new_beach_municipio);
        editTextCP = findViewById(R.id.et_add_new_beach_cp);
        editTextPais = findViewById(R.id.et_add_new_beach_pais);

        // Photo
        playaImageView = findViewById(R.id.ivAddNewPlayaTakePhoto);

        setFillUpEditText(userUtmx, userUtmy, userUtmz);
    }

    public void setFillUpEditText(String userUtmx, String userUtmy, String userUtmz){

        utmRef = new UTMRef(Double.valueOf(userUtmx), Double.valueOf(userUtmy), userUtmr.charAt(0), Integer.valueOf(userUtmz));
        uk.me.jstott.jcoord.LatLng l = utmRef.toLatLng();
        Double latitude = l.getLat();
        Double longitude = l.getLng();

        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        List<Address> matches = null;
        try {
            matches = geoCoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Address bestMatch = (matches.isEmpty() ? null : matches.get(0));

        /*
        System.out.println(bestMatch.getAddressLine(0));
        System.out.println(bestMatch.getCountryCode());
        System.out.println(bestMatch.getPostalCode());
        System.out.println(bestMatch.getLocality());
        System.out.println(bestMatch.getAdminArea());
        System.out.println(bestMatch.getFeatureName());
        */

        editTextCoordenadas.setText(DF.format(utmRef.getEasting()) + " - " + DF.format(utmRef.getNorthing()) + " -  " + userUtmz + userUtmr.charAt(0));
        editTextMunicipio.setText(bestMatch.getLocality());
        editTextCP.setText(bestMatch.getPostalCode());
        editTextPais.setText(bestMatch.getCountryCode());
    }

    public void doSendNewPlayaData(View view){

        Boolean isAllDataFillUp = false;

        progressBar = (ProgressBar) findViewById(R.id.progressBarIndeterminateNewPlaya);
        progressBar.setMax(10);

        String nameBeach = editTextName.getText().toString();
        String localityBeach = editTextMunicipio.getText().toString();
        String cpBeach = editTextCP.getText().toString();
        String paisBeach = editTextPais.getText().toString();

        if (!nameBeach.isEmpty()){
            if (!localityBeach.isEmpty()){
                if (!cpBeach.isEmpty()){
                    if(!paisBeach.isEmpty()){
                        isAllDataFillUp = true;
                    }
                }
            }
        }


        if (isAllDataFillUp) {

            PlayaWSAddNew playaWSAddNew = null;

            if (isFotoClicked()) {

                playaWSAddNew = new PlayaWSAddNew(AddNewBeachActivity.this, progressBar);
                playaWSAddNew.execute(tk, DF.format(getUtmRef().getEasting()), DF.format(getUtmRef().getNorthing()), String.valueOf(getUtmRef().getLngZone()), String.valueOf(getUtmRef().getLatZone()), nameBeach, localityBeach, cpBeach, paisBeach);

                Integer newPlayaId = null;

                try {

                    newPlayaId = (Integer) playaWSAddNew.get();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                FotografiaWSAddNew fotografiaWSAddNew = new FotografiaWSAddNew(AddNewBeachActivity.this, progressBar);
                fotografiaWSAddNew.execute(tk, userId, newPlayaId, getStrPhotoBase64());

            } else {

                playaWSAddNew = new PlayaWSAddNew(AddNewBeachActivity.this, progressBar);
                playaWSAddNew.execute(tk, DF.format(getUtmRef().getEasting()), DF.format(getUtmRef().getNorthing()), String.valueOf(getUtmRef().getLngZone()), String.valueOf(getUtmRef().getLatZone()), nameBeach, localityBeach, cpBeach, paisBeach);
            }

        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.left_info_to_send), Toast.LENGTH_LONG).show();
        }

        finish();
    }

    public UTMRef getUtmRef() {
        return utmRef;
    }

    public void setUtmRef(UTMRef utmRef) {
        this.utmRef = utmRef;
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

    public String getPhotoFilePath() {
        return photoFile.getAbsolutePath();
    }
    private String getmCurrentPhotoPath() {
        return mCurrentPhotoPath;
    }

    private byte[] getBytesPhoto(String imagePath){

        Bitmap bm = ImageUtils.getReducedBitmapImages(imagePath, 1280, 800);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 70, bao);
        byte[] ba = bao.toByteArray();

        return ba;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Bitmap imageBitmap = ImageUtils.getReducedBitmapImages(this.getPhotoFilePath(), 1280, 800);
            playaImageView.setImageBitmap(imageBitmap);

            strPhotoBase64 = Base64.encode(getBytesPhoto(getmCurrentPhotoPath()));
        }
    }

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

    public boolean isFotoClicked() {
        return fotoClicked;
    }

    public void setFotoClicked(boolean fotoClicked) {
        this.fotoClicked = fotoClicked;
    }

    public String getStrPhotoBase64() {
        return strPhotoBase64;
    }

}
