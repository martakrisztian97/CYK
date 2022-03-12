import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * CYK algoritmus
 * @author Marta Krisztian (FSB5V7)
 * @since 2022-03-06
 */
public class CYK {

    /**
     * Globalis valtozok:
     * - Input sztring
     * - Szabalyok listak segitsegevel
     * - Piramis ketdimenzios tombje
     */
    public static final String inputString = "aabbaba";
    public static final int N = inputString.length();
    public static final List<String> sRules = new ArrayList<String>(Arrays.asList("AB", "CD", "CB", "SS"));
    public static final List<String> aRules = new ArrayList<String>(Arrays.asList("BC", "a"));
    public static final List<String> bRules = new ArrayList<String>(Arrays.asList("SC", "b"));
    public static final List<String> cRules = new ArrayList<String>(Arrays.asList("DD", "b"));
    public static final List<String> dRules = new ArrayList<String>(Arrays.asList("BA"));
    public static final String[][] pyramid = new String[N][N];

    /**
     * Ket mezo nemterminalisainak elemzese a szabalyok alapjan. (Descartes-szorzat segitsegevel.)
     * @param stringLeft A baloldali mezo nemterminalisai.
     * @param stringRight A jobboldali mezo nemterminalisai.
     * @return A mezo nemterminalisai.
     */
    public static String aXb(String stringLeft, String stringRight) {
        String nonterminals = "";
        Set<String> pairs = new TreeSet<>();
        if (!stringLeft.isEmpty() && !stringRight.isEmpty()) {  // ha egyik sztring sem ures
            for (int i = 0; i < stringLeft.length(); i++) {
                for (int j = 0; j < stringRight.length(); j++) {
                    pairs.add(stringLeft.charAt(i)+""+stringRight.charAt(j)+"");
                }
            }
        }

        for (String item : pairs) {
            if (sRules.contains(item) && !nonterminals.contains("S")) {
                nonterminals = nonterminals.concat("S");
            }
            if (aRules.contains(item) && !nonterminals.contains("A")) {
                nonterminals = nonterminals.concat("A");
            }
            if (bRules.contains(item) && !nonterminals.contains("B")) {
                nonterminals = nonterminals.concat("B");
            }
            if (cRules.contains(item) && !nonterminals.contains("C")) {
                nonterminals = nonterminals.concat("C");
            }
            if (dRules.contains(item) && !nonterminals.contains("D")) {
                nonterminals = nonterminals.concat("D");
            }
        }
        return nonterminals;
    }

    /**
     * Elso sor feltoltese.
     */
    public static void firstRowUpload() {
        String nonterminals = "";
        for (int i = 0; i < N; i++) {
            nonterminals = "";
            if (sRules.contains(inputString.charAt(i)+"")) {
                nonterminals = nonterminals.concat("S");
            }
            if (aRules.contains(inputString.charAt(i)+"")) {
                nonterminals = nonterminals.concat("A");
            }
            if (bRules.contains(inputString.charAt(i)+"")) {
                nonterminals = nonterminals.concat("B");
            }
            if (cRules.contains(inputString.charAt(i)+"")) {
                nonterminals = nonterminals.concat("C");
            }
            if (dRules.contains(inputString.charAt(i)+"")) {
                nonterminals = nonterminals.concat("D");
            }
            if (!nonterminals.isEmpty()) {
                pyramid[0][i] = nonterminals;
            }
        }
    }

    /**
     * Sorok feltoltese a masodik sortol a tetejeig.
     */
    public static void rowsUpload() {
        int k = N-1;
        for (int i = 1; i < N-1; i++) {
            for (int j = 0; j < k; j++) {
                uploadField(i,j);
            }
            k--;
        }
        uploadField(N-1,0);
    }

