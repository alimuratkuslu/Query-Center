import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { TextField, Button, Typography, Snackbar, Alert, Autocomplete, FormControlLabel, Checkbox } from '@mui/material';
import Dashboard from './Dashboard';

const AddOwnership = () => {
  const [selectedReport, setSelectedReport] = useState(null);
  const [selectedEmployee, setSelectedEmployee] = useState(null);
  const [isOwner, setIsOwner] = useState(false);
  const [isRead, setIsRead] = useState(false);
  const [isWrite, setIsWrite] = useState(false);
  const [isRun, setIsRun] = useState(false);

  const [showSuccessMessage, setShowSuccessMessage] = useState(false);

  const [employees, setEmployees] = useState([]);
  const [reports, setReports] = useState([]);

  useEffect(() => {

    const fetchEmployees = async () => {
        const employeeData = await fetch('/employee');
        const employeeJson = await employeeData.json();
        console.log(employeeJson);
        setEmployees(employeeJson);
    };
    fetchEmployees();

    const fetchReports = async () => {
        const reportData = await fetch('/report');
        const reportJson = await reportData.json();
        console.log(reportJson);
        setReports(reportJson);
    };
    fetchReports();

  }, []);

  const handleSubmit = async (e) => {
    setShowSuccessMessage(true);
    e.preventDefault();
      const response = await axios.post('/ownership', { report: selectedReport,
         employee: selectedEmployee,
          isOwner: isOwner,
           isRead: isRead,
            isWrite: isWrite,
             isRun:  isRun});
      console.log(response.data);
      console.log("Ownership saved successfully");
  };

  const handleCheckboxChange = (event) => {
    const { name, checked } = event.target;

    switch (name) {
      case 'IsOwner':
        setIsOwner(checked);
        break;
      case 'IsRead':
        setIsRead(checked);
        break;
      case 'IsWrite':
        setIsWrite(checked);
        break;
      case 'IsRun':
        setIsRun(checked);
        break;
      default:
        break;
    }
  };

  return (
    <div>
        <Dashboard />
        <form style={{display: 'flex', flexDirection: 'column', alignItems: 'center'}} onSubmit={handleSubmit}>
            <Typography variant="h4">
                Add Ownership
            </Typography>
            <br />
            <div>
                <Autocomplete
                    id="search-reports"
                    style={{ width: '100%'}}
                    disablePortal
                    options={reports}
                    getOptionLabel={option => option.name}
                    value={selectedReport}
                    onChange={(event, value) => setSelectedReport(value)}
                    renderInput={(params) => (
                        <TextField 
                        {...params} 
                        label="Pick Report"
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
                    id="search-employees"
                    style={{ width: '100%'}}
                    disablePortal
                    options={employees}
                    getOptionLabel={option => option.name}
                    value={selectedEmployee}
                    onChange={(event, value) => setSelectedEmployee(value)}
                    renderInput={(params) => (
                        <TextField 
                        {...params} 
                        label="Pick Employee"
                        margin='normal'
                        style={{ width: '300px'}}
                        variant="outlined" 
                    />
                    )}
                />
            </div>
            <br />
            <FormControlLabel
                control={<Checkbox checked={isOwner} onChange={handleCheckboxChange} name="IsOwner" />}
                label="Owner"
            />
            <br />
            <FormControlLabel
                control={<Checkbox checked={isRead} onChange={handleCheckboxChange} name="IsRead" />}
                label="Read"
            />
            <br />
            <FormControlLabel
                control={<Checkbox checked={isWrite} onChange={handleCheckboxChange} name="IsWrite" />}
                label="Write"
            />
            <br />
            <FormControlLabel
                control={<Checkbox checked={isRun} onChange={handleCheckboxChange} name="IsRun" />}
                label="Run"
            />
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
                Ownership added successfully
            </Alert>
        </Snackbar>
    </div>
  );
};

export default AddOwnership;


