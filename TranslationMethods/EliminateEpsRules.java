import java.util.*;

public class EliminateEpsRules {

    private static Map<String, List<String>> map;
    private static List<String> epsContainer;

    public static void main(String[] args) {

        map = new LinkedHashMap<>();
        epsContainer = new ArrayList<>();

        map.put("S", new ArrayList<>(Arrays.asList("ABCd")));
        map.put("A", new ArrayList<>(Arrays.asList("A", "eps")));
        map.put("B", new ArrayList<>(Arrays.asList("AC")));
        map.put("C", new ArrayList<>(Arrays.asList("C", "eps")));

        provideEliminate(map);

        System.out.println(epsContainer);

        System.out.println("Deleted eps");
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    private static void provideEliminate(Map<String, List<String>> map) {
        //get eps rules
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            if (entry.getValue().contains("eps")) {
                System.out.println("contains: " + entry.getValue());
                epsContainer.add(entry.getKey());
            }
        }

        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            for (String rule : entry.getValue()) {
                for (String eps : epsContainer) {
                    if (rule.contains(eps)) {
                        addedRule(entry.getKey(), rule, eps);
                        clearingBaseRule(entry.getKey(), rule, epsContainer);
                    }
                }
            }
        }
    }

    private static void addedRule(String key, String rule, String eps) {
        List<String> rules = new ArrayList<>(map.get(key));
        char[] arrayOfString = rule.toCharArray();
        for (int i = 0; i < arrayOfString.length; i++) {
            if (arrayOfString[i] == eps.charAt(0)) {
                String newRule = rule;
                newRule = newRule.replace(String.valueOf(arrayOfString[i]), "");
                if (rules.contains("eps")) {
                    rules.remove("eps");
                }
                if (newRule.length() != 0) {
                    rules.add(newRule);
                }
                map.put(key, rules);
            }
        }
    }

    private static void clearingBaseRule(String key, String rule, List<String> epsList) {
        List<String> commonRules = map.get(key);
        for (String eps : epsList) {
            rule = rule.replace(eps, "");
        }
        if (rule.length() != 0) {
            commonRules.add(rule);
        }
        Set<String> unique = new LinkedHashSet<>(commonRules);
        commonRules.clear();
        commonRules.addAll(unique);
        map.put(key, commonRules);
    }
}
