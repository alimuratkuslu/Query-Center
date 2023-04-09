import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { DataGrid } from '@mui/x-data-grid';
import Dashboard from '../Dashboard';
import SearchDatabase from './SearchDatabase';
import AddIcon from '@mui/icons-material/Add';
import IconButton from '@material-ui/core/IconButton';

const columns = [
  { field: '_id', headerName: 'ID', width: 70 },
  { field: 'name', headerName: 'Database Name', width: 130 },
  { field: 'connectionString', headerName: 'Connection String', width: 200 },
];

const getRowId = (row) => row._id;

const DatabaseList = () => {
  const [databases, setDatabases] = useState([]);

  useEffect(() => {

    const fetchDatabases = async () => {
        const databaseData = await fetch('/database');
        const databaseJson = await databaseData.json();
        console.log(databaseJson);
        setDatabases(databaseJson);
    };
    fetchDatabases();

  }, []);

  return (
    <div style={{ height: 600, width: '100%' }}>
      <Dashboard />
      <SearchDatabase />

      <Link style={{position: 'absolute', top: '100px', right: '100px'}} to="/addDatabase">
        <IconButton color='primary' >
          <AddIcon />
        </IconButton>
      </Link>
      <br />
      <div style={{ height: '65vh', width: '100%', display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
        <div style={{width: '80%', height: 450}}>
          <DataGrid rows={databases} columns={columns} getRowId={getRowId} pageSize={5} />
        </div>
      </div>
    </div>
  );
};

export default DatabaseList;

