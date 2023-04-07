import React, { useState, useEffect } from 'react';
import { TextField, Button, Card, Table, TableBody, TableCell, TableContainer, TableRow, Paper, Modal, Autocomplete, Box, Typography, Snackbar, Alert } from '@mui/material';
import IconButton from '@material-ui/core/IconButton';
import CreateIcon from '@material-ui/icons/Create';
import axios from 'axios';

function SearchSchedule() {
  const [searchTerm, setSearchTerm] = useState('');
  const [searchResults, setSearchResults] = useState([]);
  const [allSchedules, setAllSchedules] = useState([]);
  const [selectedTrigger, setSelectedTrigger] = useState(null);
  const [allTriggers, setAllTriggers] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [selectedSchedule, setSelectedSchedule] = useState(null);
  const [newMail, setNewMail] = useState('');
  const [addTriggerModal, setAddTriggerModal] = useState(false);
  const [updateModalOpen, setUpdateModalOpen] = useState(false);
  const [showUpdateSuccess, setShowUpdateSuccess] = useState(false);
  const [requestId, setRequestId] = useState('');

  const handleSearch = async () => {
    try {
      const response = await fetch(`/schedule/searchSchedule?name=${searchTerm}`);
      const data = await response.json();
      setSelectedSchedule(data._id)
      console.log(data);
      setSearchResults(Array.isArray(data) ? data : [data]);
    } catch (error) {
      console.error(error);
      setSearchResults([]);
    }
  };

  useEffect(() => {

    const fetchTriggers = async () => {
      const triggerData = await fetch('/trigger');
      const triggerJson = await triggerData.json();
      console.log(triggerJson);
      setAllTriggers(triggerJson);
    };
    fetchTriggers();

    const fetchSchedules = async () => {
        const scheduleData = await fetch('/schedule');
        const scheduleJson = await scheduleData.json();
        console.log(scheduleJson);
        setAllSchedules(scheduleJson);
    };
    fetchSchedules();
  }, []);

  const handleInputChange = (event) => {
    setSearchTerm(event.target.value);
  };

  const handleAutocompleteChange = (event, value) => {
    if (value) {
      setSearchTerm(value.name);
    } else {
      setSearchTerm("");
    }
  };

  const handleSubjectChange = async () => {
    try {
      const response = await fetch(`/schedule/addSubject/${selectedSchedule}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ subject: newMail })
      });
      const data = await response.json();
      setUpdateModalOpen(false);
      setShowUpdateSuccess(true);
      console.log(data);
    } catch (error) {
      console.error(error);
    }
  };

  const addTriggerToReport = async () => {
    try {
      const response = await axios.post('/schedule/addTrigger', { 
        scheduleId: selectedSchedule, 
        triggerId: selectedTrigger._id});
      
      const result = response.data;
      console.log(result);

    } catch (error) {
      console.error('There was a problem with the fetch operation:', error);
    }
  };

  return (
    <div>
      <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
      <br />
      <Autocomplete
            id="search-schedules"
            style={{ width: '50%'}}
            disablePortal
            options={allSchedules}
            getOptionLabel={option => option.name}
            value={allSchedules.find(schedule => schedule.name === searchTerm) || null}
            onChange={handleAutocompleteChange}
            renderInput={params => (
            <TextField
                {...params}
                label="Search Schedules"
                margin='normal'
                variant="outlined"
                fullWidth
                value={searchTerm}
                onChange={handleInputChange}
            />
            )}
        />
      <br />
      <Button variant="contained" color="primary" onClick={handleSearch}>
        Search
      </Button>
      {searchResults.map(result => (
        <Card key={result._id} style={{ width: '80%', margin: '1rem 0' }}>
          <h3 style={{ margin: '0.5rem' }}>{result.name}</h3>
          <TableContainer component={Paper}>
            <Table aria-label="schedule attributes">
              <TableBody>
                <TableRow>
                  <TableCell component="th" scope="row">Mail Subject:</TableCell>
                  <TableCell align="left" style={{display: 'flex', justifyContent: 'space-between'}}>
                    <Box sx={{flex: 0.3}}>
                      {result.mailSubject} 
                    </Box>   
                    <Box sx={{flex: 1, marginLeft: '8px'}}>
                      <Button variant='outlined' style={{ flex: 1}} onClick={() => setUpdateModalOpen(true)}>Update Mail Subject</Button>
                    </Box>
                    <Modal open={updateModalOpen} onClose={() => setUpdateModalOpen(false)}>
                      <Box sx={{position: 'absolute', top: '35%', left: '35%', width: 400, boxShadow: 4, p: 4, bgcolor: 'background.paper'}}>
                        <TextField label="New Mail Subject" multiline rows={4} variant="outlined" value={newMail} onChange={(e) => setNewMail(e.target.value)} />
                        <TextField label="Request ID" style={{ marginTop: '16px', width: '50%', height: '10%' }} helperText="* Necessary" multiline rows={4} variant="outlined" value={requestId} onChange={(e) => setRequestId(e.target.value)} />
                        <Button variant='contained' color='primary' style={{ marginLeft: '1rem' }} onClick={() => handleSubjectChange()}>Save</Button>
                        <Button variant='contained' color='primary' style={{ marginLeft: '1rem' }} onClick={() => setUpdateModalOpen(false)}>Cancel</Button>
                      </Box>
                    </Modal>
                    {showUpdateSuccess && (
                      <Snackbar
                          anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
                          open={showUpdateSuccess}
                          autoHideDuration={3000}
                          onClose={() => setShowUpdateSuccess(false)}
                      >
                          <Alert severity="success" onClose={() => setShowUpdateSuccess(false)}>
                              Mail Subject Successfully Updated
                          </Alert>
                      </Snackbar>
                    )}                                    
                  </TableCell>
                </TableRow>
                <TableRow>
                  <TableCell component="th" scope="row">Recipients:</TableCell>
                  <TableCell align="left">
                          <Button variant='outlined' onClick={() => setShowModal(!showModal)}>Show Recipients</Button>
                  </TableCell>
                </TableRow>
                <TableRow>
                  <TableCell component="th" scope="row">Triggers:</TableCell>
                  <TableCell align="left" style={{display: 'flex', justifyContent: 'space-between'}}>
                  {result.triggers.length === 0 ? (
                      'No Trigger Found'
                    ) : (
                      <ul style={{ margin: 0, paddingInlineStart: '1rem' }}>
                        {result.triggers.map(trigger => (
                          <li key={trigger._id}>{trigger.name}</li>
                        ))}
                      </ul>
                  )}
                  <IconButton aria-label='addSchedule' onClick={() => setAddTriggerModal(!addTriggerModal)}>
                    <CreateIcon />
                  </IconButton>
                  <Modal open={addTriggerModal} onClose={() => setAddTriggerModal(false)}>
                      <Box sx={{position: 'absolute', top: '35%', left: '35%', width: 400, boxShadow: 4, p: 4, bgcolor: 'background.paper'}}>
                      <Autocomplete
                            id="search-triggers"
                            style={{ width: '50%'}}
                            disablePortal
                            options={allTriggers}
                            getOptionLabel={option => option.name}
                            value={selectedTrigger}
                            onChange={(event, value) => setSelectedTrigger(value)}
                            renderInput={params => (
                            <TextField
                                {...params}
                                label="Pick Trigger"
                                margin='normal'
                                variant="outlined"
                                fullWidth
                            />
                            )}
                        />
                        <Button variant='contained' color='primary' style={{ marginLeft: '1rem' }} onClick={() => addTriggerToReport()}>Submit</Button>
                        <Button variant='contained' color='primary' style={{ marginLeft: '1rem' }} onClick={() => setAddTriggerModal(false)}>Cancel</Button>
                      </Box>
                  </Modal>
                  </TableCell>
                </TableRow>
              </TableBody>
            </Table>
          </TableContainer>
          <Modal open={showModal} onClose={() => setShowModal(false)}>
            <Box sx={{position: 'absolute', top: '35%', left: '35%', width: 400, boxShadow: 24, p: 4, bgcolor: 'background.paper'}}>
                <Typography variant='h6' component='h2'>
                  Recipients
                </Typography>
                <Typography sx={{ mt: 2 }}>
                  <ul>
                    {result.recipients.map((item, index) => (
                      <li key={index}>{item}</li>
                    ))}
                  </ul>
                </Typography>
            </Box>
          </Modal>
        </Card>
      ))}
      </div>
    </div>
  );
}

export default SearchSchedule;