package com.example.prachi.justjava;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.zip.CheckedInputStream;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity=0;
    /**
     * This method is called when the order button is clicked.
     */
    public void increment(View view){
        quantity=quantity+1;
        display(quantity);
    }
    public void decrement(View view){
        quantity=quantity-1;
        if(quantity<=1)
        {
            quantity=1;
            Toast.makeText(this,"You cannot order less than 1 coffee",Toast.LENGTH_SHORT).show();
        }
        display(quantity);
    }
    public void submitOrder(View view) {
        int price = calculatePrice();
        String message = createOrderSummary(price);
        TextView orderSummary = (TextView) findViewById(R.id.order_summary);
        orderSummary.setVisibility(View.VISIBLE);
        displayMessage(message);
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setVisibility(View.VISIBLE);
        TextView order = (TextView) findViewById(R.id.order);
        order.setVisibility(View.GONE);
        TextView proceed = (TextView) findViewById(R.id.proceed);
        proceed.setVisibility(View.VISIBLE);
//        String subject =getString(R.string.order_summary_email_subject,getName());
//        Intent intent = new Intent(Intent.ACTION_SENDTO);
//        intent.setData(Uri.parse("mailto:"));
//        intent.putExtra(Intent.EXTRA_EMAIL, emailid);
//        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
//        intent.putExtra(Intent.EXTRA_TEXT, message);
//        if(intent.resolveActivity(getPackageManager())!=null){
//            startActivity(intent);
//        }
    }
    public void displayMessage(String message) {
        TextView textView = (TextView) findViewById(R.id.order_summary_text_view);
        textView.setText(message);
    }
    private int calculatePrice(){
        CheckBox checkBoxCappucino = (CheckBox) findViewById(R.id.cappucino);
        boolean isCappucino = checkBoxCappucino.isChecked();
        CheckBox checkBoxLatte = (CheckBox) findViewById(R.id.latte);
        boolean isLatte = checkBoxLatte.isChecked();
        CheckBox checkBoxMocha = (CheckBox) findViewById(R.id.mocha);
        boolean isMocha = checkBoxMocha.isChecked();
        CheckBox checkBoxAmericano = (CheckBox) findViewById(R.id.americano);
        boolean isAmericano = checkBoxAmericano.isChecked();
        CheckBox checkBoxEspresso = (CheckBox) findViewById(R.id.espresso);
        boolean isEspresso = checkBoxEspresso.isChecked();
        int basePrice = 0;
        if(isCappucino) {
             basePrice = basePrice + 5;
        }
        if(isAmericano) {
            basePrice = basePrice + 3;
        }
        if(isLatte) {
            basePrice = basePrice + 5;
        }
        if(isMocha) {
            basePrice = basePrice + 8;
        }
        if(isEspresso) {
            basePrice = basePrice + 2;
        }
        int totalPrice = calculateToppingPrice(basePrice)*quantity;
        return totalPrice;
    }
    private int calculateToppingPrice(int price) {
        CheckBox checkBoxWhipped = (CheckBox) findViewById(R.id.cream);
        boolean isWhippedCream = checkBoxWhipped.isChecked();
        CheckBox checkBoxChocolate = (CheckBox) findViewById(R.id.chocolate);
        boolean isChocolate = checkBoxChocolate.isChecked();
        CheckBox checkBoxBiscuit = (CheckBox) findViewById(R.id.biscuitcrumbs);
        boolean isBiscuitCrumbs = checkBoxBiscuit.isChecked();
        CheckBox checkBoxCaramel = (CheckBox) findViewById(R.id.caramelbits);
        boolean isCaramelBits = checkBoxCaramel.isChecked();

        if(isChocolate) {
            price = price + 2;
        }
        if(isWhippedCream){
            price = price + 1;
        }
        if(isBiscuitCrumbs) {
            price = price + 1;
        }
        if(isCaramelBits) {
            price = price + 3;
        }
        return price;
    }

    private String createOrderSummary(int price){

        String messageToppings = " ";

        CheckBox checkBoxWhipped = (CheckBox) findViewById(R.id.cream);
        boolean isWhippedCream = checkBoxWhipped.isChecked();
        CheckBox checkBoxChocolate = (CheckBox) findViewById(R.id.chocolate);
        boolean isChocolate = checkBoxChocolate.isChecked();
        CheckBox checkBoxBiscuit = (CheckBox) findViewById(R.id.biscuitcrumbs);
        boolean isBiscuitCrumbs = checkBoxBiscuit.isChecked();
        CheckBox checkBoxCaramel = (CheckBox) findViewById(R.id.caramelbits);
        boolean isCaramelBits = checkBoxCaramel.isChecked();

        if(isWhippedCream) {
            messageToppings += "#WhippedCream ";
        }
        if(isChocolate){
            messageToppings += "#Chocolate ";
        }
        if(isBiscuitCrumbs) {
            messageToppings += "#BiscuitCrumbs ";
        }
        if(isCaramelBits) {
            messageToppings += "#CaramelBits ";
        }

        EditText editTextName = (EditText) findViewById(R.id.name);
        String name = editTextName.getText().toString();

        String message = getString(R.string.order_summary_name,name) +
                    "\n" + getString(R.string.toppings) + ":" + messageToppings +
                    "\n" + getString(R.string.order_summary_quantity,quantity) +
                    "\n" + getString(R.string.order_summary_price,price) +
                    "\n" + getString(R.string.thank_you);
        return message;
    }
    public void address(View view) {
        EditText editTextAddress = (EditText) findViewById(R.id.address);
        RadioButton radioButtonDelivery = (RadioButton)findViewById(R.id.delivery);
        RadioButton radioButtonPickUp = (RadioButton)findViewById(R.id.pickup);

        boolean isDelivery = radioButtonDelivery.isChecked();
        boolean ispickup = radioButtonPickUp.isChecked();
        if(isDelivery == true ) {

            editTextAddress.setVisibility(View.VISIBLE);
        }
        if(ispickup == true) {
            editTextAddress.setVisibility(View.GONE);
        }
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}