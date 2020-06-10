package sg.edu.np.week_6_whackamole_3_0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    /* Hint:
        1. This is the create new user page for user to log in
        2. The user can enter - Username and Password
        3. The user create is checked against the database for existence of the user and prompts
           accordingly via Toastbox if user already exists.
        4. For the purpose the practical, successful creation of new account will send the user
           back to the login page and display the "User account created successfully".
           the page remains if the user already exists and "User already exist" toastbox message will appear.
        5. There is an option to cancel. This loads the login user page.
     */


    private static final String FILENAME = "Main2Activity.java";
    private static final String TAG = "Whack-A-Mole3.0!";
    private Button cancel, create;
    private EditText username, password;
    private MyDBHandler dbHandler;
    private UserData userData;
    private ArrayList<Integer> levelList, scoreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        cancel = findViewById(R.id.cancel);
        create = findViewById(R.id.Create);
        username = findViewById(R.id.usernameField);
        password = findViewById(R.id.passwordField);
        dbHandler = new MyDBHandler(this);
        userData = new UserData();
        levelList = new ArrayList<Integer>(){
            {
                add(1);
                add(2);
                add(3);
                add(4);
                add(5);
                add(6);
                add(7);
                add(8);
                add(9);
                add(10);
            }
        };
        scoreList = new ArrayList<Integer>(){
            {
                add(0);
                add(0);
                add(0);
                add(0);
                add(0);
                add(0);
                add(0);
                add(0);
                add(0);
                add(0);
            }
        };

        /* Hint:
            This prepares the create and cancel account buttons and interacts with the database to determine
            if the new user created exists already or is new.
            If it exists, information is displayed to notify the user.
            If it does not exist, the user is created in the DB with default data "0" for all levels
            and the login page is loaded.

            Log.v(TAG, FILENAME + ": New user created successfully!");
            Log.v(TAG, FILENAME + ": User already exist during new user creation!");

         */
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userData.setMyUserName(username.getText().toString());
                userData.setMyPassword(password.getText().toString());
                userData.setLevels(levelList);
                userData.setScores(scoreList);
                dbHandler.addUser(userData);
                Toast.makeText(Main2Activity.this,"New user created",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void onStop() {
        super.onStop();
        finish();
    }
}
