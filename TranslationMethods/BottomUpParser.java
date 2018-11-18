import java.util.*;

public class BottomUpParser {

    private static Map<String, List<String>> map;
    private static StringBuilder chainlet, chainReveir;
    private static List<Grammar> grammarList;
    private static Map<String, List<Integer>> loserDirections;

    private static final int ONE_LETTER = 0;
    private static final int MORE_LETTERS = 1;

    //statements
    private static final String B = "b";
    private static final String Q = "q";
    private static final String T = "t";

    private static Configuration configuration;

    public static void main(String[] args) {
        map = new LinkedHashMap<>();
        chainlet = new StringBuilder("a*a");
        chainReveir = new StringBuilder();
        grammarList = new ArrayList<>();
        loserDirections = new HashMap<>();

        map.put("S", new ArrayList<>(Arrays.asList("S+T", "T")));
        map.put("T", new ArrayList<>(Arrays.asList("T*F", "F")));
        map.put("F", new ArrayList<>(Arrays.asList("a")));

        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            for (String rule : entry.getValue()) {
                Grammar grammar = new Grammar();

                grammar.setNoTerminal(entry.getKey());
                grammar.setTerminal(rule);

                grammarList.add(grammar);
            }
        }

        configuration = new Configuration();

        configuration.setCounter(1);
        configuration.setChain("$");
        configuration.setStatement("q");
        configuration.setHistory("e");

        addSymbol();

        chainReveir.append(chainlet.charAt(0));
        chainlet.deleteCharAt(0);

        //show
        System.out.println(configuration.toString());

