import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class App {
        public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter directory");
        String directory = scan.nextLine();
        System.out.println(findFiles(directory));
        writeToFile(findFiles(directory), directory);

    }

    public static ArrayList<String> findFiles(String directory) throws IOException {
        ArrayList<String> txtFiles = new ArrayList<String>();
        String name = " ";
        File file = new File(directory);
        File[] allFiles = file.listFiles();

        for (File text : allFiles) {
            name = text.getName();
            if (name.substring(name.lastIndexOf('.') + 1).equals("txt")) {
                txtFiles.add(name);

            }
        }
        return txtFiles; // return arrayList populated with all .txt files in directory
     

    }

    public static ArrayList<String> listOfWords(ArrayList<String> txtFiles, String directory) throws IOException {
        ArrayList<String> listWords = new ArrayList<>();
        for (String files : txtFiles) {
            Scanner scan = new Scanner(new File(files));
            String temp = "";
            while (scan.hasNext()) {
                temp = scan.next();
                listWords.add(temp);
            }
        }
        return listWords;

    }

    public static String longestLine(ArrayList<String> txtFiles, String directory) throws IOException {
        String longestLine = "";
        String current;

        txtFiles = findFiles(directory);

        for (String files : txtFiles) {
            Scanner scan = new Scanner(new File(files));
            while (scan.hasNext()) {
                current = scan.nextLine();
                if (current.length() > longestLine.length()) {
                    longestLine = current;
                }

            }
        }
        String[] words = longestLine.split("\\s");
        return "Longest line: " + (longestLine.replace(" ", "").length() + " chars," + words.length + " tokens\n");//replace
    }

    public static String findAvgLength(ArrayList<String> txtFiles, String directory) throws IOException {
        String currentLine;
        double numWords = 0;
        int count = 0;
        double avg = 0;
        double sum = 0;
        txtFiles = findFiles(directory);
        for (String files : txtFiles) {
            Scanner scan = new Scanner(new File(files));
            while (scan.hasNextLine()) {
                currentLine = scan.nextLine();
                numWords = currentLine.split("\\s+").length;
                sum = numWords + sum; // total # of words
                count++; // number of lines

                if (count > 0) {
                    avg = sum / count;
                }
            }

        }

        avg = avg * 100;
        avg = Math.round(avg);
        avg = avg / 100;
        return ("Average line length: " + avg + " tokens\n"); //replace
    }

    public static String uniqueTokens(ArrayList<String> txtFiles, String directory) throws IOException {
        ArrayList<String> listOfWords = new ArrayList<String>();
        txtFiles = findFiles(directory);

        for (String files : txtFiles) {
            Scanner scan = new Scanner(new File(files));
            while (scan.hasNext()) {
                String word = scan.next(); // read each word in file
                if (!listOfWords.contains(word)) { // add the word if it isn't
                    // added already
                    listOfWords.add(word);
                }
            }
        }
        return ("Number of unique tokens: " + listOfWords.size() + "\n"); //replace
    }

    public static String numWordsInFile(ArrayList<String> txtFiles, String directory) throws IOException {
        int count = 0;
        txtFiles = findFiles(directory);

        for (String files : txtFiles) {
            Scanner scan = new Scanner(new File(files));
            while (scan.hasNext()) {
                scan.next();
                count++;
            }
        }
        return "Number of all space-delineated tokens: " + count + "\n";
    }

    public static ArrayList<Object> countMostUsed(ArrayList<String> txtFiles, String directory) throws IOException {
        ArrayList<String> words = listOfWords(txtFiles, directory);
        ArrayList<Object> wordAndCount = new ArrayList<>();
        txtFiles = findFiles(directory);
        for (int i = 0; i < words.size(); i++) {
            if (!wordAndCount.contains(words.get(i))) {
                wordAndCount.add(words.get(i));
                wordAndCount.add(Collections.frequency(words, words.get(i)));
            }
        }

        return wordAndCount;

    }

    public static void writeToFile(ArrayList<String> txtFiles, String directory) throws IOException {
        ArrayList<Object> wordsAndCounts = countMostUsed(txtFiles, directory);
        int max = 0;

        for (String file : txtFiles) {
            File outputFile = new File(file.concat(".stats"));
            try {
                FileWriter writer = new FileWriter(outputFile);
                BufferedWriter bufferedWriter = new BufferedWriter(writer);
                bufferedWriter.write("Awale Ahmed");
                bufferedWriter.write(longestLine(txtFiles, directory));
                bufferedWriter.write(findAvgLength(txtFiles, directory));
                bufferedWriter.write(uniqueTokens(findFiles(directory), directory));
                bufferedWriter.write(numWordsInFile(findFiles(directory), directory));
                bufferedWriter.write(countMostUsed(findFiles(directory), directory).toString());
                for (int i = 1; i < wordsAndCounts.size(); i += 2) {
                    while (i <= 10) { //might not work
                        if ((Integer) wordsAndCounts.get(i) > max)
                            max = (Integer) (wordsAndCounts.get(i));
                        bufferedWriter.write(wordsAndCounts.get(wordsAndCounts.indexOf(max)) + " / " + wordsAndCounts.get(wordsAndCounts.indexOf(max) - 1) + "\n");

                    }
                }
                bufferedWriter.close();

            } catch (IOException ex) {
                System.out.println("Could not write to file");
            }
        }
    }
}
