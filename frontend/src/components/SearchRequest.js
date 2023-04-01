import React, { useState, useEffect } from 'react';
import { TextField, Button, Card, Table, TableBody, TableCell, TableContainer, TableRow, Paper, Autocomplete, Box, Typography } from '@mui/material';

function SearchRequest() {
  const [searchTerm, setSearchTerm] = useState('');
  const [searchResults, setSearchResults] = useState([]);
  const [allRequests, setAllRequests] = useState([]);

  const handleSearch = async () => {
    try {
      console.log("Search Term is here: ");
      console.log(searchTerm);
      const response = await fetch(`/request/searchRequest?id=${searchTerm}`);
      const data = await response.json();
      console.log(data);
      setSearchResults(Array.isArray(data) ? data : [data]);
    } catch (error) {
      console.error(error);
      setSearchResults([]);
    }
  };

  useEffect(() => {
    const fetchRequests = async () => {
        const requestData = await fetch('/request');
        const requestJson = await requestData.json();
        console.log(requestJson);
        setAllRequests(requestJson);
    };
    fetchRequests();
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
            id="search-requests"
            style={{ width: '50%'}}
            disablePortal
            options={allRequests}
            getOptionLabel={option => option._id.toString()}
            value={allRequests.find(request => request._id.toString() === searchTerm) || null}
            onChange={handleAutocompleteChange}
            renderInput={(params) => (
            <TextField
                {...params}
                label="Search Requests"
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
          <h3 style={{ margin: '0.5rem' }}> Request ID: {result._id}</h3>
          <TableContainer component={Paper}>
            <Table aria-label="request attributes">
              <TableBody>
                <TableRow>
                  <TableCell component="th" scope="row">Description:</TableCell>
                  <TableCell align="left">
                        {result.description}
                  </TableCell>
                </TableRow>
                <TableRow>
                  <TableCell component="th" scope="row">Status:</TableCell>
                  <TableCell align="left">
                  {result.status === "IN_PROGRESS" && 
                    <span
                      style={{
                        color: "orange",
                        border: "1px solid orange",
                        borderRadius: "5px",
                        padding: "0.2rem",
                      }}
                    >
                      In Progress
                    </span>
                  }
                  {result.status === 'DONE' && 
                    <span
                      style={{
                        color: "green",
                        border: "1px solid green",
                        borderRadius: "5px",
                        padding: "0.2rem",
                      }}
                    >
                      Done
                    </span>
                  }
                  {result.status === 'REJECTED' && 
                    <span
                      style={{
                        color: "red",
                        border: "1px solid red",
                        borderRadius: "5px",
                        padding: "0.2rem",
                      }}
                    >
                      Rejected
                    </span>
                  }
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

export default SearchRequest;