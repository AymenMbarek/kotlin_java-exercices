package com.example.flagquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView resultTextView;
    GridLayout buttonGrid;
    ArrayList<String> flagnames;
    ArrayList<String> continentnames;
    ImageView flagImageView;
    Random random = new Random();
    AssetManager assetManager;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;
    String incorrectCountryFilename;
    String incorrectCountry;
    ArrayList<Button> buttonArray = new ArrayList<Button>();
    int buttoncount;
    String country;
    int currentQuestion = 1;
    int totalQuestions = 10;
    TextView quizProgress;
    int randomimageindex;
    String countryFilename;
    String continent;
    String countryFilePath;

    public void disableAllButtons() {
        for (int i = 0; i < buttoncount; i++) {
            buttonArray.get(i).setEnabled(false);
        }
    }

    public void enableAllButtons() {
        for (int i = 0; i < buttoncount; i++) {
            buttonArray.get(i).setEnabled(true);
        }
    }

    private void buttonpressed(Button btn) {
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        if (btn.getText() == country) {
            resultTextView.setText(btn.getText() + " !");
            enableAllButtons();
            currentQuestion++;
            resultTextView.setText("");
            setQuestion();
            setButtons(buttoncount);
        } else {
            resultTextView.setText("Incorrect!");
            btn.setEnabled(false);

        }
    }

    private void setQuestion() {
        quizProgress.setText("Question " + currentQuestion + " out of " + totalQuestions);
        try {
            continentnames = new ArrayList<String>(Arrays.asList(assetManager.list("Countries")));
            flagnames = new ArrayList<String>(Arrays.asList(assetManager.list("Countries/" +
                    continentnames.get(random.nextInt(continentnames.size())))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        randomimageindex = random.nextInt(flagnames.size());
        countryFilename = flagnames.get(randomimageindex);
        country = countryFilename.split("-")[1].split("\\.")[0].replace("_", " ");
        continent = countryFilename.split("-")[0];
        countryFilePath = "Countries/" + continent + "/" + countryFilename;
        buttonGrid = (GridLayout) findViewById(R.id.buttonGrid);
        flagImageView = (ImageView) findViewById(R.id.flagImageView);
        try (
                InputStream inputStream = assetManager.open(countryFilePath)
        ) {
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            flagImageView.setImageBitmap(bitmap);
        } catch (IOException ex) {

        }
    }

    private void setButtons(int buttoncount) {
        Collections.shuffle(flagnames);
        flagnames.remove(countryFilename);
        if (buttonArray.size() == 0) {
            createButtons(buttoncount);
            buttonGrid.setRowCount(buttoncount / 2);
            buttonArray.add(btn1);
            buttonArray.add(btn2);
            buttonArray.add(btn3);
            buttonArray.add(btn4);
            buttonArray.add(btn5);
            buttonArray.add(btn6);
            buttonArray.add(btn7);
            buttonArray.add(btn8);
        }

        int randomButtonIndex = random.nextInt(buttoncount);
        if (buttonGrid.getChildCount() == 0) {
            for (int i = 0; i < buttoncount; i++) {
                if (i != randomButtonIndex) {
                    incorrectCountryFilename = flagnames.get(i);
                    incorrectCountry = incorrectCountryFilename.split("-")[1].split("\\.")[0].replace("_", " ");
                    buttonArray.get(i).setText(incorrectCountry);
                } else {
                    buttonArray.get(i).setText(country);
                }
              //  buttonArray.get(i).setWidth(buttonGrid.getMeasuredWidth());
                buttonGrid.addView(buttonArray.get(i));
            }
        } else {
            for (int i = 0; i < buttoncount; i++) {
                if (i != randomButtonIndex) {
                    incorrectCountryFilename = flagnames.get(i);
                    incorrectCountry = incorrectCountryFilename.split("-")[1].split("\\.")[0].replace("_", " ");
                    buttonArray.get(i).setText(incorrectCountry);

                } else {
                    buttonArray.get(i).setText(country);

                }
               // buttonArray.get(i).setWidth(buttonGrid.getMeasuredWidth());

            }
        }

    }
private void createButtons(int buttoncount){
    this.buttoncount = buttoncount;
    View.OnClickListener v=new View.OnClickListener() {
        public void onClick(View v) {
            buttonpressed(btn1);
        }
    };

    btn1 = new Button(this);
    btn1.setOnClickListener(v);
    btn2 = new Button(this);
    btn2.setOnClickListener(v);
    btn3 = new Button(this);
    btn3.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            buttonpressed(btn3);
        }
    });
    btn4 = new Button(this);
    btn4.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            buttonpressed(btn4);
        }
    });
    btn5 = new Button(this);
    btn5.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            buttonpressed(btn5);
        }
    });
    btn6 = new Button(this);
    btn6.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            buttonpressed(btn6);
        }
    });
    btn7 = new Button(this);
    btn7.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            buttonpressed(btn7);
        }
    });
    btn8 = new Button(this);
    btn8.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            buttonpressed(btn8);
        }
    });
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // using toolbar as ActionBar
        setSupportActionBar(toolbar);

        assetManager = this.getAssets();
        setContentView(R.layout.activity_main);
        quizProgress = (TextView) findViewById(R.id.quizProgress);
        setQuestion();
        setButtons(8);


    }
}