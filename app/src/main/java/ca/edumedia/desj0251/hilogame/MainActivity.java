/**
 *  HiLo Application: Assignment #1
 *  @author John + Desjardins (desj0251@algonquinlive.ca)
 */

package ca.edumedia.desj0251.hilogame;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final String ABOUT_DIALOG_TAG = "About Dialog";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_about) {
            DialogFragment newFragment = new AboutDialogFragment();
            newFragment.show(getFragmentManager(), ABOUT_DIALOG_TAG);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Variable Declarations
    public int answer = 0;
    public int num_of_guesses = 10;
    public int parsed_input;

    public boolean input_error = false;

    public String error_code = "";
    public String user_input = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        answer = (int)(Math.random() * 1000 + 1);

        // Reset Button Code
        // Todo: Reset Button OnLongClick
        Button resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(getApplicationContext(), "The current answer is: " + Integer.toString(answer), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        // Todo: Reset Button OnClick
        Button resetButton2 = (Button) findViewById(R.id.resetButton);
        resetButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                answer = (int)(Math.random() * 1000 + 1);
                num_of_guesses = 10;

                Button guessButton = (Button) findViewById(R.id.guessButton);
                guessButton.setEnabled(true);
                guessButton.setBackgroundColor(Color.parseColor("#33691E"));

                Toast toast = Toast.makeText(getApplicationContext(), "Reset Successful", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        // Guess Button Code
        // Todo: Guess Button onClick
        final Button guessButton = (Button) findViewById(R.id.guessButton);
        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Todo: Grab User Input
                EditText uInput = (EditText)findViewById(R.id.guessText);
                user_input = uInput.getText().toString();
                input_error = false; // Set Error Flag to False
                error_code = ""; // Reset Error Code

                // Todo: Parse input
                if (user_input.isEmpty() == false){
                    try{
                        parsed_input = Integer.parseInt(user_input);
                        if (parsed_input < 1 || parsed_input > 1000) {
                            error_code += "Input is not within the valid range";
                            input_error = true;
                        }
                    }catch(Exception e){
                        error_code += "Input is not a number";
                        input_error = true;
                    }
                } else {
                    error_code += "Input is Blank";
                    input_error = true;
                }

                // Todo: Check if Error and deal accordingly
                if (input_error == false) { // No Error
                    // Todo: No Error Code
                    // Check if num_of_guesses == 0
                    // if num_of_guesses != 0 then check if guess == answer
                    if (parsed_input == answer) {
                        // Todo: Win Code
                        num_of_guesses --;
                        guessButton.setEnabled(false);
                        guessButton.setBackgroundColor(Color.GRAY);

                        if ((10 - num_of_guesses) <= 5 ) {
                            Toast toast = Toast.makeText(getApplicationContext(), "SUPERIOR WIN. It took you " + Integer.toString(10 - num_of_guesses) + " guess(es)", Toast.LENGTH_LONG);
                            toast.show();
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), "EXCELLENT WIN. It took you " + Integer.toString(10 - num_of_guesses) + " guess(es)", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    } else if (parsed_input < answer) {
                        // Todo: Guess too Low code
                        num_of_guesses--;
                        if (num_of_guesses == 0) {
                            // Todo: Lose Code
                            guessButton.setEnabled(false);
                            guessButton.setBackgroundColor(Color.GRAY);
                            Toast toast2 = Toast.makeText(getApplicationContext(), "OUT OF GUESSES. PLEASE RESET", Toast.LENGTH_LONG);
                            toast2.show();
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), "TOO LOW: " + Integer.toString(num_of_guesses) + " guesses remaining.", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    } else {
                        // Todo: Guess too High Code
                        num_of_guesses--;
                        if (num_of_guesses == 0) {
                            // Todo: Lose Code
                            guessButton.setEnabled(false);
                            Toast toast2 = Toast.makeText(getApplicationContext(), "OUT OF GUESSES. PLEASE RESET", Toast.LENGTH_LONG);
                            toast2.show();
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), "TOO HIGH: " + Integer.toString(num_of_guesses) + " guesses remaining.", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                } else { // Error
                    // Todo: Error Code
                    uInput.setError(error_code);
                    uInput.requestFocus();
                    Toast toast = Toast.makeText(getApplicationContext(), "Guess Failed: " + error_code, Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });

    }
}