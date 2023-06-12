import React, { useEffect, useState } from 'react';
import axios from 'axios';

const NotesBySender = ({ senderId }) => {
  const [notes, setNotes] = useState([]);

  useEffect(() => {
    const fetchNotes = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/notes/sender/${senderId}`);
        setNotes(response.data);
      } catch (error) {
        console.log(error);
      }
    };

    fetchNotes();
  }, [senderId]);

  if (notes.length === 0) {
    return <div>No notes found.</div>;
  }

  return (
    <div>
      <h2>Notes by Sender</h2>
      {notes.map((note) => (
        <div key={note.id}>
          <p>ID: {note.id}</p>
          <p>Content: {note.content}</p>
          <p>Sender ID: {note.senderId.id}</p>
          <p>Receiver ID: {note.receiverId.id}</p>
          <hr />
        </div>
      ))}
    </div>
  );
};

export default NotesBySender;
