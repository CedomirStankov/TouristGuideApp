package rs.raf.domaci.services;

import rs.raf.domaci.entities.Aktivnost;
import rs.raf.domaci.entities.Clanak;
import rs.raf.domaci.repositories.aktivnost.AktivnostRepository;
import rs.raf.domaci.repositories.clanak.ClanakRepository;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

public class ClanakService {
    public ClanakService() {
        System.out.println(this);
    }

    @Inject
    private ClanakRepository clanakRepository;

    public Clanak addClanak(Clanak clanak) {
        return this.clanakRepository.addClanak(clanak);
    }

    public List<Clanak> allClanci() {
        return this.clanakRepository.allClanci();
    }

    public Clanak findClanak(Integer id) {
        return this.clanakRepository.findClanak(id);
    }

    public void deleteClanak(Integer id) { this.clanakRepository.deleteClanak(id); }

    public Clanak editClanak(Integer id, Clanak clanak) { return this.clanakRepository.editClanak(id, clanak);}

    public Clanak povecajBrojPoseta(Integer id) {
        return this.clanakRepository.povecajBrojPoseta(id);
    }

    public List<Clanak> najcitanijiClanci() {
        return this.clanakRepository.najcitanijiClanci();
    }

    public List<Clanak> findClankeZaDestinaciju(Integer id) {
        return this.clanakRepository.findClankeZaDestinaciju(id);
    }

    public List<Clanak> clanciAktivnosti(String nazivAktivnosti) {
        return this.clanakRepository.clanciAktivnosti(nazivAktivnosti);
    }
}
