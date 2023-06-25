package com.dama.controllers;

public class TextController {
    private final int N_SEQ = 4;

    private String sequence;

    public TextController() {
        reset();
    }

    public void addCharacterWritten(char character){
        sequence += character;
        if(sequence.length() > N_SEQ)
            sequence = sequence.substring(sequence.length() - N_SEQ);
    }

    public void deleteCharacterWritten(String oldSequence){
        if(sequence.length()>0)
            sequence = sequence.substring(0, sequence.length() - 1);

        if(oldSequence.length()>0 && oldSequence.length() >= N_SEQ)
            sequence = oldSequence.charAt(oldSequence.length() - N_SEQ) + sequence;
    }

    public String getFourCharacters(){
        String str = getSpaces() + sequence;
        return str;
    }

    private String getSpaces(){
        StringBuilder stringBuilder = new StringBuilder();
        while (stringBuilder.length() + sequence.length() < N_SEQ) {
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    public void reset(){
        sequence="";
    }
}
