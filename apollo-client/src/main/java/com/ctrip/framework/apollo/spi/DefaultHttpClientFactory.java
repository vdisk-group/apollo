package com.ctrip.framework.apollo.spi;

import com.ctrip.framework.apollo.core.spi.Ordered;
import com.ctrip.framework.apollo.util.http.DefaultHttpUtil;
import com.ctrip.framework.apollo.util.http.HttpUtil;

/**
 * @author vdisk <vdisk@foxmail.com>
 */
public class DefaultHttpClientFactory implements HttpClientFactory {

  @Override
  public HttpUtil createHttpClient() {
    return new DefaultHttpUtil();
  }

  @Override
  public int getOrder() {
    return Ordered.LOWEST_PRECEDENCE;
  }
}
