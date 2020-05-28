import React, { useState} from 'react';


function TodoForm ({ addTodo}) {

    const [subject, setSubject ] = useState('');
    const [content, setContent ] = useState('');

    const submitHandler = event => {
        event.preventDefault();
        addTodo({ subject, content}).then(() => {
            setSubject('');
            setContent('');
        })

    };

    return (

        <form onSubmit={submitHandler}>
        <p>
          제목 <input value={subject} onChange={e => setSubject(e.target.value)} />
        </p>

        내용
        <div>
          <textarea cols="80" rows="10" value={content} onChange={e => setContent(e.target.value)}></textarea>
        </div>
          <button type="submit">추가</button>
        </form>
    );
}


export default TodoForm;
