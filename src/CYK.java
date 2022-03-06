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
     */
    public static final String input = "aabbaba";
    public static final List<String> s = new ArrayList<String>(Arrays.asList("AB", "CD", "CB", "SS")); // S -> AB | CD | CB | SS
    public static final List<String> a = new ArrayList<String>(Arrays.asList("BC", "a")); // A -> BC | a
    public static final List<String> b = new ArrayList<String>(Arrays.asList("SC", "b")); // B -> SC | b
    public static final List<String> c = new ArrayList<String>(Arrays.asList("DD", "b")); // C -> DD | b
    public static final List<String> d = new ArrayList<String>(Arrays.asList("BA")); // D -> BA

    /**
        @param stringLeft A baloldali nemterminalisok.
        @param stringRight A jobboldali nemterminalisok.
        @return Nemterminalisok.
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

    public static void main(String[] args) {
    }
}