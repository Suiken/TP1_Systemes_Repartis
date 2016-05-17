package client;

/**
 * Created by Dimitri on 16/05/2016.
 */
public enum MathRegex {
    ADDITION("^[0-9]+ *\\+ *[0-9]+", "add", "\\+"),
    SUBSTRACTION("^[0-9]+ *\\- *[0-9]+", "substract", "\\-"),
    MULTIPLICATION("^[0-9]+ *\\* *[0-9]+", "multiply", "\\*"),
    OPERATION("^[0-9]+ *[\\+\\-\\*] *[0-9]+", "", "");

    private String regex;
    private String method;
    private String operator;

    MathRegex(String regex, String method, String operator){
        this.regex = regex;
        this.method = method;
        this.operator = operator;
    }

    @Override
    public String toString() {
        return regex;
    }

    public String getMethodName(){
        return method;
    }

    public String getOperator(){
        return operator;
    }
}
