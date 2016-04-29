package com.example.oyvin.zuiq.states;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.oyvin.zuiq.R;
import com.example.oyvin.zuiq.ZiuqGame;
import com.example.oyvin.zuiq.controllers.GameController;
import com.example.oyvin.zuiq.helpers.Highscore;
import com.example.oyvin.zuiq.models.Question;
import com.example.oyvin.zuiq.sprites.AnswerBtn;
import com.example.oyvin.zuiq.sprites.ReadyBtn;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;

import sheep.graphics.Image;

public class GameState extends BackgroundState {
    private static GameState gameState = null;
    public Canvas thisCanvas;
    public AnswerBtn ans1, ans2, ans3, ans4;
    public ReadyBtn rdy;
    public GameController controller;
    public String state;
    Paint playerPaint;
    Paint ans1Paint, ans2Paint, ans3Paint, ans4Paint;
    Paint QuestionPaint;
    Paint TimerPaint;
    public ArrayList<AnswerBtn> ansbtns = new ArrayList<>();
    public ArrayList<Paint> anspaints = new ArrayList<>();

    private boolean answerListeners = false;
    private boolean pauseListener = false;


    public static GameState getInstance() {
        if(gameState == null){
            gameState = new GameState();
        }
        return gameState;
    }

    private GameState() {
        super();

        ans1 = new AnswerBtn(new Image(R.drawable.optionbtn), "incorrect");
        ans2 = new AnswerBtn(new Image(R.drawable.optionbtn), "incorrect");
        ans3 = new AnswerBtn(new Image(R.drawable.optionbtn), "incorrect");
        ans4 = new AnswerBtn(new Image(R.drawable.optionbtn), "incorrect");
        rdy = new ReadyBtn(new Image(R.drawable.startbtn));

        ansbtns.add(ans1);
        ansbtns.add(ans2);
        ansbtns.add(ans3);
        ansbtns.add(ans4);


        playerPaint = new Paint();
        playerPaint.setColor(Color.WHITE);
        playerPaint.setTextSize(100);

        ans1Paint = new Paint();
        ans2Paint = new Paint();
        ans3Paint = new Paint();
        ans4Paint = new Paint();
        QuestionPaint = new Paint();
        TimerPaint = new Paint();

        ans1Paint.setColor(Color.BLACK);
        ans2Paint.setColor(Color.BLACK);
        ans3Paint.setColor(Color.BLACK);
        ans4Paint.setColor(Color.BLACK);
        QuestionPaint.setColor(Color.WHITE);
        TimerPaint.setColor(Color.WHITE);
        TimerPaint.setTextSize(150);

        anspaints.add(ans1Paint);
        anspaints.add(ans2Paint);
        anspaints.add(ans3Paint);
        anspaints.add(ans4Paint);

        ZiuqGame.selectQuestions(new Random());
        controller = new GameController(this);
    }

    public void draw(Canvas canvas){
        super.draw(canvas);
        thisCanvas = canvas;

        ans1.setPosition(canvas.getWidth() / 2 - 300, canvas.getHeight() / 3);
        ans2.setPosition(canvas.getWidth() / 2 + 300, canvas.getHeight() / 3);
        ans3.setPosition(canvas.getWidth() / 2 - 300, canvas.getHeight() / 1.5f);
        ans4.setPosition(canvas.getWidth() / 2 + 300, canvas.getHeight() / 1.5f);
        rdy.setPosition(canvas.getWidth() / 2, canvas.getHeight() / 1.5f);

        if (controller.inst == false) {
            controller.init();
            controller.inst = true;
        }
        else {
            if (state.equals("q")) {
                if (!answerListeners) {
                    this.addTouchListener(ans1);
                    this.addTouchListener(ans2);
                    this.addTouchListener(ans3);
                    this.addTouchListener(ans4);
                    answerListeners = true;
                }

                if (pauseListener){
                    this.removeTouchListener(rdy);
                    pauseListener = false;
                }


                drawQuestion();
            }
            else if (state.equals("p")) {
                if(answerListeners) {
                    this.removeTouchListener(ans1);
                    this.removeTouchListener(ans2);
                    this.removeTouchListener(ans3);
                    this.removeTouchListener(ans4);

                    answerListeners = false;
                }
                if (!pauseListener) {
                    this.addTouchListener(rdy);
                    pauseListener = true;
                }
                drawPause();

            }

            else if (state.equals("s")) {

                if (answerListeners){
                    this.removeTouchListener(ans1);
                    this.removeTouchListener(ans2);
                    this.removeTouchListener(ans3);
                    this.removeTouchListener(ans4);
                    answerListeners = false;
                }
                if (pauseListener) {
                    this.removeTouchListener(rdy);
                    pauseListener = false;
                }


                drawScore();
            }
            else if (state.equals("hs")) {
                if (answerListeners){
                    this.removeTouchListener(ans1);
                    this.removeTouchListener(ans2);
                    this.removeTouchListener(ans3);
                    this.removeTouchListener(ans4);
                    answerListeners = false;
                }
                if (pauseListener) {
                    this.removeTouchListener(rdy);
                    pauseListener = false;
                }

                drawHighScore();
            }
        }
    }

