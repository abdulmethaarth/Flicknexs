package com.webnexs.flicknexs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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


public class ForgotpasswordActivity extends AppCompatActivity {


    EditText email;
    Button submit;
    TextView title,goback;


    String changeurl=Base_URL+"resetpassword";
    private String resemail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        email=(EditText)findViewById(R.id.changeemail);
        submit=(Button) findViewById(R.id.changebutton);
        title=(TextView) findViewById(R.id.toolbar_title);
        goback=(TextView) findViewById(R.id.goback);


        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.backarrow);


        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getApplicationContext(),SigninActivity.class);
                startActivity(in);
            }
        });
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in=new Intent(getApplicationContext(),SigninActivity.class);
                startActivity(in);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(email.getText().toString().length()==0){
                    email.setError("Email not entered");
                    email.requestFocus();
                }

                final String email1=email.getText().toString();
                StringRequest request = new StringRequest(Request.Method.POST, changeurl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            resemail=jsonObject.getString("email");
                            if (jsonObject.getString("status").equalsIgnoreCase("true")) {

                                Intent In1=new Intent(getApplicationContext(),ForgotVerificationActivity.class);
                                In1.putExtra("email1",resemail);
                                startActivity(In1);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Enter your valid email id", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(ForgotpasswordActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("email",email1);
                        return parameters;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(ForgotpasswordActivity.this);
                queue.add(request);

            }



        });


    }

    @Override
    public void onBackPressed() {
        Intent in=new Intent(getApplicationContext(),SigninActivity.class);
        startActivity(in);
    }
}
