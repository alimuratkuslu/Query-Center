import React, { useState, useEffect } from 'react';
import { Drawer, List, ListItem, ListItemIcon, ListItemText, AppBar, Toolbar, Typography } from '@mui/material';
import { Link } from 'react-router-dom';
import { useLocation } from 'react-router-dom';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import CloseIcon from '@material-ui/icons/Close';
import AccessibilityIcon from '@mui/icons-material/Accessibility';
import SummarizeIcon from '@mui/icons-material/Summarize';
import ScheduleIcon from '@mui/icons-material/Schedule';
import FindInPageIcon from '@mui/icons-material/FindInPage';
import CalendarMonthIcon from '@mui/icons-material/CalendarMonth';
import CopyrightIcon from '@mui/icons-material/Copyright';
import RequestPageIcon from '@mui/icons-material/RequestPage';
import GroupIcon from '@mui/icons-material/Group';

const Dashboard = () => {
  const [drawerOpen, setDrawerOpen] = useState(false);
  const [appBarTitle, setAppBarTitle] = useState('Query Center');
  const location = useLocation();

  const handleDrawerOpen = () => {
    setDrawerOpen(true);
  };

  const handleDrawerClose = () => {
    setDrawerOpen(false);
  };

  const handlePageChange = (title) => {
    console.log(title);
    setAppBarTitle(title);
    handleDrawerClose();
  };

  useEffect(() => {
    const currentPath = location.pathname;
    switch (currentPath) {
      case '/':
        setAppBarTitle('Query Center');
        break;
      case '/employee':
        setAppBarTitle('Employees');
        break;
      case '/report':
        setAppBarTitle('Reports');
        break;
      case '/schedule':
        setAppBarTitle('Schedules');
        break;
      case '/trigger':
        setAppBarTitle('Triggers');
        break;
      case '/ownership':
        setAppBarTitle('Ownerships');
        break;
      case '/request':
        setAppBarTitle('Requests');
        break;
      case '/team':
        setAppBarTitle('Teams');
        break;
      case '/searchReport':
        setAppBarTitle('Search Report');
        break;
      default:
        setAppBarTitle('Query Center');
        break;
    }
  }, [location]);

  return (
    <div>
      <AppBar position="static" key={appBarTitle}>
        <Toolbar>
          <IconButton
            onClick={drawerOpen ? handleDrawerClose : handleDrawerOpen}
            sx={{ mr: 2 }}
          >
            {drawerOpen ? <CloseIcon /> : <MenuIcon />}
          </IconButton>
          <Typography variant="h6" component="div" sx={{flexGrow: 1, textAlign: 'center', fontSize: '24px'}}>
            {appBarTitle}
          </Typography>
        </Toolbar>
      </AppBar>
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
          <ListItem button component={Link} to="/schedule" onClick={() => handlePageChange('Schedules')}>
            <ListItemIcon>
              <CalendarMonthIcon />
            </ListItemIcon>
            <ListItemText primaryTypographyProps={{fontSize: '28px'}} primary="Schedules" />
          </ListItem>
          <ListItem button component={Link} to="/trigger" onClick={() => handlePageChange('Triggers')}>
            <ListItemIcon>
              <ScheduleIcon />
            </ListItemIcon>
            <ListItemText primaryTypographyProps={{fontSize: '28px'}} primary="Triggers" />
          </ListItem>
          <ListItem button component={Link} to="/ownership" onClick={() => handlePageChange('Ownerships')}>
            <ListItemIcon>
              <CopyrightIcon />
            </ListItemIcon>
            <ListItemText primaryTypographyProps={{fontSize: '28px'}} primary="Ownerships" />
          </ListItem>
          <ListItem button component={Link} to="/request" onClick={() => handlePageChange('Requests')}>
            <ListItemIcon>
              <RequestPageIcon />
            </ListItemIcon>
            <ListItemText primaryTypographyProps={{fontSize: '28px'}} primary="Requests" />
          </ListItem>
          <ListItem button component={Link} to="/team" onClick={() => handlePageChange('Teams')}>
            <ListItemIcon>
              <GroupIcon />
            </ListItemIcon>
            <ListItemText primaryTypographyProps={{fontSize: '28px'}} primary="Teams" />
          </ListItem>
          <ListItem button component={Link} to="/searchReport" onClick={() => handlePageChange('Search Reports')}>
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