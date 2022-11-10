package org.hopto.tiempoplaya.ws;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AEMetWSGetData extends AsyncTask<String, Integer, String> {

    private static final String API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqYWlyby5wZW5hLmFicmV1QGdtYWlsLmNvbSIsImp0aSI6ImY2YTg5NzVkLWE2YjktNGVkYS1iNDU2LTNiM2U1ODUxNDFjMSIsImlzcyI6IkFFTUVUIiwiaWF0IjoxNTYzNjE5NDkxLCJ1c2VySWQiOiJmNmE4OTc1ZC1hNmI5LTRlZGEtYjQ1Ni0zYjNlNTg1MTQxYzEiLCJyb2xlIjoiIn0.tRSJoincWDPIISlTGwjoADyP0qQc1CSJmRD1S5W2sKA";

    @Override
    protected String doInBackground(String... strings) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://opendata.aemet.es/opendata/api/valores/climatologicos/inventarioestaciones/todasestaciones/?api_key="+API_KEY)
                .get()
                .addHeader("cache-control", "no-cache")
                .build();

        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
