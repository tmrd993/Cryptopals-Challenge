package utils;

public class Pair<K extends Comparable<K>, V extends Comparable<V>> {

    private K key;
    private V value;
    
    public Pair(K key, V value) {
	this.key = key;
	this.value = value;
    }
    
    public K key() {
	return key;
    }
    
    public V value() {
	return value;
    }
}
