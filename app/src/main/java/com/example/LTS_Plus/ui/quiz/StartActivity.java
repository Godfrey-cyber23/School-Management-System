package com.example.LTS_Plus.ui.quiz;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.LTS_Plus.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StartActivity extends AppCompatActivity {

    private TextView questionTxt, indicator;
    private LinearLayout container;
    private Button nextBtn, shareBtn;
    private int score = 0;
    private int position = 0;
    private int count = 0;


    DatabaseReference reference;

    private List<QuestionData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        questionTxt = findViewById(R.id.question);
        indicator = findViewById(R.id.indicator);
        container = findViewById(R.id.container);
        nextBtn = findViewById(R.id.next_btn);
        shareBtn = findViewById(R.id.share_btn);


        list = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference();

        reference.child("Questions").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String question = Objects.requireNonNull(snapshot.child("question").getValue()).toString();
                    String option1 = Objects.requireNonNull(snapshot.child("option1").getValue()).toString();
                    String option2 = Objects.requireNonNull(snapshot.child("option2").getValue()).toString();
                    String option3 = Objects.requireNonNull(snapshot.child("option3").getValue()).toString();
                    String option4 = Objects.requireNonNull(snapshot.child("option4").getValue()).toString();
                    String correctAns = Objects.requireNonNull(snapshot.child("answer").getValue()).toString();

                    list.add(new QuestionData(option1,option2,option3,option4,question,correctAns));

                }

                if (!list.isEmpty()){
                    loadQuestion(questionTxt,0,list.get(position).getQuestion());

                    for (int i = 0; i<4; i++){
                        container.getChildAt(i).setOnClickListener(view -> checkAnswer((Button)view));
                    }

                    nextBtn.setOnClickListener(v -> {
                        nextBtn.setEnabled(false);
                        nextBtn.setAlpha(0.7f);
                        enabled(true);
                        position++;

                        if (position == list.size()){
                            Intent intent = new Intent(StartActivity.this, ScoreActivity.class);
                            intent.putExtra("score",score);
                            intent.putExtra("total",list.size());
                            startActivity(intent);
                            finish();
                            return;
                        }
                        count = 0;
                        loadQuestion(questionTxt,0,list.get(position).getQuestion());
                    });

                    shareBtn.setOnClickListener(v -> {
                        String body = "*"+list.get(position).getQuestion()+"+"+
                                "(a)"+list.get(position).getOption1()+"\n"+
                                "(b)"+list.get(position).getOption2()+"\n"+
                                "(c)"+list.get(position).getOption3()+"\n"+
                                "(d)"+list.get(position).getOption4();

                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("Text/Plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT,"School App");
                        intent.putExtra(Intent.EXTRA_TEXT,body);
                        startActivity(Intent.createChooser(intent,"Share Via"));
                    });


                }else {
                    Toast.makeText(StartActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(StartActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @SuppressLint("NewApi")
    private void checkAnswer(Button selectedOption) {
        enabled(false);
        nextBtn.setEnabled(true);
        nextBtn.setAlpha(1);
        if (selectedOption.getText().toString().equals(list.get(position).getAnswer())){
            score ++;
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4caf50")));
        }else {
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
            Button correctOption = container.findViewWithTag(list.get(position).getAnswer());
            correctOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4caf50")));

        }
    }

    @SuppressLint("NewApi")
    private void enabled(Boolean enable){
        for (int i = 0; i<4; i++){
            container.getChildAt(i).setEnabled(enable);
            if (enable){
                container.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#989898")));
            }
        }
    }

    @SuppressLint("NewApi")
    private void loadQuestion(View view, int value, String data){
        for (int i = 0; i<4 ; i++){
            container.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#989898")));
        }
        view.animate().alpha(value).scaleY(value).setDuration(500)
                .setStartDelay(100).setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(@NonNull Animator animation) {
                        if (value == 0 && count < 4){
                            String option = "";
                            if (count == 0)
                                option = list.get(position).getOption1();
                            else if (count == 1)
                                option = list.get(position).getOption2();
                            else  if (count == 2)
                                option = list.get(position).getOption3();
                            else  if (count == 3)
                                option = list.get(position).getOption4();

                            loadQuestion(container.getChildAt(count),0,option);
                            count++;
                        }
                    }

                    @Override
                    public void onAnimationEnd(@NonNull Animator animation) {
                        if (value == 0) {
                            try {
                                ((TextView) view).setText(data);
                                // Use string resource with placeholders
                                indicator.setText(view.getContext().getString(R.string.indicator_text, position, list.size()));
                            } catch (ClassCastException e) {
                                ((Button) view).setText(data);
                            }
                            view.setTag(data);
                            loadQuestion(view, 1, data);
                        }
                    }


                    @Override
                    public void onAnimationCancel(@NonNull Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animation) {

                    }
                });
    }
}