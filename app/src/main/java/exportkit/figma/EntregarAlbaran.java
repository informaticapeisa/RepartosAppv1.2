package exportkit.figma;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.repartosappv1.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class EntregarAlbaran extends Activity {
    FusedLocationProviderClient fusedLocationProviderClient;
    private final static int REQUEST_CODE = 100;
    public SurfaceView surfaceView;
    public TextView txtCliente;
    public TextView txtDireccion;
    public TextView txtBarcodeValue;
    public TextView txtCodigoAlbaran;
    public TextView txtCodigoPostal;
    public TextView txtEnt1;
    public TextView txtEnt2;
    public TextView txtEnt3;

    private Button entregar_bt;

    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    String intentData = "";
    private LinearLayout alb;
    private LinearLayout albent;

    public ImageView hole;
    public TextView alb_tv;
    String codigobarras;
    private String datosalbaran;
    public List<Address> addresses;

    public void Entregar(View view) {
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
        Toast.makeText(getApplicationContext(), "Marcando albarán como entregado...", Toast.LENGTH_SHORT).show();
    }


    private List<Address> getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location !=null){
                                Geocoder geocoder=new Geocoder(EntregarAlbaran.this, Locale.getDefault());
                                List<Address> addresses= null;
                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
//                                    latitude.setText("Latitude :" +addresses.get(0).getLatitude());
//                                    longitude.setText("Longitude :"+addresses.get(0).getLongitude());
//                                    address.setText("Address :"+addresses.get(0).getAddresLine(0));
//                                    city.setText("City :"+addresses.get(0).getLocality());
//                                    country.setText("Country :"+addresses.get(0).getCountryName());
                                    entregar_bt.setVisibility(View.GONE);
                                    albent.setVisibility(View.VISIBLE);
                                    txtEnt1.setText("Datos albarán entregado");
                                    txtEnt2.setText(addresses.get(0).getAddressLine(0));
                                    txtEnt3.setText("Latitud:" +addresses.get(0).getLatitude()+","+"Longitud:"+addresses.get(0).getLongitude());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    });

        }else
        {
            askPermission();
        }

        return addresses;
    }
    private void askPermission() {
        ActivityCompat.requestPermissions(this, new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
    }

    public interface DatosAlbaranCallBack{
        void onSuccess(String response);
        void onError(String error);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entregar_albaran);
        //Intent intent0 = getIntent();
        initViews();
    }
    private void initViews() {

        surfaceView = findViewById(R.id.surfaceView);
        hole = findViewById(R.id.hole_iv);
        alb=findViewById(R.id.albaran_ll);
        albent=findViewById(R.id.datosentregaalb_ll);
        /////////////////////////////////////////////////////
        //Lineas a poner para probar en emulador
        ////////////////////////////////////////////////////
//        alb.setVisibility(View.VISIBLE);
//        surfaceView.setVisibility(View.GONE);
//        surfaceView.setZOrderOnTop(false);
//        hole.bringToFront();
        ////////////////////////////////////////////////
        /////////////////////////////////////////////////
        txtCliente=findViewById(R.id.Cliente_tv);
        txtBarcodeValue = findViewById(R.id.txtBarcodeValue);
        txtDireccion=findViewById(R.id.Direccion_tv);
        txtCodigoAlbaran=findViewById(R.id.CodigoAlbaran_tv);
        txtCodigoPostal=findViewById(R.id.CodigoPostal_tv);

        txtEnt1=findViewById(R.id.datalb_ent_tv);
        txtEnt2=findViewById(R.id.Direccion_ent_tv);
        txtEnt3=findViewById(R.id.gps_ent_tv);

        entregar_bt=findViewById(R.id.entregarAlb_bt);

        /////////////////////////////////////////////////////
        //Lineas a poner para probar en emulador
        ////////////////////////////////////////////////////
//        ObtenerDatosAlbaran("1305340751018", new DatosAlbaranCallBack() {
//            @Override
//            public void onSuccess(String response) {
//
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    txtCodigoAlbaran.setText(jsonObject.getString("serie")+"/"+jsonObject.getString("numero"));
//                    txtCliente.setText(jsonObject.getString("cliente"));
//                    //txtid.setText(jsonObject.getString("nombre"));
//                    txtDireccion.setText(jsonObject.getString("direccion"));
//                    txtCodigoPostal.setText(jsonObject.getString("cpo"));
//                    //txtPoblacion.setText(jsonObject.getString("poblacion"));
////                    txtType.setText(jsonObject.getString("telefono"));
////                    txtDate.setText(jsonObject.getString("fecha"));
////                    txtDate.setText(jsonObject.getString("codentrega"));
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onError(String error) {
//
//            }
//        });
///////////////////
        ////////////////////////////////////////////////
        /////////////////////////////////////////////////
    }
    private String ObtenerDatosAlbaran(String barcode,DatosAlbaranCallBack callBack)
    {
        //String url="https://www.peisanet.es/api/Albaran/"+barcode;
        String url = "https://www.peisanet.es/api/Reparto/ObtenerDatosAlbaran?codebar="+barcode;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject= new JSONObject(response);
                    datosalbaran=response;
                    callBack.onSuccess(response);
                } catch (JSONException e) {
                    datosalbaran="error";
                    callBack.onError(response);
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              callBack.onError(error.getMessage());
            }
        }){
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap header = new HashMap<>();
                header.put("Authorization", "Bearer "+"cmVwYXJ0b3M6cmVwYXJ0b3M=");
                return header;
            }

        };;
        Volley.newRequestQueue(this).add(stringRequest);
        return datosalbaran;
    }

    private void initialiseDetectorsAndSources() {

        Toast.makeText(getApplicationContext(), "Comienza escaneo", Toast.LENGTH_SHORT).show();

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1024, 768)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(EntregarAlbaran.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(EntregarAlbaran.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                Toast.makeText(getApplicationContext(), "Para prevenir picos de uso de memoria se ha detenido el escaneo de códigos.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    txtBarcodeValue.post(new Runnable() {
                        @Override
                        public void run() {

                           {
                                intentData = barcodes.valueAt(0).displayValue;
                                codigobarras=intentData;
                                alb=findViewById(R.id.albaran_ll);
                                alb.setVisibility(View.VISIBLE);
                                surfaceView.setVisibility(View.GONE);
                                cameraSource.stop();
                                txtBarcodeValue.setText("Código leído: "+codigobarras);
                                ////////////////////
                                ObtenerDatosAlbaran(codigobarras, new DatosAlbaranCallBack() {
                                   @Override
                                   public void onSuccess(String response) {
                                       try {
                                           JSONObject jsonObject = new JSONObject(response);
                                           txtCodigoAlbaran.setText(jsonObject.getString("serie")+"/"+jsonObject.getString("numero"));
                                           txtCliente.setText(jsonObject.getString("cliente")+"--"+jsonObject.getString("nombre"));
                                           txtDireccion.setText(jsonObject.getString("direccion"));
                                           txtCodigoPostal.setText(jsonObject.getString("cpo")+ " " + jsonObject.getString("poblacion"));
                                       } catch (JSONException e) {
                                           e.printStackTrace();
                                       }
                                   }
                                   @Override
                                   public void onError(String error) {

                                   }
                               });


                                //////////////////////////////
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
        hole.bringToFront();
    }

}
