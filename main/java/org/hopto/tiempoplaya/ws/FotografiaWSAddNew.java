package org.hopto.tiempoplaya.ws;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.hopto.tiempoplaya.utils.WSConnectionData;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by jpenaab on 22/02/2019.
 */

public class FotografiaWSAddNew extends AsyncTask<Object, Integer, Integer> {

    private ProgressBar progressBar;
    private Context context;

    /**
     * SOAP
     */
    String NAMESPACE = "http://ws.tiempoplaya.hopto.com/";
    String URL = WSConnectionData.getPROTOCOL() + "://" + WSConnectionData.getHOST() + "/TiempoPlayaWSImplService";
    String METHOD_NAME = "addNewPhoto";
    String SOAP_ACTION = "http://ws.tiempoplaya.hopto.com/addNewPhoto";

    public FotografiaWSAddNew (Context context, ProgressBar progressBar){
        this.context = context;
        this.progressBar = progressBar;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        //super.onProgressUpdate(values);
        this.progressBar.setProgress(values[0]);
    }

    @Override
    protected void onCancelled(Integer integer) {
        super.onCancelled(integer);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        progressBar.setVisibility(View.GONE);

        if (integer != -1){

            Toast.makeText(context, "OK! Envío de fotografía correcto", Toast.LENGTH_LONG).show();

        }else{

            Toast.makeText(context, "ERROR! Problema al enviar fotografía", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Integer doInBackground(Object... objects) {

        Integer resultado = -1;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        String tk = objects[0].toString();

        Integer idU = Integer.valueOf(objects[1].toString());
        Integer idP = Integer.valueOf(objects[2].toString());

        String strBase64 = objects[3].toString();

        request.addProperty("idU",idU);
        request.addProperty("idP",idP);
        request.addProperty("bPhoto",strBase64);

        //DEBUG
        //Log.d(TAG, request.toString());

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        envelope.setOutputSoapObject(request);

        // header
        ArrayList<HeaderProperty> headerProperty = new ArrayList<HeaderProperty>();
        headerProperty.add(new HeaderProperty("tk", tk));

        HttpTransportSE httpTransportSE = new HttpTransportSE(URL);

        try {
            httpTransportSE.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            httpTransportSE.call(SOAP_ACTION, envelope, headerProperty);
            httpTransportSE.debug = true;

            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resultado = Integer.valueOf(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;

    }

}
