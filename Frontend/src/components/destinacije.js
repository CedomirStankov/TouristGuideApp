import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

export default function Destinacije() {
  const [destinacije, setDestinacije] = useState([]);

    const [currentPage, setCurrentPage] = useState(1)
    const recordsPerPage = 5;
    const lastIndex = currentPage * recordsPerPage;
    const firstIndex = lastIndex - recordsPerPage;
    const records = destinacije.slice(firstIndex,lastIndex);
    const npage = Math.ceil(destinacije.length/recordsPerPage)

  useEffect(() => { //ovim useEffectom stavljam koja funkcija ce se izvrsiti kada se renderuje prikaz, a drugi argument mi samo govori kada zelim da se izvrsi useEffect, ako je prazan niz onda se samo prvi put izvrsava pri prvom renderovanju
    fetch('http://localhost:8081/api/destinations',{
        method:'GET',
        headers: {
          'Authorization': 'Bearer ' + localStorage.getItem("token"),
        }
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

  function brisanje(id){
    fetch('http://localhost:8081/api/destinations/'+id,{
        method:'DELETE',
        headers: {
          'Authorization': 'Bearer ' + localStorage.getItem("token"),
        }
    }).then(response => {
      if (!response.ok) {
        alert("Nije moguce obrisati ovu destinaciju zato sto postoji clanak koji je vezan za nju")
      }
      return response.text();
    })
    .then(() => {
        fetch('http://localhost:8081/api/destinations',{
          method:'GET',
          headers: {
            'Authorization': 'Bearer ' + localStorage.getItem("token"),
          }
        }).then(response => response.json())
          .then(data => {
            setDestinacije(data);
          })
          .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
          });
      })
      .catch(error => {
        console.error('There was a problem with the fetch operation:', error);
      });
  }

  return(
    <div>
      <table>
        <thead>
          <tr>
            <th>Destinacije</th>
          </tr>
          <tr>
            <th>Naziv</th>
            <th>Opis</th>
            <th>Izmena</th>
            <th>Brisanje</th>
          </tr>
        </thead>
        <tbody>
          {records.map(destinacija => (
            <tr key={destinacija.id}>
              <td>{destinacija.naziv}</td>
              <td>{destinacija.opis}</td>
              <td><Link to={`/destinacije/edit/${destinacija.id}`}><button>Izmeni</button></Link></td>
              <td><button onClick={() => brisanje(destinacija.id)}>Obrisi</button></td>
            </tr>
          ))}
        </tbody>
      </table>
      <nav>
        <ul className='pagination'> 
          <li>
            <button onClick={prevPage}>Prev</button>
          </li>
          <li>
            <button onClick={nextPage}>Next</button>
          </li>
          <li>
            <Link to="/destinacije/add"><button style={{background:"green"}}>Dodaj destinaciju</button></Link>
          </li>
        </ul>
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
