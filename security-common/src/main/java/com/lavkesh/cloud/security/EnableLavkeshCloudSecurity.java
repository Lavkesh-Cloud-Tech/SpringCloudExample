package com.lavkesh.cloud.security;

import com.lavkesh.cloud.security.config.AuthenticationConfig;
import com.lavkesh.cloud.security.config.CloudSecurityConfiguaration;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * Created by RATHIL on 7/21/2017.
 */
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = { java.lang.annotation.ElementType.TYPE })
@Documented
@Import({ WebSecurityConfiguaration.class, CloudSecurityConfiguaration.class})
@EnableConfigurationProperties({AuthenticationConfig.class})
@EnableWebSecurity
@Configuration
public @interface EnableLavkeshCloudSecurity {

}
