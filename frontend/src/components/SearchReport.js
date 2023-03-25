import React, { useState, useEffect } from 'react';
import { TextField, Button, Card, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Modal, Autocomplete } from '@mui/material';

function SearchReport() {
  const [searchTerm, setSearchTerm] = useState('');
  const [searchResults, setSearchResults] = useState([]);
  const [allReports, setAllReports] = useState([]);
  const [showModal, setShowModal] = useState(false);

  const handleSearch = async () => {
    try {
      const response = await fetch(`/report/searchReport?name=${searchTerm}`);
      const data = await response.json();
      console.log(data);
      setSearchResults(Array.isArray(data) ? data : [data]);
    } catch (error) {
      console.error(error);
      setSearchResults([]);
    }
  };

  useEffect(() => {
    const fetchReports = async () => {
        const reportData = await fetch('/report');
        const reportJson = await reportData.json();
        console.log(reportJson);
        setAllReports(reportJson);
    };
    fetchReports();
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
    <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
      <br />
      <Autocomplete
            id="search-reports"
            style={{ width: '50%'}}
            disablePortal
            options={allReports}
            getOptionLabel={option => option.name}
            value={allReports.find(report => report.name === searchTerm) || null}
            onChange={handleAutocompleteChange}
            renderInput={params => (
            <TextField
                {...params}
                label="Search Reports"
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
            <Table aria-label="report attributes">
              <TableBody>
                <TableRow>
                  <TableCell component="th" scope="row">SQL Query:</TableCell>
                  <TableCell align="left">
                        <Button variant='outlined' onClick={() => setShowModal(!showModal)}>Show Query</Button>
                    </TableCell>
                </TableRow>
                <TableRow>
                  <TableCell component="th" scope="row">Employees:</TableCell>
                  <TableCell align="left">
                    <ul style={{ margin: 0, paddingInlineStart: '1rem' }}>
                      {result.employees.map(employee => (
                        <li key={employee._id}>{employee.name}</li>
                      ))}
                    </ul>
                  </TableCell>
                </TableRow>
              </TableBody>
            </Table>
          </TableContainer>
          <Modal open={showModal} onClose={() => setShowModal(false)}>
            <div style={{ padding: '1rem' }}>
                <pre>{result.sqlQuery}</pre>
            </div>
          </Modal>
        </Card>
      ))}
    </div>
  );
}

export default SearchReport;