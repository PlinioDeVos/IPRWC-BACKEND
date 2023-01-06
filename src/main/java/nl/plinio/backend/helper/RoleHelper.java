package nl.plinio.backend.helper;

import jakarta.servlet.http.HttpServletRequest;
import nl.plinio.backend.endpoints.account.model.Role;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Locale;
import java.util.Objects;

@Component
public class RoleHelper {
    public Role getRole() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(
                RequestContextHolder.getRequestAttributes())).getRequest();

        for (Role role : Role.values()) {
            if (request.isUserInRole(role.value.toUpperCase(Locale.ROOT))) {
                return role;
            }
        }

        return null;
    }
}
