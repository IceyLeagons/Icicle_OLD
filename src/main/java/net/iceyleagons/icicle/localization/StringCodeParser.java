package net.iceyleagons.icicle.localization;

import lombok.Getter;
import net.iceyleagons.icicle.utils.Checkers;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Original code from https://github.com/honsesco/StringParser
 * Greatly modified by TOTHTOMI
 *
 * @author TOTHTOMI, Honsesco
 * @version 1.0.0
 * @since 1.0.0
 */
public class StringCodeParser {

    private final HashSet<String> functions = new HashSet<>(Arrays.asList(
            "EQ", "LTEQ", "LT", "GT", "GTEQ", "IF", "CONCAT",
            "ADD", "AND", "NOT", "OR", "ENDSWITH", "STARTSWITH", "NE", "TRUE", "FALSE", "DIV", "JOIN", "MUL",
            "MOD", "SUB"));

    @Getter
    private final Map<String, String> values = new HashMap<>();

    private String getIfExists(String key) {
        return values.getOrDefault(key, key);
    }

    private boolean isBoolean(String str) {
        return str.equals("true") || str.equals("false");
    }

    private int parseInt(String str) {
        return Integer.parseInt(str);
    }

    private boolean isInteger(String str) {
        return Checkers.isInteger(str);
    }

    private boolean isString(String str) {
        int a = str.indexOf("'");
        int b = str.lastIndexOf("'");

        return a != -1 && b != -1 && a < b;
    }

    private String getStringContent(String str) {
        int a = str.indexOf("'");
        int b = str.lastIndexOf("'");

        return a != -1 && b != -1 && a < b ? str.substring(a + 1, b) : str;
    }

    public String parseCode(String input) {
        if (hasCode(input)) {
            StringBuilder result = new StringBuilder();
            int current = input.indexOf("{");
            if (current > 0) result.append(input, 0, current);

            while (hasCode(input, current)) {
                String code = getFunctionBody(input, current);
                if (code.equals("error")) return "error";
                String codeContent = getCodeContent(code);
                String parsedCode = parseFunction(codeContent);
                if (parsedCode.equals("error")) return "error";

                if (codeContent.equals(parsedCode)) {
                    result.append(code);
                } else {
                    result.append(parsedCode);
                }

                current += code.length();

                int indexOfOpenedBrace = input.indexOf("{", current);
                if (indexOfOpenedBrace == -1) break;

                result.append(input, current, indexOfOpenedBrace);

                current = indexOfOpenedBrace;
            }

            if (current >= 0 && current < input.length()) result.append(input.substring(current));

            return result.toString();
        } else {
            return "error";
        }
    }

    private String getFunctionContent(String input) {
        int a = input.indexOf("(");
        int b = input.lastIndexOf(")");

        return a == -1 || b == -1 ? input : input.substring(a + 1, b);
    }

    private String getCodeContent(String input) {
        int a = input.indexOf("{");
        int b = input.lastIndexOf("}");

        return a == -1 || b == -1 ? input : input.substring(a + 1, b);
    }

    private String getFunctionName(String input) {
        int a = input.indexOf("(");

        return a == -1 ? "" : input.substring(0, a).trim();
    }

    private boolean isFunction(String function) {
        int a = function.indexOf("(");
        int b = function.indexOf(")");

        return a != -1 && b != -1 && a < b && functions.contains(function.substring(0, a).trim());
    }

    private boolean hasCode(String input) {
        return hasCode(input, 0);
    }

    private boolean hasCode(String input, int start) {
        int a = input.indexOf("{", start);
        int b = input.lastIndexOf("}");

        return start >= 0 && a != -1 && b != -1 && a < b;
    }

    private String handleSimpleList(String input, SizeFilter sizeFilter, ReturnValueSupplier returnValueSupplier) {
        List<String> list = parseSimpleList(getFunctionContent(input));

        if (sizeFilter.isAllowed(list.size())) return "error";

        String sValue1 = getIfExists(list.get(0));
        String sValue2 = getIfExists(list.get(1));

        return returnValueSupplier.get(sValue1, sValue2);
    }

