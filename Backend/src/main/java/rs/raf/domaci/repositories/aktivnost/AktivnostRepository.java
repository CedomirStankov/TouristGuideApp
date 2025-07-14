package rs.raf.domaci.repositories.aktivnost;

import rs.raf.domaci.entities.Aktivnost;
import rs.raf.domaci.entities.Destinacija;

import java.util.List;

public interface AktivnostRepository {
    public Aktivnost addAktivnost(Aktivnost aktivnost);
    public List<Aktivnost> allAktivnosti();
    public Aktivnost findAktivnost(Integer id);

    void deleteAktivnost(Integer id);

    void editAktivnost(Integer id, Aktivnost aktivnost);
}
