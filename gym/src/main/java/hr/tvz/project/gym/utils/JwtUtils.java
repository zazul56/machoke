package hr.tvz.project.gym.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.InvalidKeyException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Component
public class JwtUtils {

    private static final long EXPIRATION_TIME = 864000000; // Token valid for 10 days
    
    // this is just for development
    private String encodedSecretKey = "BeRyqu5rGeNH6Vh78PYRSb60XxrEEmrvU9pcz+wEtK4=";
    
    private SecretKey secretKey;
    
    public JwtUtils() {
    	byte[] decodedKey = Base64.getDecoder().decode(encodedSecretKey);
		SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA256");
		
		this.secretKey = originalKey;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
    	JwtParserBuilder parserBuilder = Jwts.parser().verifyWith(secretKey);
        return parserBuilder.build().parseSignedClaims(token).getPayload();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
    	
    	String token = null;
		try {
	        token = Jwts.builder()
	                .subject(userDetails.getUsername())
	                .issuedAt(new Date())
	                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
	                .signWith(secretKey)
	                .compact();
		
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}

        return token;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
