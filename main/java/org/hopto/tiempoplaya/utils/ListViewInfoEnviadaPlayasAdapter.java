package org.hopto.tiempoplaya.utils;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.hopto.tiempoplaya.activity.R;
import org.hopto.tiempoplaya.modelo.TInfo;
import org.hopto.tiempoplaya.modelo.TPlayas;

import java.text.DecimalFormat;
import java.util.List;

public class ListViewInfoEnviadaPlayasAdapter extends BaseAdapter {

    private ImageView imageViewViento;
    private ImageView imageViewAgua;
    private ImageView imageViewBandera;
    private ImageView imageViewArena;
    private ImageView imageViewMedusas;
    private ImageView imageViewOcupacion;
    private ImageView imageViewTiempo;
    private ImageView imageViewOleaje;

    private Context context;
    private List<TInfo> infoList;

    DecimalFormat DF = new DecimalFormat("###,###.##");

    public ListViewInfoEnviadaPlayasAdapter(Context context, List<TInfo> infoList) {
        this.context = context;
        this.infoList = infoList;
    }

    @Override
    public int getCount() {
        return this.infoList.size();
    }

    @Override
    public TInfo getItem(int position) {
        return this.infoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (convertView == null) {
            // Create a new view into the list.
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.activity_info_sended_listview_content, parent, false);
        }

        TextView nombre = (TextView) row.findViewById(R.id.tv_first_line_info_beaches);
        TextView geo = (TextView) row.findViewById(R.id.tv_second_line_info_beaches);
        ImageView imageViewViento = (ImageView) row.findViewById(R.id.ivSendedDataViento);
        ImageView imageViewAgua = (ImageView) row.findViewById(R.id.ivSendedDataAgua);
        ImageView imageViewBandera = (ImageView) row.findViewById(R.id.ivSendedDataBandera);
        ImageView imageViewArena = (ImageView) row.findViewById(R.id.ivSendedDataArena);
        ImageView imageViewMedusas = (ImageView) row.findViewById(R.id.ivSendedDataMedusas);
        ImageView imageViewOcupacion = (ImageView) row.findViewById(R.id.ivSendedDataOcupacion);
        ImageView imageViewTiempo = (ImageView) row.findViewById(R.id.ivSendedDataTiempo);
        ImageView imageViewOleaje = (ImageView) row.findViewById(R.id.ivSendedDataMar);
        TextView info = (TextView) row.findViewById(R.id.tv_thrid_line_info_beaches);
        TextView other = (TextView) row.findViewById(R.id.tv_fourth_line_info_beaches);


        TInfo o = (TInfo) this.infoList.get(position);

        nombre.setText(o.getNombre());
        geo.setText(o.getTimestamp());

        setInformacionPlaya(row, o);

        info.setText(o.getDataSended());
        other.setText(o.getCoordUTMx() + ", " + o.getCoordUTMy() + ", " + o.getCoordUTMz());

