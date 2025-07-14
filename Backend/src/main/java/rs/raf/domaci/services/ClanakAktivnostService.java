package rs.raf.domaci.services;

import rs.raf.domaci.entities.Clanak;
import rs.raf.domaci.repositories.aktivnost.AktivnostRepository;
import rs.raf.domaci.repositories.clanakaktivnost.ClanakAktivnostRepository;

import javax.inject.Inject;

public class ClanakAktivnostService {
    public ClanakAktivnostService() {
        System.out.println(this);
    }

    @Inject
    private ClanakAktivnostRepository clanakAktivnostRepository;

    public String findAktivnostiZaClanak(Integer clanakId) {
        return this.clanakAktivnostRepository.findAktivnostiZaClanak(clanakId);
    }
}
