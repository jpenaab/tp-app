package org.hopto.tiempoplaya.ws;

import android.os.AsyncTask;
import android.util.Log;

import org.hopto.tiempoplaya.modelo.TPlayas;
import org.hopto.tiempoplaya.utils.WSConnectionData;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by jpenaab on 04/01/2019.
 */

//AsyncTask<Params, Progress, Result>
public class PlayaWSGetByName extends AsyncTask<String,Object,TPlayas> {

    private TPlayas tPlayas;

    /**
     * SOAP@
     */
    String NAMESPACE = "http://ws.tiempoplaya.hopto.com/";
    String URL = WSConnectionData.getPROTOCOL() + "://" + WSConnectionData.getHOST() + "/TiempoPlayaWSImplService";
    String METHOD_NAME = "getPlayaByName";
    String SOAP_ACTION = "http://ws.tiempoplaya.hopto.com/getPlayaByName";

    @Override
    protected void onProgressUpdate(Object... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected TPlayas doInBackground(String... param) {

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        String tk = param[0].toString();

        request.addProperty("playaName", param[1].toString());

        //DEBUG
        //Log.d("WS_PLAYA_BY_NAME: ",request.toString() + " " +tk);

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
            setPlaya(response);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return getPlaya();

    }

    private void setPlaya(SoapObject response){

        tPlayas = new TPlayas();

        tPlayas.setId(Integer.valueOf(response.getProperty(0).toString()));
        tPlayas.setNombre(response.getProperty(1).toString());
        tPlayas.setCoordUTMx(response.getProperty(2).toString());
        tPlayas.setCoordUTMy(response.getProperty(3).toString());
        tPlayas.setCoordUTMz(response.getProperty(4).toString());
        tPlayas.setUtmzone(response.getProperty(5).toString());
        tPlayas.setMunicipio(response.getProperty(6).toString());
        tPlayas.setCp(Integer.valueOf(response.getProperty(7).toString()));
        tPlayas.setPais(response.getProperty(8).toString());
        tPlayas.setWebcam(response.getProperty(10).toString());

    }

    private TPlayas getPlaya() {

        return tPlayas;
    }
}
