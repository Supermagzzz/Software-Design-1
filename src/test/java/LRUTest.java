import org.junit.*;

import static org.junit.jupiter.api.Assertions.*;

public class LRUTest {

    final private int ELEMENTS_COUNT = 1000;

    @Test
    public void testCacheSizeIsMoreThanZero() {
        assertThrows(AssertionError.class, () -> {
            new LRUCache<Integer, Integer>(0);
        });
        assertThrows(AssertionError.class, () -> {
            new LRUCache<Integer, Integer>(-5);
        });
        assertDoesNotThrow(() -> {
            new LRUCache<Integer, Integer>(5);
        });
    }

    @Test
    public void testSimpleAdd() {
        LRUCache<Integer, Integer> cache = new LRUCache<>(10);
        cache.add(1, 10);
        assertTrue(cache.contains(1));
        assertFalse(cache.contains(2));
        assertThrows(AssertionError.class, () -> {
            cache.get(2);
        });
        assertThrows(AssertionError.class, () -> {
            cache.remove(2);
        });
        assertDoesNotThrow(() -> {
            cache.remove(1);
        });
    }

    @Test
    public void testSimpleOverflow() {
        LRUCache<Integer, Integer> cache = new LRUCache<>(1);
        cache.add(1, 10);
        cache.add(2, 20);
        assertTrue(cache.contains(2));
        assertFalse(cache.contains(1));
        assertThrows(AssertionError.class, () -> {
            cache.remove(1);
        });
        assertDoesNotThrow(() -> {
            cache.remove(2);
        });
        assertFalse(cache.contains(2));
        assertFalse(cache.contains(1));
    }

    @Test
    public void testSimpleEmptyGet() {
        LRUCache<Integer, Integer> cache = new LRUCache<>(1);
        assertFalse(cache.contains(1));
        assertThrows(AssertionError.class, () -> {
            cache.get(1);
        });
        assertThrows(AssertionError.class, () -> {
            cache.remove(1);
        });
    }

    @Test
    public void testSimpleDoubleRemove() {
        LRUCache<Integer, Integer> cache = new LRUCache<>(1);
        cache.add(1, 10);
        assertTrue(cache.contains(1));
        assertDoesNotThrow(() -> {
            cache.remove(1);
        });
        assertThrows(AssertionError.class, () -> {
            cache.remove(1);
        });
    }

    @Test
    public void testSimpleGoodValues() {
        LRUCache<Integer, Integer> cache = new LRUCache<>(1);
        cache.add(1, 10);
        assertTrue(cache.contains(1));
        assertEquals(cache.get(1), 10);
        cache.add(2, 20);
        assertTrue(cache.contains(2));
        assertFalse(cache.contains(1));
        assertEquals(cache.get(2), 20);
    }

    @Test
    public void testSimpleLogic() {
        LRUCache<Integer, Integer> cache = new LRUCache<>(2);
        // cache = [(1, 10)]
        cache.add(1, 10);
        assertTrue(cache.contains(1));
        assertEquals(cache.get(1), 10);
        assertThrows(AssertionError.class, () -> {
            cache.get(5);
        });
        assertThrows(AssertionError.class, () -> {
            cache.remove(5);
        });

        // cache = [(1, 10), (5, 50)]
        cache.add(5, 50);
        assertEquals(cache.get(5), 50);
        assertDoesNotThrow(() -> {
            cache.get(5);
        });
        assertDoesNotThrow(() -> {
            cache.remove(5);
        });

        // cache = [(5, 50), (2, 20)]
        cache.add(2, 20);
        // cache = [(2, 20), (3, 30)]
        cache.add(3, 30);
        assertFalse(cache.contains(1));
        assertThrows(AssertionError.class, () -> {
            cache.get(1);
        });
        assertTrue(cache.contains(2));
        assertTrue(cache.contains(3));

        // cache = [(3, 30), (2, 20)]
        cache.add(2, 20);
        // cache = [(2, 20), (4, 40)]
        cache.add(4, 40);
        assertTrue(cache.contains(2));
        assertTrue(cache.contains(4));
        assertFalse(cache.contains(3));
    }

    @Test
    public void testAddAndRemove() {
        LRUCache<Integer, Integer> cache = new LRUCache<>(ELEMENTS_COUNT);
        for (int i = 0; i < ELEMENTS_COUNT; i++) {
            cache.add(i, i);
        }
        for (int i = ELEMENTS_COUNT - 1; i >= 0; i--) {
            assertEquals(cache.get(i), i);
            cache.remove(i);
            assertFalse(cache.contains(i));
        }
    }

    @Test
    public void testCacheSize() {
        LRUCache<Integer, Integer> cache = new LRUCache<>(ELEMENTS_COUNT);
        for (int i = 0; i < 2 * ELEMENTS_COUNT; i++) {
            cache.add(i, i);
        }
        for (int i = 0; i < ELEMENTS_COUNT; i++) {
            assertFalse(cache.contains(i));
        }
        for (int i = ELEMENTS_COUNT; i < 2 * ELEMENTS_COUNT; i++) {
            assertTrue(cache.contains(i));
        }
    }

    @Test
    public void testClearAndUpdate() {
        LRUCache<Integer, Integer> cache = new LRUCache<>(ELEMENTS_COUNT);
        for (int i = 0; i < ELEMENTS_COUNT; i++) {
            cache.add(i, i);
        }
        for (int i = 0; i < ELEMENTS_COUNT; i++) {
            cache.remove(i);
        }
        for (int i = 0; i < ELEMENTS_COUNT; i++) {
            assertFalse(cache.contains(i));
        }
        for (int i = 0; i < ELEMENTS_COUNT; i++) {
            cache.add(i, i);
        }
        for (int i = 0; i < ELEMENTS_COUNT; i++) {
            assertTrue(cache.contains(i));
        }
    }
}
