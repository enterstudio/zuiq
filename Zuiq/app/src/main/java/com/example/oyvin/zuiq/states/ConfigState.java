package com.example.oyvin.zuiq.states;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.oyvin.zuiq.R;
import com.example.oyvin.zuiq.controllers.ConfigController;
import com.example.oyvin.zuiq.sprites.PosNegBtn;
import com.example.oyvin.zuiq.sprites.StartBtn;

import sheep.game.State;
import sheep.graphics.Image;

/**
 * Created by oyvind on 21/04/2016.
 */
public class ConfigState extends BackgroundState {
    private static ConfigState configState = null;
    private ConfigController controller;
    String gameMode;
    int noOfPlayers = 1;
    int noOfSeconds = 5;
    int noOfQuestions = 3;
    int scoreLimit = 10;
    Paint dscrtextPaint, varTextPaint, navPaint;
    PosNegBtn addPlayer, removePlayer, addSecond, removeSecond, addQuestion, removeQuestion, addScore, removeScore;
    StartBtn startGame;

    private ConfigState(){
        super();
        addPlayer = new PosNegBtn(new Image(R.drawable.optionbtn), "addPlayer");
        removePlayer = new PosNegBtn(new Image(R.drawable.optionbtn), "removePlayer");
        addSecond = new PosNegBtn(new Image(R.drawable.optionbtn), "addSecond");
        removeSecond = new PosNegBtn(new Image(R.drawable.optionbtn), "removeSecond");
        addQuestion = new PosNegBtn(new Image(R.drawable.optionbtn), "addQuestion");
        removeQuestion = new PosNegBtn(new Image(R.drawable.optionbtn), "removeQuestion");
        addScore = new PosNegBtn(new Image(R.drawable.optionbtn), "addScore");
        removeScore = new PosNegBtn(new Image(R.drawable.optionbtn), "removeScore");
        startGame = new StartBtn(new Image(R.drawable.startbtn), "start");
        this.addTouchListener(addPlayer);
        this.addTouchListener(removePlayer);
        this.addTouchListener(addSecond);
        this.addTouchListener(removeSecond);
        this.addTouchListener(addQuestion);
        this.addTouchListener(removeQuestion);
        this.addTouchListener(startGame);
        this.addTouchListener(addScore);
        this.addTouchListener(removeScore);
        dscrtextPaint = new Paint();
        dscrtextPaint.setColor(Color.WHITE);
        dscrtextPaint.setTextSize(50);
        varTextPaint = new Paint();
        varTextPaint.setColor(Color.LTGRAY);
        varTextPaint.setTextSize(100);
        navPaint = new Paint();
        navPaint.setColor(Color.BLACK);
        navPaint.setTextSize(100);



    }
    public static ConfigState getInstance(){
        if (configState == null){
            configState = new ConfigState();
        }
        return configState;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public void draw(Canvas canvas){
        super.draw(canvas);
        drawText(canvas);

        setPositionOfSprites(canvas);

        if(controller.getGame().isQuestionnaire()){
            drawQuestionnaire(canvas);
        }
        else {
            drawScoreRace(canvas);
        }

    }


    public void drawQuestionnaire(Canvas canvas){
        canvas.drawText("No of questions", canvas.getWidth()/2-100, canvas.getHeight()/1.2f, dscrtextPaint);
        canvas.drawText(""+noOfQuestions, canvas.getWidth()/2, canvas.getHeight()/1.1f, varTextPaint);
        addQuestion.setPosition(canvas.getWidth() / 2 + 200, canvas.getHeight() / 1.1f);
        removeQuestion.setPosition(canvas.getWidth() / 2 - 200, canvas.getHeight() / 1.1f);
        addQuestion.draw(canvas);
        removeQuestion.draw(canvas);
        canvas.drawText(">", addQuestion.getX() - 20, addQuestion.getY() + 25, navPaint);
        canvas.drawText("<", removeQuestion.getX() - 20, addQuestion.getY() + 25, navPaint);


    }
    public void drawScoreRace(Canvas canvas){
        canvas.drawText("Score limit", canvas.getWidth()/2-100, canvas.getHeight()/1.2f, dscrtextPaint);
        canvas.drawText(""+scoreLimit, canvas.getWidth()/2, canvas.getHeight()/1.1f, varTextPaint);
        addScore.setPosition(canvas.getWidth() / 2 + 200, canvas.getHeight() / 1.1f);
        removeScore.setPosition(canvas.getWidth() / 2 - 200, canvas.getHeight() / 1.1f);
        addScore.draw(canvas);
        removeScore.draw(canvas);
        canvas.drawText(">", addScore.getX() - 20, addScore.getY() + 25, navPaint);
        canvas.drawText("<", removeScore.getX() - 20, removeScore.getY() + 25, navPaint);
    }
    public void setPositionOfSprites(Canvas canvas){
        addPlayer.setPosition(canvas.getWidth()/2 +200, canvas.getHeight()/2);
        removePlayer.setPosition(canvas.getWidth()/2-200, canvas.getHeight()/2);
        addSecond.setPosition(canvas.getWidth() / 2 + 200, canvas.getHeight() / 1.5f + 110);
        removeSecond.setPosition(canvas.getWidth() / 2 - 200, canvas.getHeight() / 1.5f + 110);
        startGame.setPosition(canvas.getWidth() / 2, canvas.getHeight() / 5);
        //startGame.setScale(0.2f, 0.2f);
        addPlayer.draw(canvas);
        removePlayer.draw(canvas);
        addSecond.draw(canvas);
        removeSecond.draw(canvas);
        startGame.draw(canvas);


        canvas.drawText(">", addSecond.getX() - 20, addSecond.getY() + 25, navPaint);
        canvas.drawText(">", addPlayer.getX()-20, addPlayer.getY()+25, navPaint);

        canvas.drawText("<", removePlayer.getX()-20, addSecond.getY()+25, navPaint);
        canvas.drawText("<", removeSecond.getX()-20, addPlayer.getY()+25, navPaint);


    }

    public void update(float dt){
        super.update(dt);
        addPlayer.update(dt);
        removePlayer.update(dt);
        addSecond.update(dt);
        removeSecond.update(dt);
        addQuestion.update(dt);
        removeQuestion.update(dt);
        addScore.update(dt);
        removeScore.update(dt);
        startGame.update(dt);
    }

    public void setNoOfPlayers(int player){
        if (noOfPlayers > 0 && noOfPlayers < 5 && player >0){
            noOfPlayers += player;
        }
        else if(noOfPlayers >1 && noOfPlayers <= 5 && player < 0){
            noOfPlayers += player;
        }
    }
    public void setNoOfSeconds(int second){
        if(noOfSeconds >= 0 && noOfSeconds < 30 && second > 0){
            noOfSeconds += second;
        }
        else if(noOfSeconds > 0 && noOfSeconds <= 30 && second <0){
            noOfSeconds += second;
        }
    }

    public void setNoOfQuestions(int question){
        if(noOfQuestions >= 3 && noOfQuestions <30 && question >0){
            noOfQuestions += question;
        }
        else if(noOfQuestions > 3 && noOfQuestions <=30 && question <0){
            noOfQuestions += question;
        }
    }

    public void setScoreLimit(int score){
        if(scoreLimit >= 10 && scoreLimit < 100 && score >0){
            scoreLimit += score;
        }
        else if(scoreLimit > 10 && scoreLimit <=100 && score <0){
            scoreLimit += score;
        }
    }

    public void drawText(Canvas canvas){
        canvas.drawText("Select no. of players", canvas.getWidth() / 2 - 200, canvas.getHeight() / 2 - 110, dscrtextPaint);
        canvas.drawText("" + noOfPlayers, canvas.getWidth() / 2, canvas.getHeight() / 2, varTextPaint);
        canvas.drawText("Select seconds for each question", canvas.getWidth()/2-300, canvas.getHeight()/1.5f, dscrtextPaint);
        canvas.drawText(""+noOfSeconds, canvas.getWidth()/2, canvas.getHeight()/1.5f+110, varTextPaint);
    }

    public void switchState(State state){
        super.switchState(state);
        removeTouchListener(addPlayer);
        removeTouchListener(removePlayer);
        removeTouchListener(addSecond);
        removeTouchListener(removeSecond);
        removeTouchListener(addQuestion);
        removeTouchListener(removeQuestion);
        removeTouchListener(addScore);
        removeTouchListener(removeScore);
    }
}

