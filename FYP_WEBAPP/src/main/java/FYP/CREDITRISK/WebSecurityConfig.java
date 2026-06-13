/* Zhi Ting - main
 * Slency   - '/members/edit/*', '/members/delete/*',
 * 			  '/data/edit/*', 'data/delete/*',
 * 			  '/', '/bootstrap/**' */

package FYP.CREDITRISK;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public MemberDetailsService memberDetailsService() {
		return new MemberDetailsService();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(memberDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
				// Admin only
				.requestMatchers("/members", "/members/edit/*", "/members/delete/*",
								"/companies/edit/*", "/companies/delete/*",
								"/data", "/data/edit/*", "data/delete/*")
				.hasRole("ADMIN")
				// Public pages
				.requestMatchers("/", "/ourservice", "/aboutus", "/contactus",
								"/members/add", "/members/save",
								"/images/**", "/bootstrap/**", "/css/**", "/videos/*")
				.permitAll()
				// Any logged-in user can access these
				.anyRequest().authenticated())
				.formLogin((login) -> login.loginPage("/login").permitAll().defaultSuccessUrl("/ourservice"))
				.logout((logout) -> logout.logoutSuccessUrl("/"))
				.exceptionHandling((exceptionHandling) -> exceptionHandling.accessDeniedPage("/403"));
		return http.build();
	}
}