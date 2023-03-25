import React, { useState, useEffect } from 'react';
import { TextField, Button, Card, Table, TableBody, TableCell, TableContainer, TableRow, Paper, Autocomplete, Checkbox } from '@mui/material';

function SearchOwnership() {
  const [searchTerm, setSearchTerm] = useState('');
  const [searchResults, setSearchResults] = useState(null);
  const [allOwnerships, setAllOwnerships] = useState([]);

  const handleSearch = async () => {
    try {
      const response = await fetch(`/ownership/searchOwnership?name=${searchTerm}`);
      const data = await response.json();
      console.log(data);
      setSearchResults(Array.isArray(data) ? data : [data]);
    } catch (error) {
      console.error(error);
      setSearchResults([]);
    }
  };

  useEffect(() => {
    const fetchOwnerships = async () => {
        const ownershipData = await fetch('/ownership');
        const ownershipJson = await ownershipData.json();
        console.log(ownershipJson);
        setAllOwnerships(ownershipJson);
    };
    fetchOwnerships();
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
            id="search-ownerships"
            style={{ width: '50%'}}
            disablePortal
            options={allOwnerships}
            getOptionLabel={option => option.report.name}
            value={allOwnerships.find(ownership => ownership.report.name === searchTerm) || null}
            onChange={handleAutocompleteChange}
            renderInput={params => (
            <TextField
                {...params}
                label="Search Ownerships"
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
      {searchResults && (
      <Card style={{ width: '80%', margin: '1rem 0' }}>
        <h3 style={{ margin: '0.5rem' }}>{searchResults.report.name}</h3>
        <TableContainer component={Paper}>
          <Table aria-label="ownership attributes">
            <TableBody>
              <TableRow>
                <TableCell component="th" scope="row">Employee:</TableCell>
                <TableCell align="left">{searchResults.employee.name}</TableCell>
              </TableRow>
              <TableRow>
                <TableCell component="th" scope="row">Owner:</TableCell>
                <TableCell align="left">
                  <Checkbox checked={searchResults.owner} disabled />
                </TableCell>
              </TableRow>
              <TableRow>
                <TableCell component="th" scope="row">Read:</TableCell>
                <TableCell align="left">
                  <Checkbox checked={searchResults.read} disabled />
                </TableCell>
              </TableRow>
              <TableRow>
                <TableCell component="th" scope="row">Write:</TableCell>
                <TableCell align="left">
                  <Checkbox checked={searchResults.write} disabled />
                </TableCell>
              </TableRow>
              <TableRow>
                <TableCell component="th" scope="row">Run:</TableCell>
                <TableCell align="left">
                  <Checkbox checked={searchResults.run} disabled />
                </TableCell>
              </TableRow>
            </TableBody>
          </Table>
        </TableContainer>
      </Card>
    )}
      </div>
    </div>
  );
}

export default SearchOwnership;