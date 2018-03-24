package com.example.karthik.myorder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_TAG = "MainActivity";
    final int PIZZA_PRICE = 5;
    final int PINEAPPLE_PRICE = 1;
    final int ANCHOVY_PRICE = 3;
    final int ARTICHOKE_PRICE = 1;
    final int SAUSAGE_PRICE = 2;
    int quantity = 1;

    EditText userInputNameView;
    CheckBox checkBoxPineapple;
    CheckBox checkBoxAnchovy;
    CheckBox checkBoxArtichoke;
    CheckBox checkBoxSausage;
    TextView textViewQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userInputNameView = findViewById(R.id.user_input);
        checkBoxPineapple = findViewById(R.id.pineapple_checked);
        checkBoxAnchovy = findViewById(R.id.anchovy_checked);
        checkBoxArtichoke = findViewById(R.id.artichoke_checked);
        checkBoxSausage = findViewById(R.id.sausage_checked);
        textViewQuantity = findViewById(R.id.quantity_text_view);
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

       /* Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:47.6,-122.3"));
        if (intent.resolveActivity(getPackageManager()) !=null){
            startActivity(intent);
        }*/

        String userInputName = userInputNameView.getText().toString();
        boolean hasPineapple = checkBoxPineapple.isChecked();
        boolean hasAnchovy = checkBoxAnchovy.isChecked();
        boolean hasArtichoke = checkBoxArtichoke.isChecked();
        boolean hasSausage = checkBoxSausage.isChecked();

        //calculate and store the total price
        float totalPrice = calculatePrice(hasPineapple, hasAnchovy, hasArtichoke, hasSausage);
        //create and store the order summary
        String orderSummaryMessage = createOrderSummary(userInputName, hasPineapple, hasAnchovy, hasArtichoke, hasSausage, totalPrice);
        // Write the relevant code for making the buttons work(i.e impelement the implicit and explicit intents

        Intent intent = new Intent(MainActivity.this,EmailActivity.class);
        intent.putExtra("order",orderSummaryMessage);
        startActivity(intent);

    }
    private String boolToString(boolean bool){
        return bool?(getString(R.string.yes)):(getString(R.string.no));
    }

    private String createOrderSummary(String userInputName, boolean hasPineapple, boolean hasAnchovy,boolean hasArtichoke,boolean hasSausage, float price) {
        String orderSummaryMessage = getString(R.string.order_summary_name,userInputName) +"\n"+
                getString(R.string.order_summary_pineapple,boolToString(hasPineapple))+"\n"+
                getString(R.string.order_summary_anchovy,boolToString(hasAnchovy)) +"\n"+
                getString(R.string.order_summary_artichoke,boolToString(hasArtichoke)) +"\n"+
                getString(R.string.order_summary_sausage,boolToString(hasSausage)) +"\n"+
                getString(R.string.order_summary_quantity,quantity)+"\n"+
                getString(R.string.order_summary_total_price,price) +"\n"+
                getString(R.string.thank_you);
        return orderSummaryMessage;
    }


    /**
     * Method to calculate the total price
     *
     * @return total Price
     */
    private float calculatePrice(boolean hasPineapple, boolean hasAnchovy, boolean hasArtichoke,boolean hasSausage ) {
        int basePrice = PIZZA_PRICE;
        if (hasPineapple) {
            basePrice += PINEAPPLE_PRICE;
        }
        if (hasAnchovy) {
            basePrice += ANCHOVY_PRICE;
        }
        if (hasArtichoke) {
            basePrice += ARTICHOKE_PRICE;
        }
        if (hasSausage) {
            basePrice += SAUSAGE_PRICE;
        }
        return quantity * basePrice;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * This method increments the quantity of pizza by one
     *
     * @param view on passes the view that we are working with to the method
     */

    public void increment(View view) {
        if (quantity < 100) {
            quantity = quantity + 1;
            display(quantity);
        } else {

            Log.i("MainActivity", "Please select less than one hundred Pizzas");
            Context context = getApplicationContext();
            String lowerLimitToast = getString(R.string.too_much_pizza);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, lowerLimitToast, duration);
            toast.show();
            return;

        }
    }

    /**
     * This method decrements the quantity of pizza by one
     *
     * @param view passes on the view that we are working with to the method
     */
    public void decrement(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
            display(quantity);
        } else {

            Log.i("MainActivity", "Please order at least one pizza");
            Context context = getApplicationContext();
            String upperLimitToast = getString(R.string.too_little_pizza);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, upperLimitToast, duration);
            toast.show();
            return;


        }
    }

    public void reset(View view){
        userInputNameView.setText("");
        checkBoxPineapple.setChecked(false);
        checkBoxAnchovy.setChecked(false);
        checkBoxArtichoke.setChecked(false);
        checkBoxSausage.setChecked(false);
        quantity = 1;
        display(quantity);
    }
}
