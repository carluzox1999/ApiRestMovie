package com.example.apirestmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String
            MOVIE_BASE_URL = "https://image.tmdb.org/t/p/w500/kqjL17yufvn9OVLyXYpvtyrFfak.jpg";
    public static final String
            endPoint =  "https://api.themoviedb.org/3/discover/movie?api_key=8939173db9446f4d1914e21394ffdbde&language=es";

    private ListView listView;
    private ArrayList<Pelicula> listaPeliculas = new
            ArrayList<Pelicula>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        PeliculasAsync pa = new PeliculasAsync();
        pa.execute(endPoint);
    }

    /*
    * Clase para leer peliculas
    * */

    class PeliculasAsync extends AsyncTask<String, Integer, String> {
        protected void onPreExecute (){
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {
            StringBuilder result = new StringBuilder();

            try{
                URL urlObj = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection)
                        urlObj.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                InputStream in = new
                        BufferedInputStream(conn.getInputStream());
                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(in));
                String line;

                while((line = reader.readLine()) !=null)
                    result.append(line);

                Log.d("test", "respuesta: " + result.toString());

            }catch (Exception e) {
                Log.d("test", "error2: " + e.toString());
            }

            return result.toString();
        }

        protected void onProgressUpdate(Integer... a){
            super.onProgressUpdate(a);
        }

        protected void onPostExecute(String result){
            super.onPostExecute(result);

            Log.d("test", "Result from server: " + result);

            // Poner las peliculas
            JSONObject resp = null;
            JSONArray peliculas = null;

            ArrayList<String> peliculasList = new ArrayList<String>();

            try{
                resp = new JSONObject(result);
                peliculas = resp.getJSONArray("results");

                for (int i=0; i< peliculas.length(); i++){
                    JSONObject pelicula = peliculas.getJSONObject(i);

                    Log.d("test", "pelicula: " +
                            pelicula.getString("title"));

                    peliculasList.add(
                            pelicula.getString("release_date") + " - " +
                                    pelicula.getString("title")
                    );

                    listaPeliculas.add(
                            new Pelicula(pelicula.getString("title"),
                                    pelicula.getString("backdrop_path"))
                    );
                }
            }catch (JSONException e){
                e.printStackTrace();
            }

            try{
                AdaptadorPelicula adaptador = new
                        AdaptadorPelicula(getApplicationContext(), listaPeliculas);

                listView.setAdapter(adaptador);

            }catch (Exception e){
                Log.d("test", "pelicula: error " + e.getMessage());
            }
        }
    }

    class AdaptadorPelicula extends BaseAdapter {

        Context context;
        ArrayList<Pelicula> arrayList;

        public AdaptadorPelicula(Context context, ArrayList<Pelicula> arrayList) {
            this.context = context;
            this.arrayList = arrayList;
        }

        public int getCount() {
            return arrayList.size();
        }

        public Pelicula getItem(int position) {
            return arrayList.get(position);
        }

        public long getItemId(int i) {
            return i;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView =
                        LayoutInflater.from(context).inflate(R.layout.pelicula, parent, false);

            }

            // Titulo
            TextView name = (TextView)
                    convertView.findViewById(R.id.title);
            name.setText(arrayList.get(position).getTitulo());

            // Imagen.
            ImageView imagen = (ImageView)
                    convertView.findViewById(R.id.list_image);
            Picasso.get().load(MOVIE_BASE_URL +
                    arrayList.get(position).getImagen()).into(imagen);
            imagen.setScaleType(ImageView.ScaleType.FIT_XY);

            return convertView;
        }
    }
}