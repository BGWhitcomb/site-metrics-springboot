package site.railcartracker.site_metrics.controller;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class SecurityController {

    @GetMapping("/public")
    public String getPublicResource() {
        return "Anyone can access this.";
    }

    @GetMapping("/secure")
    public String getSecureResource(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaim("email");
        return "You are authenticated as: " + email;
    }
}