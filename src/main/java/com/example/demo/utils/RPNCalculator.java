package com.example.demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RPNCalculator {

    private static final Logger log = LoggerFactory.getLogger(RPNCalculator.class);
    private static final String EXPRESSION = "-?[0-9.]+|[A-Za-z]+|[-+*/()\\[\\]^=,]";
    private static final Map<String, Integer> precedence = new HashMap<>();

    static {
        precedence.put("sin", 5);
        precedence.put("cos", 5);
        precedence.put("tan", 5);
        precedence.put("ctg", 5);
        precedence.put("max", 5);
        precedence.put("min", 5);
        precedence.put("avg", 5);
        precedence.put("sum", 5);
        precedence.put("^", 4);
        precedence.put("/", 3);
        precedence.put("*", 3);
        precedence.put("-", 2);
        precedence.put("+", 2);
        precedence.put(",", 1);
        precedence.put("=", 1);
        precedence.put("]", 1);
        precedence.put(")", 1);
        precedence.put("[", 0);
        precedence.put("(", 0);
    }

    public double RPNCalculate(List<String> expressions) throws EmptyStackException {
        Stack<Double> stack = new Stack<>();
        double op1;//operand
        double op2;
        double currVal;
        for (String exp : expressions) {
            if (isNumber(exp)) {
                stack.push(Double.parseDouble(exp));
            } else if (exp.length() == 1) {
                switch (exp.charAt(0)) {
                    case '+': {
                        op1 = stack.pop();
                        op2 = stack.pop();
                        currVal = op1 + op2;
                        stack.push(currVal);
                        break;
                    }
                    case '-': {
                        op1 = stack.pop();
                        op2 = stack.pop();
                        currVal = op1 - op2;
                        stack.push(currVal);
                        break;
                    }
                    case '^': {
                        op1 = stack.pop();
                        op2 = stack.pop();
                        currVal = Math.pow(op2, op1);
                        stack.push(currVal);
                        break;
                    }
                    case '*': {
                        op1 = stack.pop();
                        op2 = stack.pop();
                        currVal = op1 * op2;
                        stack.push(currVal);
                        break;
                    }
                    case '/': {
                        op1 = stack.pop();
                        op2 = stack.pop();
                        currVal = op2 / op1;
                        stack.push(currVal);
                        break;
                    }
                    default: {
                        log.debug(exp + " not +-*/");
                    }
                }
            } else if (exp.length() == 3) {

                switch (exp) {
                    case "sin": {
                        double convertToRadians = Math.toRadians(stack.pop());
                        currVal = Math.sin(convertToRadians);
                        stack.push(currVal);
                        break;
                    }
                    case "cos": {
                        double convertToRadians = Math.toRadians(stack.pop());
                        currVal = Math.cos(convertToRadians);
                        stack.push(currVal);
                        break;
                    }
                    case "tan": {
                        double convertToRadians = Math.toRadians(stack.pop());
                        currVal = Math.tan(convertToRadians);
                        stack.push(currVal);
                        break;
                    }
                    case "ctg": {
                        double convertToRadians = Math.toRadians(stack.pop());
                        currVal = 1.0 / Math.tan(convertToRadians);
                        stack.push(currVal);
                        break;
                    }
                    case "max": {
                        op1 = stack.pop();
                        int count = (int) op1;
                        double max = 0;
                        for (int i = 0; i < count; i++) {
                            double temp = stack.pop();
                            if (i == 0) {
                                max = temp;
                            } else {
                                if (temp > max) {
                                    max = temp;
                                }
                            }
                        }
                        stack.push(max);
                        break;
                    }
                    case "min": {
                        op1 = stack.pop();
                        int count = (int) op1;
                        double min = 0;
                        for (int i = 0; i < count; i++) {
                            double temp = stack.pop();
                            if (i == 0) {
                                min = temp;
                            } else {
                                if (temp < min) {
                                    min = temp;
                                }
                            }
                        }
                        stack.push(min);
                        break;
                    }
                    case "sum": {
                        op1 = stack.pop();
                        int count = (int) op1;
                        double sum = 0;
                        for (int i = 0; i < count; i++) {
                            sum += stack.pop();
                        }
                        stack.push(sum);
                        break;
                    }
                    case "avg": {
                        op1 = stack.pop();
                        int count = (int) op1;
                        double sum = 0;
                        for (int i = 0; i < count; i++) {
                            sum += stack.pop();
                        }
                        stack.push(sum / count);
                        break;
                    }
                    default: {
                        log.debug(exp + " not exp");
                    }
                }
            }
            log.debug("after current: " + exp + ", stack: " + stack);
        }
        return stack.peek();
    }

    public List<String> convertToRPN(List<String> expr) {
        List<String> res = new LinkedList<>();
        Stack<String> stack = new Stack<>();
        int counter = 0;
        for (String symbol : expr) {
            if ("(".equals(symbol)) {
                stack.push(symbol);
                continue;
            } else if (")".equals(symbol)) {
                while (!"(".equals(stack.peek()))
                    res.add(stack.pop());
                stack.pop();
                continue;
            } else if ("[".equals(symbol)) {
                stack.push(Integer.toString(counter));
                counter = 0;
                stack.push(symbol);
                continue;
            } else if (",".equals(symbol)) {
                counter++;
                while (!"[".equals(stack.peek())) {
                    res.add(stack.pop());
                }
                continue;
            } else if ("]".equals(symbol)) {
                counter++;
                while (!"[".equals(stack.peek())) {
                    res.add(stack.pop());
                }
                res.add(Integer.toString(counter));
//                res.add(symbol);
                stack.pop();
                String lastStatement = stack.pop();
                counter = Integer.parseInt(lastStatement);
                continue;
            } else if (precedence.containsKey(symbol)) {
                while (!stack.empty() && precedence.get(symbol) <= precedence.get(stack.peek())) {
                    res.add(stack.pop());
                }
                stack.push(symbol);
                continue;
            }
            res.add(symbol);
        }
        while (!stack.isEmpty()) {
            res.add(stack.pop());
        }
        return res;
    }

    /**
     * split string into expressions
     */
    public static List<String> splitLine(String line) {
        Pattern pattern = Pattern.compile(EXPRESSION);
        Matcher match = pattern.matcher(line);
        List<String> result = new ArrayList<>();
        while (match.find()) {
            result.add(match.group());
        }
        return result;
    }

    /**
     * converted list to string to avoid [] and ,
     */
    public String listToString(List<String> elements) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String elem : elements) {
            stringBuilder.append(elem).append(" ");
        }
        return stringBuilder.toString();
    }

    /**
     * check if string is a number for math operations
     */
    private boolean isNumber(String num) {
        return num.matches("-?[0-9.]+");
    }

}
