import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Button } from 'react-bootstrap';
import axios from 'axios';
import { Link } from 'react-router-dom';

const NoteList = () => {
    const [notes, setNotes] = useState([]);

    useEffect(() => {
        loadNotes();
    }, []);

    const loadNotes = async () => {
        try {
            const response = await axios.get("http://localhost:8080/notes/all");
            setNotes(response.data.content);
        } catch (error) {
            console.log(error);
        }
    };

    return (
        <Container>
            <Row>
                <Col>
                    <h1>Notes</h1>
                    {notes && notes.length > 0 ? (
                        <ul>
                            {notes.map((note) => (
                                <li key={note.id}>
                                    <p>ID: {note.id}</p>
                                    <p>Content: {note.content}</p>
                                    <Link to={`/notes/${note.id}`}>View Details</Link>
                                </li>
                            ))}
                        </ul>
                    ) : (
                        <p>No notes found.</p>
                    )}
                    <Button variant="primary" onClick={loadNotes}>
                        Refresh Notes
                    </Button>
                </Col>
            </Row>
        </Container>
    );
};

export default NoteList;
