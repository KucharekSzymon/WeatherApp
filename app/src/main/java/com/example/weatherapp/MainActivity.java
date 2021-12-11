package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.*;

import java.io.*;
import java.net.*;


public class MainActivity extends AppCompatActivity {
    Button btnHit;
    TextView txtJson;
    ProgressDialog pd;
    EditText search;
    String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnHit = (Button) findViewById(R.id.button);
        txtJson = (TextView) findViewById(R.id.textView);
        search = (EditText) findViewById(R.id.editTextTextPersonName);

        btnHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!search.getText().toString().equals("")) {
                    JsonTask x = new JsonTask();
                    x.execute("https://api.openweathermap.org/data/2.5/weather?q=" + search.getText().toString() + "&appid=9001038ad02ff68e0a10d7337787d7bb&units=metric");
                    //new JsonTask().execute("https://api.openweathermap.org/data/2.5/weather?q=" + search.getText().toString() + "&appid=9001038ad02ff68e0a10d7337787d7bb&units=metric");
                }else
                    Toast.makeText(getApplicationContext(),"Incorrect data",Toast.LENGTH_SHORT).show();
            }
        });


    }


    private class JsonTask extends AsyncTask<String, String, String> {
        public String ico;

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }
            try {
                JSONObject jObject = new JSONObject(result);
                JSONObject main = jObject.getJSONObject("main");
                JSONArray jArray = jObject.getJSONArray("weather");
                JSONObject oneObject = jArray.getJSONObject(0);
                ConstraintLayout x = findViewById(R.id.weather);
                x.setVisibility(ConstraintLayout.VISIBLE);
                TextView name = findViewById(R.id.name);
                TextView temp = findViewById(R.id.temp);
                TextView feels_like = findViewById(R.id.feels_like);
                TextView description = findViewById(R.id.description);
                TextView pressure = findViewById(R.id.pressure);
                TextView humidity = findViewById(R.id.humidity);
                TextView temp_min = findViewById(R.id.temp_min);
                TextView temp_max = findViewById(R.id.temp_max);
                ImageView icon = findViewById(R.id.imageView3);

                name.setText(jObject.getString("name"));
                temp.setText(main.getString("temp")+"°C");
                feels_like.setText("Feels like: "+main.getString("feels_like"));
                description.setText(oneObject.getString("description"));
                pressure.setText(main.getString("pressure")+" hPa");
                humidity.setText("Humidity: "+main.getString("humidity")+"%");
                temp_min.setText("Min temp: "+main.getString("temp_min")+"°C");
                temp_max.setText("Max temp: "+main.getString("temp_max")+"°C");
                ico = "i"+oneObject.getString("icon");
                switch (ico){
                    case "i01d":
                        icon.setImageResource(R.drawable.i01d);
                        break;
                    case "i01n":
                        icon.setImageResource(R.drawable.i01n);
                        break;
                    case "i03d":
                        icon.setImageResource(R.drawable.i02d);
                        break;
                    case "i03n":
                        icon.setImageResource(R.drawable.i02n);
                        break;
                    case "i04d":
                        icon.setImageResource(R.drawable.i02d);
                        break;
                    case "i04n":
                        icon.setImageResource(R.drawable.i02n);
                        break;
                    case "i09d":
                        icon.setImageResource(R.drawable.i02d);
                        break;
                    case "i09n":
                        icon.setImageResource(R.drawable.i02n);
                        break;
                    case "i10d":
                        icon.setImageResource(R.drawable.i02d);
                        break;
                    case "i10n":
                        icon.setImageResource(R.drawable.i02n);
                        break;
                    case "i11d":
                        icon.setImageResource(R.drawable.i02d);
                        break;
                    case "i11n":
                        icon.setImageResource(R.drawable.i02d);
                        break;
                    case "i13d":
                        icon.setImageResource(R.drawable.i02d);
                        break;
                    case "i13n":
                        icon.setImageResource(R.drawable.i02d);
                        break;
                    case "i50d":
                        icon.setImageResource(R.drawable.i02d);
                        break;
                    case "i50n":
                        icon.setImageResource(R.drawable.i02d);
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}