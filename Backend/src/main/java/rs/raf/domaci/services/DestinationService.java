package rs.raf.domaci.services;

import rs.raf.domaci.entities.Destinacija;
import rs.raf.domaci.entities.Komentar;
import rs.raf.domaci.entities.Post;
import rs.raf.domaci.repositories.destinations.DestinationRepository;
import rs.raf.domaci.repositories.posts.PostRepository;

import javax.inject.Inject;
import java.util.List;

public class DestinationService {
    public DestinationService() {
        System.out.println(this);
    }

    @Inject
    private DestinationRepository destinationRepository;

    public Destinacija addDestination(Destinacija destinacija) {
        return this.destinationRepository.addDestination(destinacija);
    }

    public List<Destinacija> allDestinations() {
        return this.destinationRepository.allDestinations();
    }

    public Destinacija findDestination(Integer id) {
        return this.destinationRepository.findDestination(id);
    }

    public Integer deleteDestination(Integer id) { return this.destinationRepository.deleteDestination(id); }

    public Integer editDestination(Integer id, Destinacija destinacija) { return this.destinationRepository.editDestination(id, destinacija);}
}
