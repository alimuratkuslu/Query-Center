import './App.css';
import EmployeeList from './components/EmployeeList';
import ReportList from './components/ReportList';
import ScheduleList from './components/ScheduleList';
import TriggerList from './components/TriggerList';
import Dashboard from './components/Dashboard';
import SearchReport from './components/SearchReport';
import AddEmployee from './components/AddEmployee';
import AddReport from './components/AddReport';
import AddSchedule from './components/AddSchedule';
import AddTrigger from './components/AddTrigger';
import { BrowserRouter as Router, Route, Link, Routes } from 'react-router-dom';

function App() {
  return (
    <Router>
      <Routes>
        <Route exact path='/' element={<Dashboard />} />
        <Route exact path='/employee' element={<EmployeeList />} />
        <Route exact path='/report' element={<ReportList />} />
        <Route exact path='/schedule' element={<ScheduleList />} />
        <Route exact path='/trigger' element={<TriggerList />} />
        <Route exact path='/searchReport' element={<SearchReport />} />
        <Route exact path='/addEmployee' element={<AddEmployee />} />
        <Route exact path='/addReport' element={<AddReport />} />
        <Route exact path='/addSchedule' element={<AddSchedule />} />
        <Route exact path='/addTrigger' element={<AddTrigger />} />
      </Routes>
    </Router>
  );
}

export default App;

