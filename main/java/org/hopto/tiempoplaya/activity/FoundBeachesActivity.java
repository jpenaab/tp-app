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
import org.hopto.tiempoplaya.ws.PlayaWSSearchByString;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FoundBeachesActivity extends AppCompatActivity {

    private String tk;
    private String textToSearch;

    private String userUtmx;
    private String userUtmy;
    private String userUtmz;
    private String utmzone;

    private ListView listViewFoundPlayas;
    private List<TPlayas> foundedPlayas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_beaches);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_login_main);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        mTitle.setText(getResources().getString(R.string.founded_playas));
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tk = getIntent().getStringExtra("tk");

        userUtmx = getIntent().getStringExtra("utmx");
        userUtmy = getIntent().getStringExtra("utmy");
        userUtmz = getIntent().getStringExtra("utmz");
        utmzone = getIntent().getStringExtra("utmr");

        textToSearch = getIntent().getStringExtra("textToSearch");

        foundedPlayas = new ArrayList<TPlayas>();

        PlayaWSSearchByString playaWSSearchByString = new PlayaWSSearchByString();
        playaWSSearchByString.execute(tk, userUtmx, userUtmy, userUtmz, utmzone, textToSearch);

        try {
            foundedPlayas = playaWSSearchByString.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        this.listViewFoundPlayas = (ListView) findViewById(R.id.lv_founded_beaches);
        this.listViewFoundPlayas.setAdapter(new ListViewListaPlayasAdapter(getApplicationContext(), foundedPlayas));

        this.listViewFoundPlayas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), StatusPlayaActivity.class);
                intent.putExtra("nombrePlaya", foundedPlayas.get(position).getNombre());
                intent.putExtra("tk", tk);
                startActivity(intent);

            }
        });
    }
}
