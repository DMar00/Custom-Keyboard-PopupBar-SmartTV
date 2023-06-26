package com.dama.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridLayout;
import android.widget.TextView;

import com.dama.customkeyboardpopupcolorstv.R;

public class PopupBarView extends GridLayout {
    private TextView redKeyLabel;
    private TextView greenKeyLabel;
    private TextView yellowKeyLabel;
    private TextView blueKeyLabel;

    public PopupBarView(Context context) {
        super(context);
    }

    public PopupBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PopupBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initPopupBarView(){
        redKeyLabel = findViewById(R.id.labelRedKey);
        greenKeyLabel = findViewById(R.id.labelGreenKey);
        yellowKeyLabel = findViewById(R.id.labelYellowKey);
        blueKeyLabel = findViewById(R.id.labelBlueKey);
    }

    public void changeRedLabel(String r){
        String label = checkedLabel(r);
        redKeyLabel.setText(label);
    }

    public void changeYellowLabel(String y){
        String label = checkedLabel(y);
        yellowKeyLabel.setText(label);
    }

    public void changeGreenLabel(String g){
        String label = checkedLabel(g);
        greenKeyLabel.setText(label);
    }

    public void changeBlueLabel(String b){
        String label = checkedLabel(b);
        blueKeyLabel.setText(label);
    }

    private String checkedLabel(String s){
        if(s.equals(" "))
            return "␣";
        else return s;
    }
}
