import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Form, Button } from 'react-bootstrap';
import axios from 'axios';

const NoteView = () => {
  const [content, setContent] = useState('');
  const [users, setUsers] = useState([]);
  const [selectedRecipientId, setSelectedRecipientId] = useState(null);
  const [notes, setNotes] = useState([]);

  useEffect(() => {
    loadUsers();
  }, []);

  const loadUsers = async () => {
    try {
      const response = await axios.get("http://localhost:8080/users/all?page=0&size=10");
       setUsers(response.data.content);
    } catch (error) {
      console.log(error);
      setUsers([])
    }
  };


  const handleRecipientChange = (event) => {
    setSelectedRecipientId(event.target.value);
  };

  const fetchNotesByRecipient = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/notes/receiver/${selectedRecipientId}`);
      setNotes(response.data);
    } catch (error) {
      console.log(error);
      setNotes([]);
    }
  };

  return (
    <Container>
      <h2>Note Form</h2>
      <Form>
        <Form.Group>
          <Form.Label>Recipient:</Form.Label>
          <Form.Control as="select" onChange={handleRecipientChange}>
            <option value="">Select recipient</option>
            {users.map((user) => (
              <option key={user.id} value={user.id}>
                {user.id}
              </option>
            ))}
          </Form.Control>
        </Form.Group>
        <Button onClick={fetchNotesByRecipient}>Fetch Notes</Button>
      </Form>

      <h2>Notes</h2>
      {notes.length > 0 ? (
        <ul>
        {notes.map((note) => (
          <li key={note.id}>
             ID: {note.id}, Content: {note.content}, 
        Sender ID: {note.senderId.id}, Receiver ID: {note.receiverId.id}
          </li>
        ))}
      </ul>
      ) : (
        <p>No notes found.</p>
      )}
    </Container>
  );
};

export default NoteView;
