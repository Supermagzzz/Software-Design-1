import java.util.HashMap;

public class LRUCache<K, V> {
    private final int maxSize;
    private final HashMap<K, Node<K, V>> cache;
    private Node<K, V> head;
    private Node<K, V> tail;

    LRUCache(int maxSize) {
        assert maxSize > 0;
        this.maxSize = maxSize;
        this.head = null;
        this.tail = null;
        this.cache = new HashMap<>();
    }

    public void remove(K key) {
        assert cache.containsKey(key);
        assert cache.size() <= maxSize;
        int sizeBeforeRemove = cache.size();
        doRemove(key);
        assert !cache.containsKey(key);
        assert sizeBeforeRemove == cache.size() + 1;
    }

    public void add(K key, V value) {
        assert cache.size() <= maxSize;
        doAdd(key, value);
        assert cache.containsKey(key);
        assert cache.get(key).value == value;
    }

    public boolean contains(K key) {
        assert cache.size() <= maxSize;
        boolean answer = doContains(key);
        assert cache.containsKey(key) == answer;
        return answer;
    }

    public V get(K key) {
        assert cache.size() <= maxSize;
        assert cache.containsKey(key);
        V answer = doGet(key);
        assert cache.containsKey(key);
        return answer;
    }

    private void doRemove(K key) {
        if (head != null && key == head.key) {
            head = head.prev;
        }
        if (tail != null && key == tail.key) {
            tail = tail.next;
        }
        cache.get(key).remove();
        cache.remove(key);
    }

    public void doAdd(K key, V value) {
        if (cache.containsKey(key)) {
            remove(key);
        }
        if (cache.size() == maxSize) {
            remove(tail.key);
        }
        head = new Node<>(head, null, key, value);
        if (tail == null) {
            tail = head;
        }
        cache.put(key, head);
    }

    public boolean doContains(K key) {
        return cache.containsKey(key);
    }

    public V doGet(K key) {
        if (cache.containsKey(key)) {
            return cache.get(key).value;
        } else {
            return null;
        }
    }

    private static class Node<K, V> {
        private Node<K, V> next, prev;
        private final K key;
        private final V value;

        Node(Node<K, V> prev, Node<K, V> next, K key, V value) {
            this.next = next;
            if (next != null) {
                next.prev = this;
            }
            this.prev = prev;
            if (prev != null) {
                prev.next = this;
            }
            this.key = key;
            this.value = value;
        }

        public void remove() {
            if (this.next != null) {
                this.next.prev = this.prev;
            }
            if (this.prev != null) {
                this.prev.next = this.next;
            }
        }
    }
}
