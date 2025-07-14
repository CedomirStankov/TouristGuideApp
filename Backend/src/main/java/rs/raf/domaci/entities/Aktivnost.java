package rs.raf.domaci.entities;

public class Aktivnost {
    private Integer id;
    private String naziv;

    public Aktivnost(Integer id, String naziv) {
        this.id = id;
        this.naziv = naziv;
    }

    public Aktivnost() {
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
