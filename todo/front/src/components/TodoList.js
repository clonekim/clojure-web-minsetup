import React from 'react';


function TodoList({ todos, deleteHandler }) {

    const list = todos.map(todo => {
        const label = todo.done ? '완료':'미완료';

        return (
            <li key={todo.id}>
            {todo.subject}
            <button>{label}</button>
            <button onClick={()=> deleteHandler(todo.id)}>삭제</button>
            {todo.created_at}
            </li>
        );
    });

    return <ul>{list}</ul>;

}


export default TodoList;
