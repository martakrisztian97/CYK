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
     * @param stringLeft A baloldali nemterminalisok.
     * @param stringRight A jobboldali nemterminalisok.
     * @return Nemterminalisok.
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
     * @param inputString Az input sztring.
     */
    public static void firstRowUpload(String inputString) {
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
     * Egy mezo nemterminalisainak meghatarozasa.
     * @param rowIndex A feltoltendo mezo soranak indexe.
     * @param columnIndex A feltoltendo mezo oszlopanak indexe.
     */
    public static void uploadField(int rowIndex, int columnIndex) {
        String nonterminals = "";
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
        pyramid[rowIndex][columnIndex] = nonterminals;
    }

    /**
     * Piramis egy soranak kiirasa.
     * @param row A piramis kiirnando soranak szama.
     */
    public static void printRow(int row) {
        for (int i = 0; i < 8-row; i++) {
            if (pyramid[row-1][i].isEmpty()) {
                System.out.print("- ");
            } else {
                System.out.print(pyramid[row-1][i]+" ");
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        firstRowUpload(inputString);

    }
}