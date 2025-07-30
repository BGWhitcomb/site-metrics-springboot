package site.railcartracker.site_metrics.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// felt cute, might delete later
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("http://localhost:4200", "http://127.0.0.1:4200", "http://railcar-frontend:4200", "https://fw65k8sl-4200.usw2.devtunnels.ms"
				).allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
				.allowedHeaders("*").allowCredentials(true).maxAge(3600); // Cache preflight response for 1 hour
	}
}
