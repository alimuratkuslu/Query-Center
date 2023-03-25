import React, { useState, useEffect } from 'react';
import { TextField, Button, Card, Table, TableBody, TableCell, TableContainer, TableRow, Paper, Modal, Autocomplete } from '@mui/material';

function SearchSchedule() {
  const [searchTerm, setSearchTerm] = useState('');
  const [searchResults, setSearchResults] = useState([]);
  const [allSchedules, setAllSchedules] = useState([]);
  const [showModal, setShowModal] = useState(false);

  const handleSearch = async () => {
    try {
      const response = await fetch(`/schedule/searchSchedule?name=${searchTerm}`);
      const data = await response.json();
      console.log(data);
      setSearchResults(Array.isArray(data) ? data : [data]);
    } catch (error) {
      console.error(error);
      setSearchResults([]);
    }
  };

  useEffect(() => {
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
                  <TableCell align="left">
                        {result.mailSubject}
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
                  <TableCell align="left">
                    <ul style={{ margin: 0, paddingInlineStart: '1rem' }}>
                      {result.triggers.map(trigger => (
                        <li key={trigger._id}>{trigger.name}</li>
                      ))}
                    </ul>
                  </TableCell>
                </TableRow>
              </TableBody>
            </Table>
          </TableContainer>
          <Modal open={showModal} onClose={() => setShowModal(false)}>
            <div style={{ padding: '1rem', fontSize: '26px' }}>
                <pre>
                  <ul>
                    {result.recipients.map((item, index) => (
                      <li key={index}>{item}</li>
                    ))}
                  </ul>
                </pre>
            </div>
          </Modal>
        </Card>
      ))}
      </div>
    </div>
  );
}

export default SearchSchedule;