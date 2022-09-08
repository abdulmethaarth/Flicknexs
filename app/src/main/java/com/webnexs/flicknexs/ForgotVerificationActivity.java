package com.webnexs.flicknexs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.webnexs.flicknexs.SigninActivity.Base_URL;


public class ForgotVerificationActivity extends AppCompatActivity {

    EditText verifycode;
    Button verifybutton;
    String verifyurl=Base_URL+"updatepassword";
    EditText password;
    private String resemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_verification);


        SharedPreferences prefs = getSharedPreferences(ssharedpreferences.My_preference_name, MODE_PRIVATE);
        final String userid = prefs.getString(ssharedpreferences.user_id,null );
        final String emailid=prefs.getString(ssharedpreferences.email,null);

        verifycode=(EditText)findViewById(R.id.verifycode);
        verifybutton=(Button)findViewById(R.id.submit);
        password=(EditText)findViewById(R.id.password1);



        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.backarrow);


        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getApplicationContext(),SigninActivity.class);
                startActivity(in);
            }
        });



        Intent in=getIntent();
        resemail=in.getStringExtra("email1");

        verifybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(verifycode.getText().toString().length()==0){
                    verifycode.setError("verification code not entered");
                    verifycode.requestFocus();
                }

                else if(password.getText().toString().length()==0){
                    password.setError("Password not entered");
                    password.requestFocus();
                }


                else {

                    final String verificationcode=verifycode.getText().toString();
                    final String pass=password.getText().toString();


                    StringRequest request = new StringRequest(Request.Method.POST, verifyurl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                if (jsonObject.getString("status").equalsIgnoreCase("true")) {


                                    Toast.makeText(getApplicationContext(),"Password Updated Successfully", Toast.LENGTH_LONG).show();

                                    Intent In1 = new Intent(getApplicationContext(), SigninActivity.class);
                                    startActivity(In1);

                                } else {
                                    Toast.makeText(getApplicationContext(), "Enter  valid verification code", Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(ForgotVerificationActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("verification_code", verificationcode);
                            parameters.put("email", resemail);
                            parameters.put("password", pass);



                            return parameters;
                        }
                    };
                    RequestQueue queue = Volley.newRequestQueue(ForgotVerificationActivity.this);
                    queue.add(request);
                    queue.getCache().clear();
                }
            }
        });

    }
}
