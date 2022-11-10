package org.hopto.tiempoplaya.ws;

import android.os.AsyncTask;
import android.os.Environment;
import android.os.health.SystemHealthManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.hopto.tiempoplaya.modelo.TUsuarios;
import org.hopto.tiempoplaya.utils.FilesAccess;
import org.hopto.tiempoplaya.utils.WSConnectionData;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.File;
import java.io.IOException;

/**
 * Created by jpenaab on 23/01/2019.
 */

public class UsuarioWSLogOn extends AsyncTask<String, Object, TUsuarios> {

    private TUsuarios authUser = new TUsuarios();

    private ProgressBar progressBar;


    /**
     * SOAP
     */
    String NAMESPACE = "http://ws.tiempoplaya.hopto.com/";
    String URL = WSConnectionData.getPROTOCOL() + "://" + WSConnectionData.getHOST() + "/TiempoPlayaWSImplService";
    String METHOD_NAME = "loginAccount";
    String SOAP_ACTION = "http://ws.tiempoplaya.hopto.com/loginAccount";

    public UsuarioWSLogOn (ProgressBar progressBar){
        this.progressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
        progressBar.showContextMenu();
    }

    @Override
    protected void onPostExecute(TUsuarios tUsuarios) {
        super.onPostExecute(tUsuarios);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected TUsuarios doInBackground(String... strings) {

        String username = strings[0];
        String password = strings[1];

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        request.addProperty("usuario", username);
        request.addProperty("contrasenya", password);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.dotNet = false;
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransportSE = new HttpTransportSE(URL);

        try {
            httpTransportSE.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            httpTransportSE.call(SOAP_ACTION, envelope);
            httpTransportSE.debug = true;

            SoapObject response = (SoapObject) envelope.getResponse();

            if (Integer.valueOf(response.getProperty(0).toString()) > 0)
                setAuthUser(response);

        }catch (SoapFault fault) {
            authUser.setId(-1);
            //Log.v("TAG", "soapfault = "+fault.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getAuthUser();
    }

    private void setAuthUser(SoapObject response){

        authUser.setId(Integer.valueOf(response.getProperty(0).toString()));
        authUser.setNombre(response.getProperty(1).toString());
        authUser.setApellidos(response.getProperty(2).toString());
        authUser.setUsuario(response.getProperty(3).toString());
        authUser.setEmail(response.getProperty(4).toString());
        authUser.setTk(response.getProperty(5).toString());

    }

    private TUsuarios getAuthUser() {
        return authUser;
    }
}
