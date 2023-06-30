package com.dama.views;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.dama.customkeyboardpopupcolorstv.R;

public class ContainerView extends FrameLayout {
    private KeyView cursor;
    private PopupBarView popupBarView;
    private KeyView highLightRed, highLightGreen, highLightYellow, highLightBlue;
    private float currX, currY;

    public ContainerView(@NonNull Context context) {
        super(context);
    }

    public ContainerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ContainerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initContainerView(KeyView focusedView, PopupBarView popupBarView){
        //create cursor
        Drawable cs = ContextCompat.getDrawable(getContext(), R.drawable.cursor);
        cursor = new KeyView(getContext(), cs, null, "#FBFBFB");
        //popupBar set
        this.popupBarView = popupBarView;
        //set initial position cursor
        initCursorPosition(focusedView);
        //init popupBar
        this.popupBarView.initPopupBarView();
        addView(popupBarView);
        addPopupBar(focusedView);
        hidePopupBar();
        //init highLights
        highLightRed = new KeyView(getContext(), ContextCompat.getDrawable(getContext(), R.drawable.highlight_red), null, "#FBFBFB");
        highLightGreen = new KeyView(getContext(), ContextCompat.getDrawable(getContext(), R.drawable.highlight_green), null, "#FBFBFB");
        highLightYellow = new KeyView(getContext(), ContextCompat.getDrawable(getContext(), R.drawable.highlight_yellow), null, "#FBFBFB");
        highLightBlue = new KeyView(getContext(), ContextCompat.getDrawable(getContext(), R.drawable.highlight_blue), null, "#FBFBFB");
        addView(highLightRed);
        addView(highLightGreen);
        addView(highLightYellow);
        addView(highLightBlue);
        hideHighLights();
    }

    public Rect rectCoordinate(KeyView keyView){
        Rect offsetViewBounds = new Rect();
        keyView.getDrawingRect(offsetViewBounds);
        offsetDescendantRectToMyCoords(keyView, offsetViewBounds);
        return offsetViewBounds;
    }

    public void initCursorPosition(KeyView keyView){
        keyView.post(() -> {
            //coordinate keyView
            Rect keyViewCoordinates = rectCoordinate(keyView);
            int x = keyViewCoordinates.left;
            int y = keyViewCoordinates.top;

            //remove cursor
            removeView(cursor);

            //add cursor
            LayoutParams layoutParams = new LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT
            );
            layoutParams.leftMargin = x; //set x
            layoutParams.topMargin = y; //set y
            cursor.setLayoutParams(layoutParams);
            cursor.changeDimension(keyView.getKeyHeight(), keyView.getKeyWidth(), 0);
            addView(cursor);

            currX = cursor.getX();
            currY = cursor.getY();
        });
    }

    public void moveCursor(KeyView keyView){
        keyView.post(() -> {
            //keyView coordinate
            Rect keyViewCoordinates = rectCoordinate(keyView);
            int x = keyViewCoordinates.left;
            int y = keyViewCoordinates.top;

            //distances
            float newX = x - cursor.getX();
            float newY = y - cursor.getY();


            //create animation
            TranslateAnimation animation = new TranslateAnimation(currX,newX,currY,newY);
            animation.setFillAfter(true);
            animation.setFillBefore(true);
            animation.setDuration(100);
            //animation.setInterpolator(new AccelerateInterpolator());

            //start animation on view
            cursor.startAnimation(animation);
            cursor.changeDimension(keyView.getKeyHeight(), keyView.getKeyWidth(), 0);

            //update
            this.currX = newX;
            this.currY = newY;
        });
    }

    public void addPopupBar(KeyView focusedKeyView){
        Log.d("addPopupBar","true in containerView");
        //update popupBar position
        int lenPopupBar = popupBarView.getWidth();
        int lenKey = focusedKeyView.getWidth();

        Rect keyViewCoordinates = rectCoordinate(focusedKeyView);
        int x = keyViewCoordinates.left;
        int y = keyViewCoordinates.top;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.leftMargin = x - (lenPopupBar/2) + (lenKey/2); //set x
        layoutParams.topMargin = y - 25; //set y
        popupBarView.setLayoutParams(layoutParams);

        //add popupBar
        showPopupBar();
    }

    private boolean isPopupBarInContainer(){
        int indexOfChild = indexOfChild(popupBarView);
        return indexOfChild!=-1;
    }

    public void hidePopupBar(){
        popupBarView.setVisibility(View.INVISIBLE);
    }

    private void showPopupBar(){
        popupBarView.setVisibility(View.VISIBLE);
    }

    public PopupBarView getPopupBarView() {
        return popupBarView;
    }

    /**
     *
     * @param keyView where to put the highlight
     * @param type 0 for red - 1 for green - 2 for yellow - 3 for blue
     */
    public void addHighlight(KeyView keyView, int type){
        Rect keyViewCoordinates = rectCoordinate(keyView);
        int x = keyViewCoordinates.left;
        int y = keyViewCoordinates.top;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.leftMargin = x; //set x
        layoutParams.topMargin = y; //set y
        switch (type){
            case 0 :
                highLightRed.setLayoutParams(layoutParams);
                highLightRed.changeDimension(keyView.getKeyHeight(), keyView.getKeyWidth(), 0);
                break;
            case 1 :
                highLightGreen.setLayoutParams(layoutParams);
                highLightGreen.changeDimension(keyView.getKeyHeight(), keyView.getKeyWidth(), 0);
                break;
            case 2 :
                highLightYellow.setLayoutParams(layoutParams);
                highLightYellow.changeDimension(keyView.getKeyHeight(), keyView.getKeyWidth(), 0);
                break;
            case 3 :
                highLightBlue.setLayoutParams(layoutParams);
                highLightBlue.changeDimension(keyView.getKeyHeight(), keyView.getKeyWidth(), 0);
                break;
        }

        //showHighlights
        showHighLights();
    }

    private void showHighLights(){
        highLightRed.setVisibility(View.VISIBLE);
        highLightGreen.setVisibility(View.VISIBLE);
        highLightYellow.setVisibility(View.VISIBLE);
        highLightBlue.setVisibility(View.VISIBLE);
    }

    public void hideHighLights(){
        highLightRed.setVisibility(View.INVISIBLE);
        highLightGreen.setVisibility(View.INVISIBLE);
        highLightYellow.setVisibility(View.INVISIBLE);
        highLightBlue.setVisibility(View.INVISIBLE);
    }
}
