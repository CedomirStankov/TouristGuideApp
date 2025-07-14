package rs.raf.domaci.repositories.user;

import rs.raf.domaci.entities.Korisnik;

import java.util.List;

public interface UserRepository {
    public Korisnik findKorisnik(String email);
    public Korisnik addKorisnik(Korisnik korisnik);
    public List<Korisnik> allKorisnici();

    public void deleteKorisnik(Integer id);

    public void promeniStatus(Integer id);
    public void editProfile(Integer id, Korisnik korisnik);

    Korisnik findKorisnikWithId(Integer id);

    Korisnik findKorisnikWithEmail(String email);
}