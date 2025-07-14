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

  useEffect(() => { 
    fetch('http://localhost:8081/api/korisnik/destinacije',{
        method:'GET'
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

  return(
    <div>
      <table>
        <thead>
          <tr>
            <h2>Destinacije</h2>
          </tr>
          <tr>
            <th>Naziv</th>
          </tr>
        </thead>
        <tbody>
          {records.map(destinacija => (
            <tr key={destinacija.id}>
              <Link to={`/destinacija/${destinacija.id}`} style={{ textDecoration: 'none', color:'black'}}><td>{destinacija.naziv}</td></Link>
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
