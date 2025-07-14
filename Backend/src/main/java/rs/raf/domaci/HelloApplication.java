package rs.raf.domaci;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import rs.raf.domaci.filters.CorsFilter;
import rs.raf.domaci.repositories.aktivnost.AktivnostRepository;
import rs.raf.domaci.repositories.aktivnost.MySqlAktivnostRepository;
import rs.raf.domaci.repositories.clanak.ClanakRepository;
import rs.raf.domaci.repositories.clanak.MySqlClanakRepository;
import rs.raf.domaci.repositories.clanakaktivnost.ClanakAktivnostRepository;
import rs.raf.domaci.repositories.clanakaktivnost.MySqlClanakAktivnostRepository;
import rs.raf.domaci.repositories.destinations.DestinationRepository;
import rs.raf.domaci.repositories.destinations.MySqlDestinationRepository;
import rs.raf.domaci.repositories.komentar.KomentarRepository;
import rs.raf.domaci.repositories.komentar.MySqlKomentarRepository;
import rs.raf.domaci.repositories.posts.MySqlPostRepository;
import rs.raf.domaci.repositories.posts.PostRepository;
import rs.raf.domaci.repositories.user.MySqlUserRepository;
import rs.raf.domaci.repositories.user.UserRepository;
import rs.raf.domaci.services.*;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class HelloApplication extends ResourceConfig {

    public HelloApplication() {
        // Ukljucujemo validaciju
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        // Definisemo implementacije u dependency container-u
        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(MySqlPostRepository.class).to(PostRepository.class).in(Singleton.class);
                this.bind(MySqlUserRepository.class).to(UserRepository.class).in(Singleton.class);
                this.bind(MySqlDestinationRepository.class).to(DestinationRepository.class).in(Singleton.class);
                this.bind(MySqlAktivnostRepository.class).to(AktivnostRepository.class).in(Singleton.class);
                this.bind(MySqlClanakRepository.class).to(ClanakRepository.class).in(Singleton.class);
                this.bind(MySqlKomentarRepository.class).to(KomentarRepository.class).in(Singleton.class);
                this.bind(MySqlClanakAktivnostRepository.class).to(ClanakAktivnostRepository.class).in(Singleton.class);

                this.bindAsContract(PostService.class);
                this.bindAsContract(UserService.class);
                this.bindAsContract(DestinationService.class);
                this.bindAsContract(AktivnostService.class);
                this.bindAsContract(ClanakService.class);
                this.bindAsContract(KomentarService.class);
                this.bindAsContract(ClanakAktivnostService.class);

            }
        };
        register(binder);
        register(CorsFilter.class);

        // Ucitavamo resurse
        packages("rs.raf.domaci");
    }
}
