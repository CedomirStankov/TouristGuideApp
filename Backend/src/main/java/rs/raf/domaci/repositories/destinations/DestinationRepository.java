package rs.raf.domaci.repositories.destinations;

import rs.raf.domaci.entities.Destinacija;
import rs.raf.domaci.entities.Komentar;
import rs.raf.domaci.entities.Post;

import java.util.List;

public interface DestinationRepository {
    public Destinacija addDestination(Destinacija destinacija);
    public List<Destinacija> allDestinations();
    public Destinacija findDestination(Integer id);

    Integer deleteDestination(Integer id);

    Integer editDestination(Integer id, Destinacija destinacija);
}
