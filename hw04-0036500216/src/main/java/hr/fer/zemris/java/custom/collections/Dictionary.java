package hr.fer.zemris.java.custom.collections;

import java.util.Map;

/**
 * Parameterized class that represents Dictionary
 * 
 * @author ilovrencic
 *
 * @param <K> - parameterized value of key
 * @param <V> - parameterized value of value
 */
public class Dictionary<K, V> {

	/**
	 * Parameterized {@link ArrayIndexedCollection} that serves as dictionary
	 */
	private ArrayIndexedCollection<Element<K, V>> dictionary;

	/**
	 * Default constructor that initializes the {@link ArrayIndexedCollection}
	 */
	public Dictionary() {
		this.dictionary = new ArrayIndexedCollection<>();
	}

	/**
	 * Method that checks whether the {@link Dictionary} is empty.
	 * 
	 * @return true - if there is no elements
	 */
	public boolean isEmpty() {
		return dictionary.isEmpty();
	}

	/**
	 * Method that returns number of elements inside {@link Dictionary}
	 * 
	 * @return - number of elements inside {@link Dictionary}
	 */
	public int size() {
		return dictionary.size();
	}

	/**
	 * Method that removes all the elements from the {@link Dictionary}. In this
	 * case this method is just called on {@link ArrayIndexedCollection}.
	 */
	public void clear() {
		dictionary.clear();
	}

	/**
	 * Method that puts {@link Element} value inside {@link ArrayIndexedCollection}.
	 * If there is already a {@link Element} with the same key value, the value will
	 * be overwritten.
	 * 
	 * @param key
	 * @param value
	 */
	public void put(K key, V value) {
		if(key == null) {
			throw new NullPointerException("Key can't be null!");
		}
		Element<K, V> element = new Element<>(key, value);
		if (dictionary.contains(element)) {
			dictionary.remove(element);
		}

		dictionary.add(element);
	}

	/**
	 * Method that returns value for a given {@link Object} key. If there is no such
	 * key inside {@link Dictionary} the method will return <code>null</code>.
	 * 
	 * @param key - key for which we are looking a value
	 * @return - value that corresponds to the given key
	 */
	@SuppressWarnings("unchecked")
	public V get(Object key) {
		if(key == null) {
			throw new NullPointerException("Key can't be null!");
		}
		Element<K, V> element = new Element<K, V>((K) key, null);
		if (dictionary.contains(element)) {
			return dictionary.get(dictionary.indexOf(element)).getValue();
		}
		return null;
	}

	/**
	 * Class that represents a single element inside {@link Dictionary}. It consists
	 * of key-value pair that should resemble to the {@link Map} implementation.
	 * 
	 * @author ilovrencic
	 *
	 * @param <K> - key type
	 * @param <V> - value type
	 */
	private static class Element<K, V> {

		private K key;
		private V value;

		/**
		 * Default constructor for {@link Element}
		 * 
		 * @param key
		 * @param value
		 */
		public Element(K key, V value) {
			this.key = key;
			this.value = value;
		}

		/**
		 * Getter for value
		 * 
		 * @return
		 */
		public V getValue() {
			return value;
		}

		@Override
		public int hashCode() {
			return key.hashCode() + value.hashCode();
		}

		@Override
		@SuppressWarnings("unchecked")
		public boolean equals(Object obj) {
			if (obj instanceof Element) {
				Element<K, V> element = (Element<K, V>) obj;
				return element.key.equals(key);
			}
			return false;
		}
	}
}
