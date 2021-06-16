package com.example.demo.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
public class OAuthConfig extends AuthorizationServerConfigurerAdapter {
    private String clientid = "test";
    private String clientSecret = "$2y$10$VNMB94SihaC/xvG8CRcgHuGiD4enltip7sYfKA8Aezj8HEW3dwALq";
    private String privateKey = "private key"; //  "client-secret"
    private String publicKey = "public key";

    @Autowired
    //@Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public JwtAccessTokenConverter tokenEnhancer() throws Exception {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //converter.setSigningKey(privateKey);
        //converter.setVerifierKey(publicKey);

        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("token.jks"),
                        "secret_pass".toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("adw"));
        //converter.setAccessTokenConverter(converter);
        converter.afterPropertiesSet();
        return converter;
    }
    @Bean
    public JwtTokenStore tokenStore() throws Exception {
        return new JwtTokenStore(tokenEnhancer());
    }
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore())
                .accessTokenConverter(tokenEnhancer())
                .userDetailsService(userDetailsService);
    }
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(clientid)
                .secret(clientSecret)
                .scopes("read", "write")
                .authorizedGrantTypes("password", "refresh_token")
                .accessTokenValiditySeconds(10000)
                .refreshTokenValiditySeconds(20000);

    }
}
