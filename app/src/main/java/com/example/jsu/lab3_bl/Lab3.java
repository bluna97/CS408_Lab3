package com.example.jsu.lab3_bl;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.*;
import android.widget.*;

public class Lab3 extends AppCompatActivity {

    ArrayList<String> secretWords;
    String secretWord;
    String scrambledSecretWord;
    String playerWord;

    int incorrectGuesses;
    int currentLetterIndex;

    boolean isGameOver;
    String gameOverMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        secretWords = new ArrayList<>(Arrays.asList("APPLE", "BANANA", "CHERRY", "DOORKNOB", "ELEPHANT", "FORTUNE", "GOURD",
                "HAPPY", "INSIDE", "JOURNEY", "KNIGHT", "LARGE", "MANDATORY", "NEVER", "OXYGEN", "PAPER", "QUANTUM",
                "REPTILE", "SMALL", "TOUGH", "UNDER", "VECTOR", "WONDERFUL", "XYLOPHONE", "YELLOW", "ZEBRA"));
        gameOverMessage = "You guessed the word. Press GUESS to play again.";

        refreshGame();
    }

    private void refreshGame() {
        scrambledSecretWord = generateNewWord();
        playerWord = "";
        incorrectGuesses = 0;
        currentLetterIndex = 0;
        isGameOver = false;

        TextView s = (TextView) findViewById(R.id.scrambledLabel);
        s.setText(scrambledSecretWord);
        TextView p = (TextView) findViewById(R.id.playerLabel);
        p.setText(playerWord);

        TextView i = (TextView) findViewById(R.id.incorrectGuessLabel);
        i.setText("");
        TextView g = (TextView) findViewById(R.id.gameResultLabel);
        g.setText("");

    }

    private String generateNewWord() {
        String word = secretWords.get((int) (Math.random()*(secretWords.size())));
        secretWord = word;
        String shuffledWord = "";
        ArrayList<String> splitWord = new ArrayList<>(Arrays.asList(word.split("")));
        Collections.shuffle(splitWord);
        for (String c : splitWord)
            shuffledWord += c;
        return shuffledWord;
    }

    public void guessButtonClicked(View v) {
        TextView t = (TextView) findViewById(R.id.inputField);
        String playerGuess = t.getText().toString();

        if (isGameOver) {
            refreshGame();
        }
        else {
            playerGuess = playerGuess.toUpperCase();
            checkGuess(playerGuess);
            checkIfGameOver();
        }
        t.setText("");
    }

    private void checkGuess(String guess) {
        if(guess.length() == 1 && secretWord.charAt(currentLetterIndex) == guess.charAt(0)) {
            playerWord = playerWord + guess;
            TextView p = (TextView) findViewById(R.id.playerLabel);
            p.setText(playerWord);
            ++currentLetterIndex;
        }
        else{
            ++incorrectGuesses;
            TextView p = (TextView) findViewById(R.id.incorrectGuessLabel);
            p.setText("Incorrect guesses: " + String.valueOf(incorrectGuesses));
        }
    }

    private void checkIfGameOver() {
        if(secretWord.equals(playerWord)) {
            isGameOver = true;
            TextView g = (TextView) findViewById(R.id.gameResultLabel);
            g.setText(gameOverMessage);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lab3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
