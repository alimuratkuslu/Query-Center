import React, { useState } from 'react';
import { Box, Button, Drawer, List, ListItem, ListItemIcon, ListItemText } from '@mui/material';
import EmployeeList from './EmployeeList';
import { Link } from 'react-router-dom';
import IconButton from '@material-ui/core/IconButton';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';


const Dashboard = () => {
  const [drawerOpen, setDrawerOpen] = useState(false);

  const handleDrawerOpen = () => {
    setDrawerOpen(true);
  };

  const handleDrawerClose = () => {
    setDrawerOpen(false);
  };

  return (
    <div>
      <h1 style={{ display: 'flex', justifyContent: 'center', alignItems: 'center'}}> Query Center </h1>
      <IconButton onClick={drawerOpen ? handleDrawerClose : handleDrawerOpen}>
        {drawerOpen ? <ChevronLeftIcon /> : <ChevronRightIcon />}
      </IconButton>
      <Drawer
        variant="persistent"
        anchor="left"
        PaperProps={{
          sx: {width: "20%"},
        }}
        open={drawerOpen}
      >
        <div/>
        <List>
          <ListItem button component={Link} to="/employee">
            <ListItemText primaryTypographyProps={{fontSize: '28px'}} primary="Employees" />
          </ListItem>
          <ListItem button component={Link} to="/report">
            <ListItemText primaryTypographyProps={{fontSize: '28px'}} primary="Reports" />
          </ListItem>
          <ListItem button component={Link} to="/schedule">
            <ListItemText primaryTypographyProps={{fontSize: '28px'}} primary="Schedules" />
          </ListItem>
          <ListItem button component={Link} to="/trigger">
            <ListItemText primaryTypographyProps={{fontSize: '28px'}} primary="Triggers" />
          </ListItem>
        </List>
      </Drawer>
      <Box textAlign='center'>
        <Button variant='contained' onClick={() => {
          if(drawerOpen == false){
            setDrawerOpen(true);
          }
          else{
            setDrawerOpen(false);
          }
        }}> Toggle Navbar</Button>
      </Box>
    </div>
  );
};

export default Dashboard;
