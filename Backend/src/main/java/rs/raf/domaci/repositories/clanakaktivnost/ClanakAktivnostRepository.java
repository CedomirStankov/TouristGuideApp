package rs.raf.domaci.repositories.clanakaktivnost;

import rs.raf.domaci.entities.Clanak;

public interface ClanakAktivnostRepository {
    public String findAktivnostiZaClanak(Integer clanakId);
}
