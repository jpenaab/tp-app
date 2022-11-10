package org.hopto.tiempoplaya.activity;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.hopto.tiempoplaya.utils.GridViewImagesPlayasAdapter;
import org.hopto.tiempoplaya.ws.FotografiaWSGetPlaya;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ListPhotosBeachActivity extends AppCompatActivity {

    private GridView gridView;

    private String tk;
    private Integer playaId;
    private String playaName;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_photos_beach);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_login_main);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        mTitle.setText(getResources().getString(R.string.beach_photos));
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tk = getIntent().getStringExtra("tk");
        playaId = getIntent().getIntExtra("playaId", -1);
        playaName = getIntent().getStringExtra("playaNombre");

        progressBar = findViewById(R.id.progressBarIndeterminateGetPhotosPlaya);

        List<String> l = new ArrayList<String>();
        try {

            FotografiaWSGetPlaya fotografiaWSGetPlaya = new FotografiaWSGetPlaya();
            fotografiaWSGetPlaya.execute(tk, playaId);
            l = fotografiaWSGetPlaya.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        gridView = (GridView) findViewById(R.id.gridviewListPhotos);
        gridView.setAdapter(new GridViewImagesPlayasAdapter(this, l, playaId));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                /*Intent i = new Intent(ListPhotosBeachActivity.this, ListPhotosBeachDetailsActivity.class);
                startActivity(i);*/
                Toast.makeText(getApplicationContext(), "ITEM_CLICKED: " + playaName + " " + playaId, Toast.LENGTH_LONG).show();
            }
        });
    }

    public GridView getGridView() {
        return gridView;
    }

    public void setGridView(GridView gridView) {
        this.gridView = gridView;
    }

}
