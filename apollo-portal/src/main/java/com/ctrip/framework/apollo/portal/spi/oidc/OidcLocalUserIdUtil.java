package com.ctrip.framework.apollo.portal.spi.oidc;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;

/**
 * @author vdisk <vdisk@foxmail.com>
 * @date 2021-01-08 11:22
 */
public class OidcLocalUserIdUtil {

  public static String getUserId(OidcUser oidcUser) {
    return oidcUser.getIssuer().toString() + "/" + oidcUser.getSubject();
  }
}
