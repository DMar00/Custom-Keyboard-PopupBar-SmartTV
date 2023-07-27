package com.dama.service;

import static java.lang.Character.isLetter;

import android.inputmethodservice.InputMethodService;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.FrameLayout;

import com.dama.controllers.Controller;
import com.dama.customkeyboardpopupcolorstv.R;
import com.dama.log.TemaImeLogger;
import com.dama.utils.Cell;
import com.dama.utils.Key;
import com.dama.views.PopupBarView;

public class KeyboardImeService extends InputMethodService {
    private Controller controller;
    private boolean keyboardShown;
    private InputConnection ic;
    private FrameLayout rootView;
    private TemaImeLogger temaImeLogger;

    @Override
    public View onCreateInputView() {
        rootView = (FrameLayout) this.getLayoutInflater().inflate(R.layout.keyboard_layout, null);
        controller = new Controller(getApplicationContext(), rootView);

        PopupBarView popupBarView = (PopupBarView) this.getLayoutInflater().inflate(R.layout.popup_bar, null);
        controller.drawKeyboard(popupBarView);

        temaImeLogger = new TemaImeLogger(getApplicationContext());

        return rootView;
    }

    @Override
    public boolean onEvaluateFullscreenMode() {
        return false;
    }

    @Override
    public void onStartInputView(EditorInfo info, boolean restarting) {
        super.onStartInputView(info, restarting);
        keyboardShown = true;
    }

    @Override
    public void onFinishInput() {
        super.onFinishInput();
        controller.getTextController().reset();
    }

    @Override
    public void onFinishInputView(boolean finishingInput) {
        super.onFinishInputView(finishingInput);
        keyboardShown = false;
        controller.hidePopUpBar();
        controller.getTextController().reset();
        controller.resetFocus();
        //ic = null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyboardShown){
            Log.d("KeyboardImeService", "onKeyDown code: "+ keyCode);
            ic = getCurrentInputConnection();
            switch (keyCode){
                case KeyEvent.KEYCODE_BACK:
                    hideKeyboard();
                    break;
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    temaImeLogger.writeToLog("LEFT",false);
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    temaImeLogger.writeToLog("RIGHT",false);
                case KeyEvent.KEYCODE_DPAD_UP:
                    temaImeLogger.writeToLog("UP",false);
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    if(keyCode == KeyEvent.KEYCODE_DPAD_DOWN){
                        temaImeLogger.writeToLog("DOWN",false);
                    }

                    controller.hidePopUpBar();
                    Cell newCell = controller.findNewFocus(keyCode);
                    if (controller.isNextFocusable(newCell)){
                        //update focus
                        controller.getFocusController_().setCurrentFocus(newCell);
                        controller.moveFocusOnKeyboard(newCell);
                    }
                    break;
                case KeyEvent.KEYCODE_DPAD_CENTER:
                case KeyEvent.KEYCODE_0:    //todo remove - just for emulator test OK
                    Cell focus = controller.getFocusController_().getCurrentFocus();
                    Key key = controller.getKeysController().getKeyAtPosition(focus);

                    String btn;
                    if(key.getCode() == Controller.ENTER_KEY)
                        btn = "INVIO";
                    else btn = key.getLabel();
                    temaImeLogger.writeToLog("CENTER: "+btn,false);





                    int code = key.getCode();
                    if(code!=Controller.FAKE_KEY){
                        char character = (char) code;
                        if(code!=Controller.ENTER_KEY && code!=Controller.DEL_KEY)
                            controller.getTextController().addCharacterWritten(character);
                        handleText(code, ic);

                        //if letter or space
                        if(code!=Controller.ENTER_KEY && code!=Controller.DEL_KEY){
                            if(isLetter(key.getLabel().charAt(0)) || key.getLabel().charAt(0)==' ') {
                                controller.showPopUpBar();
                            }else {
                                controller.hidePopUpBar();
                            }
                        }
                    }else {
                        controller.hidePopUpBar();
                    }
                    break;
                case KeyEvent.KEYCODE_1:
                case KeyEvent.KEYCODE_PROG_RED:
                    //write char
                    if(controller.getSuggestionsController().isShown()){
                        Key k1 = controller.getFourSuggestionsCode().get(0);

                        temaImeLogger.writeToLog("RED: "+k1.getLabel(),false);

                        handleText( k1.getCode(), ic);
                        controller.getTextController().addCharacterWritten(k1.getLabel().charAt(0));
                        //update bar
                        Cell redFocus = controller.getKeysController().getCharPosition(k1.getLabel().charAt(0));
                        controller.movePopUpBar(redFocus);
                    }
                    break;
                case KeyEvent.KEYCODE_2:
                case KeyEvent.KEYCODE_PROG_GREEN:
                    //temaImeLogger.writeToLog("GREEN",false);
                    if(controller.getSuggestionsController().isShown()){
                        Key k2 = controller.getFourSuggestionsCode().get(1);

                        temaImeLogger.writeToLog("GREEN: "+k2.getLabel(),false);

                        handleText( k2.getCode(), ic);
                        controller.getTextController().addCharacterWritten(k2.getLabel().charAt(0));
                        //update bar
                        Cell greenFocus = controller.getKeysController().getCharPosition(k2.getLabel().charAt(0));
                        controller.movePopUpBar(greenFocus);
                    }
                    break;
                case KeyEvent.KEYCODE_3:
                case KeyEvent.KEYCODE_PROG_YELLOW:
                    //temaImeLogger.writeToLog("YELLOW",false);
                    if(controller.getSuggestionsController().isShown()){
                        Key k3 = controller.getFourSuggestionsCode().get(2);

                        temaImeLogger.writeToLog("YELLOW: "+k3.getLabel(),false);

                        handleText( k3.getCode(), ic);
                        controller.getTextController().addCharacterWritten(k3.getLabel().charAt(0));
                        //update bar
                        Cell yellowFocus = controller.getKeysController().getCharPosition(k3.getLabel().charAt(0));
                        controller.movePopUpBar(yellowFocus);
                    }
                    break;
                case KeyEvent.KEYCODE_4:
                case KeyEvent.KEYCODE_PROG_BLUE:
                    //temaImeLogger.writeToLog("BLUE",false);
                    if(controller.getSuggestionsController().isShown()){
                        Key k4 = controller.getFourSuggestionsCode().get(3);

                        temaImeLogger.writeToLog("BLUE: "+k4.getLabel(),false);

                        handleText( k4.getCode(), ic);
                        controller.getTextController().addCharacterWritten(k4.getLabel().charAt(0));
                        //update bar
                        Cell blueFocus = controller.getKeysController().getCharPosition(k4.getLabel().charAt(0));
                        controller.movePopUpBar(blueFocus);
                    }
                    break;
            }
            return true;
        }
        return false;
    }

    private void handleText(int code, InputConnection ic){
        switch (code){
            case Controller.DEL_KEY:
                ic.deleteSurroundingText(1, 0);
                break;
            case Controller.ENTER_KEY:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_ENTER));
                break;
            default: //user press letter, number, symbol
                char c = (char) code;
                ic.commitText(String.valueOf(c),1);
        }
    }

    private void hideKeyboard(){
        requestHideSelf(0); //calls onFinishInputView
    }
}
