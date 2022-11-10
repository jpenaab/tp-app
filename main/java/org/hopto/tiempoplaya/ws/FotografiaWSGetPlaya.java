package org.hopto.tiempoplaya.ws;

import android.content.Context;
import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;
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
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by jpenaab on 22/02/2019.
 */

public class FotografiaWSGetPlaya extends AsyncTask<Object, Integer, List<String>> {

    /**
     * SOAP
     */
    String NAMESPACE = "http://ws.tiempoplaya.hopto.com/";
    String URL = WSConnectionData.getPROTOCOL() + "://" + WSConnectionData.getHOST() + "/TiempoPlayaWSImplService";
    String METHOD_NAME = "getPhotoBeach";
    String SOAP_ACTION = "http://ws.tiempoplaya.hopto.com/getPhotoBeach";

    @Override
    protected List<String> doInBackground(Object... objects) {

        Vector<String> list = new Vector<String>();

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        String tk = objects[0].toString();

        Integer idP = Integer.valueOf(objects[1].toString());
        request.addProperty("idP",idP);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        envelope.setOutputSoapObject(request);

        // header
        ArrayList<HeaderProperty> headerProperty = new ArrayList<HeaderProperty>();
        headerProperty.add(new HeaderProperty("tk", tk));

        HttpTransportSE httpTransportSE = new HttpTransportSE(URL);

        SoapPrimitive resultPrimitive = null;
        Vector <SoapPrimitive> resultObject = null;
        try {
            httpTransportSE.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            httpTransportSE.call(SOAP_ACTION, envelope, headerProperty);
            httpTransportSE.debug = true;

            resultObject = (Vector <SoapPrimitive>) envelope.getResponse();


        } catch (IOException e) {
            //Log.d("Error", "Some exception occurred", e);
        } catch (XmlPullParserException e) {
            //Log.d("Error", "Some exception occurred", e);
        } catch (NetworkOnMainThreadException e) {
            //Log.d("Error", "Some exception occurred", e);
        }catch (ClassCastException e) {

            try {
                resultPrimitive = (SoapPrimitive) envelope.getResponse();
                //System.out.println("-->" + resultPrimitive.getValue().toString());

                list.add(resultPrimitive.getValue().toString());

            }catch (IOException ex) {
                //Log.d("Error", "Some exception occurred", ex);
            }catch (NetworkOnMainThreadException ex) {
                //Log.d("Error", "Some exception occurred", ex);
            }catch (ClassCastException ex) {
                //Log.d("Error", "Some exception occurred", ex);
            }

            return list;
        }

        return setListContent(resultObject);
    }

    private List<String> setListContent(Vector <SoapPrimitive> listResponse){
        List<String> listFileName = new ArrayList<String>();

        try {
            if (listResponse.size() > 0 && listResponse != null && !listResponse.isEmpty()) {
                for (int i = 0; i < listResponse.size(); ++i) {

                    //System.out.println("-->" + listResponse.get(i));
                    listFileName.add(listResponse.get(i).toString());

                }
            }
        }catch (NullPointerException e){
            //Log.d("ERRNO: ", e.getMessage());
        }

        return listFileName;
    }

}
