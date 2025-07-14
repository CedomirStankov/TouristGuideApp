import React, { useEffect, useState } from 'react';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import Select from 'react-select';
import { useParams } from 'react-router-dom';

const defaultTheme = createTheme();

export default function EditClanak() {
    const [autorClanka, setAutorClanka] = useState('');
    const [naslovClanka, setNaslovClanka] = useState('');
    const [tekstClanka, setTekstClanka] = useState('');
    const [aktivnostiClanka, setAktivnostiClanka] = useState('');
    const [nazivDestinacije, setNazivDestinacije] = useState('');
    const [destinacijaId, setDestinacijaId] = useState('');
    const [options, setOptions] = useState([]);
    const [selectedOption, setSelectedOption] = useState(null);
    const { id } = useParams();

    useEffect(() => {
        fetch(`http://localhost:8081/api/clanak/${id}`,{
            method:'GET',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem("token"),
            }
        }).then(response=>{
            if(!response.ok){
                throw new Error('Network response was not ok')
            }
            return response.json();
        }).then(data => {
            setNaslovClanka(data.naslov);
            setTekstClanka(data.tekst);
            setAutorClanka(data.autor);
            setDestinacijaId(data.destinacija_id);
            return data.destinacija_id; 
          })
          .then(destId => { 
            fetch(`http://localhost:8081/api/destinations/${destId}`,{
                method:'GET',
                headers:{
                    'Authorization': 'Bearer ' + localStorage.getItem("token"),
                }
            }).then(response=>{
                if(!response.ok){
                    throw new Error('Network response was not ok')
                }
                return response.json();
            }).then(data => {
                setNazivDestinacije(data.naziv);
                setSelectedOption({ value: destId, label: data.naziv }); 
              })
              .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
              });
          })
          .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
          });
    
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

          fetch(`http://localhost:8081/api/clanakaktivnost/${id}`,{
            method:'GET'
        }).then(response=>{
            if(!response.ok){
                throw new Error('Network response was not ok')
            }
            return response.text();
        }).then(data => {
                setAktivnostiClanka(data)
                console.log(data)
          });
      }, [id]);

    const handleSubmit = async (event) => {
        event.preventDefault();
        fetch(`http://localhost:8081/api/clanak/${id}`,{
            method:'PUT',
            headers:{
                'Authorization': 'Bearer ' + localStorage.getItem("token"),
                'Content-Type':'application/json'
            },
            body: JSON.stringify({
                naslov: naslovClanka,
                tekst: tekstClanka,
                autor: autorClanka,
                destinacija_id:selectedOption.value,
                aktivnosti: aktivnostiClanka
            })
        }).then(response=>{
            return response.json();
        }).then(data => {
            console.log(data)
            // setDestinacija(data);
          })
          .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
          });
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
                        Edit clanak
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
