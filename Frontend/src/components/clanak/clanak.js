import React, { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import CommentForm from '../commentform';
import { Box } from '@mui/material';

export default function Clanak() {
    const [autorClanka, setAutorClanka] = useState('');
    const [naslovClanka, setNaslovClanka] = useState('');
    const [tekstClanka, setTekstClanka] = useState('');
    const [aktivnostiClanka, setAktivnostiClanka] = useState('');
    const [nazivDestinacije, setNazivDestinacije] = useState('');
    const [destinacijaId, setDestinacijaId] = useState('');
    const [vremeKreiranja, setVremeKreiranja] = useState('');
    const [brojPoseta, setBrojPoseta] = useState('');
    const [splitovano, setSplitovano] = useState([]);
    const [author, setAuthor] = useState('');
    const [comment, setComment] = useState('');
    const [komentari, setKomentari] = useState([]);
    const { id } = useParams();

    useEffect(() => {
        fetch(`http://localhost:8081/api/korisnik/clanak/povecajbrojposeta/${id}`, {
            method: 'PUT',
        }).then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok')
            }
            return response.json();
        })
    }, [])

    useEffect(() => {


        fetch(`http://localhost:8081/api/korisnik/clanak/${id}`, {
            method: 'GET',
        }).then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok')
            }
            return response.json();
        }).then(data => {
            setNaslovClanka(data.naslov);
            setTekstClanka(data.tekst);
            setAutorClanka(data.autor);
            setDestinacijaId(data.destinacija_id);
            setVremeKreiranja(data.vremeKreiranja)
            setBrojPoseta(data.brojPoseta)
            return data.destinacija_id; 
        })
            .then(destId => { 
                fetch(`http://localhost:8081/api/korisnik/destinacija/${destId}`, {
                    method: 'GET',
                }).then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok')
                    }
                    return response.json();
                }).then(data => {
                    setNazivDestinacije(data.naziv);
                })
                    .catch(error => {
                        console.error('There was a problem with the fetch operation:', error);
                    });
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
            });

        fetch('http://localhost:8081/api/korisnik/destinacije', {
            method: 'GET',
        }).then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
            });

        fetch(`http://localhost:8081/api/clanakaktivnost/${id}`, {
            method: 'GET'
        }).then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok')
            }
            return response.text();
        }).then(data => {
            setAktivnostiClanka(data)
            setSplitovano(data.split(" "))
        });

        fetch(`http://localhost:8081/api/komentar/clanak/${id}`, {
            method: 'GET',
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                setKomentari(data);
                console.log("aa")
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
            });
    }, []);

    const handleSubmit = (e) => {
        e.preventDefault();

        const newComment = {
            autor: author,
            komentar: comment,
            clanak_id: id
        };

        fetch(`http://localhost:8081/api/komentar`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(newComment),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
            });
        setAuthor('');
        setComment('');
        console.log(newComment)
    };


    return (
        <div>
            <h1>Naslov: {naslovClanka}</h1>
            <p>Broj poseta: {brojPoseta}</p>
            <p>Tekst: {tekstClanka}</p>
            <span><strong>Destinacija:</strong> {nazivDestinacije}</span>
            <br></br>
            <span><strong>Aktivnosti:</strong></span>
            <br></br>
            {splitovano.map((aktivnost, index) => (
                <Link to={`/aktivnost/${aktivnost}`} key={index} style={{ textDecoration: 'none', color: 'black' }}>
                    <p>{aktivnost}</p>
                </Link>
            ))}
            <span><strong>Vreme Kreiranja:</strong> {vremeKreiranja}</span>

            <h3>Komentari:</h3>
            {komentari.map((komentar, index) => (
                <div key={index}>
                    <p>Autor: {komentar.autor}</p>
                    <p>Komentar: {komentar.komentar}</p>
                    <p>Datum kreiranja: {komentar.datumKreiranja}</p>
                    <br></br>
                </div>
            ))}
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="author">Autor:</label>
                    <br></br>
                    <input
                        type="text"
                        id="author"
                        value={author}
                        onChange={(e) => setAuthor(e.target.value)}
                        required
                    />
                </div>
                <br></br>
                <div>
                    <label htmlFor="comment">Komentar:</label>
                    <br></br>
                    <textarea
                        id="comment"
                        value={comment}
                        onChange={(e) => setComment(e.target.value)}
                        required
                    />
                </div>
                <button type="submit" disabled={!author || !comment}>Submit</button>

            </form>
        </div>
    );
}
