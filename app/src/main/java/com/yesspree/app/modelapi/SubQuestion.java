package com.yesspree.app.modelapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by FuGenX-14 on 18-06-2018.
 */

public class SubQuestion {
    @SerializedName("question")
    @Expose
    private String subQuestion;
    @SerializedName("answer")
    @Expose
    private String answer;

    public String getQuestion() {
        return subQuestion;
    }

    public void setQuestion(String question) {
        this.subQuestion = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
