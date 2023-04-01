import React, { useState } from 'react';
import axios from 'axios';
import { TextField, Button, Typography, Snackbar, Alert } from '@mui/material';
import Dashboard from './Dashboard';

const AddTeam = () => {
  const [name, setName] = useState('');
  const [mail, setMail] = useState('');
  const [showSuccessMessage, setShowSuccessMessage] = useState(false);

  const handleSubmit = async (e) => {
    setShowSuccessMessage(true);
    e.preventDefault();
      const response = await axios.post('/team', { name: name, teamMail: mail });
      setName('');
      setMail('');
      console.log(response.data);
      console.log("Team saved successfully");
  };

  return (
    <div>
        <Dashboard />
        <form style={{display: 'flex', flexDirection: 'column', alignItems: 'center'}} onSubmit={handleSubmit}>
            <Typography variant="h4">
                Add Team
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
                    label="Team Mail"
                    variant="outlined"
                    fullWidth
                    required
                    value={mail}
                    onChange={(e) => setMail(e.target.value)}
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
                Team added successfully
            </Alert>
        </Snackbar>
    </div>
  );
};

export default AddTeam;


