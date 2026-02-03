-- Authorization table
CREATE TABLE `authorization` (
                                 id VARCHAR(255) PRIMARY KEY,
                                 registered_client_id VARCHAR(255),
                                 principal_name VARCHAR(255),
                                 authorization_grant_type VARCHAR(255),
                                 authorized_scopes VARCHAR(1000),
                                 attributes VARCHAR(4000),
                                 state VARCHAR(500),
                                 authorization_code_value VARCHAR(4000),
                                 authorization_code_issued_at TIMESTAMP NULL,
                                 authorization_code_expires_at TIMESTAMP NULL,
                                 authorization_code_metadata VARCHAR(2000),
                                 access_token_value VARCHAR(4000),
                                 access_token_issued_at TIMESTAMP NULL,
                                 access_token_expires_at TIMESTAMP NULL,
                                 access_token_metadata VARCHAR(2000),
                                 access_token_type VARCHAR(255),
                                 access_token_scopes VARCHAR(1000),
                                 refresh_token_value VARCHAR(4000),
                                 refresh_token_issued_at TIMESTAMP NULL,
                                 refresh_token_expires_at TIMESTAMP NULL,
                                 refresh_token_metadata VARCHAR(2000),
                                 oidc_id_token_value VARCHAR(4000),
                                 oidc_id_token_issued_at TIMESTAMP NULL,
                                 oidc_id_token_expires_at TIMESTAMP NULL,
                                 oidc_id_token_metadata VARCHAR(2000),
                                 oidc_id_token_claims VARCHAR(2000),
                                 user_code_value VARCHAR(4000),
                                 user_code_issued_at TIMESTAMP NULL,
                                 user_code_expires_at TIMESTAMP NULL,
                                 user_code_metadata VARCHAR(2000),
                                 device_code_value VARCHAR(4000),
                                 device_code_issued_at TIMESTAMP NULL,
                                 device_code_expires_at TIMESTAMP NULL,
                                 device_code_metadata VARCHAR(2000)
);

-- Authorization consent table
CREATE TABLE `authorizationConsent` (
                                        registered_client_id VARCHAR(255) NOT NULL,
                                        principal_name VARCHAR(255) NOT NULL,
                                        authorities VARCHAR(1000),
                                        PRIMARY KEY (registered_client_id, principal_name)
);

-- Client table
CREATE TABLE client (
                        id VARCHAR(255) PRIMARY KEY,
                        client_id VARCHAR(255),
                        client_id_issued_at TIMESTAMP NULL,
                        client_secret VARCHAR(255),
                        client_secret_expires_at TIMESTAMP NULL,
                        client_name VARCHAR(255),
                        client_authentication_methods VARCHAR(1000),
                        authorization_grant_types VARCHAR(1000),
                        redirect_uris VARCHAR(1000),
                        post_logout_redirect_uris VARCHAR(1000),
                        scopes VARCHAR(1000),
                        client_settings VARCHAR(2000),
                        token_settings VARCHAR(2000)
);
