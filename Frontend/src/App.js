import './App.css';
import MojAppBar from './components/appbar.js';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import Destinacije from './components/destinacije.js';
import DodajDestinaciju from './components/dodajdestinaciju.js';
import EditDestinaciju from './components/editdestinaciju.js';
import Clanci from './components/clanci.js';
import DodajClanak from './components/dodajclanak.js';
import EditClanak from './components/editclanak.js';
import SignIn from './components/login.js';
import Korisnici from './components/korisnici.js';
import DodajKorisnika from './components/dodajkorisnika.js';
import EditKorisnika from './components/editkorisnika.js';
import ProtectedRoute from './components/protecedroute.js';
import AppBarKorisnicki from './components/appbarkorisnicki.js';
import Pocetna from './components/pocetna.js';
import Najcitanije from './components/najcitanije.js';
import Svedestinacije from './components/svedestinacije.js';
import { useEffect, useState } from 'react';
import Clanak from './components/clanak.js';
import ClanciDestinacije from './components/clancidestinacije.js';
import ClanciAktivnosti from './components/clanciaktivnosti.js';

function App() {
  const [isUserLoggedIn, setIsUserLoggedIn] = useState(!!localStorage.getItem('token'));

  useEffect(() => {
    const handleStorageChange = () => {
      setIsUserLoggedIn(!!localStorage.getItem('token'));
    };

    window.addEventListener('storage', handleStorageChange);

    return () => {
      window.removeEventListener('storage', handleStorageChange);
    };
  }, []);

  return (
    <Router>
      <div>
      {isUserLoggedIn ? <MojAppBar /> : <AppBarKorisnicki />}
        <Routes>
          <Route path="/signin" element={<SignIn />} />
          <Route path="/destinacije" element={<ProtectedRoute><Destinacije /></ProtectedRoute>} />
          <Route path="/destinacije/add" element={<DodajDestinaciju />} />
          <Route path="/destinacije/edit/:id" element={<EditDestinaciju />} />
          <Route path="/destinacija/:id" element={<ClanciDestinacije />} />
          <Route path="/clanci" element={<ProtectedRoute><Clanci /></ProtectedRoute>} />
          <Route path="/clanci/add" element={<DodajClanak />} />
          <Route path="/clanci/edit/:id" element={<EditClanak />} />
          <Route path="/clanak/:id" element={<Clanak />} />
          <Route path="/aktivnost/:id" element={<ClanciAktivnosti />} />
          <Route path="/signin" element={<SignIn />} />
          <Route path="/korisnici" element={<ProtectedRoute><Korisnici /></ProtectedRoute>} />
          <Route path="/korisnici/add" element={<DodajKorisnika />} />
          <Route path="/korisnici/edit/:id" element={<EditKorisnika />} />
          <Route path="/pocetna" element={<Pocetna />} />
          <Route path="/najcitanije" element={<Najcitanije />} />
          <Route path="/svedestinacije" element={<Svedestinacije />} />
          <Route path="/" element={<Navigate to="/pocetna" />} />
          <Route path="*" element={<Navigate to="/pocetna" />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
