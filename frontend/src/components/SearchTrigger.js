import React, { useState, useEffect } from 'react';
import { TextField, Button, Card, Table, TableBody, TableCell, TableContainer, TableRow, Paper, Modal, Autocomplete } from '@mui/material';

function SearchTrigger() {
  const [searchTerm, setSearchTerm] = useState('');
  const [searchResults, setSearchResults] = useState([]);
  const [allTriggers, setAllTriggers] = useState([]);

  const handleSearch = async () => {
    try {
      const response = await fetch(`/trigger/searchTrigger?name=${searchTerm}`);
      const data = await response.json();
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
            id="search-triggers"
            style={{ width: '50%'}}
            disablePortal
            options={allTriggers}
            getOptionLabel={option => option.name}
            value={allTriggers.find(trigger => trigger.name === searchTerm) || null}
            onChange={handleAutocompleteChange}
            renderInput={params => (
            <TextField
                {...params}
                label="Search Triggers"
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
        </Card>
      ))}
      </div>
    </div>
  );
}

export default SearchTrigger;