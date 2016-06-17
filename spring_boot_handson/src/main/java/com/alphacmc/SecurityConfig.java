/**
 * 
 */
package com.alphacmc;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * セキュリティコンフィグクラス
 * @author matsumoto
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/* (non-Javadoc)
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder)
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		UserDetailsManagerConfigurer configurer = new InMemoryUserDetailsManagerConfigurer();
		configurer.withUser("hoge").password("hoge").roles("USER");
		configurer.withUser("admin").password("demo").roles("ADMIN");
		configurer.configure(auth);
		UserDetailsService userDetailsService = configurer.getUserDetailsService();

		auth.userDetailsService(userDetailsService);
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.WebSecurity)
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/js/**", "/image/**", "/api/**");
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/loginForm")
		.permitAll()
		.anyRequest()
		.authenticated();
		http.formLogin()
		.loginProcessingUrl("/login")
		.loginPage("/loginForm")
		.failureUrl("/loginForm?error")
		.defaultSuccessUrl("/book/list")
		.usernameParameter("username")
		.passwordParameter("password")
		.permitAll();
	}

}
