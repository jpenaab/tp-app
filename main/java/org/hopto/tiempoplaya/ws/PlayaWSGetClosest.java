package org.hopto.tiempoplaya.ws;

import android.os.AsyncTask;
import android.util.Log;

import org.hopto.tiempoplaya.modelo.TPlayas;
import org.hopto.tiempoplaya.utils.WSConnectionData;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Vector;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

/**
 * Created by jpenaab on 04/01/2019.
 */

//AsyncTask<Params, Progress, Result>
public class PlayaWSGetClosest extends AsyncTask<String,Object,TPlayas> {

    private TPlayas closestPlaya = new TPlayas();

    /**
     * SOAP@
     */
    String NAMESPACE = "http://ws.tiempoplaya.hopto.com/";
    String URL = WSConnectionData.getPROTOCOL() + "://" + WSConnectionData.getHOST() + "/TiempoPlayaWSImplService";
    String METHOD_NAME = "getClosestPlaya";
    String SOAP_ACTION = "http://ws.tiempoplaya.hopto.com/getClosestPlaya";

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

        request.addProperty("coordutmx", param[1].toString());
        request.addProperty("coordutmy", param[2].toString());
        request.addProperty("coordutmz", param[3].toString());
        request.addProperty("utmzone", param[4].toString());

        //DEBUG
        //Log.d("WS_CLOSETS_PLAYA: ",request.toString() + " " +tk);

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
            setClosestPlaya(response);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return getClosestPlaya();

    }

    private void setClosestPlaya(SoapObject response){

        closestPlaya.setId(Integer.valueOf(response.getProperty(0).toString()));
        closestPlaya.setNombre(response.getProperty(1).toString());
        closestPlaya.setCoordUTMx(response.getProperty(2).toString());
        closestPlaya.setCoordUTMy(response.getProperty(3).toString());
        closestPlaya.setCoordUTMz(response.getProperty(4).toString());
        closestPlaya.setUtmzone(response.getProperty(5).toString());
        closestPlaya.setMunicipio(response.getProperty(6).toString());
        closestPlaya.setCp(Integer.valueOf(response.getProperty(7).toString()));
        closestPlaya.setPais(response.getProperty(8).toString());
        closestPlaya.setWebcam(response.getProperty(10).toString());

    }

    private TPlayas getClosestPlaya() {
        return closestPlaya;
    }
}
