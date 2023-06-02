import './App.css';
import './pages/Navigation'
import { Container } from 'react-bootstrap'
import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import Navigation from './pages/Navigation';
import AddUser from './components/AddUser';
import Profile from './components/Profile';
import EditUser from './components/EditUser';
import ViewUser from './components/ViewUser';



function App() {
  return (
    <div className="App">
      <Router>
        <Navigation />
        <Container>
          <Routes>

          <Route exact path="/profile"  element={<Profile />} />
          <Route  exact path="/add" element={<AddUser />}/>
          <Route exact path='/edituser/:id' element={<EditUser />} />
          <Route exact path='/viewuser/:id' element={<ViewUser />} />
          
          </Routes>

        </Container>

      </Router>



    </div>


  );
}

export default App;
