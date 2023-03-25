import React, { useState } from 'react';
import { Box, Button, Drawer, List, ListItem, ListItemIcon, ListItemText } from '@mui/material';
import EmployeeList from './EmployeeList';
import { Link } from 'react-router-dom';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import CloseIcon from '@material-ui/icons/Close';
import AccessibilityIcon from '@mui/icons-material/Accessibility';
import SummarizeIcon from '@mui/icons-material/Summarize';
import ScheduleIcon from '@mui/icons-material/Schedule';
import FindInPageIcon from '@mui/icons-material/FindInPage';
import CalendarMonthIcon from '@mui/icons-material/CalendarMonth';

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
      <Box paddingLeft='20px'>
        <IconButton
            onClick={drawerOpen ? handleDrawerClose : handleDrawerOpen}
            sx={{ position: 'absolute', bottom: '16px' }}
          >
            {drawerOpen ? <CloseIcon /> : <MenuIcon />}
        </IconButton>
      </Box>
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
            <ListItemIcon>
              <AccessibilityIcon />
            </ListItemIcon>
            <ListItemText primaryTypographyProps={{fontSize: '28px'}} primary="Employees" />
          </ListItem>
          <ListItem button component={Link} to="/report">
            <ListItemIcon>
              <SummarizeIcon />
            </ListItemIcon>
            <ListItemText primaryTypographyProps={{fontSize: '28px'}} primary="Reports" />
          </ListItem>
          <ListItem button component={Link} to="/schedule">
            <ListItemIcon>
              <CalendarMonthIcon />
            </ListItemIcon>
            <ListItemText primaryTypographyProps={{fontSize: '28px'}} primary="Schedules" />
          </ListItem>
          <ListItem button component={Link} to="/trigger">
            <ListItemIcon>
              <ScheduleIcon />
            </ListItemIcon>
            <ListItemText primaryTypographyProps={{fontSize: '28px'}} primary="Triggers" />
          </ListItem>
          <ListItem button component={Link} to="/searchReport">
            <ListItemIcon>
              <FindInPageIcon />
            </ListItemIcon>
            <ListItemText primaryTypographyProps={{fontSize: '28px'}} primary="Search Reports" />
          </ListItem>
        </List>
        <IconButton
          onClick={drawerOpen ? handleDrawerClose : handleDrawerOpen}
          sx={{ position: 'absolute', bottom: '16px' }}
        >
          {drawerOpen ? <CloseIcon /> : <MenuIcon />}
        </IconButton>
      </Drawer>
    </div>
  );
};

export default Dashboard;
