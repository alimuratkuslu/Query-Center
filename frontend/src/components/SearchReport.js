import React, { useState, useEffect } from 'react';
import { TextField, Button, Card, Table, TableBody, TableCell, TableContainer, TableRow, Paper, Modal, Autocomplete, Box, Typography, Snackbar, Alert } from '@mui/material';
import { Tabs, Tab } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import { Checkbox } from '@mui/material';

function SearchReport() {
  const [searchTerm, setSearchTerm] = useState('');
  const [searchResults, setSearchResults] = useState([]);
  const [allReports, setAllReports] = useState([]);
  const [selectedReport, setSelectedReport] = useState(null);
  const [selectedReportName, setSelectedReportName] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [updateModalOpen, setUpdateModalOpen] = useState(false);
  const [newQuery, setNewQuery] = useState('');
  const [requestId, setRequestId] = useState('');
  const [showUpdateSuccess, setShowUpdateSuccess] = useState(false);

  const [selectedTab, setSelectedTab] = useState(0);
  const [ownerships, setOwnerships] = useState([]);
  const filteredOwnerships = ownerships.filter((ownership) => ownership.report.name === selectedReportName);

  const handleSearch = async () => {
    try {
      const response = await fetch(`/report/searchReport?name=${searchTerm}`);
      const data = await response.json();
      setSelectedReport(data._id);
      setSelectedReportName(data.name);
      setSearchResults(Array.isArray(data) ? data : [data]);
    } catch (error) {
      console.error(error);
      setSearchResults([]);
    }
  };

  const getRowId = (row) => row._id;

  useEffect(() => {
    const fetchReports = async () => {
        const reportData = await fetch('/report');
        const reportJson = await reportData.json();
        console.log(selectedReport);
        setAllReports(reportJson);
    };
    fetchReports();

    const fetchOwnerships = async () => {
      const ownershipData = await fetch('/ownership');
      const ownershipJson = await ownershipData.json();
      console.log(ownershipJson);
      setOwnerships(ownershipJson);
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

  const handleQueryUpdate = async () => {
    try {
      const response = await fetch(`/report/addQuery/${selectedReport}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ query: newQuery })
      });
      const data = await response.json();
      setUpdateModalOpen(false);
      setShowUpdateSuccess(true);
      console.log(data);
    } catch (error) {
      console.error(error);
    }
  };

  const activateReport = async () => {
    fetch(`/report/activate/${selectedReport}`, {
      method: 'PATCH',
    })
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      console.log(response.json());
    })
    .catch(error => {
      console.error('There was a problem with the fetch operation:', error);
    }); 
  };

  const deactivateReport = async () => {
      fetch(`/report/${selectedReport}`, {
        method: 'PATCH',
      })
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        console.log(response.json());
      })
      .catch(error => {
        console.error('There was a problem with the fetch operation:', error);
      });
  };

  return (
    <div>
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
          <Tabs value={selectedTab} onChange={(event, newValue) => setSelectedTab(newValue)}>
            <Tab label="SQL Query" />
            <Tab label="Ownerships" />
          </Tabs>
          {selectedTab === 0 && (
            <TableContainer component={Paper}>
            <Table aria-label="report attributes">
              <TableBody>
                <TableRow>
                  <TableCell component="th" scope="row">SQL Query:</TableCell>
                    <TableCell align="left" style={{display: 'flex', justifyContent: 'space-between'}}>
                          <Button variant='outlined' style={{flex: 1}} onClick={() => setShowModal(!showModal)}>Show Query</Button>
                          <Button variant='outlined' style={{ flex: 1, marginLeft: '8px' }} onClick={() => setUpdateModalOpen(true)}>Update Query</Button>
                          <Modal open={updateModalOpen} onClose={() => setUpdateModalOpen(false)}>
                            <Box sx={{position: 'absolute', top: '35%', left: '35%', width: 400, boxShadow: 4, p: 4, bgcolor: 'background.paper'}}>
                              <TextField label="New SQL Query" multiline rows={4} variant="outlined" value={newQuery} onChange={(e) => setNewQuery(e.target.value)} />
                              <TextField label="Request ID" style={{ marginTop: '16px', width: '50%', height: '10%' }} helperText="* Necessary" multiline rows={4} variant="outlined" value={requestId} onChange={(e) => setRequestId(e.target.value)} />
                              <Button variant='contained' color='primary' style={{ marginLeft: '1rem' }} onClick={() => handleQueryUpdate()}>Save</Button>
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
                                    Query Successfully Updated
                                </Alert>
                            </Snackbar>
                          )}
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
                <TableRow>
                  <TableCell component="th" scope="row">Active Status:</TableCell>
                  <TableCell align="left">
                    <Checkbox checked={result.active} disabled />
                    <Button variant='contained' color='success' style={{ marginLeft: '1rem' }} onClick={() => activateReport()}>Activate</Button>
                    <Button variant='outlined' color='error' style={{ marginLeft: '1rem' }} onClick={() => deactivateReport()}>Deactivate</Button>
                  </TableCell>
                </TableRow>
              </TableBody>
            </Table>
          </TableContainer>
          )}
          {selectedTab === 1 && (
            <TableContainer component={Paper}>
            <Table aria-label="ownership attributes">
              <TableBody>            
                <br />                               
                <div style={{ height: '65vh', width: '100%', display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                  <div style={{width: '80%', height: 450}}>
                    <DataGrid rows={filteredOwnerships} columns={[
                      { field: '_id', headerName: 'ID', width: 70 },

                      { field: 'employee.name', headerName: 'Employee Name', width: 130, valueGetter: (params) => (
                        params.row.employee.name
                      ), },

                      { field: 'owner', headerName: 'Ownership', width: 100, renderCell: (params) => (
                          <Checkbox checked={params.value} disabled />
                        ),
                      },
                      { field: 'read', headerName: 'Read', width: 50, renderCell: (params) => (
                          <Checkbox checked={params.value} disabled />
                        ),
                      },
                      { field: 'write', headerName: 'Write', width: 50, renderCell: (params) => (
                          <Checkbox checked={params.value} disabled />
                        ),
                      },
                      { field: 'run', headerName: 'Run', width: 50, renderCell: (params) => (
                          <Checkbox checked={params.value} disabled />
                        ),
                      },
                    ]} getRowId={getRowId} pageSize={5} />
                  </div>
                </div>
              </TableBody>
            </Table>
          </TableContainer>
          )}
          <Modal open={showModal} onClose={() => setShowModal(false)}>
            <Box sx={{position: 'absolute', top: '35%', left: '35%', width: 400, boxShadow: 24, p: 4, bgcolor: 'background.paper'}}>
              <Typography variant='h6' component='h2'>
                SQL Query
              </Typography>
              <Typography sx={{ mt: 2 }}>
                {result.sqlQuery}
              </Typography>
            </Box>
          </Modal>
        </Card>
      ))}
      </div>
    </div>
  );
}

export default SearchReport;