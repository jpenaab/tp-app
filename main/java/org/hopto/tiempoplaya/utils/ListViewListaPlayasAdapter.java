package org.hopto.tiempoplaya.utils;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.*;

import org.hopto.tiempoplaya.activity.R;
import org.hopto.tiempoplaya.activity.StatusPlayaActivity;
import org.hopto.tiempoplaya.activity.TPMainActivityLogin;
import org.hopto.tiempoplaya.modelo.TPlayas;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ListViewListaPlayasAdapter extends BaseAdapter {

    private Context context;
    private List<TPlayas> nearestPlayas;

    DecimalFormat DF = new DecimalFormat("###,###.##");

    public ListViewListaPlayasAdapter(Context context, List<TPlayas> nearestPlayas){
        this.context = context;
        this.nearestPlayas = nearestPlayas;
    }

    @Override
    public int getCount() {
        return this.nearestPlayas.size();
    }

    @Override
    public TPlayas getItem(int position) {
        return this.nearestPlayas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        if(convertView == null){
            // Create a new view into the list.
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.activity_nearest_listview_content, parent, false);
        }

        ImageView photo = (ImageView) row.findViewById(R.id.iv_thumb_nearest_beaches);
        TextView nombre = (TextView) row.findViewById(R.id.tv_first_line_nearest_beaches);
        TextView info = (TextView) row.findViewById(R.id.tv_second_line_nearest_beaches);
        TextView distance = (TextView) row.findViewById(R.id.tv_thrid_line_nearest_beaches);

        TPlayas playa = (TPlayas) this.nearestPlayas.get(position);

        Picasso.get().load(WSConnectionData.getPROTOCOL() + "://" + WSConnectionData.getHOST() + "/images/beaches/" + String.format("%04d", playa.getId()) + "/_ph_main.jpg").into(photo);

        nombre.setText(playa.getNombre());
        info.setText(playa.getMunicipio() + " - " + playa.getCp());

        Double d = Double.valueOf(playa.getDistance());

        distance.setText(DF.format(d) + " " + row.getResources().getString(R.string.distance_metric));

        return row;
    }
}
