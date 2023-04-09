import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { TextField, Button, Typography, Snackbar, Alert, Autocomplete } from '@mui/material';
import Dashboard from '../Dashboard';

const AddReport = () => {
  const [name, setName] = useState('');
  const [sqlQuery, setSqlQuery] = useState('');
  const [databases, setDatabases] = useState([]);
  const [selectedDatabase, setSelectedDatabase] = useState(null);
  const [requestId, setRequestId] = useState('');
  const [showSuccessMessage, setShowSuccessMessage] = useState(false);

  useEffect(() => {

    const fetchDatabases = async () => {
        const databaseData = await fetch('/database');
        const databaseJson = await databaseData.json();
        console.log(databaseJson);
        setDatabases(databaseJson);
    };
    fetchDatabases();

  }, []);

  const handleSubmit = async (e) => {
    console.log("Selected database name: ", selectedDatabase.name);
    setShowSuccessMessage(true);
    e.preventDefault();
      const response = await axios.post('/report', { name, sqlQuery, databaseName: selectedDatabase.name});
      setName('');
      setSqlQuery('');
      console.log(response.data);
      console.log("Report saved successfully");
  };

  return (
    <div>
        <Dashboard />
        <br />
        <form style={{display: 'flex', flexDirection: 'column', alignItems: 'center'}} onSubmit={handleSubmit}>
            <Typography variant="h4">
                Add Report
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
                    label="SqlQuery"
                    variant="outlined"
                    fullWidth
                    required
                    value={sqlQuery}
                    onChange={(e) => setSqlQuery(e.target.value)}
                />
            </div>
            <br />
            <div>
                <Autocomplete
                    id="search-databases"
                    style={{ width: '100%'}}
                    disablePortal
                    options={databases}
                    getOptionLabel={option => option.name}
                    value={selectedDatabase}
                    onChange={(event, value) => setSelectedDatabase(value)}
                    renderInput={(params) => (
                        <TextField 
                        {...params} 
                        label="Pick Database"
                        margin='normal'
                        style={{ width: '300px'}}
                        variant="outlined" 
                    />
                    )}
                />
            </div>
            <br />
            <div>
                <TextField
                    label="Request ID"
                    variant="outlined"
                    fullWidth
                    required
                    value={requestId}
                    onChange={(e) => setRequestId(e.target.value)}
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
                Report added successfully
            </Alert>
        </Snackbar>
    </div>
  );
};

export default AddReport;


