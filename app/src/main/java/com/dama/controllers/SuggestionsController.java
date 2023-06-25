package com.dama.controllers;

import android.content.Context;
import com.dama.generators.SuggestionsGenerator;

public class SuggestionsController {
    public static final int N_SUG = 4;
    private SuggestionsGenerator suggestionsGenerator;
    private boolean shown;

    public SuggestionsController(Context context) {
        suggestionsGenerator = new SuggestionsGenerator(context);
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
}
