import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

const NoteDetails = () => {
  const { noteId } = useParams();
  const [note, setNote] = useState(null);

  useEffect(() => {
    const fetchNote = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/notes/${noteId}`);
        setNote(response.data);
      } catch (error) {
        console.log(error);
      }
    };

    fetchNote();
  }, [noteId]);

  if (!note) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <h2>Note Details</h2>
    <p>ID: {note.id}</p>
    <p>Content: {note.content}</p>
    <p>Sender ID: {note.senderId.id}</p>
    <p>Receiver ID: {note.receiverId.id}</p>
    </div>
  );
};

export default NoteDetails;
