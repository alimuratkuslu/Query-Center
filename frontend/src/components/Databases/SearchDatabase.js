import React, { useState, useEffect } from 'react';
import { TextField, Button, Card, Table, TableBody, TableCell, TableContainer, TableRow, Paper, Autocomplete, Box, Typography } from '@mui/material';

function SearchDatabase() {
  const [searchTerm, setSearchTerm] = useState('');
  const [searchResults, setSearchResults] = useState([]);
  const [allDatabases, setAllDatabases] = useState([]);
  const [updateModalOpen, setUpdateModalOpen] = useState(false);
  const [selectedDatabase, setSelectedDatabase] = useState(null);
  const [showUpdateSuccess, setShowUpdateSuccess] = useState(false);

  const handleSearch = async () => {
    try {
      const response = await fetch(`/database/searchDatabase?name=${searchTerm}`);
      const data = await response.json();
      setSelectedDatabase(data._id);
      console.log(data);
      setSearchResults(Array.isArray(data) ? data : [data]);
    } catch (error) {
      console.error(error);
      setSearchResults([]);
    }
  };

  useEffect(() => {
    const fetchDatabases = async () => {
        const databaseData = await fetch('/database');
        const databaseJson = await databaseData.json();
        console.log(databaseJson);
        setAllDatabases(databaseJson);
    };
    fetchDatabases();
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
            id="search-databases"
            style={{ width: '50%'}}
            disablePortal
            options={allDatabases}
            getOptionLabel={option => option.name}
            value={allDatabases.find(database => database.name === searchTerm) || null}
            onChange={handleAutocompleteChange}
            renderInput={(params) => (
            <TextField
                {...params}
                label="Search Databases"
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
          <h3 style={{ margin: '0.5rem' }}> Database Name: {result.name}</h3>
          <TableContainer component={Paper}>
            <Table aria-label="database attributes">
              <TableBody>
                <TableRow>
                  <TableCell component="th" scope="row">Connection String</TableCell>
                  <TableCell align="left">
                        {result.connectionString}
                  </TableCell>
                </TableRow>
                <TableRow>
                    <TableCell component='th' scope='row'>Reports</TableCell>
                    <TableCell align='left'>
                    {result.reports.length === 0 ? (
                        'No Reports Found'
                        ) : (
                        <ul style={{ margin: 0, paddingInlineStart: '1rem' }}>
                            {result.reports.map(report => (
                            <li key={report._id}>{report.name}</li>
                            ))}
                        </ul>
                    )}
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

export default SearchDatabase;