package authservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
   public static final String SECRET = "357638792F423F4428472B4B6250655368566D597133743677397A2443264629";

   public String extractUsername(String token){
      return extractClaim(token, Claims::getSubject);
   }

   public <T> T extractClaim(String token, Function<Claims, T> claimResolver){
      final Claims claims =  extractAllClaims(token);
      return claimResolver.apply(claims);
   }

   public Date extractExpiration(String token){
      return extractClaim(token, Claims::getExpiration);
   }

   private Boolean isTokenExpired(String token){
      return extractExpiration(token).before(new Date());
   }

   public Boolean validateToken(String token, UserDetails userDetails){
      final String userName = extractUsername(token);
      return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
   public String GenerateToken(String username){
      Map<String, Object> claims = new HashMap<>();
      return createToken(claims, username);
   }
    private String createToken(Map<String, Object> claims, String username){
      return Jwts.builder()
              .setClaims(claims)
              .setSubject(username)
              .setIssuedAt(new Date(System.currentTimeMillis()))
              .setExpiration(new Date(System.currentTimeMillis()+1000*60*1))
              .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }


   // .build() -> is from @Builder which does chaining of username and password
   private Claims extractAllClaims(String token){
      return Jwts
              .parser()
              .setSigningKey(getSignKey())
              .build()
              .parseClaimsJws(token)
              .getBody();
   }
   private Key getSignKey(){
      byte[] keyBytes = Decoders.BASE64.decode(SECRET);
      return Keys.hmacShaKeyFor(keyBytes);

   }
}
