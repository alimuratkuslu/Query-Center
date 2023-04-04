import React, { useState, useEffect } from 'react';
import { TextField, Button, Card, Modal, Autocomplete, Box, Snackbar, Alert } from '@mui/material';

function SearchTrigger() {
  const [searchTerm, setSearchTerm] = useState('');
  const [searchResults, setSearchResults] = useState([]);
  const [allTriggers, setAllTriggers] = useState([]);
  const [selectedTrigger, setSelectedTrigger] = useState(null);
  const [newTriggerName, setNewTriggerName] = useState('');
  const [updateModalOpen, setUpdateModalOpen] = useState(false);
  const [showUpdateSuccess, setShowUpdateSuccess] = useState(false);

  const handleSearch = async () => {
    try {
      const response = await fetch(`/trigger/searchTrigger?name=${searchTerm}`);
      const data = await response.json();
      setSelectedTrigger(data._id);
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

  const handleNameChange = async () => {
    try {
      const response = await fetch(`/trigger/addName/${selectedTrigger}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ name: newTriggerName })
      });
      const data = await response.json();
      setUpdateModalOpen(false);
      setShowUpdateSuccess(true);
      console.log(data);
    } catch (error) {
      console.error(error);
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
          <Box sx={{flex: 1, marginLeft: '8px'}}>
            <Button variant='outlined' style={{ flex: 1}} onClick={() => setUpdateModalOpen(true)}>Update Trigger Name</Button>
          </Box>
          <Modal open={updateModalOpen} onClose={() => setUpdateModalOpen(false)}>
            <Box sx={{position: 'absolute', top: '35%', left: '35%', width: 400, boxShadow: 4, p: 4, bgcolor: 'background.paper'}}>
              <TextField label="New Trigger Name" multiline rows={4} variant="outlined" value={newTriggerName} onChange={(e) => setNewTriggerName(e.target.value)} />
              <Button variant='contained' color='primary' style={{ marginLeft: '1rem' }} onClick={() => handleNameChange()}>Save</Button>
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
                    Trigger Name Successfully Updated
                </Alert>
            </Snackbar>
          )}    
        </Card>
      ))}            
      </div>
    </div>
  );
}

export default SearchTrigger;