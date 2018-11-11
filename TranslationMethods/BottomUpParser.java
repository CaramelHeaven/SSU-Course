import java.util.*;

public class BottomUpParser {

    private static Map<String, List<String>> map;
    private static StringBuilder chainlet;
    private static List<String> container; // our container -> (q, 1, $, s)
    private static List<String> rulesContainer;

    private static String DLR = "$";
    private static int C = 0;
    private static String Q = "q";
    private static String B = "b";
    private static String chain = "s";
    private static boolean okNext = true;

    private static final int GET_CHAIN = 2;
    private static final int GET_HISTORY = 3;

    public static void main(String[] args) {
        map = new LinkedHashMap<>();
        chainlet = new StringBuilder("a*a");
        container = new ArrayList<>();
        rulesContainer = new ArrayList<>();

        container.add(Q);
        container.add(String.valueOf(C));
        container.add(DLR);
        container.add(chain);

        System.out.println(container);

        map.put("S", new ArrayList<>(Arrays.asList("T", "S+T")));
        map.put("T", new ArrayList<>(Arrays.asList("T*F", "F")));
        map.put("F", new ArrayList<>(Arrays.asList("a")));

        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            rulesContainer.addAll(entry.getValue());
        }

        provideBottomUpParser(map);
    }

    private static void provideBottomUpParser(Map<String, List<String>> map) {
        if (okNext) {
            okNext = false;
            String loadNextChar = String.valueOf(chainlet.charAt(0));

            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                for (String rule : entry.getValue()) {
                    if (rule.equals(String.valueOf(loadNextChar))) {
                        String key = entry.getKey();

                        String newChain = container.get(GET_CHAIN).replace(container.get(GET_CHAIN), container.get(GET_CHAIN) + key);
                        container.set(GET_CHAIN, newChain);

                        int position = 1 + rulesContainer.indexOf(rule);    // count by key + pos in the list
                        String newHistory = position + container.get(GET_HISTORY); // 5s
                        container.set(GET_HISTORY, newHistory);


                        chainlet.deleteCharAt(0);
                        provideBottomUpParser(map);

                        System.out.println("equals");
                    } else {
                        System.out.println("not equals");
                    }
                }
            }

            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                for (String rule : entry.getValue()) {
                    String cleanUp = container.get(GET_CHAIN).substring(1, container.get(GET_CHAIN).length());

                    if (cleanUp.equals(rule)) {
                        int position = 1 + rulesContainer.indexOf(rule);    // count by key + pos in the list
                        String newHistory = position + container.get(GET_HISTORY); // ...s
                        container.set(GET_HISTORY, newHistory);

                        provideBottomUpParser(map);
                    }
                }
            }
        }
    }
}