        provideBottomUpParser(grammarList);

    }

    private static void provideBottomUpParser(List<Grammar> grammarList) {
        boolean diveMore = false;
        String nextChainlet = "";

        if (chainlet.length() != 0) {
            nextChainlet = String.valueOf(chainlet.charAt(0));
        } else {
            System.out.println("k");
        }

        for (int i = 0; i < grammarList.size(); i++) {
            //provide last symbol and index
            String lastSymbol = extractChain(ONE_LETTER);
            String someSymbols = extractChain(MORE_LETTERS);
            int lastIndexSymbol = configuration.getChain().length() - 1;

            if (someSymbols.equals("T*a") && i == 4) {
                System.out.println("here");
            }

            //add loser ways if map contains it
            List<Integer> loserList = new ArrayList<>();
            if (loserDirections.containsKey(lastSymbol)) {
                loserList.addAll(loserDirections.get(lastSymbol));
            }

            if (grammarList.get(i).terminal.equals(lastSymbol)) {
                upChain(grammarList.get(i).terminal, grammarList.get(i).noTerminal, i, lastIndexSymbol);

                //show
                System.out.println(configuration.toString());

                //the end
                if (configuration.getChain().equals("$S") && chainlet.length() == 0) {
                    configuration.setStatement(T);
                    System.out.println(configuration.toString());
                    System.exit(0);
                }

                //try to cвертку еще раз
                diveMore = true;
                provideBottomUpParser(grammarList);

                if (configuration.getStatement().equals(B)) {
                    //loserDirections.add(i);
                    break;
                }
            } else if (grammarList.get(i).terminal.equals(someSymbols)) {
                System.out.println("contain some symbols");
                upChain(grammarList.get(i).terminal, grammarList.get(i).noTerminal, i, lastIndexSymbol);

                //show
                System.out.println(configuration.toString());

                diveMore = true;
                provideBottomUpParser(grammarList);

                if (configuration.getStatement().equals(B)) {
                    break;
                }
            }
        }

        if (configuration.getStatement().equals(Q)) {
            if (!diveMore) {
                if (chainlet.toString().isEmpty()) {
                    //when our chain is empty we come back
                    configuration.setStatement(B);

                    //show
                    System.out.println(configuration.toString());
                } else {
                    addSymbol();

                    //show
                    System.out.println(configuration.toString());

                    chainReveir.append(chainlet.charAt(0));
                    chainlet.deleteCharAt(0);

                    provideBottomUpParser(grammarList);
                }
            }
        }

        if (configuration.getStatement().equals(B)) {
            String history = configuration.getHistory();
            if (history.substring(0, 1).equals("s")) {
                boolean diveAnotherPath = false;
                //check other directions, if loser not contains loser direction and grammar == other direction of symbol
                String lastSymbol = extractChain(ONE_LETTER);

                //add loser ways if map contains it
                List<Integer> loserList = new ArrayList<>();
                if (loserDirections.containsKey(lastSymbol)) {
                    loserList.addAll(loserDirections.get(lastSymbol));
                }

                for (int i = 0; i < grammarList.size(); i++) {
                    if (!loserList.contains(i)
                            && grammarList.get(i).terminal.equals(lastSymbol)) {
                        diveAnotherPath = true;

                        provideBottomUpParser(grammarList);
                    }
                }

                if (!diveAnotherPath) {
                    //we not found another direction, remove last char
                    removeSymbol();

                    //add removed symbol
                    chainlet.insert(0, chainReveir.charAt(0));
                    chainReveir.deleteCharAt(0);

                    //show
                    System.out.println(configuration);
                }

                //non we should start again find terminals
            } else {
                int lastSymbolIndex = configuration.getChain().length() - 1;

                //иначе сверни еще раз, и добавь * и погнал
                if (chainlet.length() != 0) {
                    int cutRuleIndex = Integer.parseInt(String.valueOf(configuration.getHistory().charAt(0)));

                    for (int i = 0; i < grammarList.size(); i++) {
                        if (cutRuleIndex == i) {
                            downChain(grammarList.get(i).terminal, lastSymbolIndex);

                            //show
                            System.out.println(configuration.toString());
                            break;
                        }
                    }

                    //and add next symbol
                    addSymbol();
                    configuration.setStatement(Q);

                    chainReveir.append(chainlet.charAt(0));
                    chainlet.deleteCharAt(0);

                    //show
                    System.out.println(configuration.toString());

                    provideBottomUpParser(grammarList);
                } else {
                    //removal chains while we will meet first 's'
                    for (int i = 0; i < grammarList.size(); i++) {
                        int cutRuleIndex = Integer.parseInt(String.valueOf(configuration.getHistory().charAt(0)));

                        if (cutRuleIndex == i) {
                            downChain(grammarList.get(i).terminal, lastSymbolIndex);

                            //show
                            System.out.println(configuration.toString());
                            break;
                        }
                    }

                    //save loser direction
                    if (String.valueOf(history.charAt(1)).equals("s")) {
                        int index = Integer.parseInt(String.valueOf(history.charAt(0)));
                        String lastSymbol = extractChain(ONE_LETTER);
                        List<Integer> loserList = new ArrayList<>();

                        if (loserDirections.containsKey(lastSymbol)) {
                            loserList.addAll(loserDirections.get(lastSymbol));
                        } else {
                            loserList.add(index);
                        }

                        loserDirections.put(lastSymbol, loserList);
                    }
                }
            }
        }
    }

    private static void addSymbol() {
        configuration.setCounter(configuration.getCounter() + 1);

        String history = configuration.getHistory();

        if (history.equals("e")) {
            history = history.replace(history, "");
            configuration.setHistory("s");
        } else {
            configuration.setHistory("s" + history);
        }

        String chain = configuration.getChain();
        chain += chainlet.charAt(0);
        configuration.setChain(chain);
    }

    private static void removeSymbol() {
        configuration.setCounter(configuration.getCounter() - 1);

        String history = configuration.getHistory().substring(1);
        configuration.setHistory(history);

        String chain = configuration.getChain().substring(0, configuration.getChain().length() - 1);
        configuration.setChain(chain);
    }

    private static void upChain(String terminals, String noTerminal, int ruleIndex, int chainIndex) {
        String newChain = "";
        if (terminals.length() > 1) {
            newChain = new StringBuilder(configuration.getChain())
                    .toString()
                    .replace(terminals, noTerminal);
        } else {
            //set chain
            //configuration.setChain(configuration.getChain() + terminal);
            newChain = new StringBuilder(configuration.getChain())
                    .deleteCharAt(chainIndex)
                    .append(noTerminal)
                    .toString();
        }

        configuration.setChain(newChain);

        //set history
        configuration.setHistory(String.valueOf(ruleIndex) + configuration.getHistory());

    }

    private static void downChain(String terminal, int chainIndex) {
        String newChain = new StringBuilder(configuration.getChain())
                .deleteCharAt(chainIndex)
                .append(terminal)
                .toString();

        configuration.setChain(newChain);

        //set history
        String history = configuration.getHistory();
        history = history.substring(1);

        configuration.setHistory(history);
    }

    private static String extractChain(int choose) {
        switch (choose) {
            case ONE_LETTER:
                return String.valueOf(configuration.getChain().charAt(configuration.getChain().length() - 1));
            case MORE_LETTERS:
                return configuration.getChain().substring(1);
        }
        return "";
    }

    private static class Grammar {
        private String noTerminal;
        private String terminal;

        void setNoTerminal(String noTerminal) {
            this.noTerminal = noTerminal;
        }

        void setTerminal(String terminal) {
            this.terminal = terminal;
        }
    }

    private static class Configuration {

        private String statement;
        private int counter;
        private String chain;
        private String history;

        @Override
        public String toString() {
            return "[" +
                    "" + statement +
                    ", " + counter +
                    ", " + chain +
                    ", " + history +
                    ']';
        }

        String getStatement() {
            return statement;
        }

        void setStatement(String statement) {
            this.statement = statement;
        }

        int getCounter() {
            return counter;
        }

        void setCounter(int counter) {
            this.counter = counter;
        }

        String getChain() {
            return chain;
        }

        void setChain(String chain) {
            this.chain = chain;
        }

        String getHistory() {
            return history;
        }

        void setHistory(String history) {
            this.history = history;
        }
    }
}
