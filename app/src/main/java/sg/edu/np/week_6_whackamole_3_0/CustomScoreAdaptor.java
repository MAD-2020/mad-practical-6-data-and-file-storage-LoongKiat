package sg.edu.np.week_6_whackamole_3_0;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class CustomScoreAdaptor extends RecyclerView.Adapter<CustomScoreViewHolder> {

    private static final String FILENAME = "CustomScoreAdaptor.java";
    private static final String TAG = "Whack-A-Mole3.0!";
    private ArrayList<UserData> data;
    private UserData userData;
    private Context context;

    public CustomScoreAdaptor(UserData userdata, Context context){
        this.userData = userdata;
        this.context = context;
    }

    public CustomScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.level_select,parent,false);
        return new CustomScoreViewHolder(item);
    }

    public void onBindViewHolder(CustomScoreViewHolder holder, final int position){

        final String level = String.valueOf(userData.getLevels().get(position));
        holder.level.setText("Level " + level);
        final String score = String.valueOf(userData.getScores().get(position));
        holder.score.setText("Highest score: " + score);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Main4Activity.class);
                intent.putExtra("username",userData.getMyUserName());
                intent.putExtra("level",level);
                intent.putExtra("score",score);
                context.startActivity(intent);
            }
        });
    }

    public int getItemCount(){
        return userData.getLevels().size();
    }
}