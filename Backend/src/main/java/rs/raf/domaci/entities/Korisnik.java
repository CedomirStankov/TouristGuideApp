package rs.raf.domaci.entities;

public class Korisnik {
    private Integer id;
    private String email;
    private String ime;
    private String prezime;

    private String tipKorisnika;
    private Integer status;
    private String lozinka;

    public Korisnik() {
    }

    public Korisnik(Integer id, String email, String ime, String prezime, String tipKorisnika, Integer status, String lozinka) {
        this.id = id;
        this.email = email;
        this.ime = ime;
        this.prezime = prezime;
        this.tipKorisnika = tipKorisnika;
        this.status = status;
        this.lozinka = lozinka;
    }

    public Korisnik(Integer id, String email, String ime, String prezime, String tipKorisnika) {
        this.id = id;
        this.email = email;
        this.ime = ime;
        this.prezime = prezime;
        this.tipKorisnika = tipKorisnika;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getTipKorisnika() {
        return tipKorisnika;
    }

    public void setTipKorisnika(String tipKorisnika) {
        this.tipKorisnika = tipKorisnika;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }
}