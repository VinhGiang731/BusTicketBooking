package com.project.busticket.service;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.project.busticket.dto.request.user.AuthenticationRequest;
import com.project.busticket.dto.request.user.IntrospectRequest;
import com.project.busticket.dto.response.AuthenticationResponse;
import com.project.busticket.dto.response.IntrospectResponse;
import com.project.busticket.entity.Users;
import com.project.busticket.exception.Appexception;
import com.project.busticket.exception.ErrorCode;
import com.project.busticket.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        var userList = userRepository.findByUserName(request.getUserName());
        if (userList.isEmpty())
            throw new Appexception(ErrorCode.USER_NOT_EXISTED);

        var user = userList.get(0);

        boolean authenticate = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticate)
            throw new Appexception(ErrorCode.UN_AUTHENTICATED);

        var token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    private String generateToken(Users user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUserName())
                .issuer("bus-ticket")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY));
            return jwsObject.serialize();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String buildScope(Users user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRole()))
            user.getRole().forEach(stringJoiner::add);

        return stringJoiner.toString();
    }

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date exprityTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        boolean exprityTimeState = exprityTime.after(new Date());

        var verified = signedJWT.verify(verifier);

        String scope = null;
        if (verified && exprityTimeState) {
            scope = (String) signedJWT.getJWTClaimsSet().getClaim("scope");
        }

        return IntrospectResponse.builder()
                .valid(verified && exprityTimeState)
                .scope(scope)
                .build();
    }
}
