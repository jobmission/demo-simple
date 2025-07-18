package calculate;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormulaParser {
    PTagDAO pTagDAO;
    VTagDAO vtagDao;
    PTagValueDAO pTagValueDAO;
    VTagValueDAO vTagValueDAO;

    public FormulaParser(PTagDAO pTagDAO,
                         VTagDAO vtagDao,
                         PTagValueDAO pTagValueDAO,
                         VTagValueDAO vTagValueDAO) {
        this.pTagDAO = pTagDAO;
        this.vtagDao = vtagDao;
        this.pTagValueDAO = pTagValueDAO;
        this.vTagValueDAO = vTagValueDAO;
    }

    public boolean checkVTagFormula(String formula) throws SQLException {
        Map<Integer, Integer> tempValuePTag = new HashMap<>();
        Map<Integer, Integer> tempValueVTag = new HashMap<>();
        int counter = 10000;
        formula = formula.replaceAll("\\s+", ""); // 移除所有空格

        // 解析公式中的vtag引用
        Pattern vtagPattern = Pattern.compile("vtag\\|(\\d+)", Pattern.CASE_INSENSITIVE);
        Matcher vtagMatcher = vtagPattern.matcher(formula);

        while (vtagMatcher.find()) {
            int refVTagId = Integer.parseInt(vtagMatcher.group(1));
            if (!vtagDao.exists(refVTagId)) {
                throw new RuntimeException("引用的vtag|" + refVTagId + "不存在");
            }
            int refValue = tempValueVTag.getOrDefault(refVTagId, ++counter);
            formula = formula.replaceAll("vtag\\|" + refVTagId, String.valueOf(refValue));
        }

        // 解析公式中的ptag引用
        Pattern ptagPattern = Pattern.compile("ptag\\|(\\d+)", Pattern.CASE_INSENSITIVE);
        Matcher ptagMatcher = ptagPattern.matcher(formula);

        while (ptagMatcher.find()) {
            int ptagId = Integer.parseInt(ptagMatcher.group(1));
            if (!pTagDAO.exists(ptagId)) {
                throw new RuntimeException("引用的ptag|" + ptagId + "不存在");
            }
            Integer ptagValue = tempValuePTag.getOrDefault(ptagId, ++counter);
            formula = formula.replaceAll("ptag\\|" + ptagId, String.valueOf(ptagValue));
        }
        // 计算表达式
        evaluateExpression(formula).getFirst();
        return true;
    }

    public Double calculateVTag(VTag vtag, Timestamp tagTime) throws Exception {
        String formula = vtag.getFormula();
        formula = formula.replaceAll("\\s+", ""); // 移除所有空格

        // 解析公式中的vtag引用
        Pattern vtagPattern = Pattern.compile("vtag\\|(\\d+)", Pattern.CASE_INSENSITIVE);
        Matcher vtagMatcher = vtagPattern.matcher(formula);

        while (vtagMatcher.find()) {
            int refVTagId = Integer.parseInt(vtagMatcher.group(1));
            TagData tagData = vTagValueDAO.getTagValue(refVTagId, tagTime);
            if (tagData == null) {
                throw new Exception("VTag value not found for id: " + refVTagId + ", formula: " + vtag.getFormula());
            }
            double refValue = tagData.getTagValue();
            formula = formula.replaceAll("vtag\\|" + refVTagId, String.valueOf(refValue));
        }

        // 解析公式中的ptag引用
        Pattern ptagPattern = Pattern.compile("ptag\\|(\\d+)", Pattern.CASE_INSENSITIVE);
        Matcher ptagMatcher = ptagPattern.matcher(formula);

        while (ptagMatcher.find()) {
            int ptagId = Integer.parseInt(ptagMatcher.group(1));
            TagData tagData = pTagValueDAO.getTagValue(ptagId, tagTime);
            Double ptagValue = null;
            if (tagData != null) {
                ptagValue = tagData.getTagValue();
            }
            formula = formula.replaceAll("ptag\\|" + ptagId, String.valueOf(ptagValue));
        }
        // 计算表达式
        return evaluateExpression(formula).getFirst();
    }

    private List<Double> evaluateExpression(String expression) {
        // 使用双栈法计算表达式
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                StringBuilder numStr = new StringBuilder();
                while (i < expression.length() &&
                    (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    numStr.append(expression.charAt(i++));
                }
                i--;
                numbers.push(Double.parseDouble(numStr.toString()));
            } else if (c == '(') {
                operators.push(c);
            } else if (c == ')') {
                while (operators.peek() != '(') {
                    numbers.push(applyOperator(operators.pop(), numbers.pop(), numbers.pop()));
                }
                operators.pop();
            } else if (isOperator(c)) {
                while (!operators.isEmpty() && hasPrecedence(c, operators.peek())) {
                    numbers.push(applyOperator(operators.pop(), numbers.pop(), numbers.pop()));
                }
                operators.push(c);
            } else if (Character.isLetter(c)) {
                // 处理函数调用
                StringBuilder funcName = new StringBuilder();
                while (i < expression.length() && Character.isLetter(expression.charAt(i))) {
                    funcName.append(expression.charAt(i++));
                }
                if ("null".equalsIgnoreCase(funcName.toString())) {
                    numbers.push(null);
                } else {
                    if (expression.charAt(i) != '(') {
                        throw new IllegalArgumentException("Invalid function call [" + expression + "] at position " + i);
                    }
                }

                // 跳过'('
                i++;

                // 提取参数
                StringBuilder argStr = new StringBuilder();
                int parenCount = 1;
                while (i < expression.length() && parenCount > 0) {
                    char ch = expression.charAt(i);
                    if (ch == '(') parenCount++;
                    if (ch == ')') parenCount--;
                    if (parenCount > 0) {
                        argStr.append(ch);
                    }
                    i++;
                }
                i--;

                List<Double> argList = evaluateExpression(argStr.toString());
                if (!argList.isEmpty() && "null".equalsIgnoreCase(funcName.toString())) {
                    for (Double v : argList) {
                        numbers.push(v);
                    }
                } else {
                    Double funcResult = applyFunction(funcName.toString(), argList);
                    numbers.push(funcResult);
                }

            }
        }

        while (!operators.isEmpty()) {
            numbers.push(applyOperator(operators.pop(), numbers.pop(), numbers.pop()));
        }

        List<Double> result = new ArrayList<>();
        while (!numbers.isEmpty()) {
            result.add(numbers.pop());
        }
        return result;
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return false;
        }
        return (op1 != '*' && op1 != '/') || (op2 != '+' && op2 != '-');
    }

    private Double applyOperator(char op, Double b, Double a) {
        return switch (op) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> {
                if (b == 0) throw new ArithmeticException("Division by zero");
                yield a / b;
            }
            default -> 0D;
        };
    }

    private Double applyFunction(String funcName, List<Double> argList) {
        switch (funcName.toLowerCase()) {
            case "null":
                return null;
            case "abs":
                return Math.abs(argList.getFirst());
            case "sqrt":
                return Math.sqrt(argList.getFirst());
            case "sin":
                return Math.sin(argList.getFirst());
            case "cos":
                return Math.cos(argList.getFirst());
            case "tan":
                return Math.tan(argList.getFirst());
            case "log":
                return Math.log(argList.getFirst());
            case "exp":
                return Math.exp(argList.getFirst());
            case "ceil":
                return Math.ceil(argList.getFirst());
            case "floor":
                return Math.floor(argList.getFirst());
            case "round":
                return Math.round(argList.getFirst()) * 1.0D;
            case "max":
                if (argList == null || argList.size() < 2) {
                    throw new IllegalArgumentException("max function requires at least 2 arguments");
                }
                return argList.stream().max(Double::compareTo)
                    .orElseThrow(() -> new IllegalArgumentException("Max function requires at least one argument"));
            case "min":
                if (argList == null || argList.size() < 2) {
                    throw new IllegalArgumentException("max function requires at least 2 arguments");
                }
                return argList.stream().min(Double::compareTo)
                    .orElseThrow(() -> new IllegalArgumentException("Min function requires at least one argument"));
            case "ifnull":
                if (argList == null || argList.size() != 2) {
                    throw new IllegalArgumentException("ifnull function requires exactly 2 arguments: value, defaultValue, condition");
                }
                return argList.get(0) != null ? argList.get(0) : argList.get(1);
            case "rvsd": //原始值按步长求差值，如求5点的15分步长差值，5:15的值减去5:00的值
                if (argList == null || argList.size() != 3) {
                    throw new IllegalArgumentException("rvsd function requires exactly 3 arguments: tagId, step, timestamp");
                }
                int tagId = argList.get(0).intValue();
                int step = argList.get(1).intValue();
                int timestamp = argList.get(2).intValue();

                return null; // 这里需要实现具体的逻辑来计算步长差值
            default:
                throw new IllegalArgumentException("Unknown function: " + funcName);
        }
    }
}