    private String parseFunction(String input) {
        if (isFunction(input)) {
            String name = getFunctionName(input);

            switch (name) {
                case "IF": {
                    List<String> list = parseComplexList(getFunctionContent(input));
                    if (list.size() != 3) return "error";

                    String condition = parseFunction(list.get(0));
                    return condition.equals("true") ? parseFunction(list.get(1)) : condition.equals("false") ? parseFunction(list.get(2)) : "error";
                }

                case "EQ": {
                    return handleSimpleList(input, size -> size != 2, (value1, value2) -> String.valueOf(value1.equals(value2)));
                }

                case "NE": {
                    return handleSimpleList(input, size -> size != 2, (value1, value2) -> String.valueOf(!value1.equals(value2)));
                }

                case "LTEQ": {
                    return handleSimpleList(input, size -> size != 2, (value1, value2) -> {
                        if (!isInteger(value1) || !isInteger(value2)) return "error";
                        return String.valueOf(parseInt(value1) <= parseInt(value2));
                    });
                }

                case "LT": {
                    return handleSimpleList(input, size -> size != 2, (value1, value2) -> {
                        if (!isInteger(value1) || !isInteger(value2)) return "error";
                        return String.valueOf(parseInt(value1) < parseInt(value2));
                    });
                }

                case "GTEQ": {
                    return handleSimpleList(input, size -> size != 2, (value1, value2) -> {
                        if (!isInteger(value1) || !isInteger(value2)) return "error";
                        return String.valueOf(parseInt(value1) >= parseInt(value2));
                    });
                }

                case "GT": {
                    return handleSimpleList(input, size -> size != 2, (value1, value2) -> {
                        if (!isInteger(value1) || !isInteger(value2)) return "error";
                        return String.valueOf(parseInt(value1) > parseInt(value2));
                    });

                }

                case "CONCAT": {
                    List<String> list = parseComplexList(getFunctionContent(input));
                    StringBuilder sb = new StringBuilder();
                    for (String str : list) {
                        String parsed = parseFunction(str);
                        if (parsed.equals("error")) return "error";
                        sb.append(parsed);
                    }
                    return sb.toString();
                }

                case "ADD": {
                    return handleSimpleList(input, size -> size != 2, (value1, value2) -> {
                        if (!isInteger(value1) || !isInteger(value2)) return "error";
                        return String.valueOf(parseInt(value1) + parseInt(value2));
                    });
                }

                case "AND": {
                    List<String> list = parseComplexList(getFunctionContent(input));

                    for (String str : list) {
                        if (isBoolean(str)) return "error";
                        String parsed = parseFunction(str);

                        if (parsed.equals("false")) return "false";
                        else if (!parsed.equals("true")) return "error";
                    }

                    return "true";
                }

                case "NOT": {
                    String functionContent = getFunctionContent(input);
                    if (isBoolean(functionContent)) return "error";
                    String parsed = parseFunction(functionContent);

                    return isBoolean(parsed) ? String.valueOf(!Boolean.parseBoolean(parsed)) : "error";
                }

                case "OR": {
                    List<String> list = parseComplexList(getFunctionContent(input));
                    if (list.size() == 0) return "error";

                    for (String str : list) {
                        if (isBoolean(str)) return "error";

                        String parsed = parseFunction(str);
                        if (parsed.equals("true")) return "true";
                        else if (!parsed.equals("false")) return "error";
                    }
                    return "false";
                }

                case "ENDSWITH": {
                    List<String> list = parseSimpleList(getFunctionContent(input));

                    if (list.size() <= 1) return "error";
                    String value = parseFunction(list.get(0));

                    for (int i = 1; i < list.size(); i++) {
                        if (value.endsWith(parseFunction(list.get(i)))) return "true";
                    }

                    return "false";
                }

                case "STARTSWITH": {
                    List<String> list = parseSimpleList(getFunctionContent(input));

                    if (list.size() <= 1) return "error";
                    String value = parseFunction(list.get(0));
                    for (int i = 1; i < list.size(); i++) {
                        if (value.startsWith(parseFunction(list.get(i)))) return "true";
                    }

                    return "false";
                }

                case "DIV": {
                    return handleSimpleList(input, size -> size != 2, (value1, value2) -> {
                        if (!isInteger(value1) || !isInteger(value2)) return "error";
                        return String.valueOf(parseInt(value1) / parseInt(value2));
                    });
                }

                case "JOIN": {
                    List<String> list = parseComplexList(getFunctionContent(input));
                    if (list.size() < 2) return "error";

                    String value = parseFunction(list.get(0));
                    List<String> parsed = new ArrayList<>();

                    for (int i = 1; i < list.size(); i++) {
                        parsed.add(parseFunction(list.get(i)));
                    }

                    return String.join(value, parsed);
                }

                case "MUL": {
                    return handleSimpleList(input, size -> size != 2, (value1, value2) -> {
                        if (!isInteger(value1) || !isInteger(value2)) return "error";
                        return String.valueOf(parseInt(value1) * parseInt(value2));
                    });
                }

                case "MOD": {
                    return handleSimpleList(input, size -> size != 2, (value1, value2) -> {
                        if (!isInteger(value1) || !isInteger(value2)) return "error";
                        return String.valueOf(parseInt(value1) % parseInt(value2));
                    });
                }

                case "SUB": {
                    return handleSimpleList(input, size -> size != 2, (value1, value2) -> {
                        if (!isInteger(value1) || !isInteger(value2)) return "error";
                        return String.valueOf(parseInt(value1) - parseInt(value2));
                    });
                }

                case "TRUE":
                    return "true";

                case "FALSE":
                    return "false";
            }

            return "error";
        } else {
            if (values.containsKey(input)) return values.get(input);
            if (hasCode(input)) input = parseCode(input);
            return isString(input) ? getStringContent(input) : input;
        }
    }

