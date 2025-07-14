package rs.raf.domaci.repositories.clanak;

import rs.raf.domaci.entities.Aktivnost;
import rs.raf.domaci.entities.Clanak;

import java.util.List;

public interface ClanakRepository {
    public Clanak addClanak(Clanak clanak);
    public List<Clanak> allClanci();
    public Clanak findClanak(Integer id);

    void deleteClanak(Integer id);

    Clanak editClanak(Integer id, Clanak clanak);

    Clanak povecajBrojPoseta(Integer id);

    List<Clanak> najcitanijiClanci();

    List<Clanak> findClankeZaDestinaciju(Integer id);

    List<Clanak> clanciAktivnosti(String nazivAktivnosti);
}
