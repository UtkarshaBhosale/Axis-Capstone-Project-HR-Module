import "./App.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Navbar from "./Components/Navbar";
import Footer from "./Components/Footer";
import Login from "./Components/Login";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import Form from "./Job-Details/Job-Posting-Form";
import JobDetailsTable from "./Job-Details/ViewJobs";
import UpdateJobApplicationStatus from "./Employees/UpdateStatus";
import EmployeeTable from "./Employees/ViewEmployee";
import Dashboard from "./Components/hr-dashboard";
import Employee from "./Employees/RegisterEmployee";
import PieChart from "./Components/Piechart";



function App() {
  return (
    <div>
      <BrowserRouter>
        <Navbar />
        <Routes>
          <Route path="/hr-dashboard" element={<Dashboard />} />
          <Route path="/Login" element={<Login />} />
          <Route path="/Piechart" element={<PieChart />} />
          <Route path="/Job-Posting-Form" element={<Form />} />
          <Route path="/ViewJobs" element={<JobDetailsTable />} />
          <Route
            path="/UpdateStatus"
            element={<UpdateJobApplicationStatus />}
          />
          <Route path="/ViewEmployee" element={<EmployeeTable />} />

          <Route path="RegisterEmployee" element={<Employee />} />
        
        </Routes>
        <Footer />
      </BrowserRouter>
      <ToastContainer />
    </div>
  );
}

export default App;
