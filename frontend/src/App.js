import './App.css';
import LoginPage from './components/LoginPage';
import DatabaseList from './components/Databases/DatabaseList';
import EmployeeList from './components/Employees/EmployeeList';
import ReportList from './components/Reportss/ReportList';
import ReportSchedules from './components/Reportss/ReportSchedules';
import ScheduleList from './components/Schedules/ScheduleList';
import TriggerList from './components/Triggers/TriggerList';
import ReportOwnershipList from './components/Ownerships/ReportOwnershipList';
import RequestList from './components/Requests/RequestList';
import TeamList from './components/Teams/TeamList';
import Dashboard from './components/Dashboard';
import SearchReport from './components/Reportss/SearchReport';
import AddDatabase from './components/Databases/AddDatabase';
import AddEmployee from './components/Employees/AddEmployee';
import AddReport from './components/Reportss/AddReport';
import AddSchedule from './components/Schedules/AddSchedule';
import UpdateSchedule from './components/Schedules/UpdateSchedule';
import AddTrigger from './components/Triggers/AddTrigger';
import AddOwnership from './components/Ownerships/AddOwnership';
import AddRequest from './components/Requests/AddRequest';
import AddTeam from './components/Teams/AddTeam';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

function App() {
  return (
    <Router>
      <Routes>
        <Route exact path='/login' element={<LoginPage />} />
        <Route exact path='/' element={<Dashboard />} />
        <Route exact path='/employee' element={<EmployeeList />} />
        <Route exact path='/report' element={<ReportList />} />
        <Route exact path='/report-schedules/:selectedReport' element={<ReportSchedules />} />
        <Route exact path='/schedule' element={<ScheduleList />} />
        <Route exact path='/trigger' element={<TriggerList />} />
        <Route exact path='/ownership' element={<ReportOwnershipList />} />
        <Route exact path='/request' element={<RequestList />} />
        <Route exact path='/team' element={<TeamList />} />
        <Route exact path='/database' element={<DatabaseList />} />
        <Route exact path='/searchReport' element={<SearchReport />} />
        <Route exact path='/addEmployee' element={<AddEmployee />} />
        <Route exact path='/addReport' element={<AddReport />} />
        <Route exact path='/addSchedule' element={<AddSchedule />} />
        <Route exact path='/update-schedule/:selectedSchedule' element={<UpdateSchedule />} />
        <Route exact path='/addTrigger' element={<AddTrigger />} />
        <Route exact path='/addOwnership' element={<AddOwnership />} />
        <Route exact path='/addRequest' element={<AddRequest />} />
        <Route exact path='/addTeam' element={<AddTeam />} />
        <Route exact path='/addDatabase' element={<AddDatabase />} />
      </Routes>
    </Router>
  );
}

export default App;


