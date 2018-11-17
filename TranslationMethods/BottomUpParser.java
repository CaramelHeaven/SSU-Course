import java.util.*;

public class BottomUpParser {

    private static Map<String, List<String>> map;
    private static StringBuilder chainlet;
    private static StringBuilder cacheChainlet;
    private static List<String> container; // our container -> (q, 1, $, s)
    private static List<String> rulesContainer;

    private static String DLR = "$";
    private static int C = 1;
    private static String Q = "q";
    private static String B = "b";
    private static String chain = "e";

    private static boolean DIRECTION = true; // true = q, false - back
    private static boolean okNext = true;

    private static final int GET_STATUS = 0;
    private static final int GET_COUNTER = 1;
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

        map.put("S", new ArrayList<>(Arrays.asList("S+T", "T")));
        map.put("T", new ArrayList<>(Arrays.asList("T*F", "F")));
        map.put("F", new ArrayList<>(Arrays.asList("a")));

        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            rulesContainer.addAll(entry.getValue());
        }

        cacheChainlet = new StringBuilder(chainlet.toString());
        provideBottomUpParser(map, okNext);
    }


    private static void provideBottomUpParser(Map<String, List<String>> map, boolean okNextLetter) {
        boolean successfulSymbolUp = false;
        String loadNextChar = "";

        if (okNextLetter) {
            okNext = false;
            loadNextChar = String.valueOf(cacheChainlet.charAt(0));
            String complexChars = "";

            //add history
            addHistoryAndCharacter(loadNextChar);

            cacheChainlet.deleteCharAt(0);

            //govnocode
            String checkLine = container.get(GET_CHAIN).substring(1, container.get(GET_CHAIN).length());
            if (!checkLine.isEmpty()) {
                complexChars = checkLine;
            }

            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                for (String rule : entry.getValue()) {
                    if (rule.equals(String.valueOf(loadNextChar)) || rule.equals(complexChars)) {
                        String key = entry.getKey();

                        //bug below, if u put aa - u will get FF for example
                        String newChain = container.get(GET_CHAIN).replace(rule, key);
                        container.set(GET_CHAIN, newChain);

                        int position = 1 + rulesContainer.indexOf(rule);    // count by key + pos in the list
                        String newHistory = position + container.get(GET_HISTORY); // 5s
                        container.set(GET_HISTORY, newHistory);

                        //show result our steps
                        System.out.println(container);

                        okNext = false;
                        successfulSymbolUp = true;
                        provideBottomUpParser(map, okNext);

                        System.out.println("equals");
                    } else {
                        System.out.println("not equals");
                    }
                }
            }
        } else {
            boolean diveMore = false;

            //Свертка, ласт элемент свернулся и мы смотрим, может ли новая цепь свернуться во что-нибудь больше
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                for (String rule : entry.getValue()) {
                    String cleanUp = container.get(GET_CHAIN).substring(1, container.get(GET_CHAIN).length());
                    String lastSymbol = cleanUp.substring(cleanUp.length() - 1);

                    if (cleanUp.equals(rule)) {
                        int position = 1 + rulesContainer.indexOf(rule);    // count by key + pos in the list
                        String newHistory = position + container.get(GET_HISTORY); // ...s
                        container.set(GET_HISTORY, newHistory);

                        String newChain = container.get(GET_CHAIN).replace(
                                container.get(GET_CHAIN), "$" + entry.getKey()
                        );
                        container.set(GET_CHAIN, newChain);

                        //show result our steps
                        System.out.println(container);

                        diveMore = true;
                        provideBottomUpParser(map, false);
                    } else if (rule.equals(lastSymbol)) {
                        int position = 1 + rulesContainer.indexOf(rule);    // count by key + pos in the list
                        String newHistory = position + container.get(GET_HISTORY); // ...s
                        container.set(GET_HISTORY, newHistory);

                        String newChain = container.get(GET_CHAIN).replace(lastSymbol, entry.getKey());
                        container.set(GET_CHAIN, newChain);

                        //show results
                        System.out.println(container);

                        diveMore = true;
                        provideBottomUpParser(map, false);
                    } else {
                        System.out.println("i'm here");
                    }
                }
            }

            //if chains equals nil and we come back
            if (cacheChainlet.length() == 0) {
                container.set(GET_STATUS, "b");
                DIRECTION = false;
            }

            //if наш дайв во всех правилах не нашел продолжения, мы добавляем next symbol and provide next step
            if (!diveMore && DIRECTION) {
                provideBottomUpParser(map, true);
            }
        }

        //clean code
        if (!successfulSymbolUp && DIRECTION) {
            if (!loadNextChar.isEmpty()) {
                provideBottomUpParser(map, true);
            }
        }
    }

    private static void addHistoryAndCharacter(String loadNextChar) {
        container.set(GET_CHAIN, container.get(GET_CHAIN) + loadNextChar);

        C += 1;
        container.set(GET_COUNTER, String.valueOf(C));

        if (container.get(GET_HISTORY).equals("e") && container.get(GET_CHAIN).length() > 1) {
            container.set(GET_HISTORY, "s");
        } else {
            StringBuilder builder = new StringBuilder(container.get(GET_HISTORY)).insert(0, "s");
            container.set(GET_HISTORY, builder.toString());
        }

        System.out.println(container);
    }
}
