package com.auto.test.service.impl;

import com.auto.test.dao.TAutoModuleDao;
import com.auto.test.dao.TAutoUserDao;
import com.auto.test.dao.TRoleDao;
import com.auto.test.common.dto.LoginUser;
import com.auto.test.entity.TAutoModule;
import com.auto.test.entity.TAutoPermission;
import com.auto.test.entity.TAutoUser;
import com.auto.test.entity.TRole;
import com.auto.test.service.TAutoPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * spring security登陆处理<br>
 */
@Service
@Slf4j
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {
  
  @Autowired
  private TAutoPermissionService permissionService;
  @Autowired
  private TRoleDao roleDao;
  @Autowired
  private TAutoUserDao userDao;
  
  @Autowired
  private TAutoModuleDao autoModuleDao;
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    TAutoUser sysUser = userDao.getUser(username);
    if (sysUser == null) {
      throw new AuthenticationCredentialsNotFoundException("用户名不存在");
    }
//        else if (sysUser.getStatus() == Status.LOCKED) {
//            throw new LockedException("用户被锁定,请联系管理员");
//        } else if (sysUser.getStatus() == Status.DISABLED) {
//            throw new DisabledException("用户已作废");
//        }
    
    LoginUser loginUser = new LoginUser();
    BeanUtils.copyProperties(sysUser, loginUser);
    List<TAutoPermission> permissions;
    List<TAutoModule> projects;
    
    TRole role = roleDao.queryById(sysUser.getRoleId());
//        List<TRole> admin = list.stream().filter(role -> role.getName().equals("admin")).collect(Collectors.toList());
    if ("admin".equals(role.getName())) {
      log.debug("如果有admin角色，就有所有权限");
      permissions = permissionService.getPermissionAll();
      projects = autoModuleDao.queryAll(null);
    } else {
      permissions = permissionService.getPermissionByUserId(sysUser.getId());
      projects = autoModuleDao.selectAllByUserId(sysUser.getId());
    }
    loginUser.setPermissions(permissions);
    loginUser.setProjects(projects);
    return loginUser;
  }
  
}
