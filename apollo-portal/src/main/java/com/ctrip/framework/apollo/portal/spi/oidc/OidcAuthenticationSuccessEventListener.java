package com.ctrip.framework.apollo.portal.spi.oidc;

import com.ctrip.framework.apollo.portal.entity.bo.UserInfo;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

/**
 * @author vdisk <vdisk@foxmail.com>
 * @date 2021-01-08 11:19
 */
public class OidcAuthenticationSuccessEventListener implements
    ApplicationListener<AuthenticationSuccessEvent> {

  private final OidcLocalUserService oidcLocalUserService;

  public OidcAuthenticationSuccessEventListener(
      OidcLocalUserService oidcLocalUserService) {
    this.oidcLocalUserService = oidcLocalUserService;
  }

  @Override
  public void onApplicationEvent(AuthenticationSuccessEvent authenticationSuccessEvent) {
    Object principal = authenticationSuccessEvent.getAuthentication().getPrincipal();
    if (!(principal instanceof OidcUser)) {
      throw new RuntimeException("cannot get user info from principal[" + principal + "]");
    }
    OidcUser oidcUser = (OidcUser) principal;
    UserInfo userInfo = oidcLocalUserService.findByUserId(OidcLocalUserIdUtil.getUserId(oidcUser));
    if (userInfo != null) {
      return;
    }
    oidcLocalUserService.createOidcLocalUser(oidcUser);
  }
}
