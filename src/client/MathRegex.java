package client;

/**
 * Created by Dimitri on 16/05/2016.
 */
public enum MathRegex {
    ADDITION("^[0-9]+ *\\+ *[0-9]+"),
    SUBSTRACTION("");

    private String regex;

    MathRegex(String regex){
        this.regex = regex;
    }

    @Override
    public String toString() {
        return regex;
    }
}
