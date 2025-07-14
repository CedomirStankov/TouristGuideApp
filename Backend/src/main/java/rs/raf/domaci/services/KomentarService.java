package rs.raf.domaci.services;

import rs.raf.domaci.entities.Aktivnost;
import rs.raf.domaci.entities.Komentar;
import rs.raf.domaci.repositories.aktivnost.AktivnostRepository;
import rs.raf.domaci.repositories.komentar.KomentarRepository;

import javax.inject.Inject;
import javax.ws.rs.Path;
import java.util.List;


public class KomentarService {
    public KomentarService() {
        System.out.println(this);
    }

    @Inject
    private KomentarRepository komentarRepository;

    public Komentar addKomentar(Komentar komentar) {
        return this.komentarRepository.addKomentar(komentar);
    }

    public List<Komentar> allKomentari(Integer id) {
        return this.komentarRepository.allKomentari(id);
    }

    public Komentar findKomentar(Integer id) {
        return this.komentarRepository.findKomentar(id);
    }

    public void deleteKomentar(Integer id) { this.komentarRepository.deleteKomentar(id); }

}
