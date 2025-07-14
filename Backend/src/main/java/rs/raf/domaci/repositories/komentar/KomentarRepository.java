package rs.raf.domaci.repositories.komentar;

import rs.raf.domaci.entities.Komentar;

import java.util.List;

public interface KomentarRepository {
    public Komentar addKomentar(Komentar komentar);
    public List<Komentar> allKomentari(Integer id);
    public Komentar findKomentar(Integer id);

    void deleteKomentar(Integer id);
}
