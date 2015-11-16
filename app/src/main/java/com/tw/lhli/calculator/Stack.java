package com.tw.lhli.calculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Stack {

    List<String> stack = new ArrayList<String>();
    private String stackTop;
    private static final HashMap<String, Integer> operationWeight;

    static {
        operationWeight = new HashMap<String, Integer>();
        operationWeight.put("×", 3);
        operationWeight.put("÷", 3);
        operationWeight.put("+", 2);
        operationWeight.put("-", 2);
        operationWeight.put("(", 1);
        operationWeight.put(")", 1);
        operationWeight.put(null, 0);
    }

    public void push(String operationString) {
        stack.add(operationString);
        this.stackTop = operationString;
    }

    public String pop() {
        if (stack.size() == 0) {
            return null;
        } else {
            String popString = stackTop;
            stack.remove(stackTop);
            stackTop = stack.size() == 0 ? null : stack.get(stack.size() - 1);
            return popString;
        }
    }

    public static boolean priority(String operationSymbol, String theOtherOperationSymbol) {
        return theOtherOperationSymbol == null
                || operationSymbol.matches("[+]|[-]|[×]|[÷]|[(]|[)]")
                && theOtherOperationSymbol.matches("[+]|[-]|[×]|[÷]|[(]|[)]")
                && operationWeight.get(operationSymbol) > operationWeight.get(theOtherOperationSymbol);
    }

    public boolean isEmpty() {
        return stack.size() == 0;
    }

    public String getStackTop() {
        return stackTop;
    }
}
