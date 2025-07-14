import React, { useState, useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';

export default function ClanciDestinacije() {
  const [clanci, setClanci] = useState([]);
  const [destinacije, setDestinacije] = useState([]);

    const [currentPage, setCurrentPage] = useState(1)
    const recordsPerPage = 10;
    const lastIndex = currentPage * recordsPerPage;
    const firstIndex = lastIndex - recordsPerPage;
    const records = clanci.slice(firstIndex,lastIndex);
    const npage = Math.ceil(clanci.length/recordsPerPage)
    const { id } = useParams();

  useEffect(() => { //ovim useEffectom stavljam koja funkcija ce se izvrsiti kada se renderuje prikaz, a drugi argument mi samo govori kada zelim da se izvrsi useEffect, ako je prazan niz onda se samo prvi put izvrsava pri prvom renderovanju
    fetch(`http://localhost:8081/api/korisnik/clanakdestinacije/${id}`,{
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
      <h2>Destinacija</h2>
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
      <nav className="pagination">
        <button onClick={prevPage} disabled={currentPage === 1}>Prev</button>
        <button onClick={nextPage} disabled={currentPage === npage}>Next</button>
      </nav>
    </div>
  );

    function nextPage(){
        if(currentPage===npage)
            return
        setCurrentPage(currentPage+1)
    }

    function prevPage(){
        if(currentPage===1)
            return
        setCurrentPage(currentPage-1)
    }
}
