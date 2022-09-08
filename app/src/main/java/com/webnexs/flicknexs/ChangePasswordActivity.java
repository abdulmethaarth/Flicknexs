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


public class ChangePasswordActivity extends AppCompatActivity {


    Button verifybutton;
    String verifyurl=Base_URL+"verifyandupdatepassword";
    EditText password,confirmpass,oldpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);



        SharedPreferences prefs = getSharedPreferences(ssharedpreferences.My_preference_name, MODE_PRIVATE);
        final String userid = prefs.getString(ssharedpreferences.user_id,null );
        final String emailid=prefs.getString(ssharedpreferences.email,null);


        verifybutton=(Button)findViewById(R.id.submit);
        password=(EditText)findViewById(R.id.password1);
        confirmpass=(EditText)findViewById(R.id.confirmpassword1) ;
        oldpass=(EditText)findViewById(R.id.oldpass);


        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.backarrow);


        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getApplicationContext(),MyAccountActivity.class);
                startActivity(in);
            }
        });


        verifybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(oldpass.getText().toString().length()==0){
                    oldpass.setError("Old password not entered");
                    oldpass.requestFocus();
                }

                else if(password.getText().toString().length()==0){
                    password.setError("Password not entered");
                    password.requestFocus();
                }
                else if(confirmpass.getText().toString().length()==0){
                    confirmpass.setError("Confirm password not entered");
                    confirmpass.requestFocus();
                }
                else if(!password.getText().toString().equals(confirmpass.getText().toString())){
                    confirmpass.setError("Password Not matched");
                    confirmpass.requestFocus();
                }

                else {


                    final String pass=password.getText().toString();
                    final String conpass=confirmpass.getText().toString();
                    final String oldpass1=oldpass.getText().toString();

                    StringRequest request = new StringRequest(Request.Method.POST, verifyurl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                                    Toast.makeText(getApplicationContext(),"Password Changed Successfully", Toast.LENGTH_LONG).show();
                                    Intent In1 = new Intent(getApplicationContext(), MyAccountActivity.class);
                                    startActivity(In1);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Check your old password", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(ChangePasswordActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("email", emailid);
                            parameters.put("password", pass);
                            parameters.put("old_password",oldpass1);
                            return parameters;
                        }
                    };
                    RequestQueue queue = Volley.newRequestQueue(ChangePasswordActivity.this);
                    queue.add(request);
                    queue.getCache().clear();

                }
            }
        });

    }

    @Override
    public void onBackPressed() {

        Intent in=new Intent(getApplicationContext(),MyAccountActivity.class);
        startActivity(in);
    }
}
