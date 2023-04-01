import React, { useState, useEffect } from 'react';
import { TextField, Button, Card, Table, TableBody, TableCell, TableContainer, TableRow, Paper, Autocomplete, Box, Typography } from '@mui/material';

function SearchTeam() {
  const [searchTerm, setSearchTerm] = useState('');
  const [searchResults, setSearchResults] = useState([]);
  const [allTeams, setAllTeams] = useState([]);
  const [updateModalOpen, setUpdateModalOpen] = useState(false);
  const [selectedTeam, setSelectedTeam] = useState(null);
  const [showUpdateSuccess, setShowUpdateSuccess] = useState(false);

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
                    <ul style={{ margin: 0, paddingInlineStart: '1rem' }}>
                      {result.employees.map(employee => (
                        <li key={employee._id}>{employee.name}</li>
                      ))}
                    </ul>
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