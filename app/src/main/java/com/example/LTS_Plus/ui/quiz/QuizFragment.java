package com.example.LTS_Plus.ui.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.LTS_Plus.R;
import com.example.LTS_Plus.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class QuizFragment extends Fragment {


    public QuizFragment() {
        // Required empty public constructor
    }

    Button playQuiz;
    FirebaseUser user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_quiz, container, false);

        playQuiz = root.findViewById(R.id.play_quiz);
        user = FirebaseAuth.getInstance().getCurrentUser();
        playQuiz.setOnClickListener(v -> {
            if (user != null){
                startActivity(new Intent(getContext(),StartActivity.class));
            }else {
                    startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

        return  root;
    }
}