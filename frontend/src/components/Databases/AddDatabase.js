import React, { useState } from 'react';
import axios from 'axios';
import { TextField, Button, Typography, Snackbar, Alert } from '@mui/material';
import Dashboard from '../Dashboard';

const AddDatabase = () => {
  const [name, setName] = useState('');
  const [connectionString, setConnectionString] = useState('');
  const [recipients, setRecipients] = useState([]);
  const [showSuccessMessage, setShowSuccessMessage] = useState(false);

  const handleSubmit = async (e) => {
    setShowSuccessMessage(true);
    e.preventDefault();
      const response = await axios.post('/database', { name, connectionString });
      setName('');
      setConnectionString('');
      console.log(response.data);
      console.log("Database saved successfully");
  };

  return (
    <div>
        <Dashboard />
        <br />
        <form style={{display: 'flex', flexDirection: 'column', alignItems: 'center'}} onSubmit={handleSubmit}>
            <Typography variant="h4">
                Add Database
            </Typography>
            <br />
            <div>
                <TextField
                    label="Name"
                    variant="outlined"
                    fullWidth
                    required
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                />
            </div>
            <br />
            <div>
                <TextField
                    label="Connection String"
                    variant="outlined"
                    fullWidth
                    required
                    value={connectionString}
                    onChange={(e) => setConnectionString(e.target.value)}
                />
            </div>
            <br />
                <Button
                    variant="contained"
                    color="primary"
                    type="submit"
                >
                    Submit
                </Button>
        </form>
        <Snackbar
            anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
            open={showSuccessMessage}
            autoHideDuration={3000}
            onClose={() => setShowSuccessMessage(false)}
        >
            <Alert severity="success" onClose={() => setShowSuccessMessage(false)}>
                Database added successfully
            </Alert>
        </Snackbar>
    </div>
  );
};

export default AddDatabase;


