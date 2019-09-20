package com.cmc.dashboard.security.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.cmc.dashboard.security.CustomUserDetailsService;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
    private AuthenticationManager authenticationManager;
// 
//    @Autowired
//	private TokenStore tokenStore;
//    
//    @Autowired
//	private JwtAccessTokenConverter jwtAccessTokenConverter;
    
    @Autowired
	private CustomUserDetailsService userDetailsService;

    @Value("${app.jwt.signingKey}")
	private String signinKey;
    
//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
//        oauthServer.tokenKeyAccess("permitAll()")
//          .checkTokenAccess("isAuthenticated()");
//    }
 
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    	clients.inMemory()
    	//User Basic Authen
		.withClient("dashboard") // Username Basic Auth
		.secret("abc@1234!2354%")//Password Basic Auth
		.authorizedGrantTypes("password", "refresh_token")
		.authorities("USER","DUL","QA", "PM","BOD").scopes("read", "write")
		.resourceIds("restservice")
                // set a zero or negative value for the access token validity to the token to expire
		.accessTokenValiditySeconds(0);
    }
 
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints.authenticationManager(authenticationManager);
    	endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager)
		.accessTokenConverter(jwtAccessTokenConverter()).userDetailsService(userDetailsService);
    }
    
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        final JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(signinKey);
        return jwtAccessTokenConverter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }
    
    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }
}
