package com.ctrip.framework.apollo.portal.spi.oidc;

import com.ctrip.framework.apollo.portal.entity.bo.UserInfo;
import com.ctrip.framework.apollo.portal.spi.UserInfoHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

/**
 * @author vdisk <vdisk@foxmail.com>
 * @date 2021-01-08 11:03
 */
public class OidcUserInfoHolder implements UserInfoHolder {

  @Override
  public UserInfo getUser() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (!(principal instanceof OidcUser)) {
      throw new RuntimeException("cannot get user info from principal[" + principal + "]");
    }
    OidcUser oidcUser = (OidcUser) principal;
    UserInfo userInfo = new UserInfo();
    userInfo.setUserId(OidcLocalUserIdUtil.getUserId(oidcUser));
    userInfo.setName(oidcUser.getNickName());
    userInfo.setEmail(oidcUser.getEmail());
    return userInfo;
  }
}
