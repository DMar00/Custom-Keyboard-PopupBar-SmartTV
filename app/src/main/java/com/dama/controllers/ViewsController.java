package com.dama.controllers;

import android.view.View;
import android.widget.FrameLayout;

import com.dama.customkeyboardpopupcolorstv.R;
import com.dama.utils.Cell;
import com.dama.utils.Key;
import com.dama.views.ContainerView;
import com.dama.views.KeyView;
import com.dama.views.KeyboardView;
import com.dama.views.PopupBarView;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewsController {
    private ContainerView containerView;
    private KeyboardView keyboardView;

    public ViewsController(FrameLayout rootView) {
        containerView = rootView.findViewById(R.id.container_view);
        keyboardView = containerView.findViewById(R.id.keyboard_view);
    }

    public void drawKeyboard(HashMap<Integer, ArrayList<Key>> keys, Cell focus, PopupBarView popupBarView){
        keyboardView.initKeyboardView(keys);

        KeyView keyView = keyboardView.getKeyViewAtCell(focus);
        keyView.post(()->{
            containerView.initContainerView(keyView, popupBarView);
        });

    }

    public void moveCursorPosition(Cell focus){
        KeyView keyView = keyboardView.getKeyViewAtCell(focus);
        containerView.moveCursor(keyView);
    }

    public void fillPopBar(char r, char g, char y, char b){
        containerView.getPopupBarView().changeRedLabel(r+"");
        containerView.getPopupBarView().changeGreenLabel(g+"");
        containerView.getPopupBarView().changeYellowLabel(y+"");
        containerView.getPopupBarView().changeBlueLabel(b+"");
    }

    public void addPopupBar(Cell focus){
        KeyView keyView = keyboardView.getKeyViewAtCell(focus);
        containerView.addPopupBar(keyView);
    }

    public void hidePopupBar(){
        containerView.hidePopupBar();
    }

    public void addHighlights(Cell k1, Cell k2, Cell k3, Cell k4){
        KeyView keyView1 = keyboardView.getKeyViewAtCell(k1);
        KeyView keyView2 = keyboardView.getKeyViewAtCell(k2);
        KeyView keyView3 = keyboardView.getKeyViewAtCell(k3);
        KeyView keyView4 = keyboardView.getKeyViewAtCell(k4);
        containerView.addHighlight(keyView1, 0);
        containerView.addHighlight(keyView2, 1);
        containerView.addHighlight(keyView3, 2);
        containerView.addHighlight(keyView4, 3);
    }

    public void hideHighLights(){
        containerView.hideHighLights();
    }
}
