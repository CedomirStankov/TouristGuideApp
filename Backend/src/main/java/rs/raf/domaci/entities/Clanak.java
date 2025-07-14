package rs.raf.domaci.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import rs.raf.domaci.serialization.LocalDateTimeSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Clanak {
    private Integer id;
    private String naslov;
    private String tekst;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime vremeKreiranja = LocalDateTime.now();
    private Integer brojPoseta=0;
    private String autor;

    private Integer destinacija_id;
    private String aktivnosti;

    public Clanak(Integer id, String naslov, String tekst, LocalDateTime vremeKreiranja, Integer brojPoseta, String autor, Integer destinacija_id) {
        this.id = id;
        this.naslov = naslov;
        this.tekst = tekst;
        this.vremeKreiranja = vremeKreiranja;
        this.brojPoseta = brojPoseta;
        this.autor = autor;
        this.destinacija_id = destinacija_id;
    }

    public Clanak(Integer id, String naslov, String tekst, LocalDateTime vremeKreiranja, Integer brojPoseta, String autor, Integer destinacija_id, String aktivnosti) {
        this.id = id;
        this.naslov = naslov;
        this.tekst = tekst;
        this.vremeKreiranja = vremeKreiranja;
        this.brojPoseta = brojPoseta;
        this.autor = autor;
        this.destinacija_id = destinacija_id;
        this.aktivnosti = aktivnosti;
    }

    public Clanak() {
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public LocalDateTime getVremeKreiranja() {
        return vremeKreiranja;
    }

    public void setVremeKreiranja(LocalDateTime vremeKreiranja) {
        this.vremeKreiranja = vremeKreiranja;
    }

    public Integer getBrojPoseta() {
        return brojPoseta;
    }

    public void setBrojPoseta(Integer brojPoseta) {
        this.brojPoseta = brojPoseta;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Integer getDestinacija_id() {
        return destinacija_id;
    }

    public void setDestinacija_id(Integer destinacija_id) {
        this.destinacija_id = destinacija_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAktivnosti() {
        return aktivnosti;
    }

    public void setAktivnosti(String aktivnosti) {
        this.aktivnosti = aktivnosti;
    }
}
