package org.hopto.tiempoplaya.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.hopto.tiempoplaya.modelo.TPlayas;
import org.hopto.tiempoplaya.utils.ListViewListaPlayasAdapter;
import org.hopto.tiempoplaya.ws.PlayaWSGetNearest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NearestActivity extends AppCompatActivity {

    private List<TPlayas> nearestPlayas;
    private ListView listViewNearestPlayas;

    private String tk;
    private String userUtmx;
    private String userUtmy;
    private String userUtmz;
    private String utmzone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearest);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_login_main);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        mTitle.setText(getResources().getString(R.string.nearest_beaches));
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tk = getIntent().getStringExtra("tk");
        userUtmx = getIntent().getStringExtra("utmx");
        userUtmy = getIntent().getStringExtra("utmy");
        userUtmz = getIntent().getStringExtra("utmz");
        utmzone = getIntent().getStringExtra("utmr");

        nearestPlayas = new ArrayList<TPlayas>();

        PlayaWSGetNearest playaWSGetFiveNearest = new PlayaWSGetNearest();
        playaWSGetFiveNearest.execute(tk, userUtmx, userUtmy, userUtmz, utmzone);

        try {
            nearestPlayas = playaWSGetFiveNearest.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        this.listViewNearestPlayas = (ListView) findViewById(R.id.lv_nearest_beaches);
        this.listViewNearestPlayas.setAdapter(new ListViewListaPlayasAdapter(getApplicationContext(), nearestPlayas));

        this.listViewNearestPlayas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), StatusPlayaActivity.class);
                intent.putExtra("nombrePlaya", nearestPlayas.get(position).getNombre());
                intent.putExtra("tk", tk);
                startActivity(intent);

            }
        });
    }
}
