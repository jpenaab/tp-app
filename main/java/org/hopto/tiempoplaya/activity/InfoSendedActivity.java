package org.hopto.tiempoplaya.activity;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import org.hopto.tiempoplaya.modelo.TInfo;
import org.hopto.tiempoplaya.modelo.TPlayas;
import org.hopto.tiempoplaya.utils.ListViewInfoEnviadaPlayasAdapter;
import org.hopto.tiempoplaya.utils.ListViewListaFavoritasPlayasAdapter;
import org.hopto.tiempoplaya.ws.DatosWSGetInfoSended;
import org.hopto.tiempoplaya.ws.PlayaWSGetFavourites;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class InfoSendedActivity extends AppCompatActivity {

    private List<TInfo> infoList;
    private ListView listViewInfoSendedPlayas;

    private String tk;
    private Integer userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_sended);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_login_main);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        mTitle.setText(getResources().getString(R.string.info_sended));
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tk = getIntent().getStringExtra("tk");
        userId = getIntent().getIntExtra("userId", -1);

        infoList = new ArrayList<TInfo>();

        DatosWSGetInfoSended datosWSGetInfoSended = new DatosWSGetInfoSended();
        datosWSGetInfoSended.execute(tk, String.valueOf(userId));

        try {
            infoList = datosWSGetInfoSended.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        this.listViewInfoSendedPlayas = (ListView) findViewById(R.id.lv_info_sended_beaches);
        this.listViewInfoSendedPlayas.setAdapter(new ListViewInfoEnviadaPlayasAdapter(getApplicationContext(), infoList));
    }
}
