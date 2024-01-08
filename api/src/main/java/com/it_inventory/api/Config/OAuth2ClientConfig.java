package com.it_inventory.api.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

@Configuration
public class OAuth2ClientConfig {

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration clientRegistration = ClientRegistration.withRegistrationId("azure")
                .clientId("47880aa7-9724-4a44-8067-483612d7a582")
                .clientSecret("Lmf8Q~cXfsWc9mTi9XTaRrBnJKG7sKbHhzT4tdkd")
                .authorizationUri("https://login.microsoftonline.com/95b4977b-2e19-4499-8928-882caf67f448/oauth2/v2.0/authorize")
                .tokenUri("https://login.microsoftonline.com/95b4977b-2e19-4499-8928-882caf67f448/oauth2/v2.0/token")
                .userInfoUri("https://graph.microsoft.com/oidc/userinfo")
                .userNameAttributeName("id")
                .clientName("Azure")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
                .scope("openid", "profile", "email")
                .build();

        return new InMemoryClientRegistrationRepository(clientRegistration);
    }
}

