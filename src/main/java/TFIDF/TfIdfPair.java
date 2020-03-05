package TFIDF;

public class TfIdfPair implements Comparable<TfIdfPair> {

    String key;

    Double value;

    public TfIdfPair(String key, Double value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }


    //descending
    public int compareTo(TfIdfPair o)
    {
        if(this.value<o.value)
            return 1;
        else if(o.value<this.value)
            return -1;
        return 0;
    }
}
