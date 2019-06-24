package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import javax.annotation.Resource;
import javax.sql.DataSource;

@SpringBootApplication
public class Springboot0623Application extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource = null;

//    // 使用用户名称查询密码
//    String pwdQuery = " select user_name, pwd, available "
//                    + " from t_user where user_name = ?";
//    // 使用用户名称查询角色信息
//    String roleQuery = " select u.user_name, r.role_name  "
//                    + " from t_user u, t_user_role ur, t_role r  "
//                      + "where u.id = ur.user_id and r.id = ur.role_id" + " and u.user_name = ?";
//
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        auth.jdbcAuthentication()
//                .passwordEncoder(passwordEncoder)
//                .dataSource(dataSource)
//                .usersByUsernameQuery(pwdQuery)
//                .authoritiesByUsernameQuery(roleQuery);
//    }

    //注入配置的钥匙
    @Value("${system.user.password.secret}")
    private String secret = null;

    @Resource
    private UserDetailsService userDetailsService = null;

    //使用自定义的 UserDetailsService
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder(secret);
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }



    //12-4-1使用Abt风格配置限定请求路径访问权限
//    protected void configure(HttpSecurity http) throws Exception {
//        //限定签名后的权限
//        http
//                    /* ########第一段######## */
//                .authorizeRequests()
//                     // 限定"/user/welcome"请求赋予角色ROLE_USER或者ROLE_ADMIN
//                .antMatchers("/user/welcome","/user/details").hasAnyRole("USER","ADMIN")
//                      // 限定"/admin/"下所有请求权限赋予角色ROLE_ADMIN
//                .antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")
//                       // 其他路径允许签名后访问
//                .anyRequest().permitAll()
//                        /* ######## 第二段 ######## */
//                        /** and代表连接词 **/
//                      // 对于没有配置权限的其他请求允许匿名访问
//                .and().formLogin()
//                       // /* ######## 第三段 ######## */
//                       // // 使用Spring Security默认的登录页面
//                .and().anonymous()
//                    // 启动HTTP基础验证
//                .and().httpBasic();
//    }


    //12-4-2 使用spring表达式配置访问权限
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                      // 使用Spring表达式限定只有角色ROLE_USER或者ROLE_ADMIN
//                .antMatchers("/user/**").access("hasRole('USER') or hasRole('ADMIN')" )
//                      // 设置访问权限给角色ROLE_ADMIN，要求是完整登录(非记住我登录)
//                .antMatchers("/admin/welcome")
//                .access("hasRole('ROLE_ADMIN') && isFullyAuthenticated()")
//                       // 限定"/admin/welcome2"访问权限给角色ROLE_ADMIN，允许不完整登录
//                .antMatchers("/admin/welcome2").access("hasAnyAuthority('ROLE_ADMIN')")
//                       // 使用记住我的功能
//                .and().rememberMe()
//                       // 使用Spring Security默认的登录页面
//                .and().formLogin()
//                       // 启动HTTP基础验证
//                .and().httpBasic();
//    }


//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                /**
//                 * 具体就是框架内部防止CSRF（Cross-site request forgery跨站请求伪造）的发生，
//                 * 限制了除了get以外的大多数方法。
//                 */
//                .csrf().disable()//关闭csrf认证（不建议关闭，此处不不关闭测试时出现异常，原因不详，所以此处先关闭）
//                .authorizeRequests().antMatchers("/admin/**").access("hasRole('ADMIN')")
//                .and().rememberMe().tokenValiditySeconds(86400).key("remember-me-key")
//                .and().httpBasic()
//                .and().authorizeRequests().antMatchers("/**").permitAll()
//                .and().formLogin().loginPage("/login/page")
//                .defaultSuccessUrl("/admin/welcome1");
//    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                      // 访问/admin下的请求需要管理员权限
                .authorizeRequests().antMatchers("/admin/**")
                .access("hasRole('ADMIN')")
                      // 通过签名后可以访问任何请求
                .and().authorizeRequests()
                .antMatchers("/**").permitAll()
                      // 设置登录页和默认的跳转路径
                .and().formLogin().loginPage("/login/page")
                .defaultSuccessUrl("/admin/welcome1")
                       // 登出页面和默认跳转路径
                .and().logout().logoutUrl("/logout/page")
                .logoutSuccessUrl("/welcome");
    }

    public static void main(String[] args) {
        SpringApplication.run(Springboot0623Application.class, args);
         PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder("uvwxyz");
         String p1 = passwordEncoder.encode("111");
         String p2 = passwordEncoder.encode("222");
         System.out.println("=====p1==== "+p1);
         System.out.println("=====p2==== "+p2);

    }

}
