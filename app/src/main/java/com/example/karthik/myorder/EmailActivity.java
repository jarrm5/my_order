package com.example.karthik.myorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class EmailActivity extends AppCompatActivity {

    private String orderSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        String orderSummary = unpackExtras();
        emailOrderToRecipient(orderSummary);
    }

    private String unpackExtras() {
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            return extras.getString("order");
            //this.setTitle("Welcome, " + username);
        }
        return getString(R.string.order_error);
    }

    private void emailOrderToRecipient(String order){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL  , new String[]{"rinellaj2448@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Pizza Time");
        intent.putExtra(Intent.EXTRA_TEXT   , order);
        try {
            startActivity(Intent.createChooser(intent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(EmailActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

}
