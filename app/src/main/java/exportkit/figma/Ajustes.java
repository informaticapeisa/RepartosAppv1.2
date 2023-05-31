package exportkit.figma;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.repartosappv1.R;


public class Ajustes extends Activity {
    private EditText nombre;
    private EditText matricula;
    private EditText pwd;

    private ListView almacenes;
    private TextView resultado;
    String[] alm = {"Peisa Central","Peisa Gandía","Peisa Castellón","Peisa Alzira","Peisa Valencia","Peisa Lorca","Peisa Barcelona", "Peisa Madrid","Peisa Alicante","Peisa La Mancha"};
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajustes);
        //Intent intent0 = getIntent();
        nombre=findViewById(R.id.usuario_ti);
        pwd=findViewById((R.id.pwd_ti));
        matricula=findViewById((R.id.matricula_ti));
        almacenes=findViewById(R.id.almacen_lv);
        resultado=findViewById(R.id.rdo_tv);
        ArrayAdapter<String> arr;
        arr = new ArrayAdapter<String>(this, R.layout.items_lista, alm);
        almacenes.setAdapter(arr);
    }
    // Obtenemos los datos almacenados en onResume() porque se llamará cuando la app se abra de nuevo
    @Override
    protected void onResume() {
        super.onResume();
        // Obtenemos los datos almacenados en SharedPreferences
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String s1 = sh.getString("nombre", "");
        String s2 = sh.getString("pwd", "");
        String s3 = sh.getString("matricula", "");
        //int a = sh.getInt("age", 0);

        // Setting the fetched data in the EditTexts
        nombre.setText(s1);
        matricula.setText(s2);
        pwd.setText(s3);
        //age.setText(String.valueOf(a));
    }
    //Almacenamos los datos en SharedPreferences en el evento onPause()
    // Cuando el usuario cierre la app onPause() será llamado y los datos serán almacendos
    @Override
    protected void onPause() {
        super.onPause();
        // Creating a shared pref object with a file name "MySharedPref" in private mode
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        // write all the data entered by the user in SharedPreference and apply
        myEdit.putString("nombre", nombre.getText().toString());
        myEdit.putString("pwd", pwd.getText().toString());
        myEdit.putString("matricula", matricula.getText().toString());
        //myEdit.putInt("age", Integer.parseInt(age.getText().toString()));
        myEdit.apply();
        myEdit.commit();
    }

}
