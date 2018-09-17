import java.util.*;

public class EliminateEpsRules {

    private static Map<String, List<String>> map;
    private static List<String> epsContainer;
    private static List<String> listDiveInto;

    public static void main(String[] args) {

        map = new LinkedHashMap<>();
        epsContainer = new ArrayList<>();

        map.put("S", new ArrayList<>(Arrays.asList("ABCdFF")));
        map.put("A", new ArrayList<>(Arrays.asList("A", "eps")));
        map.put("B", new ArrayList<>(Arrays.asList("AC")));
        map.put("C", new ArrayList<>(Arrays.asList("C", "eps")));
        map.put("F", new ArrayList<>(Arrays.asList("eps")));

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
            //по каждому правилу running
            for (String rule : entry.getValue()) {
                List<String> epsList = new ArrayList<>();
                for (String eps : epsContainer) {
                    if (rule.contains(eps)) {
                        epsList.add(eps);
                    }
                }
                provideEpsRule(entry.getKey(), rule, epsList);
            }
        }
    }

    private static void provideEpsRule(String key, String rule, List<String> epsList) {
        char[] arrayOfNeTerminals = rule.toCharArray();
        List<String> listWithoutEps = new ArrayList<>(map.get(key));
        int counter = 0; // counter eps char
        for (char arrayOfNeTerminal : arrayOfNeTerminals) {
            if (String.valueOf(arrayOfNeTerminal).equals(String.valueOf(arrayOfNeTerminal).toUpperCase())) {
                StringBuilder withoutEps = new StringBuilder(rule);
                for (String eps : epsList) {
                    if (arrayOfNeTerminal == eps.charAt(0)) {
                        counter++;
                        withoutEps.deleteCharAt(withoutEps.indexOf(eps));
                        listWithoutEps.add(withoutEps.toString());
                    }
                }
            }
        }
        if (counter > 1) {
            for (char arrayOfNeTerminal : arrayOfNeTerminals) {
                StringBuilder builder = new StringBuilder(rule);
                if (String.valueOf(arrayOfNeTerminal).equals(String.valueOf(arrayOfNeTerminal).toUpperCase())) {
                    for (String eps : epsList) {
                        if (eps.contains(String.valueOf(arrayOfNeTerminal))) {
                            builder.deleteCharAt(builder.indexOf(String.valueOf(arrayOfNeTerminal)));
                        }
                    }
                }
                listWithoutEps.add(builder.toString());
            }
        }
        listDiveInto = new ArrayList();
        diveIntoRule(new StringBuilder(rule));
        listWithoutEps.addAll(listDiveInto);
        Set<String> clearing = new LinkedHashSet<>(listWithoutEps);
        listWithoutEps.clear();
        listWithoutEps.addAll(clearing);
        map.put(key, listWithoutEps);
    }


    private static void diveIntoRule(StringBuilder rule) {
        for (char letter : rule.toString().toCharArray()) {
            if (epsContainer.contains(String.valueOf(letter))) {
                String eps = epsContainer.get(epsContainer.indexOf(String.valueOf(letter)));
                if (rule.toString().contains(eps)) {
                    rule.deleteCharAt(rule.indexOf(String.valueOf(eps)));
                    listDiveInto.add(rule.toString());
                    diveIntoRule(rule);
                }
            }
        }
    }
}
