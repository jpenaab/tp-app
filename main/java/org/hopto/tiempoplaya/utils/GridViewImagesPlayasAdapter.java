package org.hopto.tiempoplaya.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;

import org.hopto.tiempoplaya.activity.R;
import org.hopto.tiempoplaya.ws.FotografiaWSGetPlaya;

import java.util.ArrayList;
import java.util.List;

public class GridViewImagesPlayasAdapter extends BaseAdapter {

    private List<String> imagesURLs = new ArrayList<String>();
    private Context mContext;
    private List<String> listFilesname;
    private Integer playaId;

    LayoutInflater inflater;

    public GridViewImagesPlayasAdapter(Context context, List<String> list, Integer playaId) {
        this.mContext = context;
        this.listFilesname = list;
        this.playaId = playaId;

        imagesURLs.add(0,WSConnectionData.getPROTOCOL() + "://" + WSConnectionData.getHOST() + "/images/beaches/" + String.format("%04d", playaId) + "/_ph_main.jpg");
        for (int i = 1; i <= listFilesname.size(); ++i) {
            int z = i - 1;
            imagesURLs.add(i,WSConnectionData.getPROTOCOL() + "://" + WSConnectionData.getHOST() + "/images/beaches/" + String.format("%04d", playaId) + "/" + listFilesname.get(z) + "");
        }

        inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imagesURLs.size();
    }

    @Override
    public Object getItem(int position) {
        return imagesURLs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        ImageView imageView;

        if (convertView == null) {
            // If convertView is null then inflate the appropriate layout file
            convertView = inflater.inflate(R.layout.activity_list_photos_beach_details, null);
        }

        imageView = (ImageView) convertView.findViewById(R.id.gridViewItems);

        Picasso.get().load(imagesURLs.get(position)).into(imageView);

        return convertView;
    }

}
