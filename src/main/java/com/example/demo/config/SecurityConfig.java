package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().requestMatchers("/api/**").permitAll() // Permitir acesso sem login
																							// para APIs
				.requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll() // Permitir acesso sem login
																							// para login e arquivos
																							// estáticos
				.requestMatchers("/admin/**").hasRole("ADMIN").requestMatchers("/user/**").hasRole("USER").anyRequest()
				.authenticated().and().formLogin().loginPage("/login").defaultSuccessUrl("/dashboard", true).permitAll()
				.and().logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll();
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails admin = User.withUsername("admin").password(passwordEncoder().encode("admin123")).roles("ADMIN")
				.build();

		UserDetails user = User.withUsername("user").password(passwordEncoder().encode("user123")).roles("USER")
				.build();

		return new InMemoryUserDetailsManager(admin, user);
	}
}
