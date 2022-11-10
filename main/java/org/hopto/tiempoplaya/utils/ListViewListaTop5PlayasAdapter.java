package org.hopto.tiempoplaya.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.hopto.tiempoplaya.activity.R;
import org.hopto.tiempoplaya.modelo.TPlayas;

import java.text.DecimalFormat;
import java.util.List;

public class ListViewListaTop5PlayasAdapter extends BaseAdapter {

    private Context context;
    private List<TPlayas> tPlayas;

    DecimalFormat DF = new DecimalFormat("###,###.##");

    public ListViewListaTop5PlayasAdapter(Context context, List<TPlayas> tPlayas){
        this.context = context;
        this.tPlayas = tPlayas;
    }

    @Override
    public int getCount() {
        return this.tPlayas.size();
    }

    @Override
    public TPlayas getItem(int position) {
        return this.tPlayas.get(position);
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
            row = inflater.inflate(R.layout.activity_top5_listview_content, parent, false);
        }

        ImageView photo = (ImageView) row.findViewById(R.id.iv_thumb_top5_beaches);
        LinearLayout linearLayout = (LinearLayout) row.findViewById(R.id.llSendedItems);
        TextView nombre = (TextView) row.findViewById(R.id.tv_first_line_top5_beaches);
        TextView info = (TextView) row.findViewById(R.id.tv_second_line_top5_beaches);
        TextView ratingData = (TextView) row.findViewById(R.id.tv_thrid_line_top5_beaches);

        TPlayas playa = (TPlayas) this.tPlayas.get(position);

        Picasso.get().load(WSConnectionData.getPROTOCOL() + "://" + WSConnectionData.getHOST() + "/images/beaches/" + String.format("%04d", playa.getId()) + "/_ph_main.jpg").into(photo);

        nombre.setText(playa.getNombre());
        info.setText(playa.getMunicipio() + " - " + playa.getCp());
        ratingData.setText(String.valueOf(playa.getDataRating()) + " " + row.getResources().getString(R.string.list_top5_opiniones));

        return row;
    }
}
