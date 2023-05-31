
	 
package exportkit.figma;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.example.repartosappv1.R;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class pagppal_activity extends Activity {

	ImageView entregaralbaran;
	ImageView ajustes;
	private View _bg__pagppal_ek2;
	private View ellipse_7;
	private View ellipse_1;
	private View ellipse_2;
	private ImageView vector;
	private ImageView vector_ek1;
	private ImageView vector_ek2;
	private View rectangle_1;
	private TextView modificar_reparto;
	private TextView crear_reparto;

	private TextView entregar;
	private ImageView grupo_peisa___negro___sin_fondo_1;
	private TextView repartos;
	private TextView usuario;
	private TextView matricula;
	private ImageView vector_ek3;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.pagppal);

		
		_bg__pagppal_ek2 = (View) findViewById(R.id._bg__pagppal_ek2);
		ellipse_7 = (View) findViewById(R.id.crear_reparto_ellipse);
		ellipse_1 = (View) findViewById(R.id.modificar_reparto_ellipse);
		ellipse_2 = (View) findViewById(R.id.entregar_reparto_ellipse);
		vector = (ImageView) findViewById(R.id.crear_reparto_iv);
		vector_ek1 = (ImageView) findViewById(R.id.modificar_reparto_iv);
		//vector_ek2 = (ImageView) findViewById(R.id.entregar_albaran_iv);
		rectangle_1 = (View) findViewById(R.id.rectangle_1);
		modificar_reparto = (TextView) findViewById(R.id.modificar_reparto_txt);
		crear_reparto = (TextView) findViewById(R.id.crear_reparto_txt);
		entregar = (TextView) findViewById(R.id.entregar_albaran_txt);
		grupo_peisa___negro___sin_fondo_1 = (ImageView) findViewById(R.id.logo_peisa);
		repartos = (TextView) findViewById(R.id.repartos_txt);
		usuario = (TextView) findViewById(R.id.usuario_tv);
		matricula = (TextView) findViewById(R.id.matricula_tv);
		//vector_ek3 = (ImageView) findViewById(R.id.ajustes_bt);
		entregaralbaran=(ImageView)findViewById(R.id.entregar_albaran_iv);
		entregaralbaran.setOnClickListener(new View.OnClickListener() {
											  @Override
											  public void onClick(View v) {
												  Intent entrega = new Intent(pagppal_activity.this, EntregarAlbaran.class);
												  startActivity(entrega);
											  }
										  });


				//custom code goes here
		ajustes=(ImageView)findViewById(R.id.ajustes_bt);
		ajustes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent ajustes = new Intent(pagppal_activity.this, Ajustes.class);
				startActivity(ajustes);
			}
		});
	}

	protected void onResume() {
		super.onResume();
		// Obtenemos los datos almacenados en SharedPreferences
		SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
		String s1 = sh.getString("nombre", "");
		String s3 = sh.getString("matricula", "");
		int s4 = sh.getInt("almacen", 0);

		// Setting the fetched data in the EditTexts
		usuario.setText(s1);
		matricula.setText(s3);

	}

}
	
	