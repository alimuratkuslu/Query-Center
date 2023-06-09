import React, { useState } from 'react';
import axios from 'axios';
import { TextField, Button, Typography, Snackbar, Alert } from '@mui/material';
import Dashboard from '../Dashboard';

const AddTrigger = () => {
  const [name, setName] = useState('');
  const [cronExpression, setCronExpression] = useState('');
  const [showSuccessMessage, setShowSuccessMessage] = useState(false);

  const handleSubmit = async (e) => {
    setShowSuccessMessage(true);
    e.preventDefault();
      const response = await axios.post('/trigger', { name: name, cronExpression: cronExpression });
      setName('');
      setCronExpression('');
      console.log(response.data);
      console.log("Trigger saved successfully");
  };

  return (
    <div>
        <Dashboard />
        <br />
        <form style={{display: 'flex', flexDirection: 'column', alignItems: 'center'}} onSubmit={handleSubmit}>
            <Typography variant="h4">
                Add Trigger
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
                    label="Cron Expression"
                    variant="outlined"
                    fullWidth
                    required
                    value={cronExpression}
                    onChange={(e) => setCronExpression(e.target.value)}
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
                Trigger added successfully
            </Alert>
        </Snackbar>
    </div>
  );
};

export default AddTrigger;