    /**
     * Egy mezo nemterminalisainak meghatarozasa.
     * @param rowIndex A feltoltendo mezo soranak indexe.
     * @param columnIndex A feltoltendo mezo oszlopanak indexe.
     */
    public static void uploadField(int rowIndex, int columnIndex) {
        String nonterminals = "";
        String nonterminalsOnce = "";
        String temp;
        int leftI = rowIndex-1;  // kezdeti bal mezo sor indexe
        int leftJ = columnIndex; // kezdeti bal mezo oszlop indexe
        int rightI = 0;          // kezdeti jobb mezo sor indexe
        int rightJ = rowIndex+columnIndex;  // kezdeti jobb mezo oszlop indexe
        while (leftI != -1) {
            temp = aXb(pyramid[leftI][leftJ], pyramid[rightI][rightJ]);
            nonterminals = nonterminals.concat(temp);
            leftI--;
            rightI++;
            rightJ--;
        }

        // ismetlodo nemterminalisok kitorlese
        for (int i = 0; i < nonterminals.length(); i++) {
            if ( !nonterminalsOnce.contains(nonterminals.charAt(i)+"") ) {
                nonterminalsOnce = nonterminalsOnce.concat(nonterminals.charAt(i)+"");
            }
        }

        pyramid[rowIndex][columnIndex] = nonterminalsOnce;
    }

    /**
     * Piramis egy soranak kiirasa.
     * @param row A piramis kiirnando soranak szama.
     */
    public static void printRow(int row) {
        for (int i = 0; i < N+1-row; i++) {  // i az oszlop szama, a sor szamanak fuggvenyeben
            if (pyramid[row-1][i].isEmpty()) { // ha ures
                System.out.print("- ");
            } else if (pyramid[row-1][i].length() == 1){ // ha 1 nemtermilais szerepel a mezoben
                System.out.print(pyramid[row-1][i]+" ");
            } else {
                for (int j = 0; j < pyramid[row-1][i].length(); j++) { // ha 2 vagy tobb nemterminalis szerepel a mezoben
                    System.out.print(pyramid[row-1][i].charAt(j));
                    if (j != pyramid[row-1][i].length()-1) { // utolso nemterminalis utani vesszo kiszurese
                        System.out.print(",");
                    }
                }
            }
            System.out.print("\t\t  ");
        }
        System.out.println("\n------------------------------------------------------------------------------");
    }

    /**
     * A szabalyok kiiratasa.
     */
    public static void printRules() {
        System.out.print("Rules: \nS -> ");
        for (int i = 0; i < sRules.size(); i++) {
            System.out.print(sRules.get(i));
            if (i != sRules.size()-1)
                System.out.print(" | ");
        }

        System.out.print("\nA -> ");
        for (int i = 0; i < aRules.size(); i++) {
            System.out.print(aRules.get(i));
            if (i != aRules.size()-1)
                System.out.print(" | ");
        }

        System.out.print("\nB -> ");
        for (int i = 0; i < bRules.size(); i++) {
            System.out.print(bRules.get(i));
            if (i != bRules.size()-1)
                System.out.print(" | ");
        }

        System.out.print("\nC -> ");
        for (int i = 0; i < cRules.size(); i++) {
            System.out.print(cRules.get(i));
            if (i != cRules.size()-1)
                System.out.print(" | ");
        }

        System.out.print("\nD -> ");
        for (int i = 0; i < dRules.size(); i++) {
            System.out.print(dRules.get(i));
            if (i != dRules.size()-1)
                System.out.print(" | ");
        }
    }

    /**
     * Az eredmeny kiiratasa.
     */
    public static void result() {
        if (pyramid[N-1][0].contains("S")) {
            System.out.println("\nA(z) "+inputString+" sztring levezetheto.\n");
        } else {
            System.out.println("\nA(z) "+inputString+" sztring nem vezetheto le.\n");
        }
        for (int i = N; i > 0; i--) {
            printRow(i);
        }
    }

    public static void main(String[] args) {
        printRules();
        firstRowUpload();
        if (N > 1) {
            rowsUpload();
        }
        result();
    }
}