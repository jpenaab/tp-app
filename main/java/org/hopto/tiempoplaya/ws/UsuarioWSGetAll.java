package org.hopto.tiempoplaya.ws;

import android.os.AsyncTask;
import android.util.Log;

import org.hopto.tiempoplaya.utils.WSConnectionData;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by jpenaab on 28/12/2018.
 */

public class UsuarioWSGetAll extends AsyncTask<String,Object,Object> {

    /**
     * SOAP
     */
    String NAMESPACE = "http://ws.tiempoplaya.hopto.com/";
    String URL = WSConnectionData.getPROTOCOL() + "://" + WSConnectionData.getHOST() + "/TiempoPlayaWSImplService";
    String METHOD_NAME = "getAllUsuarios";
    String SOAP_ACTION = "http://ws.tiempoplaya.hopto.com/getAllUsuarios";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(String... param) {

        ArrayList<Object> resultado = new ArrayList<Object>();
        Vector<SoapObject> response = null;

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        SoapSerializationEnvelope envelope =  new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(request);
        envelope.dotNet = false;

        HttpTransportSE httpTransportSE = new HttpTransportSE(URL);

        try {

            httpTransportSE.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            httpTransportSE.call(SOAP_ACTION, envelope);
            httpTransportSE.debug = true;
            response = (Vector<SoapObject>) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
        }

        int length = response.size();
        for (int i = 0; i < length; ++i) {

            SoapObject o = response.get(i);
            resultado.add(o);

        }

        return resultado;
    }
}
