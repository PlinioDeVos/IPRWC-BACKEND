package nl.plinio.backend.endpoints.auth.cryptography;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomPasswordEncoder implements PasswordEncoder {
    @Value("${hash.argon2.parallelism}")
    private int parallelism;

    @Value("${hash.argon2.iterations}")
    private int iterations;

    @Value("${hash.argon2.memory}")
    private int memory;

    private final Argon2 argon2;

    public CustomPasswordEncoder() {
        this.argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
    }

    @Override
    public String encode(CharSequence rawPassword) {
        char[] chars = rawPassword.toString().toCharArray();
        String hashedPassword = argon2.hash(iterations, memory, parallelism, chars);
        argon2.wipeArray(chars);

        return hashedPassword;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        char[] chars = rawPassword.toString().toCharArray();
        boolean match = argon2.verify(encodedPassword, chars);
        argon2.wipeArray(chars);

        return match;
    }
}
