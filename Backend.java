
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class HashTableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {

  private LinkedList<LinkedNode>[] hash;
  int size;
  int capacity;

  public HashTableMap(int capacity) { // constructor
    hash = new LinkedList[capacity];
    this.capacity = capacity;
    for (int i = 0; i < capacity; i++) {
      hash[i] = new LinkedList<>();
    }
    this.size = 0;
  }

  public HashTableMap() { // constructor
    // with default capacity = 10
    this.capacity = 10;
    hash = new LinkedList[capacity];
    for (int i = 0; i < capacity; i++) {
      hash[i] = new LinkedList<>();
    }
    this.size = 0;
  }

  /**
   * Puts the given key and value in the hash linkedlist. Return true if successfully added and
   * false if the key already exists. Calls the resize method when the capacity reaches 85 percent
   * or more.
   * 
   * @param key   the key associated with the value
   * @param value the value associated with the keys
   * @return true if the key and value successfully added, false otherwise
   */
  @Override
  public boolean put(KeyType key, ValueType value) {
    // TODO Auto-generated method stub

    int num = Math.abs(key.hashCode()) % hash.length; // calculating the index
    if (containsKey(key)) { // if key already exists
      return false;
    }

    hash[num].add(new LinkedNode(key, value)); // otherwise add
    size++;

    if (((double) size / capacity) >= 0.85) { // resize if capacity exceeds or is 85%
      resize();
    }
    return true;
  }

  /**
   * Returns the value associated to the given key
   * 
   * @param key   the given key
   * @param value the value associated to the given key
   * @throws NoSuchElementException
   */
  @Override
  public ValueType get(KeyType key) throws NoSuchElementException {
    // TODO Auto-generated method stub
    int num = Math.abs(key.hashCode()) % hash.length;
    LinkedList<LinkedNode> node = hash[num];

    if (containsKey(key)) {
      for (LinkedNode n : node) { // if the given key matches the list key then value is returned
        if (n.key.equals(key)) {
          return (ValueType) n.value;
        }
      }
    } else {
      throw new NoSuchElementException("The key does not exist!"); // else exception if the key
                                                                   // does not match
    }
    return null;
  }

  /**
   * Returns the size of the list
   * 
   * @return size
   */
  @Override
  public int size() { // returns size
    // TODO Auto-generated method stub
    return this.size;
  }

  /**
   * Checks if the given key exists
   * 
   * @param key the given key
   * @return true if the key exists, false otherwise
   */
  @Override
  public boolean containsKey(KeyType key) {
    // TODO Auto-generated method stub
    int num = Math.abs(key.hashCode()) % hash.length;
    LinkedList<LinkedNode> li = hash[num];

    for (int i = 0; i < li.size(); i++) { // returns true is the given key matches
      if ((li.get(i)).key.equals(key)) {
        return true;
      }
    }
    return false;
  }

  /**
   * removes the given key and its value
   * 
   * @param key the given key
   * @return rmv the value associated to the removed key
   */
  @Override
  public ValueType remove(KeyType key) {
    // TODO Auto-generated method stub

    int num = Math.abs(key.hashCode()) % hash.length;
    LinkedList<LinkedNode> check = hash[num];
    for (LinkedNode n : check) { // removes the given key and returns the associated value
      if (n.key.equals(key)) {
        ValueType rmv = (ValueType) n.value;
        check.remove(n);
        size--;
        return rmv;
      }
    }
    return null;
  }

  /**
   * Clears the LinkedList
   */
  @Override
  public void clear() {
    // TODO Auto-generated method stub
    for (int i = 0; i < capacity; i++) {
      this.hash[i] = null;
    }
    this.size = 0;
  }

  /**
   * Doubles the capacity
   */
  public void resize() {

    LinkedList<LinkedNode>[] narr = new LinkedList[2 * capacity];
    for (int i = 0; i < narr.length; i++) {
      narr[i] = new LinkedList<>();
    }
    hash = narr;
    capacity = 2 * capacity;
  }

}
