import React, { useState } from 'react';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';

const defaultTheme = createTheme();

export default function DodajDestinaciju() {
    const [nazivDestinacije, setNazivDestinacije] = useState('');
    const [opisDestinacije, setOpisDestinacije] = useState('');

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const response = await fetch('http://localhost:8081/api/destinations', {
                method: 'POST',
                headers: {
                    'Authorization': 'Bearer ' + localStorage.getItem("token"),
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    naziv: nazivDestinacije,
                    opis: opisDestinacije,
                })
            });
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }

            setNazivDestinacije('');
            setOpisDestinacije('');
        } catch (error) {
            console.error('There was a problem with the fetch operation:', error);
            alert("Destinacija sa imenom "+ nazivDestinacije +" vec postoji");
            setNazivDestinacije('');
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
                        Dodaj destinaciju
                    </Typography>
                    <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            id="nazivDestinacije"
                            label="Naziv destinacije"
                            name="nazivDestinacije"
                            autoFocus
                            value={nazivDestinacije}
                            onChange={(e) => setNazivDestinacije(e.target.value)}
                        />
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            multiline
                            rows={10}
                            name="opisDestinacije"
                            label="Opis"
                            id="opisDestinacije"
                            value={opisDestinacije}
                            onChange={(e) => setOpisDestinacije(e.target.value)}
                        />
                        <Button
                            type="submit"
                            variant="contained"
                            sx={{ mt: 3, mb: 2 }}
                            disabled={!nazivDestinacije || !opisDestinacije}
                        >
                            Potvrdi
                        </Button>
                    </Box>
                </Box>
            </Container>
        </ThemeProvider>
    );
}
