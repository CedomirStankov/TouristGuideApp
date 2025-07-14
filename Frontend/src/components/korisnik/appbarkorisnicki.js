import React from 'react';
import { AppBar, Toolbar, Typography, Box  } from '@mui/material';
import { Link } from 'react-router-dom';


export default function AppBarKorisnicki() {

  return (
    <div>
      <AppBar position="static">
        <Toolbar>
          <Link to="/pocetna" style={{ textDecoration: 'none'}}><Typography variant="h6" style={{ color: 'white' }}>Pocetna</Typography></Link>
          <Box ml={2} />
          <Link to="/najcitanije" style={{ textDecoration: 'none'}}><Typography variant="h6" style={{ color: 'white' }}>Najcitanije</Typography></Link>
          <Box ml={2} />
          <Link to="/svedestinacije" style={{ textDecoration: 'none'}}><Typography variant="h6" style={{ color: 'white' }}>Destinacije</Typography></Link>
          <Box ml={2} />
          <Link to="/signin" style={{ textDecoration: 'none' }}><Typography variant="h6" style={{ color: 'white' }}>Sign in za radnike</Typography></Link>
        </Toolbar>
      </AppBar>
      
    </div>
  );
}
