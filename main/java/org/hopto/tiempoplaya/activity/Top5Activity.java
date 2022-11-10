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
import org.hopto.tiempoplaya.utils.ListViewListaTop5PlayasAdapter;
import org.hopto.tiempoplaya.ws.PlayaWSGetTopFive;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Top5Activity extends AppCompatActivity {

    private List<TPlayas> topFivePlayas;
    private ListView listViewTopFivePlayas;

    private String tk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top5);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_login_main);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        mTitle.setText(getResources().getString(R.string.top5_beaches));
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tk = getIntent().getStringExtra("tk");

        topFivePlayas = new ArrayList<TPlayas>();

        PlayaWSGetTopFive playaWSGetTopFive = new PlayaWSGetTopFive();
        playaWSGetTopFive.execute(tk);

        try {
            topFivePlayas = playaWSGetTopFive.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        this.listViewTopFivePlayas = (ListView) findViewById(R.id.lv_top_five_beaches);
        this.listViewTopFivePlayas.setAdapter(new ListViewListaTop5PlayasAdapter(getApplicationContext(), topFivePlayas));

        this.listViewTopFivePlayas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), StatusPlayaActivity.class);
                intent.putExtra("nombrePlaya", topFivePlayas.get(position).getNombre());
                intent.putExtra("tk", tk);
                startActivity(intent);

            }
        });

    }
}
