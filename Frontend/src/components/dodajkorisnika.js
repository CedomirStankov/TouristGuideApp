import React, { useState } from 'react';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';

const defaultTheme = createTheme();

export default function DodajKorisnika() {
    const [emailKorisnika, setEmailKorisnika] = useState('');
    const [imeKorisnika, setImeKorisnika] = useState('');
    const [prezimeKorisnika, setPrezimeKorisnika] = useState('');
    const [tipKorisnika, setTipKorisnika] = useState('');
    const [statusKorisnika, setStatusKorisnika] = useState('');
    const [lozinkaKorisnika, setLozinkaKorisnika] = useState('');
    const [potvrdaLozinkaKorisnika, setPotvrdaLozinkaKorisnika] = useState('');

    const handleSubmit = async (event) => {
        event.preventDefault();
        if(lozinkaKorisnika!==potvrdaLozinkaKorisnika){
            alert("Lozinka i potvrda lozinke nisu isti")
            throw new Error('Lozinka i potvrda lozinke nisu isti');
        }
        try {
            const response = await fetch('http://localhost:8081/api/users', {
                method: 'POST',
                headers: {
                    'Authorization':'Bearer '+localStorage.getItem("token"),
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    email: emailKorisnika,
                    ime: imeKorisnika,
                    prezime: prezimeKorisnika,
                    tipKorisnika: tipKorisnika,
                    status: statusKorisnika,
                    lozinka: lozinkaKorisnika
                })
            });
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }else{
                setEmailKorisnika('')
                setImeKorisnika('')
                setPrezimeKorisnika('')
                setTipKorisnika('')
                setStatusKorisnika('')
                setLozinkaKorisnika('')
                setPotvrdaLozinkaKorisnika('')
            }
        } catch (error) {
            console.error('There was a problem with the fetch operation:', error);
            alert("Korisnik sa imejlom "+ emailKorisnika +" vec postoji");
            setEmailKorisnika('');
        }
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
                        Dodaj korisnika
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
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            id="statusKorisnika"
                            label="Status Korisnika (1 ili 0) "
                            name="statusKorisnika"
                            autoFocus
                            value={statusKorisnika}
                            onChange={(e) => setStatusKorisnika(e.target.value)}
                        />
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            id="lozinkaKorisnika"
                            label="Lozinka Korisnika"
                            name="lozinkaKorisnika"
                            type="password"
                            autoFocus
                            value={lozinkaKorisnika}
                            onChange={(e) => setLozinkaKorisnika(e.target.value)}
                        />
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            id="potvrdaLozinkaKorisnika"
                            label="Potvrdi Lozinku Korisnika"
                            name="potvrdaLozinkaKorisnika"
                            type="password"
                            autoFocus
                            value={potvrdaLozinkaKorisnika}
                            onChange={(e) => setPotvrdaLozinkaKorisnika(e.target.value)}
                        />
                        <Button
                            type="submit"
                            variant="contained"
                            sx={{ mt: 3, mb: 2 }}
                            disabled={!emailKorisnika || !imeKorisnika || !prezimeKorisnika || !tipKorisnika || !statusKorisnika || !lozinkaKorisnika || !potvrdaLozinkaKorisnika}
                        >
                            Potvrdi
                        </Button>
                    </Box>
                </Box>
            </Container>
        </ThemeProvider>
    );
}
