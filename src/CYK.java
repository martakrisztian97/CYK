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
     *
     * Szabalyok:
     * S -> AB | CD | CB | SS
     * A -> BC | a
     * B -> SC | b
     * C -> DD | b
     * D -> BA
     */
    public static final String inputString = "aabbaba";
    public static final List<String> s = new ArrayList<String>(Arrays.asList("AB", "CD", "CB", "SS"));
    public static final List<String> a = new ArrayList<String>(Arrays.asList("BC", "a"));
    public static final List<String> b = new ArrayList<String>(Arrays.asList("SC", "b"));
    public static final List<String> c = new ArrayList<String>(Arrays.asList("DD", "b"));
    public static final List<String> d = new ArrayList<String>(Arrays.asList("BA"));
    public static final String[][] pyramid = new String[7][7];

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
            if (s.contains(item) && !nonterminals.contains("S")) {
                nonterminals = nonterminals.concat("S");
            }
            if (a.contains(item) && !nonterminals.contains("A")) {
                nonterminals = nonterminals.concat("A");
            }
            if (b.contains(item) && !nonterminals.contains("B")) {
                nonterminals = nonterminals.concat("B");
            }
            if (c.contains(item) && !nonterminals.contains("C")) {
                nonterminals = nonterminals.concat("C");
            }
            if (d.contains(item) && !nonterminals.contains("D")) {
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
        for (int i = 0; i < inputString.length(); i++) {
            nonterminals = "";
            if (s.contains(inputString.charAt(i)+"")) {
                nonterminals = nonterminals.concat("S");
            }
            if (a.contains(inputString.charAt(i)+"")) {
                nonterminals = nonterminals.concat("A");
            }
            if (b.contains(inputString.charAt(i)+"")) {
                nonterminals = nonterminals.concat("B");
            }
            if (c.contains(inputString.charAt(i)+"")) {
                nonterminals = nonterminals.concat("C");
            }
            if (d.contains(inputString.charAt(i)+"")) {
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
        int k = 6;
        for (int i = 1; i < 6; i++) {
            for (int j = 0; j < k; j++) {
                uploadField(i,j);
            }
            k--;
        }
        uploadField(6,0);
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
        for (int i = 0; i < 8-row; i++) {  // i az oszlop szama, a sor szamanak fuggvenyeben
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
        for (int i = 0; i < s.size(); i++) {
            System.out.print(s.get(i));
            if (i != s.size()-1)
                System.out.print(" | ");
        }

        System.out.print("\nA -> ");
        for (int i = 0; i < a.size(); i++) {
            System.out.print(a.get(i));
            if (i != a.size()-1)
                System.out.print(" | ");
        }

        System.out.print("\nB -> ");
        for (int i = 0; i < b.size(); i++) {
            System.out.print(b.get(i));
            if (i != b.size()-1)
                System.out.print(" | ");
        }

        System.out.print("\nC -> ");
        for (int i = 0; i < c.size(); i++) {
            System.out.print(c.get(i));
            if (i != c.size()-1)
                System.out.print(" | ");
        }

        System.out.print("\nD -> ");
        for (int i = 0; i < d.size(); i++) {
            System.out.print(d.get(i));
            if (i != d.size()-1)
                System.out.print(" | ");
        }
    }

    public static void main(String[] args) {
        printRules();
        firstRowUpload();
        rowsUpload();
        if (pyramid[6][0].contains("S")) {
            System.out.println("\nA(z) "+inputString+" sztring levezetheto.\n");
        } else {
            System.out.println("\nA(z) "+inputString+" sztring nem vezetheto le.\n");
        }
        printRow(7);
        printRow(6);
        printRow(5);
        printRow(4);
        printRow(3);
        printRow(2);
        printRow(1);
    }
}