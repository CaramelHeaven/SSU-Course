import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class RemovalOfChainRules {

    private static Map<String, List<String>> map;
    private static List<String> helper;
    private static List<String> futureUselessNeTerminals;

    public static void main(String[] args) {
        map = new LinkedHashMap<>();
        helper = new ArrayList<>();
        futureUselessNeTerminals = new ArrayList<>();

        System.out.println("Enter grammar, please: ");
        boolean checking = true;
        while (checking) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String input = "";
            try {
                input = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (input.equals("end")) {
                checking = false;
            } else {
                pullingMap(input);
            }
        }

        System.out.println("Entered grammar");
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        provideChainRules(map);

        for (String key : futureUselessNeTerminals) {
            map.remove(key);
        }
        map.forEach((s, strings) -> System.out.println("" + s + " -> " + strings));
    }

    private static void provideChainRules(Map<String, List<String>> grammar) {
        for (Map.Entry<String, List<String>> entry : grammar.entrySet()) {
            helper.clear();
            for (String rule : entry.getValue()) {
                if ((rule.length() == 1) && rule.equals(rule.toUpperCase())) {
                    futureUselessNeTerminals.add(rule);
                    chainRules(rule, entry.getKey());
                }
            }
            List<String> temp = clearningList(entry.getValue());
            map.put(entry.getKey(), temp);
        }
    }

    private static void chainRules(String rule, String rootKey) {
        List<String> rules = map.get(rule);
        for (String temp : rules) {
            if ((temp.length() == 1) && temp.equals(temp.toUpperCase())) {
                chainRules(temp, rule);
            } else {
                helper.add(temp);
            }
        }
    }

    private static List<String> clearningList(List<String> oldEntry) {
        List<String> array = new ArrayList<>(oldEntry);
        List<String> oneLetters = new ArrayList<>();
        for (String anArray : array) {
            if ((anArray.length() == 1) && anArray.equals(anArray.toUpperCase())) {
                oneLetters.add(anArray);
            }
        }
        array.removeAll(oneLetters);
        array.addAll(helper);
        Set<String> unique = new LinkedHashSet<>(array);
        array.clear();
        array.addAll(unique);
        return array;
    }

    private static void pullingMap(String userInput) {
        userInput = userInput.replaceAll("\\s+", "");
        String[] parts = userInput.split("->");
        String key = String.valueOf(parts[0].charAt(0));

        String[] values = parts[1].split("\\|");
        map.put(key, new ArrayList<>(Arrays.asList(values)));
    }
}
