package org.hopto.tiempoplaya.ws;

import android.os.AsyncTask;

import org.hopto.tiempoplaya.modelo.TDatos;
import org.hopto.tiempoplaya.modelo.TInfo;
import org.hopto.tiempoplaya.modelo.TPlayas;
import org.hopto.tiempoplaya.utils.WSConnectionData;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by jpenaab on 04/01/2019.
 */

//AsyncTask<Params, Progress, Result>
public class DatosWSGetInfoSended extends AsyncTask<String,Object,  List<TInfo>> {

    private ArrayList<SoapObject> result = new ArrayList<SoapObject>();
    private List<TInfo> listInfo = new ArrayList<TInfo>();

    /**
     * SOAP@
     */
    String NAMESPACE = "http://ws.tiempoplaya.hopto.com/";
    String URL = WSConnectionData.getPROTOCOL() + "://" + WSConnectionData.getHOST() + "/TiempoPlayaWSImplService";
    String METHOD_NAME = "getInfoDataSended";
    String SOAP_ACTION = "http://ws.tiempoplaya.hopto.com/getInfoDataSended";

    @Override
    protected void onProgressUpdate(Object... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<TInfo> doInBackground(String... param) {

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        String tk = param[0].toString();

        request.addProperty("userId", Integer.valueOf(param[1]));

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
            result.add(o);

        }

    }

    private List<TInfo> getListPlayas() {

        TInfo info;

        for (SoapObject o : result){

            info = new TInfo();

            info.setBanderaMar(Integer.valueOf(o.getProperty(0).toString()));
            info.setCoordUTMx(o.getProperty(1).toString());
            info.setCoordUTMy(o.getProperty(2).toString());
            info.setCoordUTMz(o.getProperty(3).toString());
            info.setLimpiezaAgua(Integer.valueOf(o.getProperty(5).toString()));
            info.setLimpiezaArena(Integer.valueOf(o.getProperty(6).toString()));
            info.setMedusas(Integer.valueOf(o.getProperty(7).toString()));
            info.setNombre(o.getProperty(8).toString());
            info.setNubosidad(Integer.valueOf(o.getProperty(9).toString()));
            info.setOcupacion(Integer.valueOf(o.getProperty(10).toString()));
            info.setOleaje(Integer.valueOf(o.getProperty(11).toString()));
            info.setTimestamp(o.getProperty(12).toString());
            info.setViento(Integer.valueOf(o.getProperty(13).toString()));

            listInfo.add(info);

        }


        return listInfo;
    }
}
