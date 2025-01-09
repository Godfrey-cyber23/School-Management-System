package com.example.LTS_Plus.ui.quiz;

public class QuestionData {
    private final String option1;
    private final String option2;
    private final String option3;
    private final String option4;
    private final String question;
    private final String answer;

    public QuestionData(String option1, String option2, String option3, String option4, String question, String answer) {
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.question = question;
        this.answer = answer;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

}