    public void drawPause() {
        thisCanvas.drawText("Pass the device to player " + (controller.currentPlayer + 1), thisCanvas.getWidth() / 5 - 100, thisCanvas.getHeight() / 1.2f, playerPaint);
        rdy.draw(thisCanvas);
    }

    public void drawHighScore () {
        thisCanvas.drawText("GAME FINISHED", thisCanvas.getWidth() / 4, thisCanvas.getHeight() / 5, playerPaint);
        Highscore hi = new Highscore(new ArrayList<>(ZiuqGame.getPlayers()));
        for (int i = 0; i < hi.getHighscore().size(); i++) {
            thisCanvas.drawText(hi.getHighscore().get(i).toString(), thisCanvas.getWidth()/5, (thisCanvas.getHeight()/3)+100*i, playerPaint );
        }
    }
    public void drawQuestion () {
        ans1.draw(thisCanvas);
        ans2.draw(thisCanvas);
        ans3.draw(thisCanvas);
        ans4.draw(thisCanvas);

        for (int i = 0; i < anspaints.size(); i++) {
            anspaints.get(i).setTextSize(75 -  (1.4f*ansbtns.get(i).getText().length()));
            thisCanvas.drawText(ansbtns.get(i).getText(), ansbtns.get(i).getX() - ansbtns.get(i).getImageWidth()/3, ansbtns.get(i).getY(), anspaints.get(i));
        }

        QuestionPaint.setTextSize(100 - 0.9f * controller.thisQuestion.getText().length());

        thisCanvas.drawText(controller.thisQuestion.getText(), thisCanvas.getWidth()/10, thisCanvas.getHeight()/5, QuestionPaint);

        if (ZiuqGame.getTimeLimit() != 0) thisCanvas.drawText(""+controller.secondsLeft, thisCanvas.getWidth()-200, thisCanvas.getHeight()/10, TimerPaint);


    }

    public void drawScore () {


        Highscore hi = new Highscore(new ArrayList<>(ZiuqGame.getPlayers()));
        for (int i = 0; i < hi.getHighscore().size(); i++) {
            thisCanvas.drawText(hi.getHighscore().get(i).toString(), thisCanvas.getWidth()/5, (thisCanvas.getHeight()/3)+100*i, playerPaint );
        }
        if (ZiuqGame.getTimeLimit() != 0) thisCanvas.drawText(""+controller.secondsLeft, thisCanvas.getWidth()-200, thisCanvas.getHeight()/10, TimerPaint);

        if (state == "s") {
            thisCanvas.drawText("Correct answer was : ", thisCanvas.getWidth()/9,
                    thisCanvas.getHeight()/1.2f, playerPaint );
            thisCanvas.drawText(controller.prevQuestion.getRightAnswer().getText(),thisCanvas.getWidth()/9,
                    thisCanvas.getHeight()/1.1f, playerPaint );
        }


    }

    public void update(float dt) {
        super.update(dt);
        ans1.update(dt);
        ans2.update(dt);
        ans3.update(dt);
        ans4.update(dt);
        rdy.update(dt);
    }
}
