import React, { useEffect, useState } from 'react';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import Select from 'react-select';

const defaultTheme = createTheme();

export default function DodajClanak() {
    const [autorClanka, setAutorClanka] = useState('');
    const [naslovClanka, setNaslovClanka] = useState('');
    const [tekstClanka, setTekstClanka] = useState('');
    const [aktivnostiClanka, setAktivnostiClanka] = useState('');
    const [options, setOptions] = useState([]);
    const [selectedOption, setSelectedOption] = useState(null);

    useEffect(() => { //ovim useEffectom stavljam koja funkcija ce se izvrsiti kada se renderuje prikaz, a drugi argument mi samo govori kada zelim da se izvrsi useEffect, ako je prazan niz onda se samo prvi put izvrsava pri prvom renderovanju
        fetch('http://localhost:8081/api/destinations',{
            method:'GET',
            headers:{
                'Authorization': 'Bearer ' + localStorage.getItem("token"),
            }
        }).then(response => {
            if (!response.ok) {
              throw new Error('Network response was not ok');
            }
            return response.json();
          })
          .then(data => {
            const options = data.map(destinacija => ({
                value: destinacija.id,
                label: destinacija.naziv
              }));
              setOptions(options);
          })
          .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
          });
      }, []);

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const response = await fetch('http://localhost:8081/api/clanak', {
                method: 'POST',
                headers: {
                    'Authorization': 'Bearer ' + localStorage.getItem("token"),
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    naslov: naslovClanka,
                    tekst: tekstClanka,
                    autor: autorClanka,
                    destinacija_id:selectedOption.value,
                    aktivnosti: aktivnostiClanka
                })
            });
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            
            setAutorClanka('');
            setNaslovClanka('');
            setTekstClanka('');
        } catch (error) {
            console.error('There was a problem with the fetch operation:', error);
        }
    };

    const handleChange = selectedOption => {
        setSelectedOption(selectedOption);
        console.log(selectedOption.value)
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
                        Dodaj clanak
                    </Typography>
                    <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            id="autorClanka"
                            label="Autor Clanka"
                            name="autorClanka"
                            autoFocus
                            value={autorClanka}
                            onChange={(e) => setAutorClanka(e.target.value)}
                        />
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            id="naslovClanka"
                            label="Naslov Clanka"
                            name="naslovClanka"
                            autoFocus
                            value={naslovClanka}
                            onChange={(e) => setNaslovClanka(e.target.value)}
                        />
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            multiline
                            rows={10}
                            name="tekstClanka"
                            label="Tekst Clanka"
                            id="tekstClanka"
                            value={tekstClanka}
                            onChange={(e) => setTekstClanka(e.target.value)}
                        />
                        <Select
                            value={selectedOption}
                            onChange={handleChange}
                            options={options}
                            placeholder="Select a destination"
                        />
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            id="aktivnostiClanka"
                            label="Aktivnosti Clanka (Aktivnosti odvojiti razmakom)"
                            name="aktivnostiClanka"
                            autoFocus
                            value={aktivnostiClanka}
                            onChange={(e) => setAktivnostiClanka(e.target.value)}
                        />
                        <Button
                            type="submit"
                            variant="contained"
                            sx={{ mt: 3, mb: 2 }}
                            disabled={!naslovClanka || !tekstClanka || !autorClanka || !selectedOption || !aktivnostiClanka}
                        >
                            Potvrdi
                        </Button>
                    </Box>
                </Box>
            </Container>
        </ThemeProvider>
    );
}
