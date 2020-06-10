package sg.edu.np.week_6_whackamole_3_0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /*
        1. This is the main page for user to log in
        2. The user can enter - Username and Password
        3. The user login is checked against the database for existence of the user and prompts
           accordingly via Toastbox if user does not exist. This loads the level selection page.
        4. There is an option to create a new user account. This loads the create user page.
     */
    private static final String FILENAME = "MainActivity.java";
    private static final String TAG = "Whack-A-Mole3.0!";
    private EditText username, password;
    private Button loginBtn;
    private TextView tvRegister;
    private MyDBHandler dbHandler;
    private UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.usernameField);
        password = findViewById(R.id.passwordField);
        loginBtn = findViewById(R.id.login);
        tvRegister = findViewById(R.id.newUser);
        dbHandler = new MyDBHandler(this);
        userData = new UserData();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameText = username.getText().toString();
                String passwordText = password.getText().toString();
                userData = dbHandler.findUser(usernameText);
                Log.d(TAG, "user: " + userData.getMyUserName());
                Boolean result = isValidUser(usernameText,passwordText);
                if (result){
                    Log.d(TAG, "Valid user! Logging in");
                    Intent intent = new Intent(MainActivity.this,Main3Activity.class);
                    intent.putExtra("name",usernameText);
                    startActivity(intent);
                }
                else{
                    Log.d(TAG, "Invalid user!");
                    Toast.makeText(MainActivity.this,"Invalid Username or Password",Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
            }
        });

    }

    protected void onStop(){
        super.onStop();
        finish();
    }

    public boolean isValidUser(String userName, String password){

        if (userData.getMyUserName().equals(userName) && userData.getMyPassword().equals(password)){
            Log.d(TAG, "true");
            return true;
        }
        Log.d(TAG, "false");
        return false;
    }
}
