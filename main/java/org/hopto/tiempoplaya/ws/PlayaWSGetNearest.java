package org.hopto.tiempoplaya.ws;

import android.os.AsyncTask;
import android.util.Log;

import org.hopto.tiempoplaya.activity.TPMainActivityLogin;
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
public class PlayaWSGetNearest extends AsyncTask<String,Object,  List<TPlayas>> {

    private ArrayList<SoapObject> result = new ArrayList<SoapObject>();
    private List<TPlayas> listPlayas = new ArrayList<TPlayas>();

    /**
     * SOAP@
     */
    String NAMESPACE = "http://ws.tiempoplaya.hopto.com/";
    String URL = WSConnectionData.getPROTOCOL() + "://" + WSConnectionData.getHOST() + "/TiempoPlayaWSImplService";
    String METHOD_NAME = "getClosePlayas";
    String SOAP_ACTION = "http://ws.tiempoplaya.hopto.com/getClosePlayas";

    @Override
    protected void onProgressUpdate(Object... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<TPlayas> doInBackground(String... param) {

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        String tk = param[0].toString();

        request.addProperty("coordutmx", param[1].toString());
        request.addProperty("coordutmy", param[2].toString());
        request.addProperty("coordutmz", param[3].toString());
        request.addProperty("utmzone", param[4].toString());

        //DEBUG
        //Log.d("WS_ALL_PLAYAS: ",request.toString() + " " +tk);

        SoapSerializationEnvelope envelope =  new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.dotNet = false;
        envelope.setOutputSoapObject(request);

        // header
        ArrayList<HeaderProperty> headerProperty = new ArrayList<HeaderProperty>();
        headerProperty.add(new HeaderProperty("tk", tk));

        HttpTransportSE httpTransportSE = new HttpTransportSE(URL);

        Vector<SoapObject> response = null;

        try {
            httpTransportSE.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            httpTransportSE.call(SOAP_ACTION, envelope, headerProperty);
            httpTransportSE.debug = true;

            response = (Vector<SoapObject>) envelope.getResponse();

            setListPlayas(response.size(), response);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return getListPlayas();

    }

    private void setListPlayas(Integer length, Vector<SoapObject> response){

        for (int i = 0; i < length; ++i) {

            SoapObject o = response.get(i);
            //DEBUG
            //Log.d("DEBUG: ",o.toString());

            result.add(o);

        }

    }

    private List<TPlayas> getListPlayas() {

        TPlayas playa;

        for (SoapObject o : result){

            playa = new TPlayas();

            playa.setId(Integer.valueOf(o.getProperty(0).toString()));
            playa.setNombre(o.getProperty(1).toString());
            playa.setCoordUTMx(o.getProperty(2).toString());
            playa.setCoordUTMy(o.getProperty(3).toString());
            playa.setCoordUTMz(o.getProperty(4).toString());
            playa.setUtmzone(o.getProperty(5).toString());
            playa.setMunicipio(o.getProperty(6).toString());
            playa.setCp(Integer.valueOf(o.getProperty(7).toString()));
            playa.setPais(o.getProperty(8).toString());
            playa.setDistance(Double.valueOf(o.getProperty(12).toString()));
            //playa.setWebcam(o.getProperty(10).toString());

            listPlayas.add(playa);

        }


        return listPlayas;
    }
}
