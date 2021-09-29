package com.admin.nacosprovide.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  /**
   * 匹配 "/" 路径，不需要权限即可访问
   * 匹配 "/user" 及其以下所有路径，都需要 "USER" 权限
   * 登录地址为 "/login"，登录成功默认跳转到页面 "/user"
   * 退出登录的地址为 "/logout"，退出成功后跳转到页面 "/login"
   * 默认启用 CSRF这是跨域设置
   */
  /**
   * 注入 自定义的  登录成功处理类
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http
        .authorizeRequests()
        .antMatchers("/").permitAll()
        .antMatchers("/user/**").hasAuthority("USER")
        .antMatchers("/login").permitAll()
        .antMatchers("/logout").permitAll()
        .and()
        .formLogin()
        .loginPage("/login")
        .loginProcessingUrl("/loginUser")
        .successForwardUrl("/user")
        .and()
        .logout().logoutUrl("/logout").logoutSuccessUrl("/login");
//                .and()
//                .csrf().disable();
    //这里不用自定义的认证过滤
    //http.addFilterAt(customFromLoginFilter(), UsernamePasswordAuthenticationFilter.class);

  }

  @Override
  public void configure(WebSecurity web) {

    web.ignoring().antMatchers();

  }


}
