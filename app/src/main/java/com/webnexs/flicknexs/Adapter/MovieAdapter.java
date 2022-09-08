package com.webnexs.flicknexs.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.webnexs.flicknexs.Model.Movie;
import com.webnexs.flicknexs.MoviesActivity;
import com.webnexs.flicknexs.R;
import com.webnexs.flicknexs.payperviewenableActivity;
import com.webnexs.flicknexs.ssharedpreferences;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.webnexs.flicknexs.SigninActivity.Base_URL;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {





    private List<Movie> movieList;
    private Context context;
    Movie movie;
    String videodetailsurl=Base_URL+"movie";
    private static SharedPreferences prefs;
    String user_id;




    public MovieAdapter(List<Movie> list, Context context) {
        this.movieList = list;
        this.context = context;

        prefs = context.getSharedPreferences(ssharedpreferences.My_preference_name, 0);
         user_id=prefs.getString(ssharedpreferences.user_id,null);

    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MovieViewHolder(LayoutInflater.from(context).inflate(R.layout.row_layout_movie, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, final int position) {

        movie= movieList.get(position);


        holder.textViewTitle.setText(movie.getId());



        //String txt=movie.getId();


       holder.textViewGenre.setText(movie.getPpv_status());

        Picasso.get().
                load(context.getResources().getString(R.string.image_url) + movie.getImage())
                .into(holder.imageViewMovie);





    }

    @Override
    public int getItemCount() {

        return movieList.size();

    }


    public class MovieViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private TextView textViewGenre;
        private ImageView imageViewMovie;


        public MovieViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.tv_title);
            textViewGenre = itemView.findViewById(R.id.tv_genre);
            imageViewMovie = itemView.findViewById(R.id.image_view_movie);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (textViewGenre.getText().toString().equals("1")) {


                        StringRequest request4 = new StringRequest(Request.Method.POST, videodetailsurl, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getString("ppv_status").equalsIgnoreCase("expired")) {

                                        Intent in = new Intent(context, payperviewenableActivity.class);
                                        in.putExtra("id", textViewTitle.getText().toString());
                                        context.startActivity(in);

                                    } else if (jsonObject.getString("ppv_status").equalsIgnoreCase("pay_now")) {
                                        Intent in = new Intent(context, payperviewenableActivity.class);
                                        in.putExtra("id",textViewTitle.getText().toString() );
                                        context.startActivity(in);
                                    } else {
                                        Intent in = new Intent(context, MoviesActivity.class);
                                        in.putExtra("id", textViewTitle.getText().toString());
                                        context.startActivity(in);
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Toast.makeText(context, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> parameters = new HashMap<String, String>();
                                parameters.put("id",textViewTitle.getText().toString() );
                                parameters.put("user_id", user_id);
                                return parameters;
                            }
                        };

                        RequestQueue queue4 = Volley.newRequestQueue(context);
                        queue4.add(request4);


                    } else {
                        Intent intent = new Intent(context, MoviesActivity.class);
                        intent.putExtra("id", textViewTitle.getText().toString());
                        context.startActivity(intent);
                    }





                }
            });






        }




    }
}