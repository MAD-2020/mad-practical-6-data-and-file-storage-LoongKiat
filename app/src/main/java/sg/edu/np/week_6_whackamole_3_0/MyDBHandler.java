package sg.edu.np.week_6_whackamole_3_0;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper {
    /*
        The Database has the following properties:
        1. Database name is WhackAMole.db
        2. The Columns consist of
            a. Username
            b. Password
            c. Level
            d. Score
        3. Add user method for adding user into the Database.
        4. Find user method that finds the current position of the user and his corresponding
           data information - username, password, level highest score for each level
        5. Delete user method that deletes based on the username
        6. To replace the data in the database, we would make use of find user, delete user and add user

        The database shall look like the following:

        Username | Password | Level | Score
        --------------------------------------
        User A   | XXX      | 1     |    0
        User A   | XXX      | 2     |    0
        User A   | XXX      | 3     |    0
        User A   | XXX      | 4     |    0
        User A   | XXX      | 5     |    0
        User A   | XXX      | 6     |    0
        User A   | XXX      | 7     |    0
        User A   | XXX      | 8     |    0
        User A   | XXX      | 9     |    0
        User A   | XXX      | 10    |    0
        User B   | YYY      | 1     |    0
        User B   | YYY      | 2     |    0

     */

    private static final String FILENAME = "MyDBHandler.java";
    private static final String TAG = "Whack-A-Mole3.0!";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "WhackAMole.db";
    public static final String TABLE_USER = "users";
    public static final String COLUMN_USERNAME = "Username";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_LEVEL = "Level";
    public static final String COLUMN_SCORE = "Score";
    private ArrayList<Integer> levels, scores;

    public MyDBHandler(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USER +
                " ("+ COLUMN_USERNAME +" TEXT,"
                +  COLUMN_PASSWORD + " TEXT,"
                + COLUMN_LEVEL + " INTEGER,"
                + COLUMN_SCORE + " INTEGER" + " )";
        db.execSQL(CREATE_USERS_TABLE);
        Log.d(TAG, "DB Created: " + CREATE_USERS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public void addUser(UserData userData)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        for(int i = 0; i < 10; i ++){
            ContentValues values = new ContentValues();
            values.put(COLUMN_USERNAME, userData.getMyUserName());
            values.put(COLUMN_PASSWORD, userData.getMyPassword());
            values.put(COLUMN_LEVEL, userData.getLevels().get(i));
            values.put(COLUMN_SCORE, userData.getScores().get(i));
            db.insert(TABLE_USER, null, values);
            Log.d(TAG, FILENAME + ": Adding data for Database: " + values.toString());
        }

        db.close();
    }

    public UserData findUser(String username)
    {
        String query = "SELECT * FROM " + TABLE_USER
                + " WHERE " + COLUMN_USERNAME + " = \"" + username + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        UserData userData = new UserData();
        if (cursor.moveToFirst()){
            userData.setMyUserName(cursor.getString(0));
            userData.setMyPassword(cursor.getString(1));
            levels = userData.getLevels();
            scores = userData.getScores();
            int level = Integer.parseInt(cursor.getString(2));
            levels.add(level);
            int score = Integer.parseInt(cursor.getString(3));
            scores.add(score);

        }
        while (cursor.moveToNext()){
            int level = Integer.parseInt(cursor.getString(2));
            levels.add(level);
            int score = Integer.parseInt(cursor.getString(3));
            scores.add(score);
        }
        userData.setLevels(levels);
        userData.setScores(scores);
        cursor.close();
        db.close();
        return userData;
    }

    public void updateScore(String username, int level, int score){
        ContentValues values = new ContentValues();
        values.put(COLUMN_SCORE,score);
        String whereQuery = COLUMN_USERNAME + " =\"" + username + "\"" + " AND "
                + COLUMN_LEVEL + " = " + level;
        String whereClause = COLUMN_USERNAME + "=" + "\"" + username + "\"" + " AND " + COLUMN_LEVEL + "=" + level;
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_USER,values,whereQuery,null);
        db.close();
    }

    public boolean deleteAccount(String username) {
        boolean result = false;
        String query = "SELECT * FROM " + TABLE_USER + " WHERE "
                + COLUMN_USERNAME + "=\"" + username + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        UserData userData = new UserData();
        if (cursor.moveToFirst()){
            userData.setMyUserName(cursor.getString(0));
            db.delete(TABLE_USER, COLUMN_USERNAME + "= ?",
                    new String[]{userData.getMyUserName()});
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
}
