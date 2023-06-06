package exportkit.figma;

import static com.google.android.gms.vision.L.TAG;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CrearReparto extends Activity {

    private Integer codigo_reparto;
    private Integer CrearCodigoReparto(){

        String url = "https://www.peisanet.es/api/Reparto/CrearReparto";

        JSONObject jsonObject= new JSONObject();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url,jsonObject, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG,"Reparto creado correctamente.");
                codigo_reparto=Integer.valueOf(String.valueOf(response));
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                codigo_reparto=-1;
            }
        }) {
            @Override
            public Map<String,String> getHeaders() {
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
