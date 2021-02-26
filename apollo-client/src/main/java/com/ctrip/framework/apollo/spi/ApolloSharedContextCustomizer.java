package com.ctrip.framework.apollo.spi;

import com.ctrip.framework.apollo.shared.ApolloSharedContext;

/**
 * @author vdisk <vdisk@foxmail.com>
 */
public interface ApolloSharedContextCustomizer {

  /**
   * Performs the customizations on the ApolloSharedContext.
   *
   * @param sharedContext input ApolloSharedContext
   */
  void customize(ApolloSharedContext sharedContext);
}
