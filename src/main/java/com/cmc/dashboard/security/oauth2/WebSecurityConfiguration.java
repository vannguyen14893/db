package com.cmc.dashboard.security.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cmc.dashboard.security.CorsFilterRequest;
import com.cmc.dashboard.security.CustomUserDetailsService;
import com.cmc.dashboard.security.UserAuthenticationProvider;

/**
 * 
 * @author longl
 *
 */
//@Configuration
//@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
    private CustomUserDetailsService userDetailsService;
//
    @Autowired
    private UserAuthenticationProvider accountAuthenticationProvider;
//
//	@Autowired
//	private AuthenticationManager authenticationManager;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/scss/**", "/js/**", "/images/**",
				"/assets/**","**/projects/**","**/resource/**");
	}
	 @Bean
     public CorsFilterRequest CorsFilterRequest() throws Exception {
		 CorsFilterRequest jwtAuthenticationTokenFilter = new CorsFilterRequest();
             jwtAuthenticationTokenFilter.setAuthenticationManager(authenticationManager());
             return jwtAuthenticationTokenFilter;
     }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
			.disable()
			.antMatcher("/api/**")
			.authorizeRequests()
			.antMatchers("oauth/token", "/webjars/**")
			.permitAll()
			.antMatchers(HttpMethod.OPTIONS).permitAll()
			.anyRequest()
			.authenticated();
		 http.cors();
		 http.addFilterBefore(CorsFilterRequest(), UsernamePasswordAuthenticationFilter.class);
         http.headers().cacheControl();

	}
	@Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
            return super.authenticationManager();
    }

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(accountAuthenticationProvider);
//        auth.parentAuthenticationManager(authenticationManager);
    }

}
