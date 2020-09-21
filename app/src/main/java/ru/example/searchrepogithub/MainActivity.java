package ru.example.searchrepogithub;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
        Button search,filters,clearFilters;
        EditText searchText,sizeOfRepo,forksSize, languageName;
        Boolean showFilters = false;
        Spinner sortFilter;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            //search option elements
            search = (Button) findViewById(R.id.search);
            filters = (Button) findViewById(R.id.showFilters);
            searchText = (EditText) findViewById(R.id.searchText);
            clearFilters = (Button) findViewById(R.id.clearFilters);
           
            //sortBy filter element
            sortFilter = (Spinner) findViewById(R.id.sortFilter);
                     
            //Add value  spinners=========================================================================                                                               
                                 

            String[] e = new String[]{"Best Match", "stars", "forks", "updated"};
            ArrayAdapter<String> adapter5 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, e);
            sortFilter.setAdapter(adapter5);
                                      //======================================================================================================
            //initially hide filters by default
            final RelativeLayout filters_layout = (RelativeLayout) findViewById(R.id.filters_layout);
            filters_layout.setVisibility(View.INVISIBLE);

            //show and hide filters
            filters.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //hide keyboard
                    View view = MainActivity.this.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }

                    if (!showFilters) {
                        clearAllFilter();
                        filters_layout.setVisibility(View.VISIBLE);
                        showFilters = true;
                    } else {
                        clearAllFilter();
                        filters_layout.setVisibility(View.INVISIBLE);
                        showFilters = false;
                    }
                }
            });

            clearFilters.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //hide keyboard
                    View view = MainActivity.this.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    clearAllFilter();
                }
            });

            //search for repo
            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //hide keyboard
                    View view = MainActivity.this.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }

                    //check if input string is empty
                    String txt = searchText.getText().toString();
                    if(txt.isEmpty()){
                        Toast.makeText(MainActivity.this,"Search Box can't be Empty!!", Toast.LENGTH_LONG ).show();
                        return;
                    }

                    //check if Internet is available
                    if(!isInternetConnected(getBaseContext())){
                        Toast.makeText(MainActivity.this, "No Internet Connection!",Toast.LENGTH_SHORT).show();
                        return;
                    }


                    //Since GitHub api provide flexibility to use filters, we just need to manipulate the searchText that we will send to API

                    
//                    String x = sizeOfRepo.getText().toString();
//                    String x3 = "";
//                    String x4 = "";
//                    if(!x.isEmpty()){
//                        String x1 = limitSizeSpinner.getSelectedItem().toString();
//                        if(x1.equals("Less than")){
//                            x4 += "<";
//                        }else{
//                            x4 += ">";
//                        }
//
//                        int x2 = sizeDimenSpinner.getSelectedItemPosition();
//                        if(x2==1){
//                            x3 += String.valueOf(Integer.parseInt(x)*1024);
//                        }else if(x2==2){
//                            x3 += String.valueOf(Integer.parseInt(x)*1024*1024);
//                        }else{
//                            x3 += x;
//                        }
//                        txt+=("+size:"+x4+x3);
//                    }
//
//                    //manipulation for forks filter
//                    String y = forksSize.getText().toString();
//                    if(!y.isEmpty()){
//                        String y1 = forkLimitSpinner.getSelectedItem().toString();
//                        if(y1.equals("More than")){
//                            txt+="+forks:>";
//                        }else{
//                            txt+="+forks:<";
//                        }
//
//                        txt+=y;
//                    }
//
//                    //manipulation for language filter
//                    String z = languageName.getText().toString();
//                    if(!z.isEmpty()){
//                        String z1 = languageIncludeSpinner.getSelectedItem().toString();
//                        if(z1.equals("Exclude")){
//                            txt+=("+-language:"+z);
//
//                        }else{
//                            txt+=("+language:"+z);
//                        }
//                    }

                    //manipulation for SortBy filter
                    String w = sortFilter.getSelectedItem().toString();
                    if(!w.equals("Best Match")){
                        txt+=("&sort="+w);
                    }

//                    //manipulation for Order filter
//                    String w1 = orderBy.getSelectedItem().toString();
//                    if(w1.equals("Asc")){
//                        txt+="&order=asc";
//                    }else{
//                        txt+="&order=desc";
//                    }
//
//                    Log.e("finalsearchtextstring:", txt);

                    //limit per page data to 5 entries
                    txt+="&per_page=5";


                    Intent i = new Intent(MainActivity.this, Search_Result.class);
                    i.putExtra("searchText",txt);
                    Log.i("searchurlfrMain", txt);
                    startActivity(i);
                }
            });
        }

        public static boolean isInternetConnected(Context context) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }

        private void clearAllFilter() {
//            sizeOfRepo.setText("");
//            forksSize.setText("");
//            languageName.setText("");
//            sortFilter.setSelection(0);
//            orderBy.setSelection(0);
        }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {

            getMenuInflater().inflate(R.menu.menu_main_search, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            int id = item.getItemId();

            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }
    }


