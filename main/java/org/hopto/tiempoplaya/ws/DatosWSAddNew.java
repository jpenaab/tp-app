package org.hopto.tiempoplaya.ws;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.hopto.tiempoplaya.activity.R;
import org.hopto.tiempoplaya.activity.TPFormDataActivity;
import org.hopto.tiempoplaya.modelo.TFotografias;
import org.hopto.tiempoplaya.modelo.TPlayas;
import org.hopto.tiempoplaya.utils.WSConnectionData;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.SocketPermission;
import java.util.ArrayList;

/**
 * Created by jpenaab on 22/02/2019.
 */

public class DatosWSAddNew extends AsyncTask<Object, Integer, Boolean> {

    /**
     * SOAP
     */
    String NAMESPACE = "http://ws.tiempoplaya.hopto.com/";
    String URL = WSConnectionData.getPROTOCOL() + "://" + WSConnectionData.getHOST() + "/TiempoPlayaWSImplService";
    String METHOD_NAME = "addNewData";
    String SOAP_ACTION = "http://ws.tiempoplaya.hopto.com/addNewData";

    private ProgressBar progressBar;
    private Context context;

    public DatosWSAddNew(Context context, ProgressBar progressBar) {
        this.context = context;
        this.progressBar = progressBar;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        //super.onProgressUpdate(values);
        progressBar.setProgress(values[0]);
    }

    @Override
    protected void onCancelled(Boolean aBoolean) {
        super.onCancelled(aBoolean);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        progressBar.setVisibility(View.GONE);

        if (aBoolean) {

            Toast.makeText(context, "OK! Envío de datos correcto", Toast.LENGTH_LONG).show();

        } else {

            Toast.makeText(context, "ERROR! Problema al enviar datos", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    protected Boolean doInBackground(Object... objects) {

        Boolean resultado = false;

        String tk = (String) objects[0];

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        request.addProperty("idU", objects[1]);
        request.addProperty("idP", objects[2]);
        request.addProperty("idF", objects[3]);
        request.addProperty("utmx", objects[4].toString());
        request.addProperty("utmy", objects[5].toString());
        request.addProperty("utmz", objects[6].toString());
        request.addProperty("viento", (Integer) objects[7]);
        request.addProperty("mar", (Integer) objects[8]);
        request.addProperty("tiempo", (Integer) objects[9]);
        request.addProperty("ocupacion", (Integer) objects[10]);
        request.addProperty("agua", (Integer) objects[11]);
        request.addProperty("arena", (Integer) objects[12]);
        request.addProperty("medusas", (Integer) objects[13]);
        request.addProperty("bandera", (Integer) objects[14]);

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
            resultado = Boolean.valueOf(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
    }

}
