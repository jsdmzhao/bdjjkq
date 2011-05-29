package com.googlecode.jtiger.modules.cache;


/**
 * Implementors define a caching algorithm. All implementors <b>must</b> be
 * threadsafe.
 * <K> the type of the key.
 */
public interface Cache<K> {

  /**
   * Get an item from the cache, nontransactionally
   * 
   * @param key
   * @return the cached object or <tt>null</tt>
   * 
   */
  public Object get(K key);

  /**
   * Add an item to the cache, nontransactionally, with failfast semantics
   * @param exp expired time in second. 
   * 
   */
  public void put(K key, Object value, int exp);

  /**
   * Add an item to the cache
   * @param exp expired time in second. 
   */
  public void update(K key, Object value, int exp);

  /**
   * Remove an item from the cache
   */
  public void remove(K key);

  /**
   * Clear the cache
   */
  public void clear();

  /**
   * Clean up
   */
  public void destroy();

  /**
   * Get a reasonable "lock timeout"
   */
  public int getTimeout();
}
