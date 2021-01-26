package com.ctrip.framework.apollo.portal.spi.oidc;

import com.ctrip.framework.apollo.core.utils.StringUtils;
import com.ctrip.framework.apollo.portal.entity.bo.UserInfo;
import com.ctrip.framework.apollo.portal.entity.po.UserPO;
import com.ctrip.framework.apollo.portal.repository.UserRepository;
import com.ctrip.framework.apollo.portal.spi.UserService;
import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author vdisk <vdisk@foxmail.com>
 * @date 2021-01-08 11:03
 */
public class OidcLocalUserService implements UserService {

  private final Collection<? extends GrantedAuthority> authorities = Collections
      .singletonList(new SimpleGrantedAuthority("ROLE_USER"));

  private final JdbcUserDetailsManager userDetailsManager;

  private final UserRepository userRepository;

  public OidcLocalUserService(
      JdbcUserDetailsManager userDetailsManager,
      UserRepository userRepository) {
    this.userDetailsManager = userDetailsManager;
    this.userRepository = userRepository;
  }

  @Transactional
  public void createOidcLocalUser(OidcUser oidcUser) {
    String userId = OidcLocalUserIdUtil.getUserId(oidcUser);
    UserDetails user = new User(userId, "ignore", authorities);
    userDetailsManager.createUser(user);
    UserPO managedUser = userRepository.findByUsername(userId);
    managedUser.setEmail(oidcUser.getEmail());
    userRepository.save(managedUser);
  }

  @Override
  public List<UserInfo> searchUsers(String keyword, int offset, int limit) {
    List<UserPO> users;
    if (StringUtils.isEmpty(keyword)) {
      users = userRepository.findFirst20ByEnabled(1);
    } else {
      users = userRepository.findByUsernameLikeAndEnabled("%" + keyword + "%", 1);
    }

    List<UserInfo> result = Lists.newArrayList();
    if (CollectionUtils.isEmpty(users)) {
      return result;
    }

    result.addAll(users.stream().map(UserPO::toUserInfo).collect(Collectors.toList()));

    return result;
  }

  @Override
  public UserInfo findByUserId(String userId) {
    UserPO userPO = userRepository.findByUsername(userId);
    return userPO == null ? null : userPO.toUserInfo();
  }

  @Override
  public List<UserInfo> findByUserIds(List<String> userIds) {
    List<UserPO> users = userRepository.findByUsernameIn(userIds);

    if (CollectionUtils.isEmpty(users)) {
      return Collections.emptyList();
    }

    List<UserInfo> result = Lists.newArrayList();

    result.addAll(users.stream().map(UserPO::toUserInfo).collect(Collectors.toList()));

    return result;
  }
}
