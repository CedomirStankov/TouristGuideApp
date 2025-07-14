import React, { useState } from 'react'

export default function PaginationTable(destinacije){
    
    const [currentPage, setCurrentPage] = useState(1)
    const recordsPerPage = 5;
    const lastIndex = currentPage * recordsPerPage;
    const firstIndex = lastIndex - recordsPerPage;
    const records = destinacije.slice(firstIndex,lastIndex);
    const npage = Math.ceil(destinacije.length/recordsPerPage)
    const numbers = [...Array(npage + 1).keys()].slice(1)


    return(
        <div>
            <table>
                <thead>
                    <th>Naziv</th>
                    <th>Opis</th>
                    <th>Akcija</th>
                </thead>
                <tbody>
                {records.map(destinacija => (
                    <tr key={destinacija.id}>
                        <td>{destinacija.naziv}</td>
                        <td>{destinacija.opis}</td>
                        <td><button onClick={()=>brisanje(destinacija.id)}>Obrisi</button></td>
                    </tr>
                ))}
                </tbody>
            </table>
            <nav>
                <ul className='pagination'>
                    <li className='page-item'>
                        <a href='#' className='page-link' onClick={prePage}>Prev</a>
                    </li>
                    {
                        numbers.map((n, i)=>(
                            <li className={`page-item ${currentPage === n ? 'active': ''}`} key={i}>
                                <a href='#' className='page-item' onClick={()=>changeCPage(n)}>{n}</a>
                            </li>
                        ))
                    }
                    <li className='page-item'>
                        <a href='#' className='page-link' onClick={nextPage}>Next</a>
                    </li>
                </ul>
            </nav>
        </div>
    )

    function nextPage(){

    }

    function prevPage(){

    }

    function changeCPage(id){

    }
}