package com.ctrip.framework.apollo.spi;

import com.ctrip.framework.apollo.core.spi.Ordered;
import com.ctrip.framework.apollo.util.http.HttpUtil;

/**
 * @author vdisk <vdisk@foxmail.com>
 */
public interface HttpClientFactory extends Ordered {

  /**
   * create HttpClient
   *
   * @return instance of HttpUtil
   */
  HttpUtil createHttpClient();
}
