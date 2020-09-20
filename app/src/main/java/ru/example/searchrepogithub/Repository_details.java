package ru.example.searchrepogithub;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Repository_details extends AppCompatActivity {

    ListView urlReceivedEventListView;

    String urlString;
    String message;
    String actorLogin;
    String actorUrlImage;
    String autorRepo;
    String namerPart3;
    String namerPart31;
    String urlStargaserzString;

    Intent intent;
    Intent intent2;

    String messageArray[];
    String  actorLoginArray[];
    String actorUrlImageArray[];

    MyAdapter adapter3;


    public void clickButtonIntent(View view) {
        startActivity(intent2);
//
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repository_details);


        String repo = getIntent().getStringExtra("repo");
        Log.i("repo", repo);
        intent = new Intent(Repository_details.this, ChartActivity.class);
        intent.putExtra("repo", repo);

        urlReceivedEventListView = (ListView) findViewById(R.id.urlReceivedEventListView);

        String[] stargazersUrl = repo.split("\",\"full_name\":\"");
        String stargazersUrl2 = stargazersUrl[0];
        String[] stargazersUrl3 = stargazersUrl2.split(",\"name\":\"");
        String stargazersUrl4 = stargazersUrl3[1];



        String[] namer = repo.split(",\"node_id\":\"");

        String namerPart = namer[0];
//        Log.i("namerPart",namerPart);
        String[] namerPart2 = namerPart.split("id\":");
        namerPart3 = namerPart2[1];
        Log.i("!!!namerPart3 =napart31", namerPart3);
        String[] parts = repo.split("owner");
        String repoPart = parts[1];
        String[] parts2 = repoPart.split("login");
        String repoPart2 = parts2[1];
        String[] parts3 = repoPart2.split("id");
        String repoPart3 = parts3[0];
        String[] parts4 = repoPart3.split("\":\"");
        String repoPart4 = parts4[1];
        String[] parts5 = repoPart4.split("\",\"");
        autorRepo = parts5[0];


        urlString = "https://api.github.com/users/" + autorRepo + "/received_events?page=1&per_page=100";
        urlStargaserzString = "https://api.github.com/repos/" + autorRepo + "/" + stargazersUrl4 + "/stargazers?page=1&per_page=100";

        DownloadTask task = new DownloadTask();
        task.execute();

    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urlss) {

            message = "";
            actorLogin = "";
            actorUrlImage = "";
            URL url;
            HttpURLConnection urlConnection = null;
            InputStream inputStream;
            String response = "";

            try {
                url = new URL(urlString);

                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("GET");

                urlConnection.setDoInput(true);
                urlConnection.connect();

                int httpStatus = urlConnection.getResponseCode();
                Log.e("httpstatus", "The response is: " + httpStatus);

                //if HTTP response is 200 i.e. HTTP_OK read inputstream else read errorstream
                if (httpStatus != HttpURLConnection.HTTP_OK) {
                    inputStream = urlConnection.getErrorStream();
                    Map<String, List<String>> map = urlConnection.getHeaderFields();
                    System.out.println("Printing Response Header...\n");
                    for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                        System.out.println(entry.getKey()
                                + " : " + entry.getValue());
                    }
                } else {
                    inputStream = urlConnection.getInputStream();
                }

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;

                while ((temp = bufferedReader.readLine()) != null) {
                    response += temp;

                }
                Log.i("receive_events", response);

                JSONArray items = (JSONArray) new JSONTokener(response).nextValue();

                for (int i = 0; i < items.length(); i++) {

                    JSONObject jsonPart = items.getJSONObject(i);

                    String actorStr = "";
                    String created_at = "";

                    String repoStr = "";
                    repoStr = jsonPart.getString("repo");
                    String[] namer2 = repoStr.split(",\"name\":\"");

                    String namerPart22 = namer2[0];
                    String[] namerPart21 = namerPart22.split("id\":");

                    namerPart31 = namerPart21[1];
                    Log.i("!namerPart31= rpart3", namerPart31);


                    actorStr = jsonPart.getString("actor");

                    String[] actorUrl = actorStr.split("id\":");
                    String actorUrl1 = actorUrl[1];
                    String[] actorUrl2 = actorUrl1.split(",\"login\":\"");
                    String actorUrl3 = actorUrl2[0];
                    String actorUrl4 = "https://avatars.githubusercontent.com/u/"+actorUrl3 + "?v=4";


                    String[] actorParts = actorStr.split("gin\":\"");
                    String actorPart1 = actorParts[1];
                    String[] avatorUrl = actorStr.split("avatar_url\":\"");
                    String avatorUrl2 = avatorUrl[1];

//                    String avatorUrl3 = avatorUrl2.substring(0, avatorUrl2.length() - 1);
                    String[] actorParts2 = actorPart1.split("\",\"dis");
                    String loginActorPart2 = actorParts2[0];


                    created_at = jsonPart.getString("created_at");
                    String[] createdParts = created_at.split("T");
                    String createdPart = createdParts[0];

                    if (namerPart31.contains(namerPart3)  ) {
                        actorLogin +=loginActorPart2 + ";";
                        actorUrlImage += actorUrl4 + ";";
                        message += createdPart + ";";

                    }

                }

                urlConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);

            List<String> adapterList = new ArrayList<>();

             messageArray = message.split(";");

            actorLoginArray= actorLogin.split(";");
            for (int i = 0; i < actorLoginArray.length; i++) {
//                adapterList.add(i + 1 + ")  " + messageArray[i]);

                Log.i("!!!!!actorLoginArray", actorLoginArray[i]);
            }
                 actorUrlImageArray =  actorUrlImage.split(";");

            intent2 = new Intent(Repository_details.this, ChartActivity.class);
            intent2.putExtra("date", message);

            adapter3 = new MyAdapter(getBaseContext(),actorLoginArray,messageArray,actorUrlImageArray);

            urlReceivedEventListView.setAdapter(adapter3);

        }
    }
}

    class MyAdapter extends ArrayAdapter {
        String[] name;
        String[] date;
        String[] imageUrl;

        public MyAdapter(Context context,
                         String[] name1,
                         String[] date1,
                         String[] imageUrl1
        ) {
            super(context, R.layout.list_item);
            this.name = name1;
            this.date = date1;
            this.imageUrl = imageUrl1;
        }

        @Override
        public int getCount() {
            return date.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater =(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.list_item, parent,false);

            ImageView imageView2 = row.findViewById(R.id.imageView2);
            TextView textName = row.findViewById(R.id.textName) ;
            TextView textDate= row.findViewById(R.id.textDate);
            Picasso.get().load(imageUrl[position]).into(imageView2);
            textName.setText(name[position]);
            textDate.setText(date[position]);
            return row;


    }
}









