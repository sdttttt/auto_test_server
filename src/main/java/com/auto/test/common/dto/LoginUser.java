package com.auto.test.common.dto;

import com.auto.test.entity.TAutoModule;
import com.auto.test.entity.TAutoPermission;
import com.auto.test.entity.TAutoUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Data
public class LoginUser extends TAutoUser implements UserDetails {
  
  private static final long serialVersionUID = -1379274258881257107L;
  
  private List<TAutoPermission> permissions;
  
  private List<TAutoModule> projects;
  
  private String token;
  /**
   * 登陆时间戳（毫秒）
   */
  private Long loginTime;
  /**
   * 过期时间戳
   */
  private Long expireTime;
  
  @Override
  @JsonIgnore
  public Collection<? extends GrantedAuthority> getAuthorities() {
//        Collection<SimpleGrantedAuthority> collect = permissions.parallelStream().filter(p -> !StringUtils.isEmpty(p.getPermission()))
//                .map(p -> new SimpleGrantedAuthority(p.getPermission())).collect(Collectors.toSet());
    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    setAuth(permissions, authorities);
    log.info("获取指令权限=====" + this.getId() + authorities);
    return authorities;
  }
  
  @Override
  public boolean isAccountNonExpired() {
    return false;
  }
  
  @Override
  public boolean isAccountNonLocked() {
    return false;
  }
  
  private void setAuth(List<TAutoPermission> list, Collection<SimpleGrantedAuthority> a) {
//    List<TAutoPermission> list = p.parallelStream().filter(b -> !StringUtils.isEmpty(b.getTAutoPermission())).collect(Collectors.toList());
    list.parallelStream().forEach(c -> {
          a.add(new SimpleGrantedAuthority(c.getId()));
          List<TAutoPermission> child = c.getChildren();
          if (!CollectionUtils.isEmpty(child)) {
            setAuth(child, a);
          }
          
        }
    );
  }
  
  public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
    // do nothing
  }
  
  // 密码是否未过期
  @JsonIgnore
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }
  
  // 账户是否激活
  @JsonIgnore
  @Override
  public boolean isEnabled() {
    return true;
  }
  
}
