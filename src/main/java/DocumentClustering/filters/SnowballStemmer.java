package DocumentClustering.filters;

import org.tartarus.snowball.ext.*;

public class SnowballStemmer {

    // no of times stemming has to be repeated
    public int STEM_REPEAT_TIMES = 1;

    public englishStemmer _stemmer;

    public SnowballStemmer() {
        _stemmer = new englishStemmer();
    }

    public SnowballStemmer(int stem_repeat_times) {
        STEM_REPEAT_TIMES = stem_repeat_times;
    }

    public String stem(String s) {
        _stemmer.setCurrent(s);
        for (int i = STEM_REPEAT_TIMES; i != 0; i--) {
            _stemmer.stem();
        }
        return _stemmer.getCurrent();
    }

}
