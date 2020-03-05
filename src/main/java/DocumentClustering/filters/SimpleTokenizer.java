package DocumentClustering.filters;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Locale;

public class SimpleTokenizer {
    BreakIterator _iter = null;

    public SimpleTokenizer() {
        Locale currentLocale = new Locale ("en","US");
        _iter = BreakIterator.getWordInstance(currentLocale);
    }

}
