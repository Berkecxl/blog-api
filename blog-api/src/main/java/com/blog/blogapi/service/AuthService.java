package com.blog.blogapi.service;

import com.blog.blogapi.application.login.LoginRequest;
import com.blog.blogapi.application.login.LoginResult;
import com.blog.blogapi.application.login.LoginResultAssembler;
import com.blog.blogapi.application.register.RegisterRequest;
import com.blog.blogapi.application.register.RegisterResult;
import com.blog.blogapi.application.register.RegisterResultAssembler;
import com.blog.blogapi.data.UsersRepository;
import com.blog.blogapi.domain.assembler.UserAssembler;
import com.blog.blogapi.domain.model.User;
import com.blog.blogapi.validate.AuthValidator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UsersRepository usersRepository;
    private final AuthValidator authValidator;
    private final RegisterResultAssembler registerResultAssembler;
    private final UserAssembler userAssembler;
    private final LoginResultAssembler loginResultAssembler;

    public RegisterResult register(RegisterRequest request) {
        log.info("Register started with Username: {}", request.getUsername());

        try {
            if (usersRepository.existsByUsername(request.getUsername()) || usersRepository.existsByEmail(request.getEmail())) {
                return registerResultAssembler.applyUserExistResult();
            }

            String validateMessage = authValidator.validateRegister(request);
            if (Objects.nonNull(validateMessage)) {
                return registerResultAssembler.applyNotValidResult(validateMessage);
            }

            String encodedPassword = encodePassword(request.getPassword());
            User user = userAssembler.applyUserWithRegisterRequest(request, encodedPassword);
            usersRepository.save(user);

            log.info("Register successfull with Username: {}", request.getUsername());
            return registerResultAssembler.applySuccessResult();
        } catch (Exception ex) {
            log.error("Unexpected error got register with username: {}", request.getUsername());
            throw new RuntimeException(ex);
        }
    }

    public LoginResult login(LoginRequest request) {
        log.info("Login started with username: {}", request.getUsername());

        try {
            User user = usersRepository.findByUsername(request.getUsername()).orElse(null);

            if (Objects.isNull(user) || !checkPassword(request.getPassword(), user.getPassword())) {
                return loginResultAssembler.applyFailureResult("Girilen bilgiler hatalı!");
            }

            if (!user.isActive()) {
                return loginResultAssembler.applyFailureResult("Hesap aktif değil!");
            }

            String jwtToken = generateJwtToken(user.getUsername());

            log.info("Login successfull with Username: {}", request.getUsername());
            return loginResultAssembler.applySuccessResult(jwtToken);
        } catch (Exception ex) {
            log.error("Unexpected error got login with username: {}", request.getUsername());
            throw new RuntimeException(ex);
        }
    }

    private static String encodePassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    private static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    private String generateJwtToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS512))
                .compact();
    }
}