        return row;
    }

    private void setInformacionPlaya(View row, TInfo o) {

        ImageView imageViewViento = (ImageView) row.findViewById(R.id.ivSendedDataViento);
        ImageView imageViewAgua = (ImageView) row.findViewById(R.id.ivSendedDataAgua);
        ImageView imageViewBandera = (ImageView) row.findViewById(R.id.ivSendedDataBandera);
        ImageView imageViewArena = (ImageView) row.findViewById(R.id.ivSendedDataArena);
        ImageView imageViewMedusas = (ImageView) row.findViewById(R.id.ivSendedDataMedusas);
        ImageView imageViewOcupacion = (ImageView) row.findViewById(R.id.ivSendedDataOcupacion);
        ImageView imageViewTiempo = (ImageView) row.findViewById(R.id.ivSendedDataTiempo);
        ImageView imageViewOleaje = (ImageView) row.findViewById(R.id.ivSendedDataMar);

        //viento
        if (o.getViento() == -1) {
            imageViewViento.setImageResource(R.drawable.ic_flip_flops_bn);
        } else if (o.getViento() == 1) {
            imageViewViento.setImageResource(R.drawable.ic_viento_brisa);

        } else if (o.getViento() == 2) {
            imageViewViento.setImageResource(R.drawable.ic_viento_suave);

        } else if (o.getViento() == 3) {
            imageViewViento.setImageResource(R.drawable.ic_viento_fuerte);

        } else if (o.getViento() == 4) {
            imageViewViento.setImageResource(R.drawable.ic_viento_muyfuerte);

        }

        //mar
        if (o.getOleaje() == -1) {
            imageViewOleaje.setImageResource(R.drawable.ic_flip_flops_bn);

        } else if (o.getOleaje() == 1) {
            imageViewOleaje.setImageResource(R.drawable.ic_mar_calma);

        } else if (o.getOleaje() == 2) {
            imageViewOleaje.setImageResource(R.drawable.ic_mar_marejadilla);

        } else if (o.getOleaje() == 3) {
            imageViewOleaje.setImageResource(R.drawable.ic_mar_marejada);

        } else if (o.getOleaje() == 4) {
            imageViewOleaje.setImageResource(R.drawable.ic_mar_granmarejada);

        }

        //tiempo
        if (o.getNubosidad() == -1) {
            imageViewTiempo.setImageResource(R.drawable.ic_flip_flops_bn);

        } else if (o.getNubosidad() == 1) {
            imageViewTiempo.setImageResource(R.drawable.ic_sol);

        } else if (o.getNubosidad() == 2) {
            imageViewTiempo.setImageResource(R.drawable.ic_sol_nubes);

        } else if (o.getNubosidad() == 3) {
            imageViewTiempo.setImageResource(R.drawable.ic_nubes);

        } else if (o.getNubosidad() == 4) {
            imageViewTiempo.setImageResource(R.drawable.ic_lluvia);

        }

        //ocupacion
        if (o.getOcupacion() == -1) {
            imageViewOcupacion.setImageResource(R.drawable.ic_flip_flops_bn);

        } else if (o.getOcupacion() == 1) {
            imageViewOcupacion.setImageResource(R.drawable.ic_ocupacion_empty);

        } else if (o.getOcupacion() == 2) {
            imageViewOcupacion.setImageResource(R.drawable.ic_ocupacion_baja);

        } else if (o.getOcupacion() == 3) {
            imageViewOcupacion.setImageResource(R.drawable.ic_ocupacion_media);

        } else if (o.getOcupacion() == 4) {
            imageViewOcupacion.setImageResource(R.drawable.ic_ocupacion_mucha);

        }

        //agua
        if (o.getLimpiezaAgua() == -1) {
            imageViewAgua.setImageResource(R.drawable.ic_flip_flops_bn);

        } else if (o.getLimpiezaAgua() == 1) {
            imageViewAgua.setImageResource(R.drawable.ic_agua_cristalina);

        } else if (o.getLimpiezaAgua() == 2) {
            imageViewAgua.setImageResource(R.drawable.ic_agua_limpia);

        } else if (o.getLimpiezaAgua() == 3) {
            imageViewAgua.setImageResource(R.drawable.ic_agua_sucia);

        } else if (o.getLimpiezaAgua() == 4) {
            imageViewAgua.setImageResource(R.drawable.ic_agua_muysucia);

        }

        if (o.getLimpiezaArena() == -1) {
            imageViewArena.setImageResource(R.drawable.ic_flip_flops_bn);

        } else if (o.getLimpiezaArena() == 1) {
            imageViewArena.setImageResource(R.drawable.ic_playa_muylimpia);

        } else if (o.getLimpiezaArena() == 2) {
            imageViewArena.setImageResource(R.drawable.ic_playa_limpia);

        } else if (o.getLimpiezaArena() == 3) {
            imageViewArena.setImageResource(R.drawable.ic_playa_sucia);

        } else if (o.getLimpiezaArena() == 4) {
            imageViewArena.setImageResource(R.drawable.ic_playa_muysucia);

        }

        //medusas
        if (o.getMedusas() == -1) {
            imageViewMedusas.setImageResource(R.drawable.ic_flip_flops_bn);

        } else if (o.getMedusas() == 1) {
            imageViewMedusas.setImageResource(R.drawable.ic_tortoise);

        } else if (o.getMedusas() == 2) {
            imageViewMedusas.setImageResource(R.drawable.ic_medusa_muy_pocas);

        } else if (o.getMedusas() == 3) {
            imageViewMedusas.setImageResource(R.drawable.ic_medusa_pocas);

        } else if (o.getMedusas() == 4) {
            imageViewMedusas.setImageResource(R.drawable.ic_medusa_muchas);

        }

        //bandera
        if (o.getBanderaMar() == -1) {
            imageViewBandera.setImageResource(R.drawable.ic_flip_flops_bn);

        } else if (o.getBanderaMar() == 1) {
            imageViewBandera.setImageResource(R.drawable.ic_bandera_verde);

        } else if (o.getBanderaMar() == 2) {
            imageViewBandera.setImageResource(R.drawable.ic_bandera_amarilla);

        } else if (o.getBanderaMar() == 3) {
            imageViewBandera.setImageResource(R.drawable.ic_bandera_roja);

        } else if (o.getBanderaMar() == 4) {
            imageViewBandera.setImageResource(R.drawable.ic_bandera_no);
        }

    }
}
