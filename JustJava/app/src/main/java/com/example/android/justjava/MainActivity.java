package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String displayString;
        int pricePerCup = 5;
        CheckBox isWhippedCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox isChocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        EditText inputName = (EditText) findViewById(R.id.input_name);
        boolean hasWhippedCream = isWhippedCheckbox.isChecked();
        boolean hasChocolate = isChocolateCheckbox.isChecked();
        String inputNameEntered = inputName.getText().toString();
        if (hasWhippedCream) {
            pricePerCup = pricePerCup + 1;
        }
        if (hasChocolate) {
            pricePerCup = pricePerCup + 2;
        }
        displayString = createOrderSummary(inputNameEntered, quantity, calculatePrice(quantity, pricePerCup), hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order for " + inputNameEntered);
        intent.putExtra(Intent.EXTRA_TEXT, displayString);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        displayMessage(displayString);
    }

    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @param pricePerCup is proce of coffee per cup
     * @return the total price
     * @parm quantity is no of cups of coffee ordered
     */
    private int calculatePrice(int quantity, int pricePerCup) {
        int price = quantity * pricePerCup;
        return price;
    }

    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @return the order summary string
     * @parm price of order
     */
    private String createOrderSummary(String name, int quantity, int priceofOrder, boolean whippedCream, boolean chocolate) {
        String orderSummary = "";
        orderSummary = orderSummary + "Name : " + name;
        orderSummary = orderSummary + "\nQuantity : " + quantity;
        orderSummary = orderSummary + "\nHas Whipped Cream : " + whippedCream;
        orderSummary = orderSummary + "\nHas Cocolate : " + chocolate;
        orderSummary = orderSummary + "\nTotal : ₹" + priceofOrder;
        orderSummary = orderSummary + "\nThank You!";
        return orderSummary;
    }


    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        quantity = quantity + 1;
        display(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        Context context = getApplicationContext();
        CharSequence text = "Quantiy cannot be less than 1!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        quantity = quantity - 1;
        if (quantity == 0) {
            toast.show();
            quantity = 1;
        } else
            display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}