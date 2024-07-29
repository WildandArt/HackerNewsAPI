package com.artozersky.HackerNewsAPI.cache;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.cache.Cache;

public class CacheEntity implements Cache{
    private final String name;
    private final Map<Long, Object> store;
    private final int maxSize;

    public CacheEntity(String name, int maxSize) {
        this.name = name;
        this.maxSize = maxSize;
        this.store = new LinkedHashMap<Long, Object>(maxSize, 0.75f, true);

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
		// TODO: Implement the get(Object key, Class<T> type) method
		return null;
	}
	@Override
	public <T> T get(Object key, Callable<T> valueLoader) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'get'");
	}

	@Override
	public ValueWrapper get(Object key) {
		// TODO: Implement the get(Object key) method
		return null;
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