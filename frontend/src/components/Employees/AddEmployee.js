import React, { useState } from 'react';
import axios from 'axios';
import { TextField, Button, Typography, Snackbar, Alert, Autocomplete } from '@mui/material';
import Dashboard from '../Dashboard';

const AddEmployee = () => {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [employeeRole, setEmployeeRole] = useState(null);
  const [showSuccessMessage, setShowSuccessMessage] = useState(false);
  const allowedRoles = ['USER', 'ADMIN'];

  const handleSubmit = async (e) => {
    setShowSuccessMessage(true);
    e.preventDefault();
      const response = await axios.post('/employee', { name, email, password });
      setName('');
      setEmail('');
      console.log(response.data);
      
      console.log("Employee saved successfully");
  };

  return (
    <div>
        <Dashboard />
        <br />
        <form style={{display: 'flex', flexDirection: 'column', alignItems: 'center'}} onSubmit={handleSubmit}>
            <Typography variant="h4">
                Add Employee
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
                    label="Email"
                    variant="outlined"
                    fullWidth
                    required
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
            </div>
            <br />
            <div>
                <TextField
                    label="Password"
                    variant="outlined"
                    type='password'
                    fullWidth
                    required
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
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
                Employee added successfully
            </Alert>
        </Snackbar>
    </div>
  );
};

export default AddEmployee;

/*
<div>
            <Autocomplete
                id="employee-role"
                style={{ width: '100%'}}
                disablePortal
                options={allowedRoles}
                value={employeeRole}
                onChange={(event, value) => setEmployeeRole(value)}
                renderInput={(params) => (
                    <TextField 
                    {...params} 
                    label="Pick Role"
                    margin='normal'
                    style={{ width: '300px'}}
                    variant="outlined" 
                    />
                )}
                />
            </div>
            <br />
*/


