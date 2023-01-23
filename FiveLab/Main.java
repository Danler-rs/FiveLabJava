package FiveLab;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    static String filename = "/home/ilya/IdeaProjects/Labs/src/FoursLab/lenin.txt";
    static String outPutFileName = "/home/ilya/IdeaProjects/Labs/src/FoursLab/outPut.txt";

    static String outPutEveryCharacterFileName = "/home/ilya/IdeaProjects/Labs/src/FoursLab/outPutEveryCharacter.txt";

    static String outPutEveryWordFileName = "/home/ilya/IdeaProjects/Labs/src/FoursLab/outPutEveryWord.txt";

    static String getOutPutWordLengthPlusItsCount = "/home/ilya/IdeaProjects/Labs/src/FoursLab/outPutWordLengthPlusItsCount.txt";

    public static void main(String[] args) throws IOException {
        // Очищаем файл после предыдущих запусков проги
        clearTheOutPutFile(outPutFileName);
        clearTheOutPutFile(outPutEveryWordFileName);
        clearTheOutPutFile(outPutEveryCharacterFileName);
        clearTheOutPutFile(getOutPutWordLengthPlusItsCount);

        List<String> listOfWordsInLeninFile = new ArrayList<>();
        List<String> listOfWordsWithoutDubls = new ArrayList<>();
        List<String> listOfEveryWordPlusItsCount = new ArrayList<>();
        List<String> listOfCharacterPlusItsCount = new ArrayList<>();
        List<String> listOfWordLengthPlusItsCount = new ArrayList<>();

        listOfWordsInLeninFile = getWords(filename);


        // Первые задания //
        //listOfWordsWithoutDubls = withoutDubles(listOfWordsInLeninFile);
        //writeResultToFile(outPutFileName, listOfWordsWithoutDubls);
        //-----------------------------------------------------------


        // Вторые задания //

        //для слов от 4 до 7 букв и в порядке возрастания
        //listOfEveryWordPlusItsCount = everyWordInText(listOfWordsInLeninFile);
        //listOfEveryWordPlusItsCount.sort(Comparator.naturalOrder());
        //writeResultToFile(outPutEveryWordFileName, listOfEveryWordPlusItsCount);

        //------------------------------------------------------------------------

        // Третье задание
        //listOfWordLengthPlusItsCount = wordLengthPlusNumberOfUses(listOfWordsInLeninFile);
        //writeResultToFile(getOutPutWordLengthPlusItsCount, listOfWordLengthPlusItsCount);


        // Пятые задания + 4 //
        //listOfWordsInLeninFile.sort(Comparator.naturalOrder());
        //listOfCharacterPlusItsCount = everySymbolInWord(listOfWordsInLeninFile);
        //writeResultToFile(outPutEveryCharacterFileName, listOfCharacterPlusItsCount);
        //numberSymbolsInText(listOfWordsInLeninFile);

    }

    public static List<String> withoutDubles(List<String> listOfWords) {
        return listOfWords.stream()
                .distinct() //убираем дубли
                .filter(word -> word.length() > 3 && word.length() < 8)
                .sorted().toList();

    }

    public static List<String> everyWordInText(List<String> listOfWords) {
        List<String> resultList = new ArrayList<>();
        Map<String, Long> countWord = listOfWords.stream()
                .filter(word -> word.length() > 3 && word.length() < 8)
                .sorted()
                .collect(Collectors.groupingBy(word -> word, Collectors.counting()));

        // Преобразуем map в List для печати в файл
        List<String> keyList = new ArrayList<>(countWord.keySet());
        List<Long> valueList = new ArrayList<>(countWord.values());

        for (int i = 0; i < keyList.size(); i++) {
            String result = keyList.get(i) + " - " + valueList.get(i);
            resultList.add(result);
        }
        return resultList;
    }

    public static List<String> wordLengthPlusNumberOfUses(List<String> listOfWords) {
        List<String> resultList = new ArrayList<>();
        Map<Integer, Long> map = listOfWords.stream()
                .collect(Collectors.groupingBy(String::length, Collectors.counting()));

        // Преобразуем map в List для печати в файл
        List<Integer> keyList = new ArrayList<>(map.keySet());
        List<Long> valueList = new ArrayList<>(map.values());

        for (int i = 0; i < keyList.size(); i++) {
            String result = keyList.get(i) + " - " + valueList.get(i);
            resultList.add(result);
        }
        return resultList;
    }

    public static void numberSymbolsInText(List<String> listOfWords) {
        final long sum = listOfWords.stream()
                .mapToInt(String::length)
                .sum();
        System.out.println("Кол-во симовлов в тексте " + sum);
    }

    public static List<String> everySymbolInWord(List<String> listOfWords) {
        List<String> resultList = new ArrayList<>();
        Map<String, Long> map = listOfWords.stream()
                .flatMap(word -> Arrays.asList(word.split("")).stream())
                .sorted()
                .collect(Collectors.groupingBy(word -> word, Collectors.counting()));

        // Преобразуем map в List для печати в файл
        List<String> keyList = new ArrayList<>(map.keySet());
        List<Long> valueList = new ArrayList<>(map.values());

        for (int i = 0; i < keyList.size(); i++) {
            String result = keyList.get(i) + " - " + valueList.get(i);
            resultList.add(result);
        }
        return resultList;

    }


    private static List<String> getWords(String source) throws IOException {
        String input = " ";
        var splitter = Pattern.compile("[\\p{Punct}\\d\\s«…»–]+");
        Matcher m = splitter.matcher(input);
        return Files.lines(Path.of(filename)).flatMap(splitter::splitAsStream).filter(w -> !w.isEmpty()).collect(Collectors.toList());
    }

    private static void writeResultToFile(String filename, List<String> lines) throws IOException {
        Files.write(Path.of(filename), lines);
    }


    private static void clearTheOutPutFile(String filename) throws IOException {
        FileWriter fileWriter = new FileWriter(filename, false);
        PrintWriter printWriter = new PrintWriter(fileWriter, false);
        printWriter.flush();
        printWriter.close();
        fileWriter.close();
    }
}
