package com.webnexs.flicknexs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.webnexs.flicknexs.SigninActivity.Base_URL;


public class EditProfileActivity extends AppCompatActivity {

    private static final int GALLERY_REQUEST_CODE = 123;
    private static final String URL_TO_SHARE ="welcome" ;
    EditText name,username,mobile,id,email;

    CircleImageView image;
    ImageView img;
    Button update,changepassword;
    int PICK_IMAGE_REQUEST = 111;
    String xx;
    String URL=Base_URL+"update_profile";
    String URL1=Base_URL+"view_profile";
    String ImageURL=" http://flicknexs.webnexs.org/content/uploads/avatars/";





    private String username1,mobile1,id1,password1,name1,image11,str_email1;
    private Bitmap bitmap,xxxx;
    private String filePath;
    private Object Context;

    TextView pass;
    String a1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        SharedPreferences prefs = getSharedPreferences(ssharedpreferences.My_preference_name, MODE_PRIVATE);

        final String user_id = prefs.getString(ssharedpreferences.user_id,null );
        final String email1 =prefs.getString(ssharedpreferences.email,null);
        String usernamee =prefs.getString(ssharedpreferences.username,null);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.backarrow);


        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getApplicationContext(),MyAccountActivity.class);
                startActivity(in);
            }
        });


        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        id = (EditText) findViewById(R.id.id);

        image = (CircleImageView) findViewById(R.id.profile_image1);
        update = (Button) findViewById(R.id.update);
        pass=(TextView)findViewById(R.id.pass);


       // email.setInputType(InputType.TYPE_NULL);
       // username.setInputType(InputType.TYPE_NULL);


        StringRequest viewprofilereq = new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {

                        JSONArray dataArray = jsonObject.getJSONArray("user_details");
                        for (int i = 0; i < dataArray.length(); i++) {


                            JSONObject dataobj = dataArray.getJSONObject(i);

                            String x = dataobj.getString("id");
                            String y = dataobj.getString("username");
                            String z = dataobj.getString("email");
                            a1 = dataobj.getString("avatar");
                          //  String c=dataobj.getString("name");
                          //  String d=dataobj.getString("mobile");





                            xx = ImageURL + a1;


                            Picasso.get().load(xx).into(image);
                            email.setText(z);
                            username.setText(y);
                         //   name.setText(c);
                           // mobile.setText(d);


                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(EditProfileActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("user_id", user_id);


                return parameters;
            }
        };


        RequestQueue queue1 = Volley.newRequestQueue(EditProfileActivity.this);
        queue1.add(viewprofilereq);
        queue1.getCache().clear();





        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
            }
        });







        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username1 = username.getText().toString();
                str_email1 = email.getText().toString();
                id1 = id.getText().toString();




                    if(bitmap == null)
                    {

                        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
                        final Bitmap bitmap1 = drawable.getBitmap();

                        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URL,
                                new Response.Listener<NetworkResponse>() {
                                    @Override
                                    public void onResponse(NetworkResponse response) {
                                        try {
                                            JSONObject obj = new JSONObject(new String(response.data));

                                            Toast.makeText(getApplicationContext(), "Profile updated Successfully", Toast.LENGTH_SHORT).show();
                                            Intent in = new Intent(getApplicationContext(), MyAccountActivity.class);
                                            startActivity(in);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getApplicationContext(),"Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                                    }
                                }) {


                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("user_id", user_id);
                                params.put("username", username1);
                                params.put("email", str_email1);
                                return params;
                            }

                            @Override
                            protected Map<String, DataPart> getByteData() {
                                Map<String, DataPart> params = new HashMap<>();
                                long imagename = System.currentTimeMillis();

                                params.put("avatar", new DataPart(imagename + ".jpg", getFileDataFromDrawable(bitmap1)));
                                return params;
                            }
                        };


                        Volley.newRequestQueue(EditProfileActivity.this).add(volleyMultipartRequest);

                    }

                    else {
                        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URL,
                                new Response.Listener<NetworkResponse>() {
                                    @Override
                                    public void onResponse(NetworkResponse response) {
                                        try {
                                            JSONObject obj = new JSONObject(new String(response.data));

                                            Toast.makeText(getApplicationContext(), "Profile updated Successfully", Toast.LENGTH_SHORT).show();
                                            Intent in = new Intent(getApplicationContext(), MyAccountActivity.class);
                                            startActivity(in);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getApplicationContext(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                                    }
                                }) {


                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("user_id", user_id);
                                params.put("username", username1);
                                params.put("email", str_email1);

                                return params;
                            }

                            @Override
                            protected Map<String, DataPart> getByteData() {
                                Map<String, DataPart> params = new HashMap<>();
                                long imagename = System.currentTimeMillis();
                                params.put("avatar", new DataPart(imagename + ".jpg", getFileDataFromDrawable(bitmap)));
                                return params;
                            }
                        };


                        Volley.newRequestQueue(EditProfileActivity.this).add(volleyMultipartRequest);

                    }
            }
        });
        }

    private byte[] getFileDataFromDrawable1(Bitmap xx) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        xx.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
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

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    @Override
    public void onBackPressed() {
        Intent in=new Intent(getApplicationContext(),MyAccountActivity.class);
        startActivity(in);
    }

    }

