import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

export default function Clanci() {
  const [clanci, setClanci] = useState([]);

    const [currentPage, setCurrentPage] = useState(1)
    const recordsPerPage = 5;
    const lastIndex = currentPage * recordsPerPage;
    const firstIndex = lastIndex - recordsPerPage;
    const records = clanci.slice(firstIndex,lastIndex);
    const npage = Math.ceil(clanci.length/recordsPerPage)

  useEffect(() => { //ovim useEffectom stavljam koja funkcija ce se izvrsiti kada se renderuje prikaz, a drugi argument mi samo govori kada zelim da se izvrsi useEffect, ako je prazan niz onda se samo prvi put izvrsava pri prvom renderovanju
    fetch('http://localhost:8081/api/clanak',{
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
        setClanci(data);
      })
      .catch(error => {
        console.error('There was a problem with the fetch operation:', error);
      });
  }, []);

  function brisanje(id){
    fetch('http://localhost:8081/api/clanak/'+id,{
        method:'DELETE',
        headers: {
          'Authorization': 'Bearer ' + localStorage.getItem("token"),
        }
    }).then(response => {
      if (!response.ok) {
        alert("Nije moguce obrisati ovaj clanak")
      }
      return response.text();
    })
    .then(() => {
        fetch('http://localhost:8081/api/clanak',{
          method:'GET',
          headers: {
            'Authorization': 'Bearer ' + localStorage.getItem("token"),
          }
        }).then(response => response.json())
          .then(data => {
            setClanci(data);
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
            <th>Clanci</th>
          </tr>
          <tr>
            <th>Naslov</th>
            <th>Tekst</th>
            <th>Vreme Kreiranja</th>
            <th>Broj Poseta</th>
            <th>Autor</th>
            <th>Izmena</th>
            <th>Brisanje</th>
          </tr>
        </thead>
        <tbody>
          {records.map(clanak => (
            <tr key={clanak.id}>
              <td>{clanak.naslov}</td>
              <td>{clanak.tekst}</td>
              <td>{clanak.vremeKreiranja}</td>
              <td>{clanak.brojPoseta}</td>
              <td>{clanak.autor}</td>
              <td><Link to={`/clanci/edit/${clanak.id}`}><button>Izmeni</button></Link></td>
              <td><button onClick={() => brisanje(clanak.id)}>Obrisi</button></td>
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
            <Link to="/clanci/add"><button style={{background:"green"}}>Dodaj clanak</button></Link>
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
