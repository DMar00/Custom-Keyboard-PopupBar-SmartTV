package com.dama.controllers;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import com.dama.customkeyboardpopupcolorstv.R;
import com.dama.utils.Cell;
import com.dama.utils.Key;
import com.dama.views.KeyView;
import com.dama.views.PopupBarView;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Controller {
    public static final int COLS = 10;
    public static final int ROWS = 5;
    public static final int INVALID_KEY = -1;
    public static final int HIDDEN_KEY = -3;
    public static final int SPACE_KEY = 32;
    public static final int ENTER_KEY = 66;
    public static final int FAKE_KEY = -66;
    public static final int DEL_KEY = 24;
    private FocusController focusController;
    private KeysController keysController;
    private ViewsController viewsController;
    private SuggestionsController suggestionsController;
    private TextController textController;

    public Controller(Context context, FrameLayout rootView) {
        keysController = new KeysController(new Keyboard(context, R.xml.qwerty));
        //keysController = new KeysController(new Keyboard(context, R.xml.abc));
        focusController = new FocusController();
        focusController.setCurrentFocus(new Cell(1,0)); //q
        viewsController = new ViewsController(rootView);
        suggestionsController = new SuggestionsController(context);
        textController = new TextController();
    }

    public void drawKeyboard(PopupBarView popupBarView){
        viewsController.drawKeyboard(keysController.getAllKeys(), focusController.getCurrentFocus(), popupBarView);
    }

    /*********************FOCUS**********************/
    public boolean isNextFocusable(Cell newFocus){
        Log.d("newFocus", ""+newFocus);
        if(focusController.isFocusInRange(newFocus)
                && !(keysController.isInvalidKey(newFocus))
                && !(keysController.isHiddenKey(newFocus))){
            Log.d("ok","ok");
            return true;
        }
        return false;
    }

    public Cell findNewFocus(int code){
        Cell curFocus = focusController.getCurrentFocus();
        Cell newFocus = focusController.calculateNewFocus(code);

        switch (code){
            case KeyEvent.KEYCODE_DPAD_UP:
                //last row behaviour focus
                if((curFocus.getRow() == ROWS-1)){
                    switch (curFocus.getCol()){
                        case 4:
                            newFocus.setCol(7);
                            break;
                        case 5:
                            newFocus.setCol(8);
                            break;
                        case 6:
                            newFocus.setCol(9);
                            break;
                    }
                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                //last row behaviour focus
                if((curFocus.getRow() == ROWS-2)){
                    switch (curFocus.getCol()){
                        case 4:
                        case 5:
                        case 6:
                            newFocus.setCol(3);
                            break;
                        case 7:
                            newFocus.setCol(4);
                            break;
                        case 8:
                            newFocus.setCol(5);
                            break;
                        case 9:
                            newFocus.setCol(6);
                            break;
                    }
                }
                break;
        }

        return newFocus;
    }

    public void moveFocusOnKeyboard(Cell position){
        viewsController.moveCursorPosition(position);
    }


    /*********************GETTERS**********************/
    public FocusController getFocusController_() {
        return focusController;
    }

    public KeysController getKeysController() {
        return keysController;
    }

    public TextController getTextController() {
        return textController;
    }

    /*********************SUGGESTIONS**********************/
    public void showPopUpBar(){
        //generate suggestions
        String sequence = textController.getFourCharacters();
        char[] allSuggestions = suggestionsController.getSuggestionsChars(sequence);

        //get only first 4 suggestions
        char[] fourSuggestions = Arrays.copyOfRange(allSuggestions, 0, 4);

        //put sug in bar
        viewsController.fillPopBar(fourSuggestions[0], fourSuggestions[1], fourSuggestions[2], fourSuggestions[3]);

        //add bar at position
        Cell curFocus = focusController.getCurrentFocus();
        viewsController.addPopupBar(curFocus);

        //add highlights
        Cell k1 = keysController.getCharPosition(fourSuggestions[0]);
        Cell k2 = keysController.getCharPosition(fourSuggestions[1]);
        Cell k3 = keysController.getCharPosition(fourSuggestions[2]);
        Cell k4 = keysController.getCharPosition(fourSuggestions[3]);
        viewsController.addHighlights(k1, k2, k3, k4);

        //
        suggestionsController.setShown(true);
    }

    public void hidePopUpBar(){
        viewsController.hidePopupBar();
        viewsController.hideHighLights();
        suggestionsController.setShown(false);
    }

    /*********************OTHER**********************/
    /*protected void modifyKeyContent(Cell position, int code, String label){
        keysController.modifyKeyAtPosition(position, code, label);
        viewsController.modifyKeyLabel(position, label);
    }*/
}
