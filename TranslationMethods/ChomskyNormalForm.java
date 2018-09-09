package com.caramelheaven.learnsomething.ChomskyNormalForm;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class ChomskyNormalForm {

    private static List<String> containsRandomLetters = new ArrayList<>();
    private static String alphabet = "QWERTYUIOPASDFGHJKLZXCVBNM";

    public static void main(String[] args) {
        List<List<String>> matrix = new ArrayList<>();
        matrix.add(Arrays.asList("S", "->", "AB"));
        matrix.add(Arrays.asList("A", "->", "aBcB"));
        matrix.add(Arrays.asList("D", "->", "def"));

        matrix = removeLongRules(matrix);
        System.out.println(matrix);
        removeEps(matrix);
    }

    private static List<List<String>> removeLongRules(List<List<String>> myList) {
        List<String[]> result = new ArrayList<>();
        Map<String, String[]> bindingByLetter = new HashMap<>();

        for (List<String> list : myList) {
            int count = list.get(2).length();
            if (count > 2) {
                String getCacheLetter = "";
                int changeChar = 1;
                StringBuilder bulder = new StringBuilder(list.get(2));
                for (int i = 0; i < count - 2; i++) {
                    bulder.insert(changeChar, "R");
                    changeChar += 2;
                }
                String[] currentMassive = bulder.toString().split("R");
                System.out.println("current:" + Arrays.toString(currentMassive));
                getCacheLetter = getRandomLetter();
                containsRandomLetters.add(getCacheLetter);
                for (int q = 0; q < currentMassive.length - 1; q++) {
                    currentMassive[q] = currentMassive[q] + getCacheLetter;
                }
                System.out.println("after: " + Arrays.toString(currentMassive));

                for (int q = 0; q < currentMassive.length; q++) {
                    bindingByLetter.put(getCacheLetter, currentMassive);
                }
                result.add(currentMassive);
            }
        }

        List<List<String>> futureRules = new ArrayList<>();
        for (Map.Entry<String, String[]> entry : bindingByLetter.entrySet()) {
            for (String rule : entry.getValue()) {
                if (rule.contains(entry.getKey())) {
                    String let = getRandomLetter();
                    containsRandomLetters.add(let);
                    futureRules.add(Arrays.asList(let, "->", rule));
                } else {
                    futureRules.add(Arrays.asList(entry.getKey(), "->", rule));
                }
            }
            System.out.println("key: " + entry.getKey() + " word:  " + Arrays.toString(entry.getValue()));
        }
        return futureRules;
    }

    @SuppressLint("CheckResult")
    private static void removeEps(List<List<String>> matrix) {
        List<String> containerEps = new ArrayList<>();
        Observable.just(matrix)
                .flatMapIterable((Function<List<List<String>>, Iterable<List<String>>>) lists -> lists)
                .doOnNext(list -> {
                    if (list.get(2).contains("e")) {
                        containerEps.add(list.get(2));
                    }
                })
                .doOnNext(list -> {

                })
                .subscribe();
    }

    private static String getRandomLetter() {
        Random rand = new Random();
        boolean repeated = true;
        Character ch = null;
        while (repeated) {
            ch = alphabet.charAt(rand.nextInt(alphabet.length()));
            if (!containsRandomLetters.contains(ch)) {
                System.out.println("Не содержит: " + containsRandomLetters + " and ch: " + ch);
                repeated = false;
            }
        }
        return ch.toString();
    }
}