package com.example.surtidointentimpl;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import androidx.core.app.ActivityCompat;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

	    Button btn1 = findViewById(R.id.button1);
	    Button btn2 = findViewById(R.id.button2);
	    Button btn3 = findViewById(R.id.button3);
	    Button btn4 = findViewById(R.id.button4);
	    Button btn5 = findViewById(R.id.button5);
	    Button btn6 = findViewById(R.id.button6);
		Button btn7 = findViewById(R.id.button7);
		Button btn8 = findViewById(R.id.button8);
		Button btn9 = findViewById(R.id.button9);
		Button btn10 = findViewById(R.id.button10);

	    btn1.setOnClickListener(this);
	    btn2.setOnClickListener(this);
	    btn3.setOnClickListener(this);
	    btn4.setOnClickListener(this);
	    btn5.setOnClickListener(this);
	    btn6.setOnClickListener(this);
		btn7.setOnClickListener(this);
		btn8.setOnClickListener(this);
		btn9.setOnClickListener(this);
		btn10.setOnClickListener(this);


		if (Build.VERSION.SDK_INT >= 23)
			if (! ckeckPermissions())
			  requestPermissions();
	}

	@SuppressLint("NonConstantResourceId")
	@Override
	public void onClick (View v) {
		Intent in;
		final String lat = getString(R.string.lat);
		final String lon = getString(R.string.lon);
		final String url = getString(R.string.url);
		final String address = getString(R.string.direccion);
		final String textToSearch = getString(R.string.textoABuscar);

		switch (v.getId()) {
			//Localizacion coordenadas
			case R.id.button1:
				Toast.makeText(this, getString(R.string.opcion1), Toast.LENGTH_LONG).show();
				in = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + lat + ',' + lon));
				startActivity(in);
				break;
			//Localizacion direccion
			case R.id.button2:
				Toast.makeText(this, getString(R.string.opcion2), Toast.LENGTH_LONG).show();
				in = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + address));
				//in = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + textToSearch));
				startActivity(in);
				break;
			//Acceder web
			case R.id.button3:
				Toast.makeText(this, getString(R.string.opcion3), Toast.LENGTH_LONG).show();
				in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(in);
				break;
			//Buscar  google
			case R.id.button4:
				Toast.makeText(this, getString(R.string.opcion4), Toast.LENGTH_LONG).show();
				//in = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/search?q=" + textToSearch));
				in = new Intent(Intent.ACTION_WEB_SEARCH);
				in.putExtra(SearchManager.QUERY, textToSearch);
				startActivity(in);
				break;
			//Llamar telefono
			case R.id.button5:
                callPhone();
				break;
			//Marcar telefono
			case R.id.button6:
				Toast.makeText(this, getString(R.string.opcion6), Toast.LENGTH_LONG).show();
				in = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + getText(R.string.telef)));
				startActivity(in);
				break;
			//Enviar SMS
			case R.id.button7:
				Toast.makeText(this, getString(R.string.opcion7), Toast.LENGTH_LONG).show();
			//Enviar mail
			case R.id.button8:
				Toast.makeText(this, getString(R.string.opcion8), Toast.LENGTH_LONG).show();
			//Acceder contactos
			case R.id.button9:
				Toast.makeText(this, getString(R.string.opcion9), Toast.LENGTH_LONG).show();
				accessContacts();
				break;
			//Acceder galeria
			case R.id.button10:
				Toast.makeText(this, getString(R.string.opcion10), Toast.LENGTH_LONG).show();
			}
	}


	@Override
	protected void onResume() {
		super.onResume();
		if (Build.VERSION.SDK_INT >= 23)
			if (! ckeckPermissions())
				requestPermissions();
	}

	private boolean ckeckPermissions() {
		if (Build.VERSION.SDK_INT >= 23) {
			return ckeckPermissionsCallPhone() && ckeckPermissionsReadContacts();
		    }
		else
			return true;
	}

	private boolean ckeckPermissionsCallPhone() {
		return ActivityCompat.checkSelfPermission(getApplicationContext(),
				Manifest.permission.CALL_PHONE) ==
				PackageManager.PERMISSION_GRANTED;
	}

	private boolean ckeckPermissionsReadContacts() {
		return ActivityCompat.checkSelfPermission(getApplicationContext(),
				Manifest.permission.READ_CONTACTS) ==
				PackageManager.PERMISSION_GRANTED;
	}

	private void requestPermissions() {
		ActivityCompat.requestPermissions(MainActivity.this,
				new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE},
				0);
	}

	private void requestPermissionsCallPhone() {
		ActivityCompat.requestPermissions(MainActivity.this,
				new String[]{Manifest.permission.CALL_PHONE},
				0);
	}

	private void requestPermissionsReadContacts() {
		ActivityCompat.requestPermissions(MainActivity.this,
				new String[]{Manifest.permission.READ_CONTACTS},
				0);
	}

	private void accessContacts() {

		if (Build.VERSION.SDK_INT >= 23) {
			if (ckeckPermissionsReadContacts()) {
				accessContactsAction();
			} else {
				requestPermissionsReadContacts();
			}
		}
		else {
			accessContactsAction();
		}
	}

	private void accessContactsAction() {
		Intent in;

		Toast.makeText(this, getString(R.string.opcion6), Toast.LENGTH_LONG).show();
		in = new Intent(Intent.ACTION_VIEW);
		in.setData(ContactsContract.Contacts.CONTENT_URI);
		startActivity(in);
	}


	private void callPhone() {
		Intent in;

		if (Build.VERSION.SDK_INT >= 23) {
		if (ckeckPermissionsCallPhone()) {
			callPhoneAction();
		} else {
			    requestPermissionsCallPhone();
		}
		}
		else {
			callPhoneAction();
		}
	}

	private void callPhoneAction() {
		Intent in;

		Toast.makeText(this, getString(R.string.opcion5), Toast.LENGTH_LONG).show();
		in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + getText(R.string.telef)));
		startActivity(in);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
