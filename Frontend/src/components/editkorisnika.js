import React, { useEffect, useState } from 'react';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import { useParams } from 'react-router-dom';

const defaultTheme = createTheme();

export default function EditKorisnika() {
    const [emailKorisnika, setEmailKorisnika] = useState('');
    const [imeKorisnika, setImeKorisnika] = useState('');
    const [prezimeKorisnika, setPrezimeKorisnika] = useState('');
    const [tipKorisnika, setTipKorisnika] = useState('');
    const { id } = useParams(); //odavde uzimam id iz urla
    
    useEffect(()=>{
        fetch(`http://localhost:8081/api/users/${id}`,{
            method:'GET',
            headers:{
                'Authorization':'Bearer '+localStorage.getItem("token")
            }
        }).then(response=>{
            if(!response.ok){
                throw new Error('Network response was not ok')
            }
            return response.json();
        }).then(data => {
                setEmailKorisnika(data.email)
                setImeKorisnika(data.ime)
                setPrezimeKorisnika(data.prezime)
                setTipKorisnika(data.tipKorisnika)
          })
          .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
          });
      }, [id]);
      
    const handleSubmit = async (event) => {
        event.preventDefault();
        fetch(`http://localhost:8081/api/users/${id}`,{
            method:'PUT',
            headers:{
                'Authorization':'Bearer '+localStorage.getItem("token"),
                'Content-Type':'application/json'
            },
            body: JSON.stringify({
                    email: emailKorisnika,
                    ime: imeKorisnika,
                    prezime: prezimeKorisnika,
                    tipKorisnika: tipKorisnika
            })
        }).catch(error => {
            console.error('There was a problem with the fetch operation:', error);
          });
    };

    return (
        <ThemeProvider theme={defaultTheme}>
            <Container component="main" maxWidth="xs">
                <CssBaseline />
                <Box
                    sx={{
                        marginTop: 8,
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                    }}
                >
                    <Typography component="h1" variant="h5">
                        Edit korisnika
                    </Typography>
                    <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
                    <TextField
                            margin="normal"
                            required
                            fullWidth
                            id="emailKorisnika"
                            label="Email Korisnika "
                            name="emailKorisnika"
                            autoFocus
                            value={emailKorisnika}
                            onChange={(e) => setEmailKorisnika(e.target.value)}
                        />
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            id="imeKorisnika"
                            label="Ime Korisnika "
                            name="imeKorisnika"
                            autoFocus
                            value={imeKorisnika}
                            onChange={(e) => setImeKorisnika(e.target.value)}
                        />
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            id="prezimeKorisnika"
                            label="Prezime Korisnika "
                            name="prezimeKorisnika"
                            autoFocus
                            value={prezimeKorisnika}
                            onChange={(e) => setPrezimeKorisnika(e.target.value)}
                        />
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            id="tipKorisnika"
                            label="Tip Korisnika "
                            name="tipKorisnika"
                            autoFocus
                            value={tipKorisnika}
                            onChange={(e) => setTipKorisnika(e.target.value)}
                        />
                        <Button
                            type="submit"
                            variant="contained"
                            sx={{ mt: 3, mb: 2 }}
                            disabled={!emailKorisnika || !imeKorisnika || !prezimeKorisnika || !tipKorisnika}
                        >
                            Potvrdi
                        </Button>
                    </Box>
                </Box>
            </Container>
        </ThemeProvider>
    );
}
