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



    //
    protected void configure(HttpSecurity http) throws Exception {
        //限定签名后的权限
        http
                    /* ########第一段######## */
                .authorizeRequests()
                     // 限定"/user/welcome"请求赋予角色ROLE_USER或者ROLE_ADMIN
                .antMatchers("/user/welcome","/user/details").hasAnyRole("USER","ADMIN")
                      // 限定"/admin/"下所有请求权限赋予角色ROLE_ADMIN
                .antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")
                       // 其他路径允许签名后访问
                .anyRequest().permitAll()
                        /* ######## 第二段 ######## */
                        /** and代表连接词 **/
                      // 对于没有配置权限的其他请求允许匿名访问
                .and().formLogin()
                       // /* ######## 第三段 ######## */
                       // // 使用Spring Security默认的登录页面
                .and().anonymous()
                    // 启动HTTP基础验证
                .and().httpBasic();
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
