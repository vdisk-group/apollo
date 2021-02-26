package com.ctrip.framework.apollo.shared;

/**
 * @author vdisk <vdisk@foxmail.com>
 */
public interface ApolloSharedContext {

  /**
   * Sets an object that is shared by apollo client {@link com.ctrip.framework.apollo.internals.DefaultInjector}.
   *
   * @param sharedType the Class to key the shared object by.
   * @param object     the Object to store
   */
  <C> void setSharedObject(Class<C> sharedType, C object);

  /**
   * Gets a shared Object. Note that object heirarchies are not considered.
   *
   * @param sharedType the type of the shared Object
   * @return the shared Object or null if it is not found
   */
  <C> C getSharedObject(Class<C> sharedType);
}
