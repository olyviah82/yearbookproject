import './App.css';
import './pages/Navigation'
import { Container } from 'react-bootstrap'
import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import Navigation from './pages/Navigation';
import AddUser from './components/AddUser';
import Profile from './components/Profile';
import EditUser from './components/EditUser';
import ViewUser from './components/ViewUser';
import NoteForm from './components/NoteForm';
import NoteList from './components/NoteList';
import NoteDetails from './components/NoteDetails';
import NoteView from './components/NoteView';
import NoteViewSender from './components/NoteViewSender';



function App() {
  return (
    <div className="App">
      <Router>
        <Navigation />
        <Container>
          <Routes>
            <Route exact path='/noteform' element={<NoteForm />} />
            <Route exact path="/profile" element={<Profile />} />
            <Route exact path="/add" element={<AddUser />} />
            <Route exact path='/edituser/:id' element={<EditUser />} />
            <Route exact path='/viewuser/:id' element={<ViewUser />} />
            <Route exact path="/notes" element={<NoteList />} />
            <Route path="/notes/:noteId" element={<NoteDetails />} />
            <Route path="/notesview" element={<NoteView/>} />
            <Route path="/notesviewsender" element={<NoteViewSender/>} />
          
          </Routes>

        </Container>

      </Router>



    </div>


  );
}

export default App;
