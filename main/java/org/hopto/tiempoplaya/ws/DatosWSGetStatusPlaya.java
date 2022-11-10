package org.hopto.tiempoplaya.ws;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.hopto.tiempoplaya.modelo.TDatos;
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

public class DatosWSGetStatusPlaya extends AsyncTask<String, Integer, TDatos> {

    private TDatos tDatos;

    /**
     * SOAP
     */
    String NAMESPACE = "http://ws.tiempoplaya.hopto.com/";
    String URL = WSConnectionData.getPROTOCOL() + "://" + WSConnectionData.getHOST() + "/TiempoPlayaWSImplService";
    String METHOD_NAME = "getInfoStatusPlaya";
    String SOAP_ACTION = "http://ws.tiempoplaya.hopto.com/getInfoStatusPlaya";

    private ProgressBar progressBar;
    private Context context;

    public DatosWSGetStatusPlaya(){
    }

    @Override
    protected TDatos doInBackground(String... param) {

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        String tk = param[0].toString();

        request.addProperty("idP", Integer.valueOf(param[1]));

        //DEBUG
        //Log.d("WS_DATOS_PLAYA: ",request.toString() + " " +tk);

        SoapSerializationEnvelope envelope =  new SoapSerializationEnvelope(SoapEnvelope.VER11);

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

            SoapObject response = (SoapObject) envelope.getResponse();
            setDatos(response);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return getDatos();

    }

    private void setDatos(SoapObject response){

        tDatos = new TDatos();

        tDatos.setBanderaMar(Integer.valueOf(response.getProperty(0).toString()));
        tDatos.setLimpiezaAgua(Integer.valueOf(response.getProperty(1).toString()));
        tDatos.setLimpiezaArena(Integer.valueOf(response.getProperty(2).toString()));
        tDatos.setMedusas(Integer.valueOf(response.getProperty(3).toString()));
        tDatos.setNubosidad(Integer.valueOf(response.getProperty(4).toString()));
        tDatos.setOcupacion(Integer.valueOf(response.getProperty(5).toString()));
        tDatos.setOleaje(Integer.valueOf(response.getProperty(6).toString()));
        tDatos.setViento(Integer.valueOf(response.getProperty(7).toString()));

    }

    private TDatos getDatos() {

        return tDatos;
    }

    }