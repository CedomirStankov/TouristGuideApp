import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

export default function Korisnici() {
    const [korisnici, setKorisnici] = useState([]);

    const [currentPage, setCurrentPage] = useState(1)
    const recordsPerPage = 5;
    const lastIndex = currentPage * recordsPerPage;
    const firstIndex = lastIndex - recordsPerPage;
    const records = korisnici.slice(firstIndex, lastIndex);
    const npage = Math.ceil(korisnici.length / recordsPerPage)

    useEffect(() => { 

        fetch('http://localhost:8081/api/users', {
            method: 'GET',
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
                setKorisnici(data);
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
            });
    }, []);

    function brisanje(id) {
        fetch('http://localhost:8081/api/users/' + id, {
            method: 'DELETE',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem("token")
            }
        }).then(response => {
            return response.text();
        })
            .then(() => {
                fetch('http://localhost:8081/api/users', {
                    method: 'GET',
                    headers: {
                        'Authorization': 'Bearer ' + localStorage.getItem("token")
                    }
                }).then(response => response.json())
                    .then(data => {
                        setKorisnici(data);
                    })
                    .catch(error => {
                        console.error('There was a problem with the fetch operation:', error);
                    });
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
            });
    }

    function status(id) {
        fetch(`http://localhost:8081/api/users/status/${id}`, {
            method: 'PUT',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem("token"),
                'Content-Type': 'application/json'
            }
        }).then(() => {
            fetch('http://localhost:8081/api/users', {
                method: 'GET',
                headers: {
                    'Authorization': 'Bearer ' + localStorage.getItem("token")
                }
            }).then(response => response.json())
                .then(data => {
                    setKorisnici(data);
                })
                .catch(error => {
                    console.error('There was a problem with the fetch operation:', error);
                });
        })
    }

    return (
        <div>
            <table>
                <thead>
                    <tr>
                        <th>Korisnici</th>
                    </tr>
                    <tr>
                        <th>Email</th>
                        <th>Ime</th>
                        <th>Prezime</th>
                        <th>Tip Korisnika</th>
                        <th>Status</th>
                        <th>Promena statusa</th>
                        <th>Izmena</th>
                        <th>Brisanje</th>
                    </tr>
                </thead>
                <tbody>
                    {records.map(korisnik => (
                        <tr key={korisnik.id}>
                            <td>{korisnik.email}</td>
                            <td>{korisnik.ime}</td>
                            <td>{korisnik.prezime}</td>
                            <td>{korisnik.tipKorisnika}</td>
                            <td>{korisnik.status}</td>
                            <td><button onClick={() => status(korisnik.id)} disabled={korisnik.tipKorisnika === 'admin'}>Promeni status</button></td>
                            <td><Link to={`/korisnici/edit/${korisnik.id}`}><button>Izmeni</button></Link></td>
                            <td><button onClick={() => brisanje(korisnik.id)} disabled={korisnik.tipKorisnika === 'admin'}>Obriši</button></td>
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
                        <Link to="/korisnici/add"><button style={{ background: "green" }}>Dodaj korisnika</button></Link>
                    </li>
                </ul>
            </nav>
        </div>
    );

    function nextPage() {
        if (currentPage === npage)
            return
        setCurrentPage(currentPage + 1)
    }

    function prevPage() {
        if (currentPage === 1)
            return
        setCurrentPage(currentPage - 1)
    }
}
