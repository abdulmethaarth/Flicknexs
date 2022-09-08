package com.webnexs.flicknexs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardMultilineWidget;
import com.stripe.android.view.StripeEditText;

public class SignupPaymentActivity extends AppCompatActivity {

    CardMultilineWidget cardMultilineWidget;
    Button save;
    StripeEditText s1;

    String user_id;
    TextView amount,period;
    CheckBox check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_payment);

        amount=(TextView)findViewById(R.id.amount);
        period=(TextView)findViewById(R.id.period);
        check=(CheckBox)findViewById(R.id.check);



        Intent in=getIntent();
        String x= in.getStringExtra("period");
        String y=  in.getStringExtra("amonut");

        amount.setText("You are payimg "+y+" for this transaction.");
        period.setText("for one "+x+"");


        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.blackarraow);
        s1=findViewById(R.id.cardholdername);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in=new Intent(getApplicationContext(),SignupSubscribeActivity.class);
                startActivity(in);
            }
        });


        SharedPreferences prefs = getSharedPreferences(ssharedpreferences.My_preference_name, MODE_PRIVATE);
        user_id = prefs.getString(ssharedpreferences.user_id, null);
        final String user_role = prefs.getString(ssharedpreferences.role, null);


        cardMultilineWidget = findViewById(R.id.card_input_widget);
        save =  findViewById(R.id.save_payment);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //final String username= s1.getText().toString();



                if(s1.getText().toString().length()==0){
                    s1.setError("card holder name not entered");
                    s1.requestFocus();
                    // Toast.makeText(getApplicationContext(),"please enter the mobile number",Toast.LENGTH_SHORT).show();
                }

                else if (!check.isChecked())
                {

                    check.setError("Accept terms and contition");
                    check.requestFocus();

                }
               else {
                    saveCard();
                }
            }
        });

    }

    private void saveCard() {

        Card card =  cardMultilineWidget.getCard();

        if(card == null){
            Toast.makeText(getApplicationContext(),"Invalid card", Toast.LENGTH_SHORT).show();
        }else {
            if (!card.validateCard()) {
                // Do not continue token creation.
                Toast.makeText(getApplicationContext(), "Invalid card", Toast.LENGTH_SHORT).show();
            } else {
                CreateToken(card);
            }
        }
    }

    private void CreateToken( Card card) {
        Stripe stripe = new Stripe(getApplicationContext(), getString(R.string.publishablekey));
        stripe.createToken(
                card,
                new TokenCallback() {
                    public void onSuccess(final Token token) {

                        // Send token to your server
                        Log.e("Stripe Token", token.getId());
                        Intent intent = new Intent();
                        intent.putExtra("card",token.getCard().getLast4());
                        intent.putExtra("stripe_token",token.getId());
                        intent.putExtra("cardtype",token.getCard().getBrand());
                        setResult(0077,intent);
                        finish();



                    }
                    public void onError(Exception error) {

                        // Show localized error message
                        Toast.makeText(getApplicationContext(),
                                error.getLocalizedMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );
    }


    @Override
    public void onBackPressed() {
        Intent in=new Intent(getApplicationContext(),SignupSubscribeActivity.class);
        startActivity(in);
    }
}

