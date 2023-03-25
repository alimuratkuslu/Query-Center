import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import SearchTrigger from './SearchTrigger';
import { DataGrid } from '@mui/x-data-grid';
import Dashboard from './Dashboard';

import AddIcon from '@mui/icons-material/Add';
import IconButton from '@material-ui/core/IconButton';

const columns = [
  { field: '_id', headerName: 'ID', width: 70 },
  { field: 'name', headerName: 'Trigger Name', width: 130 },
];

const getRowId = (row) => row._id;

const TriggerList = () => {
  const [triggers, setTriggers] = useState([]);

  useEffect(() => {

    const fetchTriggers = async () => {
        const triggerData = await fetch('/trigger');
        const triggerJson = await triggerData.json();
        console.log(triggerJson);
        setTriggers(triggerJson);
    };
    fetchTriggers();

  }, []);

  return (
    <div style={{ height: 600, width: '100%' }}>
      <Dashboard />
      <SearchTrigger />

      <Link style={{position: 'absolute', top: '100px', right: '100px'}} to="/addTrigger">
        <IconButton color='primary' >
          <AddIcon />
        </IconButton>
      </Link>
      <br />
      <DataGrid rows={triggers} columns={columns} getRowId={getRowId} pageSize={5} />
    </div>
  );
};

export default TriggerList;

