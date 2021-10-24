

/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/
package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @param quantity the quantity of coffee
     * @param price price of each coffee
     * @return total bill of the coffees
     */
    private int calculatePrice(int quantity,int price,boolean hasWhippedCream,boolean hasChocolate) {
        int total=0;
        int baseprice=price;

        if(hasWhippedCream){ baseprice=(baseprice+1);}
        if(hasChocolate){ baseprice=(baseprice+2);}
        total=baseprice*quantity;

        return total;

    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox Checkedwhipedcream = ((CheckBox) findViewById(R.id.whipped_cream));
        boolean haswhippedcream=Checkedwhipedcream.isChecked();
        CheckBox CheckedChocolate = ((CheckBox) findViewById(R.id.chocolate));
        boolean hasChocolate=CheckedChocolate.isChecked();
        EditText userName=(EditText) findViewById(R.id.name_text_input);
        String name=userName.getText().toString();

        String msg=createOrderSummary(haswhippedcream,hasChocolate,name);



        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just java order for "+name);
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }
    public void Increment(View view) {
        if(quantity==100){
            Toast.makeText(getApplicationContext(),"you cannot order more than 100.",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity+=1;
        display(quantity);

    }
    public void Decrement(View view) {
        if(quantity==1){
            Toast.makeText(getApplicationContext(),"you cannot order less than 1 cup.",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity-=1;
        display(quantity);

    }


    /**
     *
     * @return the message to be printed on the screen
     */
    private String createOrderSummary(boolean haswhippedcream,boolean hasChocolate,String name){

        int price=  calculatePrice(quantity,5,haswhippedcream,hasChocolate);
        String finalmsg="Name: "+name+"\nAdd whipped cream? "+haswhippedcream+"\nAdd Chocolate? "+hasChocolate +"\nQuantity: " +quantity+"\nPrice: $"+price+"\nThankYou!";
        return finalmsg;
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}