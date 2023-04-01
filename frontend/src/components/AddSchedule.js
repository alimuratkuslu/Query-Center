import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { TextField, Button, Typography, Snackbar, Alert, Autocomplete } from '@mui/material';
import Dashboard from './Dashboard';

const AddSchedule = () => {
  const [name, setName] = useState('');
  const [mailSubject, setMailSubject] = useState('');
  const [recipients, setRecipients] = useState([]);
  const [showSuccessMessage, setShowSuccessMessage] = useState(false);
  const [employees, setEmployees] = useState([]);
  const [triggers, setTriggers] = useState([]);
  const [requestId, setRequestId] = useState('');
  const [selectedTriggers, setSelectedTriggers] = useState([]);

  useEffect(() => {

    const fetchEmployees = async () => {
        const employeeData = await fetch('/employee');
        const employeeJson = await employeeData.json();
        console.log(employeeJson);
        setEmployees(employeeJson);
    };
    fetchEmployees();

    const fetchTriggers = async () => {
        const triggerData = await fetch('/trigger');
        const triggerJson = await triggerData.json();
        console.log(triggerJson);
        setTriggers(triggerJson);
    };
    fetchTriggers();

  }, []);

  const handleSubmit = async (e) => {
    setShowSuccessMessage(true);
    e.preventDefault();
      const recipientNames = recipients.map((recipient) => recipient.name);
      const response = await axios.post('/schedule', { name, mailSubject, recipients: recipientNames, triggers: selectedTriggers });
      setName('');
      setMailSubject('');
      setRecipients([]);
      console.log(response.data);
      console.log("Schedule saved successfully");
  };

  return (
    <div>
        <Dashboard />
        <form style={{display: 'flex', flexDirection: 'column', alignItems: 'center'}} onSubmit={handleSubmit}>
            <Typography variant="h4">
                Add Schedule
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
                    label="Mail Subject"
                    variant="outlined"
                    fullWidth
                    required
                    value={mailSubject}
                    onChange={(e) => setMailSubject(e.target.value)}
                />
            </div>
            <br />
            <div>
                <Autocomplete
                    multiple
                    id="search-recipients"
                    style={{ width: '100%'}}
                    disablePortal
                    options={employees}
                    getOptionLabel={option => option.name}
                    value={recipients}
                    onChange={(event, value) => setRecipients(value)}
                    renderInput={(params) => (
                        <TextField 
                        {...params} 
                        label="Pick Recipients"
                        margin='normal'
                        style={{ width: '300px'}}
                        variant="outlined" 
                    />
                    )}
                />
            </div>
            <br />
            <div>
                <Autocomplete
                    multiple
                    id="search-triggers"
                    style={{ width: '100%'}}
                    disablePortal
                    options={triggers}
                    getOptionLabel={option => option.name}
                    value={selectedTriggers}
                    onChange={(event, value) => setSelectedTriggers(value)}
                    renderInput={(params) => (
                        <TextField 
                        {...params} 
                        label="Pick Triggers"
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
                Schedule added successfully
            </Alert>
        </Snackbar>
    </div>
  );
};

export default AddSchedule;


