package com.dama.controllers;

import android.content.Context;
import com.dama.generators.SuggestionsGenerator;

public class SuggestionsController {
    public static final int N_SUG = 4;
    private SuggestionsGenerator suggestionsGenerator;
    private boolean shown;
    private char[] currentSuggestions;

    public SuggestionsController(Context context) {
        suggestionsGenerator = new SuggestionsGenerator(context);
        currentSuggestions = new char[4];
        shown = false;
    }

    /***************GENERATE SUGGESTIONS*****************/
    public char[] getSuggestionsChars(String sequence){
        char[] allSuggestions = suggestionsGenerator.getSuggestionArray(sequence);
        return allSuggestions;
    }

    /***************GETTERS & SETTERS*****************/
    public boolean isShown() {
        return shown;
    }

    public void setShown(boolean shown) {
        this.shown = shown;
    }

    public void setCurrentSuggestions(char c1, char c2, char c3, char c4){
        currentSuggestions[0] = c1;
        currentSuggestions[1] = c2;
        currentSuggestions[2] = c3;
        currentSuggestions[3] = c4;
    }

    public char[] getCurrentSuggestions() {
        return currentSuggestions;
    }
}
