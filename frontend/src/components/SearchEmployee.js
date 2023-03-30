import React, { useState, useEffect } from 'react';
import { TextField, Button, Card, Table, TableBody, TableCell, TableContainer, TableRow, Paper, Autocomplete } from '@mui/material';

function SearchEmployee() {
  const [searchTerm, setSearchTerm] = useState('');
  const [searchResults, setSearchResults] = useState([]);
  const [allEmployees, setAllEmployees] = useState([]);

  const handleSearch = async () => {
    try {
      const response = await fetch(`/employee/searchEmployee?name=${searchTerm}`);
      const data = await response.json();
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
            id="search-employees"
            style={{ width: '50%'}}
            disablePortal
            options={allEmployees}
            getOptionLabel={option => option.name}
            value={allEmployees.find(employee => employee.name === searchTerm) || null}
            onChange={handleAutocompleteChange}
            renderInput={params => (
            <TextField
                {...params}
                label="Search Employees"
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
            <Table aria-label="employee attributes">
              <TableBody>
                <TableRow>
                  <TableCell component="th" scope="row">Email:</TableCell>
                  <TableCell align="left">
                        {result.email}
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

export default SearchEmployee;