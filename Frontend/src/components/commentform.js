import React, { useState } from 'react';

export default function CommentForm({ onSubmit }) {
  const [author, setAuthor] = useState('');
  const [comment, setComment] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    // Kreiramo objekat sa podacima iz forme
    const newComment = {
      author: author,
      comment: comment,
    };
    // Pozivamo funkciju za submit koju smo prosledili kao prop
    onSubmit(newComment);
    // Resetujemo formu
    setAuthor('');
    setComment('');
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label htmlFor="author">Autor:</label>
        <br></br>
        <input
          type="text"
          id="author"
          value={author}
          onChange={(e) => setAuthor(e.target.value)}
          required
        />
      </div>
      <br></br>
      <div>
        <label htmlFor="comment">Komentar:</label>
        <br></br>
        <textarea
          id="comment"
          value={comment}
          onChange={(e) => setComment(e.target.value)}
          required
        />
      </div>
      <button type="submit" disabled={!author || !comment}>Submit</button>
    </form>
  );
}
