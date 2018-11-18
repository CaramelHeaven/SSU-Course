import java.util.*;

public class CYK_Algorithm {

    private static Map<String, List<String>> map;
    private static String[][] massiveResult;
    private static char[] chainArray;
    private static String mainChain;

    public static void main(String[] args) {
        map = new LinkedHashMap<>();

        map.put("S", new ArrayList<>(Arrays.asList("AA", "AS", "b")));
        map.put("A", new ArrayList<>(Arrays.asList("SA", "AS", "a")));

        mainChain = "abaab";
        chainArray = mainChain.toCharArray();

        massiveResult = new String[mainChain.length() + 1][mainChain.length()];

        fillBaseLine();
        showResult();

        provideCYK(map);
    }


    private static void provideCYK(Map<String, List<String>> map) {
        fillFirstLine(map);

        showResult();
    }

    private static void fillFirstLine(Map<String, List<String>> map) {
        List<String> line = Arrays.asList(massiveResult[mainChain.length()]);

        for (int i = 0; i < line.size(); i++) {
            String newLetter = "";

            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                for (String letter : entry.getValue()) {
                    if (line.get(i).equals(letter)) {
                        newLetter += entry.getKey();
                    }
                }
            }

            massiveResult[mainChain.length()][i] = newLetter;
        }
    }

    private static void fillBaseLine() {
        for (int i = massiveResult.length - 1; i > massiveResult.length - 2; i--) {
            for (int j = 0; j < massiveResult[i].length; j++) {
                massiveResult[i][j] = String.valueOf(chainArray[j]);
            }
        }
    }

    private static void showResult() {
        for (int i = 0; i < massiveResult.length; i++) {
            System.out.println(Arrays.toString(massiveResult[i]));
        }
    }
}
