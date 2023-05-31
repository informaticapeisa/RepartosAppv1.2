package exportkit.figma;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.repartosappv1.R;


public class Ajustes extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private EditText nombre;
    private EditText matricula;
    private EditText pwd;

    private Spinner almacenes;
    private TextView resultado;
    public String[] alm = {"Peisa Central","Peisa Gandía","Peisa Castellón","Peisa Alzira","Peisa Valencia","Peisa Lorca","Peisa Barcelona", "Peisa Madrid","Peisa Alicante","Peisa La Mancha"};
    protected void onCreate(Bundle savedInstanceState)  {
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
        arr.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        almacenes.setAdapter(arr);
        almacenes.setOnItemSelectedListener(this);
    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int posicion, long id) {
        //Toast.makeText(getApplicationContext(), "Almacén Seleccionado: "+alm[posicion] ,Toast.LENGTH_SHORT).show();
        resultado.setText(alm[posicion]);
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        resultado.setText(alm[0]);
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
        int s4 = sh.getInt("almacen", 0);

        // Setting the fetched data in the EditTexts
        nombre.setText(s1);
        matricula.setText(s2);
        pwd.setText(s3);
        almacenes.setSelection((s4));
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
        myEdit.putInt("almacen", almacenes.getSelectedItemPosition());
        myEdit.apply();
        myEdit.commit();
    }

}
