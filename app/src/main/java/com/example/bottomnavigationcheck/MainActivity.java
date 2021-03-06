package com.example.bottomnavigationcheck;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Button buyBox, openBox, clearTyans;
    private int numberOfBoxes = 0, numberOfUnits = 0;
    private String FILENAME = "values";
    private ListView lvMain;
    private TyanList boxAdapter;
    private ArrayList<Tyan> list = new ArrayList<Tyan>();
    private Available availableList;

    private void setupShop() {
        lvMain.setVisibility(View.INVISIBLE);
        mTextMessage.setVisibility(View.VISIBLE);
        openBox.setVisibility(View.INVISIBLE);
        buyBox.setVisibility(View.VISIBLE);
        clearTyans.setVisibility(View.INVISIBLE);
        mTextMessage.setTextSize(30);
        mTextMessage.setText("Support us pls");
    }

    private void setupOpening() {
        lvMain.setVisibility(View.INVISIBLE);
        mTextMessage.setVisibility(View.VISIBLE);
        openBox.setVisibility(View.VISIBLE);
        buyBox.setVisibility(View.INVISIBLE);
        clearTyans.setVisibility(View.INVISIBLE);
        mTextMessage.setTextSize(24);
        mTextMessage.setText("You've got " + Integer.toString(numberOfBoxes) + " boxes");
    }

    private void setupList() {
        updateList();
        lvMain.setVisibility(View.VISIBLE);
        openBox.setVisibility(View.INVISIBLE);
        buyBox.setVisibility(View.INVISIBLE);
        clearTyans.setVisibility(View.VISIBLE);
        mTextMessage.setVisibility(View.VISIBLE);
        mTextMessage.setTextSize(24);
        mTextMessage.setText("You've got " + Integer.toString(numberOfUnits) + " girls");
    }

    private void writeValues() {
        FileOutputStream fos = null;
        try {
            String out = Integer.toString(numberOfBoxes) + " " + Integer.toString(numberOfUnits);
            out += " ";
            for (int i = 0; i < numberOfUnits; i++) {
                out += getShort(list.get(i).getName()) + " ";
            }
            fos = openFileOutput(FILENAME, MODE_PRIVATE);
            fos.write(out.getBytes());
//            Toast.makeText(this, "Файл сохранен", Toast.LENGTH_LONG).show();
        } catch(IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }  finally {
            try {
                if(fos != null) {
                    fos.close();
                }
            } catch(IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void readValues() {
        FileInputStream fin = null;
        try {
            fin = openFileInput(FILENAME);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String (bytes);
            String textArr[] = text.split(" ");
            numberOfBoxes = Integer.parseInt(textArr[0]);
            numberOfUnits = Integer.parseInt(textArr[1]);
//            Toast.makeText(this, numberOfUnits, Toast.LENGTH_LONG).show();
            for (int i = 0; i < numberOfUnits; i++) {
                String nxtName = textArr[i + 2];
                addToList("mipmap/ic_" + nxtName);
            }
        } catch(IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        finally {
            try {
                if(fin != null) {
                    fin.close();
                }
            } catch(IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private String getReal(String shortName) {
        for (int i = 0; i < availableList.getSize(); i++) {
            if (availableList.names.get(i)[0].equals(shortName)) {
                return availableList.names.get(i)[1];
            }
        }
        return "anime";
    }

    private String getShort(String realName) {
        for (int i = 0; i < availableList.getSize(); i++) {
            if (availableList.names.get(i)[1].equals(realName)) {
                return availableList.names.get(i)[0];
            }
        }
        return "anime";
    }

    private void addToList(String name) {
        String shortName;
        int posStart = -1, posEnd = name.length();
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) == '_' && posStart == -1) {
                posStart = i + 1;
            } else if (name.charAt(i) == '_' && posEnd == name.length()) {
                posEnd = i;
            }
        }
        shortName = name.substring(posStart, posEnd);
        String realName = getReal(shortName);
        int temp = getResources().getIdentifier(name, "id", getPackageName());
        list.add(new Tyan(realName, temp, list.size() + 1, true));
        updateList();
    }

    public boolean haveTyan(String realName) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(realName)) {
                return true;
            }
        }
        return false;
    }

    private boolean addRandomToList() {
        int min = 0;
        int max = availableList.getSize() - 1;
        int position = min + (int)(Math.random() * (max - min + 1));
        if (haveTyan(availableList.names.get(position)[1])) {
            return false;
        }
        addToList("mipmap/ic_" + availableList.names.get(position)[0]);
        return  true;
    }

    private void updateList() {
        lvMain = findViewById(R.id.lvMain);
        lvMain.setAdapter(boxAdapter);
    }

    private void init() {
        availableList = new Available();
        boxAdapter = new TyanList(this, list);
        mTextMessage = findViewById(R.id.message);
        buyBox = findViewById(R.id.buy);
        openBox = findViewById(R.id.open);
        clearTyans = findViewById(R.id.clear);
        mTextMessage.setVisibility(View.INVISIBLE);
        buyBox.setVisibility(View.INVISIBLE);
        openBox.setVisibility(View.INVISIBLE);
        buyBox.setOnClickListener(onClickListener);
        openBox.setOnClickListener(onClickListener);
        clearTyans.setOnClickListener(onClickListener);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        readValues();
        setupList();
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buy:
                    numberOfBoxes++;
                    writeValues();
                    break;
                case R.id.open:
                    if (numberOfBoxes == 0) {
                        mTextMessage.setTextSize(15);
                        mTextMessage.setText(getString(R.string.notEnoughBoxes));
                        break;
                    }
                    numberOfBoxes--;
                    if (addRandomToList()) {
                        numberOfUnits++;
                    } else {
                        Toast.makeText(getApplicationContext(), "You have got this one, sorry", Toast.LENGTH_LONG).show();
                    }
                    writeValues();
                    setupOpening();
                    break;
                case R.id.clear:
                    resetValues();
                    setupList();
                    break;
            }
        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_boxes:
                    setupList();
                    return true;
                case R.id.navigation_open:
                    setupOpening();
                    return true;
                case R.id.navigation_shop:
                    setupShop();
                    return true;
            }
            return false;
        }
    };

    private void resetValues() {
        numberOfBoxes = numberOfUnits = 0;
        list.clear();
        writeValues();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        resetValues();
        init();
    }

}
