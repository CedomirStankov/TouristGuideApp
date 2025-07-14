import React, { useEffect, useState } from 'react';
import { AppBar, Toolbar, Typography, Box  } from '@mui/material';
import { Link, useNavigate } from 'react-router-dom';


export default function MojAppBar() {
  const [username, setUsername] = useState('');
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    setUsername(''); 
    navigate('/signin'); 
  };

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (!token) {
      navigate('/signin');
    } else {
      const name = localStorage.getItem('username');
      if (name) {
        setUsername(name);
      }
    }
  }, [navigate]);

  return (
    <div>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" style={{ color: 'white' }}>{username ? `${username}` :""}</Typography>
          <Box ml={2} />
          <Link to="/destinacije" style={{ textDecoration: 'none'}}><Typography variant="h6" style={{ color: 'white' }}>Destinacije</Typography></Link>
          <Box ml={2} />
          <Link to="/clanci" style={{ textDecoration: 'none'}}><Typography variant="h6" style={{ color: 'white' }}>Clanci</Typography></Link>
          <Box ml={2} />
          <Link to="/korisnici" style={{ textDecoration: 'none'}}><Typography variant="h6" style={{ color: 'white' }}>Korisnici</Typography></Link>
          <Box ml={2} />
          {!username && (
            <>
              <Link to="/signin" style={{ textDecoration: 'none' }}><Typography variant="h6" style={{ color: 'white' }}>Sign in</Typography></Link>
              <Box ml={2} />
            </>
          )}
          {username && (
            <Typography variant="h6" style={{ color: 'white', cursor: 'pointer' }} onClick={handleLogout}>Log out</Typography>
          )}
        </Toolbar>
      </AppBar>
      
    </div>
  );
}
