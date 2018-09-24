import java.util.*;

public class ChomskyNormal {

    private static Map<String, List<String>> map;
    private static List<String> listOfRules;
    private static Map<String, List<String>> testingMap;

    public static void main(String[] args) {

        map = new LinkedHashMap<>();

        map.put("S", new ArrayList<>(Arrays.asList("AaBCDb", "BCab")));
        map.put("A", new ArrayList<>(Arrays.asList("aABBB", "Daaba")));
        map.put("C", new ArrayList<>(Arrays.asList("aC", "baa")));
        map.put("D", new ArrayList<>(Arrays.asList("AD", "aaDBC", "aaa")));

        provideChomsky(map);
    }

    private static void provideChomsky(Map<String, List<String>> mapGrammar) {
        testingMap = new HashMap<>();

        System.out.println("Введенная грамматика: ");
        map.forEach((s, strings) -> {
            System.out.println("" + s + " -> " + strings);
        });

        for (Map.Entry<String, List<String>> entry : mapGrammar.entrySet()) {
            for (String rule : entry.getValue()) {
                listOfRules = new ArrayList<>();
                char[] letters = rule.toCharArray();
                diveIntoRules(entry.getKey(), letters);
            }
        }
        System.out.println();
        System.out.println("Преобразованная грамматика: ");

        testingMap.forEach((s, strings) -> {
            System.out.println("" + s + " -> " + strings);
        });
    }

    private static void diveIntoRules(String key, char[] ruleChars) {
        switch (ruleChars.length) {
            case 1:
                if (String.valueOf(ruleChars[0]).equals(String.valueOf(ruleChars[0]).toLowerCase())) {
                    List<String> array = new ArrayList<>();
                    if (testingMap.containsKey(key)) {
                        array.addAll(testingMap.get(key));
                    }
                    array.add(new String(ruleChars));
                    testingMap.put(key, array);
                }
                break;
            case 2:
                if (String.valueOf(ruleChars[0]).equals(String.valueOf(ruleChars[0]).toUpperCase())
                        && (String.valueOf(ruleChars[1]).equals(String.valueOf(ruleChars[1]).toUpperCase()))) {
                    List<String> array = new ArrayList<>();
                    if (testingMap.containsKey(key)) {
                        array.addAll(testingMap.get(key));
                    }
                    array.add(String.valueOf(ruleChars));
                    testingMap.put(key, array);
                } else {
                    extraChecking(key, ruleChars);
                }
                break;
            default:
                String safetyLetter = String.valueOf(ruleChars[0]);
                if (safetyLetter.equals(safetyLetter.toLowerCase())) {
                    safetyLetter = safetyLetter + "'";
                }
                char[] newArray = Arrays.copyOfRange(ruleChars, 1, ruleChars.length);
                if (newArray.length == 1) {
                    extraChecking(key, ruleChars);
                } else {
                    String newNeTerminal = "<" + String.valueOf(newArray) + ">";
                    String newRule = safetyLetter + newNeTerminal;
                    List<String> container = new ArrayList<>();
                    if (testingMap.containsKey(key)) {
                        container.addAll(testingMap.get(key));
                    }
                    container.add(newRule);
                    testingMap.put(key, container);
                    testingMap.put(newNeTerminal, new ArrayList<>());
                    diveIntoRules(newNeTerminal, newArray);
                }
        }
    }

    private static void extraChecking(String key, char[] rule) {
        if (String.valueOf(rule[0]).equals(String.valueOf(rule[0]).toUpperCase())
                && (String.valueOf(rule[1]).equals(String.valueOf(rule[1]).toUpperCase()))) {
            listOfRules.add(new String(rule));
            System.out.println("rule: " + Arrays.toString(rule));
        } else {
            switch (rule.length) {
                case 1:
                    if (String.valueOf(rule[0]).equals(String.valueOf(rule[0]).toLowerCase())) {
                        listOfRules.add(new String(rule));
                    }
                    System.out.println("rule: " + Arrays.toString(rule));
                    break;
                default:
                    if (rule[0] == rule[1] && String.valueOf(rule[0]).equals(String.valueOf(rule[0]).toLowerCase())) {
                        String symbol = "" + String.valueOf(rule[0]) + "'";
                        String doubleLetter = symbol + symbol;

                        List<String> container = new ArrayList<>();
                        if (testingMap.containsKey(key)) {
                            container.addAll(testingMap.get(key));
                        }
                        container.add(doubleLetter);
                        testingMap.put(key, container);

                        List<String> sy = new ArrayList<>();
                        sy.add(String.valueOf(rule[0]));
                        testingMap.put(symbol, sy);
                    } else {
                        List<String> container = new ArrayList<>();
                        if (testingMap.containsKey(key)) {
                            container.addAll(testingMap.get(key));
                        }
                        StringBuilder futureRule = new StringBuilder();
                        for (char aRule : rule) {
                            if (String.valueOf(aRule).equals(String.valueOf(aRule).toLowerCase())) {
                                String symbol = "" + aRule + "'";
                                futureRule.append(symbol);
                                if (!testingMap.containsKey(symbol)) {
                                    List<String> temp = new ArrayList<>(Arrays.asList(String.valueOf(aRule)));
                                    testingMap.put(symbol, temp);
                                }
                            } else {
                                futureRule.append(aRule);
                            }
                        }
                        container.add(futureRule.toString());
                        testingMap.put(key, container);
                    }
            }
        }
    }
}