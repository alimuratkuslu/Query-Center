import React, { useState, useEffect } from 'react';
import { TextField, Button, Card, Table, TableBody, TableCell, TableContainer, TableRow, Paper, Autocomplete, Box, Modal } from '@mui/material';
import IconButton from '@material-ui/core/IconButton';
import CreateIcon from '@material-ui/icons/Create';
import axios from 'axios';

function SearchTeam() {
  const [searchTerm, setSearchTerm] = useState('');
  const [searchResults, setSearchResults] = useState([]);
  const [allTeams, setAllTeams] = useState([]);
  const [allEmployees, setAllEmployees] = useState([]);
  const [selectedEmployee, setSelectedEmployee] = useState(null);
  const [addEmployeeModal, setAddEmployeeModal] = useState(false);
  const [selectedTeam, setSelectedTeam] = useState(null);

  const handleSearch = async () => {
    try {
      console.log("Search Term is here: ");
      console.log(searchTerm);
      const response = await fetch(`/team/searchTeam?name=${searchTerm}`);
      const data = await response.json();
      setSelectedTeam(data._id);
      console.log(data);
      setSearchResults(Array.isArray(data) ? data : [data]);
    } catch (error) {
      console.error(error);
      setSearchResults([]);
    }
  };

  useEffect(() => {

    const fetchEmployees = async () => {
      const employeeData = await fetch('/employee');
      const employeeJson = await employeeData.json();
      console.log(employeeJson);
      setAllEmployees(employeeJson);
  };
  fetchEmployees();

    const fetchTeams = async () => {
        const teamData = await fetch('/team');
        const teamJson = await teamData.json();
        console.log(teamJson);
        setAllTeams(teamJson);
    };
    fetchTeams();
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

  const addEmployeeToTeam = async () => {
    try {
      const response = await axios.post('/team/addEmployee', { 
        employeeId: selectedEmployee._id, 
        teamId: selectedTeam});
      
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
            id="search-teams"
            style={{ width: '50%'}}
            disablePortal
            options={allTeams}
            getOptionLabel={option => option.name}
            value={allTeams.find(team => team.name === searchTerm) || null}
            onChange={handleAutocompleteChange}
            renderInput={(params) => (
            <TextField
                {...params}
                label="Search Teams"
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
          <h3 style={{ margin: '0.5rem' }}> Team Name: {result.name}</h3>
          <TableContainer component={Paper}>
            <Table aria-label="team attributes">
              <TableBody>
                <TableRow>
                  <TableCell component="th" scope="row">Employees</TableCell>
                  <TableCell align="left">
                      {result.employees.length === 0 ? (
                        'No Employees Found'
                        ) : (
                        <ul style={{ margin: 0, paddingInlineStart: '1rem' }}>
                          {result.employees.map(employee => (
                            <li key={employee._id}>{employee.name}</li>
                          ))}
                        </ul>
                    )}
                    <IconButton style={{ marginRight: '300px'}} aria-label='addEmployee' onClick={() => setAddEmployeeModal(!addEmployeeModal)}>
                      <CreateIcon />
                    </IconButton>
                    <Modal open={addEmployeeModal} onClose={() => setAddEmployeeModal(false)}>
                      <Box sx={{position: 'absolute', top: '35%', left: '35%', width: 400, boxShadow: 4, p: 4, bgcolor: 'background.paper'}}>
                      <Autocomplete
                            id="search-employees"
                            style={{ width: '50%'}}
                            disablePortal
                            options={allEmployees}
                            getOptionLabel={option => option.name}
                            value={selectedEmployee}
                            onChange={(event, value) => setSelectedEmployee(value)}
                            renderInput={params => (
                            <TextField
                                {...params}
                                label="Pick Employee"
                                margin='normal'
                                variant="outlined"
                                fullWidth
                            />
                            )}
                        />
                        <Button variant='contained' color='primary' style={{ marginLeft: '1rem' }} onClick={() => addEmployeeToTeam()}>Submit</Button>
                        <Button variant='contained' color='primary' style={{ marginLeft: '1rem' }} onClick={() => setAddEmployeeModal(false)}>Cancel</Button>
                      </Box>
                  </Modal>
                  </TableCell>
                </TableRow>
                <TableRow>
                  <TableCell component="th" scope="row">Team Mail</TableCell>
                  <TableCell align="left">
                    {result.teamMail}
                  </TableCell>
                </TableRow>
              </TableBody>
            </Table>
          </TableContainer>
        </Card>
      ))}
      </div>
    </div>
  );
}

export default SearchTeam;