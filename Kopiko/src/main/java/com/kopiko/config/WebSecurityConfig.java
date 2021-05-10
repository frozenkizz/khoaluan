package com.kopiko.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.kopiko.security.AuthenticationProviderImpl;
import com.kopiko.security.UserDetailsServiceImpl;





@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, // bật tính năng cho annotable @PreAuthorize hoạt động
		securedEnabled = true, // bật tính năng cho annotaion @Secured hoạt động
		jsr250Enabled = true // bật ｔính năng cho @RoleAllowed annotation
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthenticationProviderImpl authenticationProvider;
	
	/*
	 * @Autowired AuthenticationSuccessHandler authenticationSuccessHandler;
	 */

	@Autowired
	DataSource dataSource;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// setting user cố định, mặc định sử dụng bean bCryptPasswordEncoder để encode
		// password
//		auth.inMemoryAuthentication().withUser("user1").password("user1").roles("USER").and().withUser("user2")
//				.password(passwordEncoder().encode("user2")).roles("USER") // cái này chạy được
//				.and().withUser("admin1").password(passwordEncoder().encode("admin1Pass")).roles("ADMIN");

		
		// Sét đặt dịch vụ để tìm kiếm User trong Database.
		// Và sét đặt PasswordEncoder.
	        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

		// Setting không xóa password sau khi xác thực
		// Nếu xóa thì password sẽ bằng null.
		auth.eraseCredentials(false); // default : true
	}

	/**
	 * / /welcome /login /logout /403 /userinfo ROLE_USER ROLE_ADMIN /admin
	 * ROLE_ADMIN
	 * 
	 * https://o7planning.org/vi/11705/tao-ung-dung-login-voi-spring-boot-spring-security-jpa#a13945296
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Cái csrf là cái middle gì đó nó được RUN khi extends
		// WebSecurityConfigurerAdapter
		// Cái này có tác dụng Ngăn chặn cái gì đó (hiện tại đang thấy nó đang ngăn chặn
		// method POST, không ngăn chặn GET)
		// đoạn code http.csrf().ignoringAntMatchers sẽ là từ bỏ việc ngăn chặn, tức là
		// cho nó hoạt động
		http.csrf().disable(); // TẮT lệnh này cũng bị lỗi 405
		// BẬT lệnh này bị lỗi 405
		// http.csrf().ignoringAntMatchers("/ajax/**");

		// Các trang không yêu cầu login
		http.authorizeRequests().antMatchers("/", "/login", "/logout", "/admin/login").permitAll();

		// Trang /userInfo yêu cầu phải login với vai trò ROLE_USER hoặc ROLE_ADMIN.
		// Nếu chưa login, nó sẽ redirect tới trang /login.
		http.authorizeRequests().antMatchers("/checkout/payment").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");
		http.authorizeRequests().antMatchers("/comment/**").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");
		http.authorizeRequests().antMatchers("/account/**").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");

		// Trang chỉ dành cho ADMIN
		http.authorizeRequests().antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')");
		http.authorizeRequests().antMatchers("/api/v1/admin/**").access("hasRole('ROLE_ADMIN')");

		// Khi người dùng đã login, với vai trò XX.
		// Nhưng truy cập vào trang yêu cầu vai trò YY,
		// Ngoại lệ AccessDeniedException sẽ ném ra.
		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

		// Cấu hình cho Login Form.
		http.authorizeRequests().and().formLogin()
				// Chỉ định Url sẽ được submit login
				// Khi submit URL này thì Security sẽ gọi Provider đã được setup ở
				// configureGlobal để xử lý check
				// .loginProcessingUrl("/j_spring_security_check") // Submit URL
				.loginProcessingUrl("/login-check") // Submit URL
				.loginPage("/login")//
				.defaultSuccessUrl("/home")//
				.failureUrl("/login?error=true")//
				.usernameParameter("username")//
				.passwordParameter("password");
//				.successHandler(authenticationSuccessHandler);

		// Cấu hình cho Logout Page.
		http.authorizeRequests().and().logout().logoutUrl("/logout").logoutSuccessUrl("/home");

		// Cấu hình Remember Me.
	        http.authorizeRequests().and() //
	                .rememberMe()
	                // .alwaysRemember(true) // default : false : ko remember, phải chọn remember
	                .rememberMeParameter("remember-me") // default : remember-me
	                .rememberMeCookieName("RememberMeApp") // default : remember-me
	                .tokenRepository(this.persistentTokenRepository()) //
	                .tokenValiditySeconds(1 * 24 * 60 * 60); // custom 24h // default : 2 weeks
	}

	// Token stored in Table (Persistent_Logins)
	// Table phải tự tạo, schema trong file readme.txt
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}

	// Token stored in Memory (Of Web Server).
//	    @Bean
//	    public PersistentTokenRepository persistentTokenRepository() {
//	        InMemoryTokenRepositoryImpl memory = new InMemoryTokenRepositoryImpl();
//	        return memory;
//	    }
}