package sg.edu.np.week_6_whackamole_3_0;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CustomScoreViewHolder extends RecyclerView.ViewHolder {

    private static final String FILENAME = "CustomScoreViewHolder.java";
    private static final String TAG = "Whack-A-Mole3.0!";
    public TextView level, score;
    public View view;

    public CustomScoreViewHolder(final View itemView){
        super(itemView);

        view = itemView;
        level = itemView.findViewById(R.id.level);
        score = itemView.findViewById(R.id.highScore);

    }
}
