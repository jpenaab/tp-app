package org.hopto.tiempoplaya.ws;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import org.hopto.tiempoplaya.utils.WSConnectionData;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.ServiceConnection;

import static java.net.Proxy.Type.HTTP;

/**
 * Created by jpenaab on 28/12/2018.
 */

public class UsuarioWSCreateNew extends AsyncTask<String,Object,Object> {

    /**
     * SOAP
     */
    String NAMESPACE = "http://ws.tiempoplaya.hopto.com/";
    String URL = WSConnectionData.getPROTOCOL() + "://" + WSConnectionData.getHOST() + "/TiempoPlayaWSImplService";
    String METHOD_NAME = "createUsuario";
    String SOAP_ACTION = "http://ws.tiempoplaya.hopto.com/createUsuario";

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(String... param) {

        Boolean resultado = false;

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        request.addProperty("nombre", param[0].toString());
        request.addProperty("usuario", param[1].toString());
        request.addProperty("contrasenya", param[2].toString());
        request.addProperty("email", param[3].toString());

        SoapSerializationEnvelope envelope =  new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.dotNet = false;
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransportSE = new HttpTransportSE(URL);

        try {
            httpTransportSE.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            httpTransportSE.call(SOAP_ACTION, envelope);
            httpTransportSE.debug = true;
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resultado = Boolean.valueOf(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
    }

}
