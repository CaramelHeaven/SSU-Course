import java.util.*;

public class EliminateEpsRules {

    private static Map<String, List<String>> map;
    private static Set<String> epsContainer;
    private static final String E = "EPSILON";
    private static Set<String> combinedRule;

    public static void main(String[] args) {

        map = new LinkedHashMap<>();
        epsContainer = new LinkedHashSet<>();

        map.put("S", new ArrayList<>(Arrays.asList("AF")));
        map.put("A", new ArrayList<>(Arrays.asList("aA", "BB")));
        map.put("B", new ArrayList<>(Arrays.asList("CC", "aC", "B")));
        map.put("C", new ArrayList<>(Arrays.asList("aC", "b", E)));
        map.put("D", new ArrayList<>(Arrays.asList("ABC", "ACCBba")));

        grabAllEpsilons(map);

        provideEliminateEpsilons(map);

        map.forEach((s, strings) -> System.out.println("key: " + s + " values: " + strings));
    }

    private static void provideEliminateEpsilons(Map<String, List<String>> map) {
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            for (String rule : entry.getValue()) {
                combinedRule = new LinkedHashSet<>();
                permutationsRule(rule, new StringBuilder());
                Set<String> newRules = new LinkedHashSet<>(map.get(entry.getKey()));
                newRules.addAll(combinedRule);
                if (newRules.contains(E)) {
                    newRules.remove(E);
                }
                map.put(entry.getKey(), new ArrayList<>(newRules));
            }
        }
    }

    private static void permutationsRule(String rule, StringBuilder nonContainerEps) {
        StringBuilder containerEps = new StringBuilder();
        StringBuilder cache;
        if (nonContainerEps.length() == 0) {
            nonContainerEps = new StringBuilder();
        }

        for (char letter : rule.toCharArray()) {
            String temp = String.valueOf(letter);
            if (epsContainer.contains(temp)) {
                containerEps.append(temp);
            } else {
                if (temp.equals(temp.toLowerCase()))
                    nonContainerEps.append(temp);
            }
        }

        for (char letter : containerEps.toString().toCharArray()) {
            cache = new StringBuilder(containerEps);
            String temp = containerEps.deleteCharAt(containerEps.indexOf(String.valueOf(letter))).toString();
            if (temp.length() != 0) {
                combinedRule.add(temp + nonContainerEps);
            }
            if (containerEps.length() > 2) {
                permutationsRule(containerEps.toString(), nonContainerEps);
            }
            containerEps.setLength(0);
            containerEps.append(cache);
        }
        for (char character : containerEps.toString().toCharArray()) {
            combinedRule.add(nonContainerEps.toString() + String.valueOf(character));
            combinedRule.add(String.valueOf(character));
        }
    }


    private static void grabAllEpsilons(Map<String, List<String>> map) {
        if (epsContainer.size() == 0) {
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                for (String rule : entry.getValue()) {
                    if (rule.equals(E)) {
                        epsContainer.add(entry.getKey());
                    }
                }
            }
            grabAllEpsilons(map);
        } else {
            Set<String> futureContainer = new LinkedHashSet<>();
            StringBuilder pasteEps = new StringBuilder();
            for (String epMain : epsContainer) {
                pasteEps.append(epMain);
            }
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                for (String rule : entry.getValue()) {
                    if (!rule.equals(E) && pasteEps.length() > 0
                            && sameChars(pasteEps.toString(), rule) && !epsContainer.contains(entry.getKey())) {
                        futureContainer.add(entry.getKey());
                    }
                }
            }
            if (futureContainer.size() > 0) {
                epsContainer.addAll(futureContainer);
                grabAllEpsilons(map);
            }
        }
    }

    private static boolean sameChars(String pasteEps, String rule) {
        for (char letter : rule.toCharArray()) {
            if (pasteEps.contains(String.valueOf(letter))) {
                rule = rule.replace(String.valueOf(letter), "");
            }
        }
        return rule.length() == 0;
    }
}