    private String getFunctionBody(String input, int start) {
        int a = 0;    // '
        int ba = 0;   // {
        int bb = 0;   // }
        int ca = 0;   // (
        int cb = 0;   // )

        int current = start;

        while (input.length() > current) {
            char symbol = input.charAt(current);

            // for special chars
            if (symbol == '\\') {
                if (current + 1 == input.length()) return "error";

                char specialChar = input.charAt(current + 1);
                if (!isSpecialChar(specialChar)) current--;

                current += 2;
                continue;
            }

            if (symbol == '\'') a++;
            else if (symbol == '{') ba++;
            else if (symbol == '}') bb++;
            else if (symbol == '(') ca++;
            else if (symbol == ')') cb++;
            else if (symbol == ',' && a >= 0 && ba >= 0 && ca >= 0 && a % 2 == 0 && ba == bb && ca == cb) return input.substring(start, current);
            else {
                current++;
                continue;
            }

            if (a >= 0 && ba >= 0 && ca >= 0 && a % 2 == 0 && ba == bb && ca == cb) return input.substring(start, current + 1);

            current++;
        }
        if (a >= 0 && ba >= 0 && ca >= 0 && a % 2 == 0 && ba == bb && ca == cb) return input.substring(start);

        return "error";
    }

    private boolean isSpecialChar(char symbol) {
        return symbol == '\'' || symbol == '(' || symbol == ')' || symbol == '{' || symbol == '}';
    }

    private List<String> parseSimpleList(String input) {
        return Arrays.stream(input.split(",")).map(String::trim).collect(Collectors.toList());
    }

    private List<String> parseComplexList(String input) {
        int start = 0;
        List<String> list = new ArrayList<>();

        while (input.indexOf(",", start) != -1) {
            String body = getFunctionBody(input, start);
            if (body.equals("error")) return Collections.emptyList();

            int startsAt = input.indexOf(body, start);
            int comma = input.indexOf(",", startsAt + body.length());
            if (comma == -1) break;

            start = comma + 1;
            list.add(body.trim());
        }

        String lastElement = getFunctionBody(input, start);
        if (lastElement.equals("error")) return Collections.emptyList();

        list.add(lastElement.trim());
        return list;
    }

    public interface ReturnValueSupplier {
        String get(String value1, String value2);
    }

    public interface SizeFilter {
        boolean isAllowed(int size);
    }
}

