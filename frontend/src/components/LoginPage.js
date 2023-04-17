import React, { useState } from 'react';
import axios from 'axios';
import { TextField, Button, Typography, Box, Avatar } from '@mui/material';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import { useNavigate } from 'react-router-dom';
import Link from '@mui/material/Link';

const LoginPage = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [token, setToken] = useState(null);
  const navigate = useNavigate();

  const handleEmailChange = (event) => {
    setEmail(event.target.value);
  };

  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
        axios.post('/auth/login',{ email, password })
        .then(response => {
            console.log(response.data.accessToken);

            const accessToken = response.data.accessToken;

            localStorage.setItem('token', accessToken);
            setToken(accessToken);
            navigate(`/`);
        })
        .catch(error => {
            setIsLoading(false);
            if(error.response.status === 401){
                setError("Invalid username or password");
            }
            else {
                setError("An error occurred. Please try again later.");
            }
        });
  };

  return (
    <Box sx={{display: 'flex', flexDirection: 'row', alignItems: 'center', justifyContent: 'center', height: '100vh'}}>
        <br />
        <Typography variant="h3" align="center" gutterBottom style={{ marginLeft: '16px', marginRight: '100px', fontFamily:'monospace' }}>
            Query Center 
        </Typography>
        <form onSubmit={handleSubmit} style={{display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center'}}>

        <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
          <LockOutlinedIcon />
        </Avatar>

        <Typography component="h1" variant="h5">
          Sign in
        </Typography>

        <TextField
            label="Email"
            type="email"
            variant="outlined"
            value={email}
            onChange={handleEmailChange}
            margin="normal"
            required
            style={{ marginBottom: '16px' }}
            InputLabelProps={{ style: { fontSize: 18 } }}
            inputProps={{ style: { fontSize: 18 } }}
        />
        <br />
        <TextField
            label="Password"
            type="password"
            variant="outlined"
            value={password}
            onChange={handlePasswordChange}
            margin="normal"
            required
            style={{ marginBottom: '16px' }}
            InputLabelProps={{ style: { fontSize: 18 } }}
            inputProps={{ style: { fontSize: 18 } }}
        />
        <br />
        <Button type="submit" variant="contained" >
            Login
        </Button>
        <br />
        <Typography variant="body2" color="text.secondary" align="center">
          {'Copyright © '}
          <Link color="inherit" href="https://mui.com/">
            Query Center
          </Link>{' '}
          {new Date().getFullYear()}
          {'.'}
        </Typography>
        </form>
    </Box>
  );
};

export default LoginPage;
