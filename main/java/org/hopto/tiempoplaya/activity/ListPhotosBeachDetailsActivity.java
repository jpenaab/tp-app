package org.hopto.tiempoplaya.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;

import org.hopto.tiempoplaya.utils.GridViewImagesPlayasAdapter;

public class ListPhotosBeachDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_photos_beach_details);

        /*GridView gridView = (GridView) findViewById(R.id.gridviewListPhotos);
        gridView.setAdapter(new GridViewImagesPlayasAdapter(this));*/

    }
}
