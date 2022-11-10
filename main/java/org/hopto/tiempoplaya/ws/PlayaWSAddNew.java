package org.hopto.tiempoplaya.ws;

import android.content.Context;
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

public class PlayaWSAddNew extends AsyncTask<String, Integer, Integer> {

    /**
     * SOAP
     */
    String NAMESPACE = "http://ws.tiempoplaya.hopto.com/";
    String URL = WSConnectionData.getPROTOCOL() + "://" + WSConnectionData.getHOST() + "/TiempoPlayaWSImplService";
    String METHOD_NAME = "addNewBeach";
    String SOAP_ACTION = "http://ws.tiempoplaya.hopto.com/addNewBeach";

    private ProgressBar progressBar;
    private Context context;

    public PlayaWSAddNew(Context context, ProgressBar progressBar){
        this.context = context;
        this.progressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(Integer returnedValue) {
        super.onPostExecute(returnedValue);
        progressBar.setVisibility(View.GONE);

        if (returnedValue != -1){

            Toast.makeText(context, "OK! Envío de datos correcto.\n En breve se revisará la nueva playa.", Toast.LENGTH_LONG).show();

        }else{

            Toast.makeText(context, "ERROR! Problema al enviar datos", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    protected Integer doInBackground(String... strings) {

        Integer resultado = -1;

        String tk = (String) strings[0];

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        request.addProperty("coordutmx", strings[1].toString());
        request.addProperty("coordutmy", strings[2].toString());
        request.addProperty("coordutmz", strings[3].toString());
        request.addProperty("utmzone", strings[4].toString());
        request.addProperty("name",  strings[5].toString());
        request.addProperty("locality", strings[6].toString());
        request.addProperty("cp", Integer.valueOf(strings[7]));
        request.addProperty("pais", strings[8].toString());

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
