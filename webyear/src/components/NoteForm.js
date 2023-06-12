import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Form, Button } from 'react-bootstrap';
import axios from 'axios';

const NoteForm = () => {
  const [content, setContent] = useState('');
  const [senderId, setSenderId] = useState('');
  const [receiverId, setReceiverId] = useState('');
  const [users, setUsers] = useState([]);
  const [sender, setSender] = useState(null);
const [receiver, setReceiver] = useState(null);

  useEffect(() => {
    loadUsers();
  }, []);

  const loadUsers = async () => {
    try {
      const response = await axios.get("http://localhost:8080/users/all?page=0&size=10");
       setUsers(response.data.content);
    } catch (error) {
      console.log(error);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
  
    // Validate that sender and receiver are not the same
    if (senderId === receiverId) {
      alert('Sender and receiver cannot be the same.');
      return;
    }
  
    // Prepare the request data
    
  
    try {
      // Send the note data to the backend
      const response = await axios.post('http://localhost:8080/notes', null, {
        params: {
          content: content,
          senderid: senderId,
          receiverid: receiverId
        }
      });
      // Handle success response here
      console.log('Note created:', response.data);
  
      // Reset form fields
      setContent('');
      setSenderId('');
      setReceiverId('');
    } catch (error) {
      // Handle error response here
      console.error('Error creating note:', error.response.data);
      alert('Error creating note. Please try again.');
      
    }
  };

  return (
    <Container>
      <Row>
        <Col>
          <Form onSubmit={handleSubmit}>
            <Form.Group controlId="note">
              <Form.Label>Note Content</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter note content"
                name="content"
                value={content}
                onChange={e => setContent(e.target.value)}
              />
            </Form.Group>

            <Form.Group controlId="senderId">
              <Form.Label>Sender ID</Form.Label>
              <Form.Control
                as="select"
                value={senderId}
                onChange={e => setSenderId(e.target.value)}
              >
                <option value="">Select sender</option>
                {users.map(user => (
                  <option key={user.id} value={user.id}>
                    {user.id}
                  </option>
                ))}
              </Form.Control>
            </Form.Group>

            <Form.Group controlId="receiverId">
              <Form.Label>Receiver ID</Form.Label>
              <Form.Control
                as="select"
                value={receiverId}
                onChange={e => setReceiverId(e.target.value)}
              >
                <option value="">Select receiver</option>
                {users
                  .filter(user => user.id !== senderId)
                  .map(user => (
                    <option key={user.id} value={user.id}>
                      {user.id}
                    </option>
                  ))}
              </Form.Control>
            </Form.Group>

            <Button variant="primary" type="submit">
              Send Note
            </Button>
          </Form>
        </Col>
      </Row>
    </Container>
  );
};

export default NoteForm;
