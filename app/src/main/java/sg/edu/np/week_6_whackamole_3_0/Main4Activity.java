package sg.edu.np.week_6_whackamole_3_0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Main4Activity extends AppCompatActivity {

    /* Hint:
        1. This creates the Whack-A-Mole layout and starts a countdown to ready the user
        2. The game difficulty is based on the selected level
        3. The levels are with the following difficulties.
            a. Level 1 will have a new mole at each 10000ms.
                - i.e. level 1 - 10000ms
                       level 2 - 9000ms
                       level 3 - 8000ms
                       ...
                       level 10 - 1000ms
            b. Each level up will shorten the time to next mole by 100ms with level 10 as 1000 second per mole.
            c. For level 1 ~ 5, there is only 1 mole.
            d. For level 6 ~ 10, there are 2 moles.
            e. Each location of the mole is randomised.
        4. There is an option return to the login page.
     */
    private static final String FILENAME = "Main4Activity.java";
    private static final String TAG = "Whack-A-Mole3.0!";
    CountDownTimer readyTimer;
    CountDownTimer newMolePlaceTimer;
    private Button backBtn, target, target2, button0,button1,button2,button3,button4,button5,button6,button7,button8;
    private int score = 0;
    private TextView tv;
    private MyDBHandler dbHandler;
    private UserData userData;
    private String level,username, highScore;

    private void readyTimer(){
        readyTimer = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Toast.makeText(Main4Activity.this,"Get ready in " + millisUntilFinished/1000 + " seconds",Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Ready CountDown!" + millisUntilFinished/1000);

            }
            @Override
            public void onFinish() {
                Toast.makeText(Main4Activity.this,"GO!",Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Ready CountDown Complete!");
                readyTimer.cancel();
                placeMoleTimer();
            }
        };
        readyTimer.start();
    }
    private void placeMoleTimer(){
        int total = 0,interval = 0;
        switch (Integer.parseInt(level)){
            case 1:{
                total = 10000;
                interval = 10000;
                break;
            }
            case 2:{
                total = 9000;
                interval = 9000;
                break;
            }
            case 3:{
                total = 8000;
                interval = 8000;
                break;
            }
            case 4:{
                total = 7000;
                interval = 7000;
                break;
            }
            case 5:{
                total = 6000;
                interval = 6000;
                break;
            }
            case 6:{
                total = 5000;
                interval = 5000;
                break;
            }
            case 7:{
                total = 4000;
                interval = 4000;
                break;
            }
            case 8:{
                total = 3000;
                interval = 3000;
                break;
            }
            case 9:{
                total = 2000;
                interval = 2000;
                break;
            }
            default:{
                total = 1000;
                interval = 1000;
                break;
            }
        }
        if (Integer.parseInt(level) < 6){
            newMolePlaceTimer = new CountDownTimer(total,interval) {
                @Override
                public void onTick(long millisUntilFinished) {
                    setNewMole();
                    Log.d(TAG, "new Mole Location!");

                }
                @Override
                public void onFinish() {
                    target.setText("0");
                    newMolePlaceTimer.start();
                }
            };
        }
        else{
            newMolePlaceTimer = new CountDownTimer(total,interval) {
                @Override
                public void onTick(long millisUntilFinished) {
                    setNewMole();
                    Log.d(TAG, "new Mole Location!");

                }
                @Override
                public void onFinish() {
                    target.setText("0");
                    target2.setText("0");
                    newMolePlaceTimer.start();
                }
            };
        }
        newMolePlaceTimer.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        dbHandler = new MyDBHandler(this);
        Intent receive = getIntent();
        username = receive.getStringExtra("username");
        level = receive.getStringExtra("level");
        highScore = receive.getStringExtra("score");
        userData = dbHandler.findUser(username);

        tv = findViewById(R.id.updatedScore);
        tv.setText(String.valueOf(0));
        backBtn = findViewById(R.id.back);
        button0 = findViewById(R.id.zero);
        button1 = findViewById(R.id.one);
        button2 = findViewById(R.id.two);
        button3 = findViewById(R.id.three);
        button4 = findViewById(R.id.four);
        button5 = findViewById(R.id.five);
        button6 = findViewById(R.id.six);
        button7 = findViewById(R.id.seven);
        button8 = findViewById(R.id.eight);

        button0.setOnClickListener(buttonClick);
        button1.setOnClickListener(buttonClick);
        button2.setOnClickListener(buttonClick);
        button3.setOnClickListener(buttonClick);
        button4.setOnClickListener(buttonClick);
        button5.setOnClickListener(buttonClick);
        button6.setOnClickListener(buttonClick);
        button7.setOnClickListener(buttonClick);
        button8.setOnClickListener(buttonClick);

        readyTimer();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (score > Integer.parseInt(highScore)){
                    updateUserScore();
                }
                Intent intent = new Intent(Main4Activity.this,Main3Activity.class);
                intent.putExtra("name",username);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onStart(){
        super.onStart();
        readyTimer();
    }
    public View.OnClickListener buttonClick = new View.OnClickListener() {
        Button test;
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.zero:{
                    test = findViewById(R.id.zero);
                    break;
                }
                case R.id.one:{
                    test = findViewById(R.id.one);
                    break;
                }
                case R.id.two:{
                    test = findViewById(R.id.two);
                    break;
                }
                case R.id.three:{
                    test = findViewById(R.id.three);
                    break;
                }
                case R.id.four:{
                    test = findViewById(R.id.four);
                    break;
                }
                case R.id.five:{
                    test = findViewById(R.id.five);
                    break;
                }
                case R.id.six:{
                    test = findViewById(R.id.six);
                    break;
                }
                case R.id.seven:{
                    test = findViewById(R.id.seven);
                    break;
                }
                default:{
                    test = findViewById(R.id.eight);
                    break;
                }
            }
            doCheck(test);
        }
    };
    private void doCheck(Button checkButton)
    {
        switch (checkButton.getId()){
            case R.id.zero:{
                if (button0.getText() == "*" ){
                    score += 1;
                    button0.setText("0");
                    Log.d(TAG, "Hit, score added!");
                }
                else {
                    score -= 1;
                    Log.d(TAG, "Missed, score deducted!");
                }
                tv.setText(String.valueOf(score));
                break;
            }
            case R.id.one:{
                if (button1.getText() == "*"){
                    score += 1;
                    button1.setText("0");
                    Log.d(TAG, "Hit, score added!");
                }
                else{
                    score -= 1;
                    Log.d(TAG, "Missed, score deducted!");
                }
                tv.setText(String.valueOf(score));
                break;
            }
            case R.id.two:{
                if (button2.getText() == "*"){
                    score += 1;
                    button2.setText("0");
                    Log.d(TAG, "Hit, score added!");
                }
                else{
                    score -= 1;
                    Log.d(TAG, "Missed, score deducted!");
                }
                tv.setText(String.valueOf(score));
                break;
            }
            case R.id.three:{
                if (button3.getText() == "*"){
                    score += 1;
                    button3.setText("0");
                    Log.d(TAG, "Hit, score added!");
                }
                else{
                    score -= 1;
                    Log.d(TAG, "Missed, score deducted!");
                }
                tv.setText(String.valueOf(score));
                break;
            }
            case R.id.four:{
                if (button4.getText() == "*"){
                    score += 1;
                    button4.setText("0");
                    Log.d(TAG, "Hit, score added!");
                }
                else{
                    score -= 1;
                    Log.d(TAG, "Missed, score deducted!");
                }
                tv.setText(String.valueOf(score));
                break;
            }
            case R.id.five:{
                if (button5.getText() == "*"){
                    score += 1;
                    button5.setText("0");
                    Log.d(TAG, "Hit, score added!");
                }
                else{
                    score -= 1;
                    Log.d(TAG, "Missed, score deducted!");
                }
                tv.setText(String.valueOf(score));
                break;
            }
            case R.id.six:{
                if (button6.getText() == "*"){
                    score += 1;
                    button6.setText("0");
                    Log.d(TAG, "Hit, score added!");
                }
                else{
                    score -= 1;
                    Log.d(TAG, "Missed, score deducted!");
                }
                tv.setText(String.valueOf(score));
                break;
            }
            case R.id.seven:{
                if (button7.getText() == "*"){
                    score += 1;
                    button7.setText("0");
                    Log.d(TAG, "Hit, score added!");
                }
                else{
                    score -= 1;
                    Log.d(TAG, "Missed, score deducted!");
                }
                tv.setText(String.valueOf(score));
                break;
            }
            default:{
                if (button8.getText() == "*"){
                    score += 1;
                    button8.setText("0");
                    Log.d(TAG, "Hit, score added!");
                }
                else{
                    score -= 1;
                    Log.d(TAG, "Missed, score deducted!");
                }
                tv.setText(String.valueOf(score));
                break;
            }
        }
    }

    public void setNewMole()
    {
        Random ran = new Random();
        int randomLocation = ran.nextInt(9);
        switch (randomLocation){
            case 0:{
                target = button0;
                target.setText("*");
                break;
            }
            case 1:{
                target = button1;
                target.setText("*");
                break;
            }
            case 2:{
                target = button2;
                target.setText("*");
                break;
            }
            case 3:{
                target = button3;
                target.setText("*");
                break;
            }
            case 4:{
                target = button4;
                target.setText("*");
                break;
            }
            case 5:{
                target = button5;
                target.setText("*");
                break;
            }
            case 6:{
                target = button6;
                target.setText("*");
                break;
            }
            case 7:{
                target = button7;
                target.setText("*");
                break;
            }
            default:{
                target = button8;
                target.setText("*");
                break;
            }
        }
        if (Integer.parseInt(level) > 5){
            int randomLocation2 = ran.nextInt(9);
            while (randomLocation2 == randomLocation){
                randomLocation2 = ran.nextInt(9);
            }
            switch (randomLocation2) {
                case 0: {
                    target2 = button0;
                    target2.setText("*");
                    break;
                }
                case 1: {
                    target2 = button1;
                    target2.setText("*");
                    break;
                }
                case 2: {
                    target2 = button2;
                    target2.setText("*");
                    break;
                }
                case 3: {
                    target2 = button3;
                    target2.setText("*");
                    break;
                }
                case 4: {
                    target2 = button4;
                    target2.setText("*");
                    break;
                }
                case 5: {
                    target2 = button5;
                    target2.setText("*");
                    break;
                }
                case 6: {
                    target2 = button6;
                    target2.setText("*");
                    break;
                }
                case 7: {
                    target2 = button7;
                    target2.setText("*");
                    break;
                }
                default: {
                    target2 = button8;
                    target2.setText("*");
                    break;
                }
            }
        }

    }

    private void updateUserScore()
    {
        dbHandler.updateScore(username,Integer.parseInt(level),score);
        newMolePlaceTimer.cancel();
        readyTimer.cancel();

    }
}
