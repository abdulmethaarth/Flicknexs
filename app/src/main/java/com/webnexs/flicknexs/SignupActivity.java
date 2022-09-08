package com.webnexs.flicknexs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Callback;
import retrofit.RetrofitError;

import static com.webnexs.flicknexs.SigninActivity.Base_URL;


public class SignupActivity extends AppCompatActivity {

    EditText mobile, pass, con_pass,email;

    String URL=Base_URL+"register";
    String ispaymenturl=Base_URL+"isPaymentEnable";

    String payperkey=Base_URL+"payment_settings";

    public static String publishkey = "";


    Button signup;
    String x="COUNTRY";
    String y="STATE";
    String z="CITY";
    ViewPager viewPager;
    TextView signinn;
    private Bitmap bitmap;
    CircleImageView image;
    int PICK_IMAGE_REQUEST = 111;
    byte[] byteArray;
    UserListResponse signUpResponsesData;
    registerresponse regrespone;
    ImageView logo;




    String ss;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        mobile = (EditText) findViewById(R.id.editText);
        pass = (EditText) findViewById(R.id.editText2);
        con_pass = (EditText) findViewById(R.id.editText3);
        signup = (Button) findViewById(R.id.button);
        email=(EditText)findViewById(R.id.editText4);
        signinn=(TextView) findViewById(R.id.textView3);
        logo=(ImageView)findViewById(R.id.logo);



        ApiClient.getClient().getUsersList(
                new Callback<UserListResponse>() {
                    @Override
                    public void success(UserListResponse signUpResponse, retrofit.client.Response response) {
                        signUpResponsesData = signUpResponse;


                        String x=signUpResponse.getImage_url();
                        String y=signUpResponse.getSettings();
                        String z=x+y;
                        Picasso.get().load(z).into(logo);


                    }

                    @Override
                    public void failure(RetrofitError error) {

                        Toast.makeText(SignupActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

                    }

                });


        StringRequest audiorequest= new StringRequest(Request.Method.GET, ispaymenturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


            try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("is_payment").equalsIgnoreCase("1")) {


            signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            final String username= mobile.getText().toString();
            final String userpassword= con_pass.getText().toString();
            final String email1= email.getText().toString();


            if(mobile.getText().toString().length()==0){
             mobile.setError("Userrname not entered");
             mobile.requestFocus();

             }

             else if(pass.getText().toString().length()==0){
             pass.setError("Password not entered");
             pass.requestFocus();
             }
            else if(email.getText().toString().length()==0){
            email.setError("E-Mail not entered");
            email.requestFocus();
         }
         else if(con_pass.getText().toString().length()==0){
         con_pass.setError( "confirm password not entrered");
         con_pass.requestFocus();
         }
          else if(!pass.getText().toString().equals(con_pass.getText().toString())){
          con_pass.setError("Password Not matched");
          con_pass.requestFocus();
          }


          else {
              RequestQueue queue = Volley.newRequestQueue(SignupActivity.this);
                StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        parseData(response);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(SignupActivity.this, "Check your Internet connection", Toast.LENGTH_LONG).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> map = new HashMap<String, String>();
                        map.put("username", username);
                        map.put("password", userpassword);
                        map.put("email",email1);

                        return map;
                    }
                };
                queue.add(request);

            }
            }
            });
            }

            else {

                signup.setText("Next");

                signup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String username1= mobile.getText().toString();
                        final String userpassword1= con_pass.getText().toString();
                        final String email2= email.getText().toString();


                        if(mobile.getText().toString().length()==0){
                            mobile.setError("Userrname not entered");
                            mobile.requestFocus();

                        }

                        else if(pass.getText().toString().length()==0){
                            pass.setError("Password not entered");
                            pass.requestFocus();
                        }
                        else if(email.getText().toString().length()==0){
                            email.setError("E-Mail not entered");
                            email.requestFocus();
                        }
                        else if(con_pass.getText().toString().length()==0){
                            con_pass.setError( "confirm password not entrered");
                            con_pass.requestFocus();
                        }
                        else if(!pass.getText().toString().equals(con_pass.getText().toString())){
                            con_pass.setError("Password Not matched");
                            con_pass.requestFocus();
                        }


                        else {

                            Intent in=new Intent(SignupActivity.this,SignupSubscribeActivity.class);
                            in.putExtra("username",mobile.getText().toString());
                            in.putExtra("email",email.getText().toString());
                            in.putExtra("password",con_pass.getText().toString());
                            startActivity(in);


                        }
                    }
                });
            }



       } catch (JSONException e) {
           e.printStackTrace();
         }

         }
        }, new Response.ErrorListener() {
           @Override
            public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(SignupActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();
           }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                return parameters;
            }
        };

        RequestQueue audiolistqueue = Volley.newRequestQueue(SignupActivity.this);
        audiolistqueue.add(audiorequest);

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();

            try {

                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                image.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private void parseData(String response) {


        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equalsIgnoreCase("true")) {

                Intent in=new Intent(getApplicationContext(),SigninActivity.class);
                startActivity(in);

            }

            else if (jsonObject.getString("status").equalsIgnoreCase("false")) {

                if (jsonObject.getString("message").equalsIgnoreCase("Email id Already Exists")) {
                    email.setError("Email Id already Exists");
                    Toast.makeText(getApplicationContext(), "Email Id already Exists", Toast.LENGTH_LONG).show();

                }
                else
                {
                    mobile.setError("Username already taken. Please choose aother username");
                    Toast.makeText(getApplicationContext(), "Username already taken. Please choose aother username", Toast.LENGTH_LONG).show();

                }
            }

            else {
                Toast.makeText(getApplicationContext(), "You are already registered user", Toast.LENGTH_SHORT).show();
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }




    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);

        byteArray= byteArrayOutputStream.toByteArray();

        return byteArrayOutputStream.toByteArray();
    }

    public void signin(View view) {

        Intent in=new Intent(getApplicationContext(),SigninActivity.class);
        startActivity(in);
    }


}





