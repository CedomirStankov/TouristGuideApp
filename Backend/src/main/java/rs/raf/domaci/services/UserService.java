package rs.raf.domaci.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.codec.digest.DigestUtils;
import rs.raf.domaci.entities.Korisnik;
import rs.raf.domaci.repositories.user.UserRepository;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

public class UserService {

    @Inject
    UserRepository userRepository;

    public String login(String email, String password)
    {
        String hashedPassword = DigestUtils.sha256Hex(password);

        Korisnik korisnik = this.userRepository.findKorisnik(email);
        if (korisnik == null || !korisnik.getLozinka().equals(hashedPassword)) {
            return null;
        }

        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + 24*60*60*1000); // One day

        Algorithm algorithm = Algorithm.HMAC256("secret");

        // JWT-om mozete bezbedono poslati informacije na FE
        // Tako sto sve sto zelite da posaljete zapakujete u claims mapu
        return JWT.create()
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withSubject(email)
                .withClaim("tipKorisnika",korisnik.getTipKorisnika())
                .withClaim("status",korisnik.getStatus())
                .sign(algorithm);
    }

    public boolean isAuthorized(String token){
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);

        String email = jwt.getSubject();
        //jwt.getClaim("role").asString();

        Korisnik korisnik = this.userRepository.findKorisnik(email);

        if (korisnik == null){
            return false;
        }

        return true;
    }

    public boolean isAdmin(String token) {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);

        String tipKorisnika = jwt.getClaim("tipKorisnika").asString();
        return "admin".equals(tipKorisnika);
    }

    public boolean isKorisnik(String token) {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);

        String tipKorisnika = jwt.getClaim("tipKorisnika").asString();
        return "korisnik".equals(tipKorisnika);
    }

    public boolean isActive(String token){
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);

        Integer status = jwt.getClaim("status").asInt();
        return 1==status;
    }
    public Korisnik addKorisnik(Korisnik korisnik) {
        String hashedPassword = DigestUtils.sha256Hex(korisnik.getLozinka());
        korisnik.setLozinka(hashedPassword);
        return this.userRepository.addKorisnik(korisnik);
    }

    public List<Korisnik> allKorisnici() {
        return this.userRepository.allKorisnici();
    }

    public void deleteKorisnik(Integer id) {
        this.userRepository.deleteKorisnik(id);
    }

    public void promeniStatus(Integer id) { this.userRepository.promeniStatus(id);}
    public void editProfile(Integer id, Korisnik korisnik) { this.userRepository.editProfile(id, korisnik);}

    public Korisnik findKorisnik(Integer id) { return this.userRepository.findKorisnikWithId(id);
    }

    public Korisnik findKorisnikWithEmail(String email) { return this.userRepository.findKorisnikWithEmail(email);
    }
}