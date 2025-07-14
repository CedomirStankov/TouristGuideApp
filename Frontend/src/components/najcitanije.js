import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

export default function Pocetna() {
  const [clanci, setClanci] = useState([]);
  const [destinacije, setDestinacije] = useState([]);

    const [currentPage, setCurrentPage] = useState(1)
    const recordsPerPage = 10;
    const lastIndex = currentPage * recordsPerPage;
    const firstIndex = lastIndex - recordsPerPage;
    const records = clanci.slice(firstIndex,lastIndex);
    const npage = Math.ceil(clanci.length/recordsPerPage)

  useEffect(() => { 
    fetch('http://localhost:8081/api/korisnik/najcitaniji',{
        method:'GET',
    }).then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        setClanci(data);
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
    .then(data => {
      setDestinacije(data);
    })
    .catch(error => {
      console.error('There was a problem with the fetch operation:', error);
    });
  }, []);

  const getDestinacijaIme = (id) => {
    const destinacija = destinacije.find(dest => dest.id === id);

    return destinacija ? destinacija.naziv : 'Nepoznato';
  };

  return (
    <div>
      <h2>Najcitanije</h2>
      {records.map(clanak => (
        <div key={clanak.id}>
          <Link to={`/clanak/${clanak.id}`} style={{ textDecoration: 'none', color:'black'}}>
          <h1>{clanak.naslov}</h1>
          <p>{clanak.tekst.substring(0, 100)}...</p>
          <div>
            <span><strong>Destinacija:</strong> {getDestinacijaIme(clanak.destinacija_id)}</span>
            <br></br>
            <span><strong>Vreme Kreiranja:</strong> {clanak.vremeKreiranja}</span>
          </div>
          </Link>
        </div>
      ))}
    </div>
  );
}
