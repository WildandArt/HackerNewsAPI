package com.artozersky.HackerNewsAPI.cache;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;

public class CacheEntity implements Cache{
    private final String name;
    private final Map<Long, Object> store;
    private final int maxSize;

    public CacheEntity(String name, int maxSize) {
        this.name = name;
        this.maxSize = maxSize;
        this.store = new LinkedHashMap<Long, Object>(maxSize, 0.75f, true) {
			@Override
            protected boolean removeEldestEntry(Map.Entry<Long, Object> eldest) {
                return size() > CacheEntity.this.maxSize;
		}
	};

    }
	@Override
	public Object getNativeCache() {
		return this.store;
	}

	@Override
	public void clear() {
		this.store.clear();
		
	}

	@Override
	public <T> T get(Object key, Class<T> type) {
		Object value = store.get(key);
        if (value != null && type.isInstance(value)) {
            return type.cast(value);
        }
        return null;
	}
	@Override
	public <T> T get(Object key, Callable<T> valueLoader) {
		Object value = store.get(key);
        if (value == null) {
            try {
                value = valueLoader.call();
                put(key, value);
            } catch (Exception e) {
                throw new RuntimeException("Failed to load value for key: " + key, e);
            }
        }
		return (T) (value);
	}

	@Override
	public ValueWrapper get(Object key) {
		Object value = store.get(key);
        return (value != null) ? () -> value : null;
	}

	@Override
	public void evict(Object key) {
		store.remove(key);
		
	}

	@Override
	public void put(Object key, Object value) {
		store.put((Long) key, value);
		
	}

	@Override
	public String getName() {
		return this.name;
	}


}