package exportkit.figma;

import static com.google.android.gms.vision.L.TAG;
import static com.google.android.material.color.utilities.MaterialDynamicColors.error;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.repartosappv1.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Date;

public class CrearReparto extends Activity {

    private Integer codigo_reparto;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrearCodigoReparto();
        setContentView(R.layout.pagppal);

    }
    private Integer CrearCodigoReparto() {

        String url = "https://www.peisanet.es/api/Reparto/CrearReparto";

        Map<String,Object> params =  new HashMap<>();

        params.put("empresa",String.valueOf(1));
        params.put("almacen",11);
        params.put("usuario", "plamarca");
        params.put("matricula", "5148GXC");


        JSONObject jsonObject = new JSONObject(params);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "Reparto creado correctamente.");
                codigo_reparto = Integer.valueOf(String.valueOf(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                codigo_reparto = -1;
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "Bearer " + "cmVwYXJ0b3M6cmVwYXJ0b3M=");
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        Volley.newRequestQueue(this).add(request);
        return codigo_reparto;
    }




}
