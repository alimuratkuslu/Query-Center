import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { TextField, Button, Card, Table, TableBody, TableCell, TableContainer, TableRow, TableHead, Paper, Modal, Autocomplete, Box, Typography, Snackbar, Alert } from '@mui/material';
import { Tabs, Tab } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import { Checkbox } from '@mui/material';
import { Mail } from '@mui/icons-material';
import IconButton from '@material-ui/core/IconButton';
import VisibilityIcon from '@material-ui/icons/Visibility';
import CreateIcon from '@material-ui/icons/Create';
import PlayCircleIcon from '@mui/icons-material/PlayCircle';

function SearchReport() {
  const [searchTerm, setSearchTerm] = useState('');
  const [searchResults, setSearchResults] = useState([]);
  const [allReports, setAllReports] = useState([]);
  const [selectedReportObject, setSelectedReportObject] = useState(null);
  const [selectedReport, setSelectedReport] = useState(null);
  const [selectedReportDatabase, setSelectedReportDatabase] = useState('');
  const [selectedReportQuery, setSelectedReportQuery] = useState(null);
  const [selectedReportName, setSelectedReportName] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [updateModalOpen, setUpdateModalOpen] = useState(false);
  const [newQuery, setNewQuery] = useState('');
  const [requestId, setRequestId] = useState('');
  const [showUpdateSuccess, setShowUpdateSuccess] = useState(false);
  const [queryResult, setQueryResult] = useState([]);
  const [runQueryModal, setRunQueryModal] = useState(false);
  const navigate = useNavigate();

  const [selectedTab, setSelectedTab] = useState(0);
  const [ownerships, setOwnerships] = useState([]);
  const filteredOwnerships = ownerships.filter((ownership) => ownership.report.name === selectedReportName);

  const handleSearch = async () => {
    try {
      const response = await fetch(`/report/searchReport?name=${searchTerm}`);
      const data = await response.json();
      console.log("This is the data object received: ", data);
      await setSelectedReportObject(data);
      console.log("This is the selected report object: " + selectedReportObject);
      setSelectedReport(data._id);
      setSelectedReportName(data.name);
      setSelectedReportQuery(data.sqlQuery);
      if (data.database) {
        setSelectedReportDatabase(data.database.name);
      }
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

  const runQuery = async () => {
    setRunQueryModal(true);
    const query = { 
       filter: selectedReportQuery,
       databaseName: selectedReportDatabase
      };
    const queryParams = new URLSearchParams(query).toString();

    try {
        const response = await fetch(`/employee/runQuery?${queryParams}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json'
        }
      });
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      const results = await response.json();
      setQueryResult(results);
      console.log(results);
    } catch (error) {
      console.error('There was a problem with the fetch operation:', error);
    }
  };

  function showSchedules(selectedReport) {
    navigate(`/report-schedules/${selectedReport}`);
  }

  function addOwnership() {
    navigate(`/addOwnership`);
  }

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
                  <TableCell component="th" scope="row">Details:</TableCell>
                    <TableCell align="left" style={{display: 'flex', justifyContent: 'space-between'}}>
                          <IconButton aria-label='query' onClick={() => setShowModal(!showModal)}>
                            <VisibilityIcon />
                          </IconButton>
                          <IconButton onClick={() => setUpdateModalOpen(true)}>
                            <CreateIcon />
                          </IconButton>
                          <IconButton aria-label='runQuery' onClick={runQuery} >
                              <PlayCircleIcon />
                          </IconButton>
                          <IconButton aria-label='mail' onClick={() => showSchedules(selectedReport)}>
                           <Mail />
                          </IconButton>
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
                  <TableCell component="th" scope="row">Database Name:</TableCell>
                  <TableCell align="left">
                    {result.database ? result.database.name : "No Database"}
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
              <TableRow>
                  <TableCell align="left">
                    <Button variant='outlined' onClick={addOwnership}> Add Ownership </Button>
                  </TableCell>
                </TableRow>
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
          <Modal open={runQueryModal} onClose={() => setRunQueryModal(false)}>
            <Box sx={{position: 'absolute', top: '15%', left: '20%', width: 1200, height: '600px', overflow: 'auto', boxShadow: 24, p: 4, bgcolor: 'background.paper'}}>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell>ID</TableCell>
                  {selectedReportDatabase !== 'Requests' && <TableCell>Name</TableCell>}
                  {selectedReportDatabase === 'Employees' && <TableCell>Email</TableCell>}
                  {selectedReportDatabase === 'Reports' && (
                    <>
                      <TableCell>SQL Query</TableCell>
                      <TableCell>Active</TableCell>
                    </>
                  )}
                  {selectedReportDatabase === 'Requests' && (
                    <>
                      <TableCell>Description</TableCell>
                      <TableCell>Status</TableCell>
                    </>
                  )}
                  {selectedReportDatabase === 'Schedules' && (
                    <>
                      <TableCell>Mail Subject</TableCell>
                    </>
                  )}
                  {selectedReportDatabase === 'Teams' && (
                    <>
                      <TableCell>Team Mail</TableCell>
                    </>
                  )}
                  {selectedReportDatabase === 'Triggers' && (
                    <>
                      <TableCell>Cron Expression</TableCell>
                    </>
                  )}
                </TableRow>
              </TableHead>
              <TableBody>
                {queryResult.map(row => {
                  const rowData = JSON.parse(row);
                  return(
                    <TableRow key={rowData._id}>
                      <TableCell>{rowData._id}</TableCell>
                      {selectedReportDatabase !== 'Requests' && <TableCell>{rowData.name}</TableCell>}
                      {selectedReportDatabase === 'Employees' && <TableCell>{rowData.email}</TableCell>}
                      {selectedReportDatabase === 'Reports' && (
                        <>
                          <TableCell>{rowData.sqlQuery}</TableCell>
                          <TableCell>{rowData.isActive}</TableCell>
                        </>
                      )}
                      {selectedReportDatabase === 'Requests' && (
                        <>
                          <TableCell>{rowData.description}</TableCell>
                          <TableCell>{rowData.status}</TableCell>
                        </>
                      )}
                      {selectedReportDatabase === 'Schedules' && (
                        <>
                          <TableCell>{rowData.mailSubject}</TableCell>
                        </>
                      )}
                      {selectedReportDatabase === 'Teams' && (
                        <>
                          <TableCell>{rowData.teamMail}</TableCell>
                        </>
                      )}
                      {selectedReportDatabase === 'Triggers' && (
                        <>
                          <TableCell>{rowData.cronExpression}</TableCell>
                        </>
                      )}
                  </TableRow>
                  );
                })}
              </TableBody>
            </Table>
            </Box>
          </Modal>
        </Card>
      ))}
      </div>
    </div>
  );
}

export default SearchReport;