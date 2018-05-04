/*Volley is a library that makes networking for Android apps easier and most importantly, faster*
It manages the processing and caching of network requests and it saves developers valuable time from writing the same network call/cache code again and again.
And one more benefit of having less code is less number of bugs and that’s all developers want and aim for
Two Main Classes of Volley:
There are 2 main classes:
1. Request queue
2. Request
Request queue: It is the interest you use for dispatching requests to the network, you can make a request queue on demand if you want, but typically, you’ll instead create it early on, at startup time, and keep it around and use it as a Singleton.
Request: It contains all the necessary details for making web API call. For example: which method to Use (GET or POST), request data to pass, response listener, error listener.
dependencies {
    ...
    compile 'com.android.volley:volley:1.1.0'
}
2.You can also clone the Volley repository and set it as a library project:
Git clone the repository by typing the following at the command line:

git clone https://github.com/google/volley
Import the downloaded source into your app project as an Android library module as described in Create an Android Library.
3.Steps:-Adding Internet Permission
Open the AndroidManifest.xml
||@Adding Volley Library
Just open your app level build.gradle file.
||Data Model Class
To store the fetched data in objects we will create a simple model class. So create a class named Hero.java and write the following code.
||Creating a ListView
As I told you already that we will display the fetched data in a ListView. And here we are going to create a custom ListView.
So first inside activity_main.xml create a ListView.
||Custom Layout for List Items
Inside the layout directory (res->layout) create a new layout resource file named list_items.xml.
||Fetching and Parsing JSON using Volley
Now come inside MainActivity.java and write

https://www.simplifiedcoding.net/android-volley-tutorial-fetch-json/
https://developer.android.com/training/volley/simple#java
 */









package com.example.hp.parentteacherapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class PLogin extends AppCompatActivity implements View.OnClickListener {
    Button button_login;
    EditText editText_password, editText_email;
    TextView textView_forgot;

    String tdemail, tdpassword, name, email, regid, url;
    //volley library decla
    //// Instantiate the RequestQueue.
    RequestQueue queue;
    //  queue = Volley.newRequestQueue(this);
    String TAG = "courserequest";
    static Set<String> sregid = new HashSet<String>();//In JSON for add

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plogin);
        /////////////////////////////////////////2222222222
        button_login = findViewById(R.id.login);

        editText_password = findViewById(R.id.password);
        editText_email = findViewById(R.id.email);
        textView_forgot = findViewById(R.id.forgot_password);

        button_login.setOnClickListener(this);
        textView_forgot.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        Initialize();

        switch (v.getId()) {

            case R.id.login:
                if(validations())
                {
                queue = Volley.newRequestQueue(this);/*Q1.*/
                url = "http://parportal.000webhostapp.com/login.php?act=Parent_Login&email=vaibhavgupta1609@gmail.com&password=vaibhav";
                final ProgressDialog pDialog = new ProgressDialog(this);
                pDialog.setMessage("Loading...");
                pDialog.show();

                Log.e("==url==", "" + url);
                //// Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        /* Implement methods*/   new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //e.g.  // Display the first 500 characters of the response string.
                        Log.e("===response==", "==" + response);  /*Q2 */
                        pDialog.dismiss();

                        try {
                            JSONObject jobj = new JSONObject(response);
                            String success = jobj.getString("result");//No need to write success just for check
                            JSONArray jarr = jobj.getJSONArray("data");
                            for (int i = 0; i < jarr.length(); i++) {
                                JSONObject jobj1 = jarr.getJSONObject(i);
                                String name = jobj1.getString("first_name");
                                String email = jobj1.getString("email");
                                String reg_id = jobj1.getString("stu_reg_id");
                                sregid.add(reg_id);/*Q4*/

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {//Implement methods
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("===error==", "==" + error);
                        pDialog.dismiss();
                    }
                });

                stringRequest.setTag(TAG);

                // Add the request to the RequestQueue.
                queue.add(stringRequest);
        }


                else {
                    Toast.makeText(getApplicationContext(),"Something Went Wrong",Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.forgot_password:
                Intent intent = new Intent(PLogin.this, Forgot.class);
                startActivity(intent);
                break;
        }


        }


        //http://parportal.000webhostapp.com/login.php?act=Parent_Details&stu_reg_id=1501
        //  String url = URLSettup.url + "act=Parent_Login&email=" + tdemail + "&password=" + tdpassword;
        // URLSettup {
        //
        //    public static String url = "http://parportal.000webhostapp.com/login.php?";
        //http://parportal.000webhostapp.com/login.php?act=Parent_Login&email=aman@gmail.com
        //Working
        //http://parportal.000webhostapp.com/login.php?act=Parent_Login&email=vaibhavgupta1609@gmail.com&password=vaibhav
                /*
                {
"result":"Login Successfull",
"data":[
{
"first_name":"Vaibhav",
"last_name":"Gupta",
"stu_reg_id":"1501",
"phone_no":"9467165501",
"address":"Ambala",
"email":"vaibhavgupta1609@gmail.com"
},
{
"first_name":"Vaibhav",
"last_name":"Gupta",
"stu_reg_id":"1502",
"phone_no":"9467165501",
"address":"Ambala",
"email":"vaibhavgupta1609@gmail.com"
}
]
}*/


        //GETTING DATA FROM EDITTEXT AND STORING THEM IN STRING
        private void Initialize () {
            tdemail = editText_email.getText().toString();
            tdpassword = editText_password.getText().toString();

        }
        private boolean validations () {

            boolean value = true;
            if (tdemail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(tdemail).matches()) {
                editText_email.setError("Required Field");
                value = false;
            }
            if (tdpassword.isEmpty()) {
                editText_password.setError("Required Field");
                value = false;
            }
            return value;
        }

    }

















