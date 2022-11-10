package org.hopto.tiempoplaya.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.hopto.tiempoplaya.modelo.TPlayas;
import org.hopto.tiempoplaya.utils.ListViewListaFavoritasPlayasAdapter;
import org.hopto.tiempoplaya.utils.ListViewListaPlayasAdapter;
import org.hopto.tiempoplaya.ws.PlayaWSGetFavourites;
import org.hopto.tiempoplaya.ws.PlayaWSGetTopFive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FavoritasActivity extends AppCompatActivity {

    private List<TPlayas> favouritesPlayas;
    private ListView listViewFavouritesPlayas;

    private String tk;
    private Integer userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_favoritas);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_login_main);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        mTitle.setText(getResources().getString(R.string.favorites_beaches));
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tk = getIntent().getStringExtra("tk");
        userId = getIntent().getIntExtra("userId",-1);

        favouritesPlayas = new ArrayList<TPlayas>();

        PlayaWSGetFavourites playaWSGetFavourites = new PlayaWSGetFavourites();
        playaWSGetFavourites.execute(tk, String.valueOf(userId));

        try {
            favouritesPlayas = playaWSGetFavourites.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        this.listViewFavouritesPlayas = (ListView) findViewById(R.id.lv_favourites_beaches);
        this.listViewFavouritesPlayas.setAdapter(new ListViewListaFavoritasPlayasAdapter(getApplicationContext(), favouritesPlayas));

        this.listViewFavouritesPlayas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), StatusPlayaActivity.class);
                intent.putExtra("nombrePlaya", favouritesPlayas.get(position).getNombre());
                intent.putExtra("tk", tk);
                startActivity(intent);

            }
        });
    }
}
