package com.eztrad.chatbotserver.dto;

public class ChatResponse {
    private String userQuestion;
    private String aiAnswer;
    private String cryptoData;
    private String questionType;

    public ChatResponse(String userQuestion, String aiAnswer, String cryptoData, String questionType) {
        this.userQuestion = userQuestion;
        this.aiAnswer = aiAnswer;
        this.cryptoData = cryptoData;
        this.questionType = questionType;
}

    public String getUserQuestion() {
        return userQuestion;
    }

    public void setUserQuestion(String userQuestion) {
        this.userQuestion = userQuestion;
    }

    public String getAiAnswer() {
        return aiAnswer;
    }

    public void setAiAnswer(String aiAnswer) {
        this.aiAnswer = aiAnswer;
    }

    public String getCryptoData() {
        return cryptoData;
    }

    public void setCryptoData(String cryptoData) {
        this.cryptoData = cryptoData;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }
}
