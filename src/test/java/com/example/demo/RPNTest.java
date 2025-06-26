package com.example.demo;

import com.example.demo.utils.RPNCalculator;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RPNTest {

    private static final Logger log = LoggerFactory.getLogger(RPNTest.class);

    @Test
    @Disabled
    public void test() {
        RPNCalculator calculator = new RPNCalculator();

        String str1 = "min[2,3,4,5]+(3+(5+6))";
        List<String> expr1 = RPNCalculator.splitLine(str1);
        List<String> res1 = calculator.convertToRPN(expr1);
        System.out.println("Input1: " + str1);
        System.out.println("RPN: " + calculator.listToString(res1));
        System.out.println("Result: " + calculator.RPNCalculate(res1));
        Assert.isTrue(calculator.RPNCalculate(res1) == 16, "计算错误");
        System.out.println();

        String str2 = "2+max[2,3,4,5]*5+3";
        List<String> expr2 = RPNCalculator.splitLine(str2);
        List<String> res2 = calculator.convertToRPN(expr2);
        System.out.println("Input2: " + str2);
        System.out.println("RPN: " + calculator.listToString(res2));
        System.out.println("Result: " + calculator.RPNCalculate(res2));
        Assert.isTrue(calculator.RPNCalculate(res2) == 30, "计算错误");
        System.out.println();

        String str3 = "sum[sum[2,max[2,3,4,5]*5],3]";
        List<String> expr3 = RPNCalculator.splitLine(str3);
        List<String> res3 = calculator.convertToRPN(expr3);
        System.out.println("Input3: " + str3);
        System.out.println("RPN: " + calculator.listToString(res3));
        System.out.println("Result: " + calculator.RPNCalculate(res3));
        Assert.isTrue(calculator.RPNCalculate(res3) == 30, "计算错误");
        System.out.println();

        String str4 = "50/(2*5)+sin(45+45)";
        List<String> expr4 = RPNCalculator.splitLine(str4);
        List<String> res4 = calculator.convertToRPN(expr4);
        System.out.println("Input: " + str4);
        System.out.println("RPN: " + calculator.listToString(res4));
        System.out.println("Result: " + calculator.RPNCalculate(res4));
        Assert.isTrue(calculator.RPNCalculate(res4) == 6, "计算错误");
        System.out.println();

        String str5 = "12+2*((3*4)+(10/5))";
        List<String> expr5 = RPNCalculator.splitLine(str5);
        List<String> res5 = calculator.convertToRPN(expr5);
        System.out.println("Input: " + str5);
        System.out.println("RPN: " + calculator.listToString(res5));
        System.out.println("Result: " + calculator.RPNCalculate(res5));
        Assert.isTrue(calculator.RPNCalculate(res5) == 40, "计算错误");
        System.out.println();

    }

    @Test
    @Disabled
    public void patternTest() {
        String line = "abcdef|123+abcd|123456+3+min[2,3,14,15]+(3+(5+6))";
        String patternExpression = "([a-z]{4}\\|+\\d{6})|-?[0-9.]+|[A-Za-z]+|[-+*/()\\[\\]^=,]";
        Pattern pattern = Pattern.compile(patternExpression);
        Matcher matcher = pattern.matcher(line);
        System.out.println(line);
        while (matcher.find()) {
            System.out.print(matcher.group() + "  ");
        }
        System.out.println();

    }
}
