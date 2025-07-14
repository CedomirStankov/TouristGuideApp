package rs.raf.domaci.services;

import rs.raf.domaci.entities.Aktivnost;
import rs.raf.domaci.entities.Destinacija;
import rs.raf.domaci.repositories.aktivnost.AktivnostRepository;
import rs.raf.domaci.repositories.destinations.DestinationRepository;

import javax.inject.Inject;
import java.util.List;

public class AktivnostService {
    public AktivnostService() {
        System.out.println(this);
    }

    @Inject
    private AktivnostRepository aktivnostRepository;

    public Aktivnost addAktivnost(Aktivnost aktivnost) {
        return this.aktivnostRepository.addAktivnost(aktivnost);
    }

    public List<Aktivnost> allAktivnosti() {
        return this.aktivnostRepository.allAktivnosti();
    }

    public Aktivnost findAktivnost(Integer id) {
        return this.aktivnostRepository.findAktivnost(id);
    }

    public void deleteAktivnost(Integer id) { this.aktivnostRepository.deleteAktivnost(id); }

    public void editAktivnost(Integer id, Aktivnost aktivnost) { this.aktivnostRepository.editAktivnost(id, aktivnost);}
}
