package rs.raf.domaci.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import rs.raf.domaci.serialization.LocalDateTimeSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Komentar {
    private Integer id;
    private String autor;
    private String komentar;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime datumKreiranja = LocalDateTime.now();

    private Integer clanak_id;

    public Komentar(Integer id, String autor, String komentar, LocalDateTime datumKreiranja, Integer clanak_id) {
        this.id = id;
        this.autor = autor;
        this.komentar = komentar;
        this.datumKreiranja = datumKreiranja;
        this.clanak_id=clanak_id;
    }

    public Komentar() {
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public LocalDateTime getDatumKreiranja() {
        return datumKreiranja;
    }

    public void setDatumKreiranja(LocalDateTime datumKreiranja) {
        this.datumKreiranja = datumKreiranja;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClanak_id() {
        return clanak_id;
    }

    public void setClanak_id(Integer clanak_id) {
        this.clanak_id = clanak_id;
    }
}
