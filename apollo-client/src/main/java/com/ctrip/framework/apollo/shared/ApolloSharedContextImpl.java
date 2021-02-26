package com.ctrip.framework.apollo.shared;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author vdisk <vdisk@foxmail.com>
 */
public class ApolloSharedContextImpl implements ApolloSharedContext {

  /**
   * shared Objects
   */
  private final ConcurrentMap<Class<?>, Object> sharedObjects = new ConcurrentHashMap<>();

  @Override
  public <C> void setSharedObject(Class<C> sharedType, C object) {
    this.sharedObjects.put(sharedType, object);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <C> C getSharedObject(Class<C> sharedType) {
    return (C) this.sharedObjects.get(sharedType);
  }
}
