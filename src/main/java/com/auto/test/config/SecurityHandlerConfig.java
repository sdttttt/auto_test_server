package com.auto.test.config;

import com.auto.test.common.dto.ErrorInfo;
import com.auto.test.common.dto.LoginUser;
import com.auto.test.common.dto.ResponseInfo;
import com.auto.test.common.dto.Token;
import com.auto.test.service.TAutoTokenService;
import com.auto.test.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * spring security处理器
 */
@Configuration
public class SecurityHandlerConfig {
  
  @Autowired
  private TAutoTokenService tokenService;
  
  /**
   * 登陆成功，返回Token
   *
   * @return
   */
  @Bean
  public AuthenticationSuccessHandler loginSuccessHandler() {
    return new AuthenticationSuccessHandler() {
      @Override
      public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        
        Token token = tokenService.saveToken(loginUser);
        ResponseUtil.responseJson(response, HttpStatus.OK.value(), new ResponseInfo(true, token));
      }
    };
  }
  
  /**
   * 登陆失败
   *
   * @return
   */
  @Bean
  public AuthenticationFailureHandler loginFailureHandler() {
    return new AuthenticationFailureHandler() {
      @Override
      public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                          AuthenticationException exception) throws IOException, ServletException {
        String msg = null;
        if (exception instanceof BadCredentialsException) {
          msg = "用户名或密码错误";
        } else {
          msg = exception.getMessage();
        }
        ResponseInfo info = new ResponseInfo(false, new ErrorInfo(HttpStatus.UNAUTHORIZED.value(), msg));
        ResponseUtil.responseJson(response, HttpStatus.OK.value(), info);
      }
    };
  }
  
  /**
   * 未登录，返回401
   *
   * @return
   */
  @Bean
  public AuthenticationEntryPoint authenticationEntryPoint() {
    return new AuthenticationEntryPoint() {
      @Override
      public void commence(HttpServletRequest request, HttpServletResponse response,
                           AuthenticationException authException) throws IOException, ServletException {
        ResponseInfo info = new ResponseInfo(false, new ErrorInfo(HttpStatus.UNAUTHORIZED.value(), "未登录或登陆失效，请先登录"));
        ResponseUtil.responseJson(response, HttpStatus.OK.value(), info);
      }
    };
  }
  
  /**
   * 退出处理
   *
   * @return
   */
  @Bean
  public LogoutSuccessHandler logoutSussHandler() {
    return new LogoutSuccessHandler() {
      @Override
      public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ResponseInfo info = new ResponseInfo(true, "退出成功");
        String token = TokenFilter.getToken(request);
        tokenService.deleteToken(token);
        ResponseUtil.responseJson(response, HttpStatus.OK.value(), info);
      }
    };
    
  }
  
}
