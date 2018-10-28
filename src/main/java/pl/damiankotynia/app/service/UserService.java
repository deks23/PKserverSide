package pl.damiankotynia.app.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.damiankotynia.app.controller.UserController;
import pl.damiankotynia.app.exceptions.LoginFailedException;
import pl.damiankotynia.app.exceptions.UserExistsException;
import pl.damiankotynia.app.exceptions.UserNotFoundException;
import pl.damiankotynia.app.model.User;
import pl.damiankotynia.app.repository.UserRepository;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    public boolean register(String nick, String password) throws UserExistsException{
        if(!userRepository.findByName(nick).isPresent()){
            User user = new User();
            user.setName(nick);
            user.setPasswordHash(bCryptPasswordEncoder.encode(password));
            userRepository.save(user);
            logger.info("User "+ nick + " registred");
        }else{
            logger.error("User "+ nick + " alredy exists");
            throw new UserExistsException();
        }

        return true;
    }

    public String login(String user, String password) throws UserNotFoundException, LoginFailedException{
        Optional<User> userOpt =  userRepository.findByName(user);
        if(!userOpt.isPresent()){
            throw new UserNotFoundException();
        }
        User u1 = userOpt.get();
        return createJWT(u1.getId(), u1.getName(), password, 1000000L);
    }

    public Claims parseJWT(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET))
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

    private String createJWT(Long id, String issuer, String password, long ttlMillis)  throws LoginFailedException{


        User user = userRepository.findById(id).get();
        if(!bCryptPasswordEncoder.matches(password, user.getPasswordHash()))
            throw new LoginFailedException();

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id.toString())
                .setIssuedAt(now)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

      /*
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }*/

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    Logger logger = LoggerFactory.getLogger(UserService.class);

    private final String SECRET = "hasloDOodkodowaniaJWT";
}
